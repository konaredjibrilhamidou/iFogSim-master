package org.fog.test.perfeval;

import org.fog.application.AppEdge;
import org.fog.application.AppModule;
import org.fog.application.AppLoop;
import org.fog.application.Application;
import org.fog.application.selectivity.FractionalSelectivity;
import org.fog.entities.FogDevice;
import org.fog.entities.Tuple;

import java.util.ArrayList;
import java.util.List;

public class ApplicationGraph {

    static double EEG_TRANSMISSION_TIME = 5.1;

    public static Application createApplication0(String appId, int userId) {

        Application application = Application.createApplication(appId, userId); // creates an empty application model (empty directed graph)

        /*
         * Adding modules (vertices) to the application model (directed graph)
         */
        application.addAppModule("module1", 10); // adding module Client to the application model
        application.addAppModule("module2", 10); // adding module Concentration Calculator to the application model
        application.addAppModule("module3", 10); // adding module Connector to the application model
        application.addAppModule("module4", 10); // adding module Connector to the application model

        /*
         * Connecting the application modules (vertices) in the application model (directed graph) with edges
         */
        if (EEG_TRANSMISSION_TIME == 10)
            application.addAppEdge("EEG", "client", 2000, 500, "EEG", Tuple.UP, AppEdge.SENSOR); // adding edge from EEG (sensor) to Client module carrying tuples of type EEG
        else

            application.addAppEdge("EEG", "module1", 3500, 250, "EEG", Tuple.UP, AppEdge.SENSOR);
        application.addAppEdge("module1", "module2", 3000, 80, "_SENSOR", Tuple.UP, AppEdge.MODULE); // adding edge from Client to Concentration Calculator module carrying tuples of type _SENSOR
        application.addAppEdge("module1", "module3", 100, 500, 150, "PLAYER_GAME_STATE", Tuple.UP, AppEdge.MODULE); // adding periodic edge (period=1000ms) from Concentration Calculator to Connector module carrying tuples of type PLAYER_GAME_STATE
        application.addAppEdge("module2", "module3", 14, 500, "CONCENTRATION", Tuple.UP, AppEdge.MODULE);  // adding edge from Concentration Calculator to Client module carrying tuples of type CONCENTRATION
        application.addAppEdge("module2", "module4", 100, 28, 1000, "GLOBAL_GAME_STATE", Tuple.UP, AppEdge.MODULE); // adding periodic edge (period=1000ms) from Connector to Client module carrying tuples of type GLOBAL_GAME_STATE
        application.addAppEdge("module3", "module4", 100, 28, 1000, "GLOBAL_GAME_STATE", Tuple.UP, AppEdge.MODULE); // adding periodic edge (period=1000ms) from Connector to Client module carrying tuples of type GLOBAL_GAME_STATE
        application.addAppEdge("module1", "DISPLAY", 100, 600, "GLOBAL_STATE_UPDATE", Tuple.DOWN, AppEdge.ACTUATOR);  // ad

       final AppLoop loop1 = new AppLoop(new ArrayList<String>() {{
            add("EEG");
            add("module1");
            add("module2");
            add("module3");
            add("module3");
            add("module4");
            add("EEG");

        }});
        List<AppLoop> loops = new ArrayList<AppLoop>() {{
            add(loop1);
        }};
        application.setLoops(loops);

        return application;
    }

    public static Application createApplication01(String appId, int userId) {

        Application application = Application.createApplication(appId, userId); // creates an empty application model (empty directed graph)
        /*
         * Adding modules (vertices) to the application model (directed graph)
         */
        application.addAppModule("module1", 10); // adding module Client to the application model
        application.addAppModule("module2", 10); // adding module Concentration Calculator to the application model
        application.addAppModule("module3", 10); // adding module Connector to the application model
        application.addAppModule("module4", 10); // adding module Connector to the application model
        application.addAppModule("module5", 10); // adding module Connector to the application model
        application.addAppModule("module6", 10); // adding module Connector to the application model
        application.addAppModule("module7", 10); // adding module Connector to the application model
        application.addAppModule("module8", 10); // adding module Connector to the application model
        application.addAppModule("module9", 10); // adding module Connector to the application model
        application.addAppModule("module10", 10); // adding module Connector to the application model
        application.addAppModule("module11", 10); // adding module Connector to the application model

        /*
         * Connecting the application modules (vertices) in the application model (directed graph) with edges
         */
        if (EEG_TRANSMISSION_TIME == 10)
            application.addAppEdge("EEG", "client", 2000, 500, "EEG", Tuple.UP, AppEdge.SENSOR); // adding edge from EEG (sensor) to Client module carrying tuples of type EEG
        else
            application.addAppEdge("EEG", "module1", 3500, 250, "EEG", Tuple.UP, AppEdge.SENSOR);
        application.addAppEdge("module1", "module2", 3000, 80, "TYPE_1_2", Tuple.UP, AppEdge.MODULE); // adding edge from Client to Concentration Calculator module carrying tuples of type _SENSOR
        application.addAppEdge("module1", "module3", 100, 500, 150, "TYPE_1_3", Tuple.UP, AppEdge.MODULE); // adding periodic edge (period=1000ms) from Concentration Calculator to Connector module carrying tuples of type PLAYER_GAME_STATE
        application.addAppEdge("module1", "module4", 14, 500, "TYPE_1_4", Tuple.UP, AppEdge.MODULE);  // adding edge from Concentration Calculator to Client module carrying tuples of type CONCENTRATION
        application.addAppEdge("module1", "module5", 100, 28, 1000, "TYPE_1_5", Tuple.UP, AppEdge.MODULE); // adding periodic edge (period=1000ms) from Connector to Client module carrying tuples of type GLOBAL_GAME_STATE
        application.addAppEdge("module1", "module6", 100, 28, 1000, "TYPE_1_6", Tuple.UP, AppEdge.MODULE); // adding periodic edge (period=1000ms) from Connector to Client module carrying tuples of type GLOBAL_GAME_STATE
        application.addAppEdge("module2", "module7", 100, 600, "TYPE_2_6", Tuple.UP, AppEdge.MODULE);  // ad
        application.addAppEdge("module6", "module7", 100, 600, "TYPE_6_7", Tuple.UP, AppEdge.MODULE);  // ad
        application.addAppEdge("module3", "module8", 100, 600, "TYPE_3_8", Tuple.UP, AppEdge.MODULE);  // ad
        application.addAppEdge("module8", "module10", 100, 600, "TYPE_8_10", Tuple.UP, AppEdge.MODULE);  // ad
        application.addAppEdge("module4", "module10", 100, 600, "TYPE_4_10", Tuple.UP, AppEdge.MODULE);  // ad
        application.addAppEdge("module5", "module9", 100, 600, "TYPE_5_9", Tuple.UP, AppEdge.MODULE);  // ad
        application.addAppEdge("module10", "module11", 100, 600, "TYPE_10_11", Tuple.UP, AppEdge.MODULE);  // ad
        application.addAppEdge("module9", "module11", 100, 600, "TYPE_9_11", Tuple.UP, AppEdge.MODULE);  // ad
        application.addAppEdge("module1", "DISPLAY", 100, 600, "GLOBAL_STATE_UPDATE", Tuple.DOWN, AppEdge.ACTUATOR);  // ad


        application.addTupleMapping("module1", "EEG", "TYPE_1_2", new FractionalSelectivity(0.9)); // 0.9 tuples of type _SENSOR are emitted by Client module per incoming tuple of type EEG
        application.addTupleMapping("module2", "TYPE_1_2", "TYPE_2_7", new FractionalSelectivity(1.0)); // 1.0 tuples of type SELF_STATE_UPDATE are emitted by Client module per incoming tuple of type CONCENTRATION
        application.addTupleMapping("module3", "TYPE_1_3", "TYPE_3_8", new FractionalSelectivity(1.0)); // 1.0 tuples of type CONCENTRATION are emitted by Concentration Calculator module per incoming tuple of type _SENSOR
        application.addTupleMapping("module4", "TYPE_1_4", "TYPE_4_10", new FractionalSelectivity(1.0)); // 1.0 tuples of type GLOBAL_STATE_UPDATE are emitted by Client module per incoming tuple of type GLOBAL_GAME_STATE
        application.addTupleMapping("module5", "TYPE_1_5", "TYPE_5_9", new FractionalSelectivity(1.0)); // 1.0 tuples of type GLOBAL_STATE_UPDATE are emitted by Client module per incoming tuple of type GLOBAL_GAME_STATE
        application.addTupleMapping("module6", "TYPE_1_6", "TYPE_6_7", new FractionalSelectivity(1.0)); // 1.0 tuples of type GLOBAL_STATE_UPDATE are emitted by Client module per incoming tuple of type GLOBAL_GAME_STATE
        application.addTupleMapping("module8", "TYPE_3_8", "TYPE_8_10", new FractionalSelectivity(1.0)); // 1.0 tuples of type GLOBAL_STATE_UPDATE are emitted by Client module per incoming tuple of type GLOBAL_GAME_STATE
        application.addTupleMapping("module9", "TYPE_5_9", "TYPE_9_11", new FractionalSelectivity(1.0)); // 1.0 tuples of type GLOBAL_STATE_UPDATE are emitted by Client module per incoming tuple of type GLOBAL_GAME_STATE
        application.addTupleMapping("module10", "TYPE_8_10", "TYPE_10_11", new FractionalSelectivity(1.0)); // 1.0 tuples of type GLOBAL_STATE_UPDATE are emitted by Client module per incoming tuple of type GLOBAL_GAME_STATE


        final AppLoop loop1 = new AppLoop(new ArrayList<String>() {{
            add("EEG"); add("module1"); add("module2"); add("module3"); add("module3");
            add("module4");add("module5");add("module6");add("module7");add("module8");
            add("module9");add("module10");add("module11"); add("EEG");
        }});
        List<AppLoop> loops = new ArrayList<AppLoop>() {{
            add(loop1);
        }};
        application.setLoops(loops);

        return application;
    }




    public static Application createApplication1(String appId, int userId){

           Application application = Application.createApplication(appId, userId); // creates an empty application model (empty directed graph)

           /*
            * Adding modules (vertices) to the application model (directed graph)
            */
           application.addAppModule("client", 10); // adding module Client to the application model
           application.addAppModule("concentration_calculator", 10); // adding module Concentration Calculator to the application model
           application.addAppModule("connector", 10); // adding module Connector to the application model

           /*
            * Connecting the application modules (vertices) in the application model (directed graph) with edges
            */
           if(EEG_TRANSMISSION_TIME==10)
               application.addAppEdge("EEG", "client", 2000, 500, "EEG", Tuple.UP, AppEdge.SENSOR); // adding edge from EEG (sensor) to Client module carrying tuples of type EEG
           else

			application.addAppEdge("EEG", "client", 3500, 250, "EEG", Tuple.UP, AppEdge.SENSOR);
           application.addAppEdge("client", "concentration_calculator", 3000, 80, "_SENSOR", Tuple.UP, AppEdge.MODULE); // adding edge from Client to Concentration Calculator module carrying tuples of type _SENSOR
           application.addAppEdge("concentration_calculator", "connector", 100, 500, 150, "PLAYER_GAME_STATE", Tuple.UP, AppEdge.MODULE); // adding periodic edge (period=1000ms) from Concentration Calculator to Connector module carrying tuples of type PLAYER_GAME_STATE
           application.addAppEdge("concentration_calculator", "client", 14, 500, "CONCENTRATION", Tuple.DOWN, AppEdge.MODULE);  // adding edge from Concentration Calculator to Client module carrying tuples of type CONCENTRATION
           application.addAppEdge("connector", "client", 100, 28, 1000, "GLOBAL_GAME_STATE", Tuple.DOWN, AppEdge.MODULE); // adding periodic edge (period=1000ms) from Connector to Client module carrying tuples of type GLOBAL_GAME_STATE
           application.addAppEdge("client", "DISPLAY", 950, 500, "SELF_STATE_UPDATE", Tuple.DOWN, AppEdge.ACTUATOR);  // adding edge from Client module to Display (actuator) carrying tuples of type SELF_STATE_UPDATE
           application.addAppEdge("client", "DISPLAY", 100, 600, "GLOBAL_STATE_UPDATE", Tuple.DOWN, AppEdge.ACTUATOR);  // adding edge from Client module to Display (actuator) carrying tuples of type GLOBAL_STATE_UPDATE

           /*
            * Defining the input-output relationships (represented by selectivity) of the application modules.
            */
           application.addTupleMapping("client", "EEG", "_SENSOR", new FractionalSelectivity(0.9)); // 0.9 tuples of type _SENSOR are emitted by Client module per incoming tuple of type EEG
           application.addTupleMapping("client", "CONCENTRATION", "SELF_STATE_UPDATE", new FractionalSelectivity(1.0)); // 1.0 tuples of type SELF_STATE_UPDATE are emitted by Client module per incoming tuple of type CONCENTRATION
           application.addTupleMapping("concentration_calculator", "_SENSOR", "CONCENTRATION", new FractionalSelectivity(1.0)); // 1.0 tuples of type CONCENTRATION are emitted by Concentration Calculator module per incoming tuple of type _SENSOR
           application.addTupleMapping("client", "GLOBAL_GAME_STATE", "GLOBAL_STATE_UPDATE", new FractionalSelectivity(1.0)); // 1.0 tuples of type GLOBAL_STATE_UPDATE are emitted by Client module per incoming tuple of type GLOBAL_GAME_STATE

           /*
            * Defining application loops to monitor the latency of.
            * Here, we add only one loop for monitoring : EEG(sensor) -> Client -> Concentration Calculator -> Client -> DISPLAY (actuator)
            */
           final AppLoop loop1 = new AppLoop(new ArrayList<String>(){{add("EEG");add("client");add("concentration_calculator");add("connector");add("client");add("DISPLAY");}});
           List<AppLoop> loops = new ArrayList<AppLoop>(){{add(loop1);}};
           application.setLoops(loops);

           return application;

    }

    public static Application createApplication2(String appId, int userId){


        Application application = Application.createApplication(appId, userId); // creates an empty application model (empty directed graph)

        /*
         * Adding modules (vertices) to the application model (directed graph)
         */
        application.addAppModule("client", 9,2500,2500,2000); // adding module Client to the application model
        application.addAppModule("concentration_calculator", 4,1250,2000,2500); // adding module Concentration Calculator to the application model
        application.addAppModule("connector", 8,1000,1500,2000); // adding module Connector to the application model
        application.addAppModule("agent",10,2000,1800,2500);
        application.addAppModule("monitor",8,1000,800,1200);

        /*
         * Connecting the application modules (vertices) in the application model (directed graph) with edges
         */
        application.addAppEdge("EEG", "client", 3500, 250, "EEG", Tuple.UP, AppEdge.SENSOR);
        application.addAppEdge("client", "concentration_calculator", 1500, 80, "_SENSOR", Tuple.UP, AppEdge.MODULE); // adding edge from Client to Concentration Calculator module carrying tuples of type _SENSOR
        application.addAppEdge("concentration_calculator", "connector", 100, 1200, 150, "PLAYER_GAME_STATE", Tuple.UP, AppEdge.MODULE); // adding periodic edge (period=1000ms) from Concentration Calculator to Connector module carrying tuples of type PLAYER_GAME_STATE
        application.addAppEdge("concentration_calculator", "client", 1050, 800, "CONCENTRATION", Tuple.DOWN, AppEdge.MODULE);  // adding edge from Concentration Calculator to Client module carrying tuples of type CONCENTRATION
        application.addAppEdge("connector", "client", 100, 1250, 1000, "GLOBAL_GAME_STATE", Tuple.DOWN, AppEdge.MODULE); // adding periodic edge (period=1000ms) from Connector to Client module carrying tuples of type GLOBAL_GAME_STATE
        application.addAppEdge("client", "DISPLAY", 950, 500, "SELF_STATE_UPDATE", Tuple.DOWN, AppEdge.ACTUATOR);  // adding edge from Client module to Display (actuator) carrying tuples of type SELF_STATE_UPDATE
        application.addAppEdge("client", "DISPLAY", 1100, 600, "GLOBAL_STATE_UPDATE", Tuple.DOWN, AppEdge.ACTUATOR);  // adding edge from Client module to Display (actuator) carrying tuples of type GLOBAL_STATE_UPDATE
        application.addAppEdge("connector","agent",2000,1100,200,"AGENT_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("connector","monitor",1000,2000,250,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);


        application.addAppEdge("EEG", "client", 3500, 250, "EEG", Tuple.UP, AppEdge.SENSOR);
        application.addAppEdge("client", "concentration_calculator", 3000, 80, "_SENSOR", Tuple.UP, AppEdge.MODULE); // adding edge from Client to Concentration Calculator module carrying tuples of type _SENSOR
        application.addAppEdge("concentration_calculator", "connector", 100, 500, 150, "PLAYER_GAME_STATE", Tuple.UP, AppEdge.MODULE); // adding periodic edge (period=1000ms) from Concentration Calculator to Connector module carrying tuples of type PLAYER_GAME_STATE
        application.addAppEdge("concentration_calculator", "client", 14, 500, "CONCENTRATION", Tuple.DOWN, AppEdge.MODULE);  // adding edge from Concentration Calculator to Client module carrying tuples of type CONCENTRATION
        application.addAppEdge("connector", "client", 100, 28, 1000, "GLOBAL_GAME_STATE", Tuple.DOWN, AppEdge.MODULE); // adding periodic edge (period=1000ms) from Connector to Client module carrying tuples of type GLOBAL_GAME_STATE
        application.addAppEdge("client", "DISPLAY", 950, 500, "SELF_STATE_UPDATE", Tuple.DOWN, AppEdge.ACTUATOR);  // adding edge from Client module to Display (actuator) carrying tuples of type SELF_STATE_UPDATE
        application.addAppEdge("client", "DISPLAY", 100, 600, "GLOBAL_STATE_UPDATE", Tuple.DOWN, AppEdge.ACTUATOR);  // adding edge from Client module to Display (actuator) carrying tuples of type GLOBAL_STATE_UPDATE

        /*
         * Defining application loops to monitor the latency of.
         * Here, we add only one loop for monitoring : EEG(sensor) -> Client -> Concentration Calculator -> Client -> DISPLAY (actuator)
         */
        final AppLoop loop1 = new AppLoop(new ArrayList<String>(){{add("EEG");add("client");add("concentration_calculator");add("connector");add("agent");add("monitor");add("client");add("DISPLAY");}});
        List<AppLoop> loops = new ArrayList<AppLoop>(){{add(loop1);}};
        application.setLoops(loops);
        return application;
    }
    public static Application createApplication3(String appId, int userId){

        Application application = Application.createApplication(appId, userId); // creates an empty application model (empty directed graph)

        /*
         * Adding modules (vertices) to the application model (directed graph)
         */
        application.addAppModule("client", 9,2500,2500,2000); // adding module Client to the application model
        application.addAppModule("concentration_calculator", 4,1250,2000,2500); // adding module Concentration Calculator to the application model
        application.addAppModule("connector", 8,1000,1500,2000); // adding module Connector to the application model
        application.addAppModule("agent",10,2000,1800,2500);
        application.addAppModule("monitor",8,1000,800,1200);
        application.addAppModule("motion_detector",8,800,1000,1000);


        /*
         * Connecting the application modules (vertices) in the application model (directed graph) with edges
         */
        application.addAppEdge("EEG", "client", 3500, 250, "EEG", Tuple.UP, AppEdge.SENSOR);
        application.addAppEdge("client", "concentration_calculator", 1500, 80, "_SENSOR", Tuple.UP, AppEdge.MODULE); // adding edge from Client to Concentration Calculator module carrying tuples of type _SENSOR
        application.addAppEdge("concentration_calculator", "connector", 100, 1200, 150, "PLAYER_GAME_STATE", Tuple.UP, AppEdge.MODULE); // adding periodic edge (period=1000ms) from Concentration Calculator to Connector module carrying tuples of type PLAYER_GAME_STATE
        application.addAppEdge("concentration_calculator", "client", 1050, 800, "CONCENTRATION", Tuple.DOWN, AppEdge.MODULE);  // adding edge from Concentration Calculator to Client module carrying tuples of type CONCENTRATION
        application.addAppEdge("connector", "client", 100, 1250, 1000, "GLOBAL_GAME_STATE", Tuple.DOWN, AppEdge.MODULE); // adding periodic edge (period=1000ms) from Connector to Client module carrying tuples of type GLOBAL_GAME_STATE
        application.addAppEdge("client", "DISPLAY", 950, 500, "SELF_STATE_UPDATE", Tuple.DOWN, AppEdge.ACTUATOR);  // adding edge from Client module to Display (actuator) carrying tuples of type SELF_STATE_UPDATE
        application.addAppEdge("client", "DISPLAY", 1100, 600, "GLOBAL_STATE_UPDATE", Tuple.DOWN, AppEdge.ACTUATOR);  // adding edge from Client module to Display (actuator) carrying tuples of type GLOBAL_STATE_UPDATE
        application.addAppEdge("concentration_calculator","agent",2000,1100,200,"AGENT_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("concentration_calculator","monitor",1000,2000,250,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("client","motion_detector",900,250,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("motion_detector","monitor",900,250,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        /*
         * Defining application loops to monitor the latency of.
         * Here, we add only one loop for monitoring : EEG(sensor) -> Client -> Concentration Calculator -> Client -> DISPLAY (actuator)
         */
        final AppLoop loop1 = new AppLoop(new ArrayList<String>(){{add("EEG");add("client");add("concentration_calculator");add("client");add("DISPLAY");add("agent");add("monitor");add("motion_detector");}});
        List<AppLoop> loops = new ArrayList<AppLoop>(){{add(loop1);}};
        application.setLoops(loops);

        return application;
    }


    public static Application createApplication4(String appId, int userId){


        Application application = Application.createApplication(appId, userId); // creates an empty application model (empty directed graph)

        /*
         * Adding modules (vertices) to the application model (directed graph)
         */
        application.addAppModule("client", 9,2500,2500,2000); // adding module Client to the application model
        application.addAppModule("concentration_calculator", 4,1250,2000,2500); // adding module Concentration Calculator to the application model
        application.addAppModule("connector", 8,1000,1500,2000); // adding module Connector to the application model
        application.addAppModule("agent",10,2000,1800,2500);
        application.addAppModule("monitor",8,1000,800,1200);
        application.addAppModule("motion_detector",8,800,1000,1000);
        application.addAppModule("central",8,800,1000,1000);
        application.addAppModule("object_tracker",8,900,1000,1000);

        /*
         * Connecting the application modules (vertices) in the application model (directed graph) with edges
         */
        application.addAppEdge("EEG", "client", 3500, 250, "EEG", Tuple.UP, AppEdge.SENSOR);
        application.addAppEdge("client", "concentration_calculator", 1500, 80, "_SENSOR", Tuple.UP, AppEdge.MODULE); // adding edge from Client to Concentration Calculator module carrying tuples of type _SENSOR
        application.addAppEdge("concentration_calculator", "connector", 100, 1200, 150, "PLAYER_GAME_STATE", Tuple.UP, AppEdge.MODULE); // adding periodic edge (period=1000ms) from Concentration Calculator to Connector module carrying tuples of type PLAYER_GAME_STATE
        application.addAppEdge("concentration_calculator", "client", 1050, 800, "CONCENTRATION", Tuple.DOWN, AppEdge.MODULE);

        application.addAppEdge("connector", "client", 100, 1250, 1000, "GLOBAL_GAME_STATE", Tuple.DOWN, AppEdge.MODULE); // adding periodic edge (period=1000ms) from Connector to Client module carrying tuples of type GLOBAL_GAME_STATE
        application.addAppEdge("client", "DISPLAY", 950, 500, "SELF_STATE_UPDATE", Tuple.DOWN, AppEdge.ACTUATOR);  // adding edge from Client module to Display (actuator) carrying tuples of type SELF_STATE_UPDATE
        application.addAppEdge("client", "DISPLAY", 1100, 600, "GLOBAL_STATE_UPDATE", Tuple.DOWN, AppEdge.ACTUATOR);  // adding edge from Client module to Display (actuator) carrying tuples of type GLOBAL_STATE_UPDATE
        application.addAppEdge("concentration_calculator","agent",2000,1100,200,"AGENT_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("concentration_calculator","monitor",1000,2000,250,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("client","motion_detector",900,250,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("motion_detector","monitor",900,250,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("monitor","central",1000,550,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("central","monitor",750,400,"MONITOR_TYPE",Tuple.DOWN,AppEdge.MODULE);
        application.addAppEdge("agent","object_tracker",700,400,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);

        /*
         * Defining application loops to monitor the latency of.
         * Here, we add only one loop for monitoring : EEG(sensor) -> Client -> Concentration Calculator -> Client -> DISPLAY (actuator)
         */
        final AppLoop loop1 = new AppLoop(new ArrayList<String>(){{add("EEG");add("client");add("concentration_calculator");add("connector");
        add("agent");add("monitor");add("motion_detector");add("central");add("object_tracker");add("client");add("DISPLAY");}});
        List<AppLoop> loops = new ArrayList<AppLoop>(){{add(loop1);}};
        application.setLoops(loops);
        return application;
    }
    public static Application createApplication5(String appId, int userId){


        Application application = Application.createApplication(appId, userId); // creates an empty application model (empty directed graph)

        /*
         * Adding modules (vertices) to the application model (directed graph)
         */
        application.addAppModule("client", 9,2500,2500,2000); // adding module Client to the application model
        application.addAppModule("concentration_calculator", 4,1250,2000,2500); // adding module Concentration Calculator to the application model
        application.addAppModule("connector", 8,1000,1500,2000); // adding module Connector to the application model
        application.addAppModule("agent",10,2000,1800,2500);
        application.addAppModule("monitor",8,1000,800,1200);
        application.addAppModule("motion_detector",8,800,1000,1000);
        application.addAppModule("central",8,800,1000,1000);
        application.addAppModule("object_tracker",8,900,1000,1000);
        application.addAppModule("camera",10,1500,4000,950);
        application.addAppModule("ptz_control",8,1200,1500,850);
        application.addAppModule("driver_module",9,1000,850,735);





        /*
         * Connecting the application modules (vertices) in the application model (directed graph) with edges
         */
        application.addAppEdge("EEG", "client", 3500, 250, "EEG", Tuple.UP, AppEdge.SENSOR);
        application.addAppEdge("client", "concentration_calculator", 1500, 80, "_SENSOR", Tuple.UP, AppEdge.MODULE); // adding edge from Client to Concentration Calculator module carrying tuples of type _SENSOR
        application.addAppEdge("concentration_calculator", "connector", 100, 1200, 150, "PLAYER_GAME_STATE", Tuple.UP, AppEdge.MODULE); // adding periodic edge (period=1000ms) from Concentration Calculator to Connector module carrying tuples of type PLAYER_GAME_STATE
        application.addAppEdge("concentration_calculator", "client", 1050, 800, "CONCENTRATION", Tuple.DOWN, AppEdge.MODULE);  // adding edge from Concentration Calculator to Client module carrying tuples of type CONCENTRATION
        application.addAppEdge("connector", "client", 100, 1250, 1000, "GLOBAL_GAME_STATE", Tuple.DOWN, AppEdge.MODULE); // adding periodic edge (period=1000ms) from Connector to Client module carrying tuples of type GLOBAL_GAME_STATE
        application.addAppEdge("client", "DISPLAY", 950, 500, "SELF_STATE_UPDATE", Tuple.DOWN, AppEdge.ACTUATOR);  // adding edge from Client module to Display (actuator) carrying tuples of type SELF_STATE_UPDATE
        application.addAppEdge("client", "DISPLAY", 1100, 600, "GLOBAL_STATE_UPDATE", Tuple.DOWN, AppEdge.ACTUATOR);  // adding edge from Client module to Display (actuator) carrying tuples of type GLOBAL_STATE_UPDATE
        application.addAppEdge("concentration_calculator","agent",2000,1100,200,"AGENT_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("concentration_calculator","monitor",1000,2000,250,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("client","motion_detector",900,250,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("motion_detector","monitor",900,250,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("monitor","central",1000,550,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("central","monitor",750,400,"MONITOR_TYPE",Tuple.DOWN,AppEdge.MODULE);
        application.addAppEdge("agent","object_tracker",700,400,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("object_tracker","camera",900,123,"OBJECT_TRACKER_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("object_tracker","ptz_control",800,423,"OBJECT_TRACKER_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("central","driver_module",700,423,"CENTRAL_TYPE",Tuple.UP,AppEdge.MODULE);


        /*
         * Defining application loops to monitor the latency of.
         * Here, we add only one loop for monitoring : EEG(sensor) -> Client -> Concentration Calculator -> Client -> DISPLAY (actuator)
         */
        final AppLoop loop1 = new AppLoop(new ArrayList<String>(){{add("EEG");add("client");add("concentration_calculator");
        add("client");add("DISPLAY");add("agent");add("monitor");add("motion_detector");add("central");add("object_tracker");
        add("camera");add("ptz_control");add("driver_module");}});
        List<AppLoop> loops = new ArrayList<AppLoop>(){{add(loop1);}};
        application.setLoops(loops);
        return application;
    }
    public static Application createApplication6(String appId, int userId){
        Application application = Application.createApplication(appId, userId); // creates an empty application model (empty directed graph)
        /*
         * Adding modules (vertices) to the application model (directed graph)
         */
        application.addAppModule("client", 9,2500,2500,2000); // adding module Client to the application model
        application.addAppModule("concentration_calculator", 4,1250,2000,2500); // adding module Concentration Calculator to the application model
        application.addAppModule("connector", 8,1000,1500,2000); // adding module Connector to the application model
        application.addAppModule("agent",10,2000,1800,2500);
        application.addAppModule("monitor",8,1000,800,1200);
        application.addAppModule("motion_detector",8,800,1000,1000);
        application.addAppModule("central",8,800,1000,1000);
        application.addAppModule("object_tracker",8,900,1000,1000);
        application.addAppModule("camera",10,1500,4000,950);
        application.addAppModule("ptz_control",8,1200,1500,850);
        application.addAppModule("driver_module",9,1000,850,735);
        application.addAppModule("packet_module",10,2000,2850,735);
        application.addAppModule("mouse_control",9,1500,850,735);
        application.addAppModule("backbone",9,50,1850,735);


        /*
         * Connecting the application modules (vertices) in the application model (directed graph) with edges
         */
        application.addAppEdge("EEG", "client", 3500, 250, "EEG", Tuple.UP, AppEdge.SENSOR);
        application.addAppEdge("client", "concentration_calculator", 1500, 80, "_SENSOR", Tuple.UP, AppEdge.MODULE); // adding edge from Client to Concentration Calculator module carrying tuples of type _SENSOR
        application.addAppEdge("concentration_calculator", "connector", 100, 1200, 150, "PLAYER_GAME_STATE", Tuple.UP, AppEdge.MODULE); // adding periodic edge (period=1000ms) from Concentration Calculator to Connector module carrying tuples of type PLAYER_GAME_STATE
        application.addAppEdge("concentration_calculator", "client", 1050, 800, "CONCENTRATION", Tuple.DOWN, AppEdge.MODULE);  // adding edge from Concentration Calculator to Client module carrying tuples of type CONCENTRATION
        application.addAppEdge("connector", "client", 100, 1250, 1000, "GLOBAL_GAME_STATE", Tuple.DOWN, AppEdge.MODULE); // adding periodic edge (period=1000ms) from Connector to Client module carrying tuples of type GLOBAL_GAME_STATE
        application.addAppEdge("client", "DISPLAY", 950, 500, "SELF_STATE_UPDATE", Tuple.DOWN, AppEdge.ACTUATOR);  // adding edge from Client module to Display (actuator) carrying tuples of type SELF_STATE_UPDATE
        application.addAppEdge("client", "DISPLAY", 1100, 600, "GLOBAL_STATE_UPDATE", Tuple.DOWN, AppEdge.ACTUATOR);  // adding edge from Client module to Display (actuator) carrying tuples of type GLOBAL_STATE_UPDATE
        application.addAppEdge("concentration_calculator","agent",2000,1100,200,"AGENT_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("concentration_calculator","monitor",1000,2000,250,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("client","motion_detector",900,250,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("motion_detector","monitor",900,250,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("monitor","central",1000,550,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("central","monitor",750,400,"MONITOR_TYPE",Tuple.DOWN,AppEdge.MODULE);
        application.addAppEdge("agent","object_tracker",700,400,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("object_tracker","camera",900,123,"OBJECT_TRACKER_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("object_tracker","ptz_control",800,423,"OBJECT_TRACKER_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("central","driver_module",700,423,"CENTRAL_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("ptz_control","packet_module",850,150,"PTZ_CONTROL_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("driver_module","packet_module",1450,2250,"DRIVER_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("camera","mouse_control",1450,2250,"CAMERA_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("camera","backbone",1000,1250,"CAMERA_TYPE",Tuple.UP,AppEdge.MODULE);
        /*
         * Defining application loops to monitor the latency of.
         * Here, we add only one loop for monitoring : EEG(sensor) -> Client -> Concentration Calculator -> Client -> DISPLAY (actuator)
         */
        final AppLoop loop1 = new AppLoop(new ArrayList<String>(){{add("EEG");add("client");add("concentration_calculator");
        add("client");add("DISPLAY");add("agent");add("monitor");add("motion_detector");add("central");add("object_tracker");
        add("camera");add("ptz_control");add("driver_module");add("packet_module");add("mouse_control");add("backbone");
        }});
        List<AppLoop> loops = new ArrayList<AppLoop>(){{add(loop1);}};
        application.setLoops(loops);
        return application;
    }


    public static Application createApplication7(String appId, int userId){
        Application application = Application.createApplication(appId, userId); // creates an empty application model (empty directed graph)
        /*
         * Adding modules (vertices) to the application model (directed graph)
         */
        application.addAppModule("client", 9,2500,2500,2000); // adding module Client to the application model
        application.addAppModule("concentration_calculator", 4,1250,2000,2500); // adding module Concentration Calculator to the application model
        application.addAppModule("connector", 8,1000,1500,2000); // adding module Connector to the application model
        application.addAppModule("agent",10,2000,1800,2500);
        application.addAppModule("monitor",8,1000,800,1200);
        application.addAppModule("motion_detector",8,800,1000,1000);
        application.addAppModule("central",8,800,1000,1000);
        application.addAppModule("object_tracker",8,900,1000,1000);
        application.addAppModule("camera",10,1500,4000,950);
        application.addAppModule("ptz_control",8,1200,1500,850);
        application.addAppModule("driver_module",9,1000,850,735);
        application.addAppModule("packet_module",10,2000,2850,735);
        application.addAppModule("mouse_control",9,1500,850,735);
        application.addAppModule("backbone",9,50,1850,735);
        application.addAppModule("network_control",9,1050,850,735);
        application.addAppModule("image_processing",9,1050,850,735);
        application.addAppModule("compressing",9,1050,850,735);

        /*
         * Connecting the application modules (vertices) in the application model (directed graph) with edges
         */
        application.addAppEdge("EEG", "client", 3500, 250, "EEG", Tuple.UP, AppEdge.SENSOR);
        application.addAppEdge("client", "concentration_calculator", 1500, 80, "_SENSOR", Tuple.UP, AppEdge.MODULE); // adding edge from Client to Concentration Calculator module carrying tuples of type _SENSOR
        application.addAppEdge("concentration_calculator", "connector", 100, 1200, 150, "PLAYER_GAME_STATE", Tuple.UP, AppEdge.MODULE); // adding periodic edge (period=1000ms) from Concentration Calculator to Connector module carrying tuples of type PLAYER_GAME_STATE
        application.addAppEdge("concentration_calculator", "client", 1050, 800, "CONCENTRATION", Tuple.DOWN, AppEdge.MODULE);  // adding edge from Concentration Calculator to Client module carrying tuples of type CONCENTRATION
        application.addAppEdge("connector", "client", 100, 1250, 1000, "GLOBAL_GAME_STATE", Tuple.DOWN, AppEdge.MODULE); // adding periodic edge (period=1000ms) from Connector to Client module carrying tuples of type GLOBAL_GAME_STATE
        application.addAppEdge("client", "DISPLAY", 950, 500, "SELF_STATE_UPDATE", Tuple.DOWN, AppEdge.ACTUATOR);  // adding edge from Client module to Display (actuator) carrying tuples of type SELF_STATE_UPDATE
        application.addAppEdge("client", "DISPLAY", 1100, 600, "GLOBAL_STATE_UPDATE", Tuple.DOWN, AppEdge.ACTUATOR);  // adding edge from Client module to Display (actuator) carrying tuples of type GLOBAL_STATE_UPDATE
        application.addAppEdge("concentration_calculator","agent",2000,1100,200,"AGENT_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("concentration_calculator","monitor",1000,2000,250,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("client","motion_detector",900,250,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("motion_detector","monitor",900,250,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("monitor","central",1000,550,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("central","monitor",750,400,"MONITOR_TYPE",Tuple.DOWN,AppEdge.MODULE);
        application.addAppEdge("agent","object_tracker",700,400,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("object_tracker","camera",900,123,"OBJECT_TRACKER_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("object_tracker","ptz_control",800,423,"OBJECT_TRACKER_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("central","driver_module",700,423,"CENTRAL_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("ptz_control","packet_module",850,150,"PTZ_CONTROL_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("driver_module","packet_module",1450,2250,"DRIVER_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("camera","mouse_control",1450,2250,"CAMERA_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("camera","backbone",1000,1250,"CAMERA_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("backbone","network_control",1050,1050,"BACKBONE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("network_control","mouse_control",1300,1020,"NETWORK_CONTROL_TYPE",Tuple.DOWN,AppEdge.MODULE);
        application.addAppEdge("packet_module","compressing",1050,1050,"PACKET_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("packet_module","image_processing",1000,1050,"PACKET_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        /*
         * Defining application loops to monitor the latency of.
         * Here, we add only one loop for monitoring : EEG(sensor) -> Client -> Concentration Calculator -> Client -> DISPLAY (actuator)
         */
        final AppLoop loop1 = new AppLoop(new ArrayList<String>(){{add("EEG");add("client");add("concentration_calculator");add("connector");
        add("agent");add("monitor");add("motion_detector");add("central");add("object_tracker"); add("camera");add("ptz_control");add("driver_module");
        add("packet_module");add("mouse_control");add("backbone"); add("network_control");add("image_processing");add("compressing");add("client");add("DISPLAY");}});
        List<AppLoop> loops = new ArrayList<AppLoop>(){{add(loop1);}};
        application.setLoops(loops);
        return application;
    }


    public static Application createApplication8(String appId, int userId){
        Application application = Application.createApplication(appId, userId); // creates an empty application model (empty directed graph)
        /*
         * Adding modules (vertices) to the application model (directed graph)
         */
        application.addAppModule("client", 9,1500,2500,2000); // adding module Client to the application model
        application.addAppModule("concentration_calculator", 4,1250,2000,2500); // adding module Concentration Calculator to the application model
        application.addAppModule("connector", 8,1000,1500,2000); // adding module Connector to the application model
        application.addAppModule("agent",10,1800,1800,2500);
        application.addAppModule("monitor",8,1200,800,1200);
        application.addAppModule("motion_detector",8,1800,1000,1000);
        application.addAppModule("central",8,850,950,1000);
        application.addAppModule("object_tracker",8,900,1000,1000);
        application.addAppModule("camera",10,1500,4000,950);
        application.addAppModule("ptz_control",8,1200,1500,850);
        application.addAppModule("driver_module",9,1000,850,735);
        application.addAppModule("packet_module",10,1900,2850,735);
        application.addAppModule("mouse_control",9,1545,850,735);
        application.addAppModule("backbone",9,50,1850,735);
        application.addAppModule("network_control",9,1050,850,735);
        application.addAppModule("image_processing",9,1050,850,735);
        application.addAppModule("compressing",9,1050,850,735);
        application.addAppModule("tracking_module",8,1450,1500,1020);
        application.addAppModule("data_storage",8,1900,1000,1020);
        application.addAppModule("analytics_module",10,1450,100,1000);

        /*
         * Connecting the application modules (vertices) in the application model (directed graph) with edges
         */
        application.addAppEdge("EEG", "client", 2500, 250, "EEG", Tuple.UP, AppEdge.SENSOR);
        application.addAppEdge("client", "concentration_calculator", 1500, 80, "_SENSOR", Tuple.UP, AppEdge.MODULE); // adding edge from Client to Concentration Calculator module carrying tuples of type _SENSOR
        application.addAppEdge("concentration_calculator", "connector", 100, 1200, 150, "PLAYER_GAME_STATE", Tuple.UP, AppEdge.MODULE); // adding periodic edge (period=1000ms) from Concentration Calculator to Connector module carrying tuples of type PLAYER_GAME_STATE
        application.addAppEdge("concentration_calculator", "client", 1050, 800, "CONCENTRATION", Tuple.DOWN, AppEdge.MODULE);  // adding edge from Concentration Calculator to Client module carrying tuples of type CONCENTRATION
        application.addAppEdge("connector", "client", 100, 1250, 1000, "GLOBAL_GAME_STATE", Tuple.DOWN, AppEdge.MODULE); // adding periodic edge (period=1000ms) from Connector to Client module carrying tuples of type GLOBAL_GAME_STATE
        application.addAppEdge("client", "DISPLAY", 950, 500, "SELF_STATE_UPDATE", Tuple.DOWN, AppEdge.ACTUATOR);  // adding edge from Client module to Display (actuator) carrying tuples of type SELF_STATE_UPDATE
        application.addAppEdge("client", "DISPLAY", 1100, 600, "GLOBAL_STATE_UPDATE", Tuple.DOWN, AppEdge.ACTUATOR);  // adding edge from Client module to Display (actuator) carrying tuples of type GLOBAL_STATE_UPDATE
        application.addAppEdge("concentration_calculator","agent",2000,1100,200,"AGENT_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("concentration_calculator","monitor",1000,2000,250,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("client","motion_detector",900,250,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("motion_detector","monitor",900,250,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("monitor","central",1000,550,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("central","monitor",1050,400,"MONITOR_TYPE",Tuple.DOWN,AppEdge.MODULE);
        application.addAppEdge("agent","object_tracker",1700,400,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("object_tracker","camera",1900,123,"OBJECT_TRACKER_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("object_tracker","ptz_control",1800,423,"OBJECT_TRACKER_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("central","driver_module",1000,423,"CENTRAL_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("ptz_control","packet_module",1850,150,"PTZ_CONTROL_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("driver_module","packet_module",1450,2250,"DRIVER_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("camera","mouse_control",1450,2250,"CAMERA_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("camera","backbone",1000,1250,"CAMERA_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("backbone","network_control",1050,1050,"BACKBONE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("network_control","mouse_control",1300,1020,"NETWORK_CONTROL_TYPE",Tuple.DOWN,AppEdge.MODULE);
        application.addAppEdge("packet_module","compressing",1050,1050,"PACKET_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("packet_module","image_processing",1000,1050,"PACKET_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("image_processing","data_storage",1150,1000,"IMAGE_PROCESSING_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("compressing","data_storage",1250,1000,"COMPRESSING_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("network_control","tracking_module",1000,1005,"NETWORK_CONTROL_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("data_storage","analytics_module",1050,1000,"DATA_STORAGE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("tracking_module","analytics_module",1650,1121,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);

        /*
         * Defining application loops to monitor the latency of.
         * Here, we add only one loop for monitoring : EEG(sensor) -> Client -> Concentration Calculator -> Client -> DISPLAY (actuator)
         */
        final AppLoop loop1 = new AppLoop(new ArrayList<String>(){{add("EEG");add("client");add("concentration_calculator");add("connector");
        add("agent");add("monitor");add("motion_detector");add("central");add("object_tracker");add("camera");add("ptz_control");add("driver_module");
        add("packet_module");add("mouse_control");add("backbone"); add("network_control");add("image_processing");add("compressing");add(("data_storage"));add("tracking_module");
        add("analytics_module");add("client");add("DISPLAY");}});
        List<AppLoop> loops = new ArrayList<AppLoop>(){{add(loop1);}};
        application.setLoops(loops);
        return application;
    }
 public static Application createApplication9(String appId, int userId){
        Application application = Application.createApplication(appId, userId); // creates an empty application model (empty directed graph)
        /*
         * Adding modules (vertices) to the application model (directed graph)
         */
     application.addAppModule("client", 9,1500,2500,2000); // adding module Client to the application model
     application.addAppModule("concentration_calculator", 4,1250,2000,2500); // adding module Concentration Calculator to the application model
     application.addAppModule("connector", 8,1000,1500,2000); // adding module Connector to the application model
     application.addAppModule("agent",10,1800,1800,2500);
     application.addAppModule("monitor",8,1200,800,1200);
     application.addAppModule("motion_detector",8,1800,1000,1000);
     application.addAppModule("central",8,850,950,1000);
     application.addAppModule("object_tracker",8,900,1000,1000);
     application.addAppModule("camera",10,1500,4000,950);
     application.addAppModule("ptz_control",8,1200,1500,850);
     application.addAppModule("driver_module",9,1000,850,735);
     application.addAppModule("packet_module",10,1900,2850,735);
     application.addAppModule("mouse_control",9,1545,850,735);
     application.addAppModule("backbone",9,50,1850,735);
     application.addAppModule("network_control",9,1050,850,735);
     application.addAppModule("image_processing",9,1050,850,735);
     application.addAppModule("compressing",9,1050,850,735);
     application.addAppModule("tracking_module",8,1450,1500,1020);
     application.addAppModule("data_storage",8,1900,1000,1020);
     application.addAppModule("analytics_module",10,1450,100,1000);
     application.addAppModule("application_integration",10,1250,1100,1000);
     application.addAppModule("monitoring",9,1050,1000,1100);
     application.addAppModule("cost-management",10,1450,1000,1100);
     application.addAppModule("transfert_module",10,1050,1000,1050);
        /*
         * Connecting the application modules (vertices) in the application model (directed graph) with edges
         */
     application.addAppEdge("EEG", "client", 2500, 250, "EEG", Tuple.UP, AppEdge.SENSOR);
     application.addAppEdge("client", "concentration_calculator", 1500, 80, "_SENSOR", Tuple.UP, AppEdge.MODULE); // adding edge from Client to Concentration Calculator module carrying tuples of type _SENSOR
     application.addAppEdge("concentration_calculator", "connector", 100, 1200, 150, "PLAYER_GAME_STATE", Tuple.UP, AppEdge.MODULE); // adding periodic edge (period=1000ms) from Concentration Calculator to Connector module carrying tuples of type PLAYER_GAME_STATE
     application.addAppEdge("concentration_calculator", "client", 1050, 800, "CONCENTRATION", Tuple.DOWN, AppEdge.MODULE);  // adding edge from Concentration Calculator to Client module carrying tuples of type CONCENTRATION
     application.addAppEdge("connector", "client", 100, 1250, 1000, "GLOBAL_GAME_STATE", Tuple.DOWN, AppEdge.MODULE); // adding periodic edge (period=1000ms) from Connector to Client module carrying tuples of type GLOBAL_GAME_STATE
     application.addAppEdge("client", "DISPLAY", 950, 500, "SELF_STATE_UPDATE", Tuple.DOWN, AppEdge.ACTUATOR);  // adding edge from Client module to Display (actuator) carrying tuples of type SELF_STATE_UPDATE
     application.addAppEdge("client", "DISPLAY", 1100, 600, "GLOBAL_STATE_UPDATE", Tuple.DOWN, AppEdge.ACTUATOR);  // adding edge from Client module to Display (actuator) carrying tuples of type GLOBAL_STATE_UPDATE
     application.addAppEdge("concentration_calculator","agent",2000,1100,200,"AGENT_TYPE",Tuple.UP,AppEdge.MODULE);
     application.addAppEdge("concentration_calculator","monitor",1000,2000,250,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
     application.addAppEdge("client","motion_detector",900,250,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
     application.addAppEdge("motion_detector","monitor",900,250,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
     application.addAppEdge("monitor","central",1000,550,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
     application.addAppEdge("central","monitor",1050,400,"MONITOR_TYPE",Tuple.DOWN,AppEdge.MODULE);
     application.addAppEdge("agent","object_tracker",1700,400,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
     application.addAppEdge("object_tracker","camera",1900,123,"OBJECT_TRACKER_TYPE",Tuple.UP,AppEdge.MODULE);
     application.addAppEdge("object_tracker","ptz_control",1800,423,"OBJECT_TRACKER_TYPE",Tuple.UP,AppEdge.MODULE);
     application.addAppEdge("central","driver_module",1000,423,"CENTRAL_TYPE",Tuple.UP,AppEdge.MODULE);
     application.addAppEdge("ptz_control","packet_module",1850,150,"PTZ_CONTROL_TYPE",Tuple.UP,AppEdge.MODULE);
     application.addAppEdge("driver_module","packet_module",1450,2250,"DRIVER_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
     application.addAppEdge("camera","mouse_control",1450,2250,"CAMERA_TYPE",Tuple.UP,AppEdge.MODULE);
     application.addAppEdge("camera","backbone",1000,1250,"CAMERA_TYPE",Tuple.UP,AppEdge.MODULE);
     application.addAppEdge("backbone","network_control",1050,1050,"BACKBONE_TYPE",Tuple.UP,AppEdge.MODULE);
     application.addAppEdge("network_control","mouse_control",1300,1020,"NETWORK_CONTROL_TYPE",Tuple.DOWN,AppEdge.MODULE);
     application.addAppEdge("packet_module","compressing",1050,1050,"PACKET_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
     application.addAppEdge("packet_module","image_processing",1000,1050,"PACKET_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
     application.addAppEdge("image_processing","data_storage",1150,1000,"IMAGE_PROCESSING_TYPE",Tuple.UP,AppEdge.MODULE);
     application.addAppEdge("compressing","data_storage",1250,1000,"COMPRESSING_TYPE",Tuple.UP,AppEdge.MODULE);
     application.addAppEdge("network_control","tracking_module",1000,1005,"NETWORK_CONTROL_TYPE",Tuple.UP,AppEdge.MODULE);
     application.addAppEdge("data_storage","analytics_module",1050,1000,"DATA_STORAGE_TYPE",Tuple.UP,AppEdge.MODULE);
     application.addAppEdge("tracking_module","analytics_module",1650,1121,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
     application.addAppEdge("analytics_module","application_integration",1100,1121,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
     application.addAppEdge("analytics_module","monitoring",700,1021,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
     application.addAppEdge("analytics_module","cost-management",1500,1000,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
     application.addAppEdge("analytics_module","transfert_module",1235,3050,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);

        /*
         * Defining application loops to monitor the latency of.
         * Here, we add only one loop for monitoring : EEG(sensor) -> Client -> Concentration Calculator -> Client -> DISPLAY (actuator)
         */
        final AppLoop loop1 = new AppLoop(new ArrayList<String>(){{add("EEG");add("client");add("concentration_calculator");
        add("client");add("DISPLAY");add("agent");add("monitor");add("motion_detector");add("central");add("object_tracker");
        add("camera");add("ptz_control");add("driver_module");add("packet_module");add("mouse_control");add("backbone");
        add("network_control");add("image_processing");add("compressing");add(("data_storage"));add("tracking_module");
        add("analytics_module");add("application-integration");add("monitoring");add("cost_management");
            add("transfert_module");}});
        List<AppLoop> loops = new ArrayList<AppLoop>(){{add(loop1);}};
        application.setLoops(loops);
        return application;
    }





    public static Application createApplication10(String appId, int userId){
        Application application = Application.createApplication(appId, userId); // creates an empty application model (empty directed graph)
        /*
         * Adding modules (vertices) to the application model (directed graph)
         */
     application.addAppModule("client", 9,1500,2500,2000); // adding module Client to the application model
     application.addAppModule("concentration_calculator", 4,1250,2000,2500); // adding module Concentration Calculator to the application model
     application.addAppModule("connector", 8,1000,1500,2000); // adding module Connector to the application model
     application.addAppModule("agent",10,1800,1800,2500);
     application.addAppModule("monitor",8,1200,800,1200);
     application.addAppModule("motion_detector",8,1800,1000,1000);
     application.addAppModule("central",8,850,950,1000);
     application.addAppModule("object_tracker",8,900,1000,1000);
     application.addAppModule("camera",10,1500,4000,950);
     application.addAppModule("ptz_control",8,1200,1500,850);
     application.addAppModule("driver_module",9,1000,850,735);
     application.addAppModule("packet_module",10,1900,2850,735);
     application.addAppModule("mouse_control",9,1545,850,735);
     application.addAppModule("backbone",9,50,1850,735);
     application.addAppModule("network_control",9,1050,850,735);
     application.addAppModule("image_processing",9,1050,850,735);
     application.addAppModule("compressing",9,1050,850,735);
     application.addAppModule("tracking_module",8,1450,1500,1020);
     application.addAppModule("data_storage",8,1900,1000,1020);
     application.addAppModule("analytics_module",10,1450,100,1000);
     application.addAppModule("application_integration",10,1250,1100,1000);
     application.addAppModule("monitoring",9,1050,1000,1100);
     application.addAppModule("cost-management",10,1450,1000,1100);
     application.addAppModule("transfert_module",10,1050,1000,1050);
     application.addAppModule("streams_module",10,800,1000,1050);
     application.addAppModule("deployment_module",10,900,1000,1050);
     application.addAppModule("convertion",10,850,1000,1050);
        /*
         * Connecting the application modules (vertices) in the application model (directed graph) with edges
         */
        application.addAppEdge("EEG", "client", 2500, 250, "EEG", Tuple.UP, AppEdge.SENSOR);
        application.addAppEdge("client", "concentration_calculator", 1500, 80, "_SENSOR", Tuple.UP, AppEdge.MODULE); // adding edge from Client to Concentration Calculator module carrying tuples of type _SENSOR
        application.addAppEdge("concentration_calculator", "connector", 100, 1200, 150, "PLAYER_GAME_STATE", Tuple.UP, AppEdge.MODULE); // adding periodic edge (period=1000ms) from Concentration Calculator to Connector module carrying tuples of type PLAYER_GAME_STATE
        application.addAppEdge("concentration_calculator", "client", 1050, 800, "CONCENTRATION", Tuple.DOWN, AppEdge.MODULE);  // adding edge from Concentration Calculator to Client module carrying tuples of type CONCENTRATION
        application.addAppEdge("connector", "client", 100, 1250, 1000, "GLOBAL_GAME_STATE", Tuple.DOWN, AppEdge.MODULE); // adding periodic edge (period=1000ms) from Connector to Client module carrying tuples of type GLOBAL_GAME_STATE
        application.addAppEdge("client", "DISPLAY", 950, 500, "SELF_STATE_UPDATE", Tuple.DOWN, AppEdge.ACTUATOR);  // adding edge from Client module to Display (actuator) carrying tuples of type SELF_STATE_UPDATE
        application.addAppEdge("client", "DISPLAY", 1100, 600, "GLOBAL_STATE_UPDATE", Tuple.DOWN, AppEdge.ACTUATOR);  // adding edge from Client module to Display (actuator) carrying tuples of type GLOBAL_STATE_UPDATE
        application.addAppEdge("concentration_calculator","agent",2000,1100,200,"AGENT_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("concentration_calculator","monitor",1000,2000,250,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("client","motion_detector",900,250,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("motion_detector","monitor",900,250,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("monitor","central",1000,550,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("central","monitor",1050,400,"MONITOR_TYPE",Tuple.DOWN,AppEdge.MODULE);
        application.addAppEdge("agent","object_tracker",1700,400,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("object_tracker","camera",1900,123,"OBJECT_TRACKER_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("object_tracker","ptz_control",1800,423,"OBJECT_TRACKER_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("central","driver_module",1000,423,"CENTRAL_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("ptz_control","packet_module",1850,150,"PTZ_CONTROL_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("driver_module","packet_module",1450,2250,"DRIVER_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("camera","mouse_control",1450,2250,"CAMERA_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("camera","backbone",1000,1250,"CAMERA_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("backbone","network_control",1050,1050,"BACKBONE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("network_control","mouse_control",1300,1020,"NETWORK_CONTROL_TYPE",Tuple.DOWN,AppEdge.MODULE);
        application.addAppEdge("packet_module","compressing",1050,1050,"PACKET_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("packet_module","image_processing",1000,1050,"PACKET_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("image_processing","data_storage",1150,1000,"IMAGE_PROCESSING_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("compressing","data_storage",1250,1000,"COMPRESSING_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("network_control","tracking_module",1000,1005,"NETWORK_CONTROL_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("data_storage","analytics_module",1050,1000,"DATA_STORAGE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("tracking_module","analytics_module",1650,1121,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("analytics_module","application_integration",1100,1121,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("analytics_module","monitoring",700,1021,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("analytics_module","cost-management",1500,1000,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("analytics_module","transfert_module",1235,3050,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("application_integration","streams_module",896,1050,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("monitoring","deployment_module",952,2050,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("monitoring","convertion",758,2050,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("streams_module","deployment_module",3500,1000,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);

        /*
         * Defining application loops to monitor the latency of.
         * Here, we add only one loop for monitoring : EEG(sensor) -> Client -> Concentration Calculator -> Client -> DISPLAY (actuator)
         */
        final AppLoop loop1 = new AppLoop(new ArrayList<String>(){{add("EEG");add("client");add("concentration_calculator");add("connector");
        add("agent");add("monitor");add("motion_detector");add("central");add("object_tracker");
        add("camera");add("ptz_control");add("driver_module");add("packet_module");add("mouse_control");add("backbone");
        add("network_control");add("image_processing");add("compressing");add(("data_storage"));add("tracking_module");
        add("analytics_module");add("application-integration");add("monitoring");add("cost_management");
            add("transfert_module");add("client");add("DISPLAY");}});
        List<AppLoop> loops = new ArrayList<AppLoop>(){{add(loop1);}};
        application.setLoops(loops);
        return application;
    }


    public static Application createApplication11(String appId, int userId){
        Application application = Application.createApplication(appId, userId); // creates an empty application model (empty directed graph)
        /*
         * Adding modules (vertices) to the application model (directed graph)
         */
     application.addAppModule("client", 9,1500,2500,2000); // adding module Client to the application model
     application.addAppModule("concentration_calculator", 4,1250,2000,2500); // adding module Concentration Calculator to the application model
     application.addAppModule("connector", 8,1000,1500,2000); // adding module Connector to the application model
     application.addAppModule("agent",10,1800,1800,2500);
     application.addAppModule("monitor",8,1200,800,1200);
     application.addAppModule("motion_detector",8,1800,1000,1000);
     application.addAppModule("central",8,850,950,1000);
     application.addAppModule("object_tracker",8,900,1000,1000);
     application.addAppModule("camera",10,1500,4000,950);
     application.addAppModule("ptz_control",8,1200,1500,850);
     application.addAppModule("driver_module",9,1000,850,735);
     application.addAppModule("packet_module",10,1900,2850,735);
     application.addAppModule("mouse_control",9,1545,850,735);
     application.addAppModule("backbone",9,50,1850,735);
     application.addAppModule("network_control",9,1050,850,735);
     application.addAppModule("image_processing",9,1050,850,735);
     application.addAppModule("compressing",9,1050,850,735);
     application.addAppModule("tracking_module",8,1450,1500,1020);
     application.addAppModule("data_storage",8,1900,1000,1020);
     application.addAppModule("analytics_module",10,1450,100,1000);
     application.addAppModule("application_integration",10,1250,1100,1000);
     application.addAppModule("monitoring",9,1050,1000,1100);
     application.addAppModule("cost-management",10,1450,1000,1100);
     application.addAppModule("transfert_module",10,1050,1000,1050);
     application.addAppModule("streams_module",10,800,1000,1050);
     application.addAppModule("deployment_module",10,900,1000,1050);
     application.addAppModule("convertion",10,850,1000,1050);
     application.addAppModule("isolation_ressource",10,1405,1010,1250);
     application.addAppModule("scalability",10,890,1010,1250);
     application.addAppModule("dns_service",10,1450,1010,1250);
        /*
         * Connecting the application modules (vertices) in the application model (directed graph) with edges
         */
        application.addAppEdge("EEG", "client", 2500, 250, "EEG", Tuple.UP, AppEdge.SENSOR);
        application.addAppEdge("client", "concentration_calculator", 1500, 80, "_SENSOR", Tuple.UP, AppEdge.MODULE); // adding edge from Client to Concentration Calculator module carrying tuples of type _SENSOR
        application.addAppEdge("concentration_calculator", "connector", 100, 1200, 150, "PLAYER_GAME_STATE", Tuple.UP, AppEdge.MODULE); // adding periodic edge (period=1000ms) from Concentration Calculator to Connector module carrying tuples of type PLAYER_GAME_STATE
        application.addAppEdge("concentration_calculator", "client", 1050, 800, "CONCENTRATION", Tuple.DOWN, AppEdge.MODULE);  // adding edge from Concentration Calculator to Client module carrying tuples of type CONCENTRATION
        application.addAppEdge("connector", "client", 100, 1250, 1000, "GLOBAL_GAME_STATE", Tuple.DOWN, AppEdge.MODULE); // adding periodic edge (period=1000ms) from Connector to Client module carrying tuples of type GLOBAL_GAME_STATE
        application.addAppEdge("client", "DISPLAY", 950, 500, "SELF_STATE_UPDATE", Tuple.DOWN, AppEdge.ACTUATOR);  // adding edge from Client module to Display (actuator) carrying tuples of type SELF_STATE_UPDATE
        application.addAppEdge("client", "DISPLAY", 1100, 600, "GLOBAL_STATE_UPDATE", Tuple.DOWN, AppEdge.ACTUATOR);  // adding edge from Client module to Display (actuator) carrying tuples of type GLOBAL_STATE_UPDATE
        application.addAppEdge("concentration_calculator","agent",2000,1100,200,"AGENT_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("concentration_calculator","monitor",1000,2000,250,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("client","motion_detector",900,250,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("motion_detector","monitor",900,250,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("monitor","central",1000,550,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("central","monitor",1050,400,"MONITOR_TYPE",Tuple.DOWN,AppEdge.MODULE);
        application.addAppEdge("agent","object_tracker",1700,400,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("object_tracker","camera",1900,123,"OBJECT_TRACKER_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("object_tracker","ptz_control",1800,423,"OBJECT_TRACKER_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("central","driver_module",1000,423,"CENTRAL_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("ptz_control","packet_module",1850,150,"PTZ_CONTROL_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("driver_module","packet_module",1450,2250,"DRIVER_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("camera","mouse_control",1450,2250,"CAMERA_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("camera","backbone",1000,1250,"CAMERA_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("backbone","network_control",1050,1050,"BACKBONE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("network_control","mouse_control",1300,1020,"NETWORK_CONTROL_TYPE",Tuple.DOWN,AppEdge.MODULE);
        application.addAppEdge("packet_module","compressing",1050,1050,"PACKET_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("packet_module","image_processing",1000,1050,"PACKET_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("image_processing","data_storage",1150,1000,"IMAGE_PROCESSING_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("compressing","data_storage",1250,1000,"COMPRESSING_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("network_control","tracking_module",1000,1005,"NETWORK_CONTROL_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("data_storage","analytics_module",1050,1000,"DATA_STORAGE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("tracking_module","analytics_module",1650,1121,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("analytics_module","application_integration",1100,1121,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("analytics_module","monitoring",700,1021,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("analytics_module","cost-management",1500,1000,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("analytics_module","transfert_module",1235,3050,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("application_integration","streams_module",896,1050,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("monitoring","deployment_module",952,2050,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("monitoring","convertion",758,2050,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("streams_module","deployment_module",3500,1000,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("cost_management","isolation_ressource",1200,2700,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("isolation_ressource","convertion",1050,2700,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("transfert_module","scalability",1500,2700,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("streams_module","dns_service",1450,1700,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);

        /*
         * Defining application loops to monitor the latency of.
         * Here, we add only one loop for monitoring : EEG(sensor) -> Client -> Concentration Calculator -> Client -> DISPLAY (actuator)
         */
        final AppLoop loop1 = new AppLoop(new ArrayList<String>(){{add("EEG");add("client");add("concentration_calculator");add("connector");
       add("agent");add("monitor");add("motion_detector");add("central");add("object_tracker");
        add("camera");add("ptz_control");add("driver_module");add("packet_module");add("mouse_control");add("backbone");
        add("network_control");add("image_processing");add("compressing");add(("data_storage"));add("tracking_module");
        add("analytics_module");add("application-integration");add("monitoring");add("cost_management");
        add("transfert_module");add("streams_module");add("deployment_module");add("convertion");add("scalability");add("isolation_ressource");
       add("dns_service"); add("client");add("DISPLAY");}});
        List<AppLoop> loops = new ArrayList<AppLoop>(){{add(loop1);}};
        application.setLoops(loops);
        return application;

    }

    public static Application createApplication12(String appId, int userId){
        Application application = Application.createApplication(appId, userId); // creates an empty application model (empty directed graph)
        /*
         * Adding modules (vertices) to the application model (directed graph)
         */
     application.addAppModule("client", 9,1500,2500,2000); // adding module Client to the application model
     application.addAppModule("concentration_calculator", 4,1250,2000,2500); // adding module Concentration Calculator to the application model
     application.addAppModule("connector", 8,1000,1500,2000); // adding module Connector to the application model
     application.addAppModule("agent",10,1800,1800,2500);
     application.addAppModule("monitor",8,1200,800,1200);
     application.addAppModule("motion_detector",8,1800,1000,1000);
     application.addAppModule("central",8,850,950,1000);
     application.addAppModule("object_tracker",8,900,1000,1000);
     application.addAppModule("camera",10,1500,4000,950);
     application.addAppModule("ptz_control",8,1200,1500,850);
     application.addAppModule("driver_module",9,1000,850,735);
     application.addAppModule("packet_module",10,1900,2850,735);
     application.addAppModule("mouse_control",9,1545,850,735);
     application.addAppModule("backbone",9,50,1850,735);
     application.addAppModule("network_control",9,1050,850,735);
     application.addAppModule("image_processing",9,1050,850,735);
     application.addAppModule("compressing",9,1050,850,735);
     application.addAppModule("tracking_module",8,1450,1500,1020);
     application.addAppModule("data_storage",8,1900,1000,1020);
     application.addAppModule("analytics_module",10,1450,100,1000);
     application.addAppModule("application_integration",10,1250,1100,1000);
     application.addAppModule("monitoring",9,1050,1000,1100);
     application.addAppModule("cost-management",10,1450,1000,1100);
     application.addAppModule("transfert_module",10,1050,1000,1050);
     application.addAppModule("streams_module",10,800,1000,1050);
     application.addAppModule("deployment_module",10,900,1000,1050);
     application.addAppModule("convertion",10,850,1000,1050);
     application.addAppModule("isolation_ressource",10,1405,1010,1250);
     application.addAppModule("scalability",10,890,1010,1250);
     application.addAppModule("dns_service",10,1450,1010,1250);
     application.addAppModule("dhcp_service",10,1010,1010,1250);
     application.addAppModule("messaging",9,1000,1050,1250);
     application.addAppModule("web_service",9,1100,1050,1250);

        /*
         * Connecting the application modules (vertices) in the application model (directed graph) with edges
         */
        application.addAppEdge("EEG", "client", 2500, 250, "EEG", Tuple.UP, AppEdge.SENSOR);
        application.addAppEdge("client", "concentration_calculator", 1500, 80, "_SENSOR", Tuple.UP, AppEdge.MODULE); // adding edge from Client to Concentration Calculator module carrying tuples of type _SENSOR
        application.addAppEdge("concentration_calculator", "connector", 100, 1200, 150, "PLAYER_GAME_STATE", Tuple.UP, AppEdge.MODULE); // adding periodic edge (period=1000ms) from Concentration Calculator to Connector module carrying tuples of type PLAYER_GAME_STATE
        application.addAppEdge("concentration_calculator", "client", 1050, 800, "CONCENTRATION", Tuple.DOWN, AppEdge.MODULE);  // adding edge from Concentration Calculator to Client module carrying tuples of type CONCENTRATION
        application.addAppEdge("connector", "client", 100, 1250, 1000, "GLOBAL_GAME_STATE", Tuple.DOWN, AppEdge.MODULE); // adding periodic edge (period=1000ms) from Connector to Client module carrying tuples of type GLOBAL_GAME_STATE
        application.addAppEdge("client", "DISPLAY", 950, 500, "SELF_STATE_UPDATE", Tuple.DOWN, AppEdge.ACTUATOR);  // adding edge from Client module to Display (actuator) carrying tuples of type SELF_STATE_UPDATE
        application.addAppEdge("client", "DISPLAY", 1100, 600, "GLOBAL_STATE_UPDATE", Tuple.DOWN, AppEdge.ACTUATOR);  // adding edge from Client module to Display (actuator) carrying tuples of type GLOBAL_STATE_UPDATE
        application.addAppEdge("concentration_calculator","agent",2000,1100,200,"AGENT_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("concentration_calculator","monitor",1000,2000,250,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("client","motion_detector",900,250,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("motion_detector","monitor",900,250,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("monitor","central",1000,550,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("central","monitor",1050,400,"MONITOR_TYPE",Tuple.DOWN,AppEdge.MODULE);
        application.addAppEdge("agent","object_tracker",1700,400,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("object_tracker","camera",1900,123,"OBJECT_TRACKER_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("object_tracker","ptz_control",1800,423,"OBJECT_TRACKER_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("central","driver_module",1000,423,"CENTRAL_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("ptz_control","packet_module",1850,150,"PTZ_CONTROL_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("driver_module","packet_module",1450,2250,"DRIVER_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("camera","mouse_control",1450,2250,"CAMERA_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("camera","backbone",1000,1250,"CAMERA_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("backbone","network_control",1050,1050,"BACKBONE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("network_control","mouse_control",1300,1020,"NETWORK_CONTROL_TYPE",Tuple.DOWN,AppEdge.MODULE);
        application.addAppEdge("packet_module","compressing",1050,1050,"PACKET_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("packet_module","image_processing",1000,1050,"PACKET_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("image_processing","data_storage",1150,1000,"IMAGE_PROCESSING_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("compressing","data_storage",1250,1000,"COMPRESSING_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("network_control","tracking_module",1000,1005,"NETWORK_CONTROL_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("data_storage","analytics_module",1050,1000,"DATA_STORAGE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("tracking_module","analytics_module",1650,1121,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("analytics_module","application_integration",1100,1121,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("analytics_module","monitoring",700,1021,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("analytics_module","cost-management",1500,1000,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("analytics_module","transfert_module",1235,3050,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("application_integration","streams_module",896,1050,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("monitoring","deployment_module",952,2050,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("monitoring","convertion",758,2050,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("streams_module","deployment_module",3500,1000,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("cost_management","convertion",1170,2590,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("cost_management","isolation_ressource",1200,2700,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("isolation_ressource","convertion",1050,2700,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("transfert_module","scalability",1500,2700,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("streams_module","dns_service",1450,1700,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("deployment_module","dhcp_service",1000,1700,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("isolation_ressource","messaging",1895,1700,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("messaging","isolation_ressource",1030,2600,"TRACKING_MODULE_TYPE",Tuple.DOWN,AppEdge.MODULE);
        application.addAppEdge("scalability","web_service",1000,2600,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);


        /*
         * Defining application loops to monitor the latency of.
         * Here, we add only one loop for monitoring : EEG(sensor) -> Client -> Concentration Calculator -> Client -> DISPLAY (actuator)
         */
        final AppLoop loop1 = new AppLoop(new ArrayList<String>(){{add("EEG");add("client");add("concentration_calculator");add("connector");
            add("agent");add("monitor");add("motion_detector");add("central");add("object_tracker");
            add("camera");add("ptz_control");add("driver_module");add("packet_module");add("mouse_control");add("backbone");
            add("network_control");add("image_processing");add("compressing");add(("data_storage"));add("tracking_module");
            add("analytics_module");add("application-integration");add("monitoring");add("cost_management");
            add("transfert_module");add("streams_module");add("deployment_module");add("convertion");add("scalability");add("isolation_ressource");
            add("dns_service"); add("dhcp_service");add("messaging");add("web_service"); add("client");add("DISPLAY");}});
        List<AppLoop> loops = new ArrayList<AppLoop>(){{add(loop1);}};
        application.setLoops(loops);
        return application;

    }


 public static Application createApplication13(String appId, int userId){
        Application application = Application.createApplication(appId, userId); // creates an empty application model (empty directed graph)
        /*
         * Adding modules (vertices) to the application model (directed graph)
         */
     application.addAppModule("client", 9,1500,2500,2000); // adding module Client to the application model
     application.addAppModule("concentration_calculator", 4,1250,2000,2500); // adding module Concentration Calculator to the application model
     application.addAppModule("connector", 8,1000,1500,2000); // adding module Connector to the application model
     application.addAppModule("agent",10,1800,1800,2500);
     application.addAppModule("monitor",8,1200,800,1200);
     application.addAppModule("motion_detector",8,1800,1000,1000);
     application.addAppModule("central",8,850,950,1000);
     application.addAppModule("object_tracker",8,900,1000,1000);
     application.addAppModule("camera",10,1500,4000,950);
     application.addAppModule("ptz_control",8,1200,1500,850);
     application.addAppModule("driver_module",9,1000,850,735);
     application.addAppModule("packet_module",10,1900,2850,735);
     application.addAppModule("mouse_control",9,1545,850,735);
     application.addAppModule("backbone",9,50,1850,735);
     application.addAppModule("network_control",9,1050,850,735);
     application.addAppModule("image_processing",9,1050,850,735);
     application.addAppModule("compressing",9,1050,850,735);
     application.addAppModule("tracking_module",8,1450,1500,1020);
     application.addAppModule("data_storage",8,1900,1000,1020);
     application.addAppModule("analytics_module",10,1450,100,1000);
     application.addAppModule("application_integration",10,1250,1100,1000);
     application.addAppModule("monitoring",9,1050,1000,1100);
     application.addAppModule("cost-management",10,1450,1000,1100);
     application.addAppModule("transfert_module",10,1050,1000,1050);
     application.addAppModule("streams_module",10,800,1000,1050);
     application.addAppModule("deployment_module",10,900,1000,1050);
     application.addAppModule("convertion",10,850,1000,1050);
     application.addAppModule("isolation_ressource",10,1405,1010,1250);
     application.addAppModule("scalability",10,890,1010,1250);
     application.addAppModule("dns_service",10,1450,1010,1250);
     application.addAppModule("dhcp_service",10,1010,1010,1250);
     application.addAppModule("messaging",9,1000,1050,1250);
     application.addAppModule("web_service",9,1100,1050,1250);
     application.addAppModule("telnet",9,1000,1050,1250);
     application.addAppModule("ssh",9,1780,1009,3800);
     application.addAppModule("ftp",9,1450,890,1580);

        /*
         * Connecting the application modules (vertices) in the application model (directed graph) with edges
         */
        application.addAppEdge("EEG", "client", 2500, 250, "EEG", Tuple.UP, AppEdge.SENSOR);
        application.addAppEdge("client", "concentration_calculator", 1500, 80, "_SENSOR", Tuple.UP, AppEdge.MODULE); // adding edge from Client to Concentration Calculator module carrying tuples of type _SENSOR
        application.addAppEdge("concentration_calculator", "connector", 100, 1200, 150, "PLAYER_GAME_STATE", Tuple.UP, AppEdge.MODULE); // adding periodic edge (period=1000ms) from Concentration Calculator to Connector module carrying tuples of type PLAYER_GAME_STATE
        application.addAppEdge("concentration_calculator", "client", 1050, 800, "CONCENTRATION", Tuple.DOWN, AppEdge.MODULE);  // adding edge from Concentration Calculator to Client module carrying tuples of type CONCENTRATION
        application.addAppEdge("connector", "client", 100, 1250, 1000, "GLOBAL_GAME_STATE", Tuple.DOWN, AppEdge.MODULE); // adding periodic edge (period=1000ms) from Connector to Client module carrying tuples of type GLOBAL_GAME_STATE
        application.addAppEdge("client", "DISPLAY", 950, 500, "SELF_STATE_UPDATE", Tuple.DOWN, AppEdge.ACTUATOR);  // adding edge from Client module to Display (actuator) carrying tuples of type SELF_STATE_UPDATE
        application.addAppEdge("client", "DISPLAY", 1100, 600, "GLOBAL_STATE_UPDATE", Tuple.DOWN, AppEdge.ACTUATOR);  // adding edge from Client module to Display (actuator) carrying tuples of type GLOBAL_STATE_UPDATE
        application.addAppEdge("concentration_calculator","agent",2000,1100,200,"AGENT_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("concentration_calculator","monitor",1000,2000,250,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("client","motion_detector",900,250,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("motion_detector","monitor",900,250,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("monitor","central",1000,550,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("central","monitor",1050,400,"MONITOR_TYPE",Tuple.DOWN,AppEdge.MODULE);
        application.addAppEdge("agent","object_tracker",1700,400,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("object_tracker","camera",1900,123,"OBJECT_TRACKER_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("object_tracker","ptz_control",1800,423,"OBJECT_TRACKER_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("central","driver_module",1000,423,"CENTRAL_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("ptz_control","packet_module",1850,150,"PTZ_CONTROL_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("driver_module","packet_module",1450,2250,"DRIVER_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("camera","mouse_control",1450,2250,"CAMERA_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("camera","backbone",1000,1250,"CAMERA_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("backbone","network_control",1050,1050,"BACKBONE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("network_control","mouse_control",1300,1020,"NETWORK_CONTROL_TYPE",Tuple.DOWN,AppEdge.MODULE);
        application.addAppEdge("packet_module","compressing",1050,1050,"PACKET_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("packet_module","image_processing",1000,1050,"PACKET_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("image_processing","data_storage",1150,1000,"IMAGE_PROCESSING_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("compressing","data_storage",1250,1000,"COMPRESSING_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("network_control","tracking_module",1000,1005,"NETWORK_CONTROL_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("data_storage","analytics_module",1050,1000,"DATA_STORAGE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("tracking_module","analytics_module",1650,1121,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("analytics_module","application_integration",1100,1121,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("analytics_module","monitoring",700,1021,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("analytics_module","cost-management",1500,1000,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("analytics_module","transfert_module",1235,3050,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("application_integration","streams_module",896,1050,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("monitoring","deployment_module",952,2050,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("monitoring","convertion",758,2050,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("streams_module","deployment_module",3500,1000,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("cost_management","convertion",1170,2590,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("cost_management","isolation_ressource",1200,2700,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("isolation_ressource","convertion",1050,2700,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("transfert_module","scalability",1500,2700,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("streams_module","dns_service",1450,1700,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("deployment_module","dhcp_service",1000,1700,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("dhcp_service","ftp",1830,2600,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("isolation_ressource","messaging",1895,1700,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("messaging","isolation_ressource",1030,2600,"TRACKING_MODULE_TYPE",Tuple.DOWN,AppEdge.MODULE);
        application.addAppEdge("scalability","web_service",1000,2600,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("dns_service","telnet",1560,2600,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("dns-service","ssh",946,2600,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("ssh","telnet",1000,2000,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);


     /*
         * Defining application loops to monitor the latency of.
         * Here, we add only one loop for monitoring : EEG(sensor) -> Client -> Concentration Calculator -> Client -> DISPLAY (actuator)
         */
        final AppLoop loop1 = new AppLoop(new ArrayList<String>(){{add("EEG");add("client");add("concentration_calculator");
        add("client");add("DISPLAY");add("agent");add("monitor");add("motion_detector");add("central");add("object_tracker");
        add("camera");add("ptz_control");add("driver_module");add("packet_module");add("mouse_control");add("backbone");
        add("network_control");add("image_processing");add("compressing");add(("data_storage"));add("tracking_module");
        add("analytics_module");add("application-integration");add("monitoring");add("cost_management");
            add("transfert_module");}});
        List<AppLoop> loops = new ArrayList<AppLoop>(){{add(loop1);}};
        application.setLoops(loops);
        return application;

    }



public static Application createApplication14(String appId, int userId){
        Application application = Application.createApplication(appId, userId); // creates an empty application model (empty directed graph)
        /*
         * Adding modules (vertices) to the application model (directed graph)
         */
     application.addAppModule("client", 9,1500,2500,2000); // adding module Client to the application model
     application.addAppModule("concentration_calculator", 4,1250,2000,2500); // adding module Concentration Calculator to the application model
     application.addAppModule("connector", 8,1000,1500,2000); // adding module Connector to the application model
     application.addAppModule("agent",10,1800,1800,2500);
     application.addAppModule("monitor",8,1200,800,1200);
     application.addAppModule("motion_detector",8,1800,1000,1000);
     application.addAppModule("central",8,850,950,1000);
     application.addAppModule("object_tracker",8,900,1000,1000);
     application.addAppModule("camera",10,1500,4000,950);
     application.addAppModule("ptz_control",8,1200,1500,850);
     application.addAppModule("driver_module",9,1000,850,735);
     application.addAppModule("packet_module",10,1900,2850,735);
     application.addAppModule("mouse_control",9,1545,850,735);
     application.addAppModule("backbone",9,50,1850,735);
     application.addAppModule("network_control",9,1050,850,735);
     application.addAppModule("image_processing",9,1050,850,735);
     application.addAppModule("compressing",9,1050,850,735);
     application.addAppModule("tracking_module",8,1450,1500,1020);
     application.addAppModule("data_storage",8,1900,1000,1020);
     application.addAppModule("analytics_module",10,1450,100,1000);
     application.addAppModule("application_integration",10,1250,1100,1000);
     application.addAppModule("monitoring",9,1050,1000,1100);
     application.addAppModule("cost-management",10,1450,1000,1100);
     application.addAppModule("transfert_module",10,1050,1000,1050);
     application.addAppModule("streams_module",10,800,1000,1050);
     application.addAppModule("deployment_module",10,900,1000,1050);
     application.addAppModule("convertion",10,850,1000,1050);
     application.addAppModule("isolation_ressource",10,1405,1010,1250);
     application.addAppModule("scalability",10,890,1010,1250);
     application.addAppModule("dns_service",10,1450,1010,1250);
     application.addAppModule("dhcp_service",10,1010,1010,1250);
     application.addAppModule("messaging",9,1000,1050,1250);
     application.addAppModule("web_service",9,1100,1050,1250);
     application.addAppModule("telnet",9,1000,1050,1250);
     application.addAppModule("ssh",9,1780,1009,3800);
     application.addAppModule("ftp",9,1450,890,1580);
    application.addAppModule("http",9,1000,890,1180);
    application.addAppModule("https",9,1945,1890,1875);
    application.addAppModule("smtp",9,1800,1890,1800);

        /*
         * Connecting the application modules (vertices) in the application model (directed graph) with edges
         */
        application.addAppEdge("EEG", "client", 2500, 250, "EEG", Tuple.UP, AppEdge.SENSOR);
        application.addAppEdge("client", "concentration_calculator", 1500, 80, "_SENSOR", Tuple.UP, AppEdge.MODULE); // adding edge from Client to Concentration Calculator module carrying tuples of type _SENSOR
        application.addAppEdge("concentration_calculator", "connector", 100, 1200, 150, "PLAYER_GAME_STATE", Tuple.UP, AppEdge.MODULE); // adding periodic edge (period=1000ms) from Concentration Calculator to Connector module carrying tuples of type PLAYER_GAME_STATE
        application.addAppEdge("concentration_calculator", "client", 1050, 800, "CONCENTRATION", Tuple.DOWN, AppEdge.MODULE);  // adding edge from Concentration Calculator to Client module carrying tuples of type CONCENTRATION
        application.addAppEdge("connector", "client", 100, 1250, 1000, "GLOBAL_GAME_STATE", Tuple.DOWN, AppEdge.MODULE); // adding periodic edge (period=1000ms) from Connector to Client module carrying tuples of type GLOBAL_GAME_STATE
        application.addAppEdge("client", "DISPLAY", 950, 500, "SELF_STATE_UPDATE", Tuple.DOWN, AppEdge.ACTUATOR);  // adding edge from Client module to Display (actuator) carrying tuples of type SELF_STATE_UPDATE
        application.addAppEdge("client", "DISPLAY", 1100, 600, "GLOBAL_STATE_UPDATE", Tuple.DOWN, AppEdge.ACTUATOR);  // adding edge from Client module to Display (actuator) carrying tuples of type GLOBAL_STATE_UPDATE
        application.addAppEdge("concentration_calculator","agent",2000,1100,200,"AGENT_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("concentration_calculator","monitor",1000,2000,250,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("client","motion_detector",900,250,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("motion_detector","monitor",900,250,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("monitor","central",1000,550,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("central","monitor",1050,400,"MONITOR_TYPE",Tuple.DOWN,AppEdge.MODULE);
        application.addAppEdge("agent","object_tracker",1700,400,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("object_tracker","camera",1900,123,"OBJECT_TRACKER_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("object_tracker","ptz_control",1800,423,"OBJECT_TRACKER_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("central","driver_module",1000,423,"CENTRAL_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("ptz_control","packet_module",1850,150,"PTZ_CONTROL_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("driver_module","packet_module",1450,2250,"DRIVER_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("camera","mouse_control",1450,2250,"CAMERA_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("camera","backbone",1000,1250,"CAMERA_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("backbone","network_control",1050,1050,"BACKBONE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("network_control","mouse_control",1300,1020,"NETWORK_CONTROL_TYPE",Tuple.DOWN,AppEdge.MODULE);
        application.addAppEdge("packet_module","compressing",1050,1050,"PACKET_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("packet_module","image_processing",1000,1050,"PACKET_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("image_processing","data_storage",1150,1000,"IMAGE_PROCESSING_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("compressing","data_storage",1250,1000,"COMPRESSING_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("network_control","tracking_module",1000,1005,"NETWORK_CONTROL_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("data_storage","analytics_module",1050,1000,"DATA_STORAGE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("tracking_module","analytics_module",1650,1121,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("analytics_module","application_integration",1100,1121,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("analytics_module","monitoring",700,1021,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("analytics_module","cost-management",1500,1000,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("analytics_module","transfert_module",1235,3050,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("application_integration","streams_module",896,1050,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("monitoring","deployment_module",952,2050,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("monitoring","convertion",758,2050,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("streams_module","deployment_module",3500,1000,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("cost_management","convertion",1170,2590,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("cost_management","isolation_ressource",1200,2700,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("isolation_ressource","convertion",1050,2700,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("transfert_module","scalability",1500,2700,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("streams_module","dns_service",1450,1700,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("deployment_module","dhcp_service",1000,1700,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("isolation_ressource","messaging",1895,1700,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("messaging","isolation_ressource",1030,2600,"TRACKING_MODULE_TYPE",Tuple.DOWN,AppEdge.MODULE);
        application.addAppEdge("scalability","web_service",1000,2600,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("dns_service","telnet",1560,2600,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
       application.addAppEdge("dns-service","ssh",946,2600,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
       application.addAppEdge("dhcp_service","ftp",1830,2600,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
       application.addAppEdge("ssh","telnet",1000,2000,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
    application.addAppEdge("messaging","http",1000,2000,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
    application.addAppEdge("https","telnet",1000,2000,"TRACKING_MODULE_TYPE",Tuple.DOWN,AppEdge.MODULE);
    application.addAppEdge("ssh","https",3500,2000,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
    application.addAppEdge("ssh","smtp",1000,2000,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
    application.addAppEdge("ftp","smtp",1500,2000,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);


    /*
         * Defining application loops to monitor the latency of.
         * Here, we add only one loop for monitoring : EEG(sensor) -> Client -> Concentration Calculator -> Client -> DISPLAY (actuator)
         */
        final AppLoop loop1 = new AppLoop(new ArrayList<String>(){{add("EEG");add("client");add("concentration_calculator");
        add("client");add("DISPLAY");add("agent");add("monitor");add("motion_detector");add("central");add("object_tracker");
        add("camera");add("ptz_control");add("driver_module");add("packet_module");add("mouse_control");add("backbone");
        add("network_control");add("image_processing");add("compressing");add(("data_storage"));add("tracking_module");
        add("analytics_module");add("application-integration");add("monitoring");add("cost_management");
            add("transfert_module");}});
        List<AppLoop> loops = new ArrayList<AppLoop>(){{add(loop1);}};
        application.setLoops(loops);
        return application;

    }

public static Application createApplication15(String appId, int userId){
        Application application = Application.createApplication(appId, userId); // creates an empty application model (empty directed graph)
        /*
         * Adding modules (vertices) to the application model (directed graph)
         */
     application.addAppModule("client", 9,1500,2500,2000); // adding module Client to the application model
     application.addAppModule("concentration_calculator", 4,1250,2000,2500); // adding module Concentration Calculator to the application model
     application.addAppModule("connector", 8,1000,1500,2000); // adding module Connector to the application model
     application.addAppModule("agent",10,1800,1800,2500);
     application.addAppModule("monitor",8,1200,800,1200);
     application.addAppModule("motion_detector",8,1800,1000,1000);
     application.addAppModule("central",8,850,950,1000);
     application.addAppModule("object_tracker",8,900,1000,1000);
     application.addAppModule("camera",10,1500,4000,950);
     application.addAppModule("ptz_control",8,1200,1500,850);
     application.addAppModule("driver_module",9,1000,850,735);
     application.addAppModule("packet_module",10,1900,2850,735);
     application.addAppModule("mouse_control",9,1545,850,735);
     application.addAppModule("backbone",9,50,1850,735);
     application.addAppModule("network_control",9,1050,850,735);
     application.addAppModule("image_processing",9,1050,850,735);
     application.addAppModule("compressing",9,1050,850,735);
     application.addAppModule("tracking_module",8,1450,1500,1020);
     application.addAppModule("data_storage",8,1900,1000,1020);
     application.addAppModule("analytics_module",10,1450,100,1000);
     application.addAppModule("application_integration",10,1250,1100,1000);
     application.addAppModule("monitoring",9,1050,1000,1100);
     application.addAppModule("cost-management",10,1450,1000,1100);
     application.addAppModule("transfert_module",10,1050,1000,1050);
     application.addAppModule("streams_module",10,800,1000,1050);
     application.addAppModule("deployment_module",10,900,1000,1050);
     application.addAppModule("convertion",10,850,1000,1050);
     application.addAppModule("isolation_ressource",10,1405,1010,1250);
     application.addAppModule("scalability",10,890,1010,1250);
     application.addAppModule("dns_service",10,1450,1010,1250);
     application.addAppModule("dhcp_service",10,1010,1010,1250);
     application.addAppModule("messaging",9,1000,1050,1250);
     application.addAppModule("web_service",9,1100,1050,1250);
     application.addAppModule("telnet",9,1000,1050,1250);
     application.addAppModule("ssh",9,1780,1009,3800);
     application.addAppModule("ftp",9,1450,890,1580);
    application.addAppModule("http",9,1000,890,1180);
    application.addAppModule("https",9,1945,1890,1875);
    application.addAppModule("smtp",9,1800,1890,1800);
    application.addAppModule("ipv4",5,1500,1890,500);
    application.addAppModule("ipv6",5,1000,1890,1500);
    application.addAppModule("icmp",5,1450,1890,1540);

        /*
         * Connecting the application modules (vertices) in the application model (directed graph) with edges
         */
        application.addAppEdge("EEG", "client", 2500, 250, "EEG", Tuple.UP, AppEdge.SENSOR);
        application.addAppEdge("client", "concentration_calculator", 1500, 80, "_SENSOR", Tuple.UP, AppEdge.MODULE); // adding edge from Client to Concentration Calculator module carrying tuples of type _SENSOR
        application.addAppEdge("concentration_calculator", "connector", 100, 1200, 150, "PLAYER_GAME_STATE", Tuple.UP, AppEdge.MODULE); // adding periodic edge (period=1000ms) from Concentration Calculator to Connector module carrying tuples of type PLAYER_GAME_STATE
        application.addAppEdge("concentration_calculator", "client", 1050, 800, "CONCENTRATION", Tuple.DOWN, AppEdge.MODULE);  // adding edge from Concentration Calculator to Client module carrying tuples of type CONCENTRATION
        application.addAppEdge("connector", "client", 100, 1250, 1000, "GLOBAL_GAME_STATE", Tuple.DOWN, AppEdge.MODULE); // adding periodic edge (period=1000ms) from Connector to Client module carrying tuples of type GLOBAL_GAME_STATE
        application.addAppEdge("client", "DISPLAY", 950, 500, "SELF_STATE_UPDATE", Tuple.DOWN, AppEdge.ACTUATOR);  // adding edge from Client module to Display (actuator) carrying tuples of type SELF_STATE_UPDATE
        application.addAppEdge("client", "DISPLAY", 1100, 600, "GLOBAL_STATE_UPDATE", Tuple.DOWN, AppEdge.ACTUATOR);  // adding edge from Client module to Display (actuator) carrying tuples of type GLOBAL_STATE_UPDATE
        application.addAppEdge("concentration_calculator","agent",2000,1100,200,"AGENT_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("concentration_calculator","monitor",1000,2000,250,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("client","motion_detector",900,250,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("motion_detector","monitor",900,250,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("monitor","central",1000,550,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("central","monitor",1050,400,"MONITOR_TYPE",Tuple.DOWN,AppEdge.MODULE);
        application.addAppEdge("agent","object_tracker",1700,400,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("object_tracker","camera",1900,123,"OBJECT_TRACKER_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("object_tracker","ptz_control",1800,423,"OBJECT_TRACKER_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("central","driver_module",1000,423,"CENTRAL_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("ptz_control","packet_module",1850,150,"PTZ_CONTROL_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("driver_module","packet_module",1450,2250,"DRIVER_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("camera","mouse_control",1450,2250,"CAMERA_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("camera","backbone",1000,1250,"CAMERA_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("backbone","network_control",1050,1050,"BACKBONE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("network_control","mouse_control",1300,1020,"NETWORK_CONTROL_TYPE",Tuple.DOWN,AppEdge.MODULE);
        application.addAppEdge("packet_module","compressing",1050,1050,"PACKET_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("packet_module","image_processing",1000,1050,"PACKET_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("image_processing","data_storage",1150,1000,"IMAGE_PROCESSING_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("compressing","data_storage",1250,1000,"COMPRESSING_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("network_control","tracking_module",1000,1005,"NETWORK_CONTROL_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("data_storage","analytics_module",1050,1000,"DATA_STORAGE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("tracking_module","analytics_module",1650,1121,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("analytics_module","application_integration",1100,1121,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("analytics_module","monitoring",700,1021,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("analytics_module","cost-management",1500,1000,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("analytics_module","transfert_module",1235,3050,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("application_integration","streams_module",896,1050,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("monitoring","deployment_module",952,2050,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("monitoring","convertion",758,2050,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("streams_module","deployment_module",3500,1000,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("cost_management","convertion",1170,2590,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("cost_management","isolation_ressource",1200,2700,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("isolation_ressource","convertion",1050,2700,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("transfert_module","scalability",1500,2700,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("streams_module","dns_service",1450,1700,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("deployment_module","dhcp_service",1000,1700,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("isolation_ressource","messaging",1895,1700,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("messaging","isolation_ressource",1030,2600,"TRACKING_MODULE_TYPE",Tuple.DOWN,AppEdge.MODULE);
        application.addAppEdge("scalability","web_service",1000,2600,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("dns_service","telnet",1560,2600,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
       application.addAppEdge("dns-service","ssh",946,2600,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
       application.addAppEdge("dhcp_service","ftp",1830,2600,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
       application.addAppEdge("ssh","telnet",1000,2000,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
    application.addAppEdge("messaging","http",1000,2000,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
    application.addAppEdge("https","telnet",1000,2000,"TRACKING_MODULE_TYPE",Tuple.DOWN,AppEdge.MODULE);
    application.addAppEdge("ssh","https",3500,2000,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
    application.addAppEdge("ssh","smtp",1000,2000,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
    application.addAppEdge("ftp","smtp",1500,2000,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
    application.addAppEdge("https","ipv6",780,2000,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
    application.addAppEdge("smtp","icmp",150,2000,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);


    /*
         * Defining application loops to monitor the latency of.
         * Here, we add only one loop for monitoring : EEG(sensor) -> Client -> Concentration Calculator -> Client -> DISPLAY (actuator)
         */
        final AppLoop loop1 = new AppLoop(new ArrayList<String>(){{add("EEG");add("client");add("concentration_calculator");
        add("client");add("DISPLAY");add("agent");add("monitor");add("motion_detector");add("central");add("object_tracker");
        add("camera");add("ptz_control");add("driver_module");add("packet_module");add("mouse_control");add("backbone");
        add("network_control");add("image_processing");add("compressing");add(("data_storage"));add("tracking_module");
        add("analytics_module");add("application-integration");add("monitoring");add("cost_management");
            add("transfert_module");}});
        List<AppLoop> loops = new ArrayList<AppLoop>(){{add(loop1);}};
        application.setLoops(loops);
        return application;

    }
public static Application createApplication16(String appId, int userId){
        Application application = Application.createApplication(appId, userId); // creates an empty application model (empty directed graph)
        /*
         * Adding modules (vertices) to the application model (directed graph)
         */
     application.addAppModule("client", 9,1500,2500,2000); // adding module Client to the application model
     application.addAppModule("concentration_calculator", 4,1250,2000,2500); // adding module Concentration Calculator to the application model
     application.addAppModule("connector", 8,1000,1500,2000); // adding module Connector to the application model
     application.addAppModule("agent",10,1800,1800,2500);
     application.addAppModule("monitor",8,1200,800,1200);
     application.addAppModule("motion_detector",8,1800,1000,1000);
     application.addAppModule("central",8,850,950,1000);
     application.addAppModule("object_tracker",8,900,1000,1000);
     application.addAppModule("camera",10,1500,4000,950);
     application.addAppModule("ptz_control",8,1200,1500,850);
     application.addAppModule("driver_module",9,1000,850,735);
     application.addAppModule("packet_module",10,1900,2850,735);
     application.addAppModule("mouse_control",9,1545,850,735);
     application.addAppModule("backbone",9,50,1850,735);
     application.addAppModule("network_control",9,1050,850,735);
     application.addAppModule("image_processing",9,1050,850,735);
     application.addAppModule("compressing",9,1050,850,735);
     application.addAppModule("tracking_module",8,1450,1500,1020);
     application.addAppModule("data_storage",8,1900,1000,1020);
     application.addAppModule("analytics_module",10,1450,100,1000);
     application.addAppModule("application_integration",10,1250,1100,1000);
     application.addAppModule("monitoring",9,1050,1000,1100);
     application.addAppModule("cost-management",10,1450,1000,1100);
     application.addAppModule("transfert_module",10,1050,1000,1050);
     application.addAppModule("streams_module",10,800,1000,1050);
     application.addAppModule("deployment_module",10,900,1000,1050);
     application.addAppModule("convertion",10,850,1000,1050);
     application.addAppModule("isolation_ressource",10,1405,1010,1250);
     application.addAppModule("scalability",10,890,1010,1250);
     application.addAppModule("dns_service",10,1450,1010,1250);
     application.addAppModule("dhcp_service",10,1010,1010,1250);
     application.addAppModule("messaging",9,1000,1050,1250);
     application.addAppModule("web_service",9,1100,1050,1250);
     application.addAppModule("telnet",9,1000,1050,1250);
     application.addAppModule("ssh",9,1780,1009,3800);
     application.addAppModule("ftp",9,1450,890,1580);
    application.addAppModule("http",9,1000,890,1180);
    application.addAppModule("https",9,1945,1890,1875);
    application.addAppModule("smtp",9,1800,1890,1800);
    application.addAppModule("ipv4",5,1500,1890,500);
    application.addAppModule("ipv6",5,1000,1890,1500);
    application.addAppModule("icmp",5,1450,1890,1540);
    application.addAppModule("ping",10,750,1890,1040);
    application.addAppModule("inap",10,750,1890,1000);
    application.addAppModule("ethernet",10,1000,1890,1500);

        /*
         * Connecting the application modules (vertices) in the application model (directed graph) with edges
         */
        application.addAppEdge("EEG", "client", 2500, 250, "EEG", Tuple.UP, AppEdge.SENSOR);
        application.addAppEdge("client", "concentration_calculator", 1500, 80, "_SENSOR", Tuple.UP, AppEdge.MODULE); // adding edge from Client to Concentration Calculator module carrying tuples of type _SENSOR
        application.addAppEdge("concentration_calculator", "connector", 100, 1200, 150, "PLAYER_GAME_STATE", Tuple.UP, AppEdge.MODULE); // adding periodic edge (period=1000ms) from Concentration Calculator to Connector module carrying tuples of type PLAYER_GAME_STATE
        application.addAppEdge("concentration_calculator", "client", 1050, 800, "CONCENTRATION", Tuple.DOWN, AppEdge.MODULE);  // adding edge from Concentration Calculator to Client module carrying tuples of type CONCENTRATION
        application.addAppEdge("connector", "client", 100, 1250, 1000, "GLOBAL_GAME_STATE", Tuple.DOWN, AppEdge.MODULE); // adding periodic edge (period=1000ms) from Connector to Client module carrying tuples of type GLOBAL_GAME_STATE
        application.addAppEdge("client", "DISPLAY", 950, 500, "SELF_STATE_UPDATE", Tuple.DOWN, AppEdge.ACTUATOR);  // adding edge from Client module to Display (actuator) carrying tuples of type SELF_STATE_UPDATE
        application.addAppEdge("client", "DISPLAY", 1100, 600, "GLOBAL_STATE_UPDATE", Tuple.DOWN, AppEdge.ACTUATOR);  // adding edge from Client module to Display (actuator) carrying tuples of type GLOBAL_STATE_UPDATE
        application.addAppEdge("concentration_calculator","agent",2000,1100,200,"AGENT_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("concentration_calculator","monitor",1000,2000,250,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("client","motion_detector",900,250,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("motion_detector","monitor",900,250,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("monitor","central",1000,550,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("central","monitor",1050,400,"MONITOR_TYPE",Tuple.DOWN,AppEdge.MODULE);
        application.addAppEdge("agent","object_tracker",1700,400,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("object_tracker","camera",1900,123,"OBJECT_TRACKER_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("object_tracker","ptz_control",1800,423,"OBJECT_TRACKER_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("central","driver_module",1000,423,"CENTRAL_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("ptz_control","packet_module",1850,150,"PTZ_CONTROL_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("driver_module","packet_module",1450,2250,"DRIVER_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("camera","mouse_control",1450,2250,"CAMERA_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("camera","backbone",1000,1250,"CAMERA_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("backbone","network_control",1050,1050,"BACKBONE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("network_control","mouse_control",1300,1020,"NETWORK_CONTROL_TYPE",Tuple.DOWN,AppEdge.MODULE);
        application.addAppEdge("packet_module","compressing",1050,1050,"PACKET_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("packet_module","image_processing",1000,1050,"PACKET_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("image_processing","data_storage",1150,1000,"IMAGE_PROCESSING_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("compressing","data_storage",1250,1000,"COMPRESSING_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("network_control","tracking_module",1000,1005,"NETWORK_CONTROL_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("data_storage","analytics_module",1050,1000,"DATA_STORAGE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("tracking_module","analytics_module",1650,1121,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("analytics_module","application_integration",1100,1121,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("analytics_module","monitoring",700,1021,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("analytics_module","cost-management",1500,1000,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("analytics_module","transfert_module",1235,3050,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("application_integration","streams_module",896,1050,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("monitoring","deployment_module",952,2050,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("monitoring","convertion",758,2050,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("streams_module","deployment_module",3500,1000,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("cost_management","convertion",1170,2590,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("cost_management","isolation_ressource",1200,2700,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("isolation_ressource","convertion",1050,2700,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("transfert_module","scalability",1500,2700,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("streams_module","dns_service",1450,1700,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("deployment_module","dhcp_service",1000,1700,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("isolation_ressource","messaging",1895,1700,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("messaging","isolation_ressource",1030,2600,"TRACKING_MODULE_TYPE",Tuple.DOWN,AppEdge.MODULE);
        application.addAppEdge("scalability","web_service",1000,2600,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("dns_service","telnet",1560,2600,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
       application.addAppEdge("dns-service","ssh",946,2600,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
       application.addAppEdge("dhcp_service","ftp",1830,2600,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
       application.addAppEdge("ssh","telnet",1000,2000,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
    application.addAppEdge("messaging","http",1000,2000,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
    application.addAppEdge("https","telnet",1000,2000,"TRACKING_MODULE_TYPE",Tuple.DOWN,AppEdge.MODULE);
    application.addAppEdge("ssh","https",3500,2000,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
    application.addAppEdge("ssh","smtp",1000,2000,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
    application.addAppEdge("ftp","smtp",1500,2000,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
    application.addAppEdge("https","ipv6",780,2000,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
    application.addAppEdge("smtp","icmp",150,2000,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
    application.addAppEdge("ipv4","ping",896,1900,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
    application.addAppEdge("ipv4","inap",3500,1900,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
    application.addAppEdge("ipv6","ethernet",975,1900,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
    application.addAppEdge("icmp","ethernet",1500,1900,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);


    /*
         * Defining application loops to monitor the latency of.
         * Here, we add only one loop for monitoring : EEG(sensor) -> Client -> Concentration Calculator -> Client -> DISPLAY (actuator)
         */
        final AppLoop loop1 = new AppLoop(new ArrayList<String>(){{add("EEG");add("client");add("concentration_calculator");
        add("client");add("DISPLAY");add("agent");add("monitor");add("motion_detector");add("central");add("object_tracker");
        add("camera");add("ptz_control");add("driver_module");add("packet_module");add("mouse_control");add("backbone");
        add("network_control");add("image_processing");add("compressing");add(("data_storage"));add("tracking_module");
        add("analytics_module");add("application-integration");add("monitoring");add("cost_management");
            add("transfert_module");}});
        List<AppLoop> loops = new ArrayList<AppLoop>(){{add(loop1);}};
        application.setLoops(loops);
        return application;

    }


    public static Application createApplication17(String appId, int userId){
        Application application = Application.createApplication(appId, userId); // creates an empty application model (empty directed graph)
        /*
         * Adding modules (vertices) to the application model (directed graph)
         */
        application.addAppModule("client", 9,1500,2500,2000); // adding module Client to the application model
        application.addAppModule("concentration_calculator", 4,1250,2000,2500); // adding module Concentration Calculator to the application model
        application.addAppModule("connector", 8,1000,1500,2000); // adding module Connector to the application model
        application.addAppModule("agent",10,1800,1800,2500);
        application.addAppModule("monitor",8,1200,800,1200);
        application.addAppModule("motion_detector",8,1800,1000,1000);
        application.addAppModule("central",8,850,950,1000);
        application.addAppModule("object_tracker",8,900,1000,1000);
        application.addAppModule("camera",10,1500,4000,950);
        application.addAppModule("ptz_control",8,1200,1500,850);
        application.addAppModule("driver_module",9,1000,850,735);
        application.addAppModule("packet_module",10,1900,2850,735);
        application.addAppModule("mouse_control",9,1545,850,735);
        application.addAppModule("backbone",9,50,1850,735);
        application.addAppModule("network_control",9,1050,850,735);
        application.addAppModule("image_processing",9,1050,850,735);
        application.addAppModule("compressing",9,1050,850,735);
        application.addAppModule("tracking_module",8,1450,1500,1020);
        application.addAppModule("data_storage",8,1900,1000,1020);
        application.addAppModule("analytics_module",10,1450,100,1000);
        application.addAppModule("application_integration",10,1250,1100,1000);
        application.addAppModule("monitoring",9,1050,1000,1100);
        application.addAppModule("cost-management",10,1450,1000,1100);
        application.addAppModule("transfert_module",10,1050,1000,1050);
        application.addAppModule("streams_module",10,800,1000,1050);
        application.addAppModule("deployment_module",10,900,1000,1050);
        application.addAppModule("convertion",10,850,1000,1050);
        application.addAppModule("isolation_ressource",10,1405,1010,1250);
        application.addAppModule("scalability",10,890,1010,1250);
        application.addAppModule("dns_service",10,1450,1010,1250);
        application.addAppModule("dhcp_service",10,1010,1010,1250);
        application.addAppModule("messaging",9,1000,1050,1250);
        application.addAppModule("web_service",9,1100,1050,1250);
        application.addAppModule("telnet",9,1000,1050,1250);
        application.addAppModule("ssh",9,1780,1009,3800);
        application.addAppModule("ftp",9,1450,890,1580);
        application.addAppModule("http",9,1000,890,1180);
        application.addAppModule("https",9,1945,1890,1875);
        application.addAppModule("smtp",9,1800,1890,1800);
        application.addAppModule("ipv4",5,1500,1890,500);
        application.addAppModule("ipv6",5,1000,1890,1500);
        application.addAppModule("icmp",5,1450,1890,1540);
        application.addAppModule("ping",10,750,1890,1040);
        application.addAppModule("inap",10,750,1890,1000);
        application.addAppModule("ethernet",10,1000,1890,1500);
        application.addAppModule("pdh",10,1456,1890,2500);
        application.addAppModule("sdh",10,1000,1890,2500);



        /*
         * Connecting the application modules (vertices) in the application model (directed graph) with edges
         */
        application.addAppEdge("EEG", "client", 2500, 250, "EEG", Tuple.UP, AppEdge.SENSOR);
        application.addAppEdge("client", "concentration_calculator", 1500, 80, "_SENSOR", Tuple.UP, AppEdge.MODULE); // adding edge from Client to Concentration Calculator module carrying tuples of type _SENSOR
        application.addAppEdge("concentration_calculator", "connector", 100, 1200, 150, "PLAYER_GAME_STATE", Tuple.UP, AppEdge.MODULE); // adding periodic edge (period=1000ms) from Concentration Calculator to Connector module carrying tuples of type PLAYER_GAME_STATE
        application.addAppEdge("concentration_calculator", "client", 1050, 800, "CONCENTRATION", Tuple.DOWN, AppEdge.MODULE);  // adding edge from Concentration Calculator to Client module carrying tuples of type CONCENTRATION
        application.addAppEdge("connector", "client", 100, 1250, 1000, "GLOBAL_GAME_STATE", Tuple.DOWN, AppEdge.MODULE); // adding periodic edge (period=1000ms) from Connector to Client module carrying tuples of type GLOBAL_GAME_STATE
        application.addAppEdge("client", "DISPLAY", 950, 500, "SELF_STATE_UPDATE", Tuple.DOWN, AppEdge.ACTUATOR);  // adding edge from Client module to Display (actuator) carrying tuples of type SELF_STATE_UPDATE
        application.addAppEdge("client", "DISPLAY", 1100, 600, "GLOBAL_STATE_UPDATE", Tuple.DOWN, AppEdge.ACTUATOR);  // adding edge from Client module to Display (actuator) carrying tuples of type GLOBAL_STATE_UPDATE
        application.addAppEdge("concentration_calculator","agent",2000,1100,200,"AGENT_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("concentration_calculator","monitor",1000,2000,250,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("client","motion_detector",900,250,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("motion_detector","monitor",900,250,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("monitor","central",1000,550,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("central","monitor",1050,400,"MONITOR_TYPE",Tuple.DOWN,AppEdge.MODULE);
        application.addAppEdge("agent","object_tracker",1700,400,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("object_tracker","camera",1900,123,"OBJECT_TRACKER_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("object_tracker","ptz_control",1800,423,"OBJECT_TRACKER_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("central","driver_module",1000,423,"CENTRAL_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("ptz_control","packet_module",1850,150,"PTZ_CONTROL_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("driver_module","packet_module",1450,2250,"DRIVER_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("camera","mouse_control",1450,2250,"CAMERA_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("camera","backbone",1000,1250,"CAMERA_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("backbone","network_control",1050,1050,"BACKBONE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("network_control","mouse_control",1300,1020,"NETWORK_CONTROL_TYPE",Tuple.DOWN,AppEdge.MODULE);
        application.addAppEdge("packet_module","compressing",1050,1050,"PACKET_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("packet_module","image_processing",1000,1050,"PACKET_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("image_processing","data_storage",1150,1000,"IMAGE_PROCESSING_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("compressing","data_storage",1250,1000,"COMPRESSING_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("network_control","tracking_module",1000,1005,"NETWORK_CONTROL_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("data_storage","analytics_module",1050,1000,"DATA_STORAGE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("tracking_module","analytics_module",1650,1121,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("analytics_module","application_integration",1100,1121,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("analytics_module","monitoring",700,1021,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("analytics_module","cost-management",1500,1000,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("analytics_module","transfert_module",1235,3050,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("application_integration","streams_module",896,1050,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("monitoring","deployment_module",952,2050,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("monitoring","convertion",758,2050,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("streams_module","deployment_module",3500,1000,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("cost_management","convertion",1170,2590,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("cost_management","isolation_ressource",1200,2700,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("isolation_ressource","convertion",1050,2700,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("transfert_module","scalability",1500,2700,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("streams_module","dns_service",1450,1700,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("deployment_module","dhcp_service",1000,1700,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("isolation_ressource","messaging",1895,1700,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("messaging","isolation_ressource",1030,2600,"TRACKING_MODULE_TYPE",Tuple.DOWN,AppEdge.MODULE);
        application.addAppEdge("scalability","web_service",1000,2600,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("dns_service","telnet",1560,2600,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("dns-service","ssh",946,2600,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("dhcp_service","ftp",1830,2600,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("ssh","telnet",1000,2000,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("messaging","http",1000,2000,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("https","telnet",1000,2000,"TRACKING_MODULE_TYPE",Tuple.DOWN,AppEdge.MODULE);
        application.addAppEdge("ssh","https",3500,2000,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("ssh","smtp",1000,2000,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("ftp","smtp",1500,2000,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("https","ipv6",780,2000,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("smtp","icmp",150,2000,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("ipv4","ping",896,1900,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("ipv4","inap",3500,1900,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("ipv6","ethernet",975,1900,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("icmp","ethernet",1500,1900,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("icmp","pdh",1500,1900,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("ping","pdh",1800,1900,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("ethernet","sdh",1500,1900,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("pdh","sdh",1000,1900,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("inap","sdh",1500,1900,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);

        /*
         * Defining application loops to monitor the latency of.
         * Here, we add only one loop for monitoring : EEG(sensor) -> Client -> Concentration Calculator -> Client -> DISPLAY (actuator)
         */
        final AppLoop loop1 = new AppLoop(new ArrayList<String>(){{add("EEG");add("client");add("concentration_calculator");
        add("client");add("DISPLAY");add("agent");add("monitor");add("motion_detector");add("central");add("object_tracker");
        add("camera");add("ptz_control");add("driver_module");add("packet_module");add("mouse_control");add("backbone");
        add("network_control");add("image_processing");add("compressing");add(("data_storage"));add("tracking_module");
        add("end_module");add("analytics_module");add("application-integration");add("monitoring");add("cost_management");
        add("transfert_module");add("streams_module");add("deployment_module");add("convertion");add("isolation_ressource");
        add("scalability");add("dns_service");add("dhcp-service");add("messaging");add("web-service");add(("telnet"));
        add("ssh");add("ftp");add("http");add("https");add("smtp");add("ipv4");add("ipv6");add(("icmp"));add("ping");
        add("inap");add("ethernet");add("pdh");add("sdh");}});
        List<AppLoop> loops = new ArrayList<AppLoop>(){{add(loop1);}};
        application.setLoops(loops);
        return application;
    }

    }


