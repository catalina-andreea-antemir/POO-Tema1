package main.entities;
import main.entities.Animal;
import main.entities.Plant;
import main.entities.Water;
import main.entities.Soil;
import main.entities.Air;
import java.util.*;

public class ForestSoil extends Soil {
    public ForestSoil(String name, double mass) {
        super(name, mass);
    }

    @Override
    protected double qualityScore() {
        double score = (getNitrogen() * 1.2) + (getOrganicMatter() * 2) + (getWaterRetention() * 1.5) + (getLeafLitter() * 0.3);
        score = normalize(score);
        return score;
    }

    @Override
    protected double blockProbability() {
        return (getWaterRetention() * 0.6 + getLeafLitter() * 0.4) / 80 * 100;
    }
}
