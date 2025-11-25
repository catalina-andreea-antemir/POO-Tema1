package main.entities;

public class Entities {
    private String name;
    private double mass;

    public Entities(String name, double mass) {
        this.name = name;
        this.mass = mass;
    }

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public double getMass() {
        return this.mass;
    }
    public void setMass(double mass) {
        this.mass = mass;
    }
}
