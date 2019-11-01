package com.nhlstenden.amazonsimulatie.models;

import java.util.Stack;
import java.util.UUID;

/*
 * Deze class stelt een robot voor. Hij impelementeerd de class Object3D, omdat het ook een
 * 3D object is. Ook implementeerd deze class de interface Updatable. Dit is omdat
 * een robot geupdate kan worden binnen de 3D wereld om zich zo voort te bewegen.
 */
class Robot implements Object3D, Updatable {
    private UUID uuid;

    private String state = "await";
    private double x, y, z, rotationX, rotationY, rotationZ, linearSpeed = 0.125, rotationSpeed = Math.PI/20, rad, deltaX, deltaZ;
    private long targetTime;
    private Node target;
    private Stack<Node> route, breadcrumbs = new Stack<Node>();
    private boolean carrying = false;

    public Robot () {
        this.uuid = UUID.randomUUID();
        System.out.println("[" + this.getUUID().toString() + "] Robot has been instantiated");
    }

    /*
     * Deze update methode wordt door de World aangeroepen wanneer de
     * World zelf geupdate wordt. Dit betekent dat elk object, ook deze
     * robot, in de 3D wereld steeds een beetje tijd krijgt om een update
     * uit te voeren. In de updatemethode hieronder schrijf je dus de code
     * die de robot steeds uitvoert (bijvoorbeeld positieveranderingen). Wanneer
     * de methode true teruggeeft (zoals in het voorbeeld), betekent dit dat
     * er inderdaad iets veranderd is en dat deze nieuwe informatie naar de views
     * moet worden gestuurd. Wordt false teruggegeven, dan betekent dit dat er niks
     * is veranderd, en de informatie hoeft dus niet naar de views te worden gestuurd.
     * (Omdat de informatie niet veranderd is, is deze dus ook nog steeds hetzelfde als
     * in de view)
     */
    @Override
    public boolean update() {
        if (target != null && System.currentTimeMillis() > targetTime) {
            moveTo(target);
            rotateTo(target);
            return true;
        } else return false;
    }

    /*
     * Makes the robot move to the nodes in the stack "route" and fill a stack with breadcrumbs to find his way back.
     * He gets this stack from the Orderhandler class
    */
    public boolean goRoute (Stack<Node> route, String order) {
        if (this.route == null || this.route.isEmpty()) {
            this.route = route;
            target = route.pop();
            breadcrumbs.push(target);
            carrying = carrying && (order == "put" && state == "moving" || order == "pull" && state == "returning");
            return true;
        } else return false;
    }

    /*Function to rotate the robot towards the target*/
    private void rotateTo(Node targetNode) {
        int targetSign = 1;
        if (targetNode.getX() != x) {
            targetSign = (int) Math.signum(targetNode.getX());
        }
        if (targetNode.getZ() != z) {
            targetSign = (int) Math.signum(targetNode.getZ());
        }
        deltaX = x - targetNode.getX();
        deltaZ = z - targetNode.getZ();
        rad = Math.atan2(deltaZ, deltaX);
        if(rad < 0)
            rad += 2*Math.PI;
        rotationY = (rad - 0.5*Math.PI) * targetSign;
    }

    /*Function to move towards the target*/
    public void moveTo(Node targetNode) {
        if (targetNode.getX() - x < 0) {
            if (targetNode.getX() < x) {
                x -= linearSpeed;
            }
        } else
            if (targetNode.getX() > x) {
                x += linearSpeed;
            }

        if (targetNode.getZ() - z < 0) {
            if (targetNode.getZ() < z) {
                z -= linearSpeed;
            }
        } else
            if (targetNode.getZ() > z) {
                z += linearSpeed;
            }

        if (Math.abs(x - targetNode.getX()) < 2 * linearSpeed) {
            x = targetNode.getX();
        }


        if (Math.abs(z - targetNode.getZ()) < 2 * linearSpeed) {
            z = targetNode.getZ();
        }

        if ((x == targetNode.getX() && z == targetNode.getZ())) {
            if (!route.isEmpty()) {
                target = route.pop();
                breadcrumbs.push(target);
                setState("moving");
            } else {
                if (state != "returning") {
                    rotateTo(targetNode);
                    targetTime = System.currentTimeMillis() + 1000;
                }
                if (!breadcrumbs.isEmpty() && target.getName() != "0, 0") {
                    target = breadcrumbs.pop();
                    setState("returning");
                } else {
                    setState("await");
                }
            }
        }
    }

    private void setState(String state) {
        this.state = state;
    }

    private void setCarrying(boolean carrying) {
        this.carrying = carrying;
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
        return Robot.class.getSimpleName().toLowerCase();
    }

    @Override
    public double getX() {
        return this.x;
    }

    @Override
    public String getCondition() {
        if (this.carrying) return "carrying";
        else return null;
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

    public String getState() {
        return state;
    }
}