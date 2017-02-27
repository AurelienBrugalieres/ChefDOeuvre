package skynamiccontrol.model;

import skynamiccontrol.communication.IncomeMessage;
import skynamiccontrol.communication.IvyManager;
import skynamiccontrol.model.mission.MissionManager;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by fabien on 13/02/17.
 */
public class Aircraft extends Observable implements Observer{
    public final static String AIRCRAFT_STATUS_PROPERTY = "aircraft_status";
    private double maxBatteryVoltage;
    private double minBatteryVoltage;
    private int id;
    private String name;
    private MissionManager missionManager;
    private Color color;


    ////Flight parameters
    private double roll;
    private double pitch;
    private double heading;
    private double latitude;
    private double longitude;
    private double speed;
    private double course;
    private double altitude;
    private double agl;
    private double climb;
    private double airspeed;

    private double throttle;
    private int flightTime;
    private double batteryLevel;
    private String status;
    private String gpsMode;

    private int apStatusMsgId;
    private int engineStatusMsgId;
    private int flightParamsMsgId;


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
        this.status = "AUTO1";
        this.color = Color.decode(Constants.DEFAULT_AIRCRAFT_COLOR);

        //FLIGHT_PARAM ac_id roll pitch heading lat long speed course alt climb agl unix_time itow airspeed
        flightParamsMsgId = IvyManager.getInstance().registerRegex("ground FLIGHT_PARAM " + id + " (.*)");
        //ENGINE_STATUS ac_id throttle throttle_accu rpm temp bat amp energy
        engineStatusMsgId = IvyManager.getInstance().registerRegex("ground ENGINE_STATUS " + id + " (.*) (.*) (.*) (.*) (.*) (.*) (.*)");
        //AP_STATUS ac_id ap_mode lat_mode horiz_mode gaz_mode gps_mode kill_mode flight_time state_filter_mode
        apStatusMsgId = IvyManager.getInstance().registerRegex("ground AP_STATUS " + id + " (.*) (.*) (.*) (.*) (.*) (.*) (.*) (.*)");
        IvyManager.getInstance().addObserver(this);
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
    }

    public MissionManager getMissionManager() {
        return missionManager;
    }

    public void setMissionManager(MissionManager missionManager) {
        this.missionManager = missionManager;
    }

    public double getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(double batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public double getThrottle() {
        return throttle;
    }

    public int getFlightTime() {
        return flightTime;
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

    @Override
    public void update(Observable observable, Object o) {
        if(o instanceof IncomeMessage) {
            IncomeMessage incomeMessage = (IncomeMessage)o;
            String[] strs = incomeMessage.getPayload();
            int msgId = incomeMessage.getId();
            if(msgId== apStatusMsgId) {
                //ap_mode lat_mode horiz_mode gaz_mode gps_mode kill_mode flight_time state_filter_mode
                status = strs[0];
                gpsMode = strs[4];
                flightTime = Integer.parseInt(strs[6]);
                setChanged();
            } else if (msgId == engineStatusMsgId) {
                //throttle throttle_accu rpm temp bat amp energy
                throttle = Double.parseDouble(strs[0]);
                batteryLevel = Double.parseDouble(strs[4]);
                setChanged();
            } else if (msgId == flightParamsMsgId) {
                //11.883718 1.123226 198.300979 43.462915 1.274396 12.670000 198.7 260.997925 -0.030000 75.997925 1487725470.567468 263086240 12.700000
                //roll pitch heading lat long speed course alt climb agl unix_time itow airspeed
                String[] params = strs[0].split(" ");
                roll = Double.parseDouble(params[0]);
                pitch = Double.parseDouble(params[1]);
                heading = Double.parseDouble(params[2]);
                latitude = Double.parseDouble(params[3]);
                longitude = Double.parseDouble(params[4]);
                speed = Double.parseDouble(params[5]);
                course = Double.parseDouble(params[6]);
                altitude = Double.parseDouble(params[7]);
                climb = Double.parseDouble(params[8]);
                agl = Double.parseDouble(params[9]);
                airspeed = Double.parseDouble(params[10]);
                setChanged();

            }
            notifyObservers();
        }
    }
}
