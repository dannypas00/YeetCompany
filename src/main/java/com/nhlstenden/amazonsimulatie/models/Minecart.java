package com.nhlstenden.amazonsimulatie.models;


import java.util.UUID;

/**
 * Deze class stelt een Minecart voor. Hij impelementeerd de class Object3D, omdat het ook een
 * 3D object is. Ook implementeerd deze class de interface Updatable. Dit is omdat
 * een Minecart geupdate kan worden binnen de 3D wereld om zich zo voort te bewegen.
 */
class Minecart implements Object3D, Updatable {
    private UUID uuid;
    /**starting position, speed and rotation */
    private double x = 1.5, y = 2.15, z = -10, rotationX, rotationY = 0.5*Math.PI, rotationZ, linearSpeed = 0.1;;
    /** Locations for the minecart to be in place and out of screen */
    private double inZ = 0, outZ = -10;
    /**string for OrderHandler class to see where the minecart is at this time */
    private String location;

    public Minecart() {
        this.uuid = UUID.randomUUID();
    }

    /**
     *This update method gets called by the World class when the World gets updated.
     * If the location of the minecart is on "Out" and the end position has not been reached.
     * Move to the end position.
     * If the location of the minecart is on "In" and the end position has not been reached.
     * Move to the end position.
     */
    @Override
    public boolean update() {
        if(location == "Out" && z != outZ){
            moveTo(outZ);
            return true;
        }
        if(location == "In" && z != inZ){
            moveTo(inZ);
            return true;
        }
        return false;
    }

    /**
    * This class gets called by the update method. The update method gives a position to move to.
     * The minecart will then move to this target with "linearSpeed" everytime it gets called by the update.
    */
    private void moveTo(double targetZ) {
        if (targetZ - z < 0) {
            if (targetZ < z) {
                z -= linearSpeed;
            }
        } else {
            if (targetZ > z) {
                z += linearSpeed;
            }
        }
        if (Math.abs(z - targetZ) < 2 * linearSpeed) {
            z = targetZ;
        }
    }

    public void setLocation(String location){
        this.location = location;
    }

    @Override
    public String getUUID() {
        return this.uuid.toString();
    }

    @Override
    public String getType() {
        return Minecart.class.getSimpleName().toLowerCase();
    }

    @Override
    public double getX() {
        return this.x;
    }

    @Override
    public double getY() {
        return this.y;
    }

    @Override
    public double getZ() {
        return this.z;
    }

    @Override
    public double getRotationX() {
        return this.rotationX;
    }

    @Override
    public double getRotationY() {
        return this.rotationY;
    }

    @Override
    public double getRotationZ() {
        return this.rotationZ;
    }

    @Override
    public String getCondition() {
        return null;
    }

    public double getInZ() {
        return this.inZ;
    }

    public double getOutZ() {
        return this.outZ;
    }

    public String getLocation() {
        return location;
    }

}