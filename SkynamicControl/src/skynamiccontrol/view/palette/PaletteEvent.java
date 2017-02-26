package skynamiccontrol.view.palette;

/**
 * Created by aurelien on 25/02/17.
 */
public class PaletteEvent {

    private PaletteEventType eventType;

    public PaletteEvent(PaletteEventType eventType) {
        this.eventType = eventType;
    }

    public PaletteEventType getEventType() {
        return eventType;
    }
}
