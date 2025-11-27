package main.simulation.robot;

import main.entities.Entities;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TerraBot {
    //Magic number fix
    private static final int INITIAL_POS = -1;
    private static final int MAX_INTEGER = 9999;
    private static final int NR_DIRECTIONS = 4;
    private static final int BATTERY_USAGE_SCAN = 7;
    private static final int BATTERY_USAGE_LEARN = 2;

    private int x; //col position
    private int y; //row position
    private int battery; //battery of the robot
    private List<Entities> inventory; //inventory of the robot
    private Map<String, List<String>> database; //database of the robot

    public TerraBot(final int energyPoints) {
        this.x = 0;
        this.y = 0;
        this.battery = energyPoints;
        this.inventory = new ArrayList<>();
        this.database = new LinkedHashMap<>();
    }

    //Getter and Setter methods for private class fields
    public final int getX() {
        return this.x;
    }
    public final void setX(final int x) {
        this.x = x;
    }
    public final int getY() {
        return this.y;
    }
    public final void setY(final int y) {
        this.y = y;
    }
    public final int getBattery() {
        return this.battery;
    }
    public final void setBattery(final int battery) {
        this.battery = battery;
    }
    public final List<Entities> getInventory() {
        return this.inventory;
    }
    public final Map<String, List<String>> getDatabase() {
        return this.database;
    }
    public final void setDatabase(final Map<String, List<String>> database) {
        this.database = database;
    }
}
