package main.entities.Air;

public class Desert extends Air {
    //Magic number fix
    private static final double TEMP_MUL = 0.3;
    private static final double DUST_MUL = 0.2;
    private static final int OXYGEN_MUL = 2;
    private static final int MAX_SCORE = 65;
    private static final int QUALITY_DEC = 30;

    public Desert(final String name, final double mass) {
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
        double dust = getDustParticles() * DUST_MUL;
        double temp = getTemperature() * TEMP_MUL;
        double quality = oxygen - dust - temp;
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
     * (desertStorm; the rest of the n-parameters don't have valid values)
     */
    @Override
    public void meteorologicalEvents(final double rainfall, final double windSpeed,
                                     final String newSeason, final boolean desertStorm,
                                     final int numberOfHikers) {
        double quality = airQuality();
        if (desertStorm) {
            quality -= QUALITY_DEC;
        }
        setTemporaryQuality(normalize(quality));
    }
}
