package skynamiccontrol.view.forms;

import com.sun.xml.internal.bind.v2.model.core.ID;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import skynamiccontrol.view.ImageButton;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Elodie on 17/02/2017.
 */
public class FormCircleController extends AbstractForm implements Initializable{

    @FXML
    private AnchorPane form_pane;

    @FXML
    private TextField field_name;

    @FXML
    private TextField field_north;

    @FXML
    private TextField field_east;

    @FXML
    private TextField field_radius;

    @FXML
    private ChoiceBox box_orientation;

    @FXML
    private ImageButton btn_ok;

    @FXML
    private ImageButton btn_next;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Font.loadFont(getClass().getResourceAsStream("resources/font/OpenSans-Regular.ttf"), 14);
        form_pane.setStyle("-fx-font-family: OpenSans-Regular;");
        btn_ok.initButton("/resources/bitmaps/btn_ok_form.png",
                "/resources/bitmaps/hoover_btn_ok_form.png",
                "/resources/bitmaps/press_btn_ok_form.png");
        btn_next.initButton("/resources/bitmaps/btn_next_form.png",
                "/resources/bitmaps/hoover_btn_next_form.png",
                "/resources/bitmaps/press_btn_next_form.png");
    }

    @FXML
    public void onMouseClickedOK(MouseEvent mouseEvent) {
        hide();
    }

    @FXML
    public void onMouseClickedNext(MouseEvent mouseEvent) {
        System.out.println("next");
    }
}
