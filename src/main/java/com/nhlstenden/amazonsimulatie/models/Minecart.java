package com.nhlstenden.amazonsimulatie.models;

import java.util.*;

/*
 * Deze class stelt een Minecart voor. Hij impelementeerd de class Object3D, omdat het ook een
 * 3D object is. Ook implementeerd deze class de interface Updatable. Dit is omdat
 * een Minecart geupdate kan worden binnen de 3D wereld om zich zo voort te bewegen.
 */
class Minecart implements Object3D, Updatable {
    private UUID uuid;

    private double x = 1.5, y = 2.15, z = -10, rotationX, rotationY = 0.5*Math.PI, rotationZ, inZ = 0, outZ = -10;

    private String location;
    private double linearSpeed = 0.1;
    private long targetTime;

    public Stack<String> orders;

    public Minecart() {
        this.uuid = UUID.randomUUID();
        orders = new Stack<String>();
        location = "Out";
    }

    /*
     * Deze update methode wordt door de World aangeroepen wanneer de
     * World zelf geupdate wordt. Dit betekent dat elk object, ook deze
     * Minecart, in de 3D wereld steeds een beetje tijd krijgt om een update
     * uit te voeren. In de updatemethode hieronder schrijf je dus de code
     * die de Minecart steeds uitvoert (bijvoorbeeld positieveranderingen). Wanneer
     * de methode true teruggeeft (zoals in het voorbeeld), betekent dit dat
     * er inderdaad iets veranderd is en dat deze nieuwe informatie naar de views
     * moet worden gestuurd. Wordt false teruggegeven, dan betekent dit dat er niks
     * is veranderd, en de informatie hoeft dus niet naar de views te worden gestuurd.
     * (Omdat de informatie niet veranderd is, is deze dus ook nog steeds hetzelfde als
     * in de view)
     */
    @Override
    public boolean update() {
        if(location == "Out" && z != -10){
            moveTo(1.5,-10);
            return true;
        }
        if(location == "In" && z != 0){
            moveTo(1.5, 0);
            return true;
        }
        return false;
    }

    /*
    *  When you give the minecart a target X and Z he will move to this target with given linearSpeed every update.
    */
    public void moveTo(double targetX, double targetZ) {
        if (targetX - x < 0) {
            if (targetX < x) {
                x -= linearSpeed;
            }
        } else
            if (targetX > x) {
                x += linearSpeed;
            }

        if (targetZ - z < 0) {
            if (targetZ < z) {
                z -= linearSpeed;
            }
        } else
            if (targetZ > z) {
                z += linearSpeed;
            }

        if (Math.abs(x - targetX) < 2 * linearSpeed) {
            x = targetX;
        }

        if (Math.abs(z - targetZ) < 2 * linearSpeed) {
            z = targetZ;
        }
    }

    //private void waiting(long seconds){
    //   targetTime = System.currentTimeMillis() + (seconds*1000);
    //}

    public void setLocation(String location){
        this.location = location;
    }

    public String getLocation(){
        if(location == "In" && z == inZ)
            return "minecartIsOnDock";
        if(location == "Out" && z == outZ)
            return "minecartIsOnStarting";
        else
            return "Moving";
    }

    @Override
    public String getUUID() {
        return this.uuid.toString();
    }

    @Override
    public String getType() {
        /*
         * Dit onderdeel wordt gebruikt om het type van dit object als stringwaarde terug
         * te kunnen geven. Het moet een stringwaarde zijn omdat deze informatie nodig
         * is op de client, en die verstuurd moet kunnen worden naar de browser. In de
         * javascript code wordt dit dan weer verder afgehandeld.
         */
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
    public String getCondition() { return null; }

}