package main.entities.Air;

import main.entities.Animal.Animal;
import main.entities.Entities;

public abstract class Air extends Entities {
    //Magic number fix
    private static final double TEMPORARY_AIR_QUALITY = -1.0;
    private static final int EXPIRATION_TIME = -1;
    private static final int TWO_ITERATIONS = 2;
    private static final double NORMALIZING_FACTOR = 100.0;
    private static final int MAX_NORMALIZE = 100;
    private static final double PERCENTAGE_70 = 70.0;
    private static final double PERCENTAGE_40 = 40.0;
    private static final double PERCENTAGE_100 = 100.0;
    private static final double TOXIC_SCORE = 0.8;

    private String type; //air type (Desert, Tropical, Temperate, Polar, Mountain)
    private double humidity; //humidity in the air
    private double temperature; //air temperature
    private double oxygenLevel; //oxygen level in the air
    private double co2Level; //co2 in the air
    private double iceCrystalConcentration; //ice/fog/snow
    private double pollenLevel; //pollen in the air
    private double dustParticles; //dust in the air
    private double altitude; //altitude
    private double temporaryQuality; //temporary air quality
    private int expirationTime; //change temp quality after 2 iterations

    public Air(final String name, final double mass) {
        super(name, mass); //Superclass's constructor
        this.type = null;
        this.humidity = 0.0;
        this.temperature = 0.0;
        this.oxygenLevel = 0.0;
        this.co2Level = 0.0;
        this.iceCrystalConcentration = 0.0;
        this.pollenLevel = 0.0;
        this.dustParticles = 0.0;
        this.altitude = 0.0;
        this.temporaryQuality = TEMPORARY_AIR_QUALITY;
        this.expirationTime = EXPIRATION_TIME;
    }

    //Getter and Setter methods for private class fields
    public final String getType() {
        return this.type;
    }
    public final void setType(final String type) {
        this.type = type;
    }
    public final double getHumidity() {
        return this.humidity;
    }
    public final void setHumidity(final double humidity) {
        this.humidity = humidity;
    }
    public final double getTemperature() {
        return this.temperature;
    }
    public final void setTemperature(final double temperature) {
        this.temperature = temperature;
    }
    public final double getOxygenLevel() {
        return this.oxygenLevel;
    }
    public final void setOxygenLevel(final double oxygenLevel) {
        this.oxygenLevel = oxygenLevel;
    }
    public final double getCo2Level() {
        return this.co2Level;
    }
    public final void setCo2Level(final double co2Level) {
        this.co2Level = co2Level;
    }
    public final double getIceCrystalConcentration() {
        return this.iceCrystalConcentration;
    }
    public final void setIceCrystalConcentration(final double iceCrystalConcentration) {
        this.iceCrystalConcentration = iceCrystalConcentration;
    }
    public final double getPollenLevel() {
        return this.pollenLevel;
    }
    public final void setPollenLevel(final double pollenLevel) {
        this.pollenLevel = pollenLevel;
    }
    public final double getDustParticles() {
        return this.dustParticles;
    }
    public final void setDustParticles(final double dustParticles) {
        this.dustParticles = dustParticles;
    }
    public final double getAltitude() {
        return this.altitude;
    }
    public final void setAltitude(final double altitude) {
        this.altitude = altitude;
    }
    public final void setTemporaryQuality(final double temporaryQuality) {
        this.temporaryQuality = temporaryQuality;
    }
    public final void setExpirationTime(final int currentTime) {
        this.expirationTime = currentTime + TWO_ITERATIONS;
    }
    public final int getExpirationTime() {
        return this.expirationTime;
    }

    /**
     * Helper method for normalization
     * @param score score
     * @return normalized value of score
     */
    protected final double normalize(final double score) {
        double normalized = Math.max(0, Math.min(MAX_NORMALIZE, score));
        return Math.round(normalized * NORMALIZING_FACTOR) / NORMALIZING_FACTOR;
    }

    /**
     * Calculates air quality according to each type of air
     * @return airQuality
     */
    protected abstract double airQuality();

    /**
     * Because airQuality is protected, the access to it is made by this method
     * Also, it returns the temporary quality if we are in those 2 iterations
     * @return airQuality / temporaryQuality
     */
    public double getQuality() {
        if (this.temporaryQuality != TEMPORARY_AIR_QUALITY) {
            return this.temporaryQuality;
        }
        return airQuality();
    }

    /**
     * Returns the max score for each type of air
     * @return maxScore
     */
    protected abstract int maxScore();

    /**
     * Calculates the new air quality affected by a particular event
     * @param rainfall field for Tropical Air
     * @param windSpeed field for Polar Air
     * @param newSeason field for Temperate Air
     * @param desertStorm field for Desert Air
     * @param numberOfHikers field for Mountain Air
     */
    public abstract void meteorologicalEvents(double rainfall, double windSpeed, String newSeason,
                                              boolean desertStorm, int numberOfHikers);

    /**
     * Return air quality label
     * @return qualityLabel
     */
    public String qualityLabel() {
        double quality = getQuality();
        if (quality > PERCENTAGE_70 && quality <= PERCENTAGE_100) {
            return "good";
        }
        if (quality > PERCENTAGE_40 && quality <= PERCENTAGE_70) {
            return "moderate";
        }
        return "poor";
    }

    /**
     * Calculates air toxicity
     * @return airToxicity
     */
    protected double airToxicity() {
        int maxScore = maxScore();
        double airQuality = getQuality();
        double toxicity = MAX_NORMALIZE * (1 - airQuality / maxScore);
        toxicity = Math.max(0, Math.min(MAX_NORMALIZE, toxicity));
        toxicity = Math.round(toxicity * NORMALIZING_FACTOR) / NORMALIZING_FACTOR;
        return toxicity;
    }

    /**
     * Because airToxicity is protected, the access to it is made by this method
     * @return airToxicity
     */
    public double getToxicProbability() {
        return airToxicity();
    }

    /**
     * It informs if the air is toxic or not
     * @return is the air toxic?
     */
    protected boolean isToxic() {
        int maxScore = maxScore();
        double toxicity = airToxicity();
        if (toxicity > (TOXIC_SCORE * maxScore)) {
            return true;
        }
        return false;
    }

    /**
     * Interaction Air-Animal (if the air is toxic the animal becomes Sick)
     * @param animal the animal with which the air interacts
     */
    public void interactionAnimal(final Animal animal) {
        if (animal != null && animal.getIsScanned() && !animal.getIsDead()) {
            if (isToxic()) {
                animal.setStatus("Sick");
            }
        }
    }
}
