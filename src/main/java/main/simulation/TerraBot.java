package main.simulation;

import main.map.*;
import main.simulation.*;
import main.entities.*;

public class TerraBot {
    private int x;
    private int y;
    private int battery;

    public TerraBot(int energyPoints) {
        this.x = 0;
        this.y = 0;
        this.battery = energyPoints;
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
}
