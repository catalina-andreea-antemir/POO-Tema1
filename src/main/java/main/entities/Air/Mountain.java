package main.entities.Air;

public class Mountain extends Air {
    //Magic number fix
    private static final int ALTITUDE_DIV = 1000;
    private static final double ALTITUDE_MUL = 0.5;
    private static final int OXYGEN_MUL = 2;
    private static final double HUMIDITY_MUL = 0.6;
    private static final int MAX_SCORE = 78;
    private static final double HIKERS_MUL = 0.1;

    public Mountain(final String name, final double mass) {
        //Superclass's constructor
        super(name, mass);
    }

    /**
     * Calculates air quality according to each type of air
     * @return airQuality
     */
    @Override
    protected double airQuality() {
        double altitude = getAltitude() / ALTITUDE_DIV * ALTITUDE_MUL;
        double oxygenFactor = getOxygenLevel() - altitude;
        double quality = (oxygenFactor * OXYGEN_MUL) + (getHumidity() * HUMIDITY_MUL);
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
     * (numberOfHikers; the rest of the n-parameters don't have valid values)
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
        quality -= numberOfHikers * HIKERS_MUL;
        setTemporaryQuality(normalize(quality));
    }
}
