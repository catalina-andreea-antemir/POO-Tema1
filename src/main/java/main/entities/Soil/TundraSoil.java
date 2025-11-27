package main.entities.Soil;

public class TundraSoil extends Soil {
    //Magic number fix
    private static final double NITROGEN_MUL = 0.7;
    private static final double ORGANIC_MUL = 0.5;
    private static final double PERMAFROST_MUL = 1.5;
    private static final int FIFTY = 50;
    private static final int HUNDRED = 100;

    public TundraSoil(final String name, final double mass) {
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
        double permafrost = getPermafrostDepth() * PERMAFROST_MUL;
        double score = nit + organic - permafrost;
        return normalize(score);
    }

    /**
     * Calculates the attack probability based on a formula specific to each type
     * @return blockProbability
     */
    @Override
    protected double blockProbability() {
        return (FIFTY - getPermafrostDepth()) / FIFTY * HUNDRED;
    }
}
