package skynamiccontrol.Timeline;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import skynamiccontrol.model.Aircraft;
import skynamiccontrol.model.GCSModel;
import skynamiccontrol.model.mission.Instruction;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Lioz-MBPR on 14/02/2017.
 */
public class MissionBlock implements Initializable {

    private Aircraft myAircraft;
    @FXML
    Pane pane;
    private GCSModel model;

    public MissionBlock(Aircraft myAircraft_, GCSModel model_){
        this.myAircraft = myAircraft_;
        this.model = model_;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateAircraftMissionBlock(myAircraft);
    }

    public void updateAircraftMissionBlock(Aircraft aircraft) {
        for (int i = 0; i < model.getAircrafts().size(); i++) {
            if (model.getAircrafts().get(i) == aircraft) {
                /*if (!aircraft.getMissionManager().getPendingInstructions().isEmpty()) {
                    for (int j = 0; j < aircraft.getMissionManager().getPendingInstructions().size(); j++) {*/
                pane.getChildren().add(new Rectangle(50, 20, Color.BEIGE));
            }


            //System.out.println(aircraft.getMissionManager().getPendingInstructions().isEmpty());
        }
    }
}
