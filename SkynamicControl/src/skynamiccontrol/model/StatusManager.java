package skynamiccontrol.model;

import skynamiccontrol.view.status.StatusListContainer;

import java.util.*;

/**
 * Created by Elodie on 14/02/2017.
 */
public class StatusManager implements Observer{


   // private StatusListController statusListController;
    private StatusListContainer statusListContainer;
    private GCSModel model;

    public StatusManager(GCSModel model) {
        this.model = model;
    }

    public void updateView(Aircraft aircraft) {

    }

    public void setStatusListContainer(StatusListContainer statusListContainer) {
        this.statusListContainer = statusListContainer;
        this.statusListContainer.setStatusListener(new StatusListContainer.StatusListener() {
            @Override
            public void onDroneStatusClick(Aircraft aircraftClicked) {
                if (model != null && aircraftClicked != null)
                    model.selectAircraft(aircraftClicked);
            }
        });
    }

    public void createView(Aircraft aircraft) {
        statusListContainer.addStatus(aircraft);
    }


    @Override
    public void update(Observable observable, Object o) {

    }

    public void selectAircraft(Aircraft aircraft) {
        statusListContainer.selectAircraft(aircraft);
    }
}
