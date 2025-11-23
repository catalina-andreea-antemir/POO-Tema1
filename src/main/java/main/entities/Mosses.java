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

    //calculeaza probabilitatea de agatare in functie de tipul de planta
    @Override
    protected double hangingProbability() {
        return 40 / 100.0;
    }

    //returneaza nivelul de oxigen generat de fiecare tip de planta
    @Override
    protected double oxygenFromPlant() {
        return 0.8;
    }

    //insumeaza oxigenul generat de planta cu oxigenul generat in functie de nivelul de maturitate
    @Override
    protected double oxygenLevel() {
        return getMaturityOxygen() + oxygenFromPlant();
    }
}
