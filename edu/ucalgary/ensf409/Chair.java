/**
 * Chair class is a class which contains fields unique to the chair class after 
 * inheriting from the Furniture abstract class.
 */

package edu.ucalgary.ensf409;

public class Chair extends Furniture {
    // class fields
    private boolean legs;
    private boolean arms;
    private boolean seat;
    private boolean cushion;
    // class methods

    // getter methods
    public boolean getArms() {
        return arms;
    }
    public boolean getCushion() {
        return cushion;
    }
    public boolean getLegs() {
        return legs;
    }
    public boolean getSeat() {
        return seat;
    }

    // setter methods
    public void setArms(boolean arms) {
        this.arms = arms;
    }
    public void setCushion(boolean cushion) {
        this.cushion = cushion;
    }
    public void setLegs(boolean legs) {
        this.legs = legs;
    }
    public void setSeat(boolean seat) {
        this.seat = seat;
    }

}