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

       public static Application createApplication1(String appId, int userId){


        Application application = Application.createApplication(appId, userId); // creates an empty application model (empty directed graph)

        /*
         * Adding modules (vertices) to the application model (directed graph)
         */
        //application.addAppModule("client", 9,1500,2500,2000); // adding module Client to the application model
        //application.addAppModule("concentration_calculator", 4,1250,2000,2500); // adding module Concentration Calculator to the application model
        //application.addAppModule("connector", 8,1000,1500,2000); // adding module Connector to the application model
           application.addAppModule("client", 9,1000,2500,2000); // adding module Client to the application model
           application.addAppModule("concentration_calculator", 4,1000,2000,2500); // adding module Concentration Calculator to the application model
           application.addAppModule("connector", 8,1000,1000,2000); // adding module Connector to the application model

        /*
         * Connecting the application modules (vertices) in the application model (directed graph) with edges
      */
        application.addAppEdge("EEG", "client", 3500, 250, "EEG", Tuple.UP, AppEdge.SENSOR);
        application.addAppEdge("client", "concentration_calculator", 1000, 80, "_SENSOR", Tuple.UP, AppEdge.MODULE); // adding edge from Client to Concentration Calculator module carrying tuples of type _SENSOR
        application.addAppEdge("concentration_calculator", "connector", 100, 500, 150, "PLAYER_GAME_STATE", Tuple.UP, AppEdge.MODULE); // adding periodic edge (period=1000ms) from Concentration Calculator to Connector module carrying tuples of type PLAYER_GAME_STATE
        application.addAppEdge("concentration_calculator", "client", 850, 500, "CONCENTRATION", Tuple.DOWN, AppEdge.MODULE);  // adding edge from Concentration Calculator to Client module carrying tuples of type CONCENTRATION
        application.addAppEdge("connector", "client", 100, 900, 1000, "GLOBAL_GAME_STATE", Tuple.DOWN, AppEdge.MODULE); // adding periodic edge (period=1000ms) from Connector to Client module carrying tuples of type GLOBAL_GAME_STATE
        application.addAppEdge("client", "DISPLAY", 950, 700, "SELF_STATE_UPDATE", Tuple.DOWN, AppEdge.ACTUATOR);  // adding edge from Client module to Display (actuator) carrying tuples of type SELF_STATE_UPDATE
        application.addAppEdge("client", "DISPLAY", 100, 600, "GLOBAL_STATE_UPDATE", Tuple.DOWN, AppEdge.ACTUATOR);  // adding edge from Client module to Display (actuator) carrying tuples of type GLOBAL_STATE_UPDATE

        /*
         * Defining application loops to monitor the latency of.
         * Here, we add only one loop for monitoring : EEG(sensor) -> Client -> Concentration Calculator -> Client -> DISPLAY (actuator)
         */
        final AppLoop loop1 = new AppLoop(new ArrayList<String>(){{add("EEG");add("client");add("concentration_calculator");add("client");add("DISPLAY");}});
        List<AppLoop> loops = new ArrayList<AppLoop>(){{add(loop1);}};
        application.setLoops(loops);

        return application;
    }

    public static Application createApplication2(String appId, int userId){


        Application application = Application.createApplication(appId, userId); // creates an empty application model (empty directed graph)

        /*
         * Adding modules (vertices) to the application model (directed graph)
         */
        application.addAppModule("client", 9,1500,2500,2000); // adding module Client to the application model
        application.addAppModule("concentration_calculator", 4,1250,2000,2500); // adding module Concentration Calculator to the application model
        application.addAppModule("connector", 8,1000,1500,2000); // adding module Connector to the application model
        //ajouter pour Application 2
        application.addAppModule("agent",10,2000,1800,2500);
        application.addAppModule("monitor",8,1000,800,1200);

        /*
         * Connecting the application modules (vertices) in the application model (directed graph) with edges
         */
        application.addAppEdge("EEG", "client", 3500, 250, "EEG", Tuple.UP, AppEdge.SENSOR);
        application.addAppEdge("client", "concentration_calculator", 3000, 80, "_SENSOR", Tuple.UP, AppEdge.MODULE); // adding edge from Client to Concentration Calculator module carrying tuples of type _SENSOR
        application.addAppEdge("concentration_calculator", "connector", 100, 500, 150, "PLAYER_GAME_STATE", Tuple.UP, AppEdge.MODULE); // adding periodic edge (period=1000ms) from Concentration Calculator to Connector module carrying tuples of type PLAYER_GAME_STATE
        application.addAppEdge("concentration_calculator", "client", 14, 500, "CONCENTRATION", Tuple.DOWN, AppEdge.MODULE);  // adding edge from Concentration Calculator to Client module carrying tuples of type CONCENTRATION
        application.addAppEdge("connector", "client", 100, 28, 1000, "GLOBAL_GAME_STATE", Tuple.DOWN, AppEdge.MODULE); // adding periodic edge (period=1000ms) from Connector to Client module carrying tuples of type GLOBAL_GAME_STATE
        application.addAppEdge("client", "DISPLAY", 950, 500, "SELF_STATE_UPDATE", Tuple.DOWN, AppEdge.ACTUATOR);  // adding edge from Client module to Display (actuator) carrying tuples of type SELF_STATE_UPDATE
        application.addAppEdge("client", "DISPLAY", 100, 600, "GLOBAL_STATE_UPDATE", Tuple.DOWN, AppEdge.ACTUATOR);  // adding edge from Client module to Display (actuator) carrying tuples of type GLOBAL_STATE_UPDATE
        application.addAppEdge("concentration_calculator","agent",2000,100,200,"AGENT_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("concentration_calculator","monitor",1000,250,250,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        /*
         * Defining application loops to monitor the latency of.
         * Here, we add only one loop for monitoring : EEG(sensor) -> Client -> Concentration Calculator -> Client -> DISPLAY (actuator)
         */
        final AppLoop loop1 = new AppLoop(new ArrayList<String>(){{add("EEG");add("client");add("concentration_calculator");add("client");add("DISPLAY");add("agent");add("monitor");}});
        List<AppLoop> loops = new ArrayList<AppLoop>(){{add(loop1);}};
        application.setLoops(loops);

        return application;
    }
    public static Application createApplication3(String appId, int userId){

        Application application = Application.createApplication(appId, userId); // creates an empty application model (empty directed graph)

        /*
         * Adding modules (vertices) to the application model (directed graph)
         */
        application.addAppModule("client", 9,1500,2500,2000); // adding module Client to the application model
        application.addAppModule("concentration_calculator", 4,1250,2000,2500); // adding module Concentration Calculator to the application model
        application.addAppModule("connector", 8,1000,1500,2000); // adding module Connector to the application model
        application.addAppModule("agent",10,2000,1800,2500);
        application.addAppModule("monitor",8,1000,800,1200);
        application.addAppModule("motion_detector",8,800,1000,1000);



        /*
         * Connecting the application modules (vertices) in the application model (directed graph) with edges
         */
        application.addAppEdge("EEG", "client", 3500, 250, "EEG", Tuple.UP, AppEdge.SENSOR);
        application.addAppEdge("client", "concentration_calculator", 3000, 80, "_SENSOR", Tuple.UP, AppEdge.MODULE); // adding edge from Client to Concentration Calculator module carrying tuples of type _SENSOR
        application.addAppEdge("concentration_calculator", "connector", 100, 500, 150, "PLAYER_GAME_STATE", Tuple.UP, AppEdge.MODULE); // adding periodic edge (period=1000ms) from Concentration Calculator to Connector module carrying tuples of type PLAYER_GAME_STATE
        application.addAppEdge("concentration_calculator", "client", 14, 500, "CONCENTRATION", Tuple.DOWN, AppEdge.MODULE);  // adding edge from Concentration Calculator to Client module carrying tuples of type CONCENTRATION
        application.addAppEdge("connector", "client", 100, 28, 1000, "GLOBAL_GAME_STATE", Tuple.DOWN, AppEdge.MODULE); // adding periodic edge (period=1000ms) from Connector to Client module carrying tuples of type GLOBAL_GAME_STATE
        application.addAppEdge("client", "DISPLAY", 950, 500, "SELF_STATE_UPDATE", Tuple.DOWN, AppEdge.ACTUATOR);  // adding edge from Client module to Display (actuator) carrying tuples of type SELF_STATE_UPDATE
        application.addAppEdge("client", "DISPLAY", 100, 600, "GLOBAL_STATE_UPDATE", Tuple.DOWN, AppEdge.ACTUATOR);  // adding edge from Client module to Display (actuator) carrying tuples of type GLOBAL_STATE_UPDATE
        application.addAppEdge("concentration_calculator","agent",2000,100,200,"AGENT_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("concentration_calculator","monitor",1000,250,250,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("client","motion_detector",500,250,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("motion_detector","monitor",500,250,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
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
        application.addAppModule("client", 9,1500,2500,2000); // adding module Client to the application model
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
        application.addAppEdge("client", "concentration_calculator", 3000, 80, "_SENSOR", Tuple.UP, AppEdge.MODULE); // adding edge from Client to Concentration Calculator module carrying tuples of type _SENSOR
        application.addAppEdge("concentration_calculator", "connector", 100, 500, 150, "PLAYER_GAME_STATE", Tuple.UP, AppEdge.MODULE); // adding periodic edge (period=1000ms) from Concentration Calculator to Connector module carrying tuples of type PLAYER_GAME_STATE
        application.addAppEdge("concentration_calculator", "client", 14, 500, "CONCENTRATION", Tuple.DOWN, AppEdge.MODULE);  // adding edge from Concentration Calculator to Client module carrying tuples of type CONCENTRATION
        application.addAppEdge("connector", "client", 100, 28, 1000, "GLOBAL_GAME_STATE", Tuple.DOWN, AppEdge.MODULE); // adding periodic edge (period=1000ms) from Connector to Client module carrying tuples of type GLOBAL_GAME_STATE
        application.addAppEdge("client", "DISPLAY", 950, 500, "SELF_STATE_UPDATE", Tuple.DOWN, AppEdge.ACTUATOR);  // adding edge from Client module to Display (actuator) carrying tuples of type SELF_STATE_UPDATE
        application.addAppEdge("client", "DISPLAY", 100, 600, "GLOBAL_STATE_UPDATE", Tuple.DOWN, AppEdge.ACTUATOR);  // adding edge from Client module to Display (actuator) carrying tuples of type GLOBAL_STATE_UPDATE
        application.addAppEdge("concentration_calculator","agent",2000,100,200,"AGENT_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("concentration_calculator","monitor",1000,250,250,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("client","motion_detector",500,250,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("motion_detector","monitor",500,250,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("monitor","central",1000,550,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("central","monitor",700,400,"MONITOR_TYPE",Tuple.DOWN,AppEdge.MODULE);
        application.addAppEdge("agent","object_tracker",700,400,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);


        /*
         * Defining application loops to monitor the latency of.
         * Here, we add only one loop for monitoring : EEG(sensor) -> Client -> Concentration Calculator -> Client -> DISPLAY (actuator)
         */
        final AppLoop loop1 = new AppLoop(new ArrayList<String>(){{add("EEG");add("client");add("concentration_calculator");
        add("client");add("DISPLAY");add("agent");add("monitor");add("motion_detector");add("central");add("object_tracker");}});
        List<AppLoop> loops = new ArrayList<AppLoop>(){{add(loop1);}};
        application.setLoops(loops);
        return application;
    }
    public static Application createApplication5(String appId, int userId){


        Application application = Application.createApplication(appId, userId); // creates an empty application model (empty directed graph)

        /*
         * Adding modules (vertices) to the application model (directed graph)
         */
        application.addAppModule("client", 9,1500,2500,2000); // adding module Client to the application model
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
        application.addAppEdge("client", "concentration_calculator", 3000, 80, "_SENSOR", Tuple.UP, AppEdge.MODULE); // adding edge from Client to Concentration Calculator module carrying tuples of type _SENSOR
        application.addAppEdge("concentration_calculator", "connector", 100, 500, 150, "PLAYER_GAME_STATE", Tuple.UP, AppEdge.MODULE); // adding periodic edge (period=1000ms) from Concentration Calculator to Connector module carrying tuples of type PLAYER_GAME_STATE
        application.addAppEdge("concentration_calculator", "client", 14, 500, "CONCENTRATION", Tuple.DOWN, AppEdge.MODULE);  // adding edge from Concentration Calculator to Client module carrying tuples of type CONCENTRATION
        application.addAppEdge("connector", "client", 100, 28, 1000, "GLOBAL_GAME_STATE", Tuple.DOWN, AppEdge.MODULE); // adding periodic edge (period=1000ms) from Connector to Client module carrying tuples of type GLOBAL_GAME_STATE
        application.addAppEdge("client", "DISPLAY", 950, 500, "SELF_STATE_UPDATE", Tuple.DOWN, AppEdge.ACTUATOR);  // adding edge from Client module to Display (actuator) carrying tuples of type SELF_STATE_UPDATE
        application.addAppEdge("client", "DISPLAY", 100, 600, "GLOBAL_STATE_UPDATE", Tuple.DOWN, AppEdge.ACTUATOR);  // adding edge from Client module to Display (actuator) carrying tuples of type GLOBAL_STATE_UPDATE
        application.addAppEdge("concentration_calculator","agent",2000,100,200,"AGENT_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("concentration_calculator","monitor",1000,250,250,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("client","motion_detector",500,250,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("motion_detector","monitor",500,250,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("monitor","central",1000,550,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("central","monitor",700,400,"MONITOR_TYPE",Tuple.DOWN,AppEdge.MODULE);
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
        application.addAppModule("client", 9,1500,2500,2000); // adding module Client to the application model
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
        application.addAppModule("mouse_control",9,25,850,735);
        application.addAppModule("backbone",9,50,850,735);


        /*
         * Connecting the application modules (vertices) in the application model (directed graph) with edges
         */
        application.addAppEdge("EEG", "client", 3500, 250, "EEG", Tuple.UP, AppEdge.SENSOR);
        application.addAppEdge("client", "concentration_calculator", 3000, 80, "_SENSOR", Tuple.UP, AppEdge.MODULE); // adding edge from Client to Concentration Calculator module carrying tuples of type _SENSOR
        application.addAppEdge("concentration_calculator", "connector", 100, 500, 150, "PLAYER_GAME_STATE", Tuple.UP, AppEdge.MODULE); // adding periodic edge (period=1000ms) from Concentration Calculator to Connector module carrying tuples of type PLAYER_GAME_STATE
        application.addAppEdge("concentration_calculator", "client", 14, 500, "CONCENTRATION", Tuple.DOWN, AppEdge.MODULE);  // adding edge from Concentration Calculator to Client module carrying tuples of type CONCENTRATION
        application.addAppEdge("connector", "client", 100, 28, 1000, "GLOBAL_GAME_STATE", Tuple.DOWN, AppEdge.MODULE); // adding periodic edge (period=1000ms) from Connector to Client module carrying tuples of type GLOBAL_GAME_STATE
        application.addAppEdge("client", "DISPLAY", 950, 500, "SELF_STATE_UPDATE", Tuple.DOWN, AppEdge.ACTUATOR);  // adding edge from Client module to Display (actuator) carrying tuples of type SELF_STATE_UPDATE
        application.addAppEdge("client", "DISPLAY", 100, 600, "GLOBAL_STATE_UPDATE", Tuple.DOWN, AppEdge.ACTUATOR);  // adding edge from Client module to Display (actuator) carrying tuples of type GLOBAL_STATE_UPDATE
        application.addAppEdge("concentration_calculator","agent",2000,100,200,"AGENT_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("concentration_calculator","monitor",1000,250,250,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("client","motion_detector",500,250,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("motion_detector","monitor",500,250,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("monitor","central",1000,550,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("central","monitor",700,400,"MONITOR_TYPE",Tuple.DOWN,AppEdge.MODULE);
        application.addAppEdge("agent","object_tracker",700,400,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("object_tracker","camera",900,123,"OBJECT_TRACKER_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("object_tracker","ptz_control",800,423,"OBJECT_TRACKER_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("central","driver_module",700,423,"CENTRAL_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("ptz_control","packet_module",850,150,"PTZ_CONTROL_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("driver_module","packet_module",450,2250,"DRIVER_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("camera","mouse_control",450,2250,"CAMERA_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("camera","backbone",1450,1250,"CAMERA_TYPE",Tuple.UP,AppEdge.MODULE);
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
        application.addAppModule("client", 9,1500,2500,2000); // adding module Client to the application model
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
        application.addAppModule("mouse_control",9,25,850,735);
        application.addAppModule("backbone",9,50,850,735);
        application.addAppModule("network_control",9,50,850,735);
        application.addAppModule("image_processing",9,50,850,735);
        application.addAppModule("compressing",9,50,850,735);

        /*
         * Connecting the application modules (vertices) in the application model (directed graph) with edges
         */
        application.addAppEdge("EEG", "client", 3500, 250, "EEG", Tuple.UP, AppEdge.SENSOR);
        application.addAppEdge("client", "concentration_calculator", 3000, 80, "_SENSOR", Tuple.UP, AppEdge.MODULE); // adding edge from Client to Concentration Calculator module carrying tuples of type _SENSOR
        application.addAppEdge("concentration_calculator", "connector", 100, 500, 150, "PLAYER_GAME_STATE", Tuple.UP, AppEdge.MODULE); // adding periodic edge (period=1000ms) from Concentration Calculator to Connector module carrying tuples of type PLAYER_GAME_STATE
        application.addAppEdge("concentration_calculator", "client", 14, 500, "CONCENTRATION", Tuple.DOWN, AppEdge.MODULE);  // adding edge from Concentration Calculator to Client module carrying tuples of type CONCENTRATION
        application.addAppEdge("connector", "client", 100, 28, 1000, "GLOBAL_GAME_STATE", Tuple.DOWN, AppEdge.MODULE); // adding periodic edge (period=1000ms) from Connector to Client module carrying tuples of type GLOBAL_GAME_STATE
        application.addAppEdge("client", "DISPLAY", 950, 500, "SELF_STATE_UPDATE", Tuple.DOWN, AppEdge.ACTUATOR);  // adding edge from Client module to Display (actuator) carrying tuples of type SELF_STATE_UPDATE
        application.addAppEdge("client", "DISPLAY", 100, 600, "GLOBAL_STATE_UPDATE", Tuple.DOWN, AppEdge.ACTUATOR);  // adding edge from Client module to Display (actuator) carrying tuples of type GLOBAL_STATE_UPDATE
        application.addAppEdge("concentration_calculator","agent",2000,100,200,"AGENT_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("concentration_calculator","monitor",1000,250,250,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("client","motion_detector",500,250,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("motion_detector","monitor",500,250,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("monitor","central",1000,550,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("central","monitor",700,400,"MONITOR_TYPE",Tuple.DOWN,AppEdge.MODULE);
        application.addAppEdge("agent","object_tracker",700,400,"MONITOR_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("object_tracker","camera",900,123,"OBJECT_TRACKER_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("object_tracker","ptz_control",800,423,"OBJECT_TRACKER_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("central","driver_module",700,423,"CENTRAL_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("ptz_control","packet_module",850,150,"PTZ_CONTROL_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("driver_module","packet_module",450,2250,"DRIVER_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("camera","mouse_control",450,2250,"CAMERA_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("camera","backbone",1450,1250,"CAMERA_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("backbone","network_control",1050,1050,"BACKBONE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("network_control","mouse_control",1000,1020,"NETWORK_CONTROL_TYPE",Tuple.DOWN,AppEdge.MODULE);
        application.addAppEdge("packet_module","compressing",1850,1050,"PACKET_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("packet_module","image_processing",1850,1050,"PACKET_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);
        /*
         * Defining application loops to monitor the latency of.
         * Here, we add only one loop for monitoring : EEG(sensor) -> Client -> Concentration Calculator -> Client -> DISPLAY (actuator)
         */
        final AppLoop loop1 = new AppLoop(new ArrayList<String>(){{add("EEG");add("client");add("concentration_calculator");
        add("client");add("DISPLAY");add("agent");add("monitor");add("motion_detector");add("central");add("object_tracker");
        add("camera");add("ptz_control");add("driver_module");add("packet_module");add("mouse_control");add("backbone");
        add("network_control");add("image_processing");add("compressing");}});
        List<AppLoop> loops = new ArrayList<AppLoop>(){{add(loop1);}};
        application.setLoops(loops);
        return application;
    }


    public static Application createApplication8(String appId, int userId){
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
        application.addAppModule("tracking_module",8,1450,1500,1020);
        application.addAppModule("data_storage",8,1900,1000,1020);
        application.addAppModule("end_module",8,1450,1500,1020);

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
        application.addAppEdge("image_processing","data_storage",1150,1000,"IMAGE_PROCESSING_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("compressing","data_storage",1250,1000,"COMPRESSING_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("network_control","tracking_module",1000,1005,"NETWORK_CONTROL_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("data_storage","end_module",1050,1000,"DATA_STORAGE_TYPE",Tuple.UP,AppEdge.MODULE);
        application.addAppEdge("tracking_module","end_module",1650,1121,"TRACKING_MODULE_TYPE",Tuple.UP,AppEdge.MODULE);

        /*
         * Defining application loops to monitor the latency of.
         * Here, we add only one loop for monitoring : EEG(sensor) -> Client -> Concentration Calculator -> Client -> DISPLAY (actuator)
         */
        final AppLoop loop1 = new AppLoop(new ArrayList<String>(){{add("EEG");add("client");add("concentration_calculator");
        add("client");add("DISPLAY");add("agent");add("monitor");add("motion_detector");add("central");add("object_tracker");
        add("camera");add("ptz_control");add("driver_module");add("packet_module");add("mouse_control");add("backbone");
        add("network_control");add("image_processing");add("compressing");add(("data_storage"));add("tracking_module");
        add("end_module");}});
        List<AppLoop> loops = new ArrayList<AppLoop>(){{add(loop1);}};
        application.setLoops(loops);
        return application;
    }



    }


