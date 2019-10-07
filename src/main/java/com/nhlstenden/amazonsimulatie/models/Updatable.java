package com.nhlstenden.amazonsimulatie.models;

/*
 * Deze interface beschrijft wat een onderdeel van het model moet kunnen om deze
 * te kunnen updaten. In de lijst met objecten in de wereld (zie class World) zitten
 * objecten die geupdate kunnen worden. Er wordt gecontroleerd of een object deze
 * interface implementeerd. Als dat zo is, wordt het object geupdate wanneer de
 * wereld update.
 */
interface Updatable {
    boolean update();
}