package main.entities;
import main.entities.Animal;
import main.entities.Plant;
import main.entities.Water;
import main.entities.Soil;
import main.entities.Air;
import java.util.*;

public class Temperate extends Air {
    public Temperate(String name, double mass) {
        super(name, mass);
    }

    @Override
    protected double airQuality() {
        double quality = (getOxygenLevel() * 2) + (getHumidity() * 0.7) - (getPollenLevel() * 0.1);
        normalize(quality);
        return quality;
    }

    @Override
    protected int maxScore() {
        return 84;
    }

    @Override
    protected double meteorologicalEvents(double rainfall, double windSpeed, String newSeason, boolean desertStorm, int numberOfHikers) {
        double quality = airQuality();
        int seasonPenalty = 0;
        if (newSeason.equalsIgnoreCase("Spring")) {
            seasonPenalty = 15;
        }
        quality -= seasonPenalty;
        normalize(quality);
        return quality;
    }
}
