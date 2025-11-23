package main.entities;
import main.entities.Animal;
import main.entities.Plant;
import main.entities.Water;
import main.entities.Soil;
import main.entities.Air;

public class Carnivores extends Animal{
    public Carnivores(String name, double mass) {
        super(name, mass);
    }

    @Override
    public double attackProbability() {
        double attackProbability =  30.0;
        attackProbability = (100 - attackProbability) / 10.0;
        return attackProbability;
    }

    @Override
    protected double animalEats(Animal prey, Plant plant, Water water) {
        int entitiesEaten = 0;
        double organicMatter;
        if (prey != null && prey.getMass() != 0.0) {
            setMass(getMass() + prey.getMass());
            prey.setMass(0.0);
            prey.setIsDead(true);
            entitiesEaten = 1;
        } else {
            if (plant != null && plant.getMass() != 0.0 && plant.getIsScanned()) {
                setMass(getMass() + plant.getMass());
                plant.setMass(0.0);
                plant.setIsDead(true);
                entitiesEaten += 1;
            }
            if (water != null && water.getMass() != 0.0) {
                double waterToDrink = Math.min(getMass() * getIntakeRate(), water.getMass());
                water.setMass(water.getMass() - waterToDrink);
                setMass(getMass() + waterToDrink);
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
