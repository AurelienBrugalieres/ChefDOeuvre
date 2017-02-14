package skynamiccontrol.textview;

import skynamiccontrol.communication.IncomeMessage;
import skynamiccontrol.communication.IvyManager;
import skynamiccontrol.model.Waypoint;
import skynamiccontrol.model.mission.Circle;
import skynamiccontrol.model.mission.MissionManager;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by fabien on 14/02/17.
 */
public class TextView implements Observer{
    private MissionManager missionManager;
    private String lastStatus;
    public TextView() {
        lastStatus = "";
        IvyManager.getInstance().initIvyBus("SkynamicControl", "SkynamicControl Ready", "127.255.255.255:2010");
        missionManager = new MissionManager(14);
        IvyManager.getInstance().addObserver(this);
        IvyManager.getInstance().registerRegex("(.*MISSION_STATUS.*)");
    }

    public boolean mainLoop() {
        try {
            int key = 0;
            key = System.in.read();
            System.out.println(key);
            switch (key) {
                case 10:
                    Circle circle = new Circle(new Waypoint(43.4637222, 1.2751827, 350), 50);
                    circle.setDuration(20.f);
                    missionManager.insertInstruction(circle, MissionManager.InsertMode.APPEND);
                    break;
                case 1:

                    break;
                case 2:
                    return false;
            }




        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    private void stop() {
        IvyManager.getInstance().stop();
    }

    @Override
    public void update(Observable observable, Object o) {
        IncomeMessage msg = (IncomeMessage)o;
        if(msg.getPayload()[0].equals(lastStatus)){
            return;
        }
        if(msg.getPayload()[0].contains("MISSION_STATUS")){
            lastStatus = msg.getPayload()[0];
        }
        System.out.println(msg.getPayload()[0]);
    }
}
