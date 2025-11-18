package main.entities;
import main.entities.Plant;
import main.entities.Animal;
import main.entities.Water;
import main.entities.Soil;
import fileio.AirInput;
import java.util.*;

public abstract class Air {
    protected String name;
    protected double mass;
    protected String type;
    private double humidity;
    private double temperature;
    private double oxygenLevel;
    private double co2Level;
    private double iceCrystalConcentration;
    private double pollenLevel;
    private double dustParticles;
    private double altitude;

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

    protected void normalize(double quality) {
        quality = Math.max(0, Math.min(100, quality));
        quality = Math.round(quality * 100.0) / 100.0;
    }

    protected abstract double airQuality();
    protected abstract int maxScore();
    protected abstract double meteorologicalEvents(double rainfall, double windSpeed, String newSeason, boolean desertStorm, int numberOfHikers);

    protected String qualityLabel() {
        double quality = airQuality();
        if (quality >= 70.0 && quality <= 100.0) {
            return "Good";
        }
        if (quality >= 40.0 && quality < 70.0) {
            return "Moderate";
        }
        return "Poor";
    }

    protected double airToxicity() {
        int maxScore = maxScore();
        double airQuality = airQuality();
        double toxicity = 100 * (1 - airQuality / maxScore);
        toxicity = Math.max(0, Math.min(100, toxicity));
        toxicity = Math.round(toxicity * 100.0) / 100.0;
        return toxicity;
    }

    protected boolean isToxic() {
        int maxScore = maxScore();
        double toxicity = airToxicity();
        if (toxicity > (0.8 * maxScore)) {
            return true;
        }
        return false;
    }
}
