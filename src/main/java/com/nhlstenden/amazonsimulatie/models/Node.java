package com.nhlstenden.amazonsimulatie.models;

public class Node {
    private int objectsStored;
    private Boolean passable;
    private int x;
    private int z;

    public Node(int x, int z, Boolean passable, int objectsStored){
        this.x = x;
        this.z = z;
        this.passable = passable;
        this.objectsStored = objectsStored;
    }

    public Boolean getPassable()
    {
        return passable;
    }
}
