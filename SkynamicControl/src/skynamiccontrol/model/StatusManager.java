package skynamiccontrol.model;

import skynamiccontrol.view.status.StatusController;
import skynamiccontrol.view.status.StatusListController;
import static skynamiccontrol.model.EventType.values;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;

/**
 * Created by Elodie on 14/02/2017.
 */
public class StatusManager {


    private StatusListController statusListController;
    private List<Observer> observers;
    private GCSModel model;
    private final Map<EventType, Boolean> eventAvailability;
    private final PropertyChangeSupport support;

    public StatusManager(GCSModel modelgcs) {
        model = modelgcs;
        support = new PropertyChangeSupport(this);
        eventAvailability = new EnumMap<>(EventType.class);

        for (EventType eventType : values()) {
            eventAvailability.put(eventType, null);
        }
        statusListController.addPropertyChangeListener(Aircraft.AIRCRAFT_STATUS_PROPERTY,(e) -> {
            firePropertyChange(StatusListController.AIRCRAFT_STATUS_PROPERTY_LIST, null, model.getAircrafts());
        });
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
    public void updateView(Aircraft aircraft) {
        statusListController.updateView(aircraft);
    }

    public void createView(Aircraft aircraft) {
        statusListController.addView(aircraft);
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
