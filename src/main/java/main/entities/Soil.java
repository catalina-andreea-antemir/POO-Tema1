package main.entities;
import main.entities.Plant;
import main.entities.Animal;
import main.entities.Water;
import main.entities.Air;
import fileio.SoilInput;

public abstract class Soil {
    private String name;
    private double mass;
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
        this.name = name;
        this.mass = mass;
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

    public double getNitrogen() {
        return this.nitrogen;
    }
    public void setNitrogen(double nitrogen) {
        this.nitrogen = nitrogen;
    }

    public double getWaterRetention() {
        return this.waterRetention;
    }
    public void setWaterRetention(double waterRetention) {
        this.waterRetention = waterRetention;
    }

    public double getSoilpH() {
        return this.soilpH;
    }
    public void setSoilpH(double soilpH) {
        this.soilpH = soilpH;
    }

    public double getOrganicMatter() {
        return this.organicMatter;
    }
    public void setOrganicMatter(double organicMatter) {
        this.organicMatter = organicMatter;
    }

    public double getLeafLitter() {
        return this.leafLitter;
    }
    public void setLeafLitter(double leafLitter) {
        this.leafLitter = leafLitter;
    }

    public double getWaterLogging() {
        return this.waterLogging;
    }
    public void setWaterLogging(double waterLogging) {
        this.waterLogging = waterLogging;
    }

    public double getRootDensity() {
        return this.rootDensity;
    }
    public void setRootDensity(double rootDensity) {
        this.rootDensity = rootDensity;
    }

    public double getPermafrostDepth() {
        return this.permafrostDepth;
    }
    public void setPermafrostDepth(double permafrostDepth) {
        this.permafrostDepth = permafrostDepth;
    }

    public double getSalinity() {
        return this.salinity;
    }
    public void setSalinity(double salinity) {
        this.salinity = salinity;
    }

    protected double normalize(double score) {
        double normalized = Math.max(0, Math.min(100, score));
        score = Math.round(normalized * 100.0) / 100.0;
        return score;
    }

    protected abstract double qualityScore();
    public double getQuality() {
        return qualityScore();
    }
    protected abstract double blockProbability();
    public double getBlockProbability() {
        return blockProbability();
    }

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

    public void interactionPlant(Plant plant) {
        if (plant != null && plant.getIsScanned() && !plant.getIsDead()) {
            plant.grow(0.2);
        }
    }
}
