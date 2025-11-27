package main.entities.Soil;

public class ForestSoil extends Soil {
    //Magic number fix
    private static final double NITROGEN_MUL = 1.2;
    private static final double WATER_RET_MUL1 = 1.5;
    private static final double WATER_RET_MUL2 = 0.6;
    private static final double LEAF_MUL1 = 0.3;
    private static final double LEAF_MUL2 = 0.4;
    private static final int ORGANIC_MUL = 2;
    private static final int HUNDRED = 100;
    private static final int EIGHTY = 80;

    public ForestSoil(final String name, final double mass) {
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
        double water = getWaterRetention() * WATER_RET_MUL1;
        double leaf = getLeafLitter() * LEAF_MUL1;
        double score = nit + organic + water + leaf;
        return normalize(score);
    }

    /**
     * Calculates the attack probability based on a formula specific to each type
     * @return blockProbability
     */
    @Override
    protected double blockProbability() {
        double water = getWaterRetention() * WATER_RET_MUL2;
        double leaf = getLeafLitter() * LEAF_MUL2;
        return (water + leaf) / EIGHTY * HUNDRED;
    }
}
