package skynamiccontrol.map;

import javafx.geometry.Point2D;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import static com.sun.javafx.util.Utils.clamp;

/**
 * Created by fabien on 01/03/17.
 */
public class AircraftZoomLayer extends Pane {
    int zoom;
    ImageView aircraftIcon;
    ImageView aircraftOutIcon;

    public AircraftZoomLayer(int zoom) {
        this.zoom = zoom;
        aircraftIcon = new ImageView("resources/bitmaps/aircraft.png");
        aircraftOutIcon = new ImageView("resources/bitmaps/aircraftOut.png");
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setHue(0.2);
        aircraftIcon.setEffect(colorAdjust);
        this.getChildren().add(aircraftIcon);
        this.getChildren().add(aircraftOutIcon);
        aircraftOutIcon.setVisible(false);
    }

    public void setAircraftPosition(GPSCoordinate aircraftCoordinates, double heading) {
        XYZCoordinate xyzCoordinate = aircraftCoordinates.toXYCoordinates(zoom);
        aircraftIcon.setRotate(heading);
        double x = xyzCoordinate.getX() * BackMapLayer.TILE_DIMENSION - aircraftIcon.getImage().getWidth()/2;
        double y = xyzCoordinate.getY() * BackMapLayer.TILE_DIMENSION - aircraftIcon.getImage().getHeight()/2;
        aircraftIcon.setTranslateX(x);
        aircraftIcon.setTranslateY(y);
        Point2D  pt =this.localToScene(x,y);
        if(pt.getX() < 0 ||
                pt.getY() < 0 ||
                pt.getX() > getScene().getWidth() ||
                pt.getY() > getScene().getHeight()) {
            aircraftOutIcon.setVisible(true);
            Point2D newPt = this.sceneToLocal(new Point2D(clamp(0, pt.getX(), getScene().getWidth()), clamp(0, pt.getY(), getScene().getWidth())));
            aircraftOutIcon.setTranslateX(newPt.getX());
            aircraftOutIcon.setTranslateY(newPt.getY());

        } else {
            aircraftOutIcon.setVisible(false);
        }
    }

    public void changeScale(double scale) {
        aircraftIcon.setScaleX(1/scale);
        aircraftIcon.setScaleY(1/scale);
    }
}
