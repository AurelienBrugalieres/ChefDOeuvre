package skynamiccontrol.model.mission;

/**
 * Created by fabien on 13/02/17.
 */
public class Instruction {
    private String name;
    private int index;          //index as in the aircraft
    private double duration;
    private CommunicationState communicationState = CommunicationState.NOT_SENT;
    private Status status = Status.PENDING;

    public enum CommunicationState {
        NOT_SENT,
        SENT,
        ACKNOWLEDGED,
        CANCELED        //the instruction has been acknowledged by the aircraft, then canceled by the user, but the cancellation has not been acknowledged (so the aircaft can still run this instruction)
    }

    public enum Status {
        PENDING,
        RUNNING,
        DONE
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public CommunicationState getCommunicationState() {
        return communicationState;
    }

    public void setCommunicationState(CommunicationState communicationState) {
        this.communicationState = communicationState;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
