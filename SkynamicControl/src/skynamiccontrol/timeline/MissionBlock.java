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
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
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
                        StackPane sp = new StackPane();
                        Text instructionTxt = new Text(aircraft.getMissionManager().getPendingInstructions().get(j).getName());
                        instructionTxt.setStroke(Color.WHITE);
                        switch(aircraft.getMissionManager().getPendingInstructions().get(i).getState()){
                            case NOT_SENT:
                                rect = new Rectangle(120,20,new javafx.scene.paint.Color(aircraft.getColor().getRed(),
                                        aircraft.getColor().getGreen(),
                                        aircraft.getColor().getBlue(),0.5));
                                rect.setStroke(Color.BLACK);
                                rect.getStrokeDashArray().addAll(2d);
                                setLayoutStackPane(sp,x,yi);
                                sp.getChildren().addAll(rect,instructionTxt);
                                break;
                            case SENT:
                                rect = new Rectangle(120,20,new javafx.scene.paint.Color(aircraft.getColor().getRed(),
                                        aircraft.getColor().getGreen(),
                                        aircraft.getColor().getBlue(),0.6));
                                setLayoutStackPane(sp,x,yi);
                                sp.getChildren().addAll(rect,instructionTxt);
                                break;
                            case ACKNOWLEDGED:
                                rect = new Rectangle(120,20,new javafx.scene.paint.Color(aircraft.getColor().getRed(),
                                        aircraft.getColor().getGreen(),
                                        aircraft.getColor().getBlue(),0.8));
                                setLayoutStackPane(sp,x,yi);
                                sp.getChildren().addAll(rect,instructionTxt);
                                break;
                            case ABORTED:
                                rect = new Rectangle(120,20,new javafx.scene.paint.Color(223,0,11,1));
                                setLayoutStackPane(sp,x,yi);
                                sp.getChildren().addAll(rect,instructionTxt);
                                break;
                            case CANCELED:
                                rect = new Rectangle(120,20,new javafx.scene.paint.Color(191,191,191,1));
                                setLayoutStackPane(sp,x,yi);
                                sp.getChildren().addAll(rect,instructionTxt);
                                break;
                            case RUNNING:
                                rect = new Rectangle(120,30,new javafx.scene.paint.Color(aircraft.getColor().getRed(),
                                        aircraft.getColor().getGreen(),
                                        aircraft.getColor().getBlue(),1));
                                setLayoutStackPane(sp,x,yi);
                                sp.getChildren().addAll(rect,instructionTxt);
                                break;
                            case DONE:
                                rect = new Rectangle(120,20,new javafx.scene.paint.Color(191,191,191,1));
                                setLayoutStackPane(sp,x,yi);
                                sp.getChildren().addAll(rect,instructionTxt);
                                break;
                        }
                        if ( x < 870 ) {
                            x += 120;
                        }
                        else {
                            x = 40;
                            break;
                        }

                        pane.getChildren().add(sp);
                    }
                }
            }
        }
        System.out.println(model.getAircrafts().get(0).getName());
        if (model.getAircrafts().get(0).getName().equals("Microjet")) {
            // Adding Instructions on the timeline ( TEST SECTION )
            Rectangle rect = new Rectangle(120, 30, Color.BLUEVIOLET);
            rect.setStroke(Color.BLACK);
            rect.getStrokeDashArray().addAll(2d);
            Text t = new Text("GoToWP                 ");
            t.setStroke(Color.WHITE);
            StackPane s = new StackPane();
            setLayoutStackPane(s, x + 870, y - 60);
            s.getChildren().addAll(rect, t);
            pane.getChildren().add(s);
            pane.getChildren().add(new Rectangle(x + 50, y - (500 * 0.244), 50, 20));
        }
    }


    public void getCoordinate(MouseEvent mouseEvent) {
        System.out.println("x : "+mouseEvent.getX() + "y : "+mouseEvent.getY());
    }

    public StackPane setLayoutStackPane(StackPane sp,double x ,double y ){
        sp.setLayoutX(x);
        sp.setLayoutY(y);
        return sp;
    }
}
