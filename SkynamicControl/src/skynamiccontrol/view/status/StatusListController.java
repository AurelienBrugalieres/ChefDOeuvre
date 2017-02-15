package skynamiccontrol.view.status;

import javafx.beans.*;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import skynamiccontrol.model.Aircraft;
import skynamiccontrol.model.Status;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.Observable;

/**
 * Created by Elodie on 14/02/2017.
 */
public class StatusListController implements Initializable, Observer {

    public final static String AIRCRAFT_STATUS_PROPERTY_LIST = "aircraft_status_list";
    private IntegerProperty nb_aircraft = new SimpleIntegerProperty(0);

    @FXML
    private VBox vbox;

    private Map<Aircraft, StatusController> status;

    private final PropertyChangeSupport support;

    public StatusListController(List<Aircraft> a) {
        System.out.println("constructor");
        status = new HashMap<>();
        support = new PropertyChangeSupport(this);
        for(Aircraft air : a) {
            status.put(air,null);

        }
        nb_aircraft.setValue(status.size());
        nb_aircraft.addListener(((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {

        }));
    }
    public StatusListController() {
       // System.out.println("constructor");
        status = new HashMap<>();
        support = new PropertyChangeSupport(this);
        nb_aircraft.addListener(((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {

        }));
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println(":)");
    }

    public void addAircraft(Aircraft air) {
        status.put(air,null);
        support.firePropertyChange(AIRCRAFT_STATUS_PROPERTY_LIST,null,status);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Set<Aircraft> keys = status.keySet();
        for (Aircraft aircraft : keys) {
            addView(aircraft);
            System.out.println(":)");
        }
       /* addView(new Aircraft(2, "machin", 99, 80, 50, Status.AUTO));

        addView(new Aircraft(2, "truc", 30, 30, 30, Status.AUTO));*/
        //System.out.println(":)");
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
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getClassLoader().getResource("skynamiccontrol/Status.fxml"));
            AnchorPane status_pane = fxmlLoader.load();
            StatusController statusController = fxmlLoader.getController();
            statusController.setView(aircraft);

            this.status.put(aircraft, statusController);

            vbox.getChildren().add(status_pane);
            VBox.setVgrow(status_pane, Priority.ALWAYS);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateView(Aircraft aircraft) {
        StatusController statusController = status.get(aircraft);
        statusController.update();
       /* Text altitude = (Text) status_pane.lookup("#altitude");
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
        */

    }
  /*  @Override
    public void initialize(URL location, ResourceBundle resources) {

    }*/
}
