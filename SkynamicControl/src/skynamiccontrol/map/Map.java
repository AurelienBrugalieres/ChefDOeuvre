package skynamiccontrol.map;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Popup;
import skynamiccontrol.SkycEvent;
import skynamiccontrol.model.Aircraft;
import skynamiccontrol.model.mission.Circle;
import skynamiccontrol.model.mission.GoToWP;
import skynamiccontrol.model.mission.Path;
import skynamiccontrol.model.mission.Survey;
import skynamiccontrol.view.forms.AbstractForm;
import skynamiccontrol.view.forms.FormCircleController;
import skynamiccontrol.view.forms.FormGoToController;
import skynamiccontrol.view.forms.FormPathController;
import skynamiccontrol.view.palette.PaletteEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by fabien on 25/02/17.
 */
public class Map extends StackPane{
    private int zoomLevelsNumber;
    private ArrayList<BackMapLayer> backMapLayers;
    private AircraftPane currentAircraftPane = null;
    private java.util.Map<Integer,AircraftPane> aircraftPanes;
    int currentZoom;
    double currentScaleFactor;
    double x, y;
    double width, height;

    public void selectAircraft(Aircraft aircraft) {
            switchToAircraftPane(aircraft);
    }

    private enum PossibleState {
        IDLE, DRAW_CIRCLE, DRAW_PATH, DRAW_GO_TO, DRAW_WAYPOINT
    }
    private PossibleState currentState;
    private DrawingMapEventType drawingEventType;

    public Map(int zoomLevelsNumber) {
        this.zoomLevelsNumber = zoomLevelsNumber;
        currentState = PossibleState.IDLE;
        drawingEventType = DrawingMapEventType.END_DRAW;
        this.backMapLayers = new ArrayList<>();
        aircraftPanes = new HashMap<>();
        for (int i = 0; i < zoomLevelsNumber; i++) {
            BackMapLayer layer = new BackMapLayer(i);
            this.backMapLayers.add(layer);
            this.getChildren().add(layer);
            layer.setTranslateX(0);
            layer.setTranslateY(0);
        }

        this.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            onMouseClicked(e);
        });

        this.addEventHandler(MouseEvent.MOUSE_MOVED, (e) -> {
            onMouseMoved(e);
        });

        this.addEventHandler(MouseEvent.MOUSE_PRESSED ,(e) -> {
            onMousePressed(e);
        });

        this.addEventHandler(MouseEvent.MOUSE_DRAGGED ,(e) -> {
            onMouseDragged(e);
        });

        this.addEventHandler(ScrollEvent.SCROLL, (e) -> {
            onScrollMap(e);
        });

        this.addEventHandler(MouseEvent.MOUSE_RELEASED ,(e) -> {
            onMouseReleased(e);
        });

        this.addEventHandler(SkycEvent.CIRCLE_CREATED, (e) -> {
            try {
                Circle circle = (Circle)e.getInstruction();
                Aircraft aircraft = e.getAircraft();
                Popup popup = new Popup();
                AbstractForm formController = null;
                //todo : bind to the right form.
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/skynamiccontrol/form_circle.fxml"));
                popup.getContent().add(loader.load());
                formController = loader.getController();
                ((FormCircleController)formController).setCircle(circle, true, aircraft);

                formController.setPopup(popup);
                //TODO : set better position.
                popup.show(this, 20, this.getScene().getHeight()/2 - popup.getHeight()/2);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        this.addEventHandler(SkycEvent.GOTO_WP_CREATED, (e) -> {

        });

        this.addEventHandler(SkycEvent.PATH_CREATED, (e) -> {

        });

    }


    public void addAircraft(Aircraft aircraft) {
        AircraftPane aircraftPane = new AircraftPane(aircraft, zoomLevelsNumber);
        aircraftPanes.put(aircraft.getId(), aircraftPane);
        this.getChildren().add(aircraftPane);
    }

    private void switchToAircraftPane(Aircraft aircraft) {
        for (int aircraftId : aircraftPanes.keySet()) {
            if (aircraftId == aircraft.getId()) {
                currentAircraftPane = aircraftPanes.get(aircraftId);
                currentAircraftPane.toFront();
                return;
            }
        }
    }

    public void setZoomLevel(int zoom) {
        setScaleFactor(Math.pow(2, zoom));
    }

    public void setScaleFactor(double scaleFactor) {
        this.setTranslateX(this.getTranslateX() * scaleFactor/currentScaleFactor);
        this.setTranslateY(this.getTranslateY() * scaleFactor/currentScaleFactor);
        currentScaleFactor = scaleFactor;
        int newZoom = (int)(Math.log(scaleFactor) / Math.log(2));
        if(newZoom != currentZoom && newZoom>=0 && newZoom <=19) {
            this.backMapLayers.get(currentZoom).setVisible(false);
            currentZoom = newZoom;
            this.backMapLayers.get(currentZoom).setVisible(true);
         }
        double remainingScale = scaleFactor / Math.pow(2, currentZoom);
        this.setScaleX(remainingScale);
        this.setScaleY(remainingScale);
        for(AircraftPane aircraftPane : aircraftPanes.values()) {
            aircraftPane.changeZoom(currentZoom, remainingScale);
        }

    }

    public void setCoordinates(GPSCoordinate gpsCoordinate) {
        XYZCoordinate xyzCoordinate = gpsCoordinate.toXYCoordinates(currentZoom);
        System.out.println("x " + -xyzCoordinate.getX() * BackMapLayer.TILE_DIMENSION + width/2);
        this.setTranslateX(-xyzCoordinate.getX() * BackMapLayer.TILE_DIMENSION + width/2);
        this.setTranslateY(-xyzCoordinate.getY() * BackMapLayer.TILE_DIMENSION + height/2);
    }


    public void pave() {
        if(width == Double.NaN || height == Double.NaN) {
            width = 800;
            height = 500;
        }
        double xMin = -this.getTranslateX()/this.getScaleX();
        double yMin = -this.getTranslateY()/this.getScaleY();
        backMapLayers.get(currentZoom).paveZone(xMin, yMin, xMin + width, yMin + height);
    }

    public void setStageWidth(double stageWidth) {
        this.width = stageWidth;
    }

    public void setStageHeight(double stageHeight) {
        this.height = stageHeight;
    }

    private void translateMap(double dx, double dy) {
        this.setTranslateX(this.getTranslateX() + dx);
        this.setTranslateY(this.getTranslateY() + dy);
    }

    private void fireDrawClickEvent(MouseEvent e) {
        DrawingMapEvent event = null;
        double xEvent = e.getSceneX();
        double yEvent = e.getSceneY();
        if (drawingEventType == DrawingMapEventType.END_DRAW) {
            drawingEventType = DrawingMapEventType.BEGIN_DRAW;
            event = new DrawingMapEvent(drawingEventType, new Point2D(xEvent, yEvent));
            fireDrawEvent(event);
        } else if (drawingEventType == DrawingMapEventType.DRAW) {
            drawingEventType = DrawingMapEventType.END_DRAW;
            event = new DrawingMapEvent(drawingEventType, new Point2D(xEvent, yEvent));
            fireDrawEvent(event);
        }
    }

    private void onMouseClicked(MouseEvent e) {
        switch (currentState) {
            case IDLE:
                x = e.getX();
                y = e.getY();
                break;
            case DRAW_CIRCLE:
                fireDrawClickEvent(e);
                break;
            case DRAW_PATH:
                fireDrawClickEvent(e);
                break;
            case DRAW_GO_TO:
                fireDrawClickEvent(e);
                break;
            case DRAW_WAYPOINT:
                break;
        }
    }

    private void fireMoveDrawEvent(MouseEvent e) {
        DrawingMapEvent event = null;
        double xEvent = e.getSceneX();
        double yEvent = e.getSceneY();
        if (drawingEventType == DrawingMapEventType.BEGIN_DRAW || drawingEventType == DrawingMapEventType.DRAW) {
            drawingEventType = DrawingMapEventType.DRAW;
            event = new DrawingMapEvent(drawingEventType, new Point2D(xEvent, yEvent));
            fireDrawEvent(event);
        }
    }

    private void onMouseMoved(MouseEvent e) {
//        System.out.println("Mouse moved start : "+drawingEventType);
        switch (currentState) {
            case IDLE:
                x = e.getX();
                y = e.getY();
                break;
            case DRAW_CIRCLE:
                fireMoveDrawEvent(e);
                break;
            case DRAW_PATH:
                fireMoveDrawEvent(e);
                break;
            case DRAW_GO_TO:
                fireMoveDrawEvent(e);
                break;
            case DRAW_WAYPOINT:
                break;
        }
//        System.out.println("Mouse moved end : "+drawingEventType);
    }

    private void onMouseDragged(MouseEvent e) {
        switch (currentState) {
            case IDLE:
                translateMap(e.getX()-x, e.getY()-y);
                break;
            case DRAW_CIRCLE:
                break;
            case DRAW_PATH:
                break;
            case DRAW_GO_TO:
                break;
            case DRAW_WAYPOINT:
                break;
        }
    }

    private void onMousePressed(MouseEvent e) {
        switch (currentState) {
            case IDLE:
                x = e.getX();
                y = e.getY();
                break;
            case DRAW_CIRCLE:
                break;
            case DRAW_PATH:
                break;
            case DRAW_GO_TO:
                break;
            case DRAW_WAYPOINT:
                break;
        }
    }

    private void onMouseReleased(MouseEvent e) {
        switch (currentState) {
            case IDLE:
                this.pave();
                break;
            case DRAW_CIRCLE:
                break;
            case DRAW_PATH:
                break;
            case DRAW_GO_TO:
                break;
            case DRAW_WAYPOINT:
                break;
        }
    }

    private void onScrollMap(ScrollEvent e) {
        double factor = Math.sqrt(2);
        if(e.getDeltaY() < 0) {
            factor = 1/factor;
        }
        double scaleFactor = currentScaleFactor * factor;

        if (scaleFactor < 1 || scaleFactor > Math.pow(2, 23)) {
            return;
        }

        this.setTranslateX(this.getTranslateX() - e.getSceneX());
        this.setTranslateY(this.getTranslateY() - e.getSceneY());
        setScaleFactor(scaleFactor);
        this.setTranslateX(this.getTranslateX() + e.getSceneX());
        this.setTranslateY(this.getTranslateY() + e.getSceneY());
        pave();
    }

    private void fireDrawEvent(DrawingMapEvent e) {
        if (currentAircraftPane != null) {
            currentAircraftPane.handleEvent(e);
        }
    }

    private void goToState(PossibleState state) {
        currentState = state;
    }

    public void handleEvent(PaletteEvent event) {
        switch (event.getEventType()) {
            case NO_ACTION:
                goToState(PossibleState.IDLE);
                break;
            case WAYPOINT:
                goToState(PossibleState.DRAW_WAYPOINT);
                break;
            case CIRCLE:
                goToState(PossibleState.DRAW_CIRCLE);
                break;
            case GOTO:
                goToState(PossibleState.DRAW_GO_TO);
                break;
            case PATH:
                goToState(PossibleState.DRAW_PATH);
                break;
        }
        if (currentAircraftPane != null) {
            currentAircraftPane.handleEvent(event);
        }
    }
}
