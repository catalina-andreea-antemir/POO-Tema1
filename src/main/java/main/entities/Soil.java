package main.entities;
import main.entities.Plant;
import main.entities.Animal;
import main.entities.Water;
import main.entities.Air;
import fileio.SoilInput;
import java.util.*;

public abstract class Soil {
    protected String name;
    protected double mass;
    protected String type;
    protected double nitrogen;
    protected double waterRetention;
    protected double soilpH;
    protected double organicMatter;
    protected double leafLitter;
    protected double waterLogging;
    protected double rootDensity;
    protected double permafrostDepth;
    protected double salinity;

    public Soil(String name, double mass) {
        this.name = name;
        this.mass = mass;
        this.type = null;
        this.nitrogen = 0.0;
        this.waterRetention = 0.0;
        this.soilpH = 0.0;
        this.organicMatter = 0.0;
        this.leafLitter = 0.0;
        this.waterLogging = 0.0;
        this.rootDensity = 0.0;
        this.permafrostDepth = 0.0;
        this.salinity = 0.0;
    }

    //FA GETTERI SI SETTERI IN CAZ CA O SA AI NEVOIE PT CAMPURILE PRIVATE

    protected double normalizeScore(double score) {
        double normalized = Math.max(0, Math.min(100, score));
        score = Math.round(normalized * 100.0) / 100.0;
        return score;
    }

    protected abstract double qualityScore();
    protected abstract double blockProbability();

    public String getQuality() {
        double quality = qualityScore();
        if (quality >= 70.0 && quality <= 100.0) {
            return "Good";
        }
        if (quality >= 40.0 && quality < 70.0) {
            return "Moderate";
        }
        return "Poor";
    }

    void addWaterRetention(double waterRetention, int iterations) {
        this.waterRetention += waterRetention * iterations;
    }
}
