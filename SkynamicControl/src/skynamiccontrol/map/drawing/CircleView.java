package skynamiccontrol.map.drawing;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;
import skynamiccontrol.model.mission.Circle;

/**
 * Created by aurelien on 01/03/17.
 */
public class CircleView implements InstructionView {

    private Circle instruction;
    private Color color;
    private int radius;
    private Point2D centerPosition;
    private WaypointView waypointView;
    private Pane parent;
    private javafx.scene.shape.Circle circle;


    public CircleView() {
        this(null, 0, new Point2D(0, 0));
    }

    public CircleView(Circle instruction, int radius, Point2D centerPosition) {
        this.instruction = instruction;
        this.radius = radius;
        this.centerPosition = centerPosition;
        this.waypointView = new WaypointView(centerPosition);
    }

    @Override
    public void paint(Pane parent, Point2D position) {
        this.parent = parent;
        waypointView.paint(parent, position);
        circle = new javafx.scene.shape.Circle(position.getX(), position.getY(), radius);
        circle.setFill(Color.TRANSPARENT);
        if (color == null)
            circle.setStroke(Color.BLACK);
        else
            circle.setStroke(color);
        circle.setStrokeWidth(circle.getStrokeWidth()+2);
        parent.getChildren().add(circle);
    }

    public Circle getInstruction() {
        return instruction;
    }

    public void setInstruction(Circle instruction) {
        this.instruction = instruction;
    }

    public int getRadius() {
        return radius;
    }

    public Color getColor() {
        return color;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public Point2D getPosition() {
        return centerPosition;
    }

    @Override
    public void remove() {
        if (parent != null) {
            waypointView.remove();
            parent.getChildren().remove(circle);
        }
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof CircleView))
            return false;
        CircleView c = (CircleView)obj;
        return (c.radius == this.radius && c.centerPosition.equals(this.centerPosition));
    }
}
