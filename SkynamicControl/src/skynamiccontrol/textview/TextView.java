package skynamiccontrol.textview;

import skynamiccontrol.communication.IncomeMessage;
import skynamiccontrol.communication.IvyManager;
import skynamiccontrol.model.Waypoint;
import skynamiccontrol.model.mission.Circle;
import skynamiccontrol.model.mission.GoToWP;
import skynamiccontrol.model.mission.MissionManager;
import skynamiccontrol.model.mission.Survey;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

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
        //IvyManager.getInstance().registerRegex("(.*GPS.*)");
    }

    public boolean mainLoop() {
        Scanner scanner = new Scanner(System.in);;
        String command = scanner.next();
        Circle circle;
        GoToWP goToWP;
        Survey survey;
        Waypoint.CoordinateSystem coordinateSystem = Waypoint.CoordinateSystem.LLA;
        double lat, lon, alt, radius, duration;
        switch (command) {
            case "circle":
                System.out.println("local vs lla ? (0/1)");
                if(scanner.nextInt() == 0) {
                    coordinateSystem = Waypoint.CoordinateSystem.LOCAL;
                }
                System.out.println("lat : ");
                lat = scanner.nextDouble();
                System.out.println("lon : ");
                lon = scanner.nextDouble();
                System.out.println("alt : ");
                alt = scanner.nextDouble();
                System.out.println("radius : ");
                radius = scanner.nextDouble();
                System.out.println("duration : ");
                duration = scanner.nextDouble();


                circle = new Circle(new Waypoint(lat, lon, alt, coordinateSystem), radius);
                circle.setDuration(duration);
                missionManager.insertInstruction(circle, MissionManager.InsertMode.APPEND);
                break;
            case "c1":
                circle = new Circle(new Waypoint(43.4637222, 1.2751827, 300, Waypoint.CoordinateSystem.LLA), 100);
                circle.setDuration(20);
                missionManager.insertInstruction(circle, MissionManager.InsertMode.APPEND);
                break;
            case "c2":
                circle = new Circle(new Waypoint(200,600, 350, Waypoint.CoordinateSystem.LOCAL), 200);
                circle.setDuration(30);
                missionManager.insertInstruction(circle, MissionManager.InsertMode.APPEND);
                break;
            case "gt1":
                goToWP = new GoToWP(new Waypoint(43.4637222, 1.2751827, 300, Waypoint.CoordinateSystem.LLA));
                goToWP.setDuration(50);
                missionManager.insertInstruction(goToWP, MissionManager.InsertMode.APPEND);
                break;
            case "gt2":
                goToWP = new GoToWP(new Waypoint(100, 200, 300, Waypoint.CoordinateSystem.LOCAL));
                goToWP.setDuration(55);
                missionManager.insertInstruction(goToWP, MissionManager.InsertMode.APPEND);
                break;
            case "s1":
                survey = new Survey(43.46, 1.27, 43.4, 1.3, 330, Waypoint.CoordinateSystem.LLA);
                survey.setDuration(50);
                missionManager.insertInstruction(survey, MissionManager.InsertMode.APPEND);
                break;
            case "s2":
                survey = new Survey(0, 400, 600, 0, 360, Waypoint.CoordinateSystem.LOCAL);
                survey.setDuration(50);
                missionManager.insertInstruction(survey, MissionManager.InsertMode.APPEND);
                break;
            case "n":
                missionManager.goToNextInstruction();
                break;
            case "q":
                stop();
                return false;
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
        System.out.println(msg.getId() + "    " + msg.getPayload()[0]);
    }
}
