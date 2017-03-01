package skynamiccontrol.view.status;/**
 * Created by Elodie on 15/02/2017.
 */

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.input.*;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import skynamiccontrol.core.StatusStateMachine;
import skynamiccontrol.model.Aircraft;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import static javafx.application.Application.launch;

public class StatusListContainer extends Parent {

    public interface StatusListener {
        void onDroneStatusClick(Aircraft aircraftClicked);
    };

    private VBox vbox;
    public final static int FONT_SIZE_BODY = 14;
    public final static int FONT_SIZE_TITLE1 = 20;
    public final static int FONT_SIZE_TITLE2 = 18;
    public final static int PADDING = 10;

    private java.util.Map<Aircraft, StatusContainer> status;
    private StatusStateMachine statusStateMachine;

    public StatusListContainer() {

        Color color = Color.decode("#E1E5FF");
        vbox = new VBox();
        vbox.setPadding(new Insets(PADDING,PADDING,PADDING,PADDING));
        vbox.setStyle("-fx-background-color: rgba("+color.getRed()+","+color.getGreen()+","+color.getBlue()+",0.7); -fx-border-radius: 10 10 10 10; -fx-background-radius: 10 10 10 10;");
        final Point dragDelta = new Point();
        vbox.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // record a delta distance for the drag and drop operation.
                dragDelta.x = (int)( vbox.getLayoutX() - mouseEvent.getSceneX());
                dragDelta.y = (int) (vbox.getLayoutY() - mouseEvent.getSceneY());
                vbox.setCursor(Cursor.MOVE);
            }
        });
        vbox.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                vbox.setCursor(Cursor.HAND);
            }
        });
        vbox.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                vbox.setLayoutX(mouseEvent.getSceneX() + dragDelta.x);
                vbox.setLayoutY(mouseEvent.getSceneY() + dragDelta.y);
            }
        });
        vbox.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                vbox.setCursor(Cursor.HAND);
            }
        });

        Font.loadFont(getClass().getResourceAsStream("resources/font/OpenSans-Regular.ttf"), FONT_SIZE_BODY);
        this.setStyle("-fx-font-family: OpenSans-Regular;");
        status = new HashMap<>();
        statusStateMachine = new StatusStateMachine(new ArrayList<>(status.values()));
        this.getChildren().add(new Group(vbox));
    }

    public void addStatus(Aircraft aircraft) {
       StatusContainer statusContainer = new StatusContainer(aircraft);
        statusStateMachine.addStatus(statusContainer);
        VBox.setVgrow(statusContainer, Priority.ALWAYS);
        statusContainer.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                statusStateMachine.onStatusSelected(statusContainer);
            }
        });
       vbox.getChildren().add(statusContainer);
       status.put(aircraft, statusContainer);
    }

    public void setStatusListener(StatusListener listener) {
        this.statusStateMachine.setStatusListener(listener);
    }

    public void selectAircraft(Aircraft aircraft) {
        this.statusStateMachine.onStatusSelected(status.get(aircraft));
    }
}
