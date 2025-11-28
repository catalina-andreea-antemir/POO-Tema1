package main.simulation.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CommandInput;
import main.simulation.map.Cell;
import main.simulation.map.MapSimulator;

import java.util.List;
import java.util.Map;

public class DebugCommands {
    //Magic number fix
    private static final double NORMALIZE = 100.0;

    /**
     * Method for "printEnvConditions" command
     * @param cell the current cell
     * @param commandInput for necessary fields
     * @return the output
     */
    public ObjectNode envCond(final Cell cell, final CommandInput commandInput) {
        ObjectMapper objMapper = new ObjectMapper();
        ObjectNode out = objMapper.createObjectNode();
        boolean desertStorm = false;
        //printing the desertStorm field for the temporary quality
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
                //normalizing value
                double val = Math.round(cell.getAir().getCo2Level() * NORMALIZE) / NORMALIZE;
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

    /**
     * Method fot "printMap" command
     * @param map simulation map
     * @return output node
     */
    public ArrayNode mapPrint(final MapSimulator map) {
        ObjectMapper objMapper = new ObjectMapper();
        ArrayNode out = objMapper.createArrayNode();
        //iterating through simulation map
        for (int j = 0; j < map.getRows(); j++) {
            for (int i = 0; i < map.getCols(); i++) {
                Cell cell = map.getCell(i, j);
                ObjectNode node = objMapper.createObjectNode();
                ArrayNode section = objMapper.createArrayNode();
                section.add(i);
                section.add(j);
                //printing coordinates for each map cell
                node.set("section", section);
                //counting how many entities are in the current cell
                int numberObj = 0;
                if (cell.getAnimal() != null) {
                    if (!cell.getAnimal().getIsDead()) {
                        numberObj++;
                    }
                }
                if (cell.getPlant() != null) {
                    if (!cell.getPlant().getIsDead()) {
                        numberObj++;
                    }
                }
                if (cell.getWater() != null) {
                    numberObj++;
                }

                //printing the number of entities in the cell
                node.put("totalNrOfObjects", numberObj);
                //printing air quality
                if (cell.getAir() != null) {
                    node.put("airQuality", cell.getAir().qualityLabel());
                }
                //printing soil quality
                if (cell.getSoil() != null) {
                    node.put("soilQuality", cell.getSoil().qualityLabel());
                }
                out.add(node);
            }
        }
        return out;
    }

    /**
     * Method for "printKnowledgeBase" command
     * @param database database of the robot
     * @return output node
     */
    public ArrayNode printKnowledgeBase(final Map<String, List<String>> database) {
        ObjectMapper objMapper = new ObjectMapper();
        ArrayNode out = objMapper.createArrayNode();

        //iterating through hashmap's keys
        for (String key : database.keySet()) {
            ObjectNode category = objMapper.createObjectNode();
            //printing the current key
            category.put("topic", key);
            //list of facts of each key
            ArrayNode values = objMapper.createArrayNode();
            for (String value : database.get(key)) {
                values.add(value);
            }
            //printing the list of facts
            category.set("facts", values);
            out.add(category);
        }
        return out;
    }
}
