package skynamiccontrol.view.status;/**
 * Created by Elodie on 15/02/2017.
 */

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import skynamiccontrol.FxUtils;
import skynamiccontrol.model.Aircraft;


import java.util.Observable;
import java.util.Observer;

/**
 * View of strip module.
 */
public class StatusContainer extends Parent implements Observer {

    /**
     * Constants for location of elements.
     */
    private final static int SIZE = 150;

    private final static int BAT_HEIGHT = 70;

    private final static int BAT_WIDTH = 30;

    private final static int TRANSLATE_X = 50;

    private final static int TRANSLATE_Y = 18;

    private final static double SPACING_ALTITUDE = 10.0;

    private final static double TRANSLATE_BATTERY = 10.0;

    private final static double SPACING_SPEED = 18.0;

    private final static double SPACING_STATUS = 20.0;

    private final static double SPACING = 10.0;

    private final static double NB_BITMAPS_BATTERIES = 5.0;

    //global pane
    private BorderPane global_pane;

    // pane with drone's informations
    private VBox info_box;

    // aircraft's name
    private Text aircraft_name;

    //aircraft's altitude
    private Text altitude;

    //aircraft's speed
    private Text speed;

    //aircraft's status
    private Text status;

    //aircraft's GPS mode
    private Text mode_gps;

    //image of the batterie
    private javafx.scene.image.ImageView batterie_image;

    //aircraft concerned
    private Aircraft aircraft;

    //if the strip is selected or not
    public boolean isSelected = false;

    public StatusContainer(Aircraft air) {
        //Get and set Font Open Sans
        Font.loadFont(getClass().getResourceAsStream("resources/font/OpenSans-Regular.ttf"), StatusListContainer.FONT_SIZE_BODY);
        this.setStyle("-fx-font-family: OpenSans-Regular;");

        //Create global pane
        global_pane = new BorderPane();
        global_pane.setPrefSize(SIZE,SIZE);
        global_pane.setStyle("-fx-border-radius: 10 10 10 10; -fx-background-radius: 10 10 10 10;");
        info_box = new VBox();

        //Create the title of the strip with the aircraft's name
        Pane title_pane = new Pane();
        String st = FxUtils.getCssColor(Color.web(air.getColor()));
        title_pane.setStyle(st);
        aircraft_name = new Text(air.getName());
        aircraft_name.setTextAlignment(TextAlignment.CENTER);
        aircraft_name.setTranslateX(TRANSLATE_X);
        aircraft_name.setTranslateY(TRANSLATE_Y);
        aircraft_name.setStyle("-fx-font-size: 18;");

        //Create views of aircraft's parameters
        this.batterie_image = new ImageView();
        aircraft = air;
        altitude = new Text(String.valueOf(air.getAltitude()));
        speed = new Text(String.valueOf(air.getSpeed()));
        mode_gps = new Text(String.valueOf(air.getGpsMode()));


        status = new Text(air.getStatus());

        //Create view of the batterie
        setBatteryImage();
        batterie_image.setSmooth(true);
        batterie_image.setTranslateY(TRANSLATE_BATTERY);
        batterie_image.setFitWidth(BAT_WIDTH);
        batterie_image.setFitHeight(BAT_HEIGHT);
        title_pane.getChildren().add(aircraft_name);
        global_pane.setTop(title_pane);
        global_pane.setLeft(batterie_image);

        //Set all the elements in panes
        HBox alt_box = new HBox();
        alt_box.getChildren().addAll(new Text("Altitude: "),altitude);
        alt_box.setSpacing(SPACING_ALTITUDE);
        HBox speed_box = new HBox();

        speed_box.getChildren().addAll(new Text("Speed: "),speed);
        speed_box.setSpacing(SPACING_SPEED);
        HBox status_box = new HBox();
        status_box.getChildren().addAll(new Text("Status: "),status);
        status_box.setSpacing(SPACING_STATUS);

        HBox gps_mode = new HBox();
        gps_mode.getChildren().addAll(new Text("GPS Mode: "),mode_gps);
        gps_mode.setSpacing(SPACING_STATUS);

        info_box.setSpacing(SPACING);
        info_box.setPadding(new Insets(SPACING,StatusListContainer.PADDING,StatusListContainer.PADDING,StatusListContainer.PADDING));
        info_box.getChildren().addAll(alt_box,speed_box,status_box,gps_mode);
        global_pane.setCenter(info_box);
        //Observe the aircraft
        aircraft.addPrivateObserver(this);
        this.getChildren().add(global_pane);


    }

    /**
     * Set the good image for the batterie (depending on the current level on drone's batterie).
     */
    public void setBatteryImage() {
        double batteryPercentage = aircraft.getBatteryPercentage();
        if (batteryPercentage < 1.0/NB_BITMAPS_BATTERIES) {
            batterie_image.setImage(new Image(getClass().getClassLoader().getResource("resources/bitmaps/bat5.png").toExternalForm()));
        } else if (batteryPercentage <  (2.0 / NB_BITMAPS_BATTERIES)) {
            batterie_image.setImage(new Image(getClass().getClassLoader().getResource("resources/bitmaps/bat4.png").toExternalForm()));
        } else if (batteryPercentage <  (3.0 / NB_BITMAPS_BATTERIES)) {
            batterie_image.setImage(new Image(getClass().getClassLoader().getResource("resources/bitmaps/bat3.png").toExternalForm()));
        } else if (batteryPercentage < (4.0 / NB_BITMAPS_BATTERIES)) {
            batterie_image.setImage(new Image(getClass().getClassLoader().getResource("resources/bitmaps/bat2.png").toExternalForm()));
        } else {
            batterie_image.setImage(new Image(getClass().getClassLoader().getResource("resources/bitmaps/bat1.png").toExternalForm()));
        }
    }

    /**
     * Deselect the strip.
     */
    public void setDeselected() {
        global_pane.setStyle("-fx-background-color: null");
    }

    /**
     * Select the strip.
     */
    public void setSelected() {
        Color color = Color.web(aircraft.getColor(), 0.7);
        global_pane.setStyle(FxUtils.getCssColor(color));
    }

    @Override
    public void update(Observable o, Object arg) {
        this.altitude.setText(String.valueOf(Math.round(aircraft.getAltitude())));
        this.speed.setText(String.valueOf(aircraft.getSpeed()));
        this.status.setText(String.valueOf(aircraft.getStatus()));
        this.mode_gps.setText(aircraft.getGpsMode());
        setBatteryImage();
    }

    public void handle(java.util.List<StatusContainer> list) {
       // statusStateMachine.onStatusSelected(this);
    }
    public BorderPane getGlobal_pane() {
        return global_pane;
    }

    public Aircraft getAircraft() {
        return aircraft;
    }
}
