package skynamiccontrol.model;

import skynamiccontrol.map.*;
import skynamiccontrol.map.Map;
import skynamiccontrol.timeline.Timeline;
import skynamiccontrol.view.notifications.NotificationContainer;
import skynamiccontrol.view.status.StatusListContainer;

import java.util.*;

/**
 * Created by Elodie on 14/02/2017.
 */
public class GCSModel extends Observable{
    private final int nb_aircraft;
    private java.util.Map<Integer, Aircraft> aircrafts;
    private int selectedAircraftId = -1;
    private StatusManager statusManager;
    private TimelineManager timelineManager;
    private NotificationManager notificationManager;
    private MapManager mapManager;

    public GCSModel() {
        this.nb_aircraft = 0;
        this.aircrafts = new HashMap<>();
        statusManager = new StatusManager(this);
        timelineManager = new TimelineManager(this);
        notificationManager = new NotificationManager(this);
        mapManager = new MapManager(this);
    }

    public List<Aircraft> getAircrafts() {
        return new LinkedList<Aircraft>(aircrafts.values());
    }

    public void addAircraft(Aircraft aircraft) {
        this.aircrafts.put(aircraft.getId(), aircraft);
        statusManager.createView(aircraft);
        timelineManager.addAircraft(aircraft);
        mapManager.addAircraft(aircraft);
        notificationManager.createView(aircraft);

        this.setChanged();
        notifyObservers();
    }

    private void setSelectedAircraft(Aircraft aircraft) {
        notificationManager.selectAircraft(aircraft);
        statusManager.selectAircraft(aircraft);
        timelineManager.selectAircraft(aircraft);
        mapManager.selectAircraft(aircraft);
    }

    public void setNotificationManager(NotificationContainer notificationContainer) {
        this.notificationManager.setNotificationContainer(notificationContainer);
    }
    public void setStatusListContainer(StatusListContainer statusListContainer) {
        this.statusManager.setStatusListContainer(statusListContainer);
    }

    public void setTimeline(Timeline timeline) {
        this.timelineManager.setTimeline(timeline);
    }

    public void setMap(Map map) {
        mapManager.setMap(map);
    }

    public void selectAircraft(Aircraft aircraft) {
        if (this.selectedAircraftId != aircraft.getId() && aircrafts.containsKey(aircraft.getId())) {
            this.selectedAircraftId = aircraft.getId();
            setSelectedAircraft(aircrafts.get(selectedAircraftId));
        }
    }

    public Aircraft getSelectedAircraft() {
        if (aircrafts == null || aircrafts.isEmpty() || selectedAircraftId == -1 || !aircrafts.containsKey(selectedAircraftId)) {
            return null;
        }
        return aircrafts.get(selectedAircraftId);
    }

}
