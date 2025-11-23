package main.entities;
import main.entities.Animal;
import main.entities.Plant;
import main.entities.Water;
import main.entities.Soil;
import main.entities.Air;
import java.util.*;

public class Desert extends Air {
    public Desert(String name, double mass) {
        //apeleaza constructorul parinte
        super(name, mass);
    }

    //aplica formula specifica tipului de aer
    @Override
    protected double airQuality() {
        double quality = (getOxygenLevel() * 2) - (getDustParticles() * 0.2) - (getTemperature() * 0.3);
        return normalize(quality);
    }

    //returneaza scorul specific tipului de aer
    @Override
    protected int maxScore() {
        return 65;
    }

    //calculeaza calitatea aerului temporara pe baza unui eveniment meteorologic (desertStorm; restul cparametrilor n au velori valide)
    @Override
    public void meteorologicalEvents(double rainfall, double windSpeed, String newSeason, boolean desertStorm, int numberOfHikers) {
        double quality = airQuality();
        if (desertStorm) {
            quality -= 30;
        }
        setTemporaryQuality(normalize(quality));
    }
}
