package main.entities;
import main.entities.Animal;
import main.entities.Plant;
import main.entities.Water;
import main.entities.Soil;
import main.entities.Air;

public class SwampSoil extends Soil {
    public SwampSoil(String name, double mass) {
        super(name, mass);
    }

    @Override
    protected double qualityScore() {
        double score = (getNitrogen() * 1.1) + (getOrganicMatter() * 2.2) - (getWaterLogging() * 5);
        return normalize(score);
    }

    @Override
    protected double blockProbability() {
        return getWaterLogging() * 10;
    }
}
