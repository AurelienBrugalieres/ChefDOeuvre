package skynamiccontrol.core;

import javafx.scene.layout.BorderPane;
import skynamiccontrol.view.status.StatusContainer;
import skynamiccontrol.view.status.StatusListContainer;

import java.awt.*;
import java.util.*;

/**
 * Created by Elodie on 15/02/2017.
 */
public class StatusStateMachine {


    private java.util.List<StatusContainer> list;
    private enum States {
        PRESSED
    }

    private States state = States.PRESSED;
    private StatusListContainer.StatusListener listener = null;

    public StatusStateMachine(java.util.List<StatusContainer> l) {
        list = l;
    }

    public void addStatus(StatusContainer status) {
        list.add(status);
    }
    public void onStatusSelected(StatusContainer statusContainer) {
        switch (state) {

            case PRESSED:
                for(StatusContainer s : list) {
                    s.setDeselected();
                }
                if(statusContainer.isSelected) {
                    state = States.PRESSED;
                    statusContainer.setDeselected();
                } else {
                    statusContainer.setSelected();
                    state = States.PRESSED;
                }

                //pane.setStyle("-fx-background-color: null");
                break;
        }
        if (listener != null)
            listener.onDroneStatusClick(statusContainer.getAircraft());
    }

    public void setStatusListener(StatusListContainer.StatusListener listener) {
        this.listener = listener;
    }
}
