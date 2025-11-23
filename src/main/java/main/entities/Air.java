package main.entities;
import main.entities.Plant;
import main.entities.Animal;
import main.entities.Water;
import main.entities.Soil;
import fileio.AirInput;

public abstract class Air {
    private String name;
    private double mass;
    private String type;
    private double humidity;
    private double temperature;
    private double oxygenLevel;
    private double co2Level;
    private double iceCrystalConcentration;
    private double pollenLevel;
    private double dustParticles;
    private double altitude;
    private double temporaryQuality;
    private int expirationTime;

    public Air(String name, double mass) {
        this.name = name;
        this.mass = mass;
        this.type = null;
        this.humidity = 0.0;
        this.temperature = 0.0;
        this.oxygenLevel = 0.0;
        this.co2Level = 0.0;
        this.iceCrystalConcentration = 0.0;
        this.pollenLevel = 0.0;
        this.dustParticles = 0.0;
        this.altitude = 0.0;
        this.temporaryQuality = -1.0;
        this.expirationTime = -1;
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

    public double getHumidity() {
        return this.humidity;
    }
    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getTemperature() {
        return this.temperature;
    }
    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getOxygenLevel() {
        return this.oxygenLevel;
    }
    public void setOxygenLevel(double oxygenLevel) {
        this.oxygenLevel = oxygenLevel;
    }

    public double getCo2Level() {
        return this.co2Level;
    }
    public void setCo2Level(double co2Level) {
        this.co2Level = co2Level;
    }

    public double getIceCrystalConcentration() {
        return this.iceCrystalConcentration;
    }
    public void setIceCrystalConcentration(double iceCrystalConcentration) {
        this.iceCrystalConcentration = iceCrystalConcentration;
    }

    public double getPollenLevel() {
        return this.pollenLevel;
    }
    public void setPollenLevel(double pollenLevel) {
        this.pollenLevel = pollenLevel;
    }

    public double getDustParticles() {
        return this.dustParticles;
    }
    public void setDustParticles(double dustParticles) {
        this.dustParticles = dustParticles;
    }

    public double getAltitude() {
        return this.altitude;
    }
    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public void setTemporaryQuality(double temporaryQuality) {
        this.temporaryQuality = temporaryQuality;
    }

    public void setExpirationTime(int currentTime) {
        this.expirationTime = currentTime + 2;
    }
    public int getExpirationTime() {
        return this.expirationTime;
    }

    protected double normalize(double score) {
        double normalized = Math.max(0, Math.min(100, score));
        score = Math.round(normalized * 100.0) / 100.0;
        return score;
    }

    protected abstract double airQuality();
    public double getQuality() {
        if (this.temporaryQuality != -1.0) {
            return this.temporaryQuality;
        }
        return airQuality();
    }
    protected abstract int maxScore();
    public abstract void meteorologicalEvents(double rainfall, double windSpeed, String newSeason, boolean desertStorm, int numberOfHikers);

    public String qualityLabel() {
        double quality = getQuality();
        if (quality > 70.0 && quality <= 100.0) {
            return "good";
        }
        if (quality > 40.0 && quality <= 70.0) {
            return "moderate";
        }
        return "poor";
    }

    protected double airToxicity() {
        int maxScore = maxScore();
        double airQuality = getQuality();
        double toxicity = 100 * (1 - airQuality / maxScore);
        toxicity = Math.max(0, Math.min(100, toxicity));
        toxicity = Math.round(toxicity * 100.0) / 100.0;
        return toxicity;
    }
    public double getToxicProbability() {
        return airToxicity();
    }

    protected boolean isToxic() {
        int maxScore = maxScore();
        double toxicity = airToxicity();
        if (toxicity > (0.8 * maxScore)) {
            return true;
        }
        return false;
    }

    public void interactionAnimal(Animal animal) {
        if (animal != null && animal.getIsScanned() && !animal.getIsDead()) {
            if (isToxic()) {
                animal.setStatus("Sick");
            }
        }
    }
}
