package skynamiccontrol.view.notifications;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import skynamiccontrol.model.Aircraft;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by Elodie on 20/02/2017.
 */
public class NotificationContainer extends Parent implements Observer{

    private List<Aircraft> aircrafts;

    private TabPane tabPane;
    private Map<Aircraft, Tab> tab_pane;
    public interface ChangeTabListener {
        void onChangeTab(Tab tab, Aircraft aircraft);
    }
    private ChangeTabListener listener = null;

    public NotificationContainer() {
        this.aircrafts = new ArrayList<>();
        this.tab_pane = new HashMap<>();
        this.tabPane = new TabPane();
        this.tabPane.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
                if (listener != null) {
                    Tab newTab = tabPane.getTabs().get(newValue.intValue());
                    Aircraft aircraft = null;
                    for (Map.Entry<Aircraft, Tab> entry : tab_pane.entrySet()) {
                        if (entry.getValue().equals(newTab)) {
                            aircraft = entry.getKey();
                        }
                    }
                    listener.onChangeTab(newTab, aircraft);

                }
            }
        });
        this.tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        this.tabPane.getStylesheets().add("skynamiccontrol/view/notifications/pane.css");
        final Point dragDelta = new Point();
        this.tabPane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // record a delta distance for the drag and drop operation.
                dragDelta.x = (int)( tabPane.getLayoutX() - mouseEvent.getSceneX());
                dragDelta.y = (int) (tabPane.getLayoutY() - mouseEvent.getSceneY());
                tabPane.setCursor(Cursor.MOVE);
            }
        });
        this.tabPane.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                tabPane.setCursor(Cursor.HAND);
            }
        });
        this.tabPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                tabPane.setLayoutX(mouseEvent.getSceneX() + dragDelta.x);
                tabPane.setLayoutY(mouseEvent.getSceneY() + dragDelta.y);
            }
        });
        this.tabPane.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                tabPane.setCursor(Cursor.HAND);
            }
        });

        this.setStyle("-fx-border-radius: 10 10 10 10; -fx-background-radius: 10 10 10 10;");
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
        borderPane.setStyle("-fx-border-radius: 10 10 10 10; -fx-background-radius: 10 10 10 10; -fx-padding: 30;");
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
        air.addPrivateObserver(this);
        this.tab_pane.put(air,tab);
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
            this.tab_pane.put(air,tab);
        }

        this.getChildren().add(this.tabPane);

    }

    public void addInfo(Aircraft aircraft, String text) {
        Text notif = new Text(text);
        VBox.setVgrow(notif, Priority.ALWAYS);
        ((VBox)tab_pane.get(aircraft).getContent()).getChildren().add(notif);
        ((ScrollPane)((BorderPane)tab_pane.get(aircraft).getContent()).getCenter()).setVvalue(1.0);
    }

    public void addWarning(Aircraft aircraft, String text) {
        Text notif = new Text(text);
        VBox.setVgrow(notif, Priority.ALWAYS);
        Color color = Color.web("#FFB12D");
        javafx.scene.paint.Paint paint = new javafx.scene.paint.Color(color.getRed(),color.getGreen(),color.getBlue(),0.7);
        notif.setFill(paint);
        ((VBox)tab_pane.get(aircraft).getContent()).getChildren().add(notif);
        ((ScrollPane)((BorderPane)tab_pane.get(aircraft).getContent()).getCenter()).setVvalue(1.0);
    }

    public void addError(Aircraft aircraft, String text) {
        Text notif = new Text(text);
        VBox.setVgrow(notif, Priority.ALWAYS);
        Color color = Color.web("#FC2C36");
        javafx.scene.paint.Paint paint = new javafx.scene.paint.Color(color.getRed(),color.getGreen(),color.getBlue(),0.7);
        notif.setFill(paint);
        ((VBox)tab_pane.get(aircraft).getContent()).getChildren().add(notif);
        ((ScrollPane)((BorderPane)tab_pane.get(aircraft).getContent()).getCenter()).setVvalue(1.0);
    }

    public void addSuccess(Aircraft aircraft, String text) {
        Text notif = new Text(text);
        VBox.setVgrow(notif, Priority.ALWAYS);
        Color color = Color.web("#3AE428");
        javafx.scene.paint.Paint paint = new javafx.scene.paint.Color(color.getRed(),color.getGreen(),color.getBlue(),0.7);
        notif.setFill(paint);
        ((VBox)tab_pane.get(aircraft).getContent()).getChildren().add(notif);
        ((ScrollPane)((BorderPane)tab_pane.get(aircraft).getContent()).getCenter()).setVvalue(1.0);
    }

    public double getWidth() {
        return tabPane.getWidth();
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    public void selectAircraft(Aircraft aircraft) {
        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
        selectionModel.select(tab_pane.get(aircraft));
    }

    public void setOnChangeTabListener(ChangeTabListener changeTabListener) {
        this.listener = changeTabListener;
    }
}
