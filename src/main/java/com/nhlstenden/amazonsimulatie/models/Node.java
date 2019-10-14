package com.nhlstenden.amazonsimulatie.models;

public class Node {
    private String nodeName;
    private int x, z;

    public Node(String nodeName) {
        this.nodeName = nodeName;
    }

    public double getX(){
        return x;
    }

    public double getZ(){
        return z;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setZ(int z) {
        this.z = z;
    }
}
