package skynamiccontrol.map;

import javafx.scene.layout.StackPane;

import java.util.ArrayList;

/**
 * Created by fabien on 25/02/17.
 */
public class MapZoomLayer extends StackPane {
    private final int zoom;
    private final BackMapLayer backMapLayer;
    private ArrayList<AircraftPane> aircraftPanes;

    public MapZoomLayer(int zoom) {
        this.backMapLayer = new BackMapLayer(zoom);
        this.zoom = zoom;
        aircraftPanes = new ArrayList<>();
        this.getChildren().add(this.backMapLayer);
    }

    public void addAircraft(int aircraftId) {
        AircraftPane aircraftPane = new AircraftPane(aircraftId);
        aircraftPanes.add(aircraftPane);
        this.getChildren().add(aircraftPane);
    }

    public int getZoom() {
        return zoom;
    }

    public BackMapLayer getBackMapLayer() {
        return backMapLayer;
    }
}
