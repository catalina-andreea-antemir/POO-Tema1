package main.entities;
import main.entities.Animal;
import main.entities.Plant;
import main.entities.Water;
import main.entities.Soil;
import main.entities.Air;

public class GrasslandSoil extends Soil {
    public GrasslandSoil(String name, double mass) {
        super(name, mass);
    }

    //returneaza calitatea solului in functie de fiecare tip de sol pe baza unei formule
    @Override
    protected double qualityScore() {
        double score = (getNitrogen() * 1.3) + (getOrganicMatter() * 1.5) + (getRootDensity() * 0.8);
        return normalize(score);
    }

    //calculeaza probabilitatea de atac pe baza unei formule specifice fiecarui tip
    @Override
    protected double blockProbability() {
        return ((50 - getRootDensity()) + getWaterRetention() * 0.5) / 75 * 100;
    }
}
