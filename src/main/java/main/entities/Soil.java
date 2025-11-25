package main.entities;
import main.entities.Plant;
import main.entities.Animal;
import main.entities.Water;
import main.entities.Air;
import fileio.SoilInput;

public abstract class Soil extends Entities {
    private String type;
    private double nitrogen;
    private double waterRetention;
    private double soilpH;
    private double organicMatter;
    private double leafLitter;
    private double waterLogging;
    private double rootDensity;
    private double permafrostDepth;
    private double salinity;

    public Soil(String name, double mass) {
        super(name, mass); //se apeleaza constructorul parintelui
        this.type = null;
        this.nitrogen = 0.0;
        this.waterRetention = 0.0;
        this.soilpH = 0.0;
        this.organicMatter = 0.0;
        this.leafLitter = 0.0;
        this.waterLogging = 0.0;
        this.rootDensity = 0.0;
        this.permafrostDepth = 0.0;
        this.salinity = 0.0;
    }

    //metode getter si setter pentru campul privat type
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }

    //metode getter si setter pentru campul privat nitrogen
    public double getNitrogen() {
        return this.nitrogen;
    }
    public void setNitrogen(double nitrogen) {
        this.nitrogen = nitrogen;
    }

    //metode getter si setter pentru campul privat waterRetention
    public double getWaterRetention() {
        return this.waterRetention;
    }
    public void setWaterRetention(double waterRetention) {
        this.waterRetention = waterRetention;
    }

    //metode getter si setter pentru campul privat soilpH
    public double getSoilpH() {
        return this.soilpH;
    }
    public void setSoilpH(double soilpH) {
        this.soilpH = soilpH;
    }

    //metode getter si setter pentru campul privat organicMetter
    public double getOrganicMatter() {
        return this.organicMatter;
    }
    public void setOrganicMatter(double organicMatter) {
        this.organicMatter = organicMatter;
    }

    //metode getter si setter pentru campul privat leafLitter
    public double getLeafLitter() {
        return this.leafLitter;
    }
    public void setLeafLitter(double leafLitter) {
        this.leafLitter = leafLitter;
    }

    //metode getter si setter pentru campul privat waterLogging
    public double getWaterLogging() {
        return this.waterLogging;
    }
    public void setWaterLogging(double waterLogging) {
        this.waterLogging = waterLogging;
    }

    //metode getter si setter pentru campul privat rootDensity
    public double getRootDensity() {
        return this.rootDensity;
    }
    public void setRootDensity(double rootDensity) {
        this.rootDensity = rootDensity;
    }

    //metode getter si setter pentru campul privat permafrostDepth
    public double getPermafrostDepth() {
        return this.permafrostDepth;
    }
    public void setPermafrostDepth(double permafrostDepth) {
        this.permafrostDepth = permafrostDepth;
    }

    //metode getter si setter pentru campul privat salinity
    public double getSalinity() {
        return this.salinity;
    }
    public void setSalinity(double salinity) {
        this.salinity = salinity;
    }

    //metoda helper de noramlizare
    protected double normalize(double score) {
        double normalized = Math.max(0, Math.min(100, score));
        score = Math.round(normalized * 100.0) / 100.0;
        return score;
    }

    //returneaza calitatea solului in functie de fiecare tip de sol pe baza unei formule
    protected abstract double qualityScore();
    public double getQuality() {
        return qualityScore();
    }

    //calculeaza probabilitatea de atac pe baza unei formule specifice fiecarui tip
    protected abstract double blockProbability();
    public double getBlockProbability() {
        return blockProbability();
    }

    //stabileste aticeta de calitate a solului in functie de calitatea calculata pt fiecare tip
    public String qualityLabel() {
        double quality = qualityScore();
        if (quality > 70.0 && quality <= 100.0) {
            return "good";
        }
        if (quality > 40.0 && quality <= 70.0) {
            return "moderate";
        }
        return "poor";
    }

    //interactiunea Soil - Plant (solul face planta sa creasca cu 0.2)
    public void interactionPlant(Plant plant) {
        if (plant != null && plant.getIsScanned() && !plant.getIsDead()) {
            plant.grow(0.2);
        }
    }
}
