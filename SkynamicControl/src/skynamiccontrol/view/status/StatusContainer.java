package skynamiccontrol.view.status;/**
 * Created by Elodie on 15/02/2017.
 */

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import skynamiccontrol.model.Aircraft;

import java.util.Observable;
import java.util.Observer;

public class StatusContainer extends Parent implements Observer {

    private BorderPane global_pane;

    private VBox info_box;

    private Text aircraft_name;

    private Text altitude;

    private Text speed;

    private Text status;

    private javafx.scene.image.ImageView batterie_image;

    private Aircraft aircraft;


    public StatusContainer(Aircraft air) {
        System.out.println("construct status container");
        global_pane = new BorderPane();
        global_pane.setPrefSize(380,270);
        info_box = new VBox();

        aircraft_name = new Text(air.getName());

        this.batterie_image = new ImageView();
        aircraft = air;
        altitude = new Text(String.valueOf(air.getAltitude()));
        speed = new Text(String.valueOf(air.getSpeed()));
        status = new Text(String.valueOf(air.getCurrent_status()));
        double battery_level = (aircraft.getBatteryLevel());

        if (battery_level < Aircraft.MAX_BATTERY_VOLTAGE / 5.0) {
            batterie_image.setImage(new Image(getClass().getClassLoader().getResource("resources/bitmaps/bat5.png").toExternalForm()));
        } else if (battery_level <= Aircraft.MAX_BATTERY_VOLTAGE * (2.0 / 5.0)) {
            batterie_image.setImage(new Image(getClass().getClassLoader().getResource("resources/bitmaps/bat4.png").toExternalForm()));
        } else if (battery_level <= Aircraft.MAX_BATTERY_VOLTAGE * (3.0 / 5.0)) {
            batterie_image.setImage(new Image(getClass().getClassLoader().getResource("resources/bitmaps/bat3.png").toExternalForm()));
        } else if (battery_level <= Aircraft.MAX_BATTERY_VOLTAGE * (4.0 / 5.0)) {
            batterie_image.setImage(new Image(getClass().getClassLoader().getResource("resources/bitmaps/bat2.png").toExternalForm()));
        } else {
            batterie_image.setImage(new Image(getClass().getClassLoader().getResource("resources/bitmaps/bat1.png").toExternalForm()));
        }
        global_pane.setTop(aircraft_name);
        global_pane.setLeft(batterie_image);
        info_box.getChildren().addAll(altitude,speed,status);
        global_pane.setCenter(info_box);
        aircraft.addPrivateObserver(this);
        this.getChildren().add(global_pane);
    }

    @Override
    public void update(Observable o, Object arg) {
        this.altitude.setText(String.valueOf(aircraft.getAltitude()));
        this.speed.setText(String.valueOf(aircraft.getSpeed()));
        this.status.setText(String.valueOf(aircraft.getCurrent_status()));
        System.out.println("update");
        System.out.println(aircraft.getCurrent_status());
        double battery_level = (aircraft.getBatteryLevel());

        if (battery_level < Aircraft.MAX_BATTERY_VOLTAGE / 5) {
            this.batterie_image.setImage(new Image("src\\resources\\bitmaps\\bat5.png"));
        } else if (battery_level <= Aircraft.MAX_BATTERY_VOLTAGE * (2 / 5)) {
            this.batterie_image.setImage(new Image("src\\resources\\bitmaps\\bat4.png"));
        } else if (battery_level <= Aircraft.MAX_BATTERY_VOLTAGE * (3 / 5)) {
            this.batterie_image.setImage(new Image("src\\resources\\bitmaps\\bat3.png"));
        }else if (battery_level <= Aircraft.MAX_BATTERY_VOLTAGE * (4 / 5)) {
            this.batterie_image.setImage(new Image("src\\resources\\bitmaps\\bat2.png"));
        } else {
            this.batterie_image.setImage(new Image("src\\resources\\bitmaps\\bat1.png"));
        }
    }
}
