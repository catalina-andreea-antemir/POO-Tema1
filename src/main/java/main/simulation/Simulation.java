package main.simulation;

import fileio.InputLoader;
import fileio.CommandInput;
import fileio.SimulationInput;

import main.simulation.commands.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import main.simulation.map.MapSimulator;
import main.simulation.robot.TerraBot;

import java.util.List;

public class Simulation {
    private int rows;
    private int cols;
    private int energyPoints;
    private MapSimulator map;
    private TerraBot bot;
    private List<CommandInput> commands;
    private boolean started;
    private InputLoader inputLoader;
    private int currentSimulationIndex;
    private ReaderSimulation jsonReader;
    private RobotCommands robotComm;
    private EnvironmentCommands envComm;
    private DebugCommands debugComm;

    public Simulation(InputLoader input) {
        this.commands = input.getCommands();
        this.inputLoader = input;
        this.currentSimulationIndex = 0;
        this.jsonReader = new ReaderSimulation();
        this.robotComm = new RobotCommands();
        this.envComm = new EnvironmentCommands();
        this.debugComm = new DebugCommands();
        initSimulation(0);
    }

    private void initSimulation(int currentSimIndex) {
        SimulationInput simulation = inputLoader.getSimulations().get(currentSimIndex);
        String[] dims = simulation.getTerritoryDim().split("x");
        this.cols = Integer.parseInt(dims[0]);
        this.rows = Integer.parseInt(dims[1]);
        this.energyPoints = simulation.getEnergyPoints();
        this.map = new MapSimulator(cols, rows);
        this.bot = new TerraBot(this.energyPoints);
        this.started = false;
        jsonReader.populate(this.map, simulation.getTerritorySectionParams());
    }

    public ArrayNode runSimulation() {
        ObjectMapper objMapper = new ObjectMapper();
        ArrayNode out = objMapper.createArrayNode();
        int botCharging = -1;
        int previousTimestamp = 0;

        for (CommandInput commandInput : commands) {
            if (started) {
                for (int t = previousTimestamp + 1; t <= commandInput.getTimestamp(); t++) {
                    envComm.entitiesInteractions(map, t);
                }
            }
            previousTimestamp = commandInput.getTimestamp();
            if (commandInput.getCommand().equals("startSimulation")) {
                if (started) {
                    ObjectNode error = objMapper.createObjectNode();
                    error.put("command", commandInput.getCommand());
                    error.put("message", "ERROR: Simulation already started. Cannot perform action");
                    error.put("timestamp", commandInput.getTimestamp());
                    out.add(error);
                } else {
                    initSimulation(currentSimulationIndex);
                    started = true;
                    ObjectNode start = objMapper.createObjectNode();
                    start.put("command", commandInput.getCommand());
                    start.put("message", "Simulation has started.");
                    start.put("timestamp", commandInput.getTimestamp());
                    out.add(start);
                }
            } else {
                if (!started) {
                    ObjectNode error = objMapper.createObjectNode();
                    error.put("command", commandInput.getCommand());
                    error.put("message", "ERROR: Simulation not started. Cannot perform action");
                    error.put("timestamp", commandInput.getTimestamp());
                    out.add(error);
                } else {
                    if (botCharging > commandInput.getTimestamp()) {
                        ObjectNode error = objMapper.createObjectNode();
                        error.put("command", commandInput.getCommand());
                        error.put("message", "ERROR: Robot still charging. Cannot perform action");
                        error.put("timestamp", commandInput.getTimestamp());
                        out.add(error);
                        continue;
                    }
                    if (commandInput.getCommand().equals("printEnvConditions")) {
                        ObjectNode printEnv = objMapper.createObjectNode();
                        printEnv.put("command", commandInput.getCommand());
                        printEnv.put("output", debugComm.envCond(map.getCell(bot.getX(), bot.getY()), commandInput));
                        printEnv.put("timestamp", commandInput.getTimestamp());
                        out.add(printEnv);
                    }
                    if (commandInput.getCommand().equals("printMap")) {
                        ObjectNode printMap = objMapper.createObjectNode();
                        printMap.put("command", commandInput.getCommand());
                        printMap.put("output", debugComm.mapPrint(map));
                        printMap.put("timestamp", commandInput.getTimestamp());
                        out.add(printMap);
                    }
                    if (commandInput.getCommand().equals("printKnowledgeBase")) {
                        ObjectNode printBase = objMapper.createObjectNode();
                        printBase.put("command", commandInput.getCommand());
                        printBase.put("output", debugComm.printKnowledgeBase(bot.getDatabase()));
                        printBase.put("timestamp", commandInput.getTimestamp());
                        out.add(printBase);
                    }
                    if (commandInput.getCommand().equals("moveRobot")) {
                        ObjectNode moveRobot = objMapper.createObjectNode();
                        moveRobot.put("command", commandInput.getCommand());
                        moveRobot.put("message", robotComm.moveRobot(bot, map));
                        moveRobot.put("timestamp", commandInput.getTimestamp());
                        out.add(moveRobot);
                    }
                    if (commandInput.getCommand().equals("rechargeBattery")) {
                        ObjectNode rechargeBattery = objMapper.createObjectNode();
                        int timeToCharge = commandInput.getTimeToCharge();
                        rechargeBattery.put("command", commandInput.getCommand());
                        bot.setBattery(bot.getBattery() + timeToCharge);
                        botCharging = commandInput.getTimestamp() + timeToCharge;
                        rechargeBattery.put("message", "Robot battery is charging.");
                        rechargeBattery.put("timestamp", commandInput.getTimestamp());
                        out.add(rechargeBattery);
                    }
                    if (commandInput.getCommand().equals("getEnergyStatus")) {
                        ObjectNode getEnergyStatus = objMapper.createObjectNode();
                        getEnergyStatus.put("command", commandInput.getCommand());
                        getEnergyStatus.put("message", "TerraBot has " + bot.getBattery() + " energy points left.");
                        getEnergyStatus.put("timestamp", commandInput.getTimestamp());
                        out.add(getEnergyStatus);
                    }
                    if (commandInput.getCommand().equals("changeWeatherConditions")) {
                        ObjectNode changeConditions = objMapper.createObjectNode();
                        changeConditions.put("command", commandInput.getCommand());
                        changeConditions.put("message", envComm.changeWeatherConditions(map, commandInput));
                        changeConditions.put("timestamp", commandInput.getTimestamp());
                        out.add(changeConditions);
                    }
                    if (commandInput.getCommand().equals("scanObject")) {
                        ObjectNode scanObject = objMapper.createObjectNode();
                        scanObject.put("command", commandInput.getCommand());
                        scanObject.put("message", robotComm.scanObject(bot, commandInput, map.getCell(bot.getX(), bot.getY())));
                        scanObject.put("timestamp", commandInput.getTimestamp());
                        out.add(scanObject);
                    }
                    if (commandInput.getCommand().equals("learnFact")) {
                        ObjectNode learnFact = objMapper.createObjectNode();
                        learnFact.put("command", commandInput.getCommand());
                        learnFact.put("message", robotComm.learnFact(bot, commandInput.getSubject(), commandInput.getComponents()));
                        learnFact.put("timestamp", commandInput.getTimestamp());
                        out.add(learnFact);
                    }
                    if (commandInput.getCommand().equals("improveEnvironment")) {
                        ObjectNode improveEnv = objMapper.createObjectNode();
                        improveEnv.put("command", commandInput.getCommand());
                        String msg = robotComm.improveEnvironment(bot, commandInput, map);
                        improveEnv.put("message", msg);
                        improveEnv.put("timestamp", commandInput.getTimestamp());
                        out.add(improveEnv);
                    }
                    if (commandInput.getCommand().equals("endSimulation")) {
                        ObjectNode end = objMapper.createObjectNode();
                        end.put("command", commandInput.getCommand());
                        end.put("message", "Simulation has ended.");
                        end.put("timestamp", commandInput.getTimestamp());
                        started = false;
                        currentSimulationIndex++;
                        out.add(end);
                    }
                }
            }
        }
        return out;
    }
}
