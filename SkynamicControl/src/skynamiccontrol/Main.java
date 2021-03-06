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
    private FXMLLoader loader;

    @Override
    public void start(Stage primaryStage) throws Exception{
        IvyManager.getInstance().initIvyBus("SkynamicControl", "SkynamicControl Ready", "127.255.255.255:2010");
        //IvyManager.getInstance().initIvyBus("SkynamicControl", "SkynamicControl Ready", "192.168.0.255:2010");
        model = new GCSModel();

        //load interface
        loader = new FXMLLoader(getClass().getResource("/skynamiccontrol/skynamicControlMain.fxml"));
        Parent root = loader.load();


        //Access to controller
        controller = loader.getController();

        //instanciate the map with 20 zoom levels
        Map map = new Map(20);


        primaryStage.getIcons().add(new Image("resources/bitmaps/quadrotorLogoDark.png"));

        //add listener on width change to adjust graphics
        primaryStage.widthProperty().addListener(((observable, oldValue, newValue) -> {
            double container_width = controller.getNotificationContainer().getWidth();
            controller.getNotificationContainer().setTranslateX((double)newValue - container_width);
            controller.adjustTimelineWidth(primaryStage.getWidth());
            map.setStageWidth(primaryStage.getWidth());
            map.pave();
        }));

        //add listener on height change to adjust graphics
        primaryStage.heightProperty().addListener(((observable, oldValue, newValue) -> {
            controller.adjustTimelineYPosition(primaryStage.getHeight());
            map.setStageHeight(primaryStage.getHeight());
            map.pave();
        }));

        //bind all components together
        controller.setModel(model);
        model.setStatusListContainer(controller.getStatusListContainer());
        model.setTimeline(controller.getTimelineController());
        model.setNotificationManager(controller.getNotificationContainer());
        model.setMap(map);

        //Adds 2 aircraft. Should not be done statically.
        Aircraft aircraft = Aircraft.loadAircraft(Constants.USER_DIR + "conf/aircrafts/microjet.conf");
        Aircraft aircraft2 = Aircraft.loadAircraft(Constants.USER_DIR + "conf/aircrafts/twinjet.conf");
        aircraft2.setColor("#7caeff");

        model.addAircraft(aircraft);
        model.addAircraft(aircraft2);
        //default selected aircraft
        model.selectAircraft(aircraft);

        //map.pave();           //sound useless, uncomment if the map is not displayed
        controller.setMap(map);
        primaryStage.setTitle("Skynamic Control");
        Scene scene = new Scene(root, 1200, 800);
        primaryStage.setScene(scene);

        primaryStage.show();

        //init map zoom and position
        map.setZoomLevel(18);
        map.setCoordinates(new GPSCoordinate(43.462344, 1.273044));
        //load and display tiles
        map.pave();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        //stop Ivy
        IvyManager.getInstance().stop();
        //stop mission manager threads
        for(Aircraft aircraft : model.getAircrafts()) {
            aircraft.getMissionManager().stop();
        }
    }

    public static void main(String[] args) {
        //use system proxies
        System.setProperty("java.net.useSystemProxies", "true");

        //parse args from command line to set proxy, or working directory (for configuration files and tiles directory)
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
                System.out.println("Usage : EXECUTABLE [-u|--user-directory <user directory>] [-p|--proxy <host> <port>]");
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
