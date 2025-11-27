package main.entities.Soil;

import main.entities.Entities;
import main.entities.Plant.Plant;

public abstract class Soil extends Entities {
    //Magic number fix
    private static final double NORMALIZING_FACTOR = 100.0;
    private static final int MAX_NORMALIZE = 100;
    private static final double PERCENTAGE_70 = 70.0;
    private static final double PERCENTAGE_40 = 40.0;
    private static final double PERCENTAGE_100 = 100.0;
    private static final double PLANT_GROW = 0.2;

    private String type; //soil type (Forest, Desert, Grassland, Tundra, Swamp)
    private double nitrogen; //nitrogen level in soil
    private double waterRetention; //water retention in soil
    private double soilpH; //soil ph
    private double organicMatter; //organic matter for fertilizer
    private double leafLitter; //for forest soil
    private double waterLogging; //for swamp soil
    private double rootDensity; //for grassland soil
    private double permafrostDepth; //for tundra soil
    private double salinity; //for desert soil

    public Soil(final String name, final double mass) {
        super(name, mass); //Superclass's constructor
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

    //Getter and Setter methods for private class fields
    public final String getType() {
        return this.type;
    }
    public final void setType(final String type) {
        this.type = type;
    }
    public final double getNitrogen() {
        return this.nitrogen;
    }
    public final void setNitrogen(final double nitrogen) {
        this.nitrogen = nitrogen;
    }
    public final double getWaterRetention() {
        return this.waterRetention;
    }
    public final void setWaterRetention(final double waterRetention) {
        this.waterRetention = waterRetention;
    }
    public final double getSoilpH() {
        return this.soilpH;
    }
    public final void setSoilpH(final double soilpH) {
        this.soilpH = soilpH;
    }
    public final double getOrganicMatter() {
        return this.organicMatter;
    }
    public final void setOrganicMatter(final double organicMatter) {
        this.organicMatter = organicMatter;
    }
    public final double getLeafLitter() {
        return this.leafLitter;
    }
    public final void setLeafLitter(final double leafLitter) {
        this.leafLitter = leafLitter;
    }
    public final double getWaterLogging() {
        return this.waterLogging;
    }
    public final void setWaterLogging(final double waterLogging) {
        this.waterLogging = waterLogging;
    }
    public final double getRootDensity() {
        return this.rootDensity;
    }
    public final void setRootDensity(final double rootDensity) {
        this.rootDensity = rootDensity;
    }
    public final double getPermafrostDepth() {
        return this.permafrostDepth;
    }
    public final void setPermafrostDepth(final double permafrostDepth) {
        this.permafrostDepth = permafrostDepth;
    }
    public final double getSalinity() {
        return this.salinity;
    }
    public final void setSalinity(final double salinity) {
        this.salinity = salinity;
    }

    /**
     * Helper method for normalization
     */
    protected double normalize(final double score) {
        double normalized = Math.max(0, Math.min(MAX_NORMALIZE, score));
        return Math.round(normalized * NORMALIZING_FACTOR) / NORMALIZING_FACTOR;
    }

    /**
     * Returns soil quality according to each soil type based on a formula
     * @return soilQuality
     */
    protected abstract double qualityScore();

    /**
     * Because qualityScore is protected, the access to it is made by this method
     * @return soilQuality
     */
    public double getQuality() {
        return qualityScore();
    }

    /**
     * Calculates the attack probability based on a formula specific to each type
     * @return blockProbability
     */
    protected abstract double blockProbability();

    /**
     * Because blockProbability is protected, the access to it is made by this method
     * @return blockProbability
     */
    public double getBlockProbability() {
        return blockProbability();
    }

    /**
     * Establish the quality of soil according to the calculated quality for each type
     * @return qualityLabel
     */
    public String qualityLabel() {
        double quality = qualityScore();
        if (quality > PERCENTAGE_70 && quality <= PERCENTAGE_100) {
            return "good";
        }
        if (quality > PERCENTAGE_40 && quality <= PERCENTAGE_70) {
            return "moderate";
        }
        return "poor";
    }

    /**
     * Interaction Soil - Plant (soil makes the plant grow by 0.2)
     */
    public void interactionPlant(final Plant plant) {
        if (plant != null && plant.getIsScanned() && !plant.getIsDead()) {
            plant.grow(PLANT_GROW);
        }
    }
}
