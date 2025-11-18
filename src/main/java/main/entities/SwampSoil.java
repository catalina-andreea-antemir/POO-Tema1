package main.entities;
import main.entities.Animal;
import main.entities.Plant;
import main.entities.Water;
import main.entities.Soil;
import main.entities.Air;
import java.util.*;

public class SwampSoil extends Soil {
    public SwampSoil(String name, double mass) {
        super(name, mass);
    }

    @Override
    protected double qualityScore() {
        double score = (nitrogen * 1.1) + (organicMatter * 2.2) - (waterLogging * 5);
        score = normalizeScore(score);
        return score;
    }

    @Override
    protected double blockProbability() {
        return waterLogging * 10;
    }
}
