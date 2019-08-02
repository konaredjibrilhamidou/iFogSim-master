package org.fog.placement;

import org.fog.application.AppEdge;
import org.fog.application.AppModule;
import org.fog.application.Application;
import org.fog.entities.Actuator;
import org.fog.entities.FogDevice;
import org.fog.entities.Sensor;
import org.fog.entities.Tuple;

import java.util.*;

public class ModulePlacementCpop  extends ModulePlacement{

    protected ModuleMapping moduleMapping;
    public static List<Sensor> sensors;
    public static List<Actuator>  actuators;
    public Map<String, Double> moduleRankUpward;
    public Map<String, Double> moduleRankDownward;
    public Map<String,Double> priority;



    List<String> criticalPath ;
    List<String> noCriticalPath;
    /**
     * Stores the current mapping of application modules to fog devices
     */

    protected Map< Integer,String> currentModuleMap= new HashMap<Integer,String>();
    protected Map<Integer, Map<String, Double>> currentModuleLoadMap;
    protected Map<Integer, Map<String, Integer>> currentModuleInstanceNum;


    boolean TREEPASSAGE=false;
    /**
     * variable qui representre la presence d'un successeur pour un module
     */


    public ModulePlacementCpop(List<FogDevice> fogdevices, List<Sensor> sensors, List<Actuator> actuators, Application applications ) {
        this.setFogDevices(fogdevices);
        this.setApplication(applications);
        this.setActuators(actuators);
        this.setSensors(sensors);
        this.setModuleToDeviceMap(new HashMap<String, List<Integer>>());
        this.setDeviceToModuleMap(new HashMap<Integer, List<AppModule>>());
        this.setNoCriticalPath(new ArrayList<String>());
        this.setCriticalPath(new ArrayList<String>());
        setCurrentModuleLoadMap(new HashMap<Integer, Map<String, Double>>());
        setCurrentModuleInstanceNum(new HashMap<Integer, Map<String, Integer>>());
        setCurrentModuleMap(new HashMap<>());

        for(FogDevice dev : getFogDevices()){
            getCurrentModuleLoadMap().put(dev.getId(), new HashMap<String, Double>());
            getCurrentModuleMap().put(dev.getId(),null);
            getCurrentModuleInstanceNum().put(dev.getId(), new HashMap<String, Integer>());
        }

        mapModules();
    }



    @Override
    protected void mapModules() {
        listScheduling();
        /**
         * ajout des sensors et des actuators aux modules à placer
         */
        getHeftPlacement();

        for( int deviceId   : getCurrentModuleMap().keySet()){
            if(getCurrentModuleMap().get(deviceId) != null){
                String name  = getCurrentModuleMap().get(deviceId);
                FogDevice device =getDeviceById(deviceId);
                AppModule module = getApplication().getModuleByName(name);
                createModuleInstanceOnDevice(module, device);
            }

        }

    }

    public void getHeftPlacement()

    {
        orderModule();
        List <String> orderModule = criticalPath;
        List <String> _orderModule = noCriticalPath;

        if(orderModule.size()>0)
        {
            Map<Integer, Double>  nameToMips = new HashMap<Integer,Double>();
            double mipsRate =0;
            int criticalPathSumMips = 0;
                for(String name : criticalPath)
                {
                    AppModule _module = getApplication().getModuleByName(name);
                     criticalPathSumMips = (int) (criticalPathSumMips + _module.getMips());
                }

             for(FogDevice fogDevice : getFogDevices())
            {
                mipsRate= criticalPathSumMips/fogDevice.getVmAllocationPolicy().getHostList().get(0).getPeList().get(0).getPeProvisioner().getMips();

                nameToMips.put(fogDevice.getId(),mipsRate);
            }

            int deviceId=0;
            deviceId = getMinKey(nameToMips);
            /**
             * effectue le placement du module sur un device s'il est libre sinon il  fait le placement sur un autre device disponible
             */
            for(String moduleName :criticalPath)
            {
                FogDevice device = getDeviceById(deviceId);
                System.out.println("Placement of operator "+moduleName+ " on device "+device.getName()+ " successful.");

            }

            currentModuleMap.put(deviceId,criticalPath.get(0));
        }

        while(_orderModule.size()>0)
        {
            Map<Integer, Double>  nameToMips = new HashMap<Integer,Double>();
            double mipsRate =0;
            String  moduleName = noCriticalPath.get(0);

            /**
             * Calcul la proportion des mips pour chaque device.**Choix du device avec la proportion minimale
             */
            AppModule _module = getApplication().getModuleByName(moduleName);
            for(FogDevice fogDevice : getFogDevices())
            {
                mipsRate= _module.getMips()/fogDevice.getVmAllocationPolicy().getHostList().get(0).getPeList().get(0).getPeProvisioner().getMips();
                nameToMips.put(fogDevice.getId(),mipsRate);
            }
            int deviceId=0;

            deviceId = getMinKey(nameToMips);
            /**
             * effectue le placement du module sur un device s'il est libre sinon il  fait le placement sur un autre device disponible
             */

           if(!currentModuleMap.containsKey(deviceId))
            {
                noCriticalPath.remove(moduleName);
                FogDevice device = getDeviceById(deviceId);
                System.out.println("Placement of operator "+moduleName+ " on device "+device.getName()+ " successful.");
            }
            else
            {
                for(Integer module : currentModuleMap.keySet()) {
                    if (currentModuleMap.get(module) !=null ) {
                        nameToMips.remove(module);
                    }
                }

                deviceId =getMinKey(nameToMips);
                FogDevice device = getDeviceById(deviceId);
                System.out.println("Placement of operator "+moduleName+ " on device "+ device.getName()+ " successful.");
                noCriticalPath.remove(moduleName);

            }
            currentModuleMap.put(deviceId,moduleName);
        }

    }



    public void listScheduling() {
        Map<String, Double> rankUpward= new HashMap<String, Double>();
        Map<String, Double> rankDownward= new HashMap<String, Double>();
        Map<String, Double> _priority= new HashMap<String, Double>();

        for(AppModule module : getApplication().getModules())

        {
            rankDownward.put(module.getName(),rankDownward(module));
            rankUpward.put(module.getName(),rankUpward(module));
            _priority.put(module.getName(),rankUpward(module)+rankDownward(module));

        }
        setModuleRankUpward(rankUpward);
        setModuleRankDownward(rankDownward);
        setPriority(_priority);
    }

    /**
     *  calcul le rank pour module de l'application
     * @param appModule
     * @return list
     */

    public double rankUpward( AppModule appModule) {
        Map<String, Double> currentRank = new HashMap<String, Double>();
        double rang ;
        for (AppEdge edge : getApplication().getEdges()) {
            if(edge.getDestination()!="DISPLAY")
            if (edge.getSource().contentEquals(appModule.getName()) & (Tuple.UP == edge.getDirection())) {
                rang = edge.getTupleCpuLength() + getApplication().getModuleByName(edge.getSource()).getMips();
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
            return appModule.getMips() + (Double) entry.getValue() + rankUpward(getApplication().getModuleByName((String) entry.getKey()));
        }
    }



    public double rankDownward(AppModule appModule) {

        Map<String, Double> currentRankDownward= new HashMap<String, Double>();
        double rang=0 ;
        for (AppEdge edge : getApplication().getEdges()) {
            if (edge.getSource()!="EEG")
                try {
                if (edge.getDestination().equalsIgnoreCase(appModule.getName()) & (Tuple.UP == edge.getDirection())) {
                    rang = edge.getTupleCpuLength() + getApplication().getModuleByName(edge.getDestination()).getMips();
                        currentRankDownward.put(edge.getSource(),rang);
                    TREEPASSAGE=true;
            }
                } catch(NullPointerException e)
                {
                    continue;
                }
        }

        if (currentRankDownward.isEmpty() ) {
            if(TREEPASSAGE ) {
                TREEPASSAGE = false;
                return 0;
            }
            else
                return 0;//appModule.getMips();
        }
        else
        {   Map<String,Double> sortedMapDownward =new TreeMap<String, Double>(currentRankDownward);
            Set set = sortedMapDownward.entrySet();
            Iterator iterator=set.iterator();
            Map.Entry entry = (Map.Entry) iterator.next();
            return appModule.getMips() + (Double) entry.getValue() + rankDownward(getApplication().getModuleByName((String) entry.getKey()));
        }

    }


    public int getMinKey(Map<Integer,Double> nameToMips)
    {
        int deviceId = 0;
        double bestDevice = Collections.min(nameToMips.values());
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



    /**
     * donne une liste ordonnée suivant les valeurs des ranks
     * @return list
     */
    public  void  orderModule(){
        Map<String,Double> map = new HashMap<>(priority);
        Double value =map.get("client");
      for (AppModule module :getApplication().getModules())
        {
            if( map.get(module.getName()).equals(value))
                 criticalPath.add(module.getName());
            else
               noCriticalPath.add(module.getName());

        }
    }




    public void setModuleMapping(ModuleMapping moduleMapping) {
        this.moduleMapping = moduleMapping;
    }

    public Map<String, Double> getModuleRankUpward() {
        return moduleRankUpward;
    }

    public void setModuleRankUpward(Map<String, Double> moduleRank) {
        this.moduleRankUpward = moduleRank;
    }

    public Map<String, Double> getModuleRankDownward() {
        return moduleRankDownward;
    }

    public void setModuleRankDownward(Map<String, Double> moduleRankDownward) {
        this.moduleRankDownward = moduleRankDownward;
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
    public Map<Integer,String> getCurrentModuleMap() {
        return currentModuleMap;
    }

    public Map<Integer, Map<String, Double>> getCurrentModuleLoadMap() {
        return currentModuleLoadMap;
    }

    public Map<Integer, Map<String, Integer>> getCurrentModuleInstanceNum() {
        return currentModuleInstanceNum;
    }
    public void setCurrentModuleMap(Map<Integer, String> currentModuleMap) {
        this.currentModuleMap = currentModuleMap;
    }

    public void setCurrentModuleLoadMap(Map<Integer, Map<String, Double>> currentModuleLoadMap) {
        this.currentModuleLoadMap = currentModuleLoadMap;
    }

    public void setCurrentModuleInstanceNum(Map<Integer, Map<String, Integer>> currentModuleInstanceNum) {
        this.currentModuleInstanceNum = currentModuleInstanceNum;
    }
    public Map<String, Double> getPriority() {
        return priority;
    }

    public void setPriority(Map<String, Double> priority) {
        this.priority = priority;
    }
    public void setCriticalPath(List<String> criticalPath) {
        this.criticalPath = criticalPath;
    }

    public void setNoCriticalPath(List<String> noCriticalPath) {
        this.noCriticalPath = noCriticalPath;
    }
}



