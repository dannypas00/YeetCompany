package com.nhlstenden.amazonsimulatie.controllers;

import com.nhlstenden.amazonsimulatie.models.Model;
import com.nhlstenden.amazonsimulatie.views.View;

import java.beans.PropertyChangeEvent;
import java.util.Stack;

public class OrderController extends Controller {

    public OrderController(Model model) {
        super(model);
    }

    @Override
    protected void onViewAdded(View view) {

    }

    @Override
    public void run() {
        Stack<String> orders = new Stack<>();
        while (true) {
            this.getModel().update();

            try {   //Check for new order every 1 second
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This method gets called when a bound property is changed.
     *
     * @param evt A PropertyChangeEvent object describing the event source
     *            and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
