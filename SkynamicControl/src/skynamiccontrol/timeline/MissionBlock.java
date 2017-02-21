package skynamiccontrol.timeline;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
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
        double x = 40;
        double y = 151;
        for (int i = 0; i < model.getAircrafts().size(); i++) {
            if (model.getAircrafts().get(i) == aircraft) {
                if (!aircraft.getMissionManager().getPendingInstructions().isEmpty()) {
                    for (int j = 0; j < aircraft.getMissionManager().getPendingInstructions().size(); j++) {
                        Rectangle rect;
                        double yi = y - (aircraft.getAltitude()*0.244);
                        switch(aircraft.getMissionManager().getPendingInstructions().get(i).getState()){
                            case NOT_SENT:
                                rect = new Rectangle(x,yi,40,20);
                                break;
                            case SENT:
                                rect = new Rectangle(x,yi,40,20);
                                break;
                            case ACKNOWLEDGED:
                                rect = new Rectangle(x,yi,40,20);
                                break;
                            case ABORTED:
                                rect = new Rectangle(x,yi,40,20);
                                break;
                            case CANCELED:
                                rect = new Rectangle(x,yi,40,20);
                                break;
                            case RUNNING:
                                rect = new Rectangle(x,yi,40,20);
                                break;
                            case DONE:
                                rect = new Rectangle(x,yi,40,20);
                                break;
                        }
                        x += 50;
                    }
                }
            }
        }

        pane.getChildren().add(new Rectangle(x,y, 50, 20));
        pane.getChildren().add(new Rectangle(x+50,y-(500*0.244),50,20));
    }


    public void getCoordinate(MouseEvent mouseEvent) {
        System.out.println("x : "+mouseEvent.getX() + "y : "+mouseEvent.getY());
    }
}
