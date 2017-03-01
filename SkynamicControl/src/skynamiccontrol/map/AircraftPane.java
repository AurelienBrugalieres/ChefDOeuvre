package skynamiccontrol.map;

import javafx.scene.layout.StackPane;
import skynamiccontrol.model.Aircraft;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by fabien on 25/02/17.
 */
public class AircraftPane extends StackPane implements Observer {
    private Aircraft aircraft;
    private ArrayList<AircraftZoomLayer> aircraftZoomLayers;

    public AircraftPane(Aircraft aircraft, int nbZoomLevels) {
        this.aircraft = aircraft;
        for (int i = 0; i < nbZoomLevels; i++) {
            AircraftZoomLayer layer = new AircraftZoomLayer(i);
            this.aircraftZoomLayers.add(layer);
            this.getChildren().add(layer);
            layer.setTranslateX(0);
            layer.setTranslateY(0);
        }
    }

    @Override
    public void update(Observable observable, Object o) {

    }
}
