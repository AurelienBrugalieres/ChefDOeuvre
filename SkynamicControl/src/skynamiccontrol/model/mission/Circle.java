package skynamiccontrol.model.mission;

import skynamiccontrol.model.Waypoint;

/**
 * Created by fabien on 13/02/17.
 */
public class Circle extends Instruction {
    private Waypoint center;
    private int radius;
    private Double altitude = null; //if default value overrided. (default value = waypoint1.altitude)

    public Waypoint getCenter() {
        return center;
    }

    public int getRadius() {
        return radius;
    }

    public Double getAltitude() {
        if(altitude == null) {
            return center.getAltitude();
        } else {
            return altitude;
        }
    }
}
