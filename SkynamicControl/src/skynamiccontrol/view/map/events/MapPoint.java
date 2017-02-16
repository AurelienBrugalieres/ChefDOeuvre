package skynamiccontrol.view.map.events;

/**
 * Created by aurelien on 16/02/17.
 */
public class MapPoint {
    private double latitude;
    private double longitude;

    public MapPoint() {
        this(0, 0);
    }

    public MapPoint(double lat, double lng) {
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
