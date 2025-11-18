package main.entities;
import main.entities.Animal;
import main.entities.Plant;
import main.entities.Water;
import main.entities.Soil;
import main.entities.Air;
import java.util.*;

public class TundraSoil extends Soil {
    public TundraSoil(String name, double mass) {
        super(name, mass);
    }

    @Override
    protected double qualityScore() {
        double score = (nitrogen * 0.7) + (organicMatter * 0.5) - (permafrostDepth * 1.5);
        score = normalizeScore(score);
        return score;
    }

    @Override
    protected double blockProbability() {
        return (50 - permafrostDepth) / 50 * 100;
    }
}
