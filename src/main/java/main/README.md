# TerraBot - TEMA 1 POO
## PACKAGE ENTITIES
### PACKAGE PLANT

- This package is structured based on a hierarchy in which the parent class
  it's the abstract class Plant. This class includes the logic of life of 
  a plant trying to implement both the mechanism by which the plant grows,
  and it changes its stage of maturity, and the way it
  interacts with other entities (in this case, the air), the plant collaborating
  at the oxygen level in the air if it is scanned by the robot. Classes that
  inherit (extends) Plant (Flowering Plants, Gymnosperms Plans, Ferns, Mosses, 
  Algae) implements abstract methods of parent class hangingProbability(),
  oxygenFromPlant() and oxygenLevel() by polymorphism as
  each type of plant has other specific formulas.

### PACKAGE ANIMAL

- The Animal Package is a hierarchy of classes in which children classes Carnivores,
  Herbivores, Omnivores, Detritivores and Parasites extends the abstract class
  Animal. The Animal class shapes an animal's life, feeding, moving,
  changing its life stage and also producing fertilizer for the soil
  (interaction with other entities). For this being abstract, its kids classes
  are required to implement abstract methods, which are attackProbability()
  and animalEats(). I chose these methods to be abstract because every type
  of animal requires other functionality at the level of feeding and the 
  impact that it has on the robot at the time of attack if it exists. Also, the
  moveAnimal() method is required by the way the animal moves to feed once every
  two iterations depending on the quality of life for each cell.

### PACKAGE WATER

- This package contains a single class, Water, that implements logic in
  which water works in the environment. It influences the other
  entities: air (by increasing the humidity level), soil (by
  make the soil retain more water), plant (by collaborating on
  its growth in maturity)

### PACKAGE SOIL

- The Soil package is a package that works based on a hierarchy of
  classes where the parent class is the abstract class Soil and the children 
  classes are Desert Soil, Forest Soil, Grassland Soil, Swamp Soil and Tundra Soil.
  The superclass implements how soil exists in the environment.
  It can hold water, help the plant grow and more. In the code
  presented by me these are the main characteristics of the soil on
  the interaction part, but it can also affect the robot by
  blocking it. This blockProbability() method is an abstract one because each
  soil type has a power to block the robot differently. Also, every type
  of soil has a different quality calculation score, which is why the method
  qualityScore() is also abstract.

### PACKAGE AIR

- This package, like the other presented, is structured in the form of
  hierarchy of classes. At the top of the hierarchy is the abstract class Air which
  contains three abstract methods: airQuality(), maxScore() and
  meteorologicalEvent(params). These classes are abstract because their logic
  is not fixed and universal for any type of air, but is a particular 
  logic depending on the type of air in the cell (Polar, Mountain, Desert,
  Tropical and Temperate). For the meteorologicalEvent method are transmitted as
  parameters some specific values for each type. So, when calling the method,
  a valid and usable value will be received only for the specific parameter
  of the type of air we work with.

### CLASS ENTITIES

- This is a "supreme :)" class because it contains two common fields
  for each type of entity: name and mass. She's also class
  parent for each of the abstract classes presented above, as
  they extend it to use the common name and table fields.

## PACKAGE SIMULATION
### PACKAGE COMMANDS

- This package contains 3 classes for commands handling.
- The class DebugCommands is for handling the commands for debugging that needed
  to be implemented through methods: printEnvConditions, printMap, 
  printKnowledgeBase, and the output for these commands is printed in JSON 
  format in the JSON output file.
- The class EnvironmentCommands is strictly designed for handling the
  changeWeatherConditions and calling the meteorologicalEvent method for changing
  the weather temporarily. It also takes control over the interactions between
  entities, making sure that they are happening in the right order and under right
  conditions (only if the entities are scanned) and it also controls the moving animals.
- The class RobotCommands has the role of taking care of the robot and the 
  actions that it has to do. For the moveRobot command, it scans the area for
  threats and forces the robot to take the safest path available. Fot the 
  learnFact and improveEnvironment commands, the robot first identifies objects
  using senses (like color or sound), studies them to learn new facts, and 
  finally combines those items and knowledge to permanently improve the environment,
  assuming it has the energy to do so.

### PACKAGE MAP

- The Cell class is implemented to simulate a map cell that can contain 
  maximum one of each entity
- The MapSimulator class is design to be the simulation map that contains 
  cells of type Cell. This the map that the robot has to scan.

### PACKAGE ROBOT

- This package only contains the class TerraBot that is simulating the robot 
  that has to do the actions on the simulation map

### CLASS READER SIMULATION

- This class is designed for reading the input from the JSON input file 
  using the Jackson Object Mapper. I am aware that is a lot of repetitive 
  code but this was the method that I found the easiest to implement as a 
  person who never worked with JSON files before.

### CLASS SIMULATION

- Finally, tha class Simulation is the one that handles the command 
  management, calling the significant method for each command, making sure 
  that the simulations work correctly, and also printing the result in the 
  JSON output file, also with Object Mapper

## FEEDBACK

- I think this assignment was helpful in understanding the basic principles 
  of OOP and its good practises, but it has also a lot of code to write and 
  some ambiguity in the assignment specifications.
- GIT: I tried to do as many commits as I could without them being 
  insignificant
- LLM USAGE: I tried to not use AI as much. I needed it for JSON reading and 
  writing because, as I said, I never worked with it before. Also I used it 
  to correct the checkstyle errors. As I was not thinking of using any type 
  of collections, AI gave me those ideas.