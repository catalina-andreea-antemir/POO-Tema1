package main.entities;
import main.entities.Animal;
import main.entities.Plant;
import main.entities.Water;
import main.entities.Soil;
import main.entities.Air;

public class TundraSoil extends Soil {
    public TundraSoil(String name, double mass) {
        super(name, mass);
    }

    @Override
    protected double qualityScore() {
        double score = (getNitrogen() * 0.7) + (getOrganicMatter() * 0.5) - (getPermafrostDepth() * 1.5);
        return normalize(score);
    }

    @Override
    protected double blockProbability() {
        return (50 - getPermafrostDepth()) / 50 * 100;
    }
}
