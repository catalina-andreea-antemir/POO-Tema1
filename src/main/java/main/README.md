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
- This is a class "supreme :)" because it contains two common fields
  for each type of entity: name and mass. She's also class
  parent for each of the abstract classes presented above, as
  they extend it to use the common name and table fields.

## PACKAGE SIMULATION