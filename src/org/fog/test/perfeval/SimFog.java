package org.fog.test.perfeval;

import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Pe;
import org.cloudbus.cloudsim.Storage;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.power.PowerHost;
import org.cloudbus.cloudsim.power.models.PowerModel;
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;
import org.cloudbus.cloudsim.sdn.overbooking.BwProvisionerOverbooking;
import org.cloudbus.cloudsim.sdn.overbooking.PeProvisionerOverbooking;
import org.fog.application.AppEdge;
import org.fog.application.AppModule;
import org.fog.application.Application;
import org.fog.entities.*;
import org.fog.placement.*;
import org.fog.policy.AppModuleAllocationPolicy;
import org.fog.scheduler.StreamOperatorScheduler;
import org.fog.utils.FogLinearPowerModel;
import org.fog.utils.FogUtils;
import org.fog.utils.TimeKeeper;
import org.fog.utils.distribution.DeterministicDistribution;

import java.util.*;

public class SimFog {

    public static List<Application> applications=new ArrayList<Application>();
    public static  List<FogDevice> fogdevices=new ArrayList<FogDevice>() ;
    public static List<Sensor> sensors=new ArrayList<Sensor>();
    public static  List<Actuator> actuators= new ArrayList<Actuator>();
    static int  nombreGateway = 3;
    static int  nombreMobile = 2;
    public static int TRANSMISSION=5;


    public static void main(String[] args){
        try
        {
            Log.disable();
            int num_user = 1; // number of cloud users
            Calendar calendar = Calendar.getInstance();
            boolean trace_flag = false; // mean trace events
            CloudSim.init(num_user, calendar, trace_flag);
            String appId = "Mygame"; // identifier of the application

            FogBroker broker = new FogBroker("broker");

// creation du graphe des applications avec l'ensemble des modules et des arcs

            applications.add((new ApplicationMasterSlave(appId,broker.getId())).getApplication());

// creation des devices
            creationFogDevice(broker.getId(),appId);
            //new ModulePlacementHeft(fogdevices,sensors,actuators,applications.get(0));


            Controller controller = new Controller("master-controller", fogdevices, sensors,
                    actuators);


            controller.submitApplication(applications.get(0), 0,new ModulePlacementHeft(fogdevices, sensors, actuators,applications.get(0)));



            TimeKeeper.getInstance().setSimulationStartTime(Calendar.getInstance().getTimeInMillis());

            CloudSim.startSimulation();

            CloudSim.stopSimulation();

            Log.printLine("VRGame finished!");


        }catch (Exception e) {
            e.printStackTrace();
            Log.printLine("Unwanted errors happen");
        }



    }
    /* creation l'architecture globale avec le cloud,proxy,gateway,mobiles
    *@param userId
    *@param appId
    *return
     */

public  static void creationFogDevice(int userId,String appId){
    FogDevice cloud =createFogDevice("cloud",1500,10,1250,1000,0,0.5,50,40);
    cloud.setParentId(1);
    fogdevices.add(cloud);
    FogDevice proxy =createFogDevice("proxy-server",1200,8,1500,1250,1,0.5,35,45);
    proxy.setParentId(cloud.getId());
    fogdevices.add(proxy);
for (int i=0;i<nombreGateway;i++)
    addGateway(i,userId,appId,cloud.getId());
}


    public  static void addGateway(int id, int userId, String appId,int parentId){
        FogDevice gateway =createFogDevice("d-"+id,1000,5,1600,1000,2,0.5,45,25);
        gateway.setParentId(parentId);
        fogdevices.add(gateway);
        for (int i=0;i<nombreMobile;i++){
            String IdName = id+"-"+i;
            addMoblie(IdName, userId, appId, gateway.getId());
        }



    }


   public  static void  addMoblie(String id,int userId,String appId,int parentId){
        FogDevice mobile=createFogDevice("m-"+id,500,4,1200,1400,3,0.5,35,30);
        mobile.setParentId(parentId);
        fogdevices.add(mobile);
        Sensor sensor=new Sensor("s-"+id,"Sensor",userId,appId, new DeterministicDistribution(TRANSMISSION));
        sensor.setGatewayDeviceId(mobile.getId());
        sensors.add(sensor);
        Actuator actuator =new Actuator("a-"+id,userId,appId,"Actuator");
        actuator.setGatewayDeviceId(mobile.getId());
        actuators.add(actuator);
       sensor.setGatewayDeviceId(mobile.getId());
       sensor.setLatency(6.0);  // latency of connection between EEG sensors and the parent Smartphone is 6 ms
       actuator.setGatewayDeviceId(mobile.getId());
       actuator.setLatency(1.0);  // latency of connection between Display actuator and the parent Smartphone is 1 ms
    }


    private static FogDevice createFogDevice(String nodeName, long mips,
                                              int ram, long upBw, long downBw, int level, double ratePerMips, double busyPower, double idlePower) {

        List<Pe> peList = new ArrayList<Pe>();

        // 3. Create PEs and add these into a list.
        peList.add(new Pe(0, new PeProvisionerOverbooking(mips))); // need to store Pe id and MIPS Rating

        int hostId = FogUtils.generateEntityId();
        long storage = 1000000; // host storage
        int bw = 10000;

        PowerHost host = new PowerHost(
                hostId,
                new RamProvisionerSimple(ram),
                new BwProvisionerOverbooking(bw),
                storage,
                peList,
                new StreamOperatorScheduler(peList),
                new FogLinearPowerModel(busyPower, idlePower)
        );

        List<Host> hostList = new ArrayList<Host>();
        hostList.add(host);

        String arch = "x86"; // system architecture
        String os = "Linux"; // operating system
        String vmm = "Xen";
        double time_zone = 10.0; // time zone this resource located
        double cost = 3.0; // the cost of using processing in this resource
        double costPerMem = 0.05; // the cost of using memory in this resource
        double costPerStorage = 0.001; // the cost of using storage in this
        // resource
        double costPerBw = 0.0; // the cost of using bw in this resource
        LinkedList<Storage> storageList = new LinkedList<Storage>(); // we are not adding SAN
        // devices by now

        FogDeviceCharacteristics characteristics = new FogDeviceCharacteristics(
                arch, os, vmm, host, time_zone, cost, costPerMem,
                costPerStorage, costPerBw);

        FogDevice fogdevice = null;
        try {
            fogdevice = new FogDevice(nodeName, characteristics,
                    new AppModuleAllocationPolicy(hostList), storageList, 10, upBw, downBw, 0, ratePerMips);
        } catch (Exception e) {
            e.printStackTrace();
        }

        fogdevice.setLevel(level);
        return fogdevice;
    }


}
