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

    //metode getter si setter pentru campul privat humidity
    public double getHumidity() {
        return this.humidity;
    }
    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    //metode getter si setter pentru campul privat temperature
    public double getTemperature() {
        return this.temperature;
    }
    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    //metode getter si setter pentru campul privat ocygenLevel
    public double getOxygenLevel() {
        return this.oxygenLevel;
    }
    public void setOxygenLevel(double oxygenLevel) {
        this.oxygenLevel = oxygenLevel;
    }

    //metode getter si setter pentru campul privat co2Level
    public double getCo2Level() {
        return this.co2Level;
    }
    public void setCo2Level(double co2Level) {
        this.co2Level = co2Level;
    }

    //metode getter si setter pentru campul privat iceCrystalConcentration
    public double getIceCrystalConcentration() {
        return this.iceCrystalConcentration;
    }
    public void setIceCrystalConcentration(double iceCrystalConcentration) {
        this.iceCrystalConcentration = iceCrystalConcentration;
    }

    //metode getter si setter pentru campul privat pollenLevel
    public double getPollenLevel() {
        return this.pollenLevel;
    }
    public void setPollenLevel(double pollenLevel) {
        this.pollenLevel = pollenLevel;
    }

    //metode getter si setter pentru campul privat dustParticles
    public double getDustParticles() {
        return this.dustParticles;
    }
    public void setDustParticles(double dustParticles) {
        this.dustParticles = dustParticles;
    }

    //metode getter si setter pentru campul privat altitude
    public double getAltitude() {
        return this.altitude;
    }
    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    //metoda setter pentru campul privat temporaryQuality
    public void setTemporaryQuality(double temporaryQuality) {
        this.temporaryQuality = temporaryQuality;
    }

    //metode getter si setter pentru campul privat expirationDate
    public void setExpirationTime(int currentTime) {
        this.expirationTime = currentTime + 2;
    }
    public int getExpirationTime() {
        return this.expirationTime;
    }

    //metoda helper pentru normalizare
    protected double normalize(double score) {
        double normalized = Math.max(0, Math.min(100, score));
        score = Math.round(normalized * 100.0) / 100.0;
        return score;
    }

    //calculeaza calitatea aerului
    protected abstract double airQuality();
    public double getQuality() {
        if (this.temporaryQuality != -1.0) {
            return this.temporaryQuality;
        }
        return airQuality();
    }
    //returneaza scorul maxim
    protected abstract int maxScore();
    //calculeaza noua calitate a aerului afectata de un anumit eveniment
    public abstract void meteorologicalEvents(double rainfall, double windSpeed, String newSeason, boolean desertStorm, int numberOfHikers);

    //returneaza eticheta calitatii aerului
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

    //calculeaza toxicitatea aerului
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

    //aerul este txic sau nu?
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
