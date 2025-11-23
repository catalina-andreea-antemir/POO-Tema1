package main.map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class MapSimulator {
    private int rows;
    private int cols;
    private Cell[][] map;

    public MapSimulator(int cols, int rows) {
        this.rows = rows;
        this.cols = cols;
        //instantiaza clasa Cell pentru o matrice ce celule
        this.map = new Cell[cols][rows];
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                //instantiaza fiecare celula
                this.map[i][j] = new Cell();
            }
        }
    }

    //metode getter si setter pentru campul privat rows
    public int getRows() {
        return this.rows;
    }
    public void setRows(int rows) {
        this.rows = rows;
    }

    //metode getter si setter pentru campul privat cols
    public int getCols() {
        return this.cols;
    }
    public void setCols(int cols) {
        this.cols = cols;
    }

    //metoda de acces la o celula din harta
    public Cell getCell(int col, int row) {
        return this.map[col][row];
    }

    //metoda pentru comanda "printMap"
    public ArrayNode mapPrint() {
        ObjectMapper objMapper = new ObjectMapper();
        ArrayNode out = objMapper.createArrayNode();

        //se parcurge matricea
        for (int j = 0; j < rows; j++) {
            for (int i = 0; i < cols; i++) {
                Cell cell = getCell(i, j);
                ObjectNode node = objMapper.createObjectNode();
                ArrayNode section = objMapper.createArrayNode();
                section.add(i);
                section.add(j);
                //pentru fiecare celula de afiseaza coordonatele
                node.set("section", section);

                //se numara cate entitati se afla in celula
                int numberObj = 0;
                if (cell.getAnimal() != null && cell.getAnimal().getMass() > 0.0) {
                    numberObj++;
                }
                if (cell.getPlant() != null && cell.getPlant().getMass() > 0.0) {
                    numberObj++;
                }
                if (cell.getWater() != null && cell.getWater().getMass() > 0.0) {
                    numberObj++;
                }

                //se afiseaza numarul de entitati prezente in celula
                node.put("totalNrOfObjects", numberObj);
                //se afiseaza calitatea aerului
                if (cell.getAir() != null) {
                    node.put("airQuality", cell.getAir().qualityLabel());
                }

                //se afiseaza calitatea solului
                if (cell.getSoil() != null) {
                    node.put("soilQuality", cell.getSoil().qualityLabel());
                }
                out.add(node);
            }
        }
        return out;
    }
}
