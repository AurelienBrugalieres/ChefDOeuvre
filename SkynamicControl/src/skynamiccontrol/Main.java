package skynamiccontrol;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import skynamiccontrol.communication.IvyManager;
import skynamiccontrol.map.Map;
import skynamiccontrol.map.GPSCoordinate;
import skynamiccontrol.model.Aircraft;
import skynamiccontrol.model.Constants;
import skynamiccontrol.model.GCSModel;
import skynamiccontrol.model.Waypoint;
import skynamiccontrol.model.mission.Circle;
import skynamiccontrol.model.mission.Instruction;


public class Main extends Application {
    private Controller controller;
    private GCSModel model;

    public static final boolean DEBUG = false;
    private FXMLLoader loader;
    double x,y;

    @Override
    public void start(Stage primaryStage) throws Exception{
        IvyManager.getInstance().initIvyBus("SkynamicControl", "SkynamicControl Ready", "127.255.255.255:2010");
        model = new GCSModel();

        //StackPane root = new StackPane();
        loader = new FXMLLoader(getClass().getResource("/skynamiccontrol/skynamicControlMain.fxml"));
        Parent root = loader.load();


        //Test map listener
        //Access to controller
        controller = loader.getController();

        Map map = new Map(20);


        primaryStage.getIcons().add(new Image("resources/bitmaps/quadrotorLogoDark.png"));
        primaryStage.widthProperty().addListener(((observable, oldValue, newValue) -> {
            double container_width = controller.getNotificationContainer().getWidth();
            controller.getNotificationContainer().setTranslateX((double)newValue - container_width);
            controller.adjustTimelineWidth(primaryStage.getWidth());
            map.setStageWidth(primaryStage.getWidth());
            map.pave();
        }));

        primaryStage.heightProperty().addListener(((observable, oldValue, newValue) -> {
            controller.adjustTimelineYPosition(primaryStage.getHeight());
            map.setStageHeight(primaryStage.getHeight());
            map.pave();
        }));

        controller.setModel(model);
        model.setStatusListContainer(controller.getStatusListContainer());
        model.setTimeline(controller.getTimelineController());
        model.setNotificationManager(controller.getNotificationContainer());
        model.setMap(map);

        Aircraft aircraft = Aircraft.loadAircraft(Constants.USER_DIR + "conf/aircrafts/microjet.conf");
        aircraft.setBatteryLevel(15.6);
        Aircraft aircraft2 = Aircraft.loadAircraft(Constants.USER_DIR + "conf/aircrafts/ardrone2.conf");
        aircraft2.setColor("#94B7EA");
        aircraft2.setBatteryLevel(13.1);
        Instruction instruction = new Circle(new Waypoint(43.47, 1.20,200,  Waypoint.CoordinateSystem.LLA),100);
        instruction.setInsertMode(Instruction.InsertMode.APPEND);
        instruction.setState(Instruction.State.SENT);

        Instruction instruction2 = new Circle(new Waypoint(43.47, 1.20,50,  Waypoint.CoordinateSystem.LLA),100);
        instruction2.setInsertMode(Instruction.InsertMode.APPEND);
        instruction2.setState(Instruction.State.ACKNOWLEDGED);

        Instruction instruction3 = new Circle(new Waypoint(43.47, 1.20,0,  Waypoint.CoordinateSystem.LLA),100);
        instruction3.setInsertMode(Instruction.InsertMode.APPEND);
        instruction3.setState(Instruction.State.ACKNOWLEDGED);

        aircraft.getMissionManager().addInstruction(instruction);
        aircraft.getMissionManager().addInstruction(instruction2);
        aircraft.getMissionManager().addInstruction(instruction3);
        aircraft2.getMissionManager().addInstruction(instruction);

        model.addAircraft(aircraft);
        model.addAircraft(aircraft2);

        if (DEBUG) {
            primaryStage.setMaximized(true);
        }
        map.pave();
        controller.setMap(map);
        primaryStage.setTitle("Skynamic Control");
        Scene scene = new Scene(root, 800, 500);

       // scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
       // primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setScene(scene);

        primaryStage.show();
        map.setZoomLevel(18);
        map.setCoordinates(new GPSCoordinate(43.462344, 1.273044));
        map.pave();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        IvyManager.getInstance().stop();
        for(Aircraft aircraft : model.getAircrafts()) {
            aircraft.getMissionManager().stop();
        }
    }

    public static void main(String[] args) {
        System.setProperty("java.net.useSystemProxies", "true");
        int i = 0;
        while (i < args.length) {
            if(args[i].equals("-u") || args[i].equals("--user-directory")) {
                Constants.USER_DIR = args[i+1];
                i+=2;
            } else if(args[i].equals("-p") || args[i].equals("--proxy")) {
                System.setProperty("http.proxyHost", args[i+1]);
                System.setProperty("http.proxyPort", args[i+2]);
                System.out.println("proxy : " + args[i+1] + ":" + args[i+2]);
                i+=3;
            } else {
                System.out.println("Usage : EXECUTABLE [-d|--user-directory <user directory>] [-p|--proxy <host> <port>]");
                System.exit(-1);
            }
        }


        Constants.loadConstants(Constants.USER_DIR + "conf/constants.conf");
        launch(args);
        ///Uncomment these two lines (and comment the others) to test in text mode
//        TextView textView = new TextView();
//        while (textView.mainLoop()){}
//        IvyManager.getInstance().stop();
    }
}
