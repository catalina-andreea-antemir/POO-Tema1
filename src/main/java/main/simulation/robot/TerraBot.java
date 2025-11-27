package main.simulation.robot;

import fileio.CommandInput;
import main.simulation.map.Cell;
import main.simulation.map.MapSimulator;
import main.entities.Entities;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TerraBot {
    //Magic number fix
    private static final int INITIAL_POS = -1;
    private static final int MAX_INTEGER = 9999;
    private static final int NR_DIRECTIONS = 4;
    private static final int BATTERY_USAGE_SCAN = 7;
    private static final int BATTERY_USAGE_LEARN = 2;

    private int x;
    private int y;
    private int battery;
    private List<Entities> inventory;
    private Map<String, List<String>> database;

    public TerraBot(final int energyPoints) {
        this.x = 0;
        this.y = 0;
        this.battery = energyPoints;
        this.inventory = new ArrayList<>();
        this.database = new LinkedHashMap<>();
    }

    //Getter and Setter methods for private class fields
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
    public final int getBattery() {
        return this.battery;
    }
    public final void setBattery(final int battery) {
        this.battery = battery;
    }
    public final List<Entities> getInventory() {
        return this.inventory;
    }
    public final Map<String, List<String>> getDatabase() {
        return this.database;
    }
    public final void setDatabase(final Map<String, List<String>> database) {
        this.database = database;
    }

    /**
     * Method for ordering "moveRobot"
     * @return message for each case
     */
    public String moveRobot(final MapSimulator map) {
        int desiredX = INITIAL_POS, desiredY = INITIAL_POS, bestScore = MAX_INTEGER;
        int[] up = {this.x, this.y + 1};
        int[] right = {this.x + 1, this.y};
        int[] down = {this.x, this.y - 1};
        int[] left = {this.x - 1, this.y};
        int[][] dir = {up, right, down, left};

        //Check if the robot has enough battery
        if (this.battery <= 0) {
            return "ERROR: Not enough battery left. Cannot perform action";
        }
        for (int i = 0; i < NR_DIRECTIONS; i++) {
            //Checking if the current direction is within the matrix boundary
            if (dir[i][0] >= 0 && dir[i][0] < map.getCols()
                    && dir[i][1] >= 0 && dir[i][1] < map.getRows()) {
                double probSoil = 0.0;
                double probAir = 0.0;
                double probPlant = 0.0;
                double probAnimal = 0.0;
                int count = 0;
                Cell cell = map.getCell(dir[i][0], dir[i][1]);

                if (cell.getSoil() != null) {
                    probSoil = cell.getSoil().getBlockProbability();
                    count++;
                }
                if (cell.getAir() != null) {
                    probAir = cell.getAir().getToxicProbability();
                    count++;
                }
                if (cell.getPlant() != null) {
                    probPlant = cell.getPlant().getHangingProbability();
                    count++;
                }
                if (cell.getAnimal() != null) {
                    probAnimal = cell.getAnimal().getAttackProbability();
                    count++;
                }
                //Formulas
                double sum = probSoil + probAir + probPlant + probAnimal;
                double mean = Math.abs(sum / count);
                int result = (int) Math.round(mean);

                //it updates the best score and ideal direction until now
                if (result < bestScore) {
                    bestScore = result;
                    desiredX = dir[i][0];
                    desiredY = dir[i][1];
                }
            }
        }
        //Does the robot have enough battery to move?
        if (this.battery < bestScore) {
            return "ERROR: Not enough battery left. Cannot perform action";
        }
        //Moving the robot
        this.x = desiredX;
        this.y = desiredY;
        this.battery -= bestScore;
        return "The robot has successfully moved to position (" + this.x + ", " + this.y + ").";
    }

    /**
     * Method for ordering "scanObject"
     * @return message for each case
     */
    public String scanObject(final CommandInput commandInput, final Cell cell) {
        //The cost of moving is 7 energyPoints;
        //if the battery does not reach the robot does not scan
        if (this.battery < BATTERY_USAGE_SCAN) {
            return "ERROR: Not enough energy to perform action";
        }
        String object = "";
        String color = commandInput.getColor();
        String smell = commandInput.getSmell();
        String sound = commandInput.getSound();

        //water object (none none none)
        if (color.equals("none") && smell.equals("none") && sound.equals("none")) {
            object = "water";
            //plant object (!none !none none)
        } else if (!color.equals("none") && !smell.equals("none") && sound.equals("none")) {
            object = "plant";
            //animal object (!none !none !none)
        } else if (!color.equals("none") && !smell.equals("none") && !sound.equals("none")) {
            object = "animal";
        }

        //if he didn't scan the plant, he drops energyPoints and adds it to the inventory
        if (object.equals("plant") && cell.getPlant() != null
                && !cell.getPlant().getIsScanned()) {
            this.battery -= BATTERY_USAGE_SCAN;
            cell.getPlant().setIsScanned(true);
            inventory.add(cell.getPlant());
            return "The scanned object is a " + object + ".";
        }
        //if he hasn't scanned the plant, he drops energyPoints and adds it to the inventory
        if (object.equals("animal") && cell.getAnimal() != null
                && !cell.getAnimal().getIsScanned()) {
            this.battery -= BATTERY_USAGE_SCAN;
            cell.getAnimal().setIsScanned(true);
            cell.getAnimal().setExpirationTime(commandInput.getTimestamp());
            inventory.add(cell.getAnimal());
            return "The scanned object is an " + object + ".";
        }
        //if it hasn't scanned the water, it scans it, adds it to the input and drops energyPoints
        if (object.equals("water") && cell.getWater() != null
                && !cell.getWater().getIsScanned()) {
            this.battery -= BATTERY_USAGE_SCAN;
            cell.getWater().setIsScanned(true);
            cell.getWater().setAirExpirationTime(commandInput.getTimestamp());
            cell.getWater().setSoilExpirationTime(commandInput.getTimestamp());
            inventory.add(cell.getWater());
            return "The scanned object is " + object + ".";
        }
        return "ERROR: Object not found. Cannot perform action";
    }

    /**
     * Method for ordering "learnFact"
     * @return message for each case
     */
    public String learnFact(final String subject, final String components) {
        if (this.battery < BATTERY_USAGE_LEARN) {
            return "ERROR: Not enough battery left. Cannot perform action";
        }

        //verifying each element in the inventory
        for (int i = 0; i < inventory.size(); i++) {
            Entities entity = inventory.get(i);
            //checking if the entity is in the database
            if (entity.getName().equals(components)) {
                //if it is in the database, we add the fact to it's key
                if (database.containsKey(components)) {
                    database.get(components).add(subject);
                    this.battery -= BATTERY_USAGE_LEARN;
                    return "The fact has been successfully saved in the database.";
                } else {
                    //if it is not, wee add both the key (entity) and the value (fact)
                    database.put(components, new ArrayList<>());
                    database.get(components).add(subject);
                    this.battery -= BATTERY_USAGE_LEARN;
                    return "The fact has been successfully saved in the database.";
                }
            }
        }
        return "ERROR: Subject not yet saved. Cannot perform action";
    }
}
