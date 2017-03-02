package skynamiccontrol.map;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.application.Platform;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import skynamiccontrol.map.drawing.CircleView;
import skynamiccontrol.map.drawing.InstructionView;
import skynamiccontrol.map.drawing.WaypointView;
import skynamiccontrol.model.Aircraft;

import javax.xml.stream.EventFilter;
import java.util.*;

/**
 * Created by fabien on 25/02/17.
 */
public class AircraftPane extends StackPane implements Observer {
    private Aircraft aircraft;
    private List<AircraftZoomLayer> aircraftZoomLayers;
    private List<InstructionView> instructionViewList;
    private int currentZoom;

    public AircraftPane(Aircraft aircraft, int nbZoomLevels) {
        aircraftZoomLayers = new LinkedList<>();
        instructionViewList = new LinkedList<>();
        aircraft.addObserver(this);
        this.aircraft = aircraft;
        for (int i = 0; i < nbZoomLevels; i++) {
            AircraftZoomLayer layer = new AircraftZoomLayer(i);
            layer.setInstructionViewList(instructionViewList);
            this.aircraftZoomLayers.add(layer);
            this.getChildren().add(layer);
            layer.setTranslateX(0);
            layer.setTranslateY(0);
        }
    }

    public void changeZoom(int zoom, double scale) {
        aircraftZoomLayers.get(zoom).setVisible(false);
        this.currentZoom = zoom;
        aircraftZoomLayers.get(zoom).setVisible(true);
        aircraftZoomLayers.get(currentZoom).changeScale(scale);
    }

    @Override
    public void update(Observable observable, Object o) {
        //if(observable instanceof Aircraft) {
        Aircraft aircraft = (Aircraft) observable;
        for(AircraftZoomLayer aircraftZoomLayer : aircraftZoomLayers) {
            Platform.runLater(() -> {
                aircraftZoomLayer.setAircraftPosition(new GPSCoordinate(aircraft.getLatitude(), aircraft.getLongitude()), aircraft.getHeading());
            });
        }
        //}
    }


    public void addCircle(double x, double y) {
        CircleView circleView = new CircleView(null, 50, new Point2D(x, y));

        instructionViewList.add(circleView);
        for(AircraftZoomLayer aircraftZoomLayer : aircraftZoomLayers) {
            Platform.runLater(() -> {
                aircraftZoomLayer.repaint();
            });
        }
    }

    public void handleEvent(DrawingMapEvent e) {
        switch (e.getEventType()) {
            case DRAW_WAYPOINT:
                break;
            case DRAW_CIRCLE:
                addCircle(e.getPosition().getX(), e.getPosition().getY());
                break;
            case DRAW_GOTO:
                break;
            case DRAW_PATH:
                break;
        }
    }
}
