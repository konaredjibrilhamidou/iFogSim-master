package org.fog.placement;

import org.cloudbus.cloudsim.core.CloudSim;
import org.fog.application.AppEdge;
import org.fog.application.AppModule;
import org.fog.application.Application;
import org.fog.entities.Actuator;
import org.fog.entities.FogDevice;
import org.fog.entities.Sensor;
import org.fog.entities.Tuple;
import org.fog.utils.TimeKeeper;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class ModulePlacementHeft  extends ModulePlacement{




    protected ModuleMapping moduleMapping;
    public static List<Sensor> sensors;
    public static List<Actuator>  actuators;
    public Map<String, Double> moduleRank;

    /**
     * Stores the current mapping of application modules to fog devices
     */

    protected Map<Integer,List<String>>currentModuleMap;
    protected Map<Integer, Map<String, Double>> currentModuleLoadMap;
    protected Map<Integer, Map<String, Integer>> currentModuleInstanceNum;


     boolean TREEPASSAGE=false;

    /**
     * variable qui representre la presence d'un successeur pour un module
     */


    public ModulePlacementHeft(List<FogDevice> fogdevices, List<Sensor> sensors, List<Actuator> actuators, Application applications,ModuleMapping moduleMapping ) {
        this.setFogDevices(fogdevices);
        this.setApplication(applications);
        this.setActuators(actuators);
        this.setSensors(sensors);
        this.setModuleMapping(moduleMapping);
        this.setModuleToDeviceMap(new HashMap<String, List<Integer>>());
        this.setDeviceToModuleMap(new HashMap<Integer, List<AppModule>>());
        setCurrentModuleLoadMap(new HashMap<Integer, Map<String, Double>>());
        setCurrentModuleInstanceNum(new HashMap<Integer, Map<String, Integer>>());
        setCurrentModuleMap(new HashMap<Integer, List<String>>());

        for(FogDevice dev : getFogDevices()){
            getCurrentModuleLoadMap().put(dev.getId(), new HashMap<String, Double>());
            getCurrentModuleMap().put(dev.getId(),new ArrayList<>());
            getCurrentModuleInstanceNum().put(dev.getId(), new HashMap<String, Integer>());
            TimeKeeper.getInstance().getDeviceOccupationTime().put(dev.getId(),new ArrayList<Double>());
        }
        mapModules();
        setModuleInstanceCountMap(getCurrentModuleInstanceNum());
    }


    @Override
    protected void mapModules() {
/*
        for(FogDevice device  : getFogDevices()){
            for(AppModule module : getApplication().getModules()){
                getCurrentModuleLoadMap().get(device.getId()).put(module.getName(), 0.0);
                getCurrentModuleInstanceNum().get(device.getId()).put(module.getName(), 0);
            }
        }
*/
        listScheduling();
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

            getHeftPlacement(placedModules);

        for(int deviceId : getCurrentModuleMap().keySet()){
            for(String module : getCurrentModuleMap().get(deviceId)){
                    FogDevice device =getDeviceById(deviceId);
                    device.getVmAllocationPolicy().getHostList().get(0).getPeList().get(0).getPeProvisioner().setMips(deviceMap.get(device.getName()));
                    createModuleInstanceOnDevice(getApplication().getModuleByName(module),device);

                }

            }
    }




    public void getHeftPlacement(List<String> placedModules)
    {
        int deviceId=0;
         List<String> orderModule = orderModule();
        while(orderModule.size()>0)
        {
            Map<Integer, Double>  nameToMips = new HashMap<Integer,Double>();
            double mipsRate =0;
            String  moduleName = orderModule.get(0);

                /**
                 * Calcul la proportion des mips pour chaque device.**Choix du device avec la proportion minimale
                 */
                AppModule _module = getApplication().getModuleByName(moduleName);
                for(FogDevice fogDevice : getFogDevices())
                {
                    mipsRate= _module.getMips()/fogDevice.getVmAllocationPolicy().getHostList().get(0).getPeList().get(0).getPeProvisioner().getMips();
                    if(mipsRate < 1)
                       nameToMips.put(fogDevice.getId(),mipsRate);
                }


                deviceId = getMinKey(nameToMips);

                     getCurrentModuleInstanceNum().get(deviceId).put(moduleName, 0);
                /**
                 * effectue le placement du module sur un device s'il est libre sinon il  fait le placement sur un autre device disponible
                 */
                if(true)
                {
                    orderModule.remove(moduleName);
                    FogDevice device = getDeviceById(deviceId);
                    double availableMips= device.getVmAllocationPolicy().getHostList().get(0).getPeList().get(0).getPeProvisioner().getMips()-getApplication().getModuleByName(moduleName).getMips();
                    device.getVmAllocationPolicy().getHostList().get(0).getPeList().get(0).getPeProvisioner().setMips(availableMips);
                    System.out.println("Placement of operator "+moduleName+ " on device "+device.getName()+ " successful.");
                    getCurrentModuleInstanceNum().get(deviceId).put(moduleName, getCurrentModuleInstanceNum().get(deviceId).get(moduleName)+1);
                    TimeKeeper.getInstance().getDeviceOccupationTime().get(deviceId).add((double) System.currentTimeMillis());
                }

                currentModuleMap.get(deviceId).add(moduleName);
            }
/*
        for(FogDevice fogDevice :getFogDevices())
        {
            if(currentModuleMap.get(fogDevice.getId()).isEmpty())
                currentModuleMap.get(fogDevice.getId()).add("connector");
        }

*/

    }
    /**
     *
     * @param nameToMips
     * @return deviceId la clé correspondant à la plus petit valeur
     */

    public int getMinKey(Map<Integer,Double> nameToMips)
    {
        int deviceId = 0;
        double bestDevice =Collections.min(nameToMips.values());
        for(int  key :nameToMips.keySet())
            if(nameToMips.get(key).equals(bestDevice))
                deviceId=key;

      return deviceId;
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



    public void setModuleMapping(ModuleMapping moduleMapping) {
        this.moduleMapping = moduleMapping;
    }
    public ModuleMapping getModuleMapping() { return moduleMapping;    }

    public Map<String, Double> getModuleRank() {
        return moduleRank;
    }

    public void setModuleRank(Map<String, Double> moduleRank) {
        this.moduleRank = moduleRank;
    }

    public static void setSensors(List<Sensor> sensors) {
        ModulePlacementHeft.sensors = sensors;
    }

    public static void setActuators(List<Actuator> actuators) {
        ModulePlacementHeft.actuators = actuators;
    }

    public static List<Sensor> getSensors() {
        return sensors;
    }

    public static List<Actuator> getActuators() {
        return actuators;
    }


    public Map<Integer, Map<String, Double>> getCurrentModuleLoadMap() {
        return currentModuleLoadMap;
    }

    public Map<Integer, Map<String, Integer>> getCurrentModuleInstanceNum() {
        return currentModuleInstanceNum;
    }


    public void setCurrentModuleLoadMap(Map<Integer, Map<String, Double>> currentModuleLoadMap) {
        this.currentModuleLoadMap = currentModuleLoadMap;
    }

    public void setCurrentModuleInstanceNum(Map<Integer, Map<String, Integer>> currentModuleInstanceNum) {
        this.currentModuleInstanceNum = currentModuleInstanceNum;
    }


    public Map<Integer, List<String>> getCurrentModuleMap() {
        return currentModuleMap;
    }

    public void setCurrentModuleMap(Map<Integer, List<String>> currentModuleMap) {
        this.currentModuleMap = currentModuleMap;
    }


}
