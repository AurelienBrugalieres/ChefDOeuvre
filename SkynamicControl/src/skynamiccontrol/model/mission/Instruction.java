package skynamiccontrol.model.mission;

/**
 * Created by fabien on 13/02/17.
 */
public abstract  class Instruction {
    private String name;
    private int index;          //index as in the aircraft
    private double duration;
    private InsertMode insertMode;
    private State state = State.NOT_SENT;

    public enum State {
        NOT_SENT(0),
        SENT(1),
        ACKNOWLEDGED(3),   //pending
        CANCELED(4),       //the instruction has been acknowledged by the aircraft, then canceled by the user, but the cancellation has not been acknowledged yet (so the aircraft can still run this instruction) ;
        RUNNING(5),        //acknowledged and running
        ABORTED(6),        //the instruction was aborted during it was running. Can happen if "next instruction" was called, or if other instruction was sent with REPLACE_ALL or REPLACE_CURRENT insertion mode.
        DONE(7);

        private int nb;

        State(int nb) {
            this.nb = nb;
        }

        public int getNb() {
            return nb;
        }
    }

    public InsertMode getInsertMode() {
        return insertMode;
    }

    public void setInsertMode(InsertMode insertMode) {
        this.insertMode = insertMode;
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

    public abstract Double getAltitude();
}
