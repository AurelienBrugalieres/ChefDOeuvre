package skynamiccontrol.model;

import skynamiccontrol.map.Map;
import skynamiccontrol.view.status.StatusListContainer;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Elodie on 14/02/2017.
 */
public class MapManager implements Observer{


   // private StatusListController statusListController;
    private Map map;
    private GCSModel model;

    public MapManager(GCSModel model) {
        this.model = model;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    @Override
    public void update(Observable observable, Object o) {

    }

    public void addAircraft(Aircraft aircraft) {
        map.addAircraft(aircraft);
    }
}
