package main.entities;
import main.entities.Animal;
import main.entities.Plant;
import main.entities.Soil;
import main.entities.Air;
import main.entities.Water;

public class Mosses extends Plant{
    public Mosses(String name, double mass) {
        super(name, mass);
    }

    @Override
    protected double hangingProbability() {
        return 40 / 100.0;
    }

    @Override
    protected double oxygenFromPlant() {
        return 0.8;
    }

    @Override
    protected double oxygenLevel() {
        return getMaturityOxygen() + oxygenFromPlant();
    }
}
