package skynamiccontrol.communication;

/**
 * Created by fabien on 13/02/17.
 */
public class IncomeMessage {
    private int id;
    private String[] payload;

    public IncomeMessage(int id, String[] payload) {
        this.id = id;
        this.payload = payload;
    }

    public int getId() {
        return id;
    }

    public String[] getPayload() {
        return payload;
    }

    public int getPayloadLenght() {
        return payload.length;
    }
}
