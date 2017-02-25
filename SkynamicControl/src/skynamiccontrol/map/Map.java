package skynamiccontrol.map;

import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.StackPane;
import skynamiccontrol.view.palette.PaletteEvent;

import java.util.ArrayList;

/**
 * Created by fabien on 25/02/17.
 */
public class Map extends StackPane{
    private ArrayList<MapZoomLayer> zoomLayers;
    int currentZoom;
    double x, y;
    double width, height;

    private enum PossibleState {
        IDLE, DRAW_CIRCLE, DRAW_PATH, DRAW_GO_TO, DRAW_WAYPOINT
    }
    private PossibleState currentState;

    public Map(int zoomLevelsNumber) {
        currentState = PossibleState.IDLE;
        this.zoomLayers = new ArrayList<>();
        for (int i = 0; i < zoomLevelsNumber; i++) {
            MapZoomLayer layer = new MapZoomLayer(i);
            this.zoomLayers.add(layer);
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

    public void setZoomLevel(int zoom) {
        double factor = Math.pow(2, zoom - this.currentZoom);
        this.setTranslateX(this.getTranslateX() * factor);
        this.setTranslateY(this.getTranslateY() * factor);
        this.currentZoom = zoom;
    }

    public void setCoordinates(GPSCoordinate gpsCoordinate) {
        XYZCoordinate xyzCoordinate = gpsCoordinate.toXYCoordinates(currentZoom);
        this.setTranslateX(-xyzCoordinate.getX() * BackMapLayer.TILE_DIMENSION);
        this.setTranslateY(-xyzCoordinate.getY() * BackMapLayer.TILE_DIMENSION);
    }


    public void pave() {
        if(width == Double.NaN || height == Double.NaN) {
            width = 400;
            height = 400;
        }
        double xMin = -this.getTranslateX();
        double yMin = -this.getTranslateY();
        zoomLayers.get(currentZoom).getBackMapLayer().paveZone(xMin, yMin, xMin + width, yMin + height);
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
        System.out.println(e.getDeltaY());
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
