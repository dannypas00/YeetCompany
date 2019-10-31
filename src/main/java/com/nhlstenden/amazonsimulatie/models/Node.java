package com.nhlstenden.amazonsimulatie.models;

import java.util.*;

public class Node {

    private int x, z;

    private String name;

    private LinkedList<Node> shortestPath = new LinkedList<>();

    private Integer distance = Integer.MAX_VALUE;

    Map<Node, Integer> adjacentNodes = new HashMap<>();

    public void addDestination(Node destination, int distance) {
        adjacentNodes.put(destination, distance);
        destination.adjacentNodes.put(this, distance);
    }

    public Node(String name) {
        this.name = name;
    }

    // getters and setters
    public Integer getDistance() {
        return distance;
    }
    public LinkedList<Node> getShortestPath() {
        return shortestPath;
    }

    public LinkedList<Node> getRealShortestPath() {
        LinkedList<Node> realShortestPath = new LinkedList<>();
        realShortestPath.add(this);
        realShortestPath.addAll(shortestPath);
        return realShortestPath;
    }

    public Map<Node, Integer> getAdjacentNodes() {
        return adjacentNodes;
    }
    public String getName() {
        return name;
    }
    public int getX() {
        return x;
    }
    public int getZ() {
        return z;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }
    public void setShortestPath(LinkedList<Node> shortestPath) {
        this.shortestPath = shortestPath;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setZ(int z) {
        this.z = z;
    }
}
