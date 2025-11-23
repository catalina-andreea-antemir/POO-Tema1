package main.entities;
import main.entities.Animal;
import main.entities.Plant;
import main.entities.Water;
import main.entities.Soil;
import main.entities.Air;

public class Herbivores extends Animal{
    public Herbivores(String name, double mass) {
        //apeleaza contructorul clasei parinte
        super(name, mass);
    }

    //returneaza probabilitatea de atac pe baza tipului de animal
    @Override
    protected double attackProbability() {
        double attackProbability =  85.0;
        attackProbability = (100 - attackProbability) / 10.0;
        return attackProbability;
    }

    //returneaza organicMatter in functie de cate entitati consuma animalul
    @Override
    protected double animalEats(Animal prey, Plant plant, Water water) {
        int entitiesEaten = 0;
        double organicMatter;
        if (prey == null) {
            if (plant != null && plant.getMass() != 0.0 && plant.getIsScanned()) {
                setMass(getMass() + plant.getMass()); //se adauga la masa
                //planta moare
                plant.setMass(0.0);
                plant.setIsDead(true);
                entitiesEaten += 1;
            }
            if (water != null && water.getMass() != 0.0) {
                double waterToDrink = Math.min(getMass() * getIntakeRate(), water.getMass()); //se calculeaza cata apa a baut
                water.setMass(water.getMass() - waterToDrink); //masa apei scade
                setMass(getMass() + waterToDrink); //se adauga la masa
                entitiesEaten += 1;
            }
        }
        //daca mananca planta si apa
        if (entitiesEaten == 2) {
            organicMatter = 0.8;
            setStatus("Well-Fed");
        } else {
            //daca mananca ori planta, ori apa
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
