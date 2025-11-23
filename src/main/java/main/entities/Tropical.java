package main.entities;
import main.entities.Animal;
import main.entities.Plant;
import main.entities.Water;
import main.entities.Soil;
import main.entities.Air;
import java.util.*;

public class Tropical extends Air {
    public Tropical(String name, double mass) {
        //apeleaza constructorul parinte
        super(name, mass);
    }

    //aplica formula specifica tipului de aer
    @Override
    protected double airQuality() {
        double quality = (getOxygenLevel() * 2) + (getHumidity() * 0.5) - (getCo2Level() * 0.01);
        return normalize(quality);
    }

    //returneaza scorul specific tipului de aer
    @Override
    protected int maxScore() {
        return 82;
    }

    //calculeaza calitatea aerului temporara pe baza unui eveniment meteorologic (rainfall; restul cparametrilor n au velori valide)
    @Override
    public void meteorologicalEvents(double rainfall, double windSpeed, String newSeason, boolean desertStorm, int numberOfHikers) {
        double quality = airQuality();
        quality += rainfall * 0.3;
        setTemporaryQuality(normalize(quality));
    }
}
