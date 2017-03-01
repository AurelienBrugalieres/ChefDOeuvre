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
import javafx.scene.text.Text;
import javafx.stage.Popup;
import skynamiccontrol.SkycEvent;
import skynamiccontrol.model.mission.Circle;
import skynamiccontrol.model.mission.Instruction;
import skynamiccontrol.view.ImageButton;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Elodie on 17/02/2017.
 */
public class FormCircleController extends AbstractForm implements Initializable{

    private Circle circle;

    @FXML
    private AnchorPane form_pane;

    @FXML
    private TextField field_name;

    @FXML
    private Text latEast;

    @FXML
    private Text lonNorth;

    @FXML
    private TextField lonNorthField;

    @FXML
    private TextField latEastField;

    @FXML
    private TextField field_radius;

    @FXML
    private ChoiceBox box_orientation;

    @FXML
    private ImageButton btn_ok;

    @FXML
    private ImageButton btn_next;

    @FXML
    private ImageButton btn_append;

    @FXML
    private ImageButton btn_choose_emplacement;

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
        box_orientation.getItems().addAll("clockwise", "counterclockwise");
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
        field_name.setText(circle.getName());
        switch (circle.getCenter().getCoordinateSystem()) {
            case LOCAL:
                lonNorth.setText("North");
                latEast.setText("East");
                lonNorthField.setText(Double.toString(circle.getCenter().getNorth()));
                latEastField.setText(Double.toString(circle.getCenter().getEast()));
                break;
            case LLA:
                lonNorth.setText("lon");
                latEast.setText("lat");
                lonNorthField.setText(Double.toString(circle.getCenter().getLongitude()));
                latEastField.setText(Double.toString(circle.getCenter().getLatitude()));
                break;
        }
        field_radius.setText(Double.toString(Math.abs(circle.getRadius())));
        if(Math.signum(circle.getRadius()) > 0) {
            box_orientation.getSelectionModel().select(0);
        } else {
            box_orientation.getSelectionModel().select(1);
        }
    }

    public void setCreateMode(boolean b) {
        btn_ok.setVisible(!b);
        btn_next.setVisible(!b);
        btn_append.setVisible(b);
        btn_choose_emplacement.setVisible(b);
    }

    @FXML
    public void onMouseClickedOK(MouseEvent mouseEvent) {
        hide();

    }

    @FXML
    public void onMouseClickedNext(MouseEvent mouseEvent) {
        System.out.println("next");
    }

    @FXML
    public void onMouseClickedAppend(MouseEvent mouseEvent) {
        hide();
        SkycEvent event = new SkycEvent(SkycEvent.APPEND_INSTRUCTION);
        getPopup().fireEvent(event);

    }

    @FXML
    public void onMouseClickedChooseEmplacement(MouseEvent mouseEvent) {
        SkycEvent event = new SkycEvent(SkycEvent.CHOOSE_INSTRUCTION_EMPLACEMENT);
        getPopup().fireEvent(event);
    }
}
