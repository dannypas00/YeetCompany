package com.nhlstenden.amazonsimulatie.models;

import javax.xml.crypto.dsig.keyinfo.KeyValue;
import java.beans.PropertyChangeListener;
import java.util.*;

public class OrderHandler implements Model {
    private World world;
    private List<Robot> robots;
    private List<Minecart> minecarts;
    private Pathfinding pathFinder = new Pathfinding();

    private Queue<String> order = new LinkedList<>();
    private Queue<String> request = new LinkedList<>();
    private final List<String> validOrders = new ArrayList<>();

    private Rack[] racks = new Rack[15];
    private HashMap<String, Rack> rackMap = new HashMap<>();

    private boolean minecartOnDock = false;

    public OrderHandler(Model world) {
        this.world = (World) world;
        validOrders.add("dirt");
        validOrders.add("glowstone");
        validOrders.add("tnt");
        validOrders.add("pig");
        validOrders.add("cobblestone");
        validOrders.add("log");
        validOrders.add("bricks");
        validOrders.add("skull");
        validOrders.add("iron");
        validOrders.add("diamond");
        validOrders.add("stoneBricks");
        validOrders.add("gold");
//        validOrders.add("emerald");
//        validOrders.add("slime");
//        validOrders.add("wool");
        Collections.shuffle(validOrders);
        fillRacks(validOrders);

        //order = generateNewOrder();
        //request = generateNewOrder();

    }

    @Override
    public void update() {
        robots = world.getRobotsAsList();
        minecarts = world.getMinecartAsList();
        for (Minecart m : minecarts) {
            if (order.isEmpty() || order == null) {
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

    private void fillRacks(List<String> items) {
        for (int i = 0; i < items.size(); i++) {
            String item = items.get(i);
            int amount = (int) Math.ceil(Math.random() * 6);
            Rack rack = racks[i] = new Rack(item, amount);
            rackMap.put(item, rack);
            System.out.println("Filled rack " + i + " with item " + item);
        }
    }

    private Queue<String> generateNewOrder() {
        int size = (int) Math.ceil(Math.random() * 5);
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
