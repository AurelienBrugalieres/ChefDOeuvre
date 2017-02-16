package skynamiccontrol.view.palette;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import skynamiccontrol.core.PaletteStateMachine;

/**
 * Created by Elodie on 16/02/2017.
 */
public class PaletteController {

    @FXML
    private ImageView buttun_waypoint;

    @FXML
    private ImageView buttun_circle;

    @FXML
    private ImageView buttun_goto;

    @FXML
    private ImageView buttun_path;

    private PaletteStateMachine paletteStateMachine;

    public PaletteController() {
        paletteStateMachine = new PaletteStateMachine(this);
    }

    public void setHooverButtunWaypoint() {
        buttun_waypoint.setImage(new Image(getClass().getClassLoader().getResource("resources/bitmaps/WaypointHover.png").toExternalForm()));
    }

    public void setHooverButtunCircle() {
        buttun_circle.setImage(new Image(getClass().getClassLoader().getResource("resources/bitmaps/CircleButton.pngHover.png").toExternalForm()));
    }
    public void setHooverButtunGoTo() {
        buttun_goto.setImage(new Image(getClass().getClassLoader().getResource("resources/bitmaps/gotoWpButtonHover.png").toExternalForm()));
    }
    public void setHooverButtunPath() {
        buttun_path.setImage(new Image(getClass().getClassLoader().getResource("resources/bitmaps/pathButtonHover.png").toExternalForm()));
    }

    @FXML
    public void onMouseClicWaypointButtun(MouseEvent mouseEvent) {

    }

    @FXML
    public void onMouseClicCircleButtun(MouseEvent mouseEvent) {
    }

    @FXML
    public void onMouseClicGoToButtun(MouseEvent mouseEvent) {
    }


    @FXML
    public void onMouseClicPathButtun(MouseEvent mouseEvent) {
    }

    @FXML
    public void onMouseEnteredWaypointButtun(MouseEvent mouseEvent) {
        paletteStateMachine.mouseHooverWaypoint();
    }

    @FXML
    public void onMouseEnteredCircleButtun(MouseEvent mouseEvent) {
        paletteStateMachine.mouseHooverCircle();
    }

    @FXML
    public void onMouseEnteredGoToButtun(MouseEvent mouseEvent) {
        paletteStateMachine.mouseHooverGoTo();
    }

    @FXML
    public void onMouseEnteredPathButtun(MouseEvent mouseEvent) {
        paletteStateMachine.mouseHooverPath();
    }

    @FXML
    public void onMouseExitedWaypointButtun(MouseEvent mouseEvent) {
    }

    @FXML
    public void onMouseExitedCircleButtun(MouseEvent mouseEvent) {
    }

    @FXML
    public void onMouseExitedGoToButtun(MouseEvent mouseEvent) {
    }

    @FXML
    public void onMouseExitedPathButtun(MouseEvent mouseEvent) {
    }
}
