package skynamiccontrol.model;

import javafx.scene.control.Tab;
import skynamiccontrol.view.notifications.NotificationContainer;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Elodie on 20/02/2017.
 */
public class NotificationManager implements Observer {


    private NotificationContainer notificationContainer;
    private GCSModel model;

    public NotificationManager(GCSModel model) {
        this.model = model;
    }

    public void updateView(Aircraft aircraft) {

    }

    public void setNotificationContainer(NotificationContainer notificationContainer) {
        this.notificationContainer = notificationContainer;
        this.notificationContainer.setOnChangeTabListener(new NotificationContainer.ChangeTabListener() {
            @Override
            public void onChangeTab(Tab tab, Aircraft aircraft) {
                if (model != null && aircraft != null)
                    model.selectAircraft(aircraft);
            }
        });
    }

    public void createView(Aircraft aircraft) {
        notificationContainer.AddTab(aircraft);
    }


    @Override
    public void update(Observable observable, Object o) {

    }

    public void selectAircraft(Aircraft aircraft) {
        notificationContainer.selectAircraft(aircraft);
    }
}
