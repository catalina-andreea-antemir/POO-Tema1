package main.simulation;

import fileio.TerritorySectionParamsInput;
import fileio.AirInput;
import fileio.AnimalInput;
import fileio.PlantInput;
import fileio.WaterInput;
import fileio.SoilInput;
import fileio.PairInput;

import main.entities.Air.Air;
import main.entities.Air.Mountain;
import main.entities.Air.Desert;
import main.entities.Air.Polar;
import main.entities.Air.Temperate;
import main.entities.Air.Tropical;

import main.entities.Animal.Animal;
import main.entities.Animal.Carnivores;
import main.entities.Animal.Herbivores;
import main.entities.Animal.Omnivores;
import main.entities.Animal.Detritivores;
import main.entities.Animal.Parasites;

import main.entities.Plant.Plant;
import main.entities.Plant.FloweringPlants;
import main.entities.Plant.Ferns;
import main.entities.Plant.GymnospermsPlants;
import main.entities.Plant.Mosses;
import main.entities.Plant.Algae;

import main.entities.Soil.Soil;
import main.entities.Soil.DesertSoil;
import main.entities.Soil.TundraSoil;
import main.entities.Soil.ForestSoil;
import main.entities.Soil.SwampSoil;
import main.entities.Soil.GrasslandSoil;

import main.entities.Water.Water;

import main.simulation.map.MapSimulator;

public class ReaderSimulation {
    public void populate(MapSimulator map, TerritorySectionParamsInput params) {
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
}
