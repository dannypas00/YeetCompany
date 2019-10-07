package com.nhlstenden.amazonsimulatie.controllers;

import java.beans.PropertyChangeEvent;

import com.nhlstenden.amazonsimulatie.base.Command;
import com.nhlstenden.amazonsimulatie.models.Model;
import com.nhlstenden.amazonsimulatie.models.Object3D;
import com.nhlstenden.amazonsimulatie.views.View;

/*
 * Dit is de controller class die de simulatie beheerd. Deze class erft van
 * een generieke class Controller. Hierdoor krijgt SimulationController gratis
 * functionaliteit mee voor het managen van views en een model.
 */
public class SimulationController extends Controller {

    public SimulationController(Model model) {
        super(model); //Met dit onderdeel roep je de constructor aan van de superclass (Controller)
    }

    /*
     * Deze methode wordt aangeroepen wanneer de controller wordt gestart. De methode start een infinite
     * while-loop op in de thread van de controller. Normaal loopt een applicatie vast in een infinite
     * loop, maar omdat de controller een eigen thread heeft loopt deze loop eigenlijk naast de rest
     * van het programma. Elke keer wordt een Thread.sleep() gedaan met 100 als parameter. Dat betekent
     * 100 miliseconden rust, en daarna gaat de loop verder. Dit betekent dat ongeveer 10 keer per seconden
     * de wereld wordt geupdate. Dit is dus in feite 10 frames per seconde.
     */
    @Override
    public void run() {
        while (true) {
            this.getModel().update();

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onViewAdded(final View view) {
        final Controller t = this;

        /*
         * Hier wordt een interface (Command) gebruikt om een nieuw object
         * te maken. Dit kan binnen Java en heet een anonymous inner class.
         * Op deze manier hoef je niet steeds een nieuwe class aan te maken
         * voor verschillende commando's. Zeker omdat je deze code maar één
         * keer nodig hebt.
         */
        view.onViewClose(new Command(){
        
            @Override
            public void execute() {
                t.removeView(view);
            }
        });

        /*
         * Dit stukje code zorgt ervoor dat wanneer een nieuwe view verbinding maakt, deze view één
         * keer alle objecten krijgt toegestuurd, ook als deze objecten niet updaten. Zo voorkom je
         * dat de view alleen objecten ziet die worden geupdate (bijvoorbeeld bewegen).
         */
        for (Object3D object : this.getModel().getWorldObjectsAsList()) {
            view.update(Model.UPDATE_COMMAND, object);
        }
    }

    /*
     * Deze methode wordt aangeroepen wanneer er een update van het model binnenkomt. Zo'n "event"
     * heeft een naam en een waarde. Die worden hieronder gebruikt om een updatesignaal te sturen
     * naar de view.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        for(int i = 0; i < this.getViews().size(); i++) {
            View currentView = this.getViews().get(i);

            if(currentView != null) {
                currentView.update(evt.getPropertyName(), (Object3D)evt.getNewValue());
            }
        }
    }

}