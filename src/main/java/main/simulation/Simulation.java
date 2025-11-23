package main.simulation;

import fileio.*;
import main.map.*;
import main.entities.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.*;

public class Simulation {
    private int rows;
    private int cols;
    private int energyPoints;
    private MapSimulator map;
    private TerraBot bot;
    private List<CommandInput> commands;
    private boolean started;

    public Simulation(InputLoader input) {
        SimulationInput simulation = input.getSimulations().get(0);
        this.commands = input.getCommands();
        String[] dims = simulation.getTerritoryDim().split("x");
        this.cols = Integer.parseInt(dims[0]);
        this.rows = Integer.parseInt(dims[1]);
        this.energyPoints = simulation.getEnergyPoints();
        this.map = new MapSimulator(cols, rows);
        this.bot = new TerraBot(this.energyPoints);
        this.started = false;

        populate(simulation.getTerritorySectionParams());
    }

    public void populate(TerritorySectionParamsInput params) {
        for (AnimalInput animalInput : params.getAnimals()) {
            for (PairInput pair : animalInput.getSections()) {
                Animal animal = initAnimal(animalInput);
                map.getCell(pair.getX(), pair.getY()).setAnimal(animal);
            }
        }
        for (PlantInput plantInput : params.getPlants()) {
            for (PairInput pair : plantInput.getSections()) {
                Plant plant = initPlant(plantInput);
                map.getCell(pair.getX(), pair.getY()).setPlant(plant);
            }
        }
        for (WaterInput waterInput : params.getWater()) {
            for (PairInput pair : waterInput.getSections()) {
                Water water = initWater(waterInput);
                map.getCell(pair.getX(), pair.getY()).setWater(water);
            }
        }
        for (SoilInput soilInput : params.getSoil()) {
            for (PairInput pair : soilInput.getSections()) {
                Soil soil = initSoil(soilInput);
                map.getCell(pair.getX(), pair.getY()).setSoil(soil);
            }
        }
        for (AirInput airInput : params.getAir()) {
            for (PairInput pair : airInput.getSections()) {
                Air air = initAir(airInput);
                map.getCell(pair.getX(), pair.getY()).setAir(air);
            }
        }
    }

    private Soil initSoil(SoilInput soil) {
        if (soil.getType().equals("ForestSoil")) {
            ForestSoil forestSoil = new ForestSoil(soil.getName(), soil.getMass());
            forestSoil.setType(soil.getType());
            forestSoil.setNitrogen(soil.getNitrogen());
            forestSoil.setWaterRetention(soil.getWaterRetention());
            forestSoil.setSoilpH(soil.getSoilpH());
            forestSoil.setOrganicMatter(soil.getOrganicMatter());
            forestSoil.setLeafLitter(soil.getLeafLitter());
            forestSoil.setWaterLogging(soil.getWaterLogging());
            forestSoil.setRootDensity(soil.getRootDensity());
            forestSoil.setPermafrostDepth(soil.getPermafrostDepth());
            forestSoil.setSalinity(soil.getSalinity());
            return forestSoil;
        }
        if (soil.getType().equals("GrasslandSoil")) {
            GrasslandSoil grasslandSoil = new GrasslandSoil(soil.getName(), soil.getMass());
            grasslandSoil.setType(soil.getType());
            grasslandSoil.setNitrogen(soil.getNitrogen());
            grasslandSoil.setWaterRetention(soil.getWaterRetention());
            grasslandSoil.setSoilpH(soil.getSoilpH());
            grasslandSoil.setOrganicMatter(soil.getOrganicMatter());
            grasslandSoil.setLeafLitter(soil.getLeafLitter());
            grasslandSoil.setWaterLogging(soil.getWaterLogging());
            grasslandSoil.setRootDensity(soil.getRootDensity());
            grasslandSoil.setPermafrostDepth(soil.getPermafrostDepth());
            grasslandSoil.setSalinity(soil.getSalinity());
            return grasslandSoil;
        }
        if (soil.getType().equals("SwampSoil")) {
            SwampSoil swampSoil = new SwampSoil(soil.getName(), soil.getMass());
            swampSoil.setType(soil.getType());
            swampSoil.setNitrogen(soil.getNitrogen());
            swampSoil.setWaterRetention(soil.getWaterRetention());
            swampSoil.setSoilpH(soil.getSoilpH());
            swampSoil.setOrganicMatter(soil.getOrganicMatter());
            swampSoil.setLeafLitter(soil.getLeafLitter());
            swampSoil.setWaterLogging(soil.getWaterLogging());
            swampSoil.setRootDensity(soil.getRootDensity());
            swampSoil.setPermafrostDepth(soil.getPermafrostDepth());
            swampSoil.setSalinity(soil.getSalinity());
            return swampSoil;
        }
        if (soil.getType().equals("DesertSoil")) {
            DesertSoil desertSoil = new DesertSoil(soil.getName(), soil.getMass());
            desertSoil.setType(soil.getType());
            desertSoil.setNitrogen(soil.getNitrogen());
            desertSoil.setWaterRetention(soil.getWaterRetention());
            desertSoil.setSoilpH(soil.getSoilpH());
            desertSoil.setOrganicMatter(soil.getOrganicMatter());
            desertSoil.setLeafLitter(soil.getLeafLitter());
            desertSoil.setWaterLogging(soil.getWaterLogging());
            desertSoil.setRootDensity(soil.getRootDensity());
            desertSoil.setPermafrostDepth(soil.getPermafrostDepth());
            desertSoil.setSalinity(soil.getSalinity());
            return desertSoil;
        }
        if (soil.getType().equals("TundraSoil")) {
            TundraSoil tundraSoil = new TundraSoil(soil.getName(), soil.getMass());
            tundraSoil.setType(soil.getType());
            tundraSoil.setNitrogen(soil.getNitrogen());
            tundraSoil.setWaterRetention(soil.getWaterRetention());
            tundraSoil.setSoilpH(soil.getSoilpH());
            tundraSoil.setOrganicMatter(soil.getOrganicMatter());
            tundraSoil.setLeafLitter(soil.getLeafLitter());
            tundraSoil.setWaterLogging(soil.getWaterLogging());
            tundraSoil.setRootDensity(soil.getRootDensity());
            tundraSoil.setPermafrostDepth(soil.getPermafrostDepth());
            tundraSoil.setSalinity(soil.getSalinity());
            return tundraSoil;
        }
        return null;
    }

    private Plant initPlant(PlantInput plant) {
        if (plant.getType().equals("FloweringPlants")) {
            FloweringPlants floweringPlants = new FloweringPlants(plant.getName(), plant.getMass());
            floweringPlants.setType(plant.getType());
            return floweringPlants;
        }
        if (plant.getType().equals("GymnospermsPlants")) {
            GymnospermsPlants gymnospermsPlants = new GymnospermsPlants(plant.getName(), plant.getMass());
            gymnospermsPlants.setType(plant.getType());
            return gymnospermsPlants;
        }
        if (plant.getType().equals("Ferns")) {
            Ferns ferns = new Ferns(plant.getName(), plant.getMass());
            ferns.setType(plant.getType());
            return ferns;
        }
        if (plant.getType().equals("Mosses")) {
            Mosses mosses = new Mosses(plant.getName(), plant.getMass());
            mosses.setType(plant.getType());
            return mosses;
        }
        if (plant.getType().equals("Algae")) {
            Algae algae = new Algae(plant.getName(), plant.getMass());
            algae.setType(plant.getType());
            return algae;
        }
        return null;
    }

    private Animal initAnimal(AnimalInput animal) {
        if (animal.getType().equals("Carnivores")) {
            Carnivores carnivores = new Carnivores(animal.getName(), animal.getMass());
            carnivores.setType(animal.getType());
            return carnivores;
        }
        if (animal.getType().equals("Herbivores")) {
            Herbivores herbivores = new Herbivores(animal.getName(), animal.getMass());
            herbivores.setType(animal.getType());
            return herbivores;
        }
        if (animal.getType().equals("Omnivores")) {
            Omnivores omnivores = new Omnivores(animal.getName(), animal.getMass());
            omnivores.setType(animal.getType());
            return omnivores;
        }
        if (animal.getType().equals("Detritivores")) {
            Detritivores detritivores = new Detritivores(animal.getName(), animal.getMass());
            detritivores.setType(animal.getType());
            return detritivores;
        }
        if (animal.getType().equals("Parasites")) {
            Parasites parasites = new Parasites(animal.getName(), animal.getMass());
            parasites.setType(animal.getType());
            return parasites;
        }
        return null;
    }

    private Water initWater(WaterInput waterInput) {
        Water water = new Water(waterInput.getName(), waterInput.getMass());
        water.setType(waterInput.getType());
        water.setSalinity(waterInput.getSalinity());
        water.setpH(waterInput.getPH());
        water.setPurity(waterInput.getPurity());
        water.setTurbidity(waterInput.getTurbidity());
        water.setContaminantIndex(waterInput.getContaminantIndex());
        water.setIsFrozen(waterInput.isFrozen());
        return water;
    }

    private Air initAir(AirInput air) {
        if (air.getType().equals("TemperateAir")) {
            Temperate temperate = new Temperate(air.getName(), air.getMass());
            temperate.setType(air.getType());
            temperate.setHumidity(air.getHumidity());
            temperate.setTemperature(air.getTemperature());
            temperate.setOxygenLevel(air.getOxygenLevel());
            temperate.setCo2Level(air.getCo2Level());
            temperate.setIceCrystalConcentration(air.getIceCrystalConcentration());
            temperate.setPollenLevel(air.getPollenLevel());
            temperate.setDustParticles(air.getDustParticles());
            temperate.setAltitude(air.getAltitude());
            return temperate;
        }
        if (air.getType().equals("TropicalAir")) {
            Tropical tropical = new Tropical(air.getName(), air.getMass());
            tropical.setType(air.getType());
            tropical.setHumidity(air.getHumidity());
            tropical.setTemperature(air.getTemperature());
            tropical.setOxygenLevel(air.getOxygenLevel());
            tropical.setCo2Level(air.getCo2Level());
            tropical.setIceCrystalConcentration(air.getIceCrystalConcentration());
            tropical.setPollenLevel(air.getPollenLevel());
            tropical.setDustParticles(air.getDustParticles());
            tropical.setAltitude(air.getAltitude());
            return tropical;
        }
        if (air.getType().equals("DesertAir")) {
            Desert desert = new Desert(air.getName(), air.getMass());
            desert.setType(air.getType());
            desert.setHumidity(air.getHumidity());
            desert.setTemperature(air.getTemperature());
            desert.setOxygenLevel(air.getOxygenLevel());
            desert.setCo2Level(air.getCo2Level());
            desert.setIceCrystalConcentration(air.getIceCrystalConcentration());
            desert.setPollenLevel(air.getPollenLevel());
            desert.setDustParticles(air.getDustParticles());
            desert.setAltitude(air.getAltitude());
            return desert;
        }
        if (air.getType().equals("MountainAir")) {
            Mountain mountain = new Mountain(air.getName(), air.getMass());
            mountain.setType(air.getType());
            mountain.setHumidity(air.getHumidity());
            mountain.setTemperature(air.getTemperature());
            mountain.setOxygenLevel(air.getOxygenLevel());
            mountain.setCo2Level(air.getCo2Level());
            mountain.setIceCrystalConcentration(air.getIceCrystalConcentration());
            mountain.setPollenLevel(air.getPollenLevel());
            mountain.setDustParticles(air.getDustParticles());
            mountain.setAltitude(air.getAltitude());
            return mountain;
        }
        if (air.getType().equals("PolarAir")) {
            Polar polar = new Polar(air.getName(), air.getMass());
            polar.setType(air.getType());
            polar.setHumidity(air.getHumidity());
            polar.setTemperature(air.getTemperature());
            polar.setOxygenLevel(air.getOxygenLevel());
            polar.setCo2Level(air.getCo2Level());
            polar.setIceCrystalConcentration(air.getIceCrystalConcentration());
            polar.setPollenLevel(air.getPollenLevel());
            polar.setDustParticles(air.getDustParticles());
            polar.setAltitude(air.getAltitude());
            return polar;
        }
        return null;
    }

    public ArrayNode runSimulation() {
        ObjectMapper objMapper = new ObjectMapper();
        ArrayNode out = objMapper.createArrayNode();
        int botCharging = -1;

        for (CommandInput commandInput : commands) {
            if (commandInput.getCommand().equals("startSimulation")) {
                started = true;
                ObjectNode start = objMapper.createObjectNode();
                start.put("command", commandInput.getCommand());
                start.put("message", "Simulation has started.");
                start.put("timestamp", commandInput.getTimestamp());
                out.add(start);
            } else {
                if (!started) {
                    ObjectNode error = objMapper.createObjectNode();
                    error.put("command", commandInput.getCommand());
                    error.put("message", "ERROR: Simulation not started. Cannot perform action");
                    error.put("timestamp", commandInput.getTimestamp());
                    out.add(error);
                } else {
                    verifyAirQualityChanging(map, commandInput);
                    if (botCharging > commandInput.getTimestamp()) {
                        ObjectNode error = objMapper.createObjectNode();
                        error.put("command", commandInput.getCommand());
                        error.put("message", "ERROR: Robot still charging. Cannot perform action");
                        error.put("timestamp", commandInput.getTimestamp());
                        out.add(error);
                        continue;
                    }
                    if (commandInput.getCommand().equals("printEnvConditions")) {
                        ObjectNode printEnv = objMapper.createObjectNode();
                        printEnv.put("command", commandInput.getCommand());
                        printEnv.put("output", envCond(map.getCell(bot.getX(), bot.getY()), commandInput));
                        printEnv.put("timestamp", commandInput.getTimestamp());
                        out.add(printEnv);
                    }
                    if (commandInput.getCommand().equals("printMap")) {
                        ObjectNode printMap = objMapper.createObjectNode();
                        printMap.put("command", commandInput.getCommand());
                        printMap.put("output", map.mapPrint());
                        printMap.put("timestamp", commandInput.getTimestamp());
                        out.add(printMap);
                    }
                    if (commandInput.getCommand().equals("moveRobot")) {
                        ObjectNode moveRobot = objMapper.createObjectNode();
                        moveRobot.put("command", commandInput.getCommand());
                        moveRobot.put("message", bot.moveRobot(map));
                        moveRobot.put("timestamp", commandInput.getTimestamp());
                        out.add(moveRobot);
                    }
                    if (commandInput.getCommand().equals("rechargeBattery")) {
                        ObjectNode rechargeBattery = objMapper.createObjectNode();
                        int timeToCharge = commandInput.getTimeToCharge();
                        rechargeBattery.put("command", commandInput.getCommand());
                        bot.setBattery(bot.getBattery() + timeToCharge);
                        botCharging = commandInput.getTimestamp() + timeToCharge;
                        rechargeBattery.put("message", "Robot battery is charging.");
                        rechargeBattery.put("timestamp", commandInput.getTimestamp());
                        out.add(rechargeBattery);
                    }
                    if (commandInput.getCommand().equals("getEnergyStatus")) {
                        ObjectNode getEnergyStatus = objMapper.createObjectNode();
                        getEnergyStatus.put("command", commandInput.getCommand());
                        getEnergyStatus.put("message", "TerraBot has " + bot.getBattery() + " energy points left.");
                        getEnergyStatus.put("timestamp", commandInput.getTimestamp());
                        out.add(getEnergyStatus);
                    }
                    if (commandInput.getCommand().equals("changeWeatherConditions")) {
                        ObjectNode changeConditions = objMapper.createObjectNode();
                        changeConditions.put("command", commandInput.getCommand());
                        changeConditions.put("message", changeWeatherConditions(map, commandInput));
                        changeConditions.put("timestamp", commandInput.getTimestamp());
                        out.add(changeConditions);
                    }
                    if (commandInput.getCommand().equals("endSimulation")) {
                        ObjectNode end = objMapper.createObjectNode();
                        end.put("command", commandInput.getCommand());
                        end.put("message", "Simulation has ended.");
                        end.put("timestamp", commandInput.getTimestamp());
                        started = false;
                        out.add(end);
                    }
                }
            }
        }
        return out;
    }

    public ObjectNode envCond(Cell cell, CommandInput commandInput) {
        ObjectMapper objMapper = new ObjectMapper();
        ObjectNode out = objMapper.createObjectNode();
        boolean desertStorm = false;
        if (cell.getAir().getExpirationTime() > commandInput.getTimestamp()) {
            desertStorm = true;
        }

        if (cell.getSoil() != null) {
            ObjectNode soil = objMapper.createObjectNode();
            soil.put("type", cell.getSoil().getType());
            soil.put("name", cell.getSoil().getName());
            soil.put("mass", cell.getSoil().getMass());
            soil.put("nitrogen", cell.getSoil().getNitrogen());
            soil.put("waterRetention", cell.getSoil().getWaterRetention());
            soil.put("soilpH", cell.getSoil().getSoilpH());
            soil.put("organicMatter", cell.getSoil().getOrganicMatter());
            soil.put("soilQuality", cell.getSoil().getQuality());
            if (cell.getSoil().getType().equals("ForestSoil")) {
                soil.put("leafLitter", cell.getSoil().getLeafLitter());
            }
            if (cell.getSoil().getType().equals("SwampSoil")) {
                soil.put("waterLogging", cell.getSoil().getWaterLogging());
            }
            if (cell.getSoil().getType().equals("DesertSoil")) {
                soil.put("salinity", cell.getSoil().getSalinity());
            }
            if (cell.getSoil().getType().equals("GrasslandSoil")) {
                soil.put("rootDensity", cell.getSoil().getRootDensity());
            }
            if (cell.getSoil().getType().equals("TundraSoil")) {
                soil.put("permafrostDepth", cell.getSoil().getPermafrostDepth());
            }
            out.set("soil", soil);
        }
        if (cell.getPlant() != null) {
            ObjectNode plant = objMapper.createObjectNode();
            plant.put("type", cell.getPlant().getType());
            plant.put("name", cell.getPlant().getName());
            plant.put("mass", cell.getPlant().getMass());
            out.set("plants", plant);
        }
        if (cell.getAnimal() != null) {
            ObjectNode animal = objMapper.createObjectNode();
            animal.put("type", cell.getAnimal().getType());
            animal.put("name", cell.getAnimal().getName());
            animal.put("mass", cell.getAnimal().getMass());
            out.set("animals", animal);
        }
        if (cell.getWater() != null) {
            ObjectNode water = objMapper.createObjectNode();
            water.put("type", cell.getWater().getType());
            water.put("name", cell.getWater().getName());
            water.put("mass", cell.getWater().getMass());
            out.set("water", water);
        }
        if (cell.getAir() != null) {
            ObjectNode air = objMapper.createObjectNode();
            air.put("type", cell.getAir().getType());
            air.put("name", cell.getAir().getName());
            air.put("mass", cell.getAir().getMass());
            air.put("humidity", cell.getAir().getHumidity());
            air.put("temperature", cell.getAir().getTemperature());
            air.put("oxygenLevel", cell.getAir().getOxygenLevel());
            air.put("airQuality", cell.getAir().getQuality());
            if (cell.getAir().getType().equals("TropicalAir")) {
                air.put("co2Level", cell.getAir().getCo2Level());
            }
            if (cell.getAir().getType().equals("PolarAir")) {
                air.put("iceCrystalConcentration", cell.getAir().getIceCrystalConcentration());
            }
            if (cell.getAir().getType().equals("TemperateAir")) {
                air.put("pollenLevel", cell.getAir().getPollenLevel());
            }
            if (cell.getAir().getType().equals("DesertAir")) {
                air.put("desertStorm", desertStorm);
            }
            if (cell.getAir().getType().equals("MountainAir")) {
                air.put("altitude", cell.getAir().getAltitude());
            }
            out.set("air", air);
        }
        return out;
    }

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

    public void verifyAirQualityChanging(MapSimulator map, CommandInput commandInput) {
        for (int i = 0; i < map.getCols(); i++) {
            for (int j = 0; j < map.getRows(); j++) {
                Cell cell = map.getCell(i, j);
                if (cell.getAir() != null) {
                    if (commandInput.getTimestamp() >= cell.getAir().getExpirationTime()) {
                        cell.getAir().setTemporaryQuality(-1.0);
                    }
                }
            }
        }
    }
}
