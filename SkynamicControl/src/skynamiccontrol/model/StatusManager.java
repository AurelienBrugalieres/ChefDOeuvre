package skynamiccontrol.model;

import com.sun.deploy.security.WIExplorerBrowserAuthenticator14;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import skynamiccontrol.view.status.StatusController;
import skynamiccontrol.view.status.StatusListContainer;
import skynamiccontrol.view.status.StatusListController;
import static skynamiccontrol.model.EventType.values;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.*;

/**
 * Created by Elodie on 14/02/2017.
 */
public class StatusManager extends Observable{


   // private StatusListController statusListController;
    private StatusListContainer statusListContainer;
    private GCSModel model;

    public StatusManager(GCSModel model1, StatusListContainer s) {
        this.model = model1;
        statusListContainer = s;
    }


    //FXMLLoader fxmlLoader;
  /*  private final Map<EventType, Boolean> eventAvailability;
    private final PropertyChangeSupport support;

    public StatusManager(GCSModel modelgcs) {
        model = modelgcs;
        support = new PropertyChangeSupport(this);
        eventAvailability = new EnumMap<>(EventType.class);

        for (EventType eventType : values()) {
            eventAvailability.put(eventType, null);
        }
        fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getClassLoader().getResource("skynamiccontrol/StatusList.fxml"));
        try {
            AnchorPane pane = (AnchorPane)fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        statusListController = fxmlLoader.getController();

       statusListController.addPropertyChangeListener(Aircraft.AIRCRAFT_STATUS_PROPERTY,(e) -> {
            firePropertyChange(StatusListController.AIRCRAFT_STATUS_PROPERTY_LIST, null, model.getAircrafts());
        })

    }*/
/*
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
    */

    public void updateView(Aircraft aircraft) {

    }

    public void createView(Aircraft aircraft) {
        statusListContainer.addStatus(aircraft);


       /* statusListController = new StatusListController(model.getAircrafts());

        statusListController.addAircraft(aircraft);
        fxmlLoader.setController(statusListController);*/
       // statusListController.addView(aircraft);
    }


/*
    public final static String AIRCRAFT_STATUS_PROPERTY = "aircraft_status";
    private StatusListController statusListController;
    private List<Aircraft> aircraftList;
    private final Map<String, Boolean> eventAvailability;
    private final PropertyChangeSupport support;

    public StatusManager() {
        support = new PropertyChangeSupport(this);
        eventAvailability = new HashMap<>();
        eventAvailability.put(AIRCRAFT_STATUS_PROPERTY, null);
        firePropertyChange(AIRCRAFT_STATUS_PROPERTY, null, Collections.unmodifiableList(aircraftList));

    }

    private void fireEventAvailabilityChanged(String propertyName, boolean newAvailability) {
        Boolean oldAvailability = eventAvailability.get(propertyName);
        eventAvailability.put(propertyName, newAvailability);
        firePropertyChange(propertyName, oldAvailability, newAvailability);
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
    */
}
