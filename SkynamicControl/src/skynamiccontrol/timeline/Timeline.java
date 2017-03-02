package skynamiccontrol.timeline;

/**
 *
 * Created by Lioz-MBPR on 14/02/2017.
 *
 **/
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import skynamiccontrol.FxUtils;
import skynamiccontrol.model.Aircraft;
import skynamiccontrol.model.GCSModel;

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
        Color color = Color.web(aircraft.getColor(), 0.7);
        String styleTab = FxUtils.getCssColor(color);
        Tab tab = new Tab();
        tab.setText(aircraft.getName());
        tab.setStyle(styleTab);

        TabContent tabContent = new TabContent(aircraft);
        String styleContent = FxUtils.getCssColor(color.deriveColor(0, 0.2, 1, 1));
        tabContent.setStyle(styleContent);
        tabContent.setContentBackground(styleContent);
        tab.setContent(tabContent);
        tabContent.tuneLayout();
        tabContent.updateContent();
        tabPane.getTabs().add(tab);

        tabs.put(aircraft, tab);
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
