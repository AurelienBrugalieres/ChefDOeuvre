package skynamiccontrol.view.status;/**
 * Created by Elodie on 15/02/2017.
 */

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import skynamiccontrol.model.Aircraft;

import java.util.ArrayList;
import java.util.List;

import static javafx.application.Application.launch;

public class StatusListContainer extends Parent {

    private VBox vbox;

    private List<StatusContainer> status;

    public StatusListContainer() {
        vbox = new VBox();
        status = new ArrayList<>();
        this.getChildren().add(vbox);
    }

    public void addStatus(Aircraft aircraft) {
       StatusContainer statusContainer = new StatusContainer(aircraft);
       VBox.setVgrow(statusContainer, Priority.ALWAYS);
       vbox.getChildren().add(statusContainer);
       status.add(statusContainer);
    }

}
