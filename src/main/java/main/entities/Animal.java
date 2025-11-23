package main.entities;
import main.entities.Plant;
import main.entities.Water;
import main.entities.Air;
import main.entities.Soil;

public abstract class Animal {
    private String name;
    private double mass;
    private String type;
    private String status;
    private double intakeRate;
    private boolean isScanned;
    private boolean isDead;

    public Animal(String name, double mass) {
        this.name = name;
        this.mass = mass;
        this.type = null;
        this.status = "Hungry";
        this.intakeRate = 0.08;
        this.isScanned = false;
        this.isDead = false;
    }

    //metode getter si setter pentru campul privat name
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    //metode getter si setter pentru campul privat mass
    public double getMass() {
        return this.mass;
    }
    public void setMass(double mass) {
        this.mass = mass;
    }

    //metode getter si setter pentru campul privat type
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }

    //metode getter si setter pentru campul privat status
    public String getStatus() {
        return this.status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    //metode getter si setter pentru campul privat intakeRate
    public double getIntakeRate() {
        return this.intakeRate;
    }
    public void setIntakeRate(double intakeRate) {
        this.intakeRate = intakeRate;
    }

    //metode getter si setter pentru campul privat isScanned
    public boolean getIsScanned() {
        return this.isScanned;
    }
    public void setIsScanned(boolean isScanned) {
        this.isScanned = isScanned;
    }

    //metode getter si setter pentru campul privat isDead
    public boolean getIsDead() {
        return this.isDead;
    }
    public void setIsDead(boolean isDead) {
        this.isDead = isDead;
    }

    //verifica daca animalul poate produce ingrasamant
    public boolean canProduceFertilizer() {
        if (this.status.equals("Well-Fed")) {
            return true;
        }
        return false;
    }

    //returneaza probabilitatea de atac pe baza tipului de animal
    protected abstract double attackProbability();
    public double getAttackProbability() {
        return attackProbability();
    }

    //returneaza valoarea de organicMetter cu care animalul contribuie la sol
    protected abstract double animalEats(Animal prey, Plant plant, Water water);

    public void interactionSoil(Soil soil, Animal prey, Plant plant, Water water) {
        if (soil != null && getIsScanned() && canProduceFertilizer()) {
            soil.setOrganicMatter(soil.getOrganicMatter() + animalEats(prey, plant, water));
        }
    }
}
