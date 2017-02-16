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

    private void pressWaypoint() {
        controller.setClicButtunWaypoint();
        controller.setCDefaultButtunCircle();
        controller.setDefaultButtunGoTo();
        controller.setDefaultButtunPath();
    }

    private void pressCircle() {
        controller.setClicButtunCircle();
        controller.setDefaultButtunWaypoint();
        controller.setDefaultButtunGoTo();
        controller.setDefaultButtunPath();
    }

    private void pressGoTo() {
        controller.setClicButtunGoTo();
        controller.setDefaultButtunWaypoint();
        controller.setCDefaultButtunCircle();
        controller.setDefaultButtunPath();
    }

    private void pressPath() {
        controller.setClicButtunPath();
        controller.setDefaultButtunWaypoint();
        controller.setDefaultButtunGoTo();
        controller.setCDefaultButtunCircle();
    }

    private void defaultButtuns() {
        controller.setDefaultButtunPath();
        controller.setDefaultButtunWaypoint();
        controller.setDefaultButtunGoTo();
        controller.setCDefaultButtunCircle();
    }

    public void mouseHooverWaypoint() {
        switch (state) {
            case IDLE:
                controller.setHooverButtunWaypoint();
                state = PalettesStates.WAYPOINT_HOVER;
                break;
            case CIRCLE_CLIC:
                controller.setHooverButtunWaypoint();
                state = PalettesStates.CIRCLE_CLIC;
                break;
            case CIRCLE_HOVER:
                controller.setHooverButtunWaypoint();
                controller.setCDefaultButtunCircle();
                state = PalettesStates.WAYPOINT_HOVER;
                break;
            case GOTO_CLIC:
                controller.setHooverButtunWaypoint();
                state = PalettesStates.GOTO_CLIC;
                break;
            case GOTO_HOVER:
                controller.setDefaultButtunGoTo();
                controller.setHooverButtunWaypoint();
                state = PalettesStates.WAYPOINT_HOVER;
                break;
            case PATH_CLIC:
                controller.setHooverButtunWaypoint();
                state = PalettesStates.PATH_CLIC;
                break;
            case PATH_HOVER:
                controller.setDefaultButtunPath();
                controller.setHooverButtunWaypoint();
                state = PalettesStates.WAYPOINT_HOVER;
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
                controller.setHooverButtunCircle();
                state = PalettesStates.GOTO_CLIC;
                break;
            case GOTO_HOVER:
                controller.setDefaultButtunGoTo();
                controller.setHooverButtunCircle();
                state = PalettesStates.CIRCLE_HOVER;
                break;
            case PATH_CLIC:
                controller.setHooverButtunCircle();
                state = PalettesStates.PATH_CLIC;
                break;
            case PATH_HOVER:
                controller.setDefaultButtunPath();
                controller.setHooverButtunCircle();
                state = PalettesStates.CIRCLE_HOVER;
                break;
            case WAYPOINT_CLIC:
                controller.setHooverButtunCircle();
                state = PalettesStates.WAYPOINT_CLIC;
                break;
            case WAYPOINT_HOVER:
                controller.setDefaultButtunWaypoint();
                controller.setHooverButtunCircle();
                state = PalettesStates.CIRCLE_HOVER;
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
                controller.setHooverButtunPath();
                state = PalettesStates.CIRCLE_CLIC;
                break;
            case CIRCLE_HOVER:
                controller.setCDefaultButtunCircle();
                controller.setHooverButtunPath();
                state = PalettesStates.PATH_HOVER;
                break;
            case GOTO_CLIC:
                controller.setHooverButtunPath();
                state = PalettesStates.GOTO_CLIC;
                break;
            case GOTO_HOVER:
                controller.setDefaultButtunGoTo();
                controller.setHooverButtunPath();
                state = PalettesStates.PATH_HOVER;
                break;
            case PATH_CLIC:
                break;
            case PATH_HOVER:
                break;
            case WAYPOINT_CLIC:
                controller.setHooverButtunPath();
                state = PalettesStates.WAYPOINT_CLIC;
                break;
            case WAYPOINT_HOVER:
                controller.setDefaultButtunWaypoint();
                controller.setHooverButtunPath();
                state = PalettesStates.PATH_HOVER;
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
                controller.setHooverButtunGoTo();
                state = PalettesStates.CIRCLE_CLIC;
                break;
            case CIRCLE_HOVER:
                controller.setCDefaultButtunCircle();
                controller.setHooverButtunGoTo();
                state = PalettesStates.GOTO_HOVER;
                break;
            case GOTO_CLIC:
                break;
            case GOTO_HOVER:
                break;
            case PATH_CLIC:
                controller.setHooverButtunGoTo();
                state = PalettesStates.PATH_CLIC;
                break;
            case PATH_HOVER:
                controller.setDefaultButtunPath();
                controller.setHooverButtunGoTo();
                state = PalettesStates.GOTO_HOVER;
                break;
            case WAYPOINT_CLIC:
                controller.setHooverButtunGoTo();
                state = PalettesStates.WAYPOINT_CLIC;
                break;
            case WAYPOINT_HOVER:
                controller.setDefaultButtunWaypoint();
                controller.setHooverButtunGoTo();
                state = PalettesStates.GOTO_HOVER;
                break;
        }
    }

    public void mouseClicWaypoint() {
        switch (state) {
            case IDLE:
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
                defaultButtuns();
                controller.setHooverButtunWaypoint();
                state = PalettesStates.WAYPOINT_HOVER;
                break;
            case WAYPOINT_HOVER:
                pressWaypoint();
                state = PalettesStates.WAYPOINT_CLIC;
                break;
        }
    }

    public void mouseClicCircle() {
        switch (state) {
            case IDLE:
                break;
            case CIRCLE_CLIC:
                defaultButtuns();
                controller.setHooverButtunCircle();
                state = PalettesStates.CIRCLE_HOVER;
                break;
            case CIRCLE_HOVER:
                pressCircle();
                state = PalettesStates.CIRCLE_CLIC;
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

    public void mouseClicPath() {
        switch (state) {
            case IDLE:
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
                defaultButtuns();
                controller.setDefaultButtunPath();
                state = PalettesStates.PATH_HOVER;
                break;
            case PATH_HOVER:
                pressPath();
                state = PalettesStates.PATH_CLIC;
                break;
            case WAYPOINT_CLIC:
                break;
            case WAYPOINT_HOVER:
                break;
        }
    }

    public void mouseClicGoTo() {
        switch (state) {
            case IDLE:
                break;
            case CIRCLE_CLIC:
                break;
            case CIRCLE_HOVER:
                break;
            case GOTO_CLIC:
                defaultButtuns();
                controller.setHooverButtunGoTo();
                state= PalettesStates.GOTO_HOVER;
                break;
            case GOTO_HOVER:
                pressGoTo();
                state = PalettesStates.GOTO_CLIC;
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

    public void mouseExitedWaypoint() {
        switch (state) {
            case IDLE:
                break;
            case CIRCLE_CLIC:
                controller.setDefaultButtunWaypoint();
                state = PalettesStates.CIRCLE_CLIC;
                break;
            case CIRCLE_HOVER:
                break;
            case GOTO_CLIC:
                controller.setDefaultButtunWaypoint();
                state = PalettesStates.GOTO_CLIC;
                break;
            case GOTO_HOVER:
                break;
            case PATH_CLIC:
                controller.setDefaultButtunWaypoint();
                state = PalettesStates.PATH_CLIC;
                break;
            case PATH_HOVER:
                break;
            case WAYPOINT_CLIC:
                break;
            case WAYPOINT_HOVER:
                controller.setDefaultButtunWaypoint();
                state = PalettesStates.IDLE;
                break;
        }
    }

    public void mouseExitedCircle() {
        switch (state) {
            case IDLE:
                break;
            case CIRCLE_CLIC:
                break;
            case CIRCLE_HOVER:
                controller.setCDefaultButtunCircle();
                state = PalettesStates.IDLE;
                break;
            case GOTO_CLIC:
                controller.setCDefaultButtunCircle();
                state = PalettesStates.GOTO_CLIC;
                break;
            case GOTO_HOVER:
                break;
            case PATH_CLIC:
                controller.setCDefaultButtunCircle();
                state = PalettesStates.PATH_CLIC;
                break;
            case PATH_HOVER:
                break;
            case WAYPOINT_CLIC:
                controller.setCDefaultButtunCircle();
                state = PalettesStates.WAYPOINT_CLIC;
                break;
            case WAYPOINT_HOVER:

                break;
        }
    }

    public void mouseExitedGoTo() {
        switch (state) {
            case IDLE:
                break;
            case CIRCLE_CLIC:
                controller.setDefaultButtunGoTo();
                state = PalettesStates.CIRCLE_CLIC;
                break;
            case CIRCLE_HOVER:
                break;
            case GOTO_CLIC:
                break;
            case GOTO_HOVER:
                controller.setDefaultButtunGoTo();
                state = PalettesStates.IDLE;
                break;
            case PATH_CLIC:
                controller.setDefaultButtunGoTo();
                state = PalettesStates.PATH_CLIC;
                break;
            case PATH_HOVER:
                break;
            case WAYPOINT_CLIC:
                controller.setDefaultButtunGoTo();
                state = PalettesStates.WAYPOINT_CLIC;
                break;
            case WAYPOINT_HOVER:

                break;
        }
    }

    public void mouseExitedPath() {
        switch (state) {
            case IDLE:
                break;
            case CIRCLE_CLIC:
                controller.setDefaultButtunPath();
                state = PalettesStates.CIRCLE_CLIC;
                break;
            case CIRCLE_HOVER:
                break;
            case GOTO_CLIC:
                controller.setDefaultButtunPath();
                state = PalettesStates.GOTO_CLIC;
                break;
            case GOTO_HOVER:
                break;
            case PATH_CLIC:
                break;
            case PATH_HOVER:
                controller.setDefaultButtunPath();
                state = PalettesStates.IDLE;
                break;
            case WAYPOINT_CLIC:
                controller.setDefaultButtunPath();
                state = PalettesStates.WAYPOINT_CLIC;
                break;
            case WAYPOINT_HOVER:

                break;
        }
    }

}
