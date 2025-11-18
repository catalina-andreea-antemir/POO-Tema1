package main.entities;
import main.entities.Animal;
import main.entities.Plant;
import main.entities.Water;
import main.entities.Soil;
import main.entities.Air;
import java.util.*;

public class DesertSoil extends Soil {
    public DesertSoil(String name, double mass) {
        super(name, mass);
    }

    @Override
    protected double qualityScore() {
        double score = (nitrogen * 0.5) + (waterRetention * 0.3) - (salinity * 2);
        score = normalizeScore(score);
        return score;
    }

    @Override
    protected double blockProbability() {
        return (100 - waterRetention + salinity) / 100 * 100;
    }
}
