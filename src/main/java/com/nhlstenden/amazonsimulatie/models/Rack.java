package com.nhlstenden.amazonsimulatie.models;

public class Rack {
    private String item;
    private int amount;

    Rack(String item, int amount) {
        this.item = item;
        this.amount = amount;
    }

    public void pullItem() {
        if (this.amount >= 1) {
            this.amount--;
        }
        else
            System.out.println("Rack with " + item + " empty!");
    }

    public void putItem() {
        this.amount ++;
    }
}
