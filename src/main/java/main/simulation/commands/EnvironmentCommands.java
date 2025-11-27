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
    public String changeWeatherConditions(MapSimulator map, CommandInput commandInput) {
        String type = commandInput.getType();
        String message = "ERROR: The weather change does not affect the environment. Cannot perform action";

        for (int i = 0; i < map.getCols(); i++) {
            for (int j = 0; j < map.getRows(); j++) {
                Cell cell = map.getCell(i, j);
                if (cell.getAir() != null) {
                    if (type.equals("desertStorm")) {
                        if (cell.getAir().getType().equals("DesertAir")) {
                            boolean desertStorm = commandInput.isDesertStorm();
                            cell.getAir().meteorologicalEvents(-1.0, -1.0, null, desertStorm, -1);
                            cell.getAir().setExpirationTime(commandInput.getTimestamp());
                            message = "The weather has changed.";
                        }
                    } else if (type.equals("peopleHiking")) {
                        if (cell.getAir().getType().equals("MountainAir")) {
                            int numberOfHikers = commandInput.getNumberOfHikers();
                            cell.getAir().meteorologicalEvents(-1.0, -1.0, null, false, numberOfHikers);
                            cell.getAir().setExpirationTime(commandInput.getTimestamp());
                            message = "The weather has changed.";
                        }
                    } else if (type.equals("newSeason")) {
                        if (cell.getAir().getType().equals("TemperateAir")) {
                            String season = commandInput.getSeason();
                            cell.getAir().meteorologicalEvents(-1.0, -1.0, season, false, -1);
                            cell.getAir().setExpirationTime(commandInput.getTimestamp());
                            message = "The weather has changed.";
                        }
                    } else if (type.equals("polarStorm")) {
                        if (cell.getAir().getType().equals("PolarAir")) {
                            double windSpeed = commandInput.getWindSpeed();
                            cell.getAir().meteorologicalEvents(-1.0, windSpeed, null, false, -1);
                            cell.getAir().setExpirationTime(commandInput.getTimestamp());
                            message = "The weather has changed.";
                        }
                    } else if (type.equals("rainfall")) {
                        if (cell.getAir().getType().equals("TropicalAir")) {
                            double rainfall = commandInput.getRainfall();
                            cell.getAir().meteorologicalEvents(rainfall, -1.0, null, false, -1);
                            cell.getAir().setExpirationTime(commandInput.getTimestamp());
                            message = "The weather has changed.";
                        }
                    }
                }
            }
        }
        return message;
    }

    public void entitiesInteractions(MapSimulator map, int timestamp) {
        for (int i = 0; i < map.getCols(); i++) {
            for (int j = 0; j < map.getRows(); j++) {
                Cell cell = map.getCell(i, j);
                if (cell.getPlant() != null && cell.getPlant().getIsDead()) {
                    cell.setPlant(null);
                }
                if (cell.getAnimal() != null && cell.getAnimal().getIsDead()) {
                    cell.setAnimal(null);
                }
                if (cell.getWater() != null && cell.getWater().getMass() <= 0) {
                    cell.setWater(null);
                }
                if (cell.getAir() != null) {
                    if (timestamp >= cell.getAir().getExpirationTime()) {
                        cell.getAir().setTemporaryQuality(-1.0);
                    }
                    if (cell.getAnimal() != null) {
                        cell.getAir().interactionAnimal(cell.getAnimal());
                    }
                }
                if (cell.getSoil() != null && cell.getPlant() != null) {
                    if (cell.getPlant().getIsScanned() && !cell.getPlant().getIsDead()) {
                        cell.getSoil().interactionPlant(cell.getPlant());
                    }
                }
                if (cell.getWater() != null && cell.getWater().getIsScanned()) {
                    if (cell.getAir() != null) {
                        if (timestamp >= cell.getWater().getAirExpirationTime()) {
                            cell.getWater().interactionAir(cell.getAir());
                            cell.getWater().setAirExpirationTime(timestamp);
                        }
                    }
                    if (cell.getSoil() != null) {
                        if (timestamp >= cell.getWater().getSoilExpirationTime()) {
                            cell.getWater().interactionSoil(cell.getSoil());
                            cell.getWater().setSoilExpirationTime(timestamp);
                        }
                    }
                    if (cell.getPlant() != null && cell.getPlant().getIsScanned() && !cell.getPlant().getIsDead()) {
                        cell.getWater().interactionPlant(cell.getPlant());
                    }
                }
                if (cell.getPlant() != null && cell.getAir() != null) {
                    cell.getPlant().interactionAir(cell.getAir());
                }
                if (cell.getAnimal() != null && cell.getAnimal().getIsScanned() && !cell.getAnimal().getIsDead()) {
                    Plant plant = null;
                    Water water = null;
                    if (cell.getPlant() != null && cell.getPlant().getIsScanned()) {
                        plant = cell.getPlant();
                    }
                    if (cell.getWater() != null && cell.getWater().getIsScanned()) {
                        water = cell.getWater();
                    }
                    cell.getAnimal().animalEats(null, plant, water);
                    if (cell.getSoil() != null && cell.getAnimal().canProduceFertilizer()) {
                        cell.getAnimal().interactionSoil(cell.getSoil());
                    }
                }
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
        List<Animal> animalsToMove = new ArrayList<>();
        for (int i = 0; i < map.getCols(); i++) {
            for (int j = 0; j < map.getRows(); j++) {
                Cell cell = map.getCell(i, j);
                if (cell.getAnimal() != null && cell.getAnimal().getIsScanned() && !cell.getAnimal().getIsDead()) {
                    animalsToMove.add(cell.getAnimal());
                }
            }
        }
        for (Animal animal : animalsToMove) {
            animal.moveAnimal(map, timestamp);
        }
    }
}
