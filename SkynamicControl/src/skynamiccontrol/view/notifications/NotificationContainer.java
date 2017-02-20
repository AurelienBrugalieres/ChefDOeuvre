package skynamiccontrol.view.notifications;

import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.*;
import javafx.scene.text.Text;
import skynamiccontrol.model.Aircraft;

import java.awt.*;
import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Elodie on 20/02/2017.
 */
public class NotificationContainer extends Parent {

    private List<Aircraft> aircrafts;

    private TabPane tabPane;
    private Map<Aircraft, ScrollPane> tab_pane;

    public NotificationContainer(List<Aircraft> aircraftList) {
        this.aircrafts = aircraftList;
        this.tab_pane = new HashMap<>();
        this.tabPane = new TabPane();


        for (Aircraft air : aircrafts) {
            Tab tab = new Tab();
            ScrollPane scrollPane = new ScrollPane();
            VBox vbox = new VBox();
            tab.setText(air.getName());
            tab.setStyle("-fx-background-color: rgba(" + air.getColor().getRed() + "," + air.getColor().getGreen() + "," + air.getColor().getBlue() + ";");
            scrollPane.setContent(vbox);
            tab.setContent(scrollPane);
            this.tabPane.getTabs().add(tab);
            this.tab_pane.put(air,scrollPane);
        }

        this.getChildren().add(this.tabPane);

    }

    public void addInfo(Aircraft aircraft, String text) {
        Text notif = new Text(text);
        ((VBox)tab_pane.get(aircraft).getContent()).getChildren().add(notif);
        tab_pane.get(aircraft).setVvalue(1.0);
    }

    public void addWarning(Aircraft aircraft, String text) {
        Text notif = new Text(text);
        Color color = Color.decode("#FFB12D");
        javafx.scene.paint.Paint paint = new javafx.scene.paint.Color(color.getRed(),color.getGreen(),color.getBlue(),0.7);
        notif.setFill(paint);
        ((VBox)tab_pane.get(aircraft).getContent()).getChildren().add(notif);
        tab_pane.get(aircraft).setVvalue(1.0);
    }

    public void addError(Aircraft aircraft, String text) {
        Text notif = new Text(text);
        Color color = Color.decode("#FC2C36");
        javafx.scene.paint.Paint paint = new javafx.scene.paint.Color(color.getRed(),color.getGreen(),color.getBlue(),0.7);
        notif.setFill(paint);
        ((VBox)tab_pane.get(aircraft).getContent()).getChildren().add(notif);
        tab_pane.get(aircraft).setVvalue(1.0);
    }

    public void addSuccess(Aircraft aircraft, String text) {
        Text notif = new Text(text);
        Color color = Color.decode("#3AE428");
        javafx.scene.paint.Paint paint = new javafx.scene.paint.Color(color.getRed(),color.getGreen(),color.getBlue(),0.7);
        notif.setFill(paint);
        ((VBox)tab_pane.get(aircraft).getContent()).getChildren().add(notif);
        tab_pane.get(aircraft).setVvalue(1.0);
    }
}
