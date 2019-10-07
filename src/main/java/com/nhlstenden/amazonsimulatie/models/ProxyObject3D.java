package com.nhlstenden.amazonsimulatie.models;

/*
 * Deze class wordt gebruikt om informatie van het model aan de view te kunnen geven. Dit
 * gaat via het zogenaamde proxy design pattern. Een proxy pattern is een manier om alleen
 * de getters van een object open te stellen voor andere objecten, maar de setters niet.
 * Hieronder zie je dat ook gebeuren. De class ProxyObject3D implementeerd wel de Object3D
 * interface, maar verwijsd door naar een Object3D dat hij binnen in zich houdt. Dit
 * Object3D, met de naam object (zie code hier direct onder), kan in principe van alles zijn.
 * Dat object kan ook setters hebben of een updatemethode, etc. Deze methoden mag de view niet
 * aanroepen, omdat de view dan direct het model veranderd. Dat is niet toegestaan binnen onze
 * implementatie van MVC. Op deze manier beschermen we daartegen, omdat de view alleen maar ProxyObject3D
 * objecten krijgt. Hiermee garanderen we dat de view dus alleen objecten krijgt die alleen maar getters
 * hebben. Zouden we dit niet doen, en bijvoorbeeld een Robot object aan de view geven, dan zouden er
 * mogelijkheden kunnen zijn zodat de view toch bij de updatemethode van de robot kan komen. Deze mag
 * alleen de World class aanroepen, dus dat zou onveilige software betekenen.
 */
public class ProxyObject3D implements Object3D {
    private Object3D object;

    public ProxyObject3D(Object3D object) {
        this.object = object;
    }

    @Override
    public String getUUID() {
        return this.object.getUUID();
    }

    @Override
    public String getType() {
        return this.object.getType();
    }

    @Override
    public double getX() {
        return this.object.getX();
    }

    @Override
    public double getY() {
        return this.object.getY();
    }

    @Override
    public double getZ() {
        return this.object.getZ();
    }

    @Override
    public double getRotationX() {
        return this.object.getRotationX();
    }

    @Override
    public double getRotationY() {
        return this.object.getRotationY();
    }

    @Override
    public double getRotationZ() {
        return this.object.getRotationZ();
    }


}