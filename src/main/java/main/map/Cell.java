package main.map;
import main.entities.Animal;
import main.entities.Plant;
import main.entities.Water;
import main.entities.Air;
import main.entities.Soil;

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

    //metode getter si setter pentru campul privat Animal
    public Animal getAnimal() {
        return this.animal;
    }
    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    //metode getter si setter pentru campul privat Plant
    public Plant getPlant() {
        return this.plant;
    }
    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    //metode getter si setter pentru campul privat Water
    public Water getWater() {
        return this.water;
    }
    public void setWater(Water water) {
        this.water = water;
    }

    //metode getter si setter pentru campul privat Soil
    public Soil getSoil() {
        return this.soil;
    }
    public void setSoil(Soil soil) {
        this.soil = soil;
    }

    //metode getter si setter pentru campul privat Air
    public Air getAir() {
        return this.air;
    }
    public void setAir(Air air) {
        this.air = air;
    }
}
