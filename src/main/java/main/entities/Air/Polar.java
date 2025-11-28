package main.entities.Air;

public class Polar extends Air {
    //Magic number fix
    private static final int MAX_SCORE = 142;
    private static final double ICE_MUL = 0.05;
    private static final int OXYGEN_MUL = 2;
    private static final double WIND_MUL = 0.2;
    private static final double HUNDRED = 100;

    public Polar(final String name, final double mass) {
        //Superclass's constructor
        super(name, mass);
    }

    /**
     * Calculates air quality according to each type of air
     * @return airQuality
     */
    @Override
    protected double airQuality() {
        double oxygen = getOxygenLevel() * OXYGEN_MUL;
        double temp = Math.abs(getTemperature());
        double ice = getIceCrystalConcentration() * ICE_MUL;
        double quality = oxygen + (HUNDRED - temp) - ice;
        return normalize(quality);
    }

    /**
     * Returns the max score for each type of air
     * @return maxScore
     */
    @Override
    protected int maxScore() {
        return MAX_SCORE;
    }

    /**
     * Calculates the temporary air quality based on a meteorological event
     * (windSpeed; the rest of the n-parameters don't have valid values)
     * @param rainfall field for Tropical Air
     * @param windSpeed field for Polar Air
     * @param newSeason field for Temperate Air
     * @param desertStorm field for Desert Air
     * @param numberOfHikers field for Mountain Air
     */
    @Override
    public void meteorologicalEvents(final double rainfall, final double windSpeed,
                                     final String newSeason, final boolean desertStorm,
                                     final int numberOfHikers) {
        double quality = airQuality();
        quality -= windSpeed * WIND_MUL;
        setTemporaryQuality(normalize(quality));
    }
}
