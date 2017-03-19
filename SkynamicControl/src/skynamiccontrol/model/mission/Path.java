package skynamiccontrol.model.mission;

import skynamiccontrol.model.Waypoint;

/**
 * Created by fabien on 13/02/17.
 * Path instruction, as defined in paparazzi. Work also for Segment instruction.
 */
public class Path extends Instruction {
    public static int NB_WAYPOINTS_MAX = 5;
    private Waypoint[] waypoints;
    private int nbWaypoints;
    private Double altitude = null; //if default value overrided. (default value = waypoint1.altitude)

    public Path() {
        this.waypoints = new Waypoint[NB_WAYPOINTS_MAX];
        nbWaypoints = 0;
    }

    public boolean addWaypoint(Waypoint waypoint) {
        if(nbWaypoints < NB_WAYPOINTS_MAX) {
            waypoints[nbWaypoints++] = waypoint;
            return true;
        }
        return false; //cannot add waypoint : max number reached
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public Double getAltitude() {
        if(altitude != null) {
            return altitude;
        }
        return waypoints[0].getAltitude();
    }

    public int getNbWaypoints() {
        return nbWaypoints;
    }

    public Waypoint getWaypoint(int i) {
        return waypoints[i];
    }
}
