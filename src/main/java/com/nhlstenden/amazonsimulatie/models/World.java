package com.nhlstenden.amazonsimulatie.models;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

/*
 * Deze class is een versie van het model van de simulatie. In dit geval is het
 * de 3D wereld die we willen modelleren (magazijn). De zogenaamde domain-logic,
 * de logica die van toepassing is op het domein dat de applicatie modelleerd, staat
 * in het model. Dit betekent dus de logica die het magazijn simuleert.
 */
public class World implements Model {

    private List<Object3D> worldObjects;
    private List<Robot> robots = new ArrayList<>();
    private List<Minecart> minecarts = new ArrayList<>();

    /*
     * Dit onderdeel is nodig om veranderingen in het model te kunnen doorgeven aan de controller.
     * Het systeem werkt al as-is, dus dit hoeft niet aangepast te worden.
     */
    PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    /**
     * De wereld maakt een lege lijst voor worldObjects aan. Daarin worden meerdere robots en een minecart gestopt.
     */
    public World() {
        this.worldObjects = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Robot robot = new Robot();
            this.worldObjects.add(robot);
            this.robots.add(robot);
        }
        for (int i = 0; i < 1; i++) {
            Minecart minecart = new Minecart();
            this.worldObjects.add(minecart);
            this.minecarts.add(minecart);
        }
    }

    /*
     * Deze methode wordt gebruikt om de wereld te updaten. Wanneer deze methode wordt aangeroepen,
     * wordt op elk object in de wereld de methode update aangeroepen. Wanneer deze true teruggeeft
     * betekent dit dat het onderdeel daadwerkelijk geupdate is (er is iets veranderd, zoals een positie).
     * Als dit zo is moet dit worden geupdate, en wordt er via het pcs systeem een notificatie gestuurd
     * naar de controller die luisterd. Wanneer de updatemethode van het onderdeel false teruggeeft,
     * is het onderdeel niet veranderd en hoeft er dus ook geen signaal naar de controller verstuurd
     * te worden.
     */
    @Override
    public void update() {
        for (Object3D object : this.worldObjects) {
            if (object instanceof Updatable) {
                if (((Updatable) object).update()) {
                    pcs.firePropertyChange(Model.UPDATE_COMMAND, null, new ProxyObject3D(object));
                }
            }
        }
    }

    /*
     * Standaardfunctionaliteit. Hoeft niet gewijzigd te worden.
     */
    @Override
    public void addObserver(PropertyChangeListener pcl) {
        pcs.addPropertyChangeListener(pcl);
    }

    /*
     * Deze methode geeft een lijst terug van alle objecten in de wereld. De lijst is echter wel
     * van ProxyObject3D objecten, voor de veiligheid. Zo kan de informatie wel worden gedeeld, maar
     * kan er niks aangepast worden.
     */
    @Override
    public List<Object3D> getWorldObjectsAsList() {
        ArrayList<Object3D> returnList = new ArrayList<>();

        for(Object3D object : this.worldObjects)
            returnList.add(new ProxyObject3D(object));

        return returnList;
    }

    public List<Robot> getRobotsAsList() {
        return robots;
    }

    public List<Minecart> getMinecartAsList() {
        return minecarts;
    }
}