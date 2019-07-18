package org.fog.placement;

import org.fog.application.AppEdge;
import org.fog.application.AppModule;
import org.fog.application.Application;
import org.fog.entities.Actuator;
import org.fog.entities.FogDevice;
import org.fog.entities.Sensor;
import org.fog.entities.Tuple;

import java.util.*;

public class ModulePlacementHeft  extends ModulePlacement{



    protected ModuleMapping moduleMapping;
    public static List<Sensor> sensors;
    public static List<Actuator>  actuators;
    public Map<String, Double> moduleRank;
    /**
     * Stores the current mapping of application modules to fog devices
     */

    protected Map< Integer,String> currentModuleMap= new HashMap<Integer,String>();
    protected Map<Integer, Map<String, Double>> currentModuleLoadMap;
    protected Map<Integer, Map<String, Integer>> currentModuleInstanceNum;


     boolean TREEPASSAGE=false;
     boolean LEAFPASSAGE=false;

    /**
     * variable qui representre la presence d'un successeur pour un module
     */


    public ModulePlacementHeft(List<FogDevice> fogdevices, List<Sensor> sensors, List<Actuator> actuators, Application applications ) {
        this.setFogDevices(fogdevices);
        this.setApplication(applications);
       this.setActuators(actuators);
        this.setSensors(sensors);
        this.setModuleToDeviceMap(new HashMap<String, List<Integer>>());
        this.setDeviceToModuleMap(new HashMap<Integer, List<AppModule>>());
        setCurrentModuleLoadMap(new HashMap<Integer, Map<String, Double>>());
        setCurrentModuleInstanceNum(new HashMap<Integer, Map<String, Integer>>());
        setCurrentModuleMap(new HashMap<>());


        for(FogDevice dev : getFogDevices()){
            getCurrentModuleLoadMap().put(dev.getId(), new HashMap<String, Double>());
            getCurrentModuleMap().put(dev.getId(),null);
            getCurrentModuleInstanceNum().put(dev.getId(), new HashMap<String, Integer>());
        }
        long starTime = System.currentTimeMillis();

        listScheduling();
        mapModules();

        long endTime = System.currentTimeMillis();
        System.out.println("Algorithme Time-----> "+ (endTime-starTime));
    }




    @Override
    protected void mapModules() {
        /**
         * ajout des sensors et des actuators aux modules à placer
         */
        List<String> placedModules = new ArrayList<String>();

        for(FogDevice fogDevice :getFogDevices())
        {
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

        for( int deviceId   : getCurrentModuleMap().keySet()){
            if(getCurrentModuleMap().get(deviceId) != null){
                String name  = getCurrentModuleMap().get(deviceId);
                FogDevice device =getDeviceById(deviceId);
                AppModule module = getApplication().getModuleByName(name);
                createModuleInstanceOnDevice(module, device);
            }

        }

    }



    public void getHeftPlacement(List<String> placedModules)

    {
        List<String> modulesToPlace =  getModulesToPlace(placedModules);
        List<String> orderModule = orderModule();
        while(modulesToPlace.size()>0)
        {
            Map<Integer, Double>  nameToMips = new HashMap<Integer,Double>();
            double mipsRate =0;
            String  moduleName = modulesToPlace.get(0);
            String nameOrder= orderModule.get(0);

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
                    placedModules.add(moduleName);
                    modulesToPlace =  getModulesToPlace(placedModules);
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
                    placedModules.add(moduleName);
                    modulesToPlace =  getModulesToPlace(placedModules);

                }

                currentModuleMap.put(deviceId,moduleName);
                modulesToPlace.remove(moduleName);
                orderModule.remove(nameOrder);
            }

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
        Map<String,Double> sortedMap =new TreeMap<String,Double>(getModuleRank());
        Map<String,Double> rang =new HashMap<String, Double>();
        List<String> list = new ArrayList<String>();
        Set set=sortedMap.entrySet();
        Iterator iterator=set.iterator();

        while(iterator.hasNext())
        {
            Map.Entry entry= (Map.Entry) iterator.next();
            list.add((String) entry.getKey());
        }

        return list;
    }




    private List<String> getModulesToPlace(List<String> placedModules){
        Application app = getApplication();
        List<String> modulesToPlace_1 = new ArrayList<String>();
        List<String> modulesToPlace = new ArrayList<String>();
        for(AppModule module : app.getModules()){
            if(!placedModules.contains(module.getName()))
                modulesToPlace_1.add(module.getName());
        }
        /*
         * Filtering based on whether modules (to be placed) lower in physical topology are already placed
         */
        for(String moduleName : modulesToPlace_1){
            boolean toBePlaced = false;

            for(AppEdge edge : app.getEdges()){
                //CHECK IF OUTGOING DOWN EDGES ARE PLACED
               if(edge.getSource().equals(moduleName) && edge.getDirection()==Tuple.DOWN && placedModules.contains(edge.getDestination()))
                    toBePlaced = true;
                //CHECK IF INCOMING UP EDGES ARE PLACED
                if(edge.getDestination().equals(moduleName) && edge.getDirection()==Tuple.UP && placedModules.contains(edge.getSource()))
                    toBePlaced = true;
            }
            if(toBePlaced)
                modulesToPlace.add(moduleName);
        }
        return modulesToPlace;
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
}
