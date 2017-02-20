package skynamiccontrol.model;

import javafx.beans.InvalidationListener;
import skynamiccontrol.model.mission.MissionManager;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by fabien on 13/02/17.
 */
public class Aircraft extends Observable{

    public final static String AIRCRAFT_STATUS_PROPERTY = "aircraft_status";
    private double maxBatteryVoltage;
    private double minBatteryVoltage;
    private int id;
    private String name;
    private MissionManager missionManager;
    private double batteryLevel;
    private double altitude;
    private double speed;
    private Status current_status;
    private Color color;

   // private List<Observer> observers;

    public Aircraft() {
       // this.observers = new ArrayList<>();

    }

    public Aircraft(String name, int id, double minBatteryVoltage, double maxBatteryVoltage) {
        this.maxBatteryVoltage = maxBatteryVoltage;
        this.minBatteryVoltage = minBatteryVoltage;
        this.id = id;
        this.name = name;
        this.missionManager = new MissionManager(id);
        this.batteryLevel = maxBatteryVoltage;
        this.altitude = 0;
        this.speed = 0;
        this.current_status = Status.AUTO;
        this.color = Color.decode(Constants.getInstance().DEFAULT_AIRCRAFT_COLOR);
    }

    public Aircraft(int id, String name, double batteryLevel, double altitude, double speed, Status current_status, Color color_aircraft) {
      //  this.observers = new ArrayList<>();
        this.id = id;
        this.name = name;
        this.batteryLevel = batteryLevel;
        this.altitude = altitude;
        this.speed = speed;
        this.current_status = current_status;
        this.color = color_aircraft;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void addPrivateObserver(Observer obs) {
        addObserver(obs);
    }

    public void removeObserver(Observer obs) {
        removeObserver(obs);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        notifyObservers();
    }

    public MissionManager getMissionManager() {
        return missionManager;
    }

    public void setMissionManager(MissionManager missionManager) {
        this.missionManager = missionManager;
        notifyObservers();
    }

    public double getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(double batteryLevel) {
        this.batteryLevel = batteryLevel;
        notifyObservers();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyObservers();
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
        notifyObservers();
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
        notifyObservers();
    }

    public Status getCurrent_status() {
        return current_status;
    }

    public void setCurrent_status(Status current_status) {
        this.current_status = current_status;
        notifyObservers();
    }

    public double getMaxBatteryVoltage() {
        return maxBatteryVoltage;
    }

    public double getMinBatteryVoltage() {
        return minBatteryVoltage;
    }

    public double getBatteryPercentage() {
        return (batteryLevel - minBatteryVoltage)/(maxBatteryVoltage - minBatteryVoltage);
    }

    @SuppressWarnings("Duplicates")
    public static Aircraft loadAircraft(String filename) {
            BufferedReader br = null;
            FileReader fr = null;
            Aircraft aircraft = null;

            try {
                String sCurrentLine;
                fr = new FileReader(filename);
                br = new BufferedReader(fr);

                String name = "";
                double minVoltage = -1, maxVoltage = -1;
                int id = -1;

                while ((sCurrentLine = br.readLine()) != null) {
                    String items[] = sCurrentLine.split(" : ");
                    switch(items[0]) {
                        case "NAME":
                            name = items[1];
                            break;
                        case "BATTERY_VOLTAGE_MAX":
                            maxVoltage = Double.parseDouble(items[1]);
                            break;
                        case "BATTERY_VOLTAGE_MIN":
                            minVoltage = Double.parseDouble(items[1]);
                            break;
                        case "ID":
                            id = Integer.parseInt(items[1]);
                    }
                }
                aircraft = new Aircraft(name, id, minVoltage, maxVoltage);

            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {

                try {
                    if (br != null){
                        br.close();
                    }
                    if (fr != null){
                        fr.close();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            return aircraft;
        }
}
