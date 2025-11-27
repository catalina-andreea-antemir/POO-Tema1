package main.entities.Soil;

public class DesertSoil extends Soil {
    private static final double NITROGEN_MUL = 0.5;
    private static final double WATER_RET_MUL = 0.3;
    private static final int SALINITY_MUL = 2;
    private static final int HUNDRED = 100;

    public DesertSoil(final String name, final double mass) {
        //Superclass's constructor
        super(name, mass);
    }

    /**
     * Returns soil quality according to each soil type based on a formula
     * @return soilQuality
     */
    @Override
    protected double qualityScore() {
        double oxygen = getNitrogen() * NITROGEN_MUL;
        double water = getWaterRetention() * WATER_RET_MUL;
        double sal = getSalinity() * SALINITY_MUL;
        double score = oxygen + water - sal;
        return normalize(score);
    }

    /**
     * Calculates the attack probability based on a formula specific to each type
     * @return blockProbability
     */
    @Override
    protected double blockProbability() {
        return (HUNDRED - getWaterRetention() + getSalinity()) / HUNDRED * HUNDRED;
    }
}
