package com.nhlstenden.amazonsimulatie.models;

import java.util.*;

public class Dijkstra {
    /*
    * Code heavily inspired by Baeldung's dijkstra algorithm
    * https://www.baeldung.com/java-dijkstra
    */

    /**
     * Calculate the shortest path from the source for each other node.
     * @param source The node that serves as the root of the pathfinding graph.
     */
    public static void calculateShortestPathFromSource(Node source) {
        source.setDistance(0);
        Set<Node> settledNodes = new HashSet<>();
        Set<Node> unsettledNodes = new HashSet<>();
        unsettledNodes.add(source);

        while (unsettledNodes.size() != 0) {
            Node currentNode = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNode);
            for (Map.Entry< Node, Integer> adjacencyPair:
                    currentNode.getAdjacentNodes().entrySet()) {
                Node adjacentNode = adjacencyPair.getKey();
                Integer edgeWeight = adjacencyPair.getValue();
                if (!settledNodes.contains(adjacentNode)) {
                    CalculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
                    unsettledNodes.add(adjacentNode);
                }
            }
            settledNodes.add(currentNode);
        }
    }

    /**
     * This function returns the node with the shortest distance value.
     * @param unsettledNodes A set of nodes to check for their distance values.
     * @return The node with the lowest distance value.
     */
    private static Node getLowestDistanceNode(Set<Node> unsettledNodes) {
        Node lowestDistanceNode = null;
        int lowestDistance = Integer.MAX_VALUE;
        for (Node node: unsettledNodes) {
            int nodeDistance = node.getDistance();
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }

    /**
     * Calculate the shortest distance between a given node and the source node.
     * @param evaluationNode The new node of which to evaluate the shortest to the source.
     * @param edgeWeigh The distance between the evaluation node and the source.
     * @param sourceNode The source node as defined in the constructor method.
     */
    private static void CalculateMinimumDistance(Node evaluationNode, Integer edgeWeigh, Node sourceNode) {
        Integer sourceDistance = sourceNode.getDistance();
        if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
            evaluationNode.setDistance(sourceDistance + edgeWeigh);
            LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
            shortestPath.push(sourceNode);
            evaluationNode.setShortestPath(shortestPath);
        }
    }
}