package skynamiccontrol.timeline;

import javafx.scene.layout.Pane;
import skynamiccontrol.model.Aircraft;
import skynamiccontrol.model.mission.Instruction;

/**
 * Created by fabien on 02/03/17.
 */
public class Content extends Pane {
    Aircraft aircraft;

    public Content(Aircraft aircraft) {
        this.aircraft = aircraft;
    }

    public void updateAircraftMissionBlock() {
        double x = 0;
        double totalHeigth = this.getPrefHeight();
        this.getChildren().clear();

        if (aircraft.getMissionManager().getFutureInstructions().isEmpty()) {
            return;
        }

        double altitudeMax = 150;
        for (Instruction instruction : aircraft.getMissionManager().getFutureInstructions()) {
            altitudeMax = Math.max(altitudeMax, instruction.getAltitude()+30);
        }

        for (Instruction instruction : aircraft.getMissionManager().getFutureInstructions()) {
            double yi = totalHeigth - totalHeigth*(instruction.getAltitude()/altitudeMax);
            //double yi = totalHeigth - (aircraft.getAltitude()*0.244);
            StackPaneInstruction sp = new StackPaneInstruction(instruction, aircraft);
            sp.setTranslateX(x);
            sp.setTranslateY(yi);
            x += 120;
            this.getChildren().add(sp);
        }
    }
}
