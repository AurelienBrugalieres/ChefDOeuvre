package skynamiccontrol.model.mission;

import skynamiccontrol.model.Waypoint;

/**
 * Created by fabien on 13/02/17.
 */
public class Segment extends Instruction {
    private Waypoint waypoint1;
    private Waypoint waypoint2;
    private Double altitude = null; //if default value overrided. (default value = waypoint1.altitude)
}
