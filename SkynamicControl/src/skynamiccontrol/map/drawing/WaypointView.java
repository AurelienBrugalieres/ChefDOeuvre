package skynamiccontrol.map.drawing;

import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * Created by aurelien on 01/03/17.
 */
public class WaypointView implements InstructionView {
    private Point2D leftTopCorner;
    private Pane parent;
    private ImageView waypointImg;

    public WaypointView() {
        this(new Point2D(0, 0));
    }

    public WaypointView(Point2D position) {
        this.leftTopCorner = position;
    }


    @Override
    public void paint(Pane parent, Point2D position) {
        this.parent = parent;
        waypointImg = new ImageView("resources/bitmaps/waypoint32x32.png");
        parent.getChildren().add(waypointImg);
        waypointImg.setTranslateX(position.getX()-waypointImg.getImage().getWidth()/2);
        waypointImg.setTranslateY(position.getY()-waypointImg.getImage().getHeight()/2);
    }

    @Override
    public Point2D getPosition() {
        return leftTopCorner;
    }

    @Override
    public void remove() {
        this.parent.getChildren().remove(waypointImg);
    }
}
