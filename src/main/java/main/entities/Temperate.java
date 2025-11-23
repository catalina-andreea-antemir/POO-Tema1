package main.entities;
import main.entities.Animal;
import main.entities.Plant;
import main.entities.Water;
import main.entities.Soil;
import main.entities.Air;

public class Temperate extends Air {
    public Temperate(String name, double mass) {
        //apeleaza constructorul parinte
        super(name, mass);
    }

    //aplica formula specifica tipului de aer
    @Override
    protected double airQuality() {
        double quality = (getOxygenLevel() * 2) + (getHumidity() * 0.7) - (getPollenLevel() * 0.1);
        return normalize(quality);
    }

    //returneaza scorul specific tipului de aer
    @Override
    protected int maxScore() {
        return 84;
    }

    //calculeaza calitatea aerului temporara pe baza unui eveniment meteorologic (newSeason; restul cparametrilor n au velori valide)
    @Override
    public void meteorologicalEvents(double rainfall, double windSpeed, String newSeason, boolean desertStorm, int numberOfHikers) {
        double quality = airQuality();
        int seasonPenalty = 0;
        if (newSeason.equalsIgnoreCase("Spring")) {
            seasonPenalty = 15;
        }
        quality -= seasonPenalty;
        setTemporaryQuality(normalize(quality));
    }
}
