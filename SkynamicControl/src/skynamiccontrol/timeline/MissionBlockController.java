package skynamiccontrol.timeline;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ScrollBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import skynamiccontrol.model.Aircraft;
import skynamiccontrol.model.GCSModel;
import skynamiccontrol.model.mission.Instruction;
import skynamiccontrol.model.mission.MissionManager;


import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

/**
 * Created by Lioz-MBPR on 14/02/2017.
 */
public class MissionBlockController implements Initializable, Observer {

    private Aircraft aircraft;
    //private GCSModel model;

    private Group group;

    @FXML
    AnchorPane pane;
    @FXML
    ScrollBar hScrollBar;
    @FXML
    Line line;

    public MissionBlockController() {
        group = new Group();
    }

    public void setStyle(String style) {
        pane.setStyle(style);
    }

    public void init(Aircraft aircraft) {
        this.aircraft = aircraft;
        aircraft.getMissionManager().addObserver(this);
        pane.getChildren().add(group);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        pane.setPrefWidth(visualBounds.getWidth()-58);
        line.setEndX(visualBounds.getWidth()-172);
    }

    public void updateAircraftMissionBlock() {
        double x = 40;
        double totalHeigth = 150;
        group.getChildren().clear();

        if (aircraft.getMissionManager().getFutureInstructions().isEmpty()) {
            return;
        }

        double altitudeMax = 0;
        for (Instruction instruction : aircraft.getMissionManager().getFutureInstructions()) {
            altitudeMax = Math.max(altitudeMax + 30, instruction.getAltitude());
        }

        for (Instruction instruction : aircraft.getMissionManager().getFutureInstructions()) {
            double yi = totalHeigth - totalHeigth*(instruction.getAltitude()/altitudeMax);
            //double yi = totalHeigth - (aircraft.getAltitude()*0.244);
            StackPaneInstruction sp = new StackPaneInstruction(instruction, aircraft);
            setLayoutStackPane(sp, x, yi);
            x += 120;
            pane.getChildren().add(sp);
        }
    }

    public StackPane setLayoutStackPane(StackPane sp,double x ,double y ){
        sp.setLayoutX(x);
        sp.setLayoutY(y);
        return sp;
    }

    @Override
    public void update(Observable observable, Object o) {
        System.out.println("Observers notified !");
        Platform.runLater(this::updateAircraftMissionBlock);
    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    public void setAircraft(Aircraft myAircraft) {
        this.aircraft = myAircraft;
    }

//    public GCSModel getModel() {
//        return model;
//    }
//
//    public void setModel(GCSModel model) {
//        this.model = model;
//    }
}
