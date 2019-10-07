package com.nhlstenden.amazonsimulatie.models;

import java.beans.PropertyChangeListener;
import java.util.List;

/*
 * Deze interface beschrijft het model van de applicatie. Het is een interface
 * omdat dit onderdeel alleen maar beschrijft wat een model hoort te zijn.
 * Hiermee wordt bedoeld wat een model moet kunnen, of wat generiek is voor
 * alle models.
 */
public interface Model {
    /*
     * Models kunnen commando's geven aan de view, bijvoorbeeld om te updaten.
     * Hier is zo'n commando weergegeven als statische waarde, omdat deze gelijk
     * is voor alle models.
     */
    static final String UPDATE_COMMAND = "object_update";

    /*
     * Alle models moeten kunnen updaten en een observer kunnen toevoegen.
     * Wanneer een class dit implementeerd, is het binnen deze software
     * genoeg om een model te zijn.
     */
    void update();
    void addObserver(PropertyChangeListener pcl);
    List<Object3D> getWorldObjectsAsList();
}