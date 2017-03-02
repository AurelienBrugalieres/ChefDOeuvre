package skynamiccontrol;

import javafx.beans.NamedArg;
import javafx.event.Event;
import javafx.event.EventType;
import skynamiccontrol.model.Aircraft;
import skynamiccontrol.model.mission.Circle;
import skynamiccontrol.model.mission.Instruction;

/**
 * Created by fabien on 01/03/17.
 */
public class SkycEvent extends Event {

    private Instruction instruction;
    private Aircraft aircraft;

    public final static EventType<SkycEvent> CIRCLE_CREATED = new EventType<>("Circle Created");
    public final static EventType<SkycEvent> PATH_CREATED = new EventType<>("Path Created");
    public final static EventType<SkycEvent> GOTO_WP_CREATED = new EventType<>("GoToWp Created");
    public final static EventType<SkycEvent> APPEND_INSTRUCTION = new EventType<>("Append Instruction");
    public final static EventType<SkycEvent> CHOOSE_INSTRUCTION_EMPLACEMENT = new EventType<>("Choose Instruction Emplacement");
    public final static EventType<SkycEvent> TIMELINE_CLICKED = new EventType<>("Timeline Clicked");

    public SkycEvent(@NamedArg("eventType") EventType<? extends Event> eventType) {
        super(eventType);
    }


    public SkycEvent(@NamedArg("eventType") EventType<? extends Event> eventType, Instruction instruction) {
        super(eventType);
        this.instruction = instruction;
    }

    public SkycEvent(EventType<SkycEvent> circleCreated, Circle circle, Aircraft aircraft) {
        this(circleCreated, circle);
        this.aircraft = aircraft;
    }

    public Instruction getInstruction() {
        return instruction;
    }

    public Aircraft getAircraft() {
        return aircraft;
    }
}
