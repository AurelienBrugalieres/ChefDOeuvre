package skynamiccontrol.model;

/**
 * Created by fabien on 13/02/17.
 */
public class Waypoint {
    private double latitude;
    private double longitude;
    private double altitude;
    private CoordinateSystem coordinateSystem;

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public CoordinateSystem getCoordinateSystem() {
        return coordinateSystem;
    }

    public enum CoordinateSystem {
        LOCAL,
        LLA
    }
}
