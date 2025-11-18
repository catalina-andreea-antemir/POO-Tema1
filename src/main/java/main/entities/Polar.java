package main.entities;
import main.entities.Animal;
import main.entities.Plant;
import main.entities.Water;
import main.entities.Soil;
import main.entities.Air;
import java.util.*;

public class Polar extends Air {
    public Polar(String name, double mass) {
        super(name, mass);
    }

    @Override
    protected double airQuality() {
        double quality = (getOxygenLevel() * 2) + (100 - Math.abs(getTemperature())) - (getIceCrystalConcentration() * 0.05);
        normalize(quality);
        return quality;
    }

    @Override
    protected int maxScore() {
        return 142;
    }

    @Override
    protected double meteorologicalEvents(double rainfall, double windSpeed, String newSeason, boolean desertStorm, int numberOfHikers) {
        double quality = airQuality();
        quality -= windSpeed * 0.2;
        normalize(quality);
        return quality;
    }
}
