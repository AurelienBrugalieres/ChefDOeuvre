package skynamiccontrol.timeline;

import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import skynamiccontrol.model.Aircraft;
import skynamiccontrol.model.mission.*;
import skynamiccontrol.view.forms.AbstractForm;
import skynamiccontrol.view.forms.FormCircleController;
import skynamiccontrol.view.forms.FormGoToController;
import skynamiccontrol.view.forms.FormPathController;

import java.io.IOException;

/**
 * Created by Lioz-MBPR on 27/02/2017.
 */
public class StackPaneInstruction extends StackPane {

    private int width = 120;
    private int height = 20;

    private Rectangle rectangle;
    private Text text;
    private Instruction instruction;

    public StackPaneInstruction(Instruction instruction, Aircraft aircraft) {
        this.instruction = instruction;
        this.text = new Text(instruction.getName());
        this.text.setStroke(Color.WHITE);
        rectangle = new Rectangle(width, height);
        Color color = null;
        switch (instruction.getState()) {
            case NOT_SENT:
                color = Color.web(aircraft.getColor(), 1).deriveColor(0, 0.6, 1.2, 1);
                rectangle.setStroke(Color.BLACK);
                rectangle.getStrokeDashArray().addAll(2d);
                break;
            case SENT:
                color = Color.web(aircraft.getColor(), 1).deriveColor(0, 0.8, 1, 1);
                break;
            case ACKNOWLEDGED:
                color = Color.web(aircraft.getColor(), 1).deriveColor(0, 1, 0.8, 1);
                break;
            case CANCELED:
                color = myColor(191, 191, 191,1);
                break;
            case RUNNING:
                color = Color.web(aircraft.getColor(), 1).deriveColor(0, 1, 0.5, 1);
                break;
            case ABORTED:
                color = myColor(223, 0, 11,1);
                break;
            case DONE:
                color = myColor(191, 191, 191,1);
                break;
        }
        rectangle.setFill(color);

        this.getChildren().add(rectangle);
        this.getChildren().add(text);
        text.setTranslateX(5);


        this.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            try {
                Popup popup = new Popup();
                AbstractForm formController = null;
                //todo : bind to the right form.
                if(instruction instanceof Circle) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/skynamiccontrol/form_circle.fxml"));
                    popup.getContent().add(loader.load());
                    formController = loader.getController();
                    ((FormCircleController)formController).setCircle((Circle)instruction);
                } else if(instruction instanceof Path) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/skynamiccontrol/form_path.fxml"));
                    popup.getContent().add(loader.load());
                    formController = loader.getController();
                    ((FormPathController)formController).setPath((Path)instruction);
                } else if(instruction instanceof GoToWP) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/skynamiccontrol/form_goto.fxml"));
                    popup.getContent().add(loader.load());
                    formController = loader.getController();
                    ((FormGoToController)formController).setGoTo((GoToWP)instruction);
                } else if(instruction instanceof Survey) {

                }

                formController.setPopup(popup);
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

}
