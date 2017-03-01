package skynamiccontrol.model;

import javafx.scene.control.Tab;
import skynamiccontrol.timeline.Timeline;
import skynamiccontrol.view.notifications.NotificationContainer;
import skynamiccontrol.view.status.StatusListContainer;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Elodie on 14/02/2017.
 */
public class TimelineManager implements Observer{


   // private StatusListController statusListController;
    private Timeline timeline;
    private GCSModel model;

    public TimelineManager(GCSModel model) {
        this.model = model;
    }

    public void updateView(Aircraft aircraft) {

    }

    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
        this.timeline.setOnChangeTabListener(new Timeline.ChangeTabListener() {
            @Override
            public void onChangeTab(Tab tab, Aircraft aircraft) {
                if (model != null && aircraft != null)
                    model.selectAircraft(aircraft);
            }
        });
    }

    public void addAircraft(Aircraft aircraft) {
        timeline.addAircraft(aircraft);
    }


    @Override
    public void update(Observable observable, Object o) {

    }

    public void selectAircraft(Aircraft aircraft) {
        timeline.selectAircraft(aircraft);
    }
}
