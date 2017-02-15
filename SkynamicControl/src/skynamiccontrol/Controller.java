package skynamiccontrol;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import skynamiccontrol.model.Aircraft;
import skynamiccontrol.model.GCSModel;
import skynamiccontrol.model.Status;
import skynamiccontrol.view.status.StatusListContainer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    @FXML
    private AnchorPane AnchorPane;

    private GCSModel model ;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
         /* test */
        Aircraft aircraft = new Aircraft(1, "microJet", 80.0, 102.0, 30.0, Status.AUTO);
        StatusListContainer statusListContainer = new StatusListContainer();
        model = new GCSModel(2, statusListContainer);
        model.addAircraft(aircraft);
        System.out.println(AnchorPane);
        AnchorPane = new AnchorPane();
        AnchorPane.setPrefSize(800.0,1000.0);
        AnchorPane.getChildren().add(statusListContainer);
    }
}
