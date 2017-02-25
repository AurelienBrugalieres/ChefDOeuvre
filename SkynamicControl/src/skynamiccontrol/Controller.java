package skynamiccontrol;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import skynamiccontrol.map.Map;
import skynamiccontrol.view.palette.PaletteEvent;
import skynamiccontrol.view.palette.PaletteEventType;
import skynamiccontrol.timeline.Timeline;
import skynamiccontrol.model.GCSModel;

import skynamiccontrol.view.notifications.NotificationContainer;
import skynamiccontrol.view.palette.PaletteController;
import skynamiccontrol.view.status.StatusListContainer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    @FXML
    private BorderPane borderPane;

    private GCSModel model ;
    private StatusListContainer statusListContainer;

    private NotificationContainer notificationContainer;

    private Timeline timelineController = null;
    private HBox pane_timeline_palette;

    private PaletteController paletteController = null;

    private Map map = null;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //System.out.println(borderPane);
        borderPane.setPrefSize(800.0,1000.0);

        pane_timeline_palette = new HBox();
        pane_timeline_palette.setBackground(Background.EMPTY);
       // borderPane.setBottom(pane_timeline_palette);



        //Aircraft aircraft = new Aircraft(1, "microJet", 80.0, 102.0, 30.0, Status.AUTO, Color.decode("#8EF183"));

        statusListContainer = new StatusListContainer();
        notificationContainer = new NotificationContainer();
        notificationContainer.setTranslateX(borderPane.getWidth());

        //intialize timeline
        initTimeline();

        //initialize palette
        initPalette();


        borderPane.getChildren().add(statusListContainer);
        borderPane.getChildren().add(notificationContainer);
        borderPane.getChildren().add(pane_timeline_palette);


    }

    public void setMap(Map map) {
        this.map = map;
        borderPane.getChildren().add(map);
        map.toBack();
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
                if (map != null) {
                    PaletteEvent event;
                    if (!paletteController.isPaletteActive())
                        event = new PaletteEvent(PaletteEventType.NO_ACTION);
                    else
                        event = new PaletteEvent(PaletteEventType.WAYPOINT);
                    map.handleEvent(event);
                }
            }

            @Override
            public void onPathButtonClick() {
                if (map != null) {
                    PaletteEvent event;
                    if (!paletteController.isPaletteActive())
                        event = new PaletteEvent(PaletteEventType.NO_ACTION);
                    else
                        event = new PaletteEvent(PaletteEventType.PATH);
                    map.handleEvent(event);
                }
            }

            @Override
            public void onGoToButtonClick() {
                if (map != null) {
                    PaletteEvent event;
                    if (!paletteController.isPaletteActive())
                        event = new PaletteEvent(PaletteEventType.NO_ACTION);
                    else
                        event = new PaletteEvent(PaletteEventType.GOTO);
                    map.handleEvent(event);
                }
            }

            @Override
            public void onCircleButtonClick() {
                if (map != null) {
                    PaletteEvent event;
                    if (!paletteController.isPaletteActive())
                        event = new PaletteEvent(PaletteEventType.NO_ACTION);
                    else
                        event = new PaletteEvent(PaletteEventType.CIRCLE);
                    map.handleEvent(event);
                }
            }
        });

    }

    private void initTimeline(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("timeline/TimelineUI.fxml"));
        try {
            Node timeline = loader.load();
            HBox.setHgrow(timeline, Priority.ALWAYS);
            pane_timeline_palette.getChildren().add(timeline);

        } catch (IOException e) {
            e.printStackTrace();
        }
        if((timelineController = loader.getController()) == null){
            timelineController = new Timeline();
            loader.setController(timelineController);
        }


    }

    public StatusListContainer getStatusListContainer() {
        return statusListContainer;
    }

    public Timeline getTimelineController() {
        return timelineController;
    }

    public void setModel(GCSModel model) {
        this.model = model;
        this.timelineController.setModel(model);
    }


    public NotificationContainer getNotificationContainer() {
        return notificationContainer;
    }

    public HBox getPane_timeline_palette(){ return pane_timeline_palette;}

    public PaletteController getPaletteController(){ return paletteController;}
}
