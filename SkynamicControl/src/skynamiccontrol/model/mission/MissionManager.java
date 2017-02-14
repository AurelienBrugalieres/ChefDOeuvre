package skynamiccontrol.model.mission;

import skynamiccontrol.communication.IvyManager;
import skynamiccontrol.model.Waypoint;

import java.util.ArrayList;

/**
 * Created by fabien on 13/02/17.
 */
public class MissionManager {
    private static String NAME_ON_IVY = "gcs";
    private static String MISSION_CIRCLE = "MISSION_CIRCLE";
    private static String MISSION_CIRCLE_LLA = "MISSION_CIRCLE_LLA";
    private static int INDEX_BIT_LENGTH = 8;
    private int aircraftId;
    private ArrayList<Instruction> instructions;
    private int currentInstructionId;
    private int nextIndex;

    public MissionManager(int aircraftId) {
        this.aircraftId = aircraftId;
        currentInstructionId = 0;
        nextIndex = 0;
    }

    public void insertInstruction(Instruction instruction, InsertMode insertMode) {
        String msg;
        if(instruction instanceof Circle) {
            msg = forgeCircleMessage((Circle) instruction, insertMode);
        } else if(instruction instanceof GoToWP) {
            msg = forgeGoToWPMessage((GoToWP) instruction, insertMode);
        } else if(instruction instanceof Path) {
            msg = forgePathMessage((Path) instruction, insertMode);
        } else if(instruction instanceof Segment) {
            msg = forgeSegmentMessage((Segment) instruction, insertMode);
        } else if(instruction instanceof Survey) {
            msg = forgeSurveyMessage((Survey) instruction, insertMode);
        } else {
            System.out.println("Type not known.");
            return;
        }

        if(IvyManager.getInstance().sendMessage(msg)) {     //if message sent
            switch(insertMode) {
                case APPEND:
                    appendInstruction(instruction);
                    break;
                case PREPEND:
                    prependInstruction(instruction);
                    break;
                case REPLACE_CURRENT:
                    replaceCurrentInstruction(instruction);
                    break;
                case REPLACE_ALL:
                    replaceAllInstructions(instruction);
                    break;
            }
        }
    }

    public ArrayList<Instruction> getInstructions() {
        return instructions;
    }

    public int getCurrentInstructionId() {
        return currentInstructionId;
    }

    private void appendInstruction(Instruction instruction) {
        instructions.add(instruction);
    }

    private void replaceCurrentInstruction(Instruction instruction) {

    }

    private void replaceAllInstructions(Instruction instruction) {

    }

    private void prependInstruction(Instruction instruction) {

    }

    private String forgeCircleMessage(Circle circle, InsertMode insertMode) {
        String msg = "";
        int index = nextIndex++ % (1<<INDEX_BIT_LENGTH);
        if(circle.getCenter().getCoordinateSystem() == Waypoint.CoordinateSystem.LLA) {
            msg = NAME_ON_IVY + " " +
                    MISSION_CIRCLE_LLA + " " +
                    aircraftId + " " +
                    insertMode.getValue() + " " +
                    (int)circle.getCenter().getLatitude() * 10e7 + " " +
                    (int)circle.getCenter().getLongitude() * 10e7 + " " +
                    circle.getRadius() + " " +
                    circle.getDuration() + " " +
                    index;
        } else if(circle.getCenter().getCoordinateSystem() == Waypoint.CoordinateSystem.LOCAL) {
            msg = NAME_ON_IVY + " " +
                    MISSION_CIRCLE + " " +
                    aircraftId + " " +
                    insertMode.getValue() + " " +
                    circle.getCenter().getLatitude() + " " +
                    circle.getCenter().getLongitude() + " " +
                    circle.getRadius() + " " +
                    circle.getDuration() + " " +
                    index;
        }
        return msg;
    }

    private String forgeGoToWPMessage(GoToWP goToWP, InsertMode insertMode) {
        return "";
    }

    private String forgePathMessage(Path path, InsertMode insertMode) {
        return "";
    }

    private String forgeSegmentMessage(Segment segment, InsertMode insertMode) {
        return "";
    }

    private String forgeSurveyMessage(Survey survey, InsertMode insertMode) {
        return "";
    }

    public enum InsertMode {
        APPEND(0),
        PREPEND(1),
        REPLACE_CURRENT(2),
        REPLACE_ALL(3);

        private int value;
        InsertMode(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
