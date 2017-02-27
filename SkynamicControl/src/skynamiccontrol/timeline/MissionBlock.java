package skynamiccontrol.timeline;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotResult;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import skynamiccontrol.model.Aircraft;
import skynamiccontrol.model.GCSModel;
import skynamiccontrol.model.mission.StackPaneInstruction;


import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Lioz-MBPR on 14/02/2017.
 */
public class MissionBlock implements Initializable {

    private Aircraft myAircraft;
    @FXML
    AnchorPane pane;
    @FXML
    ScrollBar hScrollBar;
    @FXML
    Line line;
    private GCSModel model;
    Screen screen = Screen.getPrimary();

    public MissionBlock(Aircraft myAircraft_, GCSModel model_){
        this.myAircraft = myAircraft_;
        this.model = model_;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        pane.setPrefWidth(visualBounds.getWidth()-58);
        line.setEndX(visualBounds.getWidth()-172);
    }

    public void updateAircraftMissionBlock(Aircraft aircraft) {
        double x = 40;
        double y = 150;
        for (int i = 0; i < model.getAircrafts().size(); i++) {
            if (model.getAircrafts().get(i) == aircraft) {
                if (!aircraft.getMissionManager().getPendingInstructions().isEmpty()) {
                    for (int j = 0; j < aircraft.getMissionManager().getPendingInstructions().size(); j++) {
                        Rectangle rect;
                        double yi = y - (aircraft.getAltitude()*0.244);
                        StackPaneInstruction sp = null;
                        Text instructionTxt = new Text(aircraft.getMissionManager().getPendingInstructions().get(j).getName());
                        instructionTxt.setStroke(Color.WHITE);
                        switch(aircraft.getMissionManager().getPendingInstructions().get(i).getState()){
                            case NOT_SENT:
                                sp = new StackPaneInstruction(x,yi,120,20,aircraft.getColor().getRed(),
                                        aircraft.getColor().getGreen(),
                                        aircraft.getColor().getBlue(),0.5,instructionTxt);
                                sp.getRect().setStroke(Color.BLACK);
                                sp.getRect().getStrokeDashArray().addAll(2d);
                                break;
                            case SENT:
                                sp = new StackPaneInstruction(x,yi,120,20,aircraft.getColor().getRed(),
                                        aircraft.getColor().getGreen(),
                                        aircraft.getColor().getBlue(),0.6,instructionTxt);
                                break;
                            case ACKNOWLEDGED:
                                sp = new StackPaneInstruction(x,yi,120,20,aircraft.getColor().getRed(),
                                        aircraft.getColor().getGreen(),
                                        aircraft.getColor().getBlue(),0.8,instructionTxt);
                                break;
                            case ABORTED:
                                // Red Color is generic to each aircraft for aborted instructions
                                sp = new StackPaneInstruction(x,yi,120,20,223,0,11,1,instructionTxt);
                                break;
                            case CANCELED:
                                // Gray Color is generic to each aircraft for canceled instructions
                                sp = new StackPaneInstruction(x,yi,120,20,191,191,191,1,instructionTxt);
                                break;
                            case RUNNING:
                                sp = new StackPaneInstruction(x,yi,120,30,aircraft.getColor().getRed(),
                                        aircraft.getColor().getGreen(),
                                        aircraft.getColor().getBlue(),1,instructionTxt);
                                break;
                            case DONE:
                                // Gray Color is generic to each aircraft for finished instructions
                                sp = new StackPaneInstruction(x,yi,120,20,191,191,191,1,instructionTxt);
                                break;
                        }
                        x += 120;
                        pane.getChildren().add(sp);
                    }
                }
            }
            // coloring background of the tabContent according to UAV's color
            String backgroundColor = "("+aircraft.getColor().getRed()+","+
                    aircraft.getColor().getGreen()+","+
                    aircraft.getColor().getBlue();
            String styleTab = "-fx-background-color: rgba"+backgroundColor;
            pane.setStyle(styleTab+",0.7)");
        }
    }

    public StackPane setLayoutStackPane(StackPane sp,double x ,double y ){
        sp.setLayoutX(x);
        sp.setLayoutY(y);
        return sp;
    }



}
