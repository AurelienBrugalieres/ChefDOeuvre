package skynamiccontrol.view.status;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import skynamiccontrol.model.Aircraft;

import javax.swing.text.html.ImageView;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

/**
 * Created by Elodie on 14/02/2017.
 * Controller of Status View
 */
public class StatusController implements Initializable{

    @FXML
    private Text altitude;

    @FXML
    private Text speed;

    @FXML
    private Text status;

    @FXML
    private javafx.scene.image.ImageView batterie_image;


    public StatusController() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
/*
    public void update() {
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
    }*/
}
