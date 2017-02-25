package skynamiccontrol.map;

import javafx.event.*;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.util.EventObject;

/**
 * Created by aurelien on 25/02/17.
 */
public class DrawingMapEvent {
    private int aircraftId;
    private DrawingMapEventType eventType;
    private MouseEvent mouseEvent;

    public DrawingMapEvent(DrawingMapEventType eventType) {
        this.eventType = eventType;
    }

    public DrawingMapEvent(DrawingMapEventType eventType, int aircraftId) {
        this.aircraftId = aircraftId;
        this.eventType = eventType;
    }

    public DrawingMapEvent(DrawingMapEventType eventType, int aircraftId,  MouseEvent mouseEvent) {
        this.aircraftId = aircraftId;
        this.eventType = eventType;
        this.mouseEvent = mouseEvent;
    }

    public DrawingMapEventType getEventType() {
        return eventType;
    }

    public int getAircraftId() {
        return aircraftId;
    }

    public MouseEvent getMouseEvent() {
        return mouseEvent;
    }

    public void setAircraftId(int aircraftId) {
        this.aircraftId = aircraftId;
    }

    public void setEventType(DrawingMapEventType eventType) {
        this.eventType = eventType;
    }

    public void setMouseEvent(MouseEvent mouseEvent) {
        this.mouseEvent = mouseEvent;
    }
}
