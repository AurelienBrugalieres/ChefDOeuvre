package skynamiccontrol.map;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import skynamiccontrol.map.drawing.InstructionView;

import java.util.List;

import static com.sun.javafx.util.Utils.clamp;

/**
 * Created by fabien on 01/03/17.
 */
public class AircraftZoomLayer extends Pane {
    private int zoom;
    private ImageView aircraftIcon;
    private ImageView aircraftOutIcon;
    private InstructionView ghost = null;
    private List<InstructionView> instructionViewList = null;
    private Canvas canvas;



    public AircraftZoomLayer(int zoom, String aircraftColor) {
        canvas = new Canvas(getWidth(), getHeight());
        getChildren().add(canvas);
        widthProperty().addListener(e -> canvas.setWidth(getWidth()));
        heightProperty().addListener(e -> canvas.setHeight(getHeight()));
        this.zoom = zoom;
        aircraftIcon = new ImageView("resources/bitmaps/aircraft.png");
        aircraftOutIcon = new ImageView("resources/bitmaps/aircraftOut.png");
        Color color = Color.web(aircraftColor);
        ColorAdjust colorAdjust = new ColorAdjust();
        double hue = ((color.getHue() + 180) % 360);
        colorAdjust.setHue(hue/360);
        aircraftIcon.setEffect(colorAdjust);
        aircraftOutIcon.setEffect(colorAdjust);
        this.getChildren().add(aircraftIcon);
        this.getChildren().add(aircraftOutIcon);
        aircraftOutIcon.setVisible(false);
        this.setVisible(false);
    }

    public void setAircraftPosition(GPSCoordinate aircraftCoordinates, double heading) {
        XYZCoordinate xyzCoordinate = aircraftCoordinates.toXYCoordinates(zoom);
        aircraftIcon.setRotate(heading);
        double x = xyzCoordinate.getX() * BackMapLayer.TILE_DIMENSION - aircraftIcon.getImage().getWidth()/2;
        double y = xyzCoordinate.getY() * BackMapLayer.TILE_DIMENSION - aircraftIcon.getImage().getHeight()/2;
        aircraftIcon.setTranslateX(x);
        aircraftIcon.setTranslateY(y);
        Point2D  pt =this.localToScene(x,y);
        double width = getScene().getWidth();
        double height = getScene().getHeight();
        if(pt.getX() < 0 ||
                pt.getY() < 0 ||
                pt.getX() > width ||
                pt.getY() > height) {
            double dy = height/2 - pt.getY();
            double dx = pt.getX() - width/2;
            double angle = Math.atan2(dy, dx);
            double angleUpRight = Math.atan2(height/2, width/2);
            double angleUpLeft = Math.atan2(height/2, -width/2);
            double angleBottomRight = Math.atan2(-height/2, width/2);
            double angleBottomLeft = Math.atan2(-height/2, -width/2);
            System.out.println(angle + " " + angleUpRight + " " + angleUpLeft  + " " + angleBottomLeft + " " + angleBottomRight);
            Point2D outAircraft;
            if(angle > angleUpRight && angle < angleUpLeft) {
                //haut
                outAircraft = new Point2D(width/2 + (height/2)/Math.tan(angle), 0);
            } else if (angle > angleUpLeft && angle < angleBottomLeft) {
                //gauche
                outAircraft = new Point2D(0, 0);
            } else if (angle > angleBottomLeft && angle < angleBottomRight) {
                //bas
                outAircraft = new Point2D(0, 0);
            } else {
                //droite
                outAircraft = new Point2D(0, 0);
            }

            aircraftOutIcon.setVisible(true);
            Point2D newPt = this.sceneToLocal(outAircraft);
            aircraftOutIcon.setTranslateX(newPt.getX() - aircraftOutIcon.getImage().getWidth()/2);
            aircraftOutIcon.setTranslateY(newPt.getY() - aircraftOutIcon.getImage().getHeight()/2);
            aircraftOutIcon.setRotate(Math.toDegrees(-angle));

        } else {
            aircraftOutIcon.setVisible(false);
        }
    }

    public void setInstructionViewList(List<InstructionView> list) {
        this.instructionViewList = list;
    }

    public void changeScale(double scale) {
        aircraftIcon.setScaleX(1/scale);
        aircraftIcon.setScaleY(1/scale);
        aircraftOutIcon.setScaleX(1/scale);
        aircraftOutIcon.setScaleY(1/scale);
    }

    public void repaint() {
        for (InstructionView instructionView : instructionViewList) {
            double newX = instructionView.getPosition().getX()*Math.pow(2, zoom);
            double newY = instructionView.getPosition().getY()*Math.pow(2, zoom);
            instructionView.paint(this, new Point2D(newX, newY));
        }
    }

    public int getZoom() {
        return zoom;
    }
}
