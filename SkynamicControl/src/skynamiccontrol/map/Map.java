package skynamiccontrol.map;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class Map extends StackPane {
    private int zoom = 12;
    private BackMap backMap;
    double x, y;
    double width, height;

    public Map(GPSCoordinate gpsCoordinate, int zoom) {
        super();
        backMap = new BackMap();
        this.zoom = zoom;
        this.getChildren().add(backMap);
        this.setTranslateX(0);
        this.setTranslateY(0);
        setCoordinates(gpsCoordinate, zoom);


        this.addEventHandler(MouseEvent.MOUSE_PRESSED ,(e) -> {
            x = e.getX();
            y = e.getY();
        });

        this.addEventHandler(MouseEvent.MOUSE_DRAGGED ,(e) -> {
            this.setTranslateX(this.getTranslateX() + e.getX() - x);
            this.setTranslateY(this.getTranslateY() + e.getY() - y);
        });

        this.addEventHandler(MouseEvent.MOUSE_RELEASED ,(e) -> this.pave());

    }

    public void pave() {
        if(width == Double.NaN || height == Double.NaN) {
            width = 400;
            height = 400;
        }
        double xMin = -this.getTranslateX();
        double yMin = -this.getTranslateY();
        backMap.paveZone(xMin, yMin, xMin + width, yMin + height, this.zoom);
    }

    public void setCoordinates(GPSCoordinate gpsCoordinate, int zoom) {
        setCoordinates(gpsCoordinate.toXYCoordinates(zoom));
    }

    public void setCoordinates(XYZCoordinate xyzCoordinate) {
        this.zoom = xyzCoordinate.getZoom();
        this.setTranslateX(-xyzCoordinate.getX() * backMap.TILE_DIMENSION);
        this.setTranslateY(-xyzCoordinate.getY() * backMap.TILE_DIMENSION);
    }


    public void setStageWidth(double stageWidth) {
        this.width = stageWidth;
    }

    public void setStageHeight(double stageHeight) {
        this.height = stageHeight;
    }
}
