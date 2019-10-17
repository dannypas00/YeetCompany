package com.nhlstenden.amazonsimulatie.models;

import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class OrderHandler implements Model {

    private Robot[] robots = new Robot[] {};
    private Pathfinding pathFinder = new Pathfinding();
    private Stack<String> Orders = new Stack<>();
    private Queue<String> orders;
    private final String[] validOrders = new String[] {"dirt", "glowstone", "tnt", "log", "pig"};

    public OrderHandler() {  }

    // TODO Add way of inputting orders

    @Override
    public void update() {
        for (Robot r : robots) {
            if (r.getState() == "await") {
                Stack<Node> route = new Stack<>();
                for (Node n : pathFinder.getPathToItem(orders.remove()))
                    route.push(n);
                r.goRoute(route);
            }
        }
    }

    public void pushOrder (String order) {
        for (String o : validOrders) {
            if (o == order) {
                this.orders.add(order);
                return;
            }
        }
    }

    @Override
    public void addObserver(PropertyChangeListener pcl) {

    }

    @Override
    public List<Object3D> getWorldObjectsAsList() {
        return null;
    }
}
