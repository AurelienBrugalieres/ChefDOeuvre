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
    private List<Aircraft> aircrafts;
    private StatusManager statusManager;
    private TimelineManager timelineManager;
    private NotificationManager notificationManager;
    private MapManager mapManager;

    public GCSModel() {
        this.nb_aircraft = 0;
        this.aircrafts = new ArrayList<>();
        statusManager = new StatusManager(this);
        timelineManager = new TimelineManager(this);
        notificationManager = new NotificationManager(this);
        mapManager = new MapManager(this);
    }

    public List<Aircraft> getAircrafts() {
        return aircrafts;
    }

    public void addAircraft(Aircraft aircraft) {
        this.aircrafts.add(aircraft);
        statusManager.createView(aircraft);
        timelineManager.addAircraft(aircraft);
        mapManager.addAircraft(aircraft);
        notificationManager.createView(aircraft);

        this.setChanged();
        notifyObservers();
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

}
