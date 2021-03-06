package org.fog.test.perfeval;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.DoubleStream;

import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Pe;
import org.cloudbus.cloudsim.Storage;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.power.PowerHost;
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;
import org.cloudbus.cloudsim.sdn.overbooking.BwProvisionerOverbooking;
import org.cloudbus.cloudsim.sdn.overbooking.PeProvisionerOverbooking;
import org.fog.application.AppEdge;
import org.fog.application.AppLoop;
import org.fog.application.Application;
import org.fog.application.selectivity.FractionalSelectivity;
import org.fog.entities.Actuator;
import org.fog.entities.FogBroker;
import org.fog.entities.FogDevice;
import org.fog.entities.FogDeviceCharacteristics;
import org.fog.entities.Sensor;
import org.fog.entities.Tuple;
import org.fog.placement.*;
import org.fog.policy.AppModuleAllocationPolicy;
import org.fog.scheduler.StreamOperatorScheduler;
import org.fog.utils.FogLinearPowerModel;
import org.fog.utils.FogUtils;
import org.fog.utils.TimeKeeper;
import org.fog.utils.distribution.DeterministicDistribution;

/**
 * Simulation setup for case study 1 - EEG Beam Tractor Game
 * @author Harshit Gupta
 *
 */
public class SimFog {
    static List<FogDevice> fogDevices = new ArrayList<FogDevice>();
    static List<Sensor> sensors = new ArrayList<Sensor>();
    static List<Actuator> actuators = new ArrayList<Actuator>();

    static boolean CLOUD = false;

    static int numOfDepts =3;//           4 4 4 4
    static int numOfMobilesPerDept =10;//           12  13 14 15
    static double EEG_TRANSMISSION_TIME = 5.1;
    //static double EEG_TRANSMISSION_TIME = 10;


    public static void main(String[] args) {

        Log.printLine("Starting VRGame...");
        try {
            Log.disable();

            int num_user = 1; // number of cloud users
            Calendar calendar = Calendar.getInstance();
            boolean trace_flag = false; // mean trace events

            CloudSim.init(num_user, calendar, trace_flag);

            String appId = "vr_game"; // identifier of the application

            FogBroker broker = new FogBroker("broker");
            Application application = ApplicationGraph.createApplication0(appId, broker.getId());
            application.setUserId(broker.getId());
            createFogDevices(broker.getId(), appId);

            ModuleMapping moduleMapping = ModuleMapping.createModuleMapping(); // initializing a module mapping

            Controller controller = new Controller("master-controller", fogDevices, sensors,
                    actuators);

            controller.submitApplication(application, 0,
                    new ModulePlacementHets(fogDevices, sensors, actuators, application ,moduleMapping));

            /**
             * djibril
             */

            TimeKeeper.getInstance().getExecutionTimeModule().put(appId,new ArrayList<Double>());

            TimeKeeper.getInstance().setSimulationStartTime(Calendar.getInstance().getTimeInMillis());

            CloudSim.startSimulation();

           CloudSim.stopSimulation();


            Log.printLine("VRGame finished!");


        } catch (Exception e) {
            e.printStackTrace();
            Log.printLine("Unwanted errors happen");

        }

    }

    /**
     * Creates the fog devices in the physical topology of the simulation.
     * @param userId
     * @param appId
     */
    private static void createFogDevices(int userId, String appId) {
        FogDevice cloud = createFogDevice("cloud", 4800, 4000, 100, 10000, 0, 0.01, 16*103, 16*83.25); // creates the fog device Cloud at the apex of the hierarchy with level=0
        cloud.setParentId(-1);
        FogDevice proxy = createFogDevice("proxy-server", 4000, 4000, 10000, 10000, 1, 0.0, 107.339, 83.4333); // creates the fog device Proxy Server (level=1)
        proxy.setParentId(cloud.getId()); // setting Cloud as parent of the Proxy Server
        proxy.setUplinkLatency(100); // latency of connection from Proxy Server to the Cloud is 100 ms
        fogDevices.add(cloud);
        fogDevices.add(proxy);

        for(int i=0;i<numOfDepts;i++){
            addGw(i+"", userId, appId, proxy.getId()); // adding a fog device for every Gateway in physical topology. The parent of each gateway is the Proxy Server
        }

    }

    private static FogDevice addGw(String id, int userId, String appId, int parentId){

       // long mips= randomSeed(2800,3000);

        FogDevice dept = createFogDevice("d-"+id, 3000/*mips*/, 4000, 10000, 10000, 1, 0.0, 107.339, 83.4333);
        fogDevices.add(dept);
        dept.setParentId(parentId);
        dept.setUplinkLatency(4); // latency of connection between gateways and proxy server is 4 ms
        for(int i=0;i<numOfMobilesPerDept;i++){
            String mobileId = id+"-"+i;
            FogDevice mobile = addMobile(mobileId, userId, appId, dept.getId()); // adding mobiles to the physical topology. Smartphones have been modeled as fog devices as well.
            mobile.setUplinkLatency(2); // latency of connection between the smartphone and proxy server is 2 ms
            fogDevices.add(mobile);
        }
        return dept;
    }

    private static FogDevice addMobile(String id, int userId, String appId, int parentId){
       // long mips= randomSeed(2500,2800);
        FogDevice mobile = createFogDevice("m-"+id, 2800 /*mips*/, 1000, 10000, 270, 3, 0, 87.53, 82.44);
        mobile.setParentId(parentId);
        Sensor eegSensor = new Sensor("s-"+id, "EEG", userId, appId, new DeterministicDistribution(EEG_TRANSMISSION_TIME)); // inter-transmission time of EEG sensor follows a deterministic distribution
        sensors.add(eegSensor);
        Actuator display = new Actuator("a-"+id, userId, appId, "DISPLAY");
        actuators.add(display);
        eegSensor.setGatewayDeviceId(mobile.getId());
        eegSensor.setLatency(6.0);  // latency of connection between EEG sensors and the parent Smartphone is 6 ms
        display.setGatewayDeviceId(mobile.getId());
        display.setLatency(1.0);  // latency of connection between Display actuator and the parent Smartphone is 1 ms
        return mobile;
    }


    /**
     * Creates a vanilla fog device
     * @param nodeName name of the device to be used in simulation
     * @param mips MIPS
     * @param ram RAM
     * @param upBw uplink bandwidth
     * @param downBw downlink bandwidth
     * @param level hierarchy level of the device
     * @param ratePerMips cost rate per MIPS used
     * @param busyPower
     * @param idlePower
     * @return
     */
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

    public static long randomSeed (int min, int max) {
        return min + (int) ( Math.random() * (max - min) );
    }

}