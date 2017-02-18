package skynamiccontrol.model.mission;

/**
 * Created by fabien on 13/02/17.
 */
public class Instruction {
    private String name;
    private int index;          //index as in the aircraft
    private double duration;
    private State state = State.NOT_SENT;

    public enum State {
        NOT_SENT(0),
        SENT(1),
        ACKNOWLEDGED(2),   //pending
        CANCELED(3),       //the instruction has been acknowledged by the aircraft, then canceled by the user, but the cancellation has not been acknowledged yet (so the aircraft can still run this instruction) ;
        RUNNING(4),        //acknowledged and running
        ABORTED(5),        //the instruction was aborted during it was running. Can happen if "next instruction" was called, or if other instruction was sent with REPLACE_ALL or REPLACE_CURRENT insertion mode.
        DONE(6);

        private int nb;

        State(int nb) {
            this.nb = nb;
        }

        public int getNb() {
            return nb;
        }
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

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
