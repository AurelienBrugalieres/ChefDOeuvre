package skynamiccontrol.model;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Elodie on 14/02/2017.
 */
public class GCSModel {
    private final int nb_aircraft;
    private List<Aircraft> aircrafts;
    private StatusManager statusManager;
  /*  private final Map<EventType, Boolean> eventAvailability;
    private final PropertyChangeSupport support;*/

    public GCSModel(int nb_aircraft) {
        this.nb_aircraft = nb_aircraft;
        this.aircrafts = new ArrayList<>();
        statusManager = new StatusManager(this);
    }

    public List<Aircraft> getAircrafts() {
        return aircrafts;
    }

    public void addAircraft(Aircraft aircraft) {
        this.aircrafts.add(aircraft);
        statusManager.createView(aircraft);
    }
}
