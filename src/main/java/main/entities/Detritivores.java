package main.entities;

public class Detritivores extends Animal{
    public Detritivores(String name, double mass) {
        super(name, mass);
    }

    @Override
    public double attackProbability() {
        double attackProbability =  90.0;
        attackProbability = (100 - attackProbability) / 10.0;
        return attackProbability;
    }

    @Override
    protected double animalEats(Animal prey, Plant plant, Water water) {
        int entitiesEaten = 0;
        double organicMatter = 0.0;
        if (prey == null) {
            if (plant != null) {
                mass += plant.mass;
                plant.setIsDead(true);
                setStatus("Well-Fed");
                entitiesEaten += 1;
            }
            if (water != null) {
                double waterToDrink = Math.min(mass * getIntakeRate(), water.mass);
                water.mass -= waterToDrink;
                mass += waterToDrink;
                setStatus("Well-Fed");
                entitiesEaten += 1;
            }
        }
        if (entitiesEaten == 2) {
            organicMatter = 0.8;
        } else {
            if (entitiesEaten == 1) {
                organicMatter = 0.5;
            } else {
                organicMatter = 0.0;
                setStatus("Hungry");
            }
        }
        return organicMatter;
    }
}
