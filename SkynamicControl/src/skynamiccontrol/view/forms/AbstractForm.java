package skynamiccontrol.view.forms;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Popup;
import skynamiccontrol.model.Aircraft;

/**
 * Created by fabien on 27/02/17.
 */
public abstract class AbstractForm {
    private Popup popup;
    private Aircraft aircraft;

    public Aircraft getAircraft() {
        return aircraft;
    }

    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
    }

    public Popup getPopup() {
        return popup;
    }

    public void setPopup(Popup popup) {
        this.popup = popup;
        popup.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ESCAPE) {
                    hide();
                }
            }
        });
    }

    public void hide() {
        if (popup != null && popup.isShowing())
            popup.hide();
    }
}
