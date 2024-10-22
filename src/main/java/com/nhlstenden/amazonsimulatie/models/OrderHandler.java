package com.nhlstenden.amazonsimulatie.models;

import javax.xml.crypto.dsig.keyinfo.KeyValue;
import java.beans.PropertyChangeListener;
import java.util.*;

/**
 * This class handles all the orders from the minecarts to the robots
 */
public class OrderHandler implements Model {
    private World world;
    private List<Robot> robots;
    private List<Minecart> minecarts;
    private Pathfinding pathFinder = new Pathfinding();
    /** queues to fill with orders and request to handle by the robots */
    private Queue<String> order = new LinkedList<>(), request = new LinkedList<>();;
    /** A list with possible items to order/request */
    private final List<String> validOrders = new ArrayList<>();
    /** Create 10 racks to keep track of inventory */
    private Rack[] racks = new Rack[10];
    /** Hashmap to map items to racks */
    private HashMap<String, Rack> rackMap = new HashMap<>();
    /** Boolean to keep communicate with the robots when the minecart is on the loading dock */
    private boolean minecartOnDock = false;

    /**
     * When an orderHandler gets initiated fill the Racks with valid objects
     * @param world
     */
    public OrderHandler(Model world) {
        this.world = (World) world;
        validOrders.add("dirt");
        validOrders.add("glowstone");
        validOrders.add("tnt");
        validOrders.add("slime");
        validOrders.add("cobblestone");
        validOrders.add("log");
        validOrders.add("bricks");
        validOrders.add("skull");
        validOrders.add("iron");
        validOrders.add("diamond");
        Collections.shuffle(validOrders);
        fillRacks(validOrders);
    }

    /**
     * Everytime the world gets updated the Orderhandler checks if the orders and requests are empty. then orderhandler tells the minecart to drive back and fill it again with new orders and requests.
     * Orderhandler tells the robots the minecart is on the dock and ready to be loaded/unloaded.
     * Orderhandler also calls the Pathfinding class to calculate the path for the robots
     */
    @Override
    public void update() {
        robots = world.getRobotsAsList();
        minecarts = world.getMinecartAsList();
        for (Minecart m : minecarts) {
            if (order.isEmpty() && request.isEmpty() || order == null) {
                if (m.getInZ() == m.getZ()) {
                    m.setLocation("Out");
                }
                if (m.getOutZ() == m.getZ()) {
                    m.setLocation("OnStartingDock");
                    order = generateNewOrder();
                    request = generateNewOrder();
                }
                else {
                    m.setLocation("Out");
                }
                minecartOnDock = false;
            }
            else {
                if (m.getInZ() == m.getZ()){
                    m.setLocation("OnLoadingDock");
                    minecartOnDock = true;
                }
                else if (m.getOutZ() == m.getZ()) {
                    m.setLocation("In");
                    minecartOnDock = false;
                }
                else {
                    minecartOnDock = false;
                }
            }
        }
        for (Robot r : robots) {
            if (r.getState() == "await" && minecartOnDock == true) {
                Stack<Node> route = new Stack<>();
                if (!order.isEmpty()) {
                    String item = order.poll();
                    for (Node n : pathFinder.getPathToItem(item)) {
                        route.push(n);
                        System.out.println("Added node " + n.getName() + " to route");
                    }
                    if (r.goRoute(route, "put")) {
                        rackMap.get(item).pullItem();
                    }
                }
                else if (!request.isEmpty()) {
                    String item = request.poll();
                    for (Node n : pathFinder.getPathToItem(item)) {
                        route.push(n);
                    }
                    if (r.goRoute(route, "pull")) {
                        rackMap.get(item).putItem();
                    }
                }
            }
        }
    }

    /**
     * Fills racks with random amount of a given item
     * @param items
     */
    private void fillRacks(List<String> items) {
        for (int i = 0; i < items.size(); i++) {
            String item = items.get(i);
            int amount = (int) Math.ceil(Math.random() * 6);
            Rack rack = racks[i] = new Rack(item, amount);
            rackMap.put(item, rack);
            System.out.println("Filled rack " + i + " with item " + item);
        }
    }

    /**
     * generate 1-10 orders to fill requests and orders
     * @return
     */
    private Queue<String> generateNewOrder() {
        int size = (int) Math.ceil(Math.random() * 10);
        Queue<String> order = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            order.add(validOrders.get(i));
        }
        return order;
    }

    @Override
    public void addObserver(PropertyChangeListener pcl) {

    }

    @Override
    public List<Object3D> getWorldObjectsAsList() {
        return null;
    }
}
