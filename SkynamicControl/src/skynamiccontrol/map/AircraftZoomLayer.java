package skynamiccontrol.map;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * Created by fabien on 01/03/17.
 */
public class AircraftZoomLayer extends Pane {
    int zoom;
    ImageView aircraftIcon;

    public AircraftZoomLayer(int zoom) {
        this.zoom = zoom;
        aircraftIcon = new ImageView("resources/bitmaps/aircraft.png");
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setHue(0.2);
        aircraftIcon.setEffect(colorAdjust);
        this.getChildren().add(aircraftIcon);
    }

    public void setAircraftPosition(double x, double y, double heading) {
        aircraftIcon.setLayoutX(x);
        aircraftIcon.setLayoutY(y);
        aircraftIcon.setRotate(heading);
        //aircraftIcon.setTranslateX(x);
        //aircraftIcon.setTranslateY(y);
    }
}