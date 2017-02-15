package skynamiccontrol.model;

import skynamiccontrol.view.status.StatusListContainer;

import java.util.*;

/**
 * Created by Elodie on 14/02/2017.
 */
public class StatusManager extends Observable{


   // private StatusListController statusListController;
    private StatusListContainer statusListContainer;
    private GCSModel model;

    public StatusManager(GCSModel model1, StatusListContainer s) {
        this.model = model1;
        statusListContainer = s;
    }

    public void updateView(Aircraft aircraft) {

    }

    public void createView(Aircraft aircraft) {
        statusListContainer.addStatus(aircraft);
    }


}
