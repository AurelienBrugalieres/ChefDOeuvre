package skynamiccontrol.view.forms;

import javafx.stage.Popup;

/**
 * Created by fabien on 27/02/17.
 */
public abstract class AbstractForm {
    private Popup popup;

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
