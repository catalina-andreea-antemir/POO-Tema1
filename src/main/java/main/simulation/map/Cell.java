package main.simulation.map;
import main.entities.Animal.Animal;
import main.entities.Plant.Plant;
import main.entities.Water.Water;
import main.entities.Air.Air;
import main.entities.Soil.Soil;

public class Cell {
    private Animal animal;
    private Plant plant;
    private Water water;
    private Air air;
    private Soil soil;

    public Cell() {
        this.animal = null;
        this.plant = null;
        this.water = null;
        this.air = null;
        this.soil = null;
    }

    //Getter and Setter methods for private class fields
    public final Animal getAnimal() {
        return this.animal;
    }
    public final void setAnimal(final Animal animal) {
        this.animal = animal;
    }
    public final Plant getPlant() {
        return this.plant;
    }
    public final void setPlant(final Plant plant) {
        this.plant = plant;
    }
    public final Water getWater() {
        return this.water;
    }
    public final void setWater(final Water water) {
        this.water = water;
    }
    public final Soil getSoil() {
        return this.soil;
    }
    public final void setSoil(final Soil soil) {
        this.soil = soil;
    }
    public final Air getAir() {
        return this.air;
    }
    public final void setAir(final Air air) {
        this.air = air;
    }
}
