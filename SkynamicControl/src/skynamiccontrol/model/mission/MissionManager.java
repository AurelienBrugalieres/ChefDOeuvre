package skynamiccontrol.model.mission;

import skynamiccontrol.communication.IncomeMessage;
import skynamiccontrol.communication.IvyManager;
import skynamiccontrol.model.Constants;
import skynamiccontrol.model.Waypoint;

import java.util.*;

/**
 * Created by fabien on 13/02/17.
 */
public class MissionManager  extends Observable implements Observer{
    private int missionStatusMessageId;
    private int aircraftId;
    private ArrayList<Instruction> pastInstructions;
    private int currentInstructionAircraftIndex;
    private Queue<Instruction> instructionsToSend;
    private int nextIndex;
    private ArrayList<Instruction> pendingInstructions;
    private Instruction travelingInstruction;
    private long timeSinceSent;
    private InstructionsSender instructionsSender;
    private Timer instructionsSenderTimer;
    private int nbInstructionsInAircraft;

    public MissionManager(int aircraftId) {
        this.aircraftId = aircraftId;
        pastInstructions = new ArrayList<>();
        currentInstructionAircraftIndex = 0;
        travelingInstruction = null;
        instructionsToSend = new LinkedList<>();
        pendingInstructions = new ArrayList<>();
        nbInstructionsInAircraft = 0;
        nextIndex = 1;              //start at 1 to discriminate from "no instruction" case
        IvyManager.getInstance().addObserver(this);
        missionStatusMessageId = IvyManager.getInstance().registerRegex(aircraftId + " MISSION_STATUS " + "(.*) (.*)");
        instructionsSenderTimer = new Timer();
        instructionsSender = new InstructionsSender();
        instructionsSenderTimer.scheduleAtFixedRate(instructionsSender, 0, Constants.DELAY_BETWEEN_SEND);
    }


    public void addInstruction(Instruction instruction) {
        instructionsToSend.add(instruction);
        setChanged();
        notifyObservers();
    }

    private String getMessage(Instruction instruction) {
        String msg;
        int index = nextIndex++;
        if(getAircraftIndex(index) == 0) {    //if aircraftIndex = 0, it cannot be discriminate from the "no instruction" case.
            index = nextIndex ++;
        }
        instruction.setIndex(index);
        try {
            if(instruction instanceof Circle) {
                msg = forgeCircleMessage((Circle) instruction, index);
            } else if(instruction instanceof GoToWP) {
                msg = forgeGoToWPMessage((GoToWP) instruction, index);
            } else if(instruction instanceof Path) {
                msg = forgePathMessage((Path) instruction, index);
            } else if(instruction instanceof Survey) {
                msg = forgeSurveyMessage((Survey) instruction, index);
            } else {
                msg = "";
            }
        } catch (Exception e) {
            msg = "";
        }

        return msg;
    }

    public void goToNextInstruction() {
        String msg = "NEXT_MISSION" + " " + aircraftId;
        IvyManager.getInstance().sendMessage(msg);
    }

    public ArrayList<Instruction> getPastInstructions() {
        return pastInstructions;
    }

    public ArrayList<Instruction> getPendingInstructions() {
        return pendingInstructions;
    }

    public int getCurrentInstructionAircraftIndex() {
        return currentInstructionAircraftIndex;
    }

    private void appendInstruction(Instruction instruction) {
        pendingInstructions.add(instruction);
    }

    private void replaceCurrentInstruction(Instruction instruction) {
        pendingInstructions.add(getInsertIndex(), instruction);
    }

    private void replaceAllInstructions(Instruction instruction) {
        ArrayList<Instruction> removeList = new ArrayList<>();
        for (Instruction ins : pendingInstructions) {
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
        ArrayList<Instruction> removeList = new ArrayList<>();
        for (Instruction ins : pendingInstructions) {
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

    private String forgeCircleMessage(Circle circle, int index) {
        String msg = "";
        if(circle.getCenter().getCoordinateSystem() == Waypoint.CoordinateSystem.LLA) {
            msg += Constants.MISSION_CIRCLE_LLA;
        } else {
            msg += Constants.MISSION_CIRCLE_LOCAL;
        }

        msg += " " +
                aircraftId + " " +
                circle.getInsertMode().getValue() + " " +
                extractLatEast(circle.getCenter()) + " " +
                extractLonNorth(circle.getCenter()) + " " +
                circle.getAltitude().intValue() + " " +
                circle.getRadius() + " " +
                circle.getDuration() + " " +
                getAircraftIndex(index);
        return msg;
    }

    private String forgeGoToWPMessage(GoToWP goToWP, int index) {
        String msg = "";
        if(goToWP.getWaypoint().getCoordinateSystem() == Waypoint.CoordinateSystem.LLA) {
            msg += Constants.MISSION_GOTOWP_LLA;
        } else {
            msg += Constants.MISSION_GOTOWP_LOCAL;
        }

        msg += " " +
                aircraftId + " " +
                goToWP.getInsertMode().getValue() + " " +
                extractLatEast(goToWP.getWaypoint()) + " " +
                extractLonNorth(goToWP.getWaypoint()) + " " +
                goToWP.getAltitude().intValue() + " " +
                goToWP.getDuration() + " " +
                getAircraftIndex(index);
        return msg;
    }

    private String forgePathMessage(Path path, int index) throws Exception {
        int nbPoints = path.getNbWaypoints();
        if(nbPoints < 2) {
            throw new Exception("Path with less than 2 points.");
        }

        String msg = "";
        if(path.getWaypoint(0).getCoordinateSystem() == Waypoint.CoordinateSystem.LLA) {
            if(nbPoints == 2) {
                msg += Constants.MISSION_SEGMENT_LLA;
            } else {
                msg += Constants.MISSION_PATH_LLA;
            }
        } else {
            if(nbPoints == 2) {
                msg += Constants.MISSION_SEGMENT_LOCAL;
            } else {
                msg += Constants.MISSION_PATH_LOCAL;
            }
        }

        msg += " " +
                aircraftId + " " +
                path.getInsertMode().getValue() + " ";
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
                getAircraftIndex(index);

        return msg;
    }

    private String forgeSurveyMessage(Survey survey, int index) {
        String msg = "";
        if(survey.getWpStart().getCoordinateSystem() == Waypoint.CoordinateSystem.LLA) {
            msg += Constants.MISSION_SURVEY_LLA;
        } else {
            msg += Constants.MISSION_SURVEY_LOCAL;
        }

        msg += " " +
                aircraftId + " " +
                survey.getInsertMode().getValue() + " " +
                extractLatEast(survey.getWpStart()) + " " +
                extractLonNorth(survey.getWpStart()) + " " +
                extractLatEast(survey.getWpEnd()) + " " +
                extractLonNorth(survey.getWpEnd()) + " " +
                survey.getAltitude().intValue() + " " +
                survey.getDuration() + " " +
                getAircraftIndex(index);
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

    @Override
    public void update(Observable observable, Object o) {
        if(o instanceof IncomeMessage) {
            IncomeMessage incomeMessage = (IncomeMessage) o;
            if(incomeMessage.getId() == missionStatusMessageId) {
                //System.out.println(aircraftId + " MISSION_STATUS " + incomeMessage.getPayload()[0] + " " + incomeMessage.getPayload()[1]);
                double time = Double.parseDouble(incomeMessage.getPayload()[0]);
                Integer[] indexes = parseIndexes(incomeMessage.getPayload()[1]);
                nbInstructionsInAircraft = indexes.length;
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
                        travelingInstruction = null;            //so we can allow any operation (not only APPEND)
                        setChanged();
                    }
                    //break;
                case ACKNOWLEDGED:
                    if(indexes[0].equals(aircraftIndex)) {                  //the first instruction in the aircraft list is the running one
                        instruction.setState(Instruction.State.RUNNING);
                        setChanged();
                    } else if(!contains(indexes, aircraftIndex)){           //this index disappear without being first in the aircraft list
                        System.out.println("Suspicious: instruction DONE without being RUNNING : Instruction " + aircraftIndex);
                        instruction.setState(Instruction.State.DONE);
                        pastInstructions.add(instruction);
                        removeList.add(instruction);
                        setChanged();
                    }
                    break;
                case CANCELED:
                    if(!contains(indexes, aircraftIndex)) {     //an acknowledged instruction disappear from the aircraft list : well canceled.
                        removeList.add(instruction);
                        setChanged();
                    }
                    if(indexes[0].equals(aircraftIndex)) {      //despite the cancellation, this instruction is running !
                        instruction.setState(Instruction.State.ABORTED);
                        setChanged();
                    }
                    break;
                case RUNNING:
                    if(!contains(indexes, aircraftIndex)) {     //the running instruction is no longer in the aircraft list : it is done.
                        instruction.setState(Instruction.State.DONE);
                        pastInstructions.add(instruction);
                        removeList.add(instruction);
                        setChanged();
                    }
                    break;
                case ABORTED:
                    if(!contains(indexes, aircraftIndex)) {     //the aborted instruction is no longer in the aircraft list : it is well aborted.
                        pastInstructions.add(instruction);
                        removeList.add(instruction);
                        setChanged();
                    }
                    break;
                case DONE:                                      // a done instruction should not be here !
                    System.out.println("DONE instruction should not be in pendingInstructions List !");
                    setChanged();
                    break;
            }
        }
        pendingInstructions.removeAll(removeList);
        notifyObservers();
    }

    private Integer getAircraftIndex(int index) {
        return index % Constants.MAX_INDEX_VALUE;
    }

    private Integer[] parseIndexes(String s) {
        String[] items = (s.substring(0, s.length()-1)).split(",");
        Integer[] indexes = new Integer[items.length];
        for (int i = 0; i < items.length; i++) {
            indexes[i] = Integer.parseInt(items[i]);
        }
        return indexes;
    }

    private static <T> boolean  contains(T[] array, T object) {
        for(T t : array) {
            if(t.equals(object)) {
                return true;
            }
        }
        return false;
    }

    public void sendAgain() {
        instructionsSender.sendMessage(travelingInstruction);
    }

    public void stop() {
        instructionsSenderTimer.cancel();
    }

    /**
     * Check if the previous sent instruction was acknowledged and if so, send the next instruction.
     */
    private class InstructionsSender extends TimerTask {

        @Override
        public void run() {
            if(travelingInstruction != null){       //last instruction sent not yet acknowledged
                timeSinceSent += Constants.DELAY_BETWEEN_SEND;
                if(timeSinceSent > Constants.SEND_TIMEOUT) {
                    //System.out.println("too long !!!");     //TODO: notify user
                }
                return;
            }
            Instruction instructionToSend = instructionsToSend.peek();
            if(instructionToSend == null || nbInstructionsInAircraft >= Constants.NB_MAX_INSTRUCTIONS) {
                return;
            }
            sendMessage(instructionToSend);
            instructionsToSend.poll();
        }

        public void sendMessage(Instruction instructionToSend) {
            String message = getMessage(instructionToSend);
            //System.out.println(message);
            IvyManager.getInstance().sendMessage(message);
            instructionToSend.setState(Instruction.State.SENT);
            travelingInstruction = instructionToSend;
            switch(instructionToSend.getInsertMode()) {
                case APPEND:
                    appendInstruction(instructionToSend);
                    break;
                case PREPEND:
                    //prependInstruction(instruction);
                    break;
                case REPLACE_CURRENT:
                    replaceCurrentInstruction(instructionToSend);
                    break;
                case REPLACE_ALL:
                    replaceAllInstructions(instructionToSend);
                    break;
                case REPLACE_NEXTS:
                    replaceNextsInstructions(instructionToSend);
                    break;
            }
        }
    }
}
