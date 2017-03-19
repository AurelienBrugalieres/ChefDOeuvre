package skynamiccontrol.model.mission;

import skynamiccontrol.model.Waypoint;

/**
 * Created by fabien on 13/02/17.
 * Survey instruction, as defined in paparazzi messages
 */
public class Survey extends Instruction {
    private Waypoint wpStart;
    private Waypoint wpEnd;
    private Double altitude = null; //if default value overrided. (default value = wpStart.altitude)

    public Survey(Waypoint wpStart, Waypoint wpEnd) {
        this.wpStart = wpStart;
        this.wpEnd = wpEnd;
    }

    public Survey(double latEast1, double lonNorth1, double latEast2, double lonNorth2, double altitude, Waypoint.CoordinateSystem coordinateSystem) {
        wpStart = new Waypoint(latEast1, lonNorth1, altitude, coordinateSystem);
        wpEnd = new Waypoint(latEast2, lonNorth2, altitude, coordinateSystem);
    }

    public Waypoint getWpStart() {
        return wpStart;
    }

    public Waypoint getWpEnd() {
        return wpEnd;
    }

    public Double getAltitude() {
        if(altitude == null) {
            return wpStart.getAltitude();
        }
        return altitude;
    }
}
