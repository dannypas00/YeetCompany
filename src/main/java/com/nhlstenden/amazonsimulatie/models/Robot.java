package com.nhlstenden.amazonsimulatie.models;

import java.util.*;

/*
 * Deze class stelt een robot voor. Hij impelementeerd de class Object3D, omdat het ook een
 * 3D object is. Ook implementeerd deze class de interface Updatable. Dit is omdat
 * een robot geupdate kan worden binnen de 3D wereld om zich zo voort te bewegen.
 */
class Robot implements Object3D, Updatable {
    private UUID uuid;

    private double x, y, z, rotationX, rotationY, rotationZ, linearSpeed = 0.1, rotationSpeed = 0.1;

    private Node target, root, A0, A1, A2;

    private Stack<Node> nodeStack;

    private Lerp lerp;

    HashMap<String, Double> temp;

    public Robot() {
        this.uuid = UUID.randomUUID();

        lerp = new Lerp();

        root= new Node(" startingNode");
        root.setX((int)Math.round(x));
        root.setZ((int)Math.round(z));

        A0 = new Node(" A0");
        A0.setX(0);
        A0.setZ(15);

        A1 = new Node(" A1");
        A1.setX(15);
        A1.setZ(15);

        A2 = new Node(" A2");
        A2.setX(15);
        A2.setZ(0);

        nodeStack = new Stack<Node>();
        nodeStack.push(A2);
        nodeStack.push(A1);
        nodeStack.push(A1);
        nodeStack.push(A0);

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
        //RotateTo(target);
        System.out.println("x: " + x + " z: " + z);
        MoveTo(target);
        return true;
    }

    /*Function to rotate the robot towards the target*/
    public void RotateTo(Node targetNode) {
         double deltaX = targetNode.getX()-x;
         double deltaZ = targetNode.getZ()-z;
         double rad = Math.atan(deltaZ/deltaX);
         if(rotationY < rad){
             rotationY += rotationSpeed;
         }
         if(rotationY > rad){
            rotationY -= rotationSpeed;
         }
    }

    /*Function to move towards the target*/
    public void MoveTo(Node targetNode){
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

        if (Math.abs(x - targetNode.getX()) < 2 * linearSpeed){
            x = targetNode.getX();
        }

        if (Math.abs(z - targetNode.getZ()) < 2 * linearSpeed){
            z = targetNode.getZ();
        }

        if((x == targetNode.getX() && z == targetNode.getZ()))
        {
            if(!nodeStack.isEmpty()){
                target = nodeStack.pop();
                //System.out.println("POPPED STACK, new target: " + target.getX() + ", " + target.getZ());
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