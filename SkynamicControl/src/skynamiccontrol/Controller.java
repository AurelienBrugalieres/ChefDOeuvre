package skynamiccontrol;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import skynamiccontrol.model.Aircraft;
import skynamiccontrol.model.GCSModel;
import skynamiccontrol.model.Status;
import java.awt.*;
import skynamiccontrol.view.status.StatusListContainer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    @FXML
    private BorderPane borderPane;

    private GCSModel model ;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
         /* test */
        Aircraft aircraft = new Aircraft(1, "microJet", 80.0, 102.0, 30.0, Status.AUTO, Color.decode("#8EF183"));
        Aircraft aircraft2 = new Aircraft(1, "Alpha1", 20.0, 50.0, 30.0, Status.AUTO, Color.decode("#94B7EA"));
        StatusListContainer statusListContainer = new StatusListContainer();
        model = new GCSModel(2, statusListContainer);
        model.addAircraft(aircraft);
        model.addAircraft(aircraft2);
        System.out.println(borderPane);
        borderPane.setPrefSize(800.0,1000.0);
        borderPane.getChildren().add(statusListContainer);
    }
}
