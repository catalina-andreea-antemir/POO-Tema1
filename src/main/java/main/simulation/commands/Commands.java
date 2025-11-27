package main.simulation.commands;

public class Commands {
    private String command;
    private int timestamp;

    public Commands(final String command, final int timestamp) {
        this.command = command;
        this.timestamp = timestamp;
    }

    public final String getCommand() {
        return this.command;
    }
    public final void setCommand(final String command) {
        this.command = command;
    }

    public final int getTimestamp() {
        return this.timestamp;
    }
    public final void setTimestamp(final int timestamp) {
        this.timestamp = timestamp;
    }
}
