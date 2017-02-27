package skynamiccontrol.view.forms;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Elodie on 17/02/2017.
 */
public class FormPathController extends AbstractForm implements Initializable {

    @FXML
    private AnchorPane form_pane;

    @FXML
    private TextField field_name;

    @FXML
    private TextField field_north_p1;

    @FXML
    private TextField field_east_p1;

    @FXML
    private TextField field_north_p2;

    @FXML
    private TextField field_east_p2;

    @FXML
    private TextField field_north_p3;

    @FXML
    private TextField field_east_p3;

    @FXML
    private TextField field_north_p4;

    @FXML
    private TextField field_east_p4;

    @FXML
    private TextField field_north_p5;

    @FXML
    private TextField field_east_p5;


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

}

