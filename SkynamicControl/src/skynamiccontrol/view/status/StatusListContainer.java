package skynamiccontrol.view.status;/**
 * Created by Elodie on 15/02/2017.
 */

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Box;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import skynamiccontrol.core.StatusStateMachine;
import skynamiccontrol.model.Aircraft;

import java.util.ArrayList;
import java.util.List;

import static javafx.application.Application.launch;

public class StatusListContainer extends Parent {

    private VBox vbox;
    public final static int FONT_SIZE_BODY = 14;
    public final static int FONT_SIZE_TITLE1 = 20;
    public final static int FONT_SIZE_TITLE2 = 18;
    public final static int PADDING = 10;

    private List<StatusContainer> status;
    private StatusStateMachine statusStateMachine;

    public StatusListContainer() {

        vbox = new VBox();
        vbox.setPadding(new Insets(PADDING,PADDING,PADDING,PADDING));
        vbox.setStyle("-fx-background-color: #E1E5FF; -fx-border-radius: 10 10 10 10; -fx-background-radius: 10 10 10 10;");
        Font.loadFont(getClass().getResourceAsStream("resources/font/OpenSans-Regular.ttf"), FONT_SIZE_BODY);
        this.setStyle("-fx-font-family: OpenSans-Regular;");
        Text title = new Text("Drones' Status");
        title.setStyle("-fx-font-family: OpenSans-Regular; -fx-font-size: 20;");
        vbox.getChildren().add(title);
        status = new ArrayList<>();
        statusStateMachine = new StatusStateMachine(status);
        this.getChildren().add(new Group(vbox));
    }

    public void addStatus(Aircraft aircraft) {
       StatusContainer statusContainer = new StatusContainer(aircraft);
        statusStateMachine.addStatus(statusContainer);
        VBox.setVgrow(statusContainer, Priority.ALWAYS);
        statusContainer.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                statusStateMachine.onMouseClick(statusContainer);
            }
        });
       vbox.getChildren().add(statusContainer);
       status.add(statusContainer);
    }



}
