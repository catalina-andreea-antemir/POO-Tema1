package main.entities;
import main.entities.Water;
import main.entities.Animal;
import main.entities.Air;
import main.entities.Soil;

public abstract class Plant {
    private String name;
    private double mass;
    public String type;
    private String maturityLevel;
    private double maturityOxygen;
    private double growthLevel;
    private boolean isScanned;
    private boolean isDead;

    public Plant(String name, double mass) {
        this.name = name;
        this.mass = mass;
        this.type = null;
        this.maturityLevel = "Young";
        this.maturityOxygen = 0.0;
        this.growthLevel = 0.0;
        this.isScanned = false;
        this.isDead = false;
    }

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public double getMass() {
        return this.mass;
    }
    public void setMass(double mass) {
        this.mass = mass;
    }

    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
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

    public boolean getIsScanned() {
        return this.isScanned;
    }
    public void setIsScanned(boolean isScanned) {
        this.isScanned = isScanned;
    }

    protected abstract double hangingProbability();
    protected abstract double oxygenFromPlant();
    protected abstract double oxygenLevel();

    public double getHangingProbability() {
        return hangingProbability();
    }

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

    public void interactionAir(Air air) {
        if (air != null && !getIsDead() && getIsScanned()) {
            air.setOxygenLevel(air.getOxygenLevel() + oxygenLevel());
        }
    }
}
