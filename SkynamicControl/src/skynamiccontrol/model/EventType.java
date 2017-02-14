package skynamiccontrol.model;

/**
 * Created by Elodie on 14/02/2017.
 */
public enum EventType {
    STATUS_CHANGE("status_change");

    private final String propertyName;

    private EventType(String propertyName) {
        this.propertyName = propertyName;
    }


    public String getPropertyName() {
        return propertyName;
    }
}
