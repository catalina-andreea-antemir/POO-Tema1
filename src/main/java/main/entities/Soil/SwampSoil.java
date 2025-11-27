package main.entities.Soil;

public class SwampSoil extends Soil {
    //Magic number fix
    private static final double NITROGEN_MUL = 1.1;
    private static final double ORGANIC_MUL = 2.2;
    private static final int WATER_LOG_MUL1 = 5;
    private static final int WATER_LOG_MUL2 = 10;

    public SwampSoil(final String name, final double mass) {
        //Superclass's constructor
        super(name, mass);
    }

    /**
     * Returns soil quality according to each soil type based on a formula
     * @return soilQuality
     */
    @Override
    protected double qualityScore() {
        double nit = getNitrogen() * NITROGEN_MUL;
        double organic = getOrganicMatter() * ORGANIC_MUL;
        double water = getWaterLogging() * WATER_LOG_MUL1;
        double score = nit + organic - water;
        return normalize(score);
    }

    /**
     * Calculates the attack probability based on a formula specific to each type
     * @return blockProbability
     */
    @Override
    protected double blockProbability() {
        return getWaterLogging() * WATER_LOG_MUL2;
    }
}
