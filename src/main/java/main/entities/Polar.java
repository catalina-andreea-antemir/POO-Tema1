package main.entities;
import main.entities.Animal;
import main.entities.Plant;
import main.entities.Water;
import main.entities.Soil;
import main.entities.Air;

public class Polar extends Air {
    public Polar(String name, double mass) {
        //apeleaza constructorul parinte
        super(name, mass);
    }

    //aplica formula specifica tipului de aer
    @Override
    protected double airQuality() {
        double quality = (getOxygenLevel() * 2) + (100 - Math.abs(getTemperature())) - (getIceCrystalConcentration() * 0.05);
        return normalize(quality);
    }

    //returneaza scorul specific tipului de aer
    @Override
    protected int maxScore() {
        return 142;
    }

    //calculeaza calitatea aerului temporara pe baza unui eveniment meteorologic (windSpeed; restul cparametrilor n au velori valide)
    @Override
    public void meteorologicalEvents(double rainfall, double windSpeed, String newSeason, boolean desertStorm, int numberOfHikers) {
        double quality = airQuality();
        quality -= windSpeed * 0.2;
        setTemporaryQuality(normalize(quality));
    }
}
