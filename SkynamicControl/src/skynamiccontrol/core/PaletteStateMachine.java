package skynamiccontrol.core;

import skynamiccontrol.view.palette.PaletteController;

/**
 * Created by Elodie on 16/02/2017.
 */
public class PaletteStateMachine {

    private PalettesStates state;
    private PaletteController controller;

    public PaletteStateMachine(PaletteController paletteController) {
        state = PalettesStates.IDLE;
        controller = paletteController;
    }

    public void mouseHooverWaypoint() {
        switch (state) {
            case IDLE:
                controller.setHooverButtunWaypoint();
                state = PalettesStates.WAYPOINT_HOVER;
                break;
            case CIRCLE_CLIC:
                break;
            case CIRCLE_HOVER:
                break;
            case GOTO_CLIC:
                break;
            case GOTO_HOVER:
                break;
            case PATH_CLIC:
                break;
            case PATH_HOVER:
                break;
            case WAYPOINT_CLIC:
                break;
            case WAYPOINT_HOVER:
                break;
        }
    }

    public void mouseHooverCircle() {
        switch (state) {
            case IDLE:
                controller.setHooverButtunCircle();
                state = PalettesStates.CIRCLE_HOVER;
                break;
            case CIRCLE_CLIC:
                break;
            case CIRCLE_HOVER:
                break;
            case GOTO_CLIC:
                break;
            case GOTO_HOVER:
                break;
            case PATH_CLIC:
                break;
            case PATH_HOVER:
                break;
            case WAYPOINT_CLIC:
                break;
            case WAYPOINT_HOVER:
                break;
        }
    }

    public void mouseHooverPath() {
        switch (state) {
            case IDLE:
                controller.setHooverButtunPath();
                state = PalettesStates.PATH_HOVER;
                break;
            case CIRCLE_CLIC:
                break;
            case CIRCLE_HOVER:
                break;
            case GOTO_CLIC:
                break;
            case GOTO_HOVER:
                break;
            case PATH_CLIC:
                break;
            case PATH_HOVER:
                break;
            case WAYPOINT_CLIC:
                break;
            case WAYPOINT_HOVER:
                break;
        }
    }

    public void mouseHooverGoTo() {
        switch (state) {
            case IDLE:
                controller.setHooverButtunGoTo();
                state = PalettesStates.GOTO_HOVER;
                break;
            case CIRCLE_CLIC:
                break;
            case CIRCLE_HOVER:
                break;
            case GOTO_CLIC:
                break;
            case GOTO_HOVER:
                break;
            case PATH_CLIC:
                break;
            case PATH_HOVER:
                break;
            case WAYPOINT_CLIC:
                break;
            case WAYPOINT_HOVER:
                break;
        }
    }
}
