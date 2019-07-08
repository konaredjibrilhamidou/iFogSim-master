package org.fog.placement;

import org.cloudbus.cloudsim.core.CloudSim;
import org.fog.application.AppEdge;
import org.fog.application.AppModule;
import org.fog.application.Application;
import org.fog.entities.Actuator;
import org.fog.entities.FogDevice;
import org.fog.entities.Sensor;
import org.fog.entities.Tuple;

import java.util.*;

import static org.fog.test.perfeval.SimFog.fogdevices;

public class ModulePlacementHeft  extends ModulePlacement{


    public static List<Sensor> sensors;
    public static List<Actuator>  actuators;
    public Map<String, Double> moduleRank;
    /**
     * Stores the current mapping of application modules to fog devices
     */

    protected Map<String,String> currentModuleMap= new HashMap<>();
    protected Map<Integer, Map<String, Double>> currentModuleLoadMap;
    protected Map<Integer, Map<String, Integer>> currentModuleInstanceNum;


     boolean TREEPASSAGE=false;
     boolean LEAFPASSAGE=false;

    /**
     * variable qui representre la presence d'un successeur pour un module
     */


    public ModulePlacementHeft(List<FogDevice> fogdevices, List<Sensor> sensors, List<Actuator> actuators, Application applications) {
        this.setFogDevices(fogdevices);
        this.setApplication(applications);
        setActuators(actuators);
        setSensors(sensors);
        listScheduling();
        mapModules();

    }


    @Override
    protected void mapModules() {
        /*List<String> orderModule = orderModule();
        List<AppModule> module= new ArrayList<>();
        for(String name : orderModule)
            module.add(getApplication().getModuleByName(name));

         */

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



        List<String> modulesToPlace =  getModulesToPlace(placedModules);

        while(modulesToPlace.size()>0)
        {
            Map<String, Double>  nameToMips = new HashMap<String,Double>();
            double mipsRate =0;
            String  moduleName = modulesToPlace.get(0);

/**
 * Calcul la proportion des mips pour chaque device.**Choix du device avec la proportion minimale
 */
            AppModule _module = getApplication().getModuleByName(moduleName);
            for(FogDevice fogDevice : getFogDevices())
            {
                mipsRate= (double)_module.getMips()/(fogDevice.characteristics.getMips());

                nameToMips.put(fogDevice.getName(),mipsRate);
            }

            String nameDevice;

            nameDevice = getMinKey(nameToMips);

            if(!currentModuleMap.containsValue(nameDevice))
            {
                placedModules.add(moduleName);
                modulesToPlace =  getModulesToPlace(placedModules);
                System.out.println("Placement of operator "+moduleName+ " on device "+nameDevice+ " successful.");
            }
            else
            {
                for(String module : currentModuleMap.values())
                    nameToMips.remove(module);

                nameDevice =getMinKey(nameToMips);
                System.out.println("Placement of operator "+moduleName+ " on device "+ nameDevice+ " successful.");
                placedModules.add(moduleName);
                modulesToPlace =  getModulesToPlace(placedModules);

            }

        currentModuleMap.put(moduleName,nameDevice);
            modulesToPlace.remove(moduleName);
        }



    }



    public String getMinKey(Map<String,Double> nameToMips)
    {
        String nameDevice = null;
        double bestDevice =Collections.min(nameToMips.values());
        for(String key :nameToMips.keySet())
            if(nameToMips.get(key).equals(bestDevice))
                nameDevice=key;

      return nameDevice;

    }



    public void listScheduling() {
        Map<String, Double> rank= new HashMap<String, Double>();
        for(AppModule module : getApplication().getModules())
        {

            rank.put(module.getName(),rankCompute(module));
        }
        setModuleRank(rank);
    }



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
               // if(edge.getSource().equals(moduleName) && edge.getDirection()==Tuple.DOWN && placedModules.contains(edge.getDestination()))
                  //  toBePlaced = false;
                //CHECK IF INCOMING UP EDGES ARE PLACED
                if(edge.getDestination().equals(moduleName) && edge.getDirection()==Tuple.UP && placedModules.contains(edge.getSource()))
                    toBePlaced = true;
            }
            if(toBePlaced)
                modulesToPlace.add(moduleName);
        }

        return modulesToPlace;
    }


    /*
    public Map<AppModule,FogDevice> placementOnParent(AppModule module, FogDevice fogDevice)
    {
        int deviceId=fogDevice
    }
*/
private Map<String, Integer> getAssociatedSensors(FogDevice device) {
    Map<String, Integer> endpoints = new HashMap<String, Integer>();
    for(Sensor sensor : getSensors()){
        if(sensor.getGatewayDeviceId()==device.getId()){
            if(!endpoints.containsKey(sensor.getTupleType()))
                endpoints.put(sensor.getTupleType(), 0);
            endpoints.put(sensor.getTupleType(), endpoints.get(sensor.getTupleType())+1);
        }
    }
    return endpoints;
}

    private Map<String, Integer> getAssociatedActuators(FogDevice device) {
        Map<String, Integer> endpoints = new HashMap<String, Integer>();
        for(Actuator actuator : getActuators()){
            if(actuator.getGatewayDeviceId()==device.getId()){
                if(!endpoints.containsKey(actuator.getActuatorType()))
                    endpoints.put(actuator.getActuatorType(), 0);
                endpoints.put(actuator.getActuatorType(), endpoints.get(actuator.getActuatorType())+1);
            }
        }
        return endpoints;
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
}
