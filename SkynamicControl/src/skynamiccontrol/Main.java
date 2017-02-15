package skynamiccontrol;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import skynamiccontrol.model.Aircraft;
import skynamiccontrol.model.GCSModel;
import skynamiccontrol.model.Status;
import skynamiccontrol.textview.TextView;

public class Main extends Application {

    private GCSModel model = new GCSModel(2);
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("skynamicControlMain.fxml"));


        /* test */
     //  Aircraft aircraft = new Aircraft(1,"microJet",80.0,102.0,30.0, Status.AUTO);
     //   model.addAircraft(aircraft);



        primaryStage.setTitle("Skynamic Control");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
        ///Uncomment these two lines (and comment the others) to test in text mode
        //TextView textView = new TextView();
        //while (textView.mainLoop()){}
    }
}
