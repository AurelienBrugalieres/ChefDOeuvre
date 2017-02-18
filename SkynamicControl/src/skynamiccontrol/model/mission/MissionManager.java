package skynamiccontrol.model.mission;

import skynamiccontrol.communication.IncomeMessage;
import skynamiccontrol.communication.IvyManager;
import skynamiccontrol.model.Waypoint;

import java.util.*;

/**
 * Created by fabien on 13/02/17.
 */
public class MissionManager implements Observer{
    private static String MISSION_CIRCLE_LOCAL = "MISSION_CIRCLE";
    private static String MISSION_CIRCLE_LLA = "MISSION_CIRCLE_LLA";
    private static String MISSION_GOTOWP_LOCAL = "MISSION_GOTO_WP";
    private static String MISSION_GOTOWP_LLA = "MISSION_GOTO_WP_LLA";
    private static String MISSION_SURVEY_LOCAL = "MISSION_SURVEY";
    private static String MISSION_SURVEY_LLA = "MISSION_SURVEY_LLA";
    private static String MISSION_PATH_LOCAL = "MISSION_PATH";
    private static String MISSION_PATH_LLA = "MISSION_PATH_LLA";
    private static String MISSION_SEGMENT_LOCAL = "MISSION_SEGMENT";
    private static String MISSION_SEGMENT_LLA = "MISSION_SEGMENT_LLA";
    private static int INDEX_BIT_LENGTH = 8;
    private int missionStatusMessageId;
    private int aircraftId;
    private ArrayList<Instruction> instructions;
    private int currentInstructionAircraftIndex;
    private int nextIndex;
    private ArrayList<Instruction> pendingInstructions;

    public MissionManager(int aircraftId) {
        this.aircraftId = aircraftId;
        instructions = new ArrayList<>();
        currentInstructionAircraftIndex = 0;
        nextIndex = 1;              //start at 1 to discrimine from "no instruction" case
        IvyManager.getInstance().addObserver(this);
        missionStatusMessageId = IvyManager.getInstance().registerRegex(aircraftId + " MISSION_STATUS " + "(.*) (.*)");
    }

    public void insertInstruction(Instruction instruction, InsertMode insertMode) {
        String msg;
        try {
            if(instruction instanceof Circle) {
                msg = forgeCircleMessage((Circle) instruction, insertMode);
            } else if(instruction instanceof GoToWP) {
                msg = forgeGoToWPMessage((GoToWP) instruction, insertMode);
            } else if(instruction instanceof Path) {
                msg = forgePathMessage((Path) instruction, insertMode);
            } else if(instruction instanceof Survey) {
                msg = forgeSurveyMessage((Survey) instruction, insertMode);
            } else {
                System.out.println("Type not known.");
                return;
            }
        } catch (Exception e) {
            return;
        }


        if(IvyManager.getInstance().sendMessage(msg)) {     //if message sent
            switch(insertMode) {
                case APPEND:
                    appendInstruction(instruction);
                    break;
                case PREPEND:
                    //prependInstruction(instruction);
                    break;
                case REPLACE_CURRENT:
                    replaceCurrentInstruction(instruction);
                    break;
                case REPLACE_ALL:
                    replaceAllInstructions(instruction);
                    break;
                case REPLACE_NEXTS:
                    replaceNextsInstructions(instruction);
                    break;
            }
        }
    }

    public void goToNextInstruction() {
        String msg = "NEXT_MISSION" + " " + aircraftId;
        IvyManager.getInstance().sendMessage(msg);
    }

    public ArrayList<Instruction> getInstructions() {
        return instructions;
    }

    public int getCurrentInstructionAircraftIndex() {
        return currentInstructionAircraftIndex;
    }

    private void appendInstruction(Instruction instruction) {
        instruction.setState(Instruction.State.NOT_SENT);
        pendingInstructions.add(instruction);
    }

    private void replaceCurrentInstruction(Instruction instruction) {
        instruction.setState(Instruction.State.SENT);
        pendingInstructions.add(getInsertIndex(), instruction);
    }

    private void replaceAllInstructions(Instruction instruction) {
        instruction.setState(Instruction.State.SENT);

        ArrayList<Instruction> removeList = new ArrayList<>();
        for (int i = 0; i < pendingInstructions.size(); i++) {
            Instruction ins = pendingInstructions.get(i);
            switch (ins.getState()) {
                case NOT_SENT:
                    removeList.add(ins);
                    break;
                case SENT:
                    //don't know what to do
                    break;
                case ACKNOWLEDGED:
                    ins.setState(Instruction.State.CANCELED);
                    break;
                case CANCELED:
                    //already in CANCELED state
                    break;
                case RUNNING:
                    ins.setState(Instruction.State.ABORTED);
                    break;
                case ABORTED:
                    //already in ABORTED state
                    break;
                case DONE:
                    //should not be here
                    break;
            }
        }
        pendingInstructions.removeAll(removeList);
        pendingInstructions.add(getInsertIndex(), instruction);
    }

    private void replaceNextsInstructions(Instruction instruction) {
        instruction.setState(Instruction.State.SENT);

        ArrayList<Instruction> removeList = new ArrayList<>();
        for (int i = 0; i < pendingInstructions.size(); i++) {
            Instruction ins = pendingInstructions.get(i);
            switch (ins.getState()) {
                case NOT_SENT:
                    removeList.add(ins);
                    break;
                case SENT:
                    //don't know what to do
                    break;
                case ACKNOWLEDGED:
                    ins.setState(Instruction.State.CANCELED);
                    break;
                case CANCELED:
                    //already in CANCELED state
                    break;
                case RUNNING:
                    //do nothing
                    break;
                case ABORTED:
                    //already in ABORTED state
                    break;
                case DONE:
                    //should not be here
                    break;
            }
        }
        pendingInstructions.removeAll(removeList);
        pendingInstructions.add(getInsertIndex(), instruction);
    }


    private int getInsertIndex() {
        int insertIndex = 0;
        for (int i = 0; i < pendingInstructions.size(); i++) {  //get index of first instruction which is not ABORTED or RUNNING
            if(pendingInstructions.get(i).getState() != Instruction.State.ABORTED &&
                    pendingInstructions.get(i).getState() != Instruction.State.RUNNING) {
                insertIndex = i;
                break;
            }
        }
        return insertIndex;
    }

    private String forgeCircleMessage(Circle circle, InsertMode insertMode) {
        String msg = "";
        if(circle.getCenter().getCoordinateSystem() == Waypoint.CoordinateSystem.LLA) {
            msg += MISSION_CIRCLE_LLA;
        } else {
            msg += MISSION_CIRCLE_LOCAL;
        }

        msg += " " +
                aircraftId + " " +
                insertMode.getValue() + " " +
                extractLatEast(circle.getCenter()) + " " +
                extractLonNorth(circle.getCenter()) + " " +
                circle.getAltitude().intValue() + " " +
                circle.getRadius() + " " +
                circle.getDuration() + " " +
                getNextIndex();
        return msg;
    }

    private String forgeGoToWPMessage(GoToWP goToWP, InsertMode insertMode) {
        String msg = "";
        if(goToWP.getWaypoint().getCoordinateSystem() == Waypoint.CoordinateSystem.LLA) {
            msg += MISSION_GOTOWP_LLA;
        } else {
            msg += MISSION_GOTOWP_LOCAL;
        }

        msg += " " +
                aircraftId + " " +
                insertMode.getValue() + " " +
                extractLatEast(goToWP.getWaypoint()) + " " +
                extractLonNorth(goToWP.getWaypoint()) + " " +
                goToWP.getAltitude().intValue() + " " +
                goToWP.getDuration() + " " +
                getNextIndex();
        return msg;
    }

    private String forgePathMessage(Path path, InsertMode insertMode) throws Exception {
        int nbPoints = path.getNbWaypoints();
        if(nbPoints < 2) {
            throw new Exception("Path with less than 2 points.");
        }

        String msg = "";
        if(path.getWaypoint(0).getCoordinateSystem() == Waypoint.CoordinateSystem.LLA) {
            if(nbPoints == 2) {
                msg += MISSION_SEGMENT_LLA;
            } else {
                msg += MISSION_PATH_LLA;
            }
        } else {
            if(nbPoints == 2) {
                msg += MISSION_SEGMENT_LOCAL;
            } else {
                msg += MISSION_PATH_LOCAL;
            }
        }

        msg += " " +
                aircraftId + " " +
                insertMode.getValue() + " ";
        for(int i=0; i<Path.NB_WAYPOINTS_MAX; i++) {
            if(i<nbPoints) {
                msg += extractLatEast(path.getWaypoint(i)) + " " +
                        extractLonNorth(path.getWaypoint(i)) + " ";
            } else {
                msg += "0 0 ";
            }
        }

        msg +=path.getAltitude().intValue() + " " +
                path.getDuration() + " " +
                nbPoints + " " +
                getNextIndex();

        return msg;
    }

    private String forgeSurveyMessage(Survey survey, InsertMode insertMode) {
        String msg = "";
        if(survey.getWpStart().getCoordinateSystem() == Waypoint.CoordinateSystem.LLA) {
            msg += MISSION_SURVEY_LLA;
        } else {
            msg += MISSION_SURVEY_LOCAL;
        }

        msg += " " +
                aircraftId + " " +
                insertMode.getValue() + " " +
                extractLatEast(survey.getWpStart()) + " " +
                extractLonNorth(survey.getWpStart()) + " " +
                extractLatEast(survey.getWpEnd()) + " " +
                extractLonNorth(survey.getWpEnd()) + " " +
                survey.getAltitude().intValue() + " " +
                survey.getDuration() + " " +
                getNextIndex();
        return msg;
    }

    private String extractLatEast(Waypoint wp) {
        if (wp.getCoordinateSystem() == Waypoint.CoordinateSystem.LOCAL) {
            return Double.toString(wp.getEast());
        } else {
            return Integer.toString((int)(wp.getLatitude() * 10000000));
        }
    }

    private String extractLonNorth(Waypoint wp) {
        if (wp.getCoordinateSystem() == Waypoint.CoordinateSystem.LOCAL) {
            return Double.toString(wp.getNorth());
        } else {
            return Integer.toString((int)(wp.getLongitude() * 10000000));
        }
    }

    private int getNextIndex() {
        return getAircraftIndex(nextIndex++);
    }

    @Override
    public void update(Observable observable, Object o) {
        if(o instanceof IncomeMessage) {
            IncomeMessage incomeMessage = (IncomeMessage) o;
            if(incomeMessage.getId() == missionStatusMessageId) {
                System.out.println(aircraftId + " MISSION_STATUS " + incomeMessage.getPayload()[0] + " " + incomeMessage.getPayload()[1]);
                double time = Double.parseDouble(incomeMessage.getPayload()[0].replace(".", ","));
                Integer[] indexes = parseIndexes(incomeMessage.getPayload()[1]);
                currentInstructionAircraftIndex = indexes[0];
                updatePendingInstructions(indexes);

            }
        }

    }

    private void updatePendingInstructions(Integer[] indexes) {
        ArrayList<Instruction> removeList = new ArrayList<>();
        for(Instruction instruction : pendingInstructions) {
            Integer aircraftIndex = getAircraftIndex(instruction.getIndex());
            switch (instruction.getState()) {
                case NOT_SENT:
                    //do not expect to be acknowledged by the aircraft if it was not sent
                    break;
                case SENT:
                    if(contains(indexes, aircraftIndex)) {      //the instruction sent is acknowledged by the aircraft
                        instruction.setState(Instruction.State.ACKNOWLEDGED);
                    }
                    //break;
                case ACKNOWLEDGED:
                    if(indexes[0].equals(aircraftIndex)) {                  //the first instruction in the aircraft list is the running one
                        instruction.setState(Instruction.State.RUNNING);
                    } else if(!contains(indexes, aircraftIndex)){           //this index disappear without being first in the aircraft list
                        System.out.println("Suspicious: instruction DONE without being RUNNING.");
                        instruction.setState(Instruction.State.DONE);
                        instructions.add(instruction);
                        removeList.add(instruction);
                    }
                    break;
                case CANCELED:
                    if(!contains(indexes, aircraftIndex)) {     //an acknowledged instruction disappear from the aircraft list : well canceled.
                        removeList.add(instruction);
                    }
                    if(indexes[0].equals(aircraftIndex)) {      //despite the cancellation, this instruction is running !
                        instruction.setState(Instruction.State.ABORTED);
                    }
                    break;
                case RUNNING:
                    if(!contains(indexes, aircraftIndex)) {     //the running instruction is no longer in the aircraft list : it is done.
                        instruction.setState(Instruction.State.DONE);
                        instructions.add(instruction);
                        removeList.add(instruction);
                    }
                    break;
                case ABORTED:
                    if(!contains(indexes, aircraftIndex)) {     //the aborted instruction is no longer in the aircraft list : it is well aborted.
                        instructions.add(instruction);
                        removeList.add(instruction);
                    }
                    break;
                case DONE:                                      // a done instruction should not be here !
                    System.out.println("DONE instruction should not be in pendingInstructions List !");
                    break;
            }
        }
        pendingInstructions.removeAll(removeList);
    }

    private Integer getAircraftIndex(int index) {
        return index % (1<<INDEX_BIT_LENGTH);
    }

    private Integer[] parseIndexes(String s) {
        String[] items = (s.substring(0, s.length()-1)).split(",");
        Integer[] indexes = new Integer[items.length];
        for (int i = 0; i < items.length; i++) {
            indexes[i] = Integer.parseInt(items[i]);
        }
        return indexes;
    }

    public static <T> boolean  contains(T[] array, T object) {
        for(T t : array) {
            if(t.equals(object)) {
                return true;
            }
        }
        return false;
    }

    public enum InsertMode {
        APPEND(0),
        PREPEND(1),
        REPLACE_CURRENT(2),
        REPLACE_ALL(3),
        REPLACE_NEXTS(4);

        private int value;
        InsertMode(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
