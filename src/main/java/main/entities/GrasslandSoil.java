package main.entities;
import main.entities.Animal;
import main.entities.Plant;
import main.entities.Water;
import main.entities.Soil;
import main.entities.Air;
import java.util.*;

public class GrasslandSoil extends Soil {
    public GrasslandSoil(String name, double mass) {
        super(name, mass);
    }

    @Override
    protected double qualityScore() {
        double score = (nitrogen * 1.3) + (organicMatter * 1.5) + (rootDensity * 0.8);
        score = normalizeScore(score);
        return score;
    }

    @Override
    protected double blockProbability() {
        return ((50 - rootDensity) + waterRetention * 0.5) / 75 * 100;
    }
}
