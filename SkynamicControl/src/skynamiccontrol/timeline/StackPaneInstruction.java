package skynamiccontrol.timeline;

import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import skynamiccontrol.view.forms.FormCircleController;

import java.io.IOException;

/**
 * Created by Lioz-MBPR on 27/02/2017.
 */
public class StackPaneInstruction extends StackPane {

    Rectangle rect;

    public StackPaneInstruction(double x , double y ,double width,double height, double red , double green , double blue,double opacity, Text instructionInformation){
        this.setLayoutY(y);
        this.setLayoutX(x);
        rect = new Rectangle(width,height,myColor(red,green,blue,opacity));
        this.getChildren().addAll(rect,instructionInformation);
        this.getChildren().get(1).setTranslateX(5);

        this.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            try {
                Popup popup = new Popup();
                //todo : bind to the right form.
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/skynamiccontrol/form_circle.fxml"));
                popup.getContent().add(loader.load());
                FormCircleController controller = loader.getController();
                controller.setPopup(popup);
                //TODO : set better position.
                popup.show(this, e.getScreenX() - popup.getWidth() / 2, e.getScreenY() - popup.getHeight() - 200);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

    }

    public Color myColor(double red, double green , double blue , double opacity){
        return new Color(red/255.0,green/255.0,blue/255.0,opacity);
    }

    public Rectangle getRect(){return rect;}

}
