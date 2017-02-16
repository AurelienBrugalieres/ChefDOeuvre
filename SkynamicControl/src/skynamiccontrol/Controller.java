package skynamiccontrol;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import skynamiccontrol.Timeline.Timeline;
import skynamiccontrol.model.Aircraft;
import skynamiccontrol.model.GCSModel;
import skynamiccontrol.model.Status;
import java.awt.*;

import skynamiccontrol.view.map.MapController;
import skynamiccontrol.view.map.events.MapListener;
import skynamiccontrol.view.palette.PaletteController;
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
    private HBox pane_timeline_palette;

    private PaletteController paletteController = null;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println(borderPane);
        borderPane.setPrefSize(800.0,1000.0);

        pane_timeline_palette = new HBox();
        borderPane.setBottom(pane_timeline_palette);



         /* test */
        Aircraft aircraft = new Aircraft(1, "microJet", 80.0, 102.0, 30.0, Status.AUTO, Color.decode("#8EF183"));
        Aircraft aircraft2 = new Aircraft(1, "Alpha1", 20.0, 50.0, 30.0, Status.AUTO, Color.decode("#94B7EA"));
        StatusListContainer statusListContainer = new StatusListContainer();
        model = new GCSModel(2, statusListContainer);
        model.addAircraft(aircraft);
        model.addAircraft(aircraft2);

        // initialize map
        initMapPane();

        //intialize timeline
        initTimeline();

        //initialize palette
        initPalette();


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

    /**
     * Load palette in scene
     */
    private void initPalette(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("palette.fxml"));
        try {
            Node palette = loader.load();
            HBox.setHgrow(palette, Priority.ALWAYS);
            pane_timeline_palette.getChildren().add(palette);
        } catch (IOException e) {
            e.printStackTrace();
        }
        paletteController = loader.getController();
        if(paletteController == null){
            paletteController = new PaletteController();
            loader.setController(paletteController);
        }
        paletteController.setPaletteListener(new PaletteController.PaletteListener() {
            @Override
            public void onWaypointButtonClick() {
                if (mapController != null) {
                    mapController.activeMarkerOption();
                }
            }

            @Override
            public void onPathButtonClick() {
                if (mapController != null) {
                    mapController.activePathOption();
                }
            }

            @Override
            public void onGoToButtonClick() {
                if (mapController != null) {
                    mapController.activeGoToOption();
                }
            }

            @Override
            public void onCircleButtonClick() {
                if (mapController != null) {
                    mapController.activeCircleOption();
                }
            }
        });


    }

    private void initTimeline(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Timeline/TimelineUI.fxml"));
        timelineController = loader.getController();
        if(timelineController == null){
            timelineController = new Timeline(model);
            loader.setController(timelineController);
        }
        try {
            Node timeline = loader.load();
            HBox.setHgrow(timeline, Priority.ALWAYS);
            pane_timeline_palette.getChildren().add(timeline);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public MapController getMapController() {
        return mapController;
    }
}
