package main.simulation;

public class Commands {
    private String command;
    private int timestamp;

    public Commands(String command, int timestamp) {
        this.command = command;
        this.timestamp = timestamp;
    }

    public String getCommand() {
        return this.command;
    }
    public void setCommand(String command) {
        this.command = command;
    }

    public int getTimestamp() {
        return this.timestamp;
    }
    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }
}
