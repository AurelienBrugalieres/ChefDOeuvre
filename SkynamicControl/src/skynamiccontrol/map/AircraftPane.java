package skynamiccontrol.map;

import javafx.geometry.Point2D;
import javafx.application.Platform;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import skynamiccontrol.map.drawing.CircleView;
import skynamiccontrol.map.drawing.InstructionView;
import skynamiccontrol.model.Aircraft;

import java.util.*;

/**
 * Created by fabien on 25/02/17.
 */
public class AircraftPane extends StackPane implements Observer {
    private Aircraft aircraft;
    private List<AircraftZoomLayer> aircraftZoomLayers;
    private List<InstructionView> instructionViewList;
    private int currentZoom;

    private enum PossibleState {
        IDLE, BEGIN_DRAW, DRAWING, END_DRAW
    }
    private PossibleState currentState;

    public AircraftPane(Aircraft aircraft, int nbZoomLevels) {
        currentState = PossibleState.IDLE;
        aircraftZoomLayers = new LinkedList<>();
        instructionViewList = new LinkedList<>();
        aircraft.addObserver(this);
        this.aircraft = aircraft;
        for (int i = 0; i < nbZoomLevels; i++) {
            AircraftZoomLayer layer = new AircraftZoomLayer(i);
            layer.setInstructionViewList(instructionViewList);
            this.aircraftZoomLayers.add(layer);
            this.getChildren().add(layer);
            layer.setTranslateX(0);
            layer.setTranslateY(0);
        }
    }

    private void goToState(PossibleState state) {
        currentState = state;
    }

    public void changeZoom(int zoom, double scale) {
        aircraftZoomLayers.get(zoom).setVisible(false);
        this.currentZoom = zoom;
        aircraftZoomLayers.get(zoom).setVisible(true);
        aircraftZoomLayers.get(currentZoom).changeScale(scale);
    }

    @Override
    public void update(Observable observable, Object o) {
        //if(observable instanceof Aircraft) {
        Aircraft aircraft = (Aircraft) observable;
        for(AircraftZoomLayer aircraftZoomLayer : aircraftZoomLayers) {
            Platform.runLater(() -> {
                aircraftZoomLayer.setAircraftPosition(new GPSCoordinate(aircraft.getLatitude(), aircraft.getLongitude()), aircraft.getHeading());
            });
        }
        //}
    }


    public void createCircle(double x, double y, int radius) {
        CircleView circleView = new CircleView(null, radius, new Point2D(x, y));
        circleView.setColor(Color.web(aircraft.getColor()));
        instructionViewList.add(circleView);
        for(AircraftZoomLayer aircraftZoomLayer : aircraftZoomLayers) {
            Platform.runLater(() -> {
                aircraftZoomLayer.repaint();
            });
        }
    }

    public void handleEvent(DrawingMapEvent e) {
        switch (e.getEventType()) {
            case DRAW_WAYPOINT:
                break;
            case DRAW_CIRCLE:
                createCircle(e.getPosition().getX(), e.getPosition().getY(), 50);
                break;
            case DRAW_GOTO:
                break;
            case DRAW_PATH:
                break;
        }
    }
}
