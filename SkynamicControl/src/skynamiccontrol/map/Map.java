package skynamiccontrol.map;

import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;

/**
 * Created by fabien on 25/02/17.
 */
public class Map extends StackPane{
    private ArrayList<MapZoomLayer> zoomLayers;
    int currentZoom;
    double x, y;
    double width, height;

    public Map(int zoomLevelsNumber) {
        this.zoomLayers = new ArrayList<>();
        for (int i = 0; i < zoomLevelsNumber; i++) {
            MapZoomLayer layer = new MapZoomLayer(i);
            this.zoomLayers.add(layer);
            this.getChildren().add(layer);
            layer.setTranslateX(0);
            layer.setTranslateY(0);
        }


        this.addEventHandler(MouseEvent.MOUSE_PRESSED ,(e) -> {
            x = e.getX();
            y = e.getY();
        });

        this.addEventHandler(MouseEvent.MOUSE_DRAGGED ,(e) -> {
            this.setTranslateX(this.getTranslateX() + e.getX() - x);
            this.setTranslateY(this.getTranslateY() + e.getY() - y);
        });

        this.addEventHandler(ScrollEvent.SCROLL, (e) -> {

            System.out.println(e.getDeltaX());
        });

        this.addEventHandler(MouseEvent.MOUSE_RELEASED ,(e) -> this.pave());

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
}
