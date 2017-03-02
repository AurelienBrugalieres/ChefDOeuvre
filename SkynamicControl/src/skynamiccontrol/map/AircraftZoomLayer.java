package skynamiccontrol.map;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
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



    public AircraftZoomLayer(int zoom) {
        canvas = new Canvas(getWidth(), getHeight());
        getChildren().add(canvas);
        widthProperty().addListener(e -> canvas.setWidth(getWidth()));
        heightProperty().addListener(e -> canvas.setHeight(getHeight()));
        this.zoom = zoom;
        aircraftIcon = new ImageView("resources/bitmaps/aircraft.png");
        aircraftOutIcon = new ImageView("resources/bitmaps/aircraftOut.png");
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setHue(0.2);
        aircraftIcon.setEffect(colorAdjust);
        this.getChildren().add(aircraftIcon);
        this.getChildren().add(aircraftOutIcon);
        aircraftOutIcon.setVisible(false);
    }

    public void setAircraftPosition(GPSCoordinate aircraftCoordinates, double heading) {
        XYZCoordinate xyzCoordinate = aircraftCoordinates.toXYCoordinates(zoom);
        aircraftIcon.setRotate(heading);
        double x = xyzCoordinate.getX() * BackMapLayer.TILE_DIMENSION - aircraftIcon.getImage().getWidth()/2;
        double y = xyzCoordinate.getY() * BackMapLayer.TILE_DIMENSION - aircraftIcon.getImage().getHeight()/2;
        aircraftIcon.setTranslateX(x);
        aircraftIcon.setTranslateY(y);
        Point2D  pt =this.localToScene(x,y);
        if(pt.getX() < 0 ||
                pt.getY() < 0 ||
                pt.getX() > getScene().getWidth() ||
                pt.getY() > getScene().getHeight()) {
            aircraftOutIcon.setVisible(true);
            Point2D newPt = this.sceneToLocal(new Point2D(clamp(0, pt.getX(), getScene().getWidth()), clamp(0, pt.getY(), getScene().getWidth())));
            aircraftOutIcon.setTranslateX(newPt.getX());
            aircraftOutIcon.setTranslateY(newPt.getY());

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
