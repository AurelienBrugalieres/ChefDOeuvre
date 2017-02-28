package skynamiccontrol.view.forms;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import skynamiccontrol.model.mission.GoToWP;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Elodie on 17/02/2017.
 */
public class FormGoToController extends AbstractForm implements Initializable{

    @FXML
    private AnchorPane form_pane;

    @FXML
    private TextField field_name;

    @FXML
    private TextField field_north_source;

    @FXML
    private TextField field_east_source;

    @FXML
    private TextField field_north_destination;

    @FXML
    private TextField field_east_destination;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Font.loadFont(getClass().getResourceAsStream("resources/font/OpenSans-Regular.ttf"), 14);
        form_pane.setStyle("-fx-font-family: OpenSans-Regular;");
    }

    @FXML
    public void onMouseClickedOk(MouseEvent mouseEvent) {
        hide();
    }

    @FXML
    public void onMouseClickedNext(MouseEvent mouseEvent) {
    }

    public void setGoTo(GoToWP instruction) {
    }
}
