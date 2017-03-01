package skynamiccontrol.timeline;

/**
 *
 * Created by Lioz-MBPR on 14/02/2017.
 *
 **/
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Screen;
import skynamiccontrol.model.Aircraft;
import skynamiccontrol.model.GCSModel;
import skynamiccontrol.view.notifications.NotificationContainer;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class Timeline implements Initializable{

    @FXML
    TabPane tabPane;

    GCSModel model;

    public interface ChangeTabListener {
        void onChangeTab(Tab tab, Aircraft aircraft);
    }
    private ChangeTabListener listener = null;

    private Map<Aircraft, Tab> tabs = null;

    public Timeline() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tabPane.getStylesheets().add("/resources/css/timelineTab.css");
        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        tabPane.setLayoutX(visualBounds.getWidth());
        this.tabPane.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
                if (listener != null) {
                    Tab newTab = tabPane.getTabs().get(newValue.intValue());
                    Aircraft aircraft = null;
                    for (Map.Entry<Aircraft, Tab> entry : tabs.entrySet()) {
                        if (entry.getValue().equals(newTab)) {
                            aircraft = entry.getKey();
                        }
                    }
                    listener.onChangeTab(newTab, aircraft);

                }
            }
        });
        tabs = new HashMap<>();
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
        tabs.put(aircraft, tab);
        tabPane.setStyle(styleTab+",0)");
    }

    public void setModel(GCSModel model) {
        this.model = model;
    }

    public void selectAircraft(Aircraft aircraft) {
        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
        selectionModel.select(tabs.get(aircraft));
    }

    public void setOnChangeTabListener(ChangeTabListener changeTabListener) {
        this.listener = changeTabListener;
    }
}
