package skynamiccontrol.model;

/**
 * Created by fabien on 13/02/17.
 */
public class Waypoint {
    private final double latitude;
    private final double longitude;
    private final double east;
    private final double north;
    private final double altitude;
    private final CoordinateSystem coordinateSystem;

    public Waypoint(double latEast, double lonNorth, double altitude, CoordinateSystem coordinateSystem) {
        this.coordinateSystem = coordinateSystem;
        this.altitude = altitude;

        switch (coordinateSystem) {
            case LOCAL:
                this.east = latEast;
                this.north = lonNorth;
                this.latitude = 0;
                this.longitude = 0;
                break;
            case LLA:
                this.east = 0;
                this.north = 0;
                this.latitude = latEast;
                this.longitude = lonNorth;

                break;
            default:
                this.east = 0;
                this.north = 0;
                this.latitude = 0;
                this.longitude = 0;
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
