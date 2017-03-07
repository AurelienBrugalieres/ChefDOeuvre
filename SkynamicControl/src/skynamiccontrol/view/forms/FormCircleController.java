package skynamiccontrol.view.forms;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import skynamiccontrol.SkycEvent;
import skynamiccontrol.communication.IvyManager;
import skynamiccontrol.model.Aircraft;
import skynamiccontrol.model.Waypoint;
import skynamiccontrol.model.mission.Circle;
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
    private TextField field_altitude;

    @FXML
    private Text latEastText;

    @FXML
    private Text lonNorthText;

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

    @FXML
    private Rectangle backgroungRectangle;

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

        btn_append.initButton("/resources/bitmaps/append_normal.png",
                "/resources/bitmaps/append_entered.png",
                "/resources/bitmaps/append_pressed.png");
        btn_choose_emplacement.initButton("/resources/bitmaps/choose_normal.png",
                "/resources/bitmaps/choose_entered.png",
                "/resources/bitmaps/choose_pressed.png");
        box_orientation.getItems().addAll("clockwise", "counterclockwise");
    }

    public void setCircle(Circle circle, boolean isNewInstruction, Aircraft aircraft) {
        this.setAircraft(aircraft);
        this.circle = circle;
        setCreateMode(isNewInstruction);
        field_name.setText(circle.getName());
        switch (circle.getCenter().getCoordinateSystem()) {
            case LOCAL:
                lonNorthText.setText("North");
                latEastText.setText("East");
                lonNorthField.setText(Double.toString(circle.getCenter().getNorth()));
                latEastField.setText(Double.toString(circle.getCenter().getEast()));
                break;
            case LLA:
                lonNorthText.setText("lon");
                latEastText.setText("lat");
                lonNorthField.setText(Double.toString(circle.getCenter().getLongitude()));
                latEastField.setText(Double.toString(circle.getCenter().getLatitude()));
                break;
        }
        field_radius.setText(Double.toString(Math.abs(circle.getRadius())));
        field_altitude.setText(Double.toString(Math.abs(circle.getAltitude())));
        if(Math.signum(circle.getRadius()) > 0) {
            box_orientation.getSelectionModel().select(0);
        } else {
            box_orientation.getSelectionModel().select(1);
        }

        Color color =Color.web(aircraft.getColor()).deriveColor(0, 0.5, 1.5, 1);
        backgroungRectangle.setFill(color);

    }

    public void setCreateMode(boolean b) {
        if(b) {
            form_pane.getChildren().removeAll(btn_next, btn_ok);
        } else {
            form_pane.getChildren().removeAll(btn_append, btn_choose_emplacement);
        }
    }

    @FXML
    public void onMouseClickedOK(MouseEvent mouseEvent) {
        hide();
    }

    @FXML
    public void onMouseClickedNext(MouseEvent mouseEvent) {
        getAircraft().getMissionManager().goToNextInstruction();
        hide();
    }

    @FXML
    public void onMouseClickedAppend(MouseEvent mouseEvent) {

        try {
            String name = field_name.getText();
            double lonN = Double.parseDouble(lonNorthField.getText());
            double latE = Double.parseDouble(latEastField.getText());
            double radius = Double.parseDouble(field_radius.getText());
            double altitude = Double.parseDouble(field_altitude.getText());
            circle.setCenter(new Waypoint(latE, lonN, altitude, circle.getCenter().getCoordinateSystem()));
            if(box_orientation.getSelectionModel().getSelectedIndex() == 1) {
                radius = -radius;
            }
            circle.setName(name);
            circle.setRadius(radius);

            getAircraft().getMissionManager().addInstruction(circle);
            hide();
        } catch (Exception e) {

        }


        //TODO : modify parameters.

        //SkycEvent event = new SkycEvent(SkycEvent.APPEND_INSTRUCTION);
        //getPopup().fireEvent(event);

    }

    @FXML
    public void onMouseClickedChooseEmplacement(MouseEvent mouseEvent) {
        SkycEvent event = new SkycEvent(SkycEvent.CHOOSE_INSTRUCTION_EMPLACEMENT);
        getPopup().fireEvent(event);
    }
}
