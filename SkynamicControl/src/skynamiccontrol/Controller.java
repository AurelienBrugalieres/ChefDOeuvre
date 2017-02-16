package skynamiccontrol;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import skynamiccontrol.Timeline.Timeline;
import skynamiccontrol.model.Aircraft;
import skynamiccontrol.model.GCSModel;
import skynamiccontrol.model.Status;
import java.awt.*;

import skynamiccontrol.view.map.MapController;
import skynamiccontrol.view.map.events.MapListener;
import skynamiccontrol.view.status.StatusListContainer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    @FXML
    private BorderPane borderPane;

    private GCSModel model ;

    private MapController mapController = null;
    private MapListener mapListener = null;
    private Timeline timelineController = null;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // initialize map
        initMapPane();


         /* test */
        Aircraft aircraft = new Aircraft(1, "microJet", 80.0, 102.0, 30.0, Status.AUTO, Color.decode("#8EF183"));
        Aircraft aircraft2 = new Aircraft(1, "Alpha1", 20.0, 50.0, 30.0, Status.AUTO, Color.decode("#94B7EA"));
        StatusListContainer statusListContainer = new StatusListContainer();
        model = new GCSModel(2, statusListContainer);
        model.addAircraft(aircraft);
        model.addAircraft(aircraft2);
        initTimeline();
        System.out.println(borderPane);
        borderPane.setPrefSize(800.0,1000.0);
        borderPane.getChildren().add(statusListContainer);

    }

    /**
     * Set a map listener to handle map events
     * @param listener the map event listener
     */
    public void setMapListener(MapListener listener) {
        this.mapListener = listener;
        if (mapController != null) {
            mapController.setMapListener(mapListener);
        }
    }

    /**
     * Initialize the map on the interface
     */
    private void initMapPane() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/map/Map.fxml"));
        mapController = loader.getController();
        if (mapController == null) {
            mapController = new MapController();
            loader.setController(mapController);
        }
        if (mapListener != null) {
            mapController.setMapListener(mapListener);
        }
        try {
            borderPane.setCenter(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initTimeline(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Timeline/TimelineUI.fxml"));
        timelineController = loader.getController();
        if(timelineController == null){
            timelineController = new Timeline(model);
            loader.setController(timelineController);
        }
        try {
            borderPane.setBottom(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public MapController getMapController() {
        return mapController;
    }
}
