package main.entities;
import main.entities.Animal;
import main.entities.Plant;
import main.entities.Water;
import main.entities.Soil;
import main.entities.Air;
import java.util.*;

public class Mountain extends Air {
    public Mountain(String name, double mass) {
        super(name, mass);
    }

    @Override
    protected double airQuality() {
        double oxygenFactor = getOxygenLevel() - (getAltitude() / 1000 * 0.5);
        double quality = (oxygenFactor * 2) + (getHumidity() * 0.6);
        normalize(quality);
        return quality;
    }

    @Override
    protected int maxScore() {
        return 78;
    }

    @Override
    protected double meteorologicalEvents(double rainfall, double windSpeed, String newSeason, boolean desertStorm, int numberOfHikers) {
        double quality = airQuality();
        quality -= numberOfHikers * 0.1;
        normalize(quality);
        return quality;
    }
}
