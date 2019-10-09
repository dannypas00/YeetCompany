package com.nhlstenden.amazonsimulatie.models;

public class Node {
    public String data;
    public Node left, right;

    public Node(String item) {
        data = item;
        left = right = null;
    }
}
