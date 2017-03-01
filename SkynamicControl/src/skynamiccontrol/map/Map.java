package skynamiccontrol.map;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.StackPane;
import skynamiccontrol.model.Aircraft;
import skynamiccontrol.model.Constants;
import skynamiccontrol.model.GCSModel;
import skynamiccontrol.view.palette.PaletteEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

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

    public Map(int zoomLevelsNumber) {
        this.zoomLevelsNumber = zoomLevelsNumber;
        currentState = PossibleState.IDLE;
        this.backMapLayers = new ArrayList<>();
        aircraftPanes = new HashMap<>();
        for (int i = 0; i < zoomLevelsNumber; i++) {
            BackMapLayer layer = new BackMapLayer(i);
            this.backMapLayers.add(layer);
            this.getChildren().add(layer);
            layer.setTranslateX(0);
            layer.setTranslateY(0);
        }


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

    }

    public void addAircraft(Aircraft aircraft) {
        AircraftPane aircraftPane = new AircraftPane(aircraft, zoomLevelsNumber);
        aircraftPanes.put(aircraft.getId(), aircraftPane);
        this.getChildren().add(aircraftPane);
    }

    private void switchToAircraftPane(Aircraft aircraft) {
        for (int aircraftId : aircraftPanes.keySet()) {
            if (aircraftId == aircraft.getId()) {
                aircraftPanes.get(aircraftId).setVisible(true);
                currentAircraftPane = aircraftPanes.get(aircraftId);
            } else {
                aircraftPanes.get(aircraftId).setVisible(false);
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
                x = e.getSceneX();
                y = e.getSceneY();
                drawCircle();
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

    private void drawCircle() {

    }

    private void goToState(PossibleState state) {
        currentState = state;
        System.out.println(currentState);
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
    }
}
