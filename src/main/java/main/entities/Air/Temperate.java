package main.entities.Air;

public class Temperate extends Air {
    //Magic number fix
    private static final int OXYGEN_MUL = 2;
    private static final double HUMIDITY_MUL = 0.7;
    private static final double POLLEN_MUL = 0.1;
    private static final int MAX_SCORE = 84;
    private static final int SPRING_PENALTY = 15;

    public Temperate(final String name, final double mass) {
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
        double pollen = getPollenLevel() * POLLEN_MUL;
        double quality = oxygen + humidity - pollen;
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
     * (newSeason; the rest of the n-parameters don't have valid values)
     */
    @Override
    public void meteorologicalEvents(final double rainfall, final double windSpeed,
                                     final String newSeason, final boolean desertStorm,
                                     final int numberOfHikers) {
        double quality = airQuality();
        int seasonPenalty = 0;
        if (newSeason.equalsIgnoreCase("Spring")) {
            seasonPenalty = SPRING_PENALTY;
        }
        quality -= seasonPenalty;
        setTemporaryQuality(normalize(quality));
    }
}
