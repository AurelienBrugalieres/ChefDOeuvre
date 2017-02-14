package skynamiccontrol.view.status;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import skynamiccontrol.model.Aircraft;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Created by Elodie on 14/02/2017.
 */
public class StatusListController implements Initializable, Observer {

    public final static String AIRCRAFT_STATUS_PROPERTY_LIST = "aircraft_status_list";

    @FXML
    private VBox vbox;

    private Map<Aircraft, AnchorPane> status;

    private final PropertyChangeSupport support;

    public StatusListController() {
        status = new HashMap<>();
        support = new PropertyChangeSupport(this);

    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println(":)");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        support.addPropertyChangeListener(propertyName, listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        support.removePropertyChangeListener(propertyName, listener);
    }

    protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        support.firePropertyChange(propertyName, oldValue, newValue);
    }

    public void addView(Aircraft aircraft) {
        try {
            AnchorPane status_pane = FXMLLoader.load(getClass().getResource("Status.fxml"));
            Text altitude = (Text) status_pane.lookup("#altitude");
            Text speed = (Text) status_pane.lookup("#speed");
            Text status = (Text) status_pane.lookup("#status");
            ImageView battery = (ImageView) status_pane.lookup("#batterie_image");
            altitude.setText(String.valueOf(aircraft.getAltitude()));
            speed.setText(String.valueOf(aircraft.getSpeed()));
            status.setText(String.valueOf(aircraft.getCurrent_status()));

            double battery_level = aircraft.getBatteryLevel();

            if (battery_level < Aircraft.MAX_BATTERY_VOLTAGE / 5) {
                battery.setImage(new Image("src\\resources\\bitmaps\\bat5.png"));
            } else if (battery_level <= Aircraft.MAX_BATTERY_VOLTAGE * (2 / 5)) {
                battery.setImage(new Image("src\\resources\\bitmaps\\bat4.png"));
            } else if (battery_level <= Aircraft.MAX_BATTERY_VOLTAGE * (3 / 5)) {
                battery.setImage(new Image("src\\resources\\bitmaps\\bat3.png"));
            } else if (battery_level <= Aircraft.MAX_BATTERY_VOLTAGE * (4 / 5)) {
                battery.setImage(new Image("src\\resources\\bitmaps\\bat2.png"));
            } else {
                battery.setImage(new Image("src\\resources\\bitmaps\\bat1.png"));
            }
            this.status.put(aircraft, status_pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateView(Aircraft aircraft) {
        AnchorPane status_pane = status.get(aircraft);
        Text altitude = (Text) status_pane.lookup("#altitude");
        Text speed = (Text) status_pane.lookup("#speed");
        Text status = (Text) status_pane.lookup("#status");
        ImageView battery = (ImageView) status_pane.lookup("#batterie_image");
        altitude.setText(String.valueOf(aircraft.getAltitude()));
        speed.setText(String.valueOf(aircraft.getSpeed()));
        status.setText(String.valueOf(aircraft.getCurrent_status()));

        double battery_level = aircraft.getBatteryLevel();

        if (battery_level < Aircraft.MAX_BATTERY_VOLTAGE / 5) {
            battery.setImage(new Image("src\\resources\\bitmaps\\bat5.png"));
        } else if (battery_level <= Aircraft.MAX_BATTERY_VOLTAGE * (2 / 5)) {
            battery.setImage(new Image("src\\resources\\bitmaps\\bat4.png"));
        } else if (battery_level <= Aircraft.MAX_BATTERY_VOLTAGE * (3 / 5)) {
            battery.setImage(new Image("src\\resources\\bitmaps\\bat3.png"));
        } else if (battery_level <= Aircraft.MAX_BATTERY_VOLTAGE * (4 / 5)) {
            battery.setImage(new Image("src\\resources\\bitmaps\\bat2.png"));
        } else {
            battery.setImage(new Image("src\\resources\\bitmaps\\bat1.png"));
        }
        this.status.put(aircraft, status_pane);


    }
  /*  @Override
    public void initialize(URL location, ResourceBundle resources) {

    }*/
}
