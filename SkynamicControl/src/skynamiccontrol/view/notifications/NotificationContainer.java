package skynamiccontrol.view.notifications;

import javafx.collections.FXCollections;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import skynamiccontrol.model.Aircraft;

import java.awt.*;
import java.util.ArrayList;
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

    public NotificationContainer() {
        this.aircrafts = new ArrayList<>();
        this.tab_pane = new HashMap<>();
        this.tabPane = new TabPane();
        this.tabPane.setStyle("-fx-background-color: rgba(0,0,0,0)");
        this.getChildren().add(this.tabPane);
    }

    public void AddTab(Aircraft air) {
        String backgroundColor = "("+air.getColor().getRed()+","+
                air.getColor().getGreen()+","+
                air.getColor().getBlue()+",0.7)";
        String styleTab = "-fx-background-color: rgba"+backgroundColor;

        Tab tab = new Tab();
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        VBox vbox = new VBox();
        tab.setText(air.getName());
        tab.setStyle(styleTab);

        scrollPane.setContent(vbox);

        BorderPane borderPane = new BorderPane();
        HBox hBox = new HBox();

        javafx.scene.control.TextField searchField= new TextField();
        searchField.setStyle(styleTab);
        HBox.setHgrow(searchField,Priority.ALWAYS);
        hBox.getChildren().add(searchField);

        ChoiceBox choiceBox = new ChoiceBox(FXCollections.observableArrayList(
                "All", "Info", "Warning", "Error")
        );
        choiceBox.getSelectionModel().selectFirst();
        choiceBox.setStyle(styleTab);
        HBox.setHgrow(choiceBox,Priority.ALWAYS);
        hBox.getChildren().add(choiceBox);

        borderPane.setTop(hBox);
        borderPane.setCenter(scrollPane);
        tab.setContent(borderPane);

        this.tabPane.getTabs().add(tab);
        ((BorderPane)tab.getContent()).setStyle(styleTab);
        ((ScrollPane)((BorderPane)tab.getContent()).getCenter()).getContent().setStyle(styleTab);
        this.tab_pane.put(air,scrollPane);
    }

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
        VBox.setVgrow(notif, Priority.ALWAYS);
        ((VBox)tab_pane.get(aircraft).getContent()).getChildren().add(notif);
        tab_pane.get(aircraft).setVvalue(1.0);
    }

    public void addWarning(Aircraft aircraft, String text) {
        Text notif = new Text(text);
        VBox.setVgrow(notif, Priority.ALWAYS);
        Color color = Color.web("#FFB12D");
        javafx.scene.paint.Paint paint = new javafx.scene.paint.Color(color.getRed(),color.getGreen(),color.getBlue(),0.7);
        notif.setFill(paint);
        ((VBox)tab_pane.get(aircraft).getContent()).getChildren().add(notif);
        tab_pane.get(aircraft).setVvalue(1.0);
    }

    public void addError(Aircraft aircraft, String text) {
        Text notif = new Text(text);
        VBox.setVgrow(notif, Priority.ALWAYS);
        Color color = Color.web("#FC2C36");
        javafx.scene.paint.Paint paint = new javafx.scene.paint.Color(color.getRed(),color.getGreen(),color.getBlue(),0.7);
        notif.setFill(paint);
        ((VBox)tab_pane.get(aircraft).getContent()).getChildren().add(notif);
        tab_pane.get(aircraft).setVvalue(1.0);
    }

    public void addSuccess(Aircraft aircraft, String text) {
        Text notif = new Text(text);
        VBox.setVgrow(notif, Priority.ALWAYS);
        Color color = Color.web("#3AE428");
        javafx.scene.paint.Paint paint = new javafx.scene.paint.Color(color.getRed(),color.getGreen(),color.getBlue(),0.7);
        notif.setFill(paint);
        ((VBox)tab_pane.get(aircraft).getContent()).getChildren().add(notif);
        tab_pane.get(aircraft).setVvalue(1.0);
    }

    public double getWidth() {
        return tabPane.getWidth();
    }
}
