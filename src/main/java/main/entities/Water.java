package main.entities;
import main.entities.Plant;
import main.entities.Animal;
import main.entities.Air;
import main.entities.Soil;
import fileio.WaterInput;

public class Water {
    private String name;
    private double mass;
    private String type;
    private double salinity;
    private double pH;
    private double purity;
    private double turbidity;
    private double contaminantIndex;
    private boolean isFrozen;

    public Water(String name, double mass) {
        this.name = name;
        this.mass = mass;
        this.type = null;
        this.salinity = 0.0;
        this.pH = 0.0;
        this.purity = 0.0;
        this.turbidity = 0.0;
        this.contaminantIndex = 0.0;
        this.isFrozen = false;
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

    //getter si setter pentru campul privat "salinity"
    public double getSalinity() {
        return this.salinity;
    }
    public void setSalinity(double salinity) {
        this.salinity = salinity;
    }

    //getter si setter pentru campul privat "pH"
    public double getpH() {
        return this.pH;
    }
    public void setpH(double pH) {
        this.pH = pH;
    }

    //getter si setter pentru campul privat "purity"
    public double getPurity() {
        return this.purity;
    }
    public void setPurity(double Purity) {
        this.purity = purity;
    }

    //getter si setter pentru campul privat "turbidity"
    public double getTurbidity() {
        return this.turbidity;
    }
    public void setTurbidity(double turbidity) {
        this.turbidity = turbidity;
    }

    //getter si setter pentru campul privat "contaminantIndex"
    public double getContaminantIndex() {
        return this.contaminantIndex;
    }
    public void setContaminantIndex(double contaminantIndex) {
        this.contaminantIndex = contaminantIndex;
    }

    //getter si setter pentru campul privat "isFrozen"
    public boolean getIsFrozen() {
        return this.isFrozen;
    }
    public void setIsFrozen(boolean isFrozen) {
        this.isFrozen = isFrozen;
    }

    //calculeaza calitatea apei pe baza unor formule impuse
    protected double waterQuality() {
        double purityScore = purity / 100;
        double pHScore = 1 - Math.abs(pH - 7.5) / 7.5;
        double salinityScore = 1 - (salinity / 350);
        double turbidityScore = 1 - (turbidity / 100);
        double contaminantScore = 1 - (contaminantIndex / 100);
        double frozenScore;
        if (isFrozen != false) {
            frozenScore = 0.0;
        } else {
            frozenScore = 1.0;
        }
        double quality = (0.3 * purityScore + 0.2 * pHScore + 0.15 * salinityScore + 0.1 * turbidityScore + 0.15 * contaminantScore + 0.2 * frozenScore) * 100;
        return quality;
    }

    //alege eticheta de calitate a apei pe baza valorii calitatii acesteia
    protected String qualityLabel() {
        double quality = waterQuality();
        if (quality >= 70.0 && quality <= 100.0) {
            return "Good";
        }
        if (quality >= 40.0 && quality < 70.0) {
            return "Moderate";
        }
        return "Poor";
    }

    public void interactionSoil(Soil soil) {
        if (soil != null) {
            soil.setWaterRetention(soil.getWaterRetention() + 0.1);
        }
    }

    public void interactionAir(Air air) {
        if (air != null) {
            air.setHumidity(air.getHumidity() + 0.1);
        }
    }

    public void interactionPlant(Plant plant) {
        if (plant != null && plant.getIsScanned() && !plant.getIsDead()) {
            plant.grow(0.2);
        }
    }
}
