package skynamiccontrol.model.mission;

import skynamiccontrol.model.Waypoint;

/**
 * Created by fabien on 13/02/17.
 */
public class Circle extends Instruction {
    private Waypoint center;
    private double radius;
    private Double altitude = null; //if default value overrided. (default value = waypoint1.altitude)

    public Circle(Waypoint center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    public void setAltitude(Double altitude) {
        if(getCommunicationState() == CommunicationState.NOT_SENT) {
            this.altitude = altitude;
        }
    }

    public void setCenter(Waypoint center) {
        if(getCommunicationState() == CommunicationState.NOT_SENT) {
            this.center = center;
        }
    }

    public void setRadius(double radius) {
        if(getCommunicationState() == CommunicationState.NOT_SENT) {
            this.radius = radius;
        }
    }

    public Waypoint getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }

    public Double getAltitude() {
        if(altitude == null) {
            return center.getAltitude();
        } else {
            return altitude;
        }
    }
}
