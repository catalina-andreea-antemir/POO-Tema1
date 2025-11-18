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
        double score = (getNitrogen() * 0.5) + (getWaterRetention() * 0.3) - (getSalinity() * 2);
        score = normalize(score);
        return score;
    }

    @Override
    protected double blockProbability() {
        return (100 - getWaterRetention() + getSalinity()) / 100 * 100;
    }
}
