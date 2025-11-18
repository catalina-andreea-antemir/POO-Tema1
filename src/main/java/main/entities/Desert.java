package main.entities;
import main.entities.Animal;
import main.entities.Plant;
import main.entities.Water;
import main.entities.Soil;
import main.entities.Air;
import java.util.*;

public class Desert extends Air {
    public Desert(String name, double mass) {
        super(name, mass);
    }

    @Override
    protected double airQuality() {
        double quality = (getOxygenLevel() * 2) - (getDustParticles() * 0.2) - (getTemperature() * 0.3);
        normalize(quality);
        return quality;
    }

    @Override
    protected int maxScore() {
        return 65;
    }

    @Override
    protected double meteorologicalEvents(double rainfall, double windSpeed, String newSeason, boolean desertStorm, int numberOfHikers) {
        double quality = airQuality();
        if (desertStorm) {
            quality -= 30;
        }
        normalize(quality);
        return quality;
    }
}
