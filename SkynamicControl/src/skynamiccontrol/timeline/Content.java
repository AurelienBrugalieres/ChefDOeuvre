package skynamiccontrol.timeline;

import javafx.application.Platform;
import javafx.scene.layout.Pane;
import skynamiccontrol.model.Aircraft;
import skynamiccontrol.model.mission.Instruction;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by fabien on 02/03/17.
 */
public class Content extends Pane implements Observer {
    private static int DEFAULT_HEIGHT = 150;
    private static int ALTITUDE_ROUND = 50;
    private static int ALTITUDE_MARGIN = 30;
    Aircraft aircraft;
    TabContent tabContent;
    double totalHeight;

    public Content(Aircraft aircraft, TabContent tabContent, double height) {
        this.aircraft = aircraft;
        this.tabContent = tabContent;
        totalHeight = height;
        aircraft.getMissionManager().addObserver(this);
    }

    public void updateAircraftMissionBlock() {
        double x = 0;
        this.getChildren().clear();

        if (aircraft.getMissionManager().getFutureInstructions().isEmpty()) {
            return;
        }

        double altitudeMax = DEFAULT_HEIGHT;
        for (Instruction instruction : aircraft.getMissionManager().getFutureInstructions()) {
            altitudeMax = Math.max(altitudeMax, instruction.getAltitude() + ALTITUDE_MARGIN);
        }
        altitudeMax = altitudeMax + ALTITUDE_ROUND - (altitudeMax % ALTITUDE_ROUND);
        tabContent.setMaxAltitude((int) altitudeMax);


        for (Instruction instruction : aircraft.getMissionManager().getFutureInstructions()) {
            double y = totalHeight - totalHeight*(instruction.getAltitude()/altitudeMax);
            StackPaneInstruction sp = new StackPaneInstruction(instruction, aircraft);
            sp.setTranslateX(x);
            sp.setTranslateY(y);
            x += StackPaneInstruction.BLOC_WIDTH;
            this.getChildren().add(sp);
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        Platform.runLater(this::updateAircraftMissionBlock);
    }
}
