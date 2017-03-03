package skynamiccontrol.map;

import javafx.application.Platform;
import javafx.scene.layout.StackPane;
import skynamiccontrol.map.drawing.CircleStateMachine;
import skynamiccontrol.map.drawing.DrawingStateMachine;
import skynamiccontrol.map.drawing.InstructionView;
import skynamiccontrol.model.Aircraft;
import skynamiccontrol.view.palette.PaletteEvent;

import java.util.*;

/**
 * Created by fabien on 25/02/17.
 */
public class AircraftPane extends StackPane implements Observer {
    private Aircraft aircraft;
    private List<AircraftZoomLayer> aircraftZoomLayers;
    private List<InstructionView> instructionViewList;
    private int currentZoom;
    private final DrawingStateMachine DEFAULT_CIRCLE_STATE_MACHINE = new CircleStateMachine();
    private final DrawingStateMachine DEFAULT_GOTO_STATE_MACHINE = null;
    private final DrawingStateMachine DEFAULT_PATH_STATE_MACHINE = null;
    private DrawingStateMachine currentStateMachine = null;
    private java.util.Map<PossibleState, DrawingStateMachine> availableDrawingStateMachines;

    private enum PossibleState {
        IDLE, DRAW_CIRCLE, DRAW_PATH, DRAW_GO_TO, DRAW_WAYPOINT
    }
    private PossibleState currentState;

    public AircraftPane(Aircraft aircraft, int nbZoomLevels) {
        setupStateMachine();
        currentState = PossibleState.IDLE;
        aircraftZoomLayers = new LinkedList<>();
        instructionViewList = new LinkedList<>();
        aircraft.addObserver(this);
        this.aircraft = aircraft;
        for (int i = 0; i < nbZoomLevels; i++) {
            AircraftZoomLayer layer = new AircraftZoomLayer(i, aircraft.getColor());
            layer.setInstructionViewList(instructionViewList);
            this.aircraftZoomLayers.add(layer);
            this.getChildren().add(layer);
            layer.setTranslateX(0);
            layer.setTranslateY(0);
        }
    }

    private void setupStateMachine() {
        availableDrawingStateMachines = new EnumMap<>(PossibleState.class);
        availableDrawingStateMachines.put(PossibleState.DRAW_CIRCLE, DEFAULT_CIRCLE_STATE_MACHINE);
        availableDrawingStateMachines.put(PossibleState.DRAW_GO_TO, DEFAULT_GOTO_STATE_MACHINE);
        availableDrawingStateMachines.put(PossibleState.DRAW_PATH, DEFAULT_PATH_STATE_MACHINE);
    }

    private void goToState(PossibleState state) {
        currentState = state;
        currentStateMachine = availableDrawingStateMachines.get(currentState);
        if (currentStateMachine != null)
            currentStateMachine.init(aircraftZoomLayers.get(currentZoom));
    }

    public void changeZoom(int zoom, double scale) {
        aircraftZoomLayers.get(currentZoom).setVisible(false);
        this.currentZoom = zoom;
        aircraftZoomLayers.get(currentZoom).setAircraftPosition(new GPSCoordinate(aircraft.getLatitude(), aircraft.getLongitude()), aircraft.getHeading());
        aircraftZoomLayers.get(currentZoom).changeScale(scale);
        aircraftZoomLayers.get(zoom).setVisible(true);
    }

    @Override
    public void update(Observable observable, Object o) {
        if(!(observable instanceof Aircraft)) {
            return;
        }
        Aircraft aircraft = (Aircraft) observable;
        Platform.runLater(() -> {
            aircraftZoomLayers.get(currentZoom).setAircraftPosition(new GPSCoordinate(aircraft.getLatitude(), aircraft.getLongitude()), aircraft.getHeading());
//            for(AircraftZoomLayer aircraftZoomLayer : aircraftZoomLayers) {
//                aircraftZoomLayer.setAircraftPosition(new GPSCoordinate(aircraft.getLatitude(), aircraft.getLongitude()), aircraft.getHeading());
//            }
        });
    }


    public void handleEvent(DrawingMapEvent e) {
        switch (e.getEventType()) {
            case BEGIN_DRAW:
                break;
            case DRAW:
                break;
            case END_DRAW:
                break;
        }
        if (currentStateMachine != null) {
            currentStateMachine.handleEvent(e, aircraft);
        }
    }

    public void handleEvent(PaletteEvent e) {
        switch (e.getEventType()){
            case NO_ACTION:
                noDrawing();
                break;
            case WAYPOINT:
                drawWaypoint();
                break;
            case CIRCLE:
                drawCircle();
                break;
            case GOTO:
                drawGoTo();
                break;
            case PATH:
                drawPath();
                break;
        }
    }

    private void drawCircle() {
        switch (currentState) {
            case IDLE:
                goToState(PossibleState.DRAW_CIRCLE);
                break;
            case DRAW_CIRCLE:
                break;
            case DRAW_PATH:
                goToState(PossibleState.DRAW_CIRCLE);
                break;
            case DRAW_GO_TO:
                goToState(PossibleState.DRAW_CIRCLE);
                break;
            case DRAW_WAYPOINT:
                goToState(PossibleState.DRAW_CIRCLE);
                break;
        }
    }

    private void drawPath() {
        switch (currentState) {
            case IDLE:
                goToState(PossibleState.DRAW_CIRCLE);
                break;
            case DRAW_CIRCLE:
                goToState(PossibleState.DRAW_PATH);
                break;
            case DRAW_PATH:
                break;
            case DRAW_GO_TO:
                goToState(PossibleState.DRAW_PATH);
                break;
            case DRAW_WAYPOINT:
                goToState(PossibleState.DRAW_PATH);
                break;
        }
    }

    private void drawGoTo() {
        switch (currentState) {
            case IDLE:
                goToState(PossibleState.DRAW_GO_TO);
                break;
            case DRAW_CIRCLE:
                goToState(PossibleState.DRAW_GO_TO);
                break;
            case DRAW_PATH:
                goToState(PossibleState.DRAW_GO_TO);
                break;
            case DRAW_GO_TO:
                break;
            case DRAW_WAYPOINT:
                goToState(PossibleState.DRAW_GO_TO);
                break;
        }
    }

    private void drawWaypoint() {
        switch (currentState) {
            case IDLE:
                goToState(PossibleState.DRAW_WAYPOINT);
                break;
            case DRAW_CIRCLE:
                goToState(PossibleState.DRAW_WAYPOINT);
                break;
            case DRAW_PATH:
                goToState(PossibleState.DRAW_WAYPOINT);
                break;
            case DRAW_GO_TO:
                goToState(PossibleState.DRAW_WAYPOINT);
                break;
            case DRAW_WAYPOINT:
                break;
        }
    }

    private void noDrawing() {
        switch (currentState) {
            case IDLE:
                break;
            case DRAW_CIRCLE:
                goToState(PossibleState.IDLE);
                break;
            case DRAW_PATH:
                goToState(PossibleState.IDLE);
                break;
            case DRAW_GO_TO:
                goToState(PossibleState.IDLE);
                break;
            case DRAW_WAYPOINT:
                goToState(PossibleState.IDLE);
                break;
        }
    }
}
