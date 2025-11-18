package main.entities;
import main.entities.Animal;
import main.entities.Plant;
import main.entities.Soil;
import main.entities.Air;
import main.entities.Water;
import java.util.*;

public class GymnospermsPlants extends Plant{
    public GymnospermsPlants(String name, double mass) {
        super(name, mass);
    }

    @Override
    protected double hangingProbability() {
        return 60 / 100.0;
    }

    @Override
    protected double oxygenFromPlant() {
        return 0.0;
    }

    @Override
    protected double oxygenLevel() {
        return getMaturityOxygen() + oxygenFromPlant();
    }
}
