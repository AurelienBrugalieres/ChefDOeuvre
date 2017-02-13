package skynamiccontrol.model.mission;

import skynamiccontrol.model.Waypoint;

/**
 * Created by fabien on 13/02/17.
 */
public class Path extends Instruction {
    private Waypoint waypoint1;
    private Waypoint waypoint2;
    private Waypoint waypoint3;
    private Waypoint waypoint4;
    private Waypoint waypoint5;
    private Double altitude = null; //if default value overrided. (default value = waypoint1.altitude)
}
