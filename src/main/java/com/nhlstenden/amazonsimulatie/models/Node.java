package com.nhlstenden.amazonsimulatie.models;


import java.util.LinkedList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class Node {
    private int x, z;
    private String name;
    private LinkedList<Node> shortestPath = new LinkedList<>();
    private Integer distance = Integer.MAX_VALUE;
    Map<Node, Integer> adjacentNodes = new HashMap<>();


    public Node(String name) {
        this.name = name;
    }

    /**
     * Adds a two-way traversible path between this node and the given node.
     * @param destination The node with which to make a connection.
     * @param distance The distance between this node and the target node.
     */
    public void addDestination(Node destination, int distance) {
        adjacentNodes.put(destination, distance);
        destination.adjacentNodes.put(this, distance);
    }

    /**
     * Adds this node to the returned path for pathfinding purposes.
     * @return A linkedlist of nodes in a path.
     */
    public LinkedList<Node> getRealShortestPath() {
        LinkedList<Node> realShortestPath = new LinkedList<>();
        realShortestPath.add(this);
        realShortestPath.addAll(shortestPath);
        return realShortestPath;
    }

    public Integer getDistance() {
        return distance;
    }
    public LinkedList<Node> getShortestPath() {
        return shortestPath;
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
