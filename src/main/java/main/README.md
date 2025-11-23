TerraBot - Tema1 - POO

PACHETUL "ENTITIES"

- Animal
    - clasa animal este o clasa abstracta in care am retinut urmatoarele campuri:
        - name - numele animalului
        - mass - masa animalului
        - type - tipul de animal (Carnivores, Omnivores, Herbivores, Detritivores, Parasites)
        - status - stadiul de hranire al animalului (Hungry, Well-Fed, Sick)
        - intakeRate - nivelul de apa pe care un animal il poate consuma maxim (?); el are valoarea   predefinita 0.08
        - isScanned - indica daca un animal este scanat de robot sau nu
        - isDead - indica daca un animal este mort sau nu
    - aceasta contine de asemenea constructorul care primeste ca parametrii numele si masa animalului; aici sunt initializate campurile din clasa (important: statusul este "HUngry" pentru ca un animal cand este creat in mod logic este infometat si intakeRate = 0.08 valoare impusa in enunt)
    - clasa contine o serie de metode Getteri si Setteri pentru ca toate camourile clasei sunt de tip "private"
    - petru interactiunea cu solul am incus o metoda "canProduceFertilizer" pentru a verifica daca animalul poate produce ingrasamant pentru sol
    - apo am declarat doua metode abstracte intrucat implementarea acestora depinde de tipul de animal (override):
        - attackProbability():
            - am respectat valorile din enunt pe care le am normalizat e baza formulei "attack_probability = (100 - animal_possibility_to_attack) / 10.0" data in enunt, unde animal_possibility_to_attack are valoarea specifica fiecarui tip de animal
        - animalEats(prey, plant, animal):
            - Carnivores / Parasites:
                - daca prada exista in celula, masa animalului creste cu masa pradei, setam masa pradei la 0.0, marcam prada ca "dead", si numarul de entitati consumate de animal la 1
                - daca prada nu exista verificam daca planta exista; daca exista repetam pasii de la prada doar ca pentru planta; asemeni pentru apa
                - daca animalul a mancat doar prada, planta sau apa: setam organicMetter (pentru sol) la 0.5
                - daca animalul a mancat si planta si apa: organicMetter = 0.8
            - Herbivores / Omnivores / Detrivores
                - se realizeaza aceiasi pasi ca cei de mai sus doar ca se considera prada inexistenta
    - metoda getAttackProbability() returneaza rezoltatul metodei abstracte attackProbability() intrucat aceasta este protected

- Plant
    - clasa plant este o clasa abstracta in care am retinut urmatoarele campuri:
        - name - numele plantei
        - mass - masa plantei
        - type - tipul de planta (Floering, Gymnosperm, Ferns, Mosses, Algae)
        - maturityLevel - niveul de maturitate al plantei (Young, Mature, Old, Dead)
        - maturiryOxygen - nivelul de oxigen pe care planta il creeaza intr un anumit stadiu de maturitate
        - growthLevel - unitatea cu care creste planta (maxim 1.0)
        - isScanned - indica daca o planta este scanata de robot sau nu
        - isDead - indica daca o planta este moarta sau nu
    - aceasta clasa contine contrustorul care primeste ca parametri numele si masa plantei si seteaza nivelul de maturitate "Young"
    - de asemenea contine o serie de Getteri si Setteri pentru camourile private din ckasa
    - clasa are 3 clase abstracte pentru care subclasele copii vor face override:
        - hangingProbability():
            - returneaza probabilitatea ca robotul sa ramana blocat in planta pentru fiecare tip de planta, de asta este abstracta; aceasta probabilitate respecta formula din enunt "plant_possibility / 100.0" unde plant_possibility este valoare impusa de enunt pentru fiecare tip de planta
        - ocygenFromPlant():
            -aceasta returneaza nivelul de oxygen pe care fiecare tip de planta il creeaza
        - oxygenLevel():
            - calculeaza nivelul de oxigen din aer pe baza tipului de planta (ajuta la interactiunea cu aerul)
    - metoda getHnagingProbability() returneaza rezultatul metodei abstracte hangingProbability() intrucat aceasta este protected
    - metoda setMaturityRate() seteaza nivelul de oxigen in functie de nivelul de maturitate al plantei
    - metoda growMaturity() actualizeaza nivelul de maturitate al plantei dupa cea aceasta a crescut pe baza metodei grow()

- Water
    - clasa water contine urmatoarele campuri:
        - name - numele apei
        - mass - masa apei
        - type - tipul de apa
        - salinity - nivelul de sare din apa
        - pH - pH ul apei
        - purity - puritatea apei
        - turbidity - opacitatea apei
        - contaminantIndex - nivelul de contaminare
        - isFrozen - indica daca apa este inghetata sau nu
    - clasa contine constructorul care primeste ca parametri numele si masa apei, setand restul campurilor la 0/null in functie de tipul de date
    - urmeaza o serie de Getteri si Setteri pentru campurile private ale clasei
    - metoda waterQuality() calculeaza calitatea apei pe baza formulelor date in enunt
    - metoda getQuality() returneaza eticheta calitatii apei (Good, Moderate, Poor) pe baza calitatii apei
    
- Soil
    - clasa soil este o clasa abstracta ce contine urmaoarele campuri:
        - name - numele solului
        - mass - masa solului
        - type - tipul de sol (DesertSoil, ForestSoil, GrasslandSoil, SwampSoil, TundraSoil)
        - nitrogen - nivelul de nitrogen din sol
        - waterRetention - retentia apei din sol
        - soilpH - pH ul solului
        - organicMetter - materia organica din sol
        - leafLitter - specific tipului de sol ForestSoil
        - watterLogging - specific tipului de sol SwmpSoil
        - rootDensity - specific tipului de sol GrasslandSoil
        - permafrostDepth - specic tipului de sol TundraSoil
        - salinity - specific tipului de sol DesertSoil
    - urmatorul este constructorul care primeste ca parametri numele si masa solului, setand restul campurilor la 0/null in functie de tipul de date
    - contine apoi Getteri si Setteri pentru campurile private
    - metoda noemalize() este o functie helper pentru normalizarea valorilor
    - fiind o clasa abstracta, clasa contine metode abstracte:
        - qualityScore():
            - calculeaza calitatea solului in functie de tipul de sol, rezultat pe care aplica functia normalize; se foloseste de cate o formula diferita pe baza campului specific fiecarui tip de sol
        - blockProbability():
            - calculeaza probabilitatea ca robotul sa ramana blocat in sol pe baza unei formule date in enunt in functie de campurile specifice fiecarui tip de sol
    - metodele getQuality() si getBlockPorbability() returneaza rezultatele metodelor abstracte intrucat acestea sunt de tip protected
    - metoda qualityLabel() returneaza eticheta calitatii solului pe baza valorii returnate de qualityScore()

- Air
    - clasa air este o clasa abstracta care contine urmatoarele campuri:
        - name - numele aerului
        - mass - masa aerului
        - type - tipul de aer (Desert, Temperate, Polar, Tropical, Mountain)
        - humidity - nivelul de umiditate din aer
        - temperature - temperatura aerului
        - oxygenLevel - nivelul de oxige din aer
        - co2Level - nivelul de dioxod de carbon din aer
        - iceCrystalConcentration - ceata / gheta / zapada din aer
        - pollenLevel - nivelul de polen din aer
        - dustParticles - particulele de praf din aer
        - altitude - nivelul de altitudine
        - temporaryQuality - camp pentru comanda "changeWeatherConditions" ca sa retina calitatea aerului noua timp de doua iteratii
        - expirationTime - iteratia la cate calitatea aerului trebuie sa revina la normal
    - constructorul clasei care primeste numele si masa ca parametri si initializeaza restul campurilor cu niste valori nesemnificatie (0, null, -1)
    - urmeaza Getteri si Setteri pentru campurile private ale clasei
    - metoda normalize() este o functie helper pentru normalizarea valorilor asa cum se vere in enunt
    - metode abstracte:
        - airQuality():
            - returneaza valoarea normalizata a calitatii aerului calculata pe baza unor formule date in enunt
        - maxScore():
            - returneaza scorul maxim pentru fiecare tip de aer
        - meteorologicalEven(rainfall, windSpeed, newSeason, desertStorm, numberOfHikers):
            - rainfall : specific tipului Tropical
            - windSpeed : soecific tipului Polar
            - newSeason : specific tipului Temperate
            - desertStorm : specific tipului Desert
            - numberOfHikers: specific tipului Mountain
            - metoda are rolul de a calcula noua calitate a aerului afectata de o anumita conditie in functie de tipul aerului din celula pe baza unor formule impuse in enunt
    - metoda airToxicity() calculeaza toxicitatea din aer cu ajutorul unei formule din enunt care utilizeaza maxSxore() si calitatea aerului
    - metoda getToxicProbability() returneaza valoarea functiei airToxicity() intrucat aceasta este protected
    - metoda isToxic() verifica daca aerul este sau nu toxic pe baza unei formule impuse in enunt

*Fiecare clasa copil (tiputile de animal, planta, sol, aer) contin cate un constructor in care se apeleaza constructorul din clasa parinte cu super(name, mass)

PACHETUL "MAP"

- Cell
    - clasa Cell reprezinta o celula in care poate exista un animal, o planta, o apa, un aer si un so
    - campurile sunt:
        - animal
        - plant
        - water
        - air
        - soil
    - constructorul clasei initializeaza toate campurile cu null intrucat se considera celula goala
    - urmeaza o serie de Getteri si Setteri pentru a accesa campurile fiind private

- MapSimulator
    - aceasta clasa are rolul de a reprezenta harta simularii
    - contine urmatoarele campuri:
        - rows - numele de linii din harta
        - cols - numele de coloana din harta
        - map - este o matrice de celule considerata ca un sistem cartezian intrucat robotul pleca din stanga jos, locatie unde ar trebui sa se afle si (0, 0), si motiv pentru care celulele au coordonatele (coloana, linie), in loc de (linie, coloana) ca intr o matrice norala
    - constructorul clasei primeste ca parametri numarul de coloane si cel de linii si instantiaza clasa Cell pentru fiecare celula din "matrcea carteziana" dupa ce instantiaza o matrice de tip de celula
    - urmeaza o serie de Getteri si Setteri pt campurile private
    - metoda mapPrint care este de tip ArrayNode (tip necesar pentru afisarea de tip JSON), afiseaza pentru fiecare celula coordonatele, numarul de entitati din celula, calitatea aerului si cea a solului

PACHETUL "SIMULATION"

- TerraBot
    - aceasta clasa este cea pentru robot si are ca si campuri:
        - x - coordonata x
        - y - coordonata y
        - battery - bateria = numarul de energyPoints
    - constructorul primeste ca parametru numarul de energyoints pentru baterie si initializeaza coordonatele cu (0, 0) si bateria cu energyPoints
    - Getteri si Setteri pentru campurile private
    - metoda moveRobot(map) are rolul de a muta robotul pe harta in functie de un scor minim calculat
        - declaram pozitia cea mai buna de mutare  cu (-1, -1) adica o pozitia inexistenta
        - declaram 4 vectori de directie : up, right, down, left care reprezinta ordinea in care robotul verifica celulele
        - aceste directii le salvam si pe ele intr un alt vector dir pe care il vom parcurge
        - se verifica daca robotul are suficienta baterie pentru a se muta de pe celula curenta
        - daca da, se parcurge vectorul de directii, se verifica daca fiecare directie este una valida (in limitele matricei) si daca este, se initializeaza fiecare probabilitate de risc cu 0.0.
        - se verifica ce entitati sunt prezente in celula pe care o verifica si se actualizeaza probabilitatile cu functiile ajutatoare din clasele entitatilor
        - se folosesc formulele din enunt pentru calcularea scorului si se verifica daca are valoarea mai mica decat bestScore (care a fost initializat cu 999 intrucat e imposibil sa se ajunga la o valoare mai mare de atat la calcularea scorului, iar aceasta valoare ar trebui sa fie la final minima)
        - daca scorul curent este mai mic decat valoarea din bestScore acesta este actualizat impreuna cu pozitia celulei cu scorul cel mai bun
        - la final, cand a fost gasita celula ideala, se verifica daca robotul are suficienta baterie sa se mute si daca are se actualizeaza campurile robotului, coordonatele si nivelul bateriei.

- Simulation
    - 