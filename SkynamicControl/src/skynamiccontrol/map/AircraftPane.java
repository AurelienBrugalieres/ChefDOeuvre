package skynamiccontrol.map;

import javafx.application.Platform;
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
    private int currentZoom;

    public AircraftPane(Aircraft aircraft, int nbZoomLevels) {
        aircraftZoomLayers = new ArrayList<>();
        aircraft.addObserver(this);
        this.aircraft = aircraft;
        for (int i = 0; i < nbZoomLevels; i++) {
            AircraftZoomLayer layer = new AircraftZoomLayer(i);
            this.aircraftZoomLayers.add(layer);
            this.getChildren().add(layer);
            layer.setTranslateX(0);
            layer.setTranslateY(0);
        }
    }

    public void changeZoom(int zoom) {
        this.currentZoom = zoom;
    }

    @Override
    public void update(Observable observable, Object o) {
        //if(observable instanceof Aircraft) {
        Aircraft aircraft = (Aircraft) observable;
        XYZCoordinate xyzCoordinate = new GPSCoordinate(aircraft.getLatitude(), aircraft.getLongitude()).toXYCoordinates(currentZoom);
        for(AircraftZoomLayer aircraftZoomLayer : aircraftZoomLayers) {
            Platform.runLater(() -> {
                aircraftZoomLayer.setAircraftPosition(xyzCoordinate.getX(), xyzCoordinate.getY(), aircraft.getHeading());
            });
        }
        //}
    }
}
