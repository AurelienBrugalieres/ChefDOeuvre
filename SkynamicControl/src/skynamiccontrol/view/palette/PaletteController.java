package skynamiccontrol.view.palette;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Elodie on 16/02/2017.
 */
public class PaletteController implements Initializable {

    private enum PaletteState {
        IDLE(false, false, false, false),
        WAYPOINT(true, false, false, false),
        CIRCLE(false, true, false,false),
        GOTO(false, false, true, false),
        PATH(false, false, false, true);

        public final boolean waypointEnabled;
        public final boolean circleEnabled;
        public final boolean gotoEnabled;
        public final boolean pathEnabled;

        PaletteState(boolean waypointEnabled, boolean circleEnabled, boolean gotoEnabled, boolean pathEnabled) {
            this.waypointEnabled = waypointEnabled;
            this.circleEnabled = circleEnabled;
            this.gotoEnabled = gotoEnabled;
            this.pathEnabled = pathEnabled;
        }
    }
    private PaletteState currentState;

    public interface PaletteListener {
        void onWaypointButtonClick();
        void onPathButtonClick();
        void onGoToButtonClick();
        void onCircleButtonClick();
    }
    private PaletteListener paletteListener = null;

    @FXML
    VBox vbox;

    @FXML
    private ImageView button_waypoint;

    @FXML
    private ImageView button_circle;

    @FXML
    private ImageView button_goto;

    @FXML
    private ImageView button_path;


    private void goToState(PaletteState state) {
        currentState = state;
        activeButtons();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        goToState(PaletteState.IDLE);
    }

    private void activeButtons() {
        if (currentState.waypointEnabled) {
            setClickButtonWaypoint();
        } else {
            setDefaultButtonWaypoint();
        }
        if (currentState.circleEnabled) {
            setClickButtonCircle();
        } else {
            setDefaultButtonCircle();
        }
        if (currentState.gotoEnabled) {
            setClickButtonGoto();
        } else {
            setDefaultButtonGoto();
        }
        if (currentState.pathEnabled) {
            setClickButtonPath();
        } else {
            setDefaultButtonPath();
        }
    }

    public void setOverButtonWaypoint() {
        button_waypoint.setImage(new Image(getClass().getClassLoader().getResource("resources/bitmaps/WaypointHover.png").toExternalForm()));
    }

    public void setOverButtonCircle() {
        button_circle.setImage(new Image(getClass().getClassLoader().getResource("resources/bitmaps/CircleButton.pngHover.png").toExternalForm()));
    }
    public void setOverButtonGoto() {
        button_goto.setImage(new Image(getClass().getClassLoader().getResource("resources/bitmaps/gotoWpButtonHover.png").toExternalForm()));
    }
    public void setOverButtonPath() {
        button_path.setImage(new Image(getClass().getClassLoader().getResource("resources/bitmaps/pathButtonHover.png").toExternalForm()));
    }

    public void setClickButtonWaypoint() {
        button_waypoint.setImage(new Image(getClass().getClassLoader().getResource("resources/bitmaps/WaypointPressed.png").toExternalForm()));
    }

    public void setClickButtonCircle() {
        button_circle.setImage(new Image(getClass().getClassLoader().getResource("resources/bitmaps/CircleButton.pngPressed.png").toExternalForm()));
    }
    public void setClickButtonGoto() {
        button_goto.setImage(new Image(getClass().getClassLoader().getResource("resources/bitmaps/gotoWpButtonPressed.png").toExternalForm()));
    }
    public void setClickButtonPath() {
        button_path.setImage(new Image(getClass().getClassLoader().getResource("resources/bitmaps/pathButtonPressed.png").toExternalForm()));
    }

    public void setDefaultButtonWaypoint() {
        button_waypoint.setImage(new Image(getClass().getClassLoader().getResource("resources/bitmaps/waypoint.png").toExternalForm()));
    }

    public void setDefaultButtonCircle() {
        button_circle.setImage(new Image(getClass().getClassLoader().getResource("resources/bitmaps/CircleButton.png").toExternalForm()));
    }
    public void setDefaultButtonGoto() {
        button_goto.setImage(new Image(getClass().getClassLoader().getResource("resources/bitmaps/gotoWpButton.png").toExternalForm()));
    }
    public void setDefaultButtonPath() {
        button_path.setImage(new Image(getClass().getClassLoader().getResource("resources/bitmaps/pathButton.png").toExternalForm()));
    }
    @FXML
    public void onMouseClickedWaypointButton(MouseEvent mouseEvent) {
        switch (currentState) {
            case IDLE:
                goToState(PaletteState.WAYPOINT);
                break;
            case WAYPOINT:
                goToState(PaletteState.IDLE);
                break;
            case CIRCLE:
                goToState(PaletteState.WAYPOINT);
                break;
            case GOTO:
                goToState(PaletteState.WAYPOINT);
                break;
            case PATH:
                goToState(PaletteState.WAYPOINT);
                break;
        }
        if (paletteListener != null)
            paletteListener.onWaypointButtonClick();
    }

    @FXML
    public void onMouseClickedCircleButton(MouseEvent mouseEvent) {
        switch (currentState) {
            case IDLE:
                goToState(PaletteState.CIRCLE);
                break;
            case WAYPOINT:
                goToState(PaletteState.CIRCLE);
                break;
            case CIRCLE:
                goToState(PaletteState.IDLE);
                break;
            case GOTO:
                goToState(PaletteState.CIRCLE);
                break;
            case PATH:
                goToState(PaletteState.CIRCLE);
                break;
        }
        if (paletteListener != null)
            paletteListener.onCircleButtonClick();
    }

    @FXML
    public void onMouseClickedGoToButton(MouseEvent mouseEvent) {
        switch (currentState) {
            case IDLE:
                goToState(PaletteState.GOTO);
                break;
            case WAYPOINT:
                goToState(PaletteState.GOTO);
                break;
            case CIRCLE:
                goToState(PaletteState.GOTO);
                break;
            case GOTO:
                goToState(PaletteState.IDLE);
                break;
            case PATH:
                goToState(PaletteState.GOTO);
                break;
        }
        if (paletteListener != null)
            paletteListener.onGoToButtonClick();
    }


    @FXML
    public void onMouseClickedPathButton(MouseEvent mouseEvent) {
        switch (currentState) {
            case IDLE:
                goToState(PaletteState.PATH);
                break;
            case WAYPOINT:
                goToState(PaletteState.PATH);
                break;
            case CIRCLE:
                goToState(PaletteState.PATH);
                break;
            case GOTO:
                goToState(PaletteState.PATH);
                break;
            case PATH:
                goToState(PaletteState.IDLE);
                break;
        }
        if (paletteListener != null)
            paletteListener.onPathButtonClick();
    }

    @FXML
    public void onMouseEnteredWaypointButton(MouseEvent mouseEvent) {
        switch (currentState) {
            case IDLE:
                setOverButtonWaypoint();
                break;
            case WAYPOINT:
                break;
            case CIRCLE:
                setOverButtonWaypoint();
                break;
            case GOTO:
                setOverButtonWaypoint();
                break;
            case PATH:
                setOverButtonWaypoint();
                break;
        }
    }

    @FXML
    public void onMouseEnteredCircleButton(MouseEvent mouseEvent) {
        switch (currentState) {
            case IDLE:
                setOverButtonCircle();
                break;
            case WAYPOINT:
                setOverButtonCircle();
                break;
            case CIRCLE:
                break;
            case GOTO:
                setOverButtonCircle();
                break;
            case PATH:
                setOverButtonCircle();
                break;
        }
    }

    @FXML
    public void onMouseEnteredGoToButton(MouseEvent mouseEvent) {
        switch (currentState) {
            case IDLE:
                setOverButtonGoto();
                break;
            case WAYPOINT:
                setOverButtonGoto();
                break;
            case CIRCLE:
                setOverButtonGoto();
                break;
            case GOTO:
                break;
            case PATH:
                setOverButtonGoto();

        }
    }

    @FXML
    public void onMouseEnteredPathButton(MouseEvent mouseEvent) {
        switch (currentState) {
            case IDLE:
                setOverButtonPath();
                break;
            case WAYPOINT:
                setOverButtonPath();
                break;
            case CIRCLE:
                setOverButtonPath();
                break;
            case GOTO:
                setOverButtonPath();
                break;
            case PATH:
                break;
        }
    }

    @FXML
    public void onMouseExitedWaypointButton(MouseEvent mouseEvent) {
        switch (currentState) {
            case IDLE:
                setDefaultButtonWaypoint();
                break;
            case WAYPOINT:
                break;
            case CIRCLE:
                setDefaultButtonWaypoint();
                break;
            case GOTO:
                setDefaultButtonWaypoint();
                break;
            case PATH:
                setDefaultButtonWaypoint();
                break;
        }
    }

    @FXML
    public void onMouseExitedCircleButton(MouseEvent mouseEvent) {
        switch (currentState) {
            case IDLE:
                setDefaultButtonCircle();
                break;
            case WAYPOINT:
                setDefaultButtonCircle();
                break;
            case CIRCLE:
                break;
            case GOTO:
                setDefaultButtonCircle();
                break;
            case PATH:
                setDefaultButtonCircle();
                break;
        }
    }

    @FXML
    public void onMouseExitedGoToButton(MouseEvent mouseEvent) {
        switch (currentState) {
            case IDLE:
                setDefaultButtonGoto();
                break;
            case WAYPOINT:
                setDefaultButtonGoto();
                break;
            case CIRCLE:
                setDefaultButtonGoto();
                break;
            case GOTO:
                break;
            case PATH:
                setDefaultButtonGoto();
                break;
        }
    }

    @FXML
    public void onMouseExitedPathButton(MouseEvent mouseEvent) {
        switch (currentState) {
            case IDLE:
                setDefaultButtonPath();
                break;
            case WAYPOINT:
                setDefaultButtonPath();
                break;
            case CIRCLE:
                setDefaultButtonPath();
                break;
            case GOTO:
                setDefaultButtonPath();
                break;
            case PATH:
                break;
        }
    }

    public void setPaletteListener(PaletteListener paletteListener) {
        this.paletteListener = paletteListener;
    }

    public boolean isPaletteActive() {
        return (currentState.circleEnabled||currentState.pathEnabled||currentState.gotoEnabled||currentState.waypointEnabled);
    }

    public void setTranslateY(double translate) {
        this.vbox.setTranslateY(translate);
    }

    public double getHeigth() {
        return this.vbox.getHeight();
    }
}
