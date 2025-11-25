package main.entities;
import main.entities.Water;
import main.entities.Animal;
import main.entities.Air;
import main.entities.Soil;

public abstract class Plant extends Entities {
    public String type;
    private String maturityLevel;
    private double maturityOxygen;
    private double growthLevel;
    private boolean isScanned;
    private boolean isDead;

    public Plant(String name, double mass) {
        super(name, mass); //se apeleaza constructorul parintelui
        this.type = null;
        this.maturityLevel = "Young";
        this.maturityOxygen = 0.2;
        this.growthLevel = 0.0;
        this.isScanned = false;
        this.isDead = false;
    }

    //metode getter si setter pt campul privat type
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }

    //metode getter si setter pt campul privat maturityLevel
    public String getMaturityLevel() {
        return this.maturityLevel;
    }
    public void setMaturityLevel(String maturityLevel) {
        this.maturityLevel = maturityLevel;
    }

    //metode getter si setter pt campul privat maturityOxygen
    public double getMaturityOxygen() {
        return this.maturityOxygen;
    }
    public void setMaturityOxygen(double maturityOxygen) {
        this.maturityOxygen = maturityOxygen;
    }

    //metode getter si setter pt campul privat growthLevel
    public double getGrowthLevel() {
        return this.growthLevel;
    }
    public void setGrowthLevel(double growthLevel) {
        this.growthLevel = growthLevel;
    }

    //metode getter si setter pt campul privat isDead
    public boolean getIsDead() {
        return this.isDead;
    }
    public void setIsDead(boolean isDead) {
        this.isDead = isDead;
    }

    //metode getter si setter pt campul privat isScanned
    public boolean getIsScanned() {
        return this.isScanned;
    }
    public void setIsScanned(boolean isScanned) {
        this.isScanned = isScanned;
    }

    //calculeaza probabilitatea de agatare in functie de tipul de planta
    protected abstract double hangingProbability();
    public double getHangingProbability() {
        return hangingProbability();
    }

    //returneaza nivelul de oxigen generat de fiecare tip de planta
    protected abstract double oxygenFromPlant();
    //insumeaza oxigenul generat de planta cu oxigenul generat in functie de nivelul de maturitate
    protected abstract double oxygenLevel();

    //seteaza nivelul de oxigen generat de planta la o anumita perioada din viata
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

    //updateaza nivelul de maturitate
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

    //planta creste cu growthLevel maxim 1.0
    protected void grow(double increaseLevel) {
        if (isDead == false) {
            growthLevel += increaseLevel;
            //se reseteaza daca limita a fost depasita
            if (growthLevel >= 1.0) {
                growMaturity();
                growthLevel = 0.0;
            }
        }
    }

    //interactiunea Plant - Air (oxigenul din aer creste cu oxigenul produs de planta)
    public void interactionAir(Air air) {
        if (air != null && !getIsDead() && getIsScanned()) {
            double newOxygenLevel = air.getOxygenLevel() + oxygenLevel();
            //rotunjum la doua zecimale
            newOxygenLevel = Math.round(newOxygenLevel * 100.0) / 100.0;
            air.setOxygenLevel(newOxygenLevel);
        }
    }
}
