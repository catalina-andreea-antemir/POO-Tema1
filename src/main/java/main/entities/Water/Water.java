package main.entities.Water;

import main.entities.Air.Air;
import main.entities.Entities;
import main.entities.Plant.Plant;
import main.entities.Soil.Soil;

public class Water extends Entities {
    //Magic number fix
    private static final int TWO_ITERATIONS = 2;
    private static final int EXPIRATION_TIME = -1;
    private static final double PERCENTAGE_70 = 70.0;
    private static final double PERCENTAGE_40 = 40.0;
    private static final double PERCENTAGE_100 = 100.0;
    private static final double NORMALIZING_FACTOR = 100.0;
    private static final double PURITY_MUL = 0.3;
    private static final double PH_MUL = 0.2;
    private static final double SALINITY_MUL = 0.15;
    private static final double TURBIDITY_MUL = 0.1;
    private static final double CONTAMINANT_MUL = 0.15;
    private static final double FROZEN_MUL = 0.2;
    private static final double FROZEN_SCORE1 = 0.0;
    private static final double FROZEN_SCORE2 = 1.0;
    private static final double PH_DEC = 7.5;
    private static final int SALINITY_DIV = 350;
    private static final int HUNDRED = 100;
    private static final double WATER_ADD = 0.1;
    private static final double HUMIDITY_ADD = 0.1;
    private static final double PLANT_GROW = 0.2;

    private String type; //water type
    private double salinity; //water salinity
    private double pH; //water ph
    private double purity; //water purity
    private double turbidity; //water turbidity
    private double contaminantIndex; //water contamination
    private boolean isFrozen; //true if water is frozen
    private boolean isScanned; //true if the robot scanned the water
    private int airExpirationTime; //interaction air-water every 2 iterations
    private int soilExpirationTime; //interaction water-soil every 2 iterations

    public Water(final String name, final double mass) {
        super(name, mass);
        this.type = null;
        this.salinity = 0.0;
        this.pH = 0.0;
        this.purity = 0.0;
        this.turbidity = 0.0;
        this.contaminantIndex = 0.0;
        this.isFrozen = false;
        this.isScanned = false;
        this.airExpirationTime = EXPIRATION_TIME;
        this.soilExpirationTime = EXPIRATION_TIME;
    }

    //Getter and Setter methods for private class fields
    public final String getType() {
        return this.type;
    }
    public final void setType(final String type) {
        this.type = type;
    }
    public final double getSalinity() {
        return this.salinity;
    }
    public final void setSalinity(final double salinity) {
        this.salinity = salinity;
    }
    public final void setPh(final double phValue) {
        this.pH = phValue;
    }
    public final double getPurity() {
        return this.purity;
    }
    public final void setPurity(final double purity) {
        this.purity = purity;
    }
    public final double getTurbidity() {
        return this.turbidity;
    }
    public final void setTurbidity(final double turbidity) {
        this.turbidity = turbidity;
    }
    public final double getContaminantIndex() {
        return this.contaminantIndex;
    }
    public final void setContaminantIndex(final double contaminantIndex) {
        this.contaminantIndex = contaminantIndex;
    }
    public final boolean getIsFrozen() {
        return this.isFrozen;
    }
    public final void setIsFrozen(final boolean isFrozen) {
        this.isFrozen = isFrozen;
    }
    public final boolean getIsScanned() {
        return this.isScanned;
    }
    public final void setIsScanned(final boolean isScanned) {
        this.isScanned = isScanned;
    }
    public final int getAirExpirationTime() {
        return this.airExpirationTime;
    }
    public final void setAirExpirationTime(final int currentTime) {
        this.airExpirationTime = currentTime + TWO_ITERATIONS;
    }
    public final int getSoilExpirationTime() {
        return this.soilExpirationTime;
    }
    public final void setSoilExpirationTime(final int currentTime) {
        this.soilExpirationTime = currentTime + TWO_ITERATIONS;
    }

    /**
     * Calculate the water quality based on the required formulas
     * @return waterQuality
     */
    public double waterQuality() {
        double purityScore = purity / HUNDRED;
        double pHScore = 1 - Math.abs(pH - PH_DEC) / PH_DEC;
        double salinityScore = 1 - (salinity / SALINITY_DIV);
        double turbidityScore = 1 - (turbidity / HUNDRED);
        double contaminantScore = 1 - (contaminantIndex / HUNDRED);
        double frozenScore;
        if (isFrozen) {
            frozenScore = FROZEN_SCORE1;
        } else {
            frozenScore = FROZEN_SCORE2;
        }
        double quality = (PURITY_MUL * purityScore + PH_MUL * pHScore
                + SALINITY_MUL * salinityScore + TURBIDITY_MUL * turbidityScore
                + CONTAMINANT_MUL * contaminantScore + FROZEN_MUL * frozenScore) * HUNDRED;
        return quality;
    }

    /**
     * Choose the water quality label based on its quality value
     * @return qualityLabel
     */
    protected String qualityLabel() {
        double quality = waterQuality();
        if (quality >= PERCENTAGE_70 && quality <= PERCENTAGE_100) {
            return "Good";
        }
        if (quality >= PERCENTAGE_40 && quality < PERCENTAGE_70) {
            return "Moderate";
        }
        return "Poor";
    }

    /**
     * Interaction Water - Soil (waterRetention increases from the soil by
     * 0.1 for every two iterations)
     */
    public void interactionSoil(final Soil soil) {
        if (soil != null) {
            double val = soil.getWaterRetention() + WATER_ADD;
            val = Math.round(val * NORMALIZING_FACTOR) / NORMALIZING_FACTOR;
            soil.setWaterRetention(val);
        }
    }

    /**
     * Interaction water - Air (water increases humidity in the air by
     * 0.1 once every two iterations)
     */
    public void interactionAir(final Air air) {
        if (air != null) {
            double val = air.getHumidity() + HUMIDITY_ADD;
            val = Math.round(val * NORMALIZING_FACTOR) / NORMALIZING_FACTOR;
            air.setHumidity(val);
        }
    }

    /**
     * Interaction Water - Plant (water grows plant by 0.2)
     */
    public void interactionPlant(final Plant plant) {
        if (plant != null && plant.getIsScanned() && !plant.getIsDead()) {
            plant.grow(PLANT_GROW);
        }
    }
}
