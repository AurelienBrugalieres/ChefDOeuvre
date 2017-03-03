package skynamiccontrol.map;

public class GPSCoordinate {
    private double latitude;
    private double longitude;

    public GPSCoordinate() {
        this(0,0);
    }

    public GPSCoordinate(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setCoordinate(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public XYZCoordinate toXYCoordinates(int zoom) {
        double z = 1<<zoom;
        double tx = (getLongitude()+180)/360.0;
        double latrad = getLatitude() * Math.PI / 180.0;
        double ty = 0.5 - (0.5/Math.PI)*Math.log(Math.tan(latrad) + 1.0/Math.cos(latrad));
        double X = tx*z;
        double Y = ty*z;
        return new XYZCoordinate(X, Y, zoom);
    }

    public double getDistance(GPSCoordinate g2) {
        double R = 6378000;
        double angle = R * Math.acos(Math.sin(g2.getLatitude()*Math.PI/180)*Math.sin(latitude*Math.PI/180)+Math.cos(g2.getLongitude()*Math.PI/180-longitude*Math.PI/180)*Math.cos(g2.getLatitude()*Math.PI/180)*Math.cos(latitude*Math.PI/180));
        System.out.println(latitude + ", " + longitude + "  |  " + g2.getLatitude() + ", " + g2.getLongitude());
//        return angle * 636600;
        return angle;
    }
}
