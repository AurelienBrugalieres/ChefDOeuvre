package skynamiccontrol.map.drawing;

import skynamiccontrol.map.AircraftZoomLayer;
import skynamiccontrol.map.DrawingMapEvent;
import skynamiccontrol.model.Aircraft;

/**
 * Created by aurelien on 02/03/17.
 */
public interface DrawingStateMachine {
    void init(AircraftZoomLayer container);
    void handleEvent(DrawingMapEvent e, Aircraft aircraft);
    void paintInstruction();
}
