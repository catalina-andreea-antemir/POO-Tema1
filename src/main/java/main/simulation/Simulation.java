package main.simulation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import fileio.InputLoader;
import fileio.CommandInput;
import fileio.SimulationInput;

import main.simulation.commands.DebugCommands;
import main.simulation.commands.RobotCommands;
import main.simulation.commands.EnvironmentCommands;

import main.simulation.map.MapSimulator;
import main.simulation.robot.TerraBot;

import java.util.List;

public class Simulation {
    private int rows; //map rows
    private int cols; //map cols
    private int energyPoints; //robot energy points
    private MapSimulator map; //simulation map
    private TerraBot bot; //terraBot
    private List<CommandInput> commands; //simulation commands
    private boolean started; //flag for verifying if the simulation already started or not
    private InputLoader inputLoader; //helper to read from JSON file
    private int currentSimulationIndex; //sim index to start if there are more then one
    private ReaderSimulation jsonReader; //field for ReaderSimulation instance
    private RobotCommands robotComm; //field for handling commands for robot
    private EnvironmentCommands envComm; //field for handling commands for environment
    private DebugCommands debugComm; //field for handling commands for debug

    public Simulation(final InputLoader input) {
        this.commands = input.getCommands();
        this.inputLoader = input;
        this.currentSimulationIndex = 0;
        this.jsonReader = new ReaderSimulation();
        this.robotComm = new RobotCommands();
        this.envComm = new EnvironmentCommands();
        this.debugComm = new DebugCommands();
        initSimulation(0);
    }

    /**
     * Method for initializing the simulation
     * @param currentSimIndex the index for knowing which simulation we start
     */
    private void initSimulation(final int currentSimIndex) {
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

    /**
     * Method for running the current simulation
     * Helps with handling each command and writing the result in the JSON output file
     * @return out = the output for the JSON output file
     */
    public ArrayNode runSimulation() {
        ObjectMapper objMapper = new ObjectMapper();
        ArrayNode out = objMapper.createArrayNode();
        int botCharging = -1; //flag for verifying if the robot finished charging
        //flag for handling the 2 iterations gap for the interactions between the entities
        int previousTimestamp = 0;

        for (CommandInput commandInput : commands) {
            //if sim started the env interactions will happen in the required time interval
            if (started) {
                for (int t = previousTimestamp + 1; t <= commandInput.getTimestamp(); t++) {
                    envComm.entitiesInteractions(map, t);
                }
            }
            previousTimestamp = commandInput.getTimestamp();
            if (commandInput.getCommand().equals("startSimulation")) {
                //if there is no sim started we can't start a new one so we return an error msg
                if (started) {
                    ObjectNode error = objMapper.createObjectNode();
                    error.put("command", commandInput.getCommand());
                    String msg = "ERROR: Simulation already started. Cannot perform action";
                    error.put("message", msg);
                    error.put("timestamp", commandInput.getTimestamp());
                    out.add(error);
                //starting a new simulation if one is not currently running
                } else {
                    initSimulation(currentSimulationIndex);
                    started = true;
                    ObjectNode start = objMapper.createObjectNode();
                    start.put("command", commandInput.getCommand());
                    start.put("message", "Simulation has started.");
                    start.put("timestamp", commandInput.getTimestamp());
                    out.add(start);
                }
                //if current sim not started + receiving comm diff from the start one print err msg
            } else {
                if (!started) {
                    ObjectNode error = objMapper.createObjectNode();
                    error.put("command", commandInput.getCommand());
                    String msg = "ERROR: Simulation not started. Cannot perform action";
                    error.put("message", msg);
                    error.put("timestamp", commandInput.getTimestamp());
                    out.add(error);
                } else {
                    //ignoring other commands in the recharging time
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
                        int x = bot.getX(), y = bot.getY();
                        ObjectNode op = debugComm.envCond(map.getCell(x, y), commandInput);
                        printEnv.put("output", op);
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
                        bot.setBattery(bot.getBattery() + timeToCharge); //we update the battery
                        //set timestamp for when the robot becomes available
                        botCharging = commandInput.getTimestamp() + timeToCharge;
                        rechargeBattery.put("message", "Robot battery is charging.");
                        rechargeBattery.put("timestamp", commandInput.getTimestamp());
                        out.add(rechargeBattery);
                    }
                    if (commandInput.getCommand().equals("getEnergyStatus")) {
                        ObjectNode getEnergyStatus = objMapper.createObjectNode();
                        getEnergyStatus.put("command", commandInput.getCommand());
                        String msg = "TerraBot has " + bot.getBattery() + " energy points left.";
                        getEnergyStatus.put("message", msg);
                        getEnergyStatus.put("timestamp", commandInput.getTimestamp());
                        out.add(getEnergyStatus);
                    }
                    if (commandInput.getCommand().equals("changeWeatherConditions")) {
                        ObjectNode changeConditions = objMapper.createObjectNode();
                        changeConditions.put("command", commandInput.getCommand());
                        String msg = envComm.changeWeatherConditions(map, commandInput);
                        changeConditions.put("message", msg);
                        changeConditions.put("timestamp", commandInput.getTimestamp());
                        out.add(changeConditions);
                    }
                    if (commandInput.getCommand().equals("scanObject")) {
                        ObjectNode scanObject = objMapper.createObjectNode();
                        scanObject.put("command", commandInput.getCommand());
                        int x = bot.getX(), y = bot.getY();
                        String msg = robotComm.scanObject(bot, commandInput, map.getCell(x, y));
                        scanObject.put("message", msg);
                        scanObject.put("timestamp", commandInput.getTimestamp());
                        out.add(scanObject);
                    }
                    if (commandInput.getCommand().equals("learnFact")) {
                        ObjectNode learnFact = objMapper.createObjectNode();
                        learnFact.put("command", commandInput.getCommand());
                        String comp = commandInput.getComponents();
                        String msg = robotComm.learnFact(bot, commandInput.getSubject(), comp);
                        learnFact.put("message", msg);
                        learnFact.put("timestamp", commandInput.getTimestamp());
                        out.add(learnFact);
                    }
                    if (commandInput.getCommand().equals("improveEnvironment")) {
                        ObjectNode improveEnv = objMapper.createObjectNode();
                        improveEnv.put("command", commandInput.getCommand());
                        String msg = robotComm.improveEnv(bot, commandInput, map);
                        improveEnv.put("message", msg);
                        improveEnv.put("timestamp", commandInput.getTimestamp());
                        out.add(improveEnv);
                    }
                    if (commandInput.getCommand().equals("endSimulation")) {
                        ObjectNode end = objMapper.createObjectNode();
                        end.put("command", commandInput.getCommand());
                        end.put("message", "Simulation has ended.");
                        end.put("timestamp", commandInput.getTimestamp());
                        started = false; //mark simulation as finished
                        currentSimulationIndex++; //increment index to prevent repetition
                        out.add(end);
                    }
                }
            }
        }
        return out;
    }
}
