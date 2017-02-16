package skynamiccontrol.view.map.events;

import netscape.javascript.JSObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * JavaBridge class allows the communication between Java and JavaScript API.
 */
public class JavaBridge {

    private MapListener mapListener = null;

    public JavaBridge() {
        this(null);
    }

    public JavaBridge(MapListener listener) {
        this.mapListener = listener;
    }

    public void setMapListener(MapListener mapListener) {
        this.mapListener = mapListener;
    }

    public void onMapClick(JSObject point) {
        Pattern locationPattern = Pattern.compile("\\(([0-9]+.?[0-9]*), *([0-9]+.?[0-9]*)\\)");
        Matcher m = locationPattern.matcher(point.toString());
        double lat = 0;
        double lng = 0;
        if (m.matches()) {
            lat = Double.parseDouble(m.group(1));
            lng = Double.parseDouble(m.group(2));
        }
        if (mapListener != null)
            mapListener.onMapClick(new MapEvent(lat, lng));
    }

    public void onMapDoubleClick(JSObject point) {
        Pattern locationPattern = Pattern.compile("\\(([0-9]+.?[0-9]*), *([0-9]+.?[0-9]*)\\)");
        Matcher m = locationPattern.matcher(point.toString());
        double lat = 0;
        double lng = 0;
        if (m.matches()) {
            lat = Double.parseDouble(m.group(1));
            lng = Double.parseDouble(m.group(2));
        }
        if (mapListener != null)
            mapListener.onMapDoubleClick(new MapEvent(lat, lng));
    }

    public void onMapRightClick(JSObject point) {
        Pattern locationPattern = Pattern.compile("\\(([0-9]+.?[0-9]*), *([0-9]+.?[0-9]*)\\)");
        Matcher m = locationPattern.matcher(point.toString());
        double lat = 0;
        double lng = 0;
        if (m.matches()) {
            lat = Double.parseDouble(m.group(1));
            lng = Double.parseDouble(m.group(2));
        }
        if (mapListener != null)
            mapListener.onMapRightClick(new MapEvent(lat, lng));
    }

    public void onMapDrag() {
        if (mapListener != null)
            mapListener.onMapDrag();
    }

    public void onMapDragStart() {
        if (mapListener != null)
            mapListener.onMapDragStart();
    }

    public void onMapDragEnd() {
        if (mapListener != null)
            mapListener.onMapDragEnd();
    }

    public void onMapTypeChanged(String type) {
        if (mapListener != null)
            mapListener.onMapTypeChanged(type);
    }

    public void onMapMouseMove(JSObject point) {
        Pattern locationPattern = Pattern.compile("\\(([0-9]+.?[0-9]*), *([0-9]+.?[0-9]*)\\)");
        Matcher m = locationPattern.matcher(point.toString());
        double lat = 0;
        double lng = 0;
        if (m.matches()) {
            lat = Double.parseDouble(m.group(1));
            lng = Double.parseDouble(m.group(2));
        }
        if (mapListener != null)
            mapListener.onMapMouseMove(new MapEvent(lat, lng));
    }

    public void onMapMouseOut(JSObject point) {
        Pattern locationPattern = Pattern.compile("\\(([0-9]+.?[0-9]*), *([0-9]+.?[0-9]*)\\)");
        Matcher m = locationPattern.matcher(point.toString());
        double lat = 0;
        double lng = 0;
        if (m.matches()) {
            lat = Double.parseDouble(m.group(1));
            lng = Double.parseDouble(m.group(2));
        }
        if (mapListener != null)
            mapListener.onMapMouseOut(new MapEvent(lat, lng));
    }

    public void onMapMouseOver(JSObject point) {
        Pattern locationPattern = Pattern.compile("\\(([0-9]+.?[0-9]*), *([0-9]+.?[0-9]*)\\)");
        Matcher m = locationPattern.matcher(point.toString());
        double lat = 0;
        double lng = 0;
        if (m.matches()) {
            lat = Double.parseDouble(m.group(1));
            lng = Double.parseDouble(m.group(2));
        }
        if (mapListener != null)
            mapListener.onMapMouseOver(new MapEvent(lat, lng));
    }

    public void onMapTilesLoaded() {
        if (mapListener != null)
            mapListener.onMapTilesLoaded();
    }

    public void onMapZoomChanged(String zoomStr) {
        int zoomValue = Integer.parseInt(zoomStr);
        if (mapListener != null)
            mapListener.onMapZoomChanged(zoomValue);
    }

    public void log(String message) {
        System.out.println(message);
    }
}
