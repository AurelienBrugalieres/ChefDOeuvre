package skynamiccontrol.model.mission;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.awt.event.MouseEvent;

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

    }

    public Color myColor(double red, double green , double blue , double opacity){
        return new Color(red/255.0,green/255.0,blue/255.0,opacity);
    }

    public Rectangle getRect(){return rect;}

    public void onMousePressed(MouseEvent mouseEvent){
        
    }
}
