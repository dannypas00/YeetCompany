package com.nhlstenden.amazonsimulatie.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

public class Pathfinding implements Model {
    Graph graph;
    private String[] items = new String[] {"dirt", "glowstone", "tnt", "log", "pig"};
    private HashMap<String, Node> itemMap = new HashMap<>();
    Node[][] nodes = new Node[7][5];
    public Pathfinding() {
        for (int i = 1; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                nodes[j][i] = new Node(j + ", " + i);
                Node n = nodes[j][i];

                switch (j) {
                    case 0:
                        if (i != 1)
                            addDestinationLeft(j, i);
                        break;
                    case 6:
                        if (i != 1) {
                            addDestinationLeft(j, i);
                            addDestinationAbove(j, i);
                        }
                    default:
                        addDestinationAbove(j, i);
                }
                n.setX(15 + (15 * j));
                n.setZ(15 * i);
            }
        }

        for (int i = 0; i < 3; i++) {
            nodes[i][0] = new Node(i + ", 0");
            nodes[i][0].addDestination(nodes[i*3][1], 15);
        }

        for (int i = 2; i < 4; i++){
            addDestinationAbove(i, 2);
            addDestinationAbove(i, 4);
        }

        //Add nodes to graph
        Graph graph = new Graph();

        for (Node[] a : nodes) {
            for (Node n : a) {
                if (n != null) {
                    graph.addNode(n);
                }
            }
        }

        for (int i = 0; i < items.length; i++) {
            itemMap.put(items[i], nodes[(int) Math.ceil(Math.random()*4)][i]);
        }

        System.out.print("Path to dirt ");
        for (Node n : getPathToItem("dirt")) {
            System.out.print("-> | " + n.getName() + " | ");
        }
        System.out.print("\r\n");
    }

    private void addDestinationLeft(int row, int col) {
        nodes[row][col].addDestination(nodes[row][col-1], 15);
    }

    private void addDestinationAbove(int row, int col) {
        nodes[row][col].addDestination(nodes[row - 1][col], 15);
    }

    public Stack<Node> getPath(Node node) {
        Dijkstra.calculateShortestPathFromSource(node);
        Stack<Node> path = node.getShortestPath();
        return path;
    }

    public Stack<Node> getPathToItem(String item) {
        System.out.println("Itemmap: " + itemMap.toString());
        Node target = nodes[4][4]; //itemMap.get(item);
        //System.out.println("Map for item " + item + ": " + target.getName());
        return getPath(target);
    }

    @Override
    public void update() {
        // Enqueue new set of movements for robot

    }

    @Override
    public void addObserver(PropertyChangeListener pcl) {

    }

    @Override
    public List<Object3D> getWorldObjectsAsList() {
        return null;
    }
}
