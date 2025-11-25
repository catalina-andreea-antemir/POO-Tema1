package main.entities;
import main.entities.Plant;
import main.entities.Animal;
import main.entities.Air;
import main.entities.Soil;
import fileio.WaterInput;

public class Water extends Entities {
    private String type;
    private double salinity;
    private double pH;
    private double purity;
    private double turbidity;
    private double contaminantIndex;
    private boolean isFrozen;
    private boolean isScanned;
    private int airExpirationTime;
    private int soilExpirationTime;

    public Water(String name, double mass) {
        super(name, mass);
        this.type = null;
        this.salinity = 0.0;
        this.pH = 0.0;
        this.purity = 0.0;
        this.turbidity = 0.0;
        this.contaminantIndex = 0.0;
        this.isFrozen = false;
        this.isScanned = false;
        this.airExpirationTime = -1;
        this.soilExpirationTime = -1;
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

    //getter si setter pentru campul privat "isScanned"
    public boolean getIsScanned() {
        return this.isScanned;
    }
    public void setIsScanned(boolean isScanned) {
        this.isScanned = isScanned;
    }

    //getter si setter pentru campul private airExpirationTime
    public int getAirExpirationTime() {
        return this.airExpirationTime;
    }
    public void setAirExpirationTime(int currentTime) {
        this.airExpirationTime = currentTime + 2;
    }

    //getter si setter pentru camppul private soilExpirationTime
    public int getSoilExpirationTime() {
        return this.soilExpirationTime;
    }
    public void setSoilExpirationTime(int currentTime) {
        this.soilExpirationTime = currentTime + 2;
    }

    //calculeaza calitatea apei pe baza unor formule impuse
    public double waterQuality() {
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

    //interactiunea Water - Soil (apa creste waterRetention din sol cu 0.1 la fiecare doua iteratii)
    public void interactionSoil(Soil soil) {
        if (soil != null) {
            double val = soil.getWaterRetention() + 0.1;
            //rotunjim valoarea la doua zecimale
            val = Math.round(val * 100.0) / 100.0;
            soil.setWaterRetention(val);
        }
    }

    //interactiunea Water - Air (apa creste umiditatea din aer cu 0.1 odata la doua iteratii)
    public void interactionAir(Air air) {
        if (air != null) {
            double val = air.getHumidity() + 0.1;
            //rotunjim la doua zecimale
            val = Math.round(val * 100.0) / 100.0;
            air.setHumidity(val);
        }
    }

    //interactiunea Water - Plant (apa creste planta cu 0.2)
    public void interactionPlant(Plant plant) {
        if (plant != null && plant.getIsScanned() && !plant.getIsDead()) {
            plant.grow(0.2);
        }
    }
}
