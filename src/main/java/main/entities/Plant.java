package main.entities;
import main.entities.Water;
import main.entities.Animal;
import main.entities.Air;
import main.entities.Soil;
import fileio.PlantInput;
import java.util.*;

public abstract class Plant {
    protected String name;
    protected double mass;
    protected String type;
    private String maturityLevel;
    private double maturityOxygen;
    private double growthLevel;
    private boolean isDead;

    public Plant(String name, double mass) {
        this.name = name;
        this.mass = mass;
        this.type = null;
        this.maturityLevel = "Young";
        this.maturityOxygen = 0.0;
        this.growthLevel = 0.0;
        this.isDead = false;
    }

    public String getMaturityLevel() {
        return this.maturityLevel;
    }

    public void setMaturityLevel(String maturityLevel) {
        this.maturityLevel = maturityLevel;
    }

    public double getMaturityOxygen() {
        return this.maturityOxygen;
    }

    public void setMaturityOxygen(double maturityOxygen) {
        this.maturityOxygen = maturityOxygen;
    }

    public boolean isDead() {
        return isDead;
    }

    public double getGrowthLevel() {
        return this.growthLevel;
    }

    public void setGrowthLevel(double growthLevel) {
        this.growthLevel = growthLevel;
    }

    public boolean getIsDead() {
        return this.isDead;
    }

    public void setIsDead(boolean isDead) {
        this.isDead = isDead;
    }

    protected abstract double hangingProbability();
    protected abstract double oxygenFromPlant();
    protected abstract double oxygenLevel();

    protected void setMaturityRate() {
        if (maturityLevel.equals("Young") == true) {
            maturityOxygen = 0.2;
        } else {
            if (maturityLevel.equals("Mature") == true) {
                maturityOxygen = 0.7;
            } else {
                if (maturityLevel.equals("Old") == true) {
                    maturityOxygen = 0.4;
                } else {
                    if (maturityLevel.equals("Dead") == true) {
                        maturityOxygen = 0.0;
                    }
                }
            }
        }
    }

    protected void growMaturity() {
        if (maturityLevel.equals("Young") == true) {
            maturityLevel = "Mature";
            setMaturityRate();
        } else {
            if (maturityLevel.equals("Mature") == true) {
                maturityLevel = "Old";
                setMaturityRate();
            } else {
                if (maturityLevel.equals("Old") == true) {
                    maturityLevel = "Dead";
                    setIsDead(true);
                    setMaturityRate();
                }
            }
        }
    }

    protected void grow(double increaseLevel) {
        if (isDead == false) {
            growthLevel += increaseLevel;
            if (growthLevel > 1.0) {
                growMaturity();
                growthLevel = 0.0;
            }
        }
    }
}
