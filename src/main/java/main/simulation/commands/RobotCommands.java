package main.simulation.commands;

import fileio.CommandInput;
import main.entities.Air.Air;
import main.entities.Entities;
import main.entities.Soil.Soil;
import main.simulation.map.Cell;
import main.simulation.map.MapSimulator;
import main.simulation.robot.TerraBot;

import java.util.ArrayList;
import java.util.List;

public class RobotCommands {
    //Magic number fix
    private static final int INITIAL_POS = -1;
    private static final int MAX_INTEGER = 9999;
    private static final int NR_DIRECTIONS = 4;
    private static final int BATTERY_USAGE_SCAN = 7;
    private static final int BATTERY_USAGE_LEARN = 2;
    private static final int BATTERY_USAGE_IMPROVE = 10;
    private static final double OXYGEN_MUL = 0.3;
    private static final double ORGANIC_MUL = 0.3;
    private static final double WATER_MUL = 0.3;
    private static final double HUMIDITY_MUL = 0.2;
    private static final double NORMALIZE = 100.0;

    /**
     * Method for command "moveRobot"
     * @param bot the robot
     * @param map the simulation map
     * @return message for each case
     */
    public String moveRobot(final TerraBot bot, final MapSimulator map) {
        int desiredX = INITIAL_POS, desiredY = INITIAL_POS, bestScore = MAX_INTEGER;
        int[] up = {bot.getX(), bot.getY() + 1};
        int[] right = {bot.getX() + 1, bot.getY()};
        int[] down = {bot.getX(), bot.getY() - 1};
        int[] left = {bot.getX() - 1, bot.getY()};
        int[][] dir = {up, right, down, left};

        //Check if the robot has enough battery
        if (bot.getBattery() <= 0) {
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
        if (bot.getBattery() < bestScore) {
            return "ERROR: Not enough battery left. Cannot perform action";
        }
        //Moving the robot
        bot.setX(desiredX);
        bot.setY(desiredY);
        bot.setBattery(bot.getBattery() - bestScore);
        int x = bot.getX();
        int y = bot.getY();
        return "The robot has successfully moved to position (" + x + ", " + y + ").";
    }

    /**
     * Method for command "scanObject"
     * @param bot the robot
     * @param command the commandInput for accessing the necessary fields
     * @param cell the current cell
     * @return message for each case
     */
    public String scanObject(final TerraBot bot, final CommandInput command, final Cell cell) {
        //The cost of moving is 7 energyPoints;
        //if the battery does not reach the robot does not scan
        if (bot.getBattery() < BATTERY_USAGE_SCAN) {
            return "ERROR: Not enough energy to perform action";
        }
        String object = "";
        String color = command.getColor();
        String smell = command.getSmell();
        String sound = command.getSound();

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
            bot.setBattery(bot.getBattery() - BATTERY_USAGE_SCAN);
            cell.getPlant().setIsScanned(true);
            bot.getInventory().add(cell.getPlant());
            return "The scanned object is a " + object + ".";
        }
        //if he hasn't scanned the plant, he drops energyPoints and adds it to the inventory
        if (object.equals("animal") && cell.getAnimal() != null
                && !cell.getAnimal().getIsScanned()) {
            bot.setBattery(bot.getBattery() - BATTERY_USAGE_SCAN);
            cell.getAnimal().setIsScanned(true);
            cell.getAnimal().setExpirationTime(command.getTimestamp());
            bot.getInventory().add(cell.getAnimal());
            return "The scanned object is an " + object + ".";
        }
        //if it hasn't scanned the water, it scans it, adds it to the input and drops energyPoints
        if (object.equals("water") && cell.getWater() != null
                && !cell.getWater().getIsScanned()) {
            bot.setBattery(bot.getBattery() - BATTERY_USAGE_SCAN);
            cell.getWater().setIsScanned(true);
            cell.getWater().setAirExpirationTime(command.getTimestamp());
            cell.getWater().setSoilExpirationTime(command.getTimestamp());
            bot.getInventory().add(cell.getWater());
            return "The scanned object is " + object + ".";
        }
        return "ERROR: Object not found. Cannot perform action";
    }

    /**
     * Method for command "learnFact"
     * @param bot the robot
     * @param subject the key in hashmap (entity name)
     * @param components the facts that the robot learns
     * @return message for each case
     */
    public String learnFact(final TerraBot bot, final String subject, final String components) {
        if (bot.getBattery() < BATTERY_USAGE_LEARN) {
            return "ERROR: Not enough battery left. Cannot perform action";
        }
        //verifying each element in the inventory
        for (int i = 0; i < bot.getInventory().size(); i++) {
            Entities entity = bot.getInventory().get(i);
            //checking if the entity is in the database
            if (entity.getName().equals(components)) {
                //if it is in the database, we add the fact to it's key
                if (bot.getDatabase().containsKey(components)) {
                    bot.getDatabase().get(components).add(subject);
                    bot.setBattery(bot.getBattery() - BATTERY_USAGE_LEARN);
                    return "The fact has been successfully saved in the database.";
                } else {
                    //if it is not, wee add both the key (entity) and the value (fact)
                    bot.getDatabase().put(components, new ArrayList<>());
                    bot.getDatabase().get(components).add(subject);
                    bot.setBattery(bot.getBattery() - BATTERY_USAGE_LEARN);
                    return "The fact has been successfully saved in the database.";
                }
            }
        }
        return "ERROR: Subject not yet saved. Cannot perform action";
    }

    /**
     * Method for command improveEnvironment
     * @param bot the robot
     * @param comm the commandInput for accessing necessary fields
     * @param map the simulation map
     * @return the right message
     */
    public String improveEnv(final TerraBot bot, final CommandInput comm, final MapSimulator map) {
        if (bot.getBattery() < BATTERY_USAGE_IMPROVE) {
            return "ERROR: Not enough battery left. Cannot perform action";
        }

        String name = comm.getName();
        boolean hasItem = false; //flag for item in inventory
        List<Entities> inventory = bot.getInventory();
        for (Entities e : inventory) {
            if (e.getName().equals(name)) {
                hasItem = true;
                break;
            }
        }
        if (!hasItem) {
            return "ERROR: Subject not yet saved. Cannot perform action";
        }
        if (!bot.getDatabase().containsKey(name)) {
            return "ERROR: Fact not yet saved. Cannot perform action";
        }
        List<String> facts = bot.getDatabase().get(name);
        if (comm.getImprovementType().equals("plantVegetation")) {
            //if the fact is there
            if (facts.contains("Method to plant " + name)) {
                if (map.getCell(bot.getX(), bot.getY()).getAir() != null) {
                    Air air = map.getCell(bot.getX(), bot.getY()).getAir();
                    //normalizing value
                    double val = air.getOxygenLevel() + OXYGEN_MUL;
                    val = Math.round(val * NORMALIZE) / NORMALIZE;
                    air.setOxygenLevel(val);
                    bot.setBattery(bot.getBattery() - BATTERY_USAGE_IMPROVE);
                    //removing the subject from the inventory
                    for (int i = 0; i < inventory.size(); i++) {
                        if (inventory.get(i).getName().equals(name)) {
                            inventory.remove(i);
                            break;
                        }
                    }
                    return "The " + name + " was planted successfully.";
                }
            } else {
                return "ERROR: Fact not yet saved. Cannot perform action";
            }
        }

        if (comm.getImprovementType().equals("fertilizeSoil")) {
            //if the fact is there
            if (facts.contains("Method to fertilize soil with " + name)) {
                if (map.getCell(bot.getX(), bot.getY()).getSoil() != null) {
                    Soil soil = map.getCell(bot.getX(), bot.getY()).getSoil();
                    //normalizing value
                    double val = soil.getOrganicMatter() + ORGANIC_MUL;
                    val = Math.round(val * NORMALIZE) / NORMALIZE;
                    soil.setOrganicMatter(val);
                    bot.setBattery(bot.getBattery() - BATTERY_USAGE_IMPROVE);
                    //removing the subject from the inventory
                    for (int i = 0; i < inventory.size(); i++) {
                        if (inventory.get(i).getName().equals(name)) {
                            inventory.remove(i);
                            break;
                        }
                    }
                    return "The soil was successfully fertilized using " + name;
                }
            } else {
                return "ERROR: Fact not yet saved. Cannot perform action";
            }
        }

        if (comm.getImprovementType().equals("increaseHumidity")) {
            //if the fact is there
            if (facts.contains("Method to increaseHumidity")) {
                if (map.getCell(bot.getX(), bot.getY()).getAir() != null) {
                    Air air = map.getCell(bot.getX(), bot.getY()).getAir();
                    //normalizing value
                    double val = air.getHumidity() + HUMIDITY_MUL;
                    val = Math.round(val * NORMALIZE) / NORMALIZE;
                    air.setHumidity(val);
                    bot.setBattery(bot.getBattery() - BATTERY_USAGE_IMPROVE);
                    //removing the subject from the inventory
                    for (int i = 0; i < inventory.size(); i++) {
                        if (inventory.get(i).getName().equals(name)) {
                            inventory.remove(i);
                            break;
                        }
                    }
                    return "The air was successfully increased using " + name;
                }
            } else {
                return "ERROR: Fact not yet saved. Cannot perform action";
            }
        }

        if (comm.getImprovementType().equals("increaseMoisture")) {
            //if the fact is there
            if (facts.contains("Method to increaseMoisture")) {
                if (map.getCell(bot.getX(), bot.getY()).getSoil() != null) {
                    Soil soil = map.getCell(bot.getX(), bot.getY()).getSoil();
                    //normalizing value
                    double val = soil.getWaterRetention() + WATER_MUL;
                    val = Math.round(val * NORMALIZE) / NORMALIZE;
                    soil.setWaterRetention(val);
                    bot.setBattery(bot.getBattery() - BATTERY_USAGE_IMPROVE);
                    //removing the subject from the inventory
                    for (int i = 0; i < inventory.size(); i++) {
                        if (inventory.get(i).getName().equals(name)) {
                            inventory.remove(i);
                            break;
                        }
                    }
                    return "The moisture was successfully increased using " + name;
                }
            } else {
                return "ERROR: Fact not yet saved. Cannot perform action";
            }
        }
        return "ERROR: Subject not yet saved. Cannot perform action";
    }
}
