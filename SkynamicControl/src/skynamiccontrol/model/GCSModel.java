package skynamiccontrol.model;

import java.beans.PropertyChangeSupport;
import java.util.*;

/**
 * Created by Elodie on 14/02/2017.
 */
public class GCSModel implements Observer{
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
        aircraft.addPrivateObserver(this);
        statusManager.createView(aircraft);
    }

    @Override
    public void update(Observable o, Object arg) {
        statusManager.updateView((Aircraft)o);
    }
}
