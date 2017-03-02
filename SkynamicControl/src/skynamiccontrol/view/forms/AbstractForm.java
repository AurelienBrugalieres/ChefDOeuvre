package skynamiccontrol.view.forms;

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
    }

    public void hide() {
        popup.hide();
    }
}
