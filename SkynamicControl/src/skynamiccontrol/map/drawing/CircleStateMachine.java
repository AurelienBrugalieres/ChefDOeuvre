package skynamiccontrol.map.drawing;

import javafx.geometry.Point2D;
import javafx.scene.shape.Circle;
import skynamiccontrol.SkycEvent;
import skynamiccontrol.map.AircraftZoomLayer;
import skynamiccontrol.map.DrawingMapEvent;
import skynamiccontrol.model.Waypoint;

/**
 * Created by aurelien on 02/03/17.
 */
public class CircleStateMachine implements DrawingStateMachine {

    private CircleView ghost;
    private CircleView oldGhost;
    private Point2D center;
    private AircraftZoomLayer zoomLayer;

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
                CircleView oldGhost = ghost;
                ghost = null;
                gotoState(PossibleState.IDLE);
                paintInstruction();
//                skynamiccontrol.model.mission.Circle c = new skynamiccontrol.model.mission.Circle(new Waypoint());
                SkycEvent event = new SkycEvent(SkycEvent.CIRCLE_CREATED);
                zoomLayer.fireEvent(event);
                break;
        }
    }

    private void gotoState(PossibleState possibleState) {
        currentState = possibleState;
    }

    @Override
    public void handleEvent(DrawingMapEvent event) {
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
        }
    }

    @Override
    public void init(AircraftZoomLayer container) {
        CircleView oldGhost = ghost;
        zoomLayer = container;
//        ghost = null;
        gotoState(PossibleState.IDLE);
    }

    @Override
    public void paintInstruction() {
        if (ghost != null) {
//            double newX = ghost.getPosition().getX() * Math.pow(2, zoomLayer.getZoom());
//            double newY = ghost.getPosition().getY() * Math.pow(2, zoomLayer.getZoom());
            Point2D pt = zoomLayer.sceneToLocal(ghost.getPosition().getX(), ghost.getPosition().getY());
            ghost.paint(zoomLayer, pt);
        }
    }
}
