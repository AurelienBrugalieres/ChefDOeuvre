package skynamiccontrol.map;

public class XYZCoordinate {
    private double x;
    private double y;
    private int zoom;

    public XYZCoordinate() {
        this(0,0, 1);
    }

    public XYZCoordinate(double x, double y, int zoom) {
        setCoordinate(x, y, zoom);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getZoom() {
        return zoom;
    }

    public void setZoom(int zoom) {
        this.zoom = zoom;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setCoordinate(double x, double y, int zoom) {
        this.x = x;
        this.y = y;
        this.zoom = zoom;
    }

    /**
     * you may divide by TILE_DIMENSION befor using this method.
     * @return
     */
    public GPSCoordinate toGPSCoordinate() {
        double zn = 1 << zoom;
        double lon = x * 360/zn - 180;
        double A = Math.exp(2*Math.PI * (0.5 - y / zn));
        double lat = Math.atan((Math.pow(A,2)-1) / (2*A));
        lat = lat * 180 / Math.PI;
        return new GPSCoordinate(lat, lon);
    }
}
