package skynamiccontrol.map;

import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;

import static com.sun.javafx.util.Utils.clamp;

/**
 * Created by fabien on 25/02/17.
 */
public class Map extends StackPane{
    private int numberZoomLevels;
    private ArrayList<MapZoomLayer> zoomLayers;
    int currentZoom;
    double currentScaleFactor;
    double x, y;
    double width, height;

    public Map(int zoomLevelsNumber) {
        this.numberZoomLevels = zoomLevelsNumber;
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
        });

        this.addEventHandler(MouseEvent.MOUSE_RELEASED ,(e) -> this.pave());

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
            this.zoomLayers.get(currentZoom).setVisible(false);
            currentZoom = newZoom;
            this.zoomLayers.get(currentZoom).setVisible(true);
        }
        double remainingScale = scaleFactor / Math.pow(2, currentZoom);
        this.setScaleX(remainingScale);
        this.setScaleY(remainingScale);

    }

    public void setCoordinates(GPSCoordinate gpsCoordinate) {
        XYZCoordinate xyzCoordinate = gpsCoordinate.toXYCoordinates(currentZoom);
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
        zoomLayers.get(currentZoom).getBackMapLayer().paveZone(xMin, yMin, xMin + width, yMin + height);
    }

    public void setStageWidth(double stageWidth) {
        this.width = stageWidth;
    }

    public void setStageHeight(double stageHeight) {
        this.height = stageHeight;
    }
}
