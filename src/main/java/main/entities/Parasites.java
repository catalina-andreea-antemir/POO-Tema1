package main.entities;
import main.entities.Animal;
import main.entities.Plant;
import main.entities.Water;
import main.entities.Soil;
import main.entities.Air;
import java.util.*;

public class Parasites extends Animal{
    public Parasites(String name, double mass) {
        super(name, mass);
    }

    @Override
    public double attackProbability() {
        double attackProbability =  10.0;
        attackProbability = (100 - attackProbability) / 10.0;
        return attackProbability;
    }

    @Override
    protected double animalEats(Animal prey, Plant plant, Water water) {
        int entitiesEaten = 0;
        double organicMatter;
        if (prey != null) {
            mass += prey.mass;
            prey.setIsDead(true);
            entitiesEaten = 1;
        } else {
            if (plant != null) {
                mass += plant.mass;
                plant.setIsDead(true);
                entitiesEaten += 1;
            }
            if (water != null) {
                double waterToDrink = Math.min(mass * getIntakeRate(), water.mass);
                water.mass -= waterToDrink;
                mass += waterToDrink;
                entitiesEaten += 1;
            }
        }
        if (entitiesEaten == 2) {
            organicMatter = 0.8;
            setStatus("Well-Fed");
        } else {
            if (entitiesEaten == 1) {
                organicMatter = 0.5;
                setStatus("Well-Fed");
            } else {
                organicMatter = 0.0;
                setStatus("Hungry");
            }
        }
        return organicMatter;
    }
}
