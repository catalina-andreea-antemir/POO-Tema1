package main.entities.Animal;

import main.entities.Entities;
import main.entities.Plant.Plant;
import main.entities.Soil.Soil;
import main.entities.Water.Water;
import main.simulation.map.Cell;
import main.simulation.map.MapSimulator;

public abstract class Animal extends Entities {
    //Magic numbers fix
    private static final double DEFAULT_INTAKE_RATE = 0.08;
    private static final int EXPIRATION_TIME = -2;
    private static final int TWO_ITERATIONS = 2;
    private static final int NR_DIRECTIONS = 4;

    private String type; //animal type (Carnivore, Herbivore, Parasite, Detritivore, Omnivore)
    private String status; //animal status (Hungry, Well-Fed, Sick)
    private final double intakeRate; //max rate of water drunk
    private boolean isScanned; //true if animal is scanned by the robot
    private boolean isDead; //true if the animal died
    private double organicMetter; //amount of fertilizer
    private int x; //col position
    private int y; //row position
    private int expirationTime; //moving at every 2 iterations

    public Animal(final String name, final double mass) {
        super(name, mass); //Superclass's constructor
        this.type = null;
        this.status = "Hungry";
        this.intakeRate = DEFAULT_INTAKE_RATE;
        this.isScanned = false;
        this.isDead = false;
        this.organicMetter = 0.0;
        this.x = -1;
        this.y = -1;
        this.expirationTime = EXPIRATION_TIME;
    }

    //Getter and Setter methods for private class fields
    public final String getType() {
        return this.type;
    }
    public final void setType(final String type) {
        this.type = type;
    }
    public final String getStatus() {
        return this.status;
    }
    public final void setStatus(final String status) {
        this.status = status;
    }
    public final double getIntakeRate() {
        return this.intakeRate;
    }
    public final boolean getIsScanned() {
        return this.isScanned;
    }
    public final void setIsScanned(final boolean isScanned) {
        this.isScanned = isScanned;
    }
    public final boolean getIsDead() {
        return this.isDead;
    }
    public final void setIsDead(final boolean isDead) {
        this.isDead = isDead;
    }
    public final void setOrganicMetter(final double organicMetter) {
        this.organicMetter = organicMetter;
    }
    public final int getX() {
        return this.x;
    }
    public final void setX(final int x) {
        this.x = x;
    }
    public final int getY() {
        return this.y;
    }
    public final void setY(final int y) {
        this.y = y;
    }
    public final void setExpirationTime(final int currentTime) {
        this.expirationTime = currentTime + 2;
    }

    /**
     * Check if the animal can produce fertilizer
     */
    public boolean canProduceFertilizer() {
        if (this.status.equals("Well-Fed")) {
            return true;
        }
        return false;
    }

    /**
     * Returns the probability of attack based on the type of animal
     * @return attackProbability
     */
    protected abstract double attackProbability();

    /**
     * Because attackProbability is protected, the access to it is made by this method
     * @return attackProbability
     */
    public double getAttackProbability() {
        return attackProbability();
    }

    /**
     * Calculate organicMatter according to how many entities the animal consumes
     * Soil Interaction
     */
    public abstract void animalEats(Animal prey, Plant plant, Water water);

    /**
     * Moving the animal to feed
     */
    public void moveAnimal(final MapSimulator map, final int timestamp) {
        if (!this.isScanned) {
            return;
        }
        if (timestamp < this.expirationTime) {
            return;
        }
        int desiredX = -1, desiredY = -1;
        double bestWaterQuality = -1.0;
        int[] up = {this.x, this.y + 1};
        int[] right = {this.x + 1, this.y};
        int[] down = {this.x, this.y - 1};
        int[] left = {this.x - 1, this.y};
        int[][] dir = {up, right, down, left}; //Directions array
        int both = 0, plant = 0, water = 0;

        for (int i = 0; i < NR_DIRECTIONS; i++) {
            if (dir[i][0] >= 0 && dir[i][0] < map.getCols()
                    && dir[i][1] >= 0 && dir[i][1] < map.getRows()) {
                Cell cell = map.getCell(dir[i][0], dir[i][1]);
                //If there is a plant and water in the cell
                if (cell.getPlant() != null && cell.getWater() != null
                        && cell.getPlant().getIsScanned() && cell.getWater().getIsScanned()) {
                    //If it did not find a cell like or found it but
                    //there is one with better water quality
                    if (cell.getWater().waterQuality() > bestWaterQuality || both == 0) {
                        bestWaterQuality = cell.getWater().waterQuality();
                        desiredX = dir[i][0];
                        desiredY = dir[i][1];
                        both = 1;
                    }
                    //If there is only a plant in the cell and there is not found
                    // one with the plant AND water
                } else if (cell.getPlant() != null && cell.getPlant().getIsScanned()
                        && both == 0) {
                    //If it did not find another one just with the plant
                    if (plant == 0) {
                        desiredX = dir[i][0];
                        desiredY = dir[i][1];
                        plant = 1;
                    }
                    //If there is only water in the cell, and it did not find one with plant
                    //AND water or one with the plant
                } else if (cell.getWater() != null && cell.getWater().getIsScanned()
                        && both == 0 && plant == 0) {
                    //If it did not find another one only with water or if found but exists
                    //one with better water quality
                    if (water == 0 || cell.getWater().waterQuality() > bestWaterQuality) {
                        desiredX = dir[i][0];
                        desiredY = dir[i][1];
                        water = 1;
                    }
                    //If no cell is found, the robot moves to the first check
                } else if (both == 0 && plant == 0 && water == 0) {
                    if (desiredX == -1 && desiredY == -1) {
                        desiredX = dir[i][0];
                        desiredY = dir[i][1];
                    }
                }
            }
        }
        //Update the position of the animal on the map
        if (desiredX != -1 && desiredY != -1) {
            Cell targetCell = map.getCell(desiredX, desiredY);
            //Looking for another animal in the cell
            Animal targetAnimal = targetCell.getAnimal();
            //If it exists and the animal moving is Carnivore / Parasite (animalEats)
            //it will eat the one already existing in the cell
            if (targetAnimal != null) {
                this.animalEats(targetAnimal, null, null);
                if (targetAnimal.getIsDead()) {
                    map.getCell(this.x, this.y).setAnimal(null);
                    this.x = desiredX;
                    this.y = desiredY;
                    targetCell.setAnimal(this);
                    this.expirationTime = timestamp + TWO_ITERATIONS;
                }
                //If not, just updating the position
            } else {
                map.getCell(this.x, this.y).setAnimal(null);
                this.x = desiredX;
                this.y = desiredY;
                targetCell.setAnimal(this);
                this.expirationTime = timestamp + TWO_ITERATIONS;
            }
        }
    }

    /**
     * Interaction Animal - Soil (if the animal can produce fertilizer, the
     * organicMetter of the soil grows)
     */
    public void interactionSoil(final Soil soil) {
        if (soil != null && getIsScanned() && canProduceFertilizer()) {
            soil.setOrganicMatter(soil.getOrganicMatter() + organicMetter);
        }
    }
}
