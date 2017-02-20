package skynamiccontrol;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import skynamiccontrol.communication.IvyManager;
import skynamiccontrol.model.Aircraft;
import skynamiccontrol.model.Constants;
import skynamiccontrol.model.GCSModel;
import skynamiccontrol.view.map.events.MapAdapter;
import skynamiccontrol.view.map.events.MapEvent;

import java.awt.Color;

public class Main extends Application {
    private Controller controller;
    private GCSModel model;

    public static final boolean DEBUG = true;
    private FXMLLoader loader;

    @Override
    public void start(Stage primaryStage) throws Exception{
        IvyManager.getInstance().initIvyBus("SkynamicControl", "SkynamicControl Ready", "127.255.255.255:2010");
        model = new GCSModel();

        loader = new FXMLLoader(getClass().getResource("skynamicControlMain.fxml"));
        Parent root = loader.load();

        //Test map listener
        //Access to controller
        controller = loader.getController();
        //Add map listener
        controller.setMapListener(new MapAdapter() {
            @Override
            public void onMapClick(MapEvent pt) {
                System.out.println("Test (lat="+pt.getLatitude()+"; lng="+pt.getLongitude()+")");
                // Add waypoint in model for example
            }

            @Override
            public void onMapZoomChanged(int zoomValue) {
                System.out.println("Zoom value = "+zoomValue);
            }
        });

        primaryStage.widthProperty().addListener(((observable, oldValue, newValue) -> {
            double container_width = controller.getNotificationContainer().getWidth();
            controller.getNotificationContainer().setTranslateX((double)newValue - container_width);
        }));

        controller.setModel(model);
        model.setStatusListContainer(controller.getStatusListContainer());
        model.setTimeline(controller.getTimelineController());
        model.setNotificationManager(controller.getNotificationContainer());

        Aircraft aircraft = Aircraft.loadAircraft(Constants.USER_DIR + "conf/aircrafts/microjet.conf");
        aircraft.setBatteryLevel(15.6);
        Aircraft aircraft2 = Aircraft.loadAircraft(Constants.USER_DIR + "conf/aircrafts/ardrone2.conf");
        aircraft2.setColor(Color.decode("#94B7EA"));
        aircraft2.setBatteryLevel(13.1);

        model.addAircraft(aircraft);
        model.addAircraft(aircraft2);

        /* test */
     //  Aircraft aircraft = new Aircraft(1,"microJet",80.0,102.0,30.0, Status.AUTO);
     //   model.addAircraft(aircraft);





        if (DEBUG) {
            primaryStage.setMaximized(true);
        }
        primaryStage.setTitle("Skynamic Control");
        Scene scene = new Scene(root, 300, 275);
        primaryStage.setScene(scene);
        primaryStage.show();
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
        int i = 0;
        while (i < args.length) {
            if(args[i].equals("-d") || args[i].equals("--user-directory")) {
                Constants.USER_DIR = args[i+1];
                i+=2;
            } else if(args[i].equals("-p") || args[i].equals("--proxy")) {
                System.setProperty("http.proxyHost", args[i+1]);
                System.setProperty("http.proxyPort", args[i+2]);
                i+=3;
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
