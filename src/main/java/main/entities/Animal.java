package main.entities;
import main.entities.Plant;
import main.entities.Water;
import main.entities.Air;
import main.entities.Soil;
import main.map.Cell;
import main.map.MapSimulator;

public abstract class Animal extends Entities {
    private String type;
    private String status;
    private double intakeRate;
    private boolean isScanned;
    private boolean isDead;
    private double organicMetter;
    private int x;
    private int y;
    private int expirationTime;

    public Animal(String name, double mass) {
        super(name, mass); //se apeleaza constructorul parintelui
        this.type = null;
        this.status = "Hungry";
        this.intakeRate = 0.08;
        this.isScanned = false;
        this.isDead = false;
        this.organicMetter = 0.0;
        this.x = -1;
        this.y = -1;
        this.expirationTime = -2;
    }

    //metode getter si setter pentru campul privat type
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }

    //metode getter si setter pentru campul privat status
    public String getStatus() {
        return this.status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    //metode getter si setter pentru campul privat intakeRate
    public double getIntakeRate() {
        return this.intakeRate;
    }
    public void setIntakeRate(double intakeRate) {
        this.intakeRate = intakeRate;
    }

    //metode getter si setter pentru campul privat isScanned
    public boolean getIsScanned() {
        return this.isScanned;
    }
    public void setIsScanned(boolean isScanned) {
        this.isScanned = isScanned;
    }

    //metode getter si setter pentru campul privat isDead
    public boolean getIsDead() {
        return this.isDead;
    }
    public void setIsDead(boolean isDead) {
        this.isDead = isDead;
    }

    //metode detter si setter pentru campul private organicMetter
    public double getOrganicMetter() {
        return this.organicMetter;
    }
    public void setOrganicMetter(double organicMetter) {
        this.organicMetter = organicMetter;
    }

    //metode getter si setter pentru campul private x
    public int getX() {
        return this.x;
    }
    public void setX(int x) {
        this.x = x;
    }

    //metode getter si setter pentru campul private y
    public int getY() {
        return this.y;
    }
    public void setY(int y) {
        this.y = y;
    }

    //metode getter si setter pentru campul private expirationTime
    public int getExpirationTime() {
        return this.expirationTime;
    }
    public void setExpirationTime(int currentTime) {
        this.expirationTime = currentTime + 2;
    }

    //verifica daca animalul poate produce ingrasamant
    public boolean canProduceFertilizer() {
        if (this.status.equals("Well-Fed")) {
            return true;
        }
        return false;
    }

    //returneaza probabilitatea de atac pe baza tipului de animal
    protected abstract double attackProbability();
    public double getAttackProbability() {
        return attackProbability();
    }

    //calculeaza valoarea de organicMetter cu care animalul contribuie la sol
    public abstract void animalEats(Animal prey, Plant plant, Water water);

    //metoda de mutare a animalului pentru a se hrani
    public void moveAnimal(MapSimulator map, int timestamp) {
        if (timestamp < this.expirationTime) {
            return;
        }
        int desiredX = -1, desiredY = -1;
        double bestWaterQuality = -1.0;
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
        int both = 0, plant = 0, water = 0;

        for (int i = 0; i < 4; i++) {
            if (dir[i][0] >= 0 && dir[i][0] < map.getCols() && dir[i][1] >= 0 && dir[i][1] < map.getCols()) {
                Cell cell = map.getCell(dir[i][0], dir[i][1]);
                //daca in celula exista si planta si apa
                if (cell.getPlant() != null && cell.getWater() != null) {
                    //daca nu s a gasit o celula de genul sau s a gasit dar exista una cu calitatea apei mia buna
                    if (cell.getWater().waterQuality() > bestWaterQuality || both == 0) {
                        bestWaterQuality = cell.getWater().waterQuality();
                        desiredX = dir[i][0];
                        desiredY = dir[i][1];
                        both = 1;
                    }
                    //daca in celula exista doar planta si nu s a gasit una cu planta SI apa
                } else if (cell.getPlant() != null && both == 0) {
                    //daca nu s a gasit inca una doar cu planta
                    if (plant == 0) {
                        desiredX = dir[i][0];
                        desiredY = dir[i][1];
                        plant = 1;
                    }
                    //daca in celula exista doar apa si nu s a gasit una cu planta Si apa sau una cu planta
                } else if (cell.getWater() != null && both == 0 && plant == 0) {
                    //daca nu s a gasit inca una doar cu apa sau daca s a gasit dar exista una cu calitatea apei mai buna
                    if (water == 0 || cell.getWater().waterQuality() > bestWaterQuality) {
                        desiredX = dir[i][0];
                        desiredY = dir[i][1];
                        water = 1;
                    }
                    //daca nu s a gasit nicio celula robotul se muta la prima verificata
                } else if (both == 0 && plant == 0 && water == 0) {
                    if (desiredX == -1 && desiredY == -1) {
                        desiredX = dir[i][0];
                        desiredY = dir[i][1];
                    }
                }
            }
        }
        //se updateaza pozitia animalului pe harta
        if (desiredX != -1 && desiredY != -1) {
            map.getCell(this.x, this.y).setAnimal(null);
            this.x = desiredX;
            this.y = desiredY;
            map.getCell(desiredX, desiredY).setAnimal(this);
            this.expirationTime = timestamp;
        }
    }

    //interactiunea Animal - Soil (daca animalul poate produce ingrasamant, organicMetter al solului creste)
    public void interactionSoil(Soil soil) {
        if (soil != null && getIsScanned() && canProduceFertilizer()) {
            soil.setOrganicMatter(soil.getOrganicMatter() + organicMetter);
        }
    }
}
