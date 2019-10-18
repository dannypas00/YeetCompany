package com.nhlstenden.amazonsimulatie.models;

import java.beans.PropertyChangeListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class OrderHandler implements Model {

    private List<Robot> robots;
    private Pathfinding pathFinder = new Pathfinding();
    private Queue<String> orders = new LinkedList<>();
    World world;
    private final String[] validOrders = new String[] {"dirt", "glowstone", "tnt", "log", "pig"};

    public OrderHandler(Model world) {
        this.world = (World) world;
        if (robots != null)
            for (Robot r : robots)
                orders.add(validOrders[(int) Math.floor(Math.random() * 5)]);
    }

    // TODO Add way of inputting orders

    @Override
    public void update() {
        robots = world.getRobotsAsList();
        for (Robot r : robots) {
            if (r.getState() == "await") {
                Stack<Node> route = new Stack<>();
                if (!orders.isEmpty()) {
                    for (Node n : pathFinder.getPathToItem(orders.poll())) {
                        route.push(n);
                        System.out.println("Added node " + n.getName() + " to route");
                    }
                    r.goRoute(route);
                }
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
