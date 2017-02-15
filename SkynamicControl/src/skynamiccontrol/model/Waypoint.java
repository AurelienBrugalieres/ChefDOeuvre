package skynamiccontrol.model;

/**
 * Created by fabien on 13/02/17.
 */
public class Waypoint {
    private double latitude;
    private double longitude;
    private double east;
    private double north;
    private double altitude;
    private CoordinateSystem coordinateSystem;

    public Waypoint(double latEast, double lonNorth, double altitude, CoordinateSystem coordinateSystem) {
        this.coordinateSystem = coordinateSystem;
        this.altitude = altitude;
        switch (coordinateSystem) {
            case LOCAL:
                this.east = latEast;
                this.north = lonNorth;
                break;
            case LLA:
                this.latitude = latEast;
                this.longitude = lonNorth;
                break;
        }
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getEast() {
        return east;
    }

    public double getNorth() {
        return north;
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
