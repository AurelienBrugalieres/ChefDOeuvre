package skynamiccontrol.model;

import skynamiccontrol.Timeline.Timeline;
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
    }

    public void addAircraft(Aircraft aircraft) {
        timeline.addAircraft(aircraft);
    }


    @Override
    public void update(Observable observable, Object o) {

    }
}
