package skynamiccontrol.model.mission;

import skynamiccontrol.model.Waypoint;

/**
 * Created by fabien on 13/02/17.
 */
public class GoToWP extends Instruction {
    private Waypoint waypoint;
    private Double altitude = null; //if default value overrided. (default value = waypoint1.altitude)

    public GoToWP(Waypoint waypoint) {
        this.waypoint = waypoint;
    }

    public Waypoint getWaypoint() {
        return waypoint;
    }

    public Double getAltitude() {
        if(altitude == null) {
            return waypoint.getAltitude();
        }
        return altitude;
    }
}
