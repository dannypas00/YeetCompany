package com.nhlstenden.amazonsimulatie.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

public class Pathfinding implements Model {
    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private Graph graph = new Graph();
    private String[] items = new String[] {"dirt", "glowstone", "tnt", "log", "pig"};
    private HashMap<String, Node> itemMap = new HashMap<>();
    private Node[][] nodes = new Node[7][5];

    public Pathfinding() {
        setupGrid();
    }

    private void setupGrid() {
        for (int i = 1; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                nodes[j][i] = new Node(j + ", " + i);
                Node n = nodes[j][i];

                switch (j) {
                    case 0:
                        if (i != 1)
                            addDestinationLeft(j, i, 8);
                        break;
                    case 2:
                    case 4:
                        if (i != 1) {
                            addDestinationLeft(j, i, 8);
                            addDestinationAbove(j, i, 6);
                        }
                        break;
                    case 6:
                        if (i != 1) {
                            addDestinationLeft(j, i, 8);
                            addDestinationAbove(j, i, 6);
                        }
                        break;
                    default:
                        addDestinationAbove(j, i, 6);
                        break;
                }
                n.setX(8 + (8 * j));
                n.setZ((6 * i));
            }
        }

        for (int i = 2; i < 4; i++){
            addDestinationAbove(i, 2, 6);
            addDestinationAbove(i, 4, 6);
        }

        //Starting Point
        nodes[0][0] = new Node("0, 0");
        nodes[0][0].addDestination(nodes[0][1], 2);
        nodes[0][0].setX(0);
        nodes[0][0].setZ(0);

        for (int i = 2; i < 4; i++){
            addDestinationAbove(i, 2, 6);
            addDestinationAbove(i, 4, 6);
        }

        //Add nodes to graph
        graph = new Graph();

        for (Node[] a : nodes)
            for (Node n : a)
                if (n != null)
                    graph.addNode(n);

        for (int i = 0; i < items.length; i++) {
            int rand = i; //(int) Math.ceil(Math.random()*4);
            Node location = nodes[rand][i];
            System.out.println("Putting item " + items[i] + " down at location " + location.getName());
            itemMap.put(items[i], location);
        }

        System.out.print("\r\n");
    }

    private void addDestinationLeft(int row, int col, int distance) {
        nodes[row][col].addDestination(nodes[row][col-1], distance);
    }

    private void addDestinationAbove(int row, int col, int distance) {
        nodes[row][col].addDestination(nodes[row - 1][col], distance);
    }

    private List<Node> getPath(Node node) {
        Dijkstra.calculateShortestPathFromSource(nodes[0][0]);
        List<Node> path = node.getShortestPath();
        return path;
    }

    public List<Node> getPathToItem(String item) {
        Node target = itemMap.get(item);
        return getPath(target);
    }

    @Override
    public void update() {

    }

    @Override
    public void addObserver(PropertyChangeListener pcl) {
        pcs.addPropertyChangeListener(pcl);
    }

    @Override
    public List<Object3D> getWorldObjectsAsList() {
        return null;
    }
}
