package main.simulation;

import fileio.*;
import main.entities.Air.*;
import main.entities.Animal.*;
import main.entities.Plant.*;
import main.entities.Soil.*;
import main.entities.Water.*;
import main.entities.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import main.simulation.map.Cell;
import main.simulation.map.MapSimulator;
import main.simulation.robot.TerraBot;

import java.util.*;

public class Simulation {
    private int rows;
    private int cols;
    private int energyPoints;
    private MapSimulator map;
    private TerraBot bot;
    private List<CommandInput> commands;
    private boolean started;
    private InputLoader inputLoader;
    private int currentSimulationIndex;

    public Simulation(InputLoader input) {
        this.commands = input.getCommands();
        this.inputLoader = input;
        this.currentSimulationIndex = 0;
        initSimulation(0);
    }

    private void initSimulation(int currentSimIndex) {
        SimulationInput simulation = inputLoader.getSimulations().get(currentSimIndex);
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
                animal.setX(pair.getX());
                animal.setY(pair.getY());
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
        water.setPh(waterInput.getPH());
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
        int previousTimestamp = 0;

        for (CommandInput commandInput : commands) {
            if (started) {
                for (int t = previousTimestamp + 1; t <= commandInput.getTimestamp(); t++) {
                    entitiesInteractions(map, t);
                }
            }
            previousTimestamp = commandInput.getTimestamp();
            if (commandInput.getCommand().equals("startSimulation")) {
                if (started) {
                    ObjectNode error = objMapper.createObjectNode();
                    error.put("command", commandInput.getCommand());
                    error.put("message", "ERROR: Simulation already started. Cannot perform action");
                    error.put("timestamp", commandInput.getTimestamp());
                    out.add(error);
                } else {
                    initSimulation(currentSimulationIndex);
                    started = true;
                    ObjectNode start = objMapper.createObjectNode();
                    start.put("command", commandInput.getCommand());
                    start.put("message", "Simulation has started.");
                    start.put("timestamp", commandInput.getTimestamp());
                    out.add(start);
                }
            } else {
                if (!started) {
                    ObjectNode error = objMapper.createObjectNode();
                    error.put("command", commandInput.getCommand());
                    error.put("message", "ERROR: Simulation not started. Cannot perform action");
                    error.put("timestamp", commandInput.getTimestamp());
                    out.add(error);
                } else {
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
                    if (commandInput.getCommand().equals("printKnowledgeBase")) {
                        ObjectNode printBase = objMapper.createObjectNode();
                        printBase.put("command", commandInput.getCommand());
                        printBase.put("output", printKnowledgeBase(bot.getDatabase()));
                        printBase.put("timestamp", commandInput.getTimestamp());
                        out.add(printBase);
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
                    if (commandInput.getCommand().equals("scanObject")) {
                        ObjectNode scanObject = objMapper.createObjectNode();
                        scanObject.put("command", commandInput.getCommand());
                        scanObject.put("message", bot.scanObject(commandInput, map.getCell(bot.getX(), bot.getY())));
                        scanObject.put("timestamp", commandInput.getTimestamp());
                        out.add(scanObject);
                    }
                    if (commandInput.getCommand().equals("learnFact")) {
                        ObjectNode learnFact = objMapper.createObjectNode();
                        learnFact.put("command", commandInput.getCommand());
                        learnFact.put("message", bot.learnFact(commandInput.getSubject(), commandInput.getComponents()));
                        learnFact.put("timestamp", commandInput.getTimestamp());
                        out.add(learnFact);
                    }
                    if (commandInput.getCommand().equals("improveEnvironment")) {
                        ObjectNode improveEnv = objMapper.createObjectNode();
                        improveEnv.put("command", commandInput.getCommand());
                        String msg = improveEnvironment(bot, commandInput, map);
                        improveEnv.put("message", msg);
                        improveEnv.put("timestamp", commandInput.getTimestamp());
                        out.add(improveEnv);
                    }
                    if (commandInput.getCommand().equals("endSimulation")) {
                        ObjectNode end = objMapper.createObjectNode();
                        end.put("command", commandInput.getCommand());
                        end.put("message", "Simulation has ended.");
                        end.put("timestamp", commandInput.getTimestamp());
                        started = false;
                        currentSimulationIndex++;
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
        if (cell.getPlant() != null && !cell.getPlant().getIsDead()) {
            ObjectNode plant = objMapper.createObjectNode();
            plant.put("type", cell.getPlant().getType());
            plant.put("name", cell.getPlant().getName());
            plant.put("mass", cell.getPlant().getMass());
            out.set("plants", plant);
        }
        if (cell.getAnimal() != null && !cell.getAnimal().getIsDead()) {
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
                //rotunjim valoarea la doua zecimale
                double val = Math.round(cell.getAir().getCo2Level() * 100.0) / 100.0;
                cell.getAir().setCo2Level(val);
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

    //metoda pentru comanda "printKnowledgeBase"
    public ArrayNode printKnowledgeBase (Map<String, List<String>> database) {
        ObjectMapper objMapper = new ObjectMapper();
        ArrayNode out = objMapper.createArrayNode();

        //parcurgem cheile din hashmap
        for (String key : database.keySet()) {
            ObjectNode category = objMapper.createObjectNode();
            //afisam cheia curenta
            category.put("topic", key);
            //lista de facts pentru fiecare cheie
            ArrayNode values = objMapper.createArrayNode();
            for (String value : database.get(key)) {
                values.add(value);
            }
            //afisam lista
            category.set("facts", values);
            out.add(category);
        }
        return out;
    }

    public String improveEnvironment(TerraBot bot, CommandInput commandInput, MapSimulator map) {
        if (bot.getBattery() < 10) {
            return "ERROR: Not enough battery left. Cannot perform action";
        }

        String name = commandInput.getName();
        boolean hasItem = false;
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
        if (commandInput.getImprovementType().equals("plantVegetation")) {
            if (facts.contains("Method to plant " + name)) {
                if (map.getCell(bot.getX(), bot.getY()).getAir() != null) {
                    Air air = map.getCell(bot.getX(), bot.getY()).getAir();
                    double val = air.getOxygenLevel() + 0.3;
                    val = Math.round(val * 100.0) / 100.0;
                    air.setOxygenLevel(val);
                    bot.setBattery(bot.getBattery() - 10);
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

        if (commandInput.getImprovementType().equals("fertilizeSoil")) {
            if (facts.contains("Method to fertilize soil with " + name)) {
                if (map.getCell(bot.getX(), bot.getY()).getSoil() != null) {
                    Soil soil = map.getCell(bot.getX(), bot.getY()).getSoil();
                    double val = soil.getOrganicMatter() + 0.3;
                    val = Math.round(val * 100.0) / 100.0;
                    soil.setOrganicMatter(val);
                    bot.setBattery(bot.getBattery() - 10);
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

        if (commandInput.getImprovementType().equals("increaseHumidity")) {
            if (facts.contains("Method to increaseHumidity")) {
                if (map.getCell(bot.getX(), bot.getY()).getAir() != null) {
                    Air air = map.getCell(bot.getX(), bot.getY()).getAir();
                    double val = air.getHumidity() + 0.2;
                    val = Math.round(val * 100.0) / 100.0;
                    air.setHumidity(val);
                    bot.setBattery(bot.getBattery() - 10);
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

        if (commandInput.getImprovementType().equals("increaseMoisture")) {
            if (facts.contains("Method to increaseMoisture")) {
                if (map.getCell(bot.getX(), bot.getY()).getSoil() != null) {
                    Soil soil = map.getCell(bot.getX(), bot.getY()).getSoil();
                    double val = soil.getWaterRetention() + 0.3;
                    val = Math.round(val * 100.0) / 100.0;
                    soil.setWaterRetention(val);
                    bot.setBattery(bot.getBattery() - 10);
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
