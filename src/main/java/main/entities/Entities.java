package main.entities;

public class Entities {
    private String name;
    private double mass;

    public Entities(final String name, final double mass) {
        this.name = name;
        this.mass = mass;
    }

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
