package com.nhlstenden.amazonsimulatie.views;

import com.nhlstenden.amazonsimulatie.base.Command;
import com.nhlstenden.amazonsimulatie.models.Object3D;

/*
 * Deze interface is de beschrijving van een view binnen het systeem.
 * Ze de andere classes voor meer uitleg.
 */
public interface View {
    void update(String event, Object3D data);
    void onViewClose(Command command);
}