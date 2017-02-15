package skynamiccontrol.Timeline;

/**
 *
 * Created by Lioz-MBPR on 14/02/2017.
 *
 **/

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import skynamiccontrol.model.Aircraft;

import java.util.ArrayList;

public class Timeline extends Application{
    MissionBlock mb;
    ArrayList<String> listAircraft;
    @Override
    public void start(Stage primaryStage) throws Exception {
        listAircraft.add("MicroJet");
        listAircraft.add("TangoCharlie");
        Parent root = FXMLLoader.load(getClass().getResource("TimelineUI.fxml"));
        primaryStage.setTitle("TimelineUI Control");
        Scene scene = new Scene(root, 1080, 225, Color.WHITE);

        TabPane tabPane = new TabPane();

        BorderPane borderPane = new BorderPane();
        for (int i = 0; i < listAircraft.size(); i++) {
            Tab tab = new Tab();
            tab.setText(listAircraft.get(i));
           // mb = new MissionBlock();
           // tab.setContent(mb);
            tabPane.getTabs().add(tab);
        }
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
