package skynamiccontrol;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import skynamiccontrol.communication.IvyManager;
import skynamiccontrol.model.Aircraft;
import skynamiccontrol.model.GCSModel;
import skynamiccontrol.model.Status;
import skynamiccontrol.textview.TextView;
import skynamiccontrol.view.map.events.MapListener;
import skynamiccontrol.view.map.events.MapPoint;

public class Main extends Application {

    public static final boolean DEBUG = true;
    private FXMLLoader loader;

    @Override
    public void start(Stage primaryStage) throws Exception{

        loader = new FXMLLoader(getClass().getResource("skynamicControlMain.fxml"));
        Parent root = loader.load();


        //Test map listener
        //Access to controller
        Controller controller = loader.getController();
        //Add map listener
        controller.setMapListener(new MapListener() {
            @Override
            public void onMapClickListener(MapPoint pt) {
                System.out.println("Test (lat="+pt.getLatitude()+"; lng="+pt.getLongitude()+")");
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


    public static void main(String[] args) {
        launch(args);
        ///Uncomment these two lines (and comment the others) to test in text mode
//        TextView textView = new TextView();
//        while (textView.mainLoop()){}
//        IvyManager.getInstance().stop();
    }
}
