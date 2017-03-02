package skynamiccontrol.map;

import javafx.event.*;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.util.EventObject;

/**
 * Created by aurelien on 25/02/17.
 */
public class DrawingMapEvent {
    private DrawingMapEventType eventType;
    private Point2D position;

    public DrawingMapEvent(DrawingMapEventType eventType) {
        this.eventType = eventType;
    }

    public DrawingMapEvent(DrawingMapEventType eventType,  Point2D position) {
        this.eventType = eventType;
        this.position = position;
    }

    public DrawingMapEventType getEventType() {
        return eventType;
    }

    public Point2D getPosition() {
        return position;
    }

    public void setEventType(DrawingMapEventType eventType) {
        this.eventType = eventType;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }
}
