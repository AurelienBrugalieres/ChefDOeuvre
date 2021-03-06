package skynamiccontrol.map.drawing;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import skynamiccontrol.SkycEvent;
import skynamiccontrol.map.*;
import skynamiccontrol.model.Aircraft;
import skynamiccontrol.model.Waypoint;

/**
 * Created by aurelien on 02/03/17.
 */
public class CircleStateMachine implements DrawingStateMachine {

    private CircleView ghost;
    private CircleView oldGhost;
    private Point2D center;
    private AircraftZoomLayer zoomLayer;
    private Aircraft aircraft;

    private enum PossibleState {
        IDLE, BEGIN, CIRCLE;
    }

    private PossibleState currentState;

    public CircleStateMachine() {
        ghost = null;
    }


    private void beginDraw(Point2D point) {
        switch (currentState) {
            case IDLE:
                gotoState(PossibleState.BEGIN);
                center = point;
                break;
            case BEGIN:
                break;
            case CIRCLE:
                break;
        }
    }

    private int computeDistance(Point2D center, Point2D point) {
        double res = (point.getX()-center.getX())*(point.getX()-center.getX());
        res += (point.getY()-center.getY())*(point.getY()-center.getY());
        return (int)Math.sqrt(res);
    }

    private void draw(Point2D point) {
        switch (currentState) {
            case IDLE:
                break;
            case BEGIN:
                oldGhost = ghost;
                ghost = new CircleView(null, computeDistance(center, point), center);
                gotoState(PossibleState.CIRCLE);
                paintInstruction();
                break;
            case CIRCLE:
                ghost.remove();
                ghost = new CircleView(null, computeDistance(center, point), center);
                gotoState(PossibleState.CIRCLE);
                paintInstruction();
                break;
        }
    }

    private void endDraw(Point2D point) {
        switch (currentState) {
            case IDLE:
                break;
            case BEGIN:
                gotoState(PossibleState.IDLE);
                break;
            case CIRCLE:
                Point2D finalPoint = zoomLayer.sceneToLocal(point);
                GPSCoordinate finalPointCoordinate = new XYZCoordinate(finalPoint.getX()/ BackMapLayer.TILE_DIMENSION, finalPoint.getY()/BackMapLayer.TILE_DIMENSION, zoomLayer.getZoom()).toGPSCoordinate();
                oldGhost = ghost;
                Point2D pt = zoomLayer.sceneToLocal(ghost.getPosition());
                GPSCoordinate gpsCoordinate = new XYZCoordinate(pt.getX()/ BackMapLayer.TILE_DIMENSION, pt.getY()/BackMapLayer.TILE_DIMENSION, zoomLayer.getZoom()).toGPSCoordinate();
                Waypoint wp = new Waypoint(gpsCoordinate.getLatitude(), gpsCoordinate.getLongitude(), 200, Waypoint.CoordinateSystem.LLA);
                double radius = gpsCoordinate.getDistance(finalPointCoordinate);
                skynamiccontrol.model.mission.Circle circle = new skynamiccontrol.model.mission.Circle(wp, radius);
                ghost = null;
                gotoState(PossibleState.IDLE);
                paintInstruction();

//                skynamiccontrol.model.mission.Circle c = new skynamiccontrol.model.mission.Circle(new Waypoint());
                SkycEvent event = new SkycEvent(SkycEvent.CIRCLE_CREATED, circle, aircraft);
                zoomLayer.fireEvent(event);
                break;
        }
    }

    private void cancelDraw() {
        System.out.println("Cancel");
        switch (currentState) {
            case IDLE:
                break;
            case BEGIN:
                gotoState(PossibleState.IDLE);
                break;
            case CIRCLE:
                oldGhost = ghost;
                ghost.remove();
                ghost = null;
                gotoState(PossibleState.IDLE);
                paintInstruction();
                break;
        }

    }

    private void gotoState(PossibleState possibleState) {
        currentState = possibleState;
    }

    @Override
    public void handleEvent(DrawingMapEvent event, Aircraft aircraft) {
        this.aircraft = aircraft;
        switch (event.getEventType()) {
            case BEGIN_DRAW:
                beginDraw(event.getPosition());
                break;
            case DRAW:
                draw(event.getPosition());
                break;
            case END_DRAW:
                endDraw(event.getPosition());
                break;
            case CANCEL_DRAW:
                cancelDraw();
        }
    }

    @Override
    public void init(AircraftZoomLayer container) {
        oldGhost = ghost;
        zoomLayer = container;
        gotoState(PossibleState.IDLE);
    }

    @Override
    public void paintInstruction() {
        if (ghost != null) {
            Point2D pt = zoomLayer.sceneToLocal(ghost.getPosition().getX(), ghost.getPosition().getY());
            if (aircraft != null)
                ghost.setColor(Color.web(aircraft.getColor()));
            ghost.paint(zoomLayer, pt);
        }
    }
}
