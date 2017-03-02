package skynamiccontrol.map.drawing;

import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;

/**
 * Created by aurelien on 01/03/17.
 */
public interface InstructionView {
    void paint(Pane parent, Point2D position);
    Point2D getPosition();
    void remove();
}
