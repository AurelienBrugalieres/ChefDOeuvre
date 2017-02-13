package skynamiccontrol.model;

/**
 * Created by fabien on 13/02/17.
 */
public class Waypoint {
    private double latitude;
    private double longitude;
    private double altitude;
    private CoordinateSystem coordinateSystem;

    public enum CoordinateSystem {
        LOCAL,
        LLA
    }
}
