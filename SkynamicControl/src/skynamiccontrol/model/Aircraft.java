package skynamiccontrol.model;

import javafx.beans.InvalidationListener;
import skynamiccontrol.model.mission.MissionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by fabien on 13/02/17.
 */
public class Aircraft extends Observable{

    public final static String AIRCRAFT_STATUS_PROPERTY = "aircraft_status";
    public static final double MAX_BATTERY_VOLTAGE = 100;
    public static final double MIN_BATTERY_VOLTAGE = 0;
    private int id;
    private String name;
    private MissionManager missionManager;
    private double batteryLevel;
    private double altitude;
    private double speed;
    private Status current_status;

    private List<Observer> observers;

    public Aircraft() {
        this.observers = new ArrayList<>();

    }

    public Aircraft(int id, String name, double batteryLevel, double altitude, double speed, Status current_status) {
        this.observers = new ArrayList<>();
        this.id = id;
        this.name = name;
        this.batteryLevel = batteryLevel;
        this.altitude = altitude;
        this.speed = speed;
        this.current_status = current_status;
    }

    public void addObserver(Observer obs) {
        this.observers.add(obs);
    }

    public void removeObserver(Observer obs) {
        this.observers.remove(obs);
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
}
