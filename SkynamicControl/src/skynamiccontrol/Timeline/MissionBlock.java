package skynamiccontrol.Timeline;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import skynamiccontrol.model.Aircraft;
import skynamiccontrol.model.mission.Instruction;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Lioz-MBPR on 14/02/2017.
 */
public class MissionBlock implements Initializable {

    private Aircraft myAircraft;
    @FXML
    private Pane pane;
    public MissionBlock(Aircraft myAircraft_){
        this.myAircraft = myAircraft_;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void addInstruction( Aircraft aircraft, Instruction instruction){

    }
    public void removeInstruction( Aircraft aircraft, Instruction instruction){

    }
    public void updateInstruction(Aircraft aircraft , Instruction instruction){

    }

}
