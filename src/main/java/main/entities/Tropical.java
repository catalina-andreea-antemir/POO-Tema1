package main.entities;
import main.entities.Animal;
import main.entities.Plant;
import main.entities.Water;
import main.entities.Soil;
import main.entities.Air;
import java.util.*;

public class Tropical extends Air {
    public Tropical(String name, double mass) {
        super(name, mass);
    }

    @Override
    protected double airQuality() {
        double quality = (getOxygenLevel() * 2) + (getHumidity() * 0.5) - (getCo2Level() * 0.01);
        return normalize(quality);
    }

    @Override
    protected int maxScore() {
        return 82;
    }

    @Override
    protected double meteorologicalEvents(double rainfall, double windSpeed, String newSeason, boolean desertStorm, int numberOfHikers) {
        double quality = airQuality();
        quality += rainfall * 0.3;
        normalize(quality);
        return quality;
    }
}
