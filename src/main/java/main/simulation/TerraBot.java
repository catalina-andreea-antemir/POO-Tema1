package main.simulation;

import fileio.CommandInput;
import main.map.*;
import main.simulation.*;
import main.entities.*;
import java.util.*;

public class TerraBot {
    private int x;
    private int y;
    private int battery;
    private List<Entities> inventory;

    public TerraBot(int energyPoints) {
        this.x = 0;
        this.y = 0;
        this.battery = energyPoints;
        this.inventory = new ArrayList<>();
    }

    //metode getter si setter pentru compul privat x
    public int getX() {
        return this.x;
    }
    public void setX(int x) {
        this.x = x;
    }

    //metode getter si setter pentru campul privat y
    public int getY() {
        return this.y;
    }
    public void setY(int y) {
        this.y = y;
    }

    //metoda getter si setter pentru campul privat battery
    public int getBattery() {
        return this.battery;
    }
    public void setBattery(int battery) {
        this.battery = battery;
    }

    //metode getter si setter pentru campul private inventory
    public List<Entities> getInventory() {
        return this.inventory;
    }
    public void setInventory(List<Entities> inventory) {
        this.inventory = inventory;
    }

    //metoda pentru comanda "moveRobot"
    public String moveRobot(MapSimulator map) {
        int desiredX = -1, desiredY = -1, bestScore = 9999;
        //SUS
        int[] up = {this.x, this.y + 1};
        //DREAPTA
        int[] right = {this.x + 1, this.y};
        //JOS
        int[] down = {this.x, this.y - 1};
        //STANGA
        int[] left = {this.x - 1, this.y};
        //vector de directii
        int[][] dir = {up, right, down, left};

        //se verifica daca robotul are destula baterie
        if (this.battery <= 0) {
            return "ERROR: Not enough battery left. Cannot perform action";
        }
        for (int i = 0; i < 4; i++) {
            //verific daca directia surenta e in limitele matricei
            if (dir[i][0] >= 0 && dir[i][0] < map.getCols() && dir[i][1] >= 0 && dir[i][1] < map.getCols()) {
                double probabilitySoil = 0.0;
                double probabilityAir = 0.0;
                double probabilityPlant = 0.0;
                double probabilityAnimal = 0.0;
                int count = 0;
                Cell cell = map.getCell(dir[i][0], dir[i][1]);

                if (cell.getSoil() != null) {
                    probabilitySoil = cell.getSoil().getBlockProbability();
                    count++;
                }
                if (cell.getAir() != null) {
                    probabilityAir = cell.getAir().getToxicProbability();
                    count++;
                }
                if (cell.getPlant() != null) {
                    probabilityPlant = cell.getPlant().getHangingProbability();
                    count++;
                }
                if (cell.getAnimal() != null) {
                    probabilityAnimal = cell.getAnimal().getAttackProbability();
                    count++;
                }
                //formulele din enunt
                double sum = probabilitySoil + probabilityAir + probabilityPlant + probabilityAnimal;
                double mean = Math.abs(sum / count);
                int result = (int)Math.round(mean);

                //se actualizeaza scorul cel mai bun si directia ideala pana in momentul acesta
                if (result < bestScore) {
                    bestScore = result;
                    desiredX = dir[i][0];
                    desiredY = dir[i][1];
                }
            }
        }
        //robotul are destula baterie sa se mute?
        if (this.battery < bestScore) {
            return "ERROR: Not enough battery left. Cannot perform action";
        }
        //mutam robotul
        this.x = desiredX;
        this.y = desiredY;
        this.battery -= bestScore;
        return "The robot has successfully moved to position (" + this.x + ", " + this.y + ").";
    }

    //metoda pentru comanda "scanObject"
    public String scanObject(CommandInput commandInput, Cell cell) {
        //costul mutarii este de 7 energyPoints; daca nu ajunge bateria robotul nu scaneaza
        if (this.battery < 7) {
            return "ERROR: Not enough battery left. Cannot perform action";
        }
        String object = "";
        String color = commandInput.getColor();
        String smell = commandInput.getSmell();
        String sound = commandInput.getSound();

        //obiectul apa (none none none)
        if (color.equals("none") && smell.equals("none") && sound.equals("none")) {
            object = "water";
            //obiectul planta (!none !none none)
        } else if (!color.equals("none") && !smell.equals("none") && sound.equals("none")) {
            object = "plant";
            //obiectul animal (!none !none !none)
        } else if (!color.equals("none") && !smell.equals("none") && !sound.equals("none")) {
            object = "animal";
        }

        //daca nu a scanat planta o scaneaza, scade energyPoints si o adauga in inventar
        if (object.equals("plant") && cell.getPlant() != null && !cell.getPlant().getIsScanned()) {
            this.battery -= 7;
            cell.getPlant().setIsScanned(true);
            inventory.add(cell.getPlant());
            return "The scanned object is a " + object + ".";
        }
        //daca nu a scanat animalul il scaneaza, scade energyPoints si il adauga in inventar
        if (object.equals("animal") && cell.getAnimal() != null && !cell.getAnimal().getIsScanned()) {
            this.battery -= 7;
            cell.getAnimal().setIsScanned(true);
            cell.getAnimal().setExpirationTime(commandInput.getTimestamp());
            inventory.add(cell.getAnimal());
            return "The scanned object is an " + object + ".";
        }
        //daca nu a scanat apa, o scaneaza, o adauga in inentar si scade energyPoints
        if (object.equals("water") && cell.getWater() != null && !cell.getWater().getIsScanned()) {
            this.battery -= 7;
            cell.getWater().setIsScanned(true);
            cell.getWater().setAirExpirationTime(commandInput.getTimestamp());
            cell.getWater().setSoilExpirationTime(commandInput.getTimestamp());
            inventory.add(cell.getWater());
            return "The scanned object is " + object + ".";
        }
        return "ERROR: Object not found. Cannot perform action";
    }
}
