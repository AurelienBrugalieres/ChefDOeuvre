package skynamiccontrol.model.mission;

/**
 * Created by fabien on 13/02/17.
 */
public class Instruction {
    private String name;
    private String id;
    private float duration;
    private Status status = Status.NOT_SENT;

    public enum Status {
        NOT_SENT,
        SENT,
        ACKNOWLEDGED
    }
}
