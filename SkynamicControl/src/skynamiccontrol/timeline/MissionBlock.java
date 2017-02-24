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
                                // Red Color is generic to each aircraft for aborted instructions
                                rect = new Rectangle(120,20,new javafx.scene.paint.Color(223,0,11,1));
                                setLayoutStackPane(sp,x,yi);
                                sp.getChildren().addAll(rect,instructionTxt);
                                break;
                            case CANCELED:
                                // Gray Color is generic to each aircraft for canceled instructions
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
                                // Gray Color is generic to each aircraft for finished instructions
                                rect = new Rectangle(120,20,new javafx.scene.paint.Color(191,191,191,1));
                                setLayoutStackPane(sp,x,yi);
                                sp.getChildren().addAll(rect,instructionTxt);
                                break;
                        }
                        x += 120;
                        pane.getChildren().add(sp);
                    }
                }
            }
            String backgroundColor = "("+aircraft.getColor().getRed()+","+
                    aircraft.getColor().getGreen()+","+
                    aircraft.getColor().getBlue();
            String styleTab = "-fx-background-color: rgba"+backgroundColor;
            pane.setStyle(styleTab+",0.7)");
        }
       /* if (aircraft.getName().equals("Microjet")) {
            // Adding Instructions on the timeline ( TEST SECTION )
            Rectangle rect = new Rectangle(120, 30, Color.BLUEVIOLET);
            rect.setStroke(Color.BLACK);
            rect.getStrokeDashArray().addAll(2d);
            Text t = new Text("GoToWP                 ");
            t.setStroke(Color.WHITE);
            StackPane s = new StackPane();
            setLayoutStackPane(s, Screen.getPrimary().getBounds().getWidth()-180, y - 60);
            s.getChildren().addAll(rect, t);
            String backgroundColor = "("+aircraft.getColor().getRed()+","+
                    aircraft.getColor().getGreen()+","+
                    aircraft.getColor().getBlue();
            String styleTab = "-fx-background-color: rgba"+backgroundColor;
            pane.setStyle(styleTab+",0.7)");
            pane.getChildren().add(s);
            pane.getChildren().add(new Rectangle(x + 50, y - (500 * 0.244), 50, 20));
        }*/
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
