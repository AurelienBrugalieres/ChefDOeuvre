package skynamiccontrol.core;

import javafx.scene.layout.BorderPane;

import java.awt.*;

/**
 * Created by Elodie on 15/02/2017.
 */
public class StatusStateMachine {

    private Color color;

    private enum States {
        IDLE, PRESSED
    }

    private States state = States.IDLE;

    public StatusStateMachine(Color aircraft_color) {
        color = aircraft_color;
    }

    public void onMouseClick(BorderPane pane) {
        switch (state) {
            case IDLE:
                state = States.PRESSED;
                pane.setStyle("-fx-background-color: rgba("+color.getRed()+","+color.getGreen()+","+color.getBlue()+",0.7);");
                break;
            case PRESSED:
                state = States.IDLE;
                pane.setStyle("-fx-background-color: null");
                break;
        }
    }
}
