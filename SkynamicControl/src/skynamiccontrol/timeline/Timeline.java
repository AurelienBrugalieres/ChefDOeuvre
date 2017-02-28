package skynamiccontrol.timeline;

/**
 *
 * Created by Lioz-MBPR on 14/02/2017.
 *
 **/
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Screen;
import skynamiccontrol.model.Aircraft;
import skynamiccontrol.model.GCSModel;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Timeline implements Initializable{

    @FXML
    TabPane tabPane;

    GCSModel model;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tabPane.getStylesheets().add("/resources/css/timelineTab.css");
        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        tabPane.setLayoutX(visualBounds.getWidth());
    }

    public void addAircraft(Aircraft aircraft) {
        String backgroundColor = "("+aircraft.getColor().getRed()+","+
                aircraft.getColor().getGreen()+","+
                aircraft.getColor().getBlue();
        String styleTab = "-fx-background-color: rgba"+backgroundColor;
        Tab tab = new Tab();
        tab.setText(aircraft.getName());
        tab.setStyle(styleTab+",0.7)");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/skynamiccontrol/timeline/MissionBlockUI.fxml"));
        try {
            Parent node = loader.load();
            MissionBlockController missionBlockController = loader.getController();
            //missionBlockController.setAircraft(aircraft);
            //missionBlockController.setModel(model);
            missionBlockController.init(aircraft);
            missionBlockController.setStyle(styleTab+",0.7)");
            tab.setContent(node);
            node.toFront();
            missionBlockController.updateAircraftMissionBlock();
        } catch (IOException e) {
            e.printStackTrace();
        }


        tabPane.getTabs().add(tab);
        tabPane.setStyle(styleTab+",0)");
    }

    public void setModel(GCSModel model) {
        this.model = model;
    }

}
