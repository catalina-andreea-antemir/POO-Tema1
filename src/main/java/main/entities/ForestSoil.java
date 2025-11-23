package main.entities;
import main.entities.Animal;
import main.entities.Plant;
import main.entities.Water;
import main.entities.Soil;
import main.entities.Air;

public class ForestSoil extends Soil {
    public ForestSoil(String name, double mass) {
        super(name, mass);
    }

    //returneaza calitatea solului in functie de fiecare tip de sol pe baza unei formule
    @Override
    protected double qualityScore() {
        double score = (getNitrogen() * 1.2) + (getOrganicMatter() * 2) + (getWaterRetention() * 1.5) + (getLeafLitter() * 0.3);
        return normalize(score);
    }

    //calculeaza probabilitatea de atac pe baza unei formule specifice fiecarui tip
    @Override
    protected double blockProbability() {
        return (getWaterRetention() * 0.6 + getLeafLitter() * 0.4) / 80 * 100;
    }
}
