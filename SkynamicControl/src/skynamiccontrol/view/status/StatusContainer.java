package skynamiccontrol.view.status;/**
 * Created by Elodie on 15/02/2017.
 */

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import skynamiccontrol.core.StatusStateMachine;
import skynamiccontrol.model.Aircraft;


import java.util.Observable;
import java.util.Observer;

public class StatusContainer extends Parent implements Observer {

    private final static int SIZE = 250;

    private final static int TRANSLATE_X = 50;

    private final static int TRANSLATE_Y = 18;

    private final static double SPACING_ALTITUDE = 10.0;

    private final static double TRANSLATE_BATTERY = 10.0;

    private final static double SPACING_SPEED = 18.0;

    private final static double SPACING_STATUS = 20.0;

    private final static double SPACING = 20.0;

    private final static double NB_BITMAPS_BATTERIES = 5.0;


    private BorderPane global_pane;

    private VBox info_box;

    private Text aircraft_name;

    private Text altitude;

    private Text speed;

    private Text status;

    private javafx.scene.image.ImageView batterie_image;

    private Aircraft aircraft;
    private StatusStateMachine statusStateMachine;

    public StatusContainer(Aircraft air) {
        System.out.println("construct status container");
        statusStateMachine = new StatusStateMachine(air.getColor());
        Font.loadFont(getClass().getResourceAsStream("resources/font/OpenSans-Regular.ttf"), StatusListContainer.FONT_SIZE_BODY);
        this.setStyle("-fx-font-family: OpenSans-Regular;");
        global_pane = new BorderPane();
        global_pane.setPrefSize(SIZE,SIZE);
        global_pane.setStyle("-fx-border-radius: 10 10 10 10; -fx-background-radius: 10 10 10 10;");
        info_box = new VBox();

        Pane title_pane = new Pane();
        title_pane.setStyle("-fx-background-color: rgb("+air.getColor().getRed()+","+air.getColor().getGreen()+","+air.getColor().getBlue()+");");
        aircraft_name = new Text(air.getName());
        aircraft_name.setTextAlignment(TextAlignment.CENTER);
        aircraft_name.setTranslateX(TRANSLATE_X);
        aircraft_name.setTranslateY(TRANSLATE_Y);
        aircraft_name.setStyle("-fx-font-size: 18;");

        this.batterie_image = new ImageView();
        aircraft = air;
        altitude = new Text(String.valueOf(air.getAltitude()));
        speed = new Text(String.valueOf(air.getSpeed()));


        status = new Text(String.valueOf(air.getCurrent_status()));
        double battery_level = (aircraft.getBatteryLevel());

        if (battery_level < Aircraft.MAX_BATTERY_VOLTAGE / NB_BITMAPS_BATTERIES) {
            batterie_image.setImage(new Image(getClass().getClassLoader().getResource("resources/bitmaps/bat5.png").toExternalForm()));
        } else if (battery_level <= Aircraft.MAX_BATTERY_VOLTAGE * (2.0 / NB_BITMAPS_BATTERIES)) {
            batterie_image.setImage(new Image(getClass().getClassLoader().getResource("resources/bitmaps/bat4.png").toExternalForm()));
        } else if (battery_level <= Aircraft.MAX_BATTERY_VOLTAGE * (3.0 / NB_BITMAPS_BATTERIES)) {
            batterie_image.setImage(new Image(getClass().getClassLoader().getResource("resources/bitmaps/bat3.png").toExternalForm()));
        } else if (battery_level <= Aircraft.MAX_BATTERY_VOLTAGE * (4.0 / NB_BITMAPS_BATTERIES)) {
            batterie_image.setImage(new Image(getClass().getClassLoader().getResource("resources/bitmaps/bat2.png").toExternalForm()));
        } else {
            batterie_image.setImage(new Image(getClass().getClassLoader().getResource("resources/bitmaps/bat1.png").toExternalForm()));
        }
        batterie_image.setSmooth(true);
        batterie_image.setTranslateY(TRANSLATE_BATTERY);
        title_pane.getChildren().add(aircraft_name);
        global_pane.setTop(title_pane);
        global_pane.setLeft(batterie_image);

        HBox alt_box = new HBox();
        alt_box.getChildren().addAll(new Text("Altitude: "),altitude);
        alt_box.setSpacing(SPACING_ALTITUDE);
        HBox speed_box = new HBox();

        speed_box.getChildren().addAll(new Text("Speed: "),speed);
        speed_box.setSpacing(SPACING_SPEED);
        HBox status_box = new HBox();
        status_box.getChildren().addAll(new Text("Status: "),status);
        status_box.setSpacing(SPACING_STATUS);

        info_box.setSpacing(SPACING);
        info_box.setPadding(new Insets(SPACING,StatusListContainer.PADDING,StatusListContainer.PADDING,StatusListContainer.PADDING));
        info_box.getChildren().addAll(alt_box,speed_box,status_box);
        global_pane.setCenter(info_box);
        aircraft.addPrivateObserver(this);
        this.getChildren().add(global_pane);

        this.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                statusStateMachine.onMouseClick(global_pane);
            }
        });
    }

    @Override
    public void update(Observable o, Object arg) {
        this.altitude.setText(String.valueOf(aircraft.getAltitude()));
        this.speed.setText(String.valueOf(aircraft.getSpeed()));
        this.status.setText(String.valueOf(aircraft.getCurrent_status()));
        System.out.println("update");
        System.out.println(aircraft.getCurrent_status());
        double battery_level = (aircraft.getBatteryLevel());

        if (battery_level < Aircraft.MAX_BATTERY_VOLTAGE / NB_BITMAPS_BATTERIES) {
            batterie_image.setImage(new Image(getClass().getClassLoader().getResource("resources/bitmaps/bat5.png").toExternalForm()));
        } else if (battery_level <= Aircraft.MAX_BATTERY_VOLTAGE * (2.0 / NB_BITMAPS_BATTERIES)) {
            batterie_image.setImage(new Image(getClass().getClassLoader().getResource("resources/bitmaps/bat4.png").toExternalForm()));
        } else if (battery_level <= Aircraft.MAX_BATTERY_VOLTAGE * (3.0 / NB_BITMAPS_BATTERIES)) {
            batterie_image.setImage(new Image(getClass().getClassLoader().getResource("resources/bitmaps/bat3.png").toExternalForm()));
        } else if (battery_level <= Aircraft.MAX_BATTERY_VOLTAGE * (4.0 / NB_BITMAPS_BATTERIES)) {
            batterie_image.setImage(new Image(getClass().getClassLoader().getResource("resources/bitmaps/bat2.png").toExternalForm()));
        } else {
            batterie_image.setImage(new Image(getClass().getClassLoader().getResource("resources/bitmaps/bat1.png").toExternalForm()));
        }
    }
}
