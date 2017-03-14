package skynamiccontrol.model;

import skynamiccontrol.view.status.StatusListContainer;

import java.util.*;

/**
 * Created by Elodie on 14/02/2017.
 * Manage the comportement of the list of strips.
 */
public class StatusManager implements Observer{

    //List of strips.
    private StatusListContainer statusListContainer;
    //Model
    private GCSModel model;

    public StatusManager(GCSModel model) {
        this.model = model;
    }

    public void updateView(Aircraft aircraft) {

    }

    /**
     * Set the list of strips.
     * @param statusListContainer
     * List of strips.
     */
    public void setStatusListContainer(StatusListContainer statusListContainer) {
        this.statusListContainer = statusListContainer;
        //Set a listener to select a strip/ a drone
        this.statusListContainer.setStatusListener(new StatusListContainer.StatusListener() {
            @Override
            public void onDroneStatusClick(Aircraft aircraftClicked) {
                if (model != null && aircraftClicked != null)
                    model.selectAircraft(aircraftClicked);
            }
        });
    }

    /**
     * Create a view for an aircraft.
     * @param aircraft
     * the new aircraft
     */
    public void createView(Aircraft aircraft) {
        statusListContainer.addStatus(aircraft);
    }


    @Override
    public void update(Observable observable, Object o) {

    }

    /**
     * Select the strip of the aircraft.
     * @param aircraft
     * aircraft selected.
     */
    public void selectAircraft(Aircraft aircraft) {
        statusListContainer.selectAircraft(aircraft);
    }
}
