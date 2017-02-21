package skynamiccontrol.Timeline;

/**
 *
 * Created by Lioz-MBPR on 14/02/2017.
 *
 **/
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.*;
import javafx.stage.Screen;
import skynamiccontrol.model.Aircraft;
import skynamiccontrol.model.GCSModel;
import skynamiccontrol.timeline.MissionBlock;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.ResourceBundle;

public class Timeline implements Initializable{
    MissionBlock mb;
    @FXML
    TabPane tabPane;
    GCSModel model;

    public void initBlockMission(Tab tab){
        for ( int i = 0 ;  i < model.getAircrafts().size();i++) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/skynamiccontrol/timeline/MissionBlockUI.fxml"));
            mb = loader.getController();
            if (mb == null) {
                mb = new MissionBlock(model.getAircrafts().get(i),model);
                loader.setController(mb);
            }
            try {
                tab.setContent(loader.load());
                mb.updateAircraftMissionBlock(model.getAircrafts().get(i));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Clipboard data format for draggable tabs.
     */
    public static final DataFormat TAB_TYPE = new DataFormat("nonserializableObject/JfxTab");

    /**
     * Helper method to create a new tab with the given label and make it draggable with {@link #makeDraggable}.
     */
    public static Tab newDraggableTab(Aircraft aircraft) {
        Tab rr = new Tab();
        rr.setGraphic(new Label(aircraft.getName()));
        makeDraggable(rr);
        return rr;
    }

    /**
     * global for drag-n-drop of non-serializable type
     */
    private static WeakReference<Tab> dndTab;

    /**
     * Makes the specified tab draggable. It can be dragged to a {@link TabPane} set up by {@link #makeDroppable(TabPane)}.
     * setOnDragDetected on the tab's graphic is called to handle the event.
     */
    public static void makeDraggable(final Tab tab) {
        tab.getGraphic().setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Dragboard dragboard = tab.getGraphic().startDragAndDrop(TransferMode.MOVE);
                ClipboardContent clipboardContent = new ClipboardContent();
                clipboardContent.put(TAB_TYPE, 1);
                dndTab = new WeakReference<>(tab);
                dragboard.setContent(clipboardContent);
                event.consume();
            }
        });
    }

    /**
     * Makes the specified {@link TabPane} a drag target for draggable tabs from {@link #makeDraggable(Tab)}.
     * setOnDragOver and setOnDragDropped are called on the pane to handle the event.
     */
    public static void makeDroppable(final TabPane tabPane) {
        tabPane.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                if (dndTab != null && event.getDragboard().hasContent(TAB_TYPE)) {
                    Tab tab = dndTab.get();
                    if (tab != null && tab.getTabPane() != tabPane) {// && different from source location
                        event.acceptTransferModes(TransferMode.MOVE);
                        event.consume();
                    }
                }
            }
        });
        tabPane.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                if (dndTab != null && event.getDragboard().hasContent(TAB_TYPE)) {
                    Tab tab = dndTab.get();
                    if (tab != null && tab.getTabPane() != tabPane) {// && different from source location
                        tab.getTabPane().getTabs().remove(tab);
                        tabPane.getTabs().add(tab);
                        event.setDropCompleted(true);
                        event.consume();
                    }
                    dndTab = null;
                }
            }
        });

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tabPane.getStylesheets().add("/resources/css/timelineTab.css");
        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        tabPane.setLayoutX(visualBounds.getWidth());
    }

    public void addAircraft(Aircraft aircraft) {
        String backgroundColor = "("+aircraft.getColor().getRed()+","+
                aircraft.getColor().getGreen()+","+
                aircraft.getColor().getBlue();
        String styleTab = "-fx-background-color: rgba"+backgroundColor;
        Tab tab = new Tab();
        tab.setText(aircraft.getName());
        tab.setStyle(styleTab+",1)");
        initBlockMission(tab);
        tabPane.getTabs().add(tab);
        tab.getContent().setStyle(styleTab+",0.7)");

    }

    public void setModel(GCSModel model) {
        this.model = model;
    }
}
