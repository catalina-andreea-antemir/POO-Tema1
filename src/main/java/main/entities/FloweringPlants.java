package main.entities;
import main.entities.Animal;
import main.entities.Plant;
import main.entities.Soil;
import main.entities.Air;
import main.entities.Water;
import java.util.*;

public class FloweringPlants extends Plant{
    public FloweringPlants(String name, double mass) {
        super(name, mass);
    }

    @Override
    protected double hangingProbability() {
        return 90 / 100.0;
    }

    @Override
    protected double oxygenFromPlant() {
        return 6.0;
    }

    @Override
    protected double oxygenLevel() {
        return getMaturityOxygen() + oxygenFromPlant();
    }
}
