package skynamiccontrol.view.palette;

import java.beans.PropertyChangeListener;

/**
 * Created by aurelien on 25/02/17.
 */
public enum PaletteEventType {
    NO_ACTION("noActionProperty"), WAYPOINT("waypointProperty"), CIRCLE("circleProperty"), GOTO("gotoProperty"), PATH("pathProperty");

    private String propertyName;

    private PaletteEventType(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyName() {
        return propertyName;
    }
}
