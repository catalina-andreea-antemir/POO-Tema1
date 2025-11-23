package main.entities;
import main.entities.Animal;
import main.entities.Plant;
import main.entities.Water;
import main.entities.Soil;
import main.entities.Air;

public class Mountain extends Air {
    public Mountain(String name, double mass) {
        //apeleaza constructorul parinte
        super(name, mass);
    }

    //aplica formula specifica tipului de aer
    @Override
    protected double airQuality() {
        double oxygenFactor = getOxygenLevel() - (getAltitude() / 1000 * 0.5);
        double quality = (oxygenFactor * 2) + (getHumidity() * 0.6);
        return normalize(quality);
    }

    //returneaza scorul specific tipului de aer
    @Override
    protected int maxScore() {
        return 78;
    }

    //calculeaza calitatea aerului temporara pe baza unui eveniment meteorologic (numberOfHikers; restul cparametrilor n au velori valide)
    @Override
    public void meteorologicalEvents(double rainfall, double windSpeed, String newSeason, boolean desertStorm, int numberOfHikers) {
        double quality = airQuality();
        quality -= numberOfHikers * 0.1;
        setTemporaryQuality(normalize(quality));
    }
}
