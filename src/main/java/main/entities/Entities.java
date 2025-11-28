package main.entities;

public class Entities {
    private String name; //name of the entity
    private double mass; //mass of the entity

    public Entities(final String name, final double mass) {
        this.name = name;
        this.mass = mass;
    }

    //Getter and Setter methods for private class fields
    public final String getName() {
        return this.name;
    }
    public final void setName(final String name) {
        this.name = name;
    }
    public final double getMass() {
        return this.mass;
    }
    public final void setMass(final double mass) {
        this.mass = mass;
    }
}
