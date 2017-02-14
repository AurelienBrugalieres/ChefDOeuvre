package skynamiccontrol.Timeline;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.ScrollEvent;
import javafx.stage.Stage;
import skynamiccontrol.model.Aircraft;

/**
 * Created by Lioz-MBPR on 14/02/2017.
 */
public class MissionBlock extends Application{

/*    private Aircraft myAircraft;

    public MissionBlock(Aircraft myAircraft_){
        this.myAircraft = myAircraft_;
    }
*/

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("MissionBlockUI.fxml"));
        primaryStage.setTitle("MissionBlock Control");
        primaryStage.setScene(new Scene(root, 1080, 225));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


}
