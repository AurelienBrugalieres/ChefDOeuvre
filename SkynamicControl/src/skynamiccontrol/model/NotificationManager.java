package skynamiccontrol.model;

import skynamiccontrol.view.notifications.NotificationContainer;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Elodie on 20/02/2017.
 */
public class NotificationManager implements Observer{

    // private StatusListController statusListController;
    private NotificationContainer notificationContainer;
    private GCSModel model;

    public NotificationManager(GCSModel model) {
        this.model = model;
    }

    public void updateView(Aircraft aircraft) {

    }

    public void setStatusListContainer(NotificationContainer notificationContainer) {
        this.notificationContainer = notificationContainer;
    }

    public void createView(Aircraft aircraft) {
        notificationContainer.AddTab(aircraft);
    }


    @Override
    public void update(Observable observable, Object o) {

    }
}
