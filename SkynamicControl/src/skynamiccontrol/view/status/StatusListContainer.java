package skynamiccontrol.view.status;/**
 * Created by Elodie on 15/02/2017.
 */

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Box;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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
        vbox.setPadding(new Insets(10,10,10,10));
        vbox.setStyle("-fx-background-color: #E1E5FF; -fx-border-radius: 10 10 10 10; -fx-background-radius: 10 10 10 10;");
        Font.loadFont(getClass().getResourceAsStream("resources/font/OpenSans-Regular.ttf"), 14);
        this.setStyle("-fx-font-family: OpenSans-Regular;");
        Text title = new Text("Drones' Status");
        title.setStyle("-fx-font-family: OpenSans-Regular; -fx-font-size: 20;");
        vbox.getChildren().add(title);
        status = new ArrayList<>();
        this.getChildren().add(new Group(vbox));
    }

    public void addStatus(Aircraft aircraft) {
       StatusContainer statusContainer = new StatusContainer(aircraft);

       VBox.setVgrow(statusContainer, Priority.ALWAYS);
       vbox.getChildren().add(statusContainer);
       status.add(statusContainer);
    }

}
