package skynamiccontrol.textview;

import skynamiccontrol.communication.IncomeMessage;
import skynamiccontrol.communication.IvyManager;
import skynamiccontrol.model.Waypoint;
import skynamiccontrol.model.mission.*;

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
        //IvyManager.getInstance().addObserver(this);
        //IvyManager.getInstance().registerRegex("(.*MISSION_STATUS.*)");
        //IvyManager.getInstance().registerRegex("(.*GPS.*)");
    }

    public boolean mainLoop() {
        Scanner scanner = new Scanner(System.in);;
        String command = scanner.next();
        Circle circle;
        GoToWP goToWP;
        Survey survey;
        Path path;
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
                circle.setInsertMode(Instruction.InsertMode.APPEND);
                missionManager.addInstruction(circle);
                break;
            case "c1":
                circle = new Circle(new Waypoint(43.4637222, 1.2751827, 300, Waypoint.CoordinateSystem.LLA), 100);
                circle.setDuration(20);
                circle.setInsertMode(Instruction.InsertMode.APPEND);
                missionManager.addInstruction(circle);
                break;
            case "c2":
                circle = new Circle(new Waypoint(200,600, 350, Waypoint.CoordinateSystem.LOCAL), 200);
                circle.setDuration(30);
                circle.setInsertMode(Instruction.InsertMode.APPEND);
                missionManager.addInstruction(circle);
                break;
            case "gt1":
                goToWP = new GoToWP(new Waypoint(43.4637222, 1.2751827, 323, Waypoint.CoordinateSystem.LLA));
                goToWP.setDuration(50);
                goToWP.setInsertMode(Instruction.InsertMode.APPEND);
                missionManager.addInstruction(goToWP);
                break;
            case "gt2":
                goToWP = new GoToWP(new Waypoint(100, 200, 321, Waypoint.CoordinateSystem.LOCAL));
                goToWP.setDuration(55);
                goToWP.setInsertMode(Instruction.InsertMode.APPEND);
                missionManager.addInstruction(goToWP);
                break;
            case "p1":
                path = new Path();
                path.addWaypoint(new Waypoint(43.4637117,1.2756799, 320, Waypoint.CoordinateSystem.LLA));
                path.addWaypoint(new Waypoint(43.4661319, 1.2768373, 340, Waypoint.CoordinateSystem.LLA));
                path.addWaypoint(new Waypoint(43.4665644, 1.2726432, 340, Waypoint.CoordinateSystem.LLA));
                path.setDuration(700);
                path.setInsertMode(Instruction.InsertMode.APPEND);
                missionManager.addInstruction(path);
                break;
            case "c1r":
                circle = new Circle(new Waypoint(43.4637222, 1.2751827, 300, Waypoint.CoordinateSystem.LLA), 100);
                circle.setDuration(20);
                circle.setInsertMode(Instruction.InsertMode.REPLACE_NEXTS);
                missionManager.addInstruction(circle);
                break;
            case "c2r":
                circle = new Circle(new Waypoint(200,600, 350, Waypoint.CoordinateSystem.LOCAL), 200);
                circle.setDuration(30);
                circle.setInsertMode(Instruction.InsertMode.REPLACE_NEXTS);
                missionManager.addInstruction(circle);
                break;
            case "gt1r":
                goToWP = new GoToWP(new Waypoint(43.4637222, 1.2751827, 323, Waypoint.CoordinateSystem.LLA));
                goToWP.setDuration(50);
                goToWP.setInsertMode(Instruction.InsertMode.REPLACE_NEXTS);
                missionManager.addInstruction(goToWP);
                break;
            case "gt2r":
                goToWP = new GoToWP(new Waypoint(100, 200, 321, Waypoint.CoordinateSystem.LOCAL));
                goToWP.setDuration(55);
                goToWP.setInsertMode(Instruction.InsertMode.REPLACE_NEXTS);
                missionManager.addInstruction(goToWP);
                break;
            case "p1r":
                path = new Path();
                path.addWaypoint(new Waypoint(43.4637117,1.2756799, 320, Waypoint.CoordinateSystem.LLA));
                path.addWaypoint(new Waypoint(43.4661319, 1.2768373, 340, Waypoint.CoordinateSystem.LLA));
                path.addWaypoint(new Waypoint(43.4665644, 1.2726432, 340, Waypoint.CoordinateSystem.LLA));
                path.setDuration(700);
                path.setInsertMode(Instruction.InsertMode.REPLACE_NEXTS);
                missionManager.addInstruction(path);
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
//        IncomeMessage msg = (IncomeMessage)o;
//        if(msg.getPayload()[0].equals(lastStatus)){
//            return;
//        }
//        if(msg.getPayload()[0].contains("MISSION_STATUS")){
//            lastStatus = msg.getPayload()[0];
//        }
//        System.out.println(msg.getId() + "    " + msg.getPayload()[0]);
    }
}
