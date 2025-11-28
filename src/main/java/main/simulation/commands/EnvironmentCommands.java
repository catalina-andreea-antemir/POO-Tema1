package main.simulation.commands;

import fileio.CommandInput;
import main.entities.Animal.Animal;
import main.entities.Plant.Plant;
import main.entities.Water.Water;
import main.simulation.map.Cell;
import main.simulation.map.MapSimulator;

import java.util.ArrayList;
import java.util.List;

public class EnvironmentCommands {
    /**
     * Method for "changeWeatherConditions" command
     * @param map simulation map
     * @param command commandInput to access necessary fields
     * @return message
     */
    public String changeWeatherConditions(final MapSimulator map, final CommandInput command) {
        String type = command.getType();
        String msg = "ERROR: The weather change does not affect the environment. "
                + "Cannot perform action";
        for (int i = 0; i < map.getCols(); i++) {
            for (int j = 0; j < map.getRows(); j++) {
                Cell cell = map.getCell(i, j);
                if (cell.getAir() != null) {
                    if (type.equals("desertStorm")) {
                        if (cell.getAir().getType().equals("DesertAir")) {
                            boolean desertStorm = command.isDesertStorm();
                            cell.getAir().meteorologicalEvents(-1.0, -1.0, null, desertStorm, -1);
                            cell.getAir().setExpirationTime(command.getTimestamp());
                            msg = "The weather has changed.";
                        }
                    } else if (type.equals("peopleHiking")) {
                        if (cell.getAir().getType().equals("MountainAir")) {
                            int nrHikers = command.getNumberOfHikers();
                            cell.getAir().meteorologicalEvents(-1.0, -1.0, null, false, nrHikers);
                            cell.getAir().setExpirationTime(command.getTimestamp());
                            msg = "The weather has changed.";
                        }
                    } else if (type.equals("newSeason")) {
                        if (cell.getAir().getType().equals("TemperateAir")) {
                            String season = command.getSeason();
                            cell.getAir().meteorologicalEvents(-1.0, -1.0, season, false, -1);
                            cell.getAir().setExpirationTime(command.getTimestamp());
                            msg = "The weather has changed.";
                        }
                    } else if (type.equals("polarStorm")) {
                        if (cell.getAir().getType().equals("PolarAir")) {
                            double windSpeed = command.getWindSpeed();
                            cell.getAir().meteorologicalEvents(-1.0, windSpeed, null, false, -1);
                            cell.getAir().setExpirationTime(command.getTimestamp());
                            msg = "The weather has changed.";
                        }
                    } else if (type.equals("rainfall")) {
                        if (cell.getAir().getType().equals("TropicalAir")) {
                            double rainfall = command.getRainfall();
                            cell.getAir().meteorologicalEvents(rainfall, -1.0, null, false, -1);
                            cell.getAir().setExpirationTime(command.getTimestamp());
                            msg = "The weather has changed.";
                        }
                    }
                }
            }
        }
        return msg;
    }

    /**
     * Method for the interactions between entities
     * @param map simulation map
     * @param timestamp current timestamp
     */
    public void entitiesInteractions(final MapSimulator map, final int timestamp) {
        //iterate through every column and row of the map
        for (int i = 0; i < map.getCols(); i++) {
            for (int j = 0; j < map.getRows(); j++) {
                Cell cell = map.getCell(i, j);
                //if the entities are dead and still on the map, we erase them
                if (cell.getPlant() != null && cell.getPlant().getIsDead()) {
                    cell.setPlant(null);
                }
                if (cell.getAnimal() != null && cell.getAnimal().getIsDead()) {
                    cell.setAnimal(null);
                }
                if (cell.getWater() != null && cell.getWater().getMass() <= 0) {
                    cell.setWater(null);
                }
                //interactions with air
                if (cell.getAir() != null) {
                    //if the time of 2 iterations expired, the temporary quality is reset
                    if (timestamp >= cell.getAir().getExpirationTime()) {
                        cell.getAir().setTemporaryQuality(-1.0);
                    }
                    if (cell.getAnimal() != null) {
                        cell.getAir().interactionAnimal(cell.getAnimal());
                    }
                }
                //interactions with soil
                if (cell.getSoil() != null && cell.getPlant() != null) {
                    if (cell.getPlant().getIsScanned() && !cell.getPlant().getIsDead()) {
                        cell.getSoil().interactionPlant(cell.getPlant());
                    }
                }
                //interactions with water
                if (cell.getWater() != null && cell.getWater().getIsScanned()) {
                    //the interaction with air is available for 2 iterations
                    if (cell.getAir() != null) {
                        if (timestamp >= cell.getWater().getAirExpirationTime()) {
                            cell.getWater().interactionAir(cell.getAir());
                            cell.getWater().setAirExpirationTime(timestamp);
                        }
                    }
                    //the interaction with soil is available for 2 iterations
                    if (cell.getSoil() != null) {
                        if (timestamp >= cell.getWater().getSoilExpirationTime()) {
                            cell.getWater().interactionSoil(cell.getSoil());
                            cell.getWater().setSoilExpirationTime(timestamp);
                        }
                    }
                    //the interaction with plant happens each iteration
                    if (cell.getPlant() != null) {
                        if (cell.getPlant().getIsScanned() && !cell.getPlant().getIsDead()) {
                            cell.getWater().interactionPlant(cell.getPlant());
                        }
                    }
                }
                //interactions with plant
                if (cell.getPlant() != null && cell.getAir() != null) {
                    cell.getPlant().interactionAir(cell.getAir());
                }
                //iteration with animal
                if (cell.getAnimal() != null) {
                    if (cell.getAnimal().getIsScanned() && !cell.getAnimal().getIsDead()) {
                        Plant plant = null;
                        Water water = null;
                        if (cell.getPlant() != null && cell.getPlant().getIsScanned()) {
                            plant = cell.getPlant();
                        }
                        if (cell.getWater() != null && cell.getWater().getIsScanned()) {
                            water = cell.getWater();
                        }
                        //at first, we consider the animal nor being Carnivore/Parasite
                        cell.getAnimal().animalEats(null, plant, water);
                        if (cell.getSoil() != null && cell.getAnimal().canProduceFertilizer()) {
                            cell.getAnimal().interactionSoil(cell.getSoil());
                        }
                    }
                }
                //if the entities died during interactions we erase them from the map
                if (cell.getPlant() != null && cell.getPlant().getIsDead()) {
                    cell.setPlant(null);
                }
                if (cell.getAnimal() != null && cell.getAnimal().getIsDead()) {
                    cell.setAnimal(null);
                }
                if (cell.getWater() != null && cell.getWater().getMass() <= 0) {
                    cell.setWater(null);
                }
            }
        }
        //moving the animal at every 2 iterations
        List<Animal> animalsToMove = new ArrayList<>();
        //making a list with the animals that need to be moved
        for (int i = 0; i < map.getCols(); i++) {
            for (int j = 0; j < map.getRows(); j++) {
                Cell cell = map.getCell(i, j);
                //if the animal exists in the cell, we add it to the list
                if (cell.getAnimal() != null) {
                    if (cell.getAnimal().getIsScanned() && !cell.getAnimal().getIsDead()) {
                        animalsToMove.add(cell.getAnimal());
                    }
                }
            }
        }
        //we move the animals
        for (Animal animal : animalsToMove) {
            animal.moveAnimal(map, timestamp);
        }
    }
}
