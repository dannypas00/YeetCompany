package com.nhlstenden.amazonsimulatie.models;

import java.util.*;

/*
 * Deze class stelt een robot voor. Hij impelementeerd de class Object3D, omdat het ook een
 * 3D object is. Ook implementeerd deze class de interface Updatable. Dit is omdat
 * een robot geupdate kan worden binnen de 3D wereld om zich zo voort te bewegen.
 */
class Robot implements Object3D, Updatable {
    private UUID uuid;

    private double x = 16, y, z = 16, rotationX, rotationY, rotationZ, linearSpeed = 0.1, rotationSpeed = Math.PI/20, rad, deltaX, deltaZ;
    private boolean rotating = true, moving = true;

    private Node target, root, A0, A1, A2, A3, A4, A5, A6;

    private Stack<Node> nodeStack;

    public Robot() {
        this.uuid = UUID.randomUUID();

        root= new Node(" startingNode");
        root.setX((int)Math.round(x));
        root.setZ((int)Math.round(z));

        A0 = new Node(" A0");
        A0.setX(-16);
        A0.setZ(16);

        A1 = new Node(" A1");
        A1.setX(-16);
        A1.setZ(-16);

        A2 = new Node(" A2");
        A2.setX(16);
        A2.setZ(-16);

        A3 = new Node(" A3");
        A3.setX(16);
        A3.setZ(16);

        A4 = new Node(" A16");
        A4.setX(-16);
        A4.setZ(16);

        A5 = new Node(" A5");
        A5.setX(-16);
        A5.setZ(-16);

        A6 = new Node(" A6");
        A6.setX(16);
        A6.setZ(-16);

        nodeStack = new Stack<Node>();
        nodeStack.push(A6);
        nodeStack.push(A5);
        nodeStack.push(A4);
        nodeStack.push(A3);
        nodeStack.push(A2);
        nodeStack.push(A1);
        nodeStack.push(A0);

        nodeStack.push(A0);
        nodeStack.push(A1);
        nodeStack.push(A2);
        nodeStack.push(A3);
        nodeStack.push(A4);
        nodeStack.push(A5);
        nodeStack.push(A6);


        target = nodeStack.pop();
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
        System.out.println("x: " + x + " z: " + z + " rotationY :" + rotationY + " rad: " + rad + " deltaX: " + deltaX + " deltaZ: " + deltaZ);
        MoveTo(target);
        RotateTo(target);




        return true;
    }

    /*Function to rotate the robot towards the target*/
    public void RotateTo(Node targetNode) {
        deltaX = x - targetNode.getX();
        deltaZ = z - targetNode.getZ();
        rad = Math.atan2(deltaZ, deltaX);
        if(rad < 0){
            rad += 2*Math.PI;
        }
        rotationY = rad + 0.5*Math.PI;
    }

    /*Function to move towards the target*/
    public void MoveTo(Node targetNode) {
        if (targetNode.getX() - x < 0) {
            if (targetNode.getX() < x) {
                x -= linearSpeed;
            }
        } else {
            if (targetNode.getX() > x) {
                x += linearSpeed;
            }
        }
        if (targetNode.getZ() - z < 0) {
            if (targetNode.getZ() < z) {
                z -= linearSpeed;
            }
        } else {
            if (targetNode.getZ() > z) {
                z += linearSpeed;
            }
        }

        if (Math.abs(x - targetNode.getX()) < 2 * linearSpeed) {
            x = targetNode.getX();
        }


        if (Math.abs(z - targetNode.getZ()) < 2 * linearSpeed) {
            z = targetNode.getZ();
        }

        if ((x == targetNode.getX() && z == targetNode.getZ())) {
            if (!nodeStack.isEmpty()) {
                target = nodeStack.pop();
            }
        }
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
}