package main.entities.Soil;

public class GrasslandSoil extends Soil {
    //Magic number fix
    private static final double NITROGEN_MUL = 1.3;
    private static final double ORGANIC_MUL = 1.5;
    private static final double WATER_RET_MUL = 0.5;
    private static final double ROOT_MUL = 0.8;
    private static final int HUNDRED = 100;
    private static final int SEVENTY_FIVE = 75;
    private static final int FIFTY = 50;

    public GrasslandSoil(final String name, final double mass) {
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
        double root = getRootDensity() * ROOT_MUL;
        double organic = getOrganicMatter() * ORGANIC_MUL;
        double score = nit + organic + root;
        return normalize(score);
    }

    /**
     * Calculates the attack probability based on a formula specific to each type
     * @return blockProbability
     */
    @Override
    protected double blockProbability() {
        double water = getWaterRetention() * WATER_RET_MUL;
        return ((FIFTY - getRootDensity()) + water) / SEVENTY_FIVE * HUNDRED;
    }
}
