package org.fog.placement;

import org.fog.application.AppEdge;
import org.fog.application.AppModule;
import org.fog.application.Application;
import org.fog.entities.Actuator;
import org.fog.entities.FogDevice;
import org.fog.entities.Sensor;
import org.fog.entities.Tuple;
import org.fog.utils.TimeKeeper;

import java.util.*;

public class ModulePlacementHets extends ModulePlacement{

    public static List<Sensor> sensors;
    public static List<Actuator>  actuators;
    public Map<String, Double> moduleRank;
    protected ModuleMapping moduleMapping;
    /**
     * Stores the current mapping of application modules to fog devices
     */

    protected Map<Integer,List<String> >currentModuleMap;




    protected Map<Integer, Double> currentCpuLoad;
    protected Map<Integer, Map<String, Double>> currentModuleLoadMap;
    protected Map<Integer, Map<String, Integer>> currentModuleInstanceNum;

    boolean TREEPASSAGE=false;

    /**
     * variable qui representre la presence d'un successeur pour un module
     */


    public ModulePlacementHets(List<FogDevice> fogdevices, List<Sensor> sensors, List<Actuator> actuators, Application applications ) {

        this.setFogDevices(fogdevices);
        this.setApplication(applications);
        this.setActuators(actuators);
        this.setSensors(sensors);
        this.setModuleToDeviceMap(new HashMap<String, List<Integer>>());
        this.setDeviceToModuleMap(new HashMap<Integer, List<AppModule>>());
        setCurrentModuleMap(new HashMap<Integer, List<String>>());
        setCurrentCpuLoad(new HashMap<Integer, Double>());
        setCurrentModuleLoadMap(new HashMap<Integer, Map<String, Double>>());
        setCurrentModuleInstanceNum(new HashMap<Integer, Map<String, Integer>>());

        for(FogDevice dev : getFogDevices()){
            getCurrentModuleMap().put(dev.getId(), new ArrayList<String>());
            getCurrentCpuLoad().put(dev.getId(), 0.0);
            getCurrentModuleLoadMap().put(dev.getId(), new HashMap<String, Double>());
            getCurrentModuleInstanceNum().put(dev.getId(), new HashMap<String, Integer>());
        }

        mapModules();

    }



    @Override
    public void mapModules() {
        /**
         * ajout des sensors et des actuators aux modules à placer
         */
        List<String> placedModules = new ArrayList<String>();
        Map<String ,Double> deviceMap = new HashMap<>();
        for(FogDevice fogDevice :getFogDevices())
        {
            deviceMap.put(fogDevice.getName(),fogDevice.getVmAllocationPolicy().getHostList().get(0).getPeList().get(0).getPeProvisioner().getMips());
            for(Sensor sensor : getSensors())
                if(sensor.getGatewayDeviceId()==fogDevice.getId())
                    if(!placedModules.contains(sensor.getTupleType()))
                        placedModules.add(sensor.getTupleType());

            for(Actuator actuator : getActuators())
                if(actuator.getGatewayDeviceId()==fogDevice.getId())
                    if(!placedModules.contains(actuator.getActuatorType()))
                        placedModules.add(actuator.getActuatorType());
        }
            listScheduling();
        getHetsPlacement(placedModules);

        for(int deviceId : getCurrentModuleMap().keySet()){
            for(String module : getCurrentModuleMap().get(deviceId)){
                createModuleInstanceOnDevice(getApplication().getModuleByName(module), getFogDeviceById(deviceId));
                //TimeKeeper.getInstance().getExecutionTimeModule().put(module,0.0);
            }
        }

    }




    public void getHetsPlacement(List<String> placedModules)

    {       int deviceId=0;
        List<String> orderModule = orderModule();

        while(orderModule.size()>0)
        {
            Map<Integer, Double>  nameToMips = new HashMap<Integer,Double>();
            double bwRate =0;
            String  moduleName = orderModule.get(0);

            /**
             * Calcul la proportion des mips pour chaque device.**Choix du device avec la proportion minimale
             */
            AppModule module= getApplication().getModuleByName(moduleName);
            for(FogDevice fogDevice : getFogDevices())
            {
                double mean = (fogDevice.getDownlinkBandwidth()+fogDevice.getUplinkBandwidth())/2;
                 bwRate=module.getBw()/mean;
                 nameToMips.put(fogDevice.getId(),bwRate);
            }

            deviceId = getMinKey(nameToMips);

            /**
             * effectue le placement du module sur un device s'il est libre sinon il  fait le placement sur un autre device disponible
             */
            if(!currentModuleMap.containsKey(deviceId))
            {
              orderModule.remove(moduleName);
                FogDevice device = getDeviceById(deviceId);
                currentModuleMap.get(deviceId).add(moduleName);
                //double availableMips= device.getVmAllocationPolicy().getHostList().get(0).getPeList().get(0).getPeProvisioner().getMips()-getApplication().getModuleByName(moduleName).getMips();
                //device.getVmAllocationPolicy().getHostList().get(0).getPeList().get(0).getPeProvisioner().setMips(availableMips);
                System.out.println("Placement of operator "+moduleName+ " on device "+device.getName()+ " successful.");

            }
            else
            {
                for(Integer _module : currentModuleMap.keySet()) {
                    if (!currentModuleMap.get(_module).isEmpty() ) {
                        nameToMips.remove(_module);
                    }
                }

                orderModule.remove(moduleName);
                deviceId =getMinKey(nameToMips);
                FogDevice device = getDeviceById(deviceId);
                currentModuleMap.get(deviceId).add(moduleName);
                System.out.println("Placement of operator "+moduleName+ " on device "+ device.getName()+ " successful.");

            }

        }

    }


    public int getMinKey(Map<Integer,Double> nameToMips)
    {
        int deviceId = 0;
        double bestDevice =Collections.min(nameToMips.values());
        for(int  key :nameToMips.keySet())
            if(nameToMips.get(key).equals(bestDevice))
                deviceId=key;

        return deviceId;
    }

    public void listScheduling() {
        Map<String, Double> rank= new HashMap<String, Double>();
        for(AppModule module : getApplication().getModules())
        {

            rank.put(module.getName(),rankCompute(module));
        }
        setModuleRank(rank);
    }

    /**
     *  calcul le rank pour module de l'application
     * @param appModule
     * @return list
     */

    public double rankCompute( AppModule appModule) {
        Map<String, Double> currentRank = new HashMap<String, Double>();
        double rang ;
        for (AppEdge edge : getApplication().getEdges()) {

            if (edge.getSource().contentEquals(appModule.getName()) & (Tuple.UP == edge.getDirection())) {
                rang = edge.getTupleCpuLength() + getApplication().getModuleByName(edge.getDestination()).getMips();
                currentRank.put( edge.getDestination(),rang);
                TREEPASSAGE=true;
            }
        }

        if (currentRank.isEmpty() ) {
            if(TREEPASSAGE ) {
                TREEPASSAGE = false;
                return 0;
            }
            else
                return appModule.getMips();
        }

        else
        {   Map<String,Double> sortedMap =new TreeMap<String, Double>(currentRank);
            Set set = sortedMap.entrySet();
            Iterator iterator=set.iterator();
            Map.Entry entry = (Map.Entry) iterator.next();
            return appModule.getMips() + (Double) entry.getValue() + rankCompute(getApplication().getModuleByName((String) entry.getKey()));
        }

    }

    /**
     * donne une liste ordonnée suivant les valeurs des ranks
     * @return list
     */
    public  List<String>  orderModule(){
        List<String>  list= new ArrayList<>();
        Map< String,Double> map = new HashMap<>(moduleRank);
        while(map.size()>0)
        {   String name= getMinModuleName(map);
            list.add(name);
            map.remove(name);

        }
        return list ;
    }

    public String getMinModuleName(Map<String,Double> nameToMips)
    {
        Map<String ,Double> map = new HashMap< String, Double>(nameToMips);
        String moduleName =null;
        double bestDevice = Collections.max(map.values());
        for(String  key :map.keySet())
            if(map.get(key).equals(bestDevice))
                moduleName=key;
        return moduleName;

    }
    public void setModuleMapping(ModuleMapping moduleMapping) {
        this.moduleMapping = moduleMapping;
    }

    public Map<String, Double> getModuleRank() {
        return moduleRank;
    }

    public void setModuleRank(Map<String, Double> moduleRank) {
        this.moduleRank = moduleRank;
    }


    public void setSensors(List<Sensor> sensors) {
        this.sensors = sensors;
    }

    public void setActuators(List<Actuator> actuators) {
        this.actuators = actuators;
    }

    public static List<Sensor> getSensors() {
        return sensors;
    }

    public static List<Actuator> getActuators() {
        return actuators;
    }

    public Map<Integer,List <String>> getCurrentModuleMap() {
        return currentModuleMap;
    }

    public void setCurrentModuleMap(Map<Integer, List<String>> currentModuleMap) {
        this.currentModuleMap = currentModuleMap;
    }
    public Map<Integer, Double> getCurrentCpuLoad() {
        return currentCpuLoad;
    }

    public void setCurrentCpuLoad(Map<Integer, Double> currentCpuLoad) {
        this.currentCpuLoad = currentCpuLoad;
    }

    public Map<Integer, Map<String, Double>> getCurrentModuleLoadMap() {
        return currentModuleLoadMap;
    }

    public void setCurrentModuleLoadMap(Map<Integer, Map<String, Double>> currentModuleLoadMap) {
        this.currentModuleLoadMap = currentModuleLoadMap;
    }

    public Map<Integer, Map<String, Integer>> getCurrentModuleInstanceNum() {
        return currentModuleInstanceNum;
    }

    public void setCurrentModuleInstanceNum(Map<Integer, Map<String, Integer>> currentModuleInstanceNum) {
        this.currentModuleInstanceNum = currentModuleInstanceNum;
    }
}
