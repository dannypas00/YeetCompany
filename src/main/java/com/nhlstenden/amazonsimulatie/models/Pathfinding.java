package com.nhlstenden.amazonsimulatie.models;

import java.util.*;

public class Pathfinding {

    private int g;                              //movement cost to move to next square
    private int h;                              //estimated movement cost to final destination.

    private static int rows = 11;
    private static int cols = 13;
    private int countNodes = 0;
    private Boolean passable;

    private ArrayList<Node> allNodes = new ArrayList<Node>();
    private Integer[] unpassable = {1,2,3,5,6,7,8,10,11,12,15,15,17,19,20,21,22,24,25,26,43,44,45,47,48,49,50,52,53,54,57,58,59,61,62,63,64,66,67,68,85,86,87,89,90,91,92,94,95,96,99,100,101,103,104,105,106,108,109,110,127,128,129,131,132,133,134,136,137,138,141,142,143,145,146,147,148,150,151,152};
    private Node[] openList;
    private Node[] closedList;


    public Pathfinding(){
        fillArrayAllNodes();
        fillLists();
    }

    private void fillArrayAllNodes(){
        for (int z = 0; z < rows; z++){
            for (int x = 0; x < cols; x++){
                for (Integer i : unpassable)
                {
                    if(i == countNodes) {
                        passable = false;
                    }
                    else
                    {
                        passable = true;
                    }
                }
                Node node = new Node(x, z, passable, 0);
                allNodes.add(node);
            }
        }
    }

    private void fillLists(){
        Integer i = 0, j = 0;
        for(Node node : allNodes){
            if(node.getPassable()){
                openList[i] = node;
                i++;
            }
            else{
                closedList[j] = node;
                j++;
            }
        }
    }

    public Stack<Node> getPathToo(Node currentNode, Node targetNode){
        Stack<Node> route = new Stack<Node>();


        //implement path generating algorithm between current x,y and target x,y

        return route;
    }
}
