package main.entities.Animal;

import main.entities.Plant.Plant;
import main.entities.Water.Water;

public class Omnivores extends Animal {
    //Magic numbers fix
    private static final double ATTACK_PROB = 60.0;
    private static final double MAX_SCORE = 100.0;
    private static final double DIVISOR = 10.0;
    private static final double ORGANIC_MATTER_HIGH = 0.8;
    private static final double ORGANIC_MATTER_MEDIUM = 0.5;
    private static final double ORGANIC_MATTER_LOW = 0.0;
    private static final double ZERO_MASS = 0.0;

    public Omnivores(final String name, final double mass) {
        //Superclass's constructor (Animal)
        super(name, mass);
    }

    /**
     * Returns the probability of attack based on the type of animal
     * @return attackProbability
     */
    @Override
    protected double attackProbability() {
        //Given formulas
        double attackProbability =  ATTACK_PROB;
        attackProbability = (MAX_SCORE - attackProbability) / DIVISOR;
        return attackProbability;
    }

    /**
     * Calculate organicMatter according to how many entities the animal consumes
     */
    @Override
    public void animalEats(final Animal prey, final Plant plant, final Water water) {
        int entitiesEaten = 0;
        //Omnivore animals do not eat prey
        if (prey == null) {
            if (plant != null && plant.getMass() != ZERO_MASS && plant.getIsScanned()) {
                setMass(getMass() + plant.getMass());
                //plant dies
                plant.setMass(ZERO_MASS);
                plant.setIsDead(true);
                entitiesEaten += 1;
            }
            if (water != null && water.getMass() != ZERO_MASS) {
                //calculating water to drink
                double waterToDrink = Math.min(getMass() * getIntakeRate(), water.getMass());
                water.setMass(water.getMass() - waterToDrink);
                setMass(getMass() + waterToDrink);
                entitiesEaten += 1;
            }
        }
        //If it ate plant AND drunk water
        if (entitiesEaten == 2) {
            setOrganicMetter(ORGANIC_MATTER_HIGH);
            setStatus("Well-Fed");
        } else {
            //If ot ate plant / water
            if (entitiesEaten == 1) {
                setOrganicMetter(ORGANIC_MATTER_MEDIUM);
                setStatus("Well-Fed");
            } else {
                setOrganicMetter(ORGANIC_MATTER_LOW);
                setStatus("Hungry");
            }
        }
    }
}
