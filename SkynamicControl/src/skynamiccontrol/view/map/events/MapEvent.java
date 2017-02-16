package skynamiccontrol.view.map.events;

/**
 * MapEvent class encapsulates the map event information
 * @author Aurelien Brugalieres
 */
public class MapEvent {
    private double latitude;
    private double longitude;

    public MapEvent() {
        this(0, 0);
    }

    public MapEvent(double lat, double lng) {
        this.latitude = lat;
        this.longitude = lng;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
