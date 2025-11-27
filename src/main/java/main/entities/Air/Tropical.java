package main.entities.Air;

public class Tropical extends Air {
    //Magic number fix
    private static final int MAX_SCORE = 82;
    private static final double HUMIDITY_MUL = 0.5;
    private static final int OXYGEN_MUL = 2;
    private static final double CO2_MUL = 0.01;
    private static final double RAINFALL_MUL = 0.3;

    public Tropical(final String name, final double mass) {
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
        double humidity = getHumidity() * HUMIDITY_MUL;
        double co2 = getCo2Level() * CO2_MUL;
        double quality = oxygen + humidity - co2;
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
     * (rainfall; the rest of the n-parameters don't have valid values)
     */
    @Override
    public void meteorologicalEvents(final double rainfall, final double windSpeed,
                                     final String newSeason, final boolean desertStorm,
                                     final int numberOfHikers) {
        double quality = airQuality();
        quality += rainfall * RAINFALL_MUL;
        setTemporaryQuality(normalize(quality));
    }
}
