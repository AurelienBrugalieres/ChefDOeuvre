package skynamiccontrol;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import skynamiccontrol.communication.IvyManager;
import skynamiccontrol.view.map.events.MapAdapter;
import skynamiccontrol.view.map.events.MapEvent;

public class Main extends Application {

    public static final boolean DEBUG = true;
    private FXMLLoader loader;

    @Override
    public void start(Stage primaryStage) throws Exception{
        IvyManager.getInstance().initIvyBus("SkynamicControl", "SkynamicControl Ready", "127.255.255.255:2010");
        loader = new FXMLLoader(getClass().getResource("skynamicControlMain.fxml"));
        Parent root = loader.load();

        //Test map listener
        //Access to controller
        Controller controller = loader.getController();
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
    }

    public static void main(String[] args) {
        launch(args);
        ///Uncomment these two lines (and comment the others) to test in text mode
//        TextView textView = new TextView();
//        while (textView.mainLoop()){}
//        IvyManager.getInstance().stop();
    }
}
