package skynamiccontrol.map.drawing;

import skynamiccontrol.map.AircraftZoomLayer;
import skynamiccontrol.map.DrawingMapEvent;

/**
 * Created by aurelien on 02/03/17.
 */
public interface DrawingStateMachine {
    void init(AircraftZoomLayer container);
    void handleEvent(DrawingMapEvent e);
    void paintInstruction();
}
