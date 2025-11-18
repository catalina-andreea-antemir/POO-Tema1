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
        double score = (nitrogen * 1.2) + (organicMatter * 2) + (waterRetention * 1.5) + (leafLitter * 0.3);
        score = normalizeScore(score);
        return score;
    }

    @Override
    protected double blockProbability() {
        return (waterRetention * 0.6 + leafLitter * 0.4) / 80 * 100;
    }
}
