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
        this.map = new Cell[cols][rows];
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                this.map[i][j] = new Cell();
            }
        }
    }

    public int getRows() {
        return this.rows;
    }
    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCols() {
        return this.cols;
    }
    public void setCols(int cols) {
        this.cols = cols;
    }

    public Cell getCell(int col, int row) {
        return this.map[col][row];
    }

    public ArrayNode mapPrint() {
        ObjectMapper objMapper = new ObjectMapper();
        ArrayNode out = objMapper.createArrayNode();
        for (int j = 0; j < rows; j++) {
            for (int i = 0; i < cols; i++) {
                Cell cell = getCell(i, j);
                ObjectNode node = objMapper.createObjectNode();
                ArrayNode section = objMapper.createArrayNode();
                section.add(i);
                section.add(j);
                node.set("section", section);

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

                node.put("totalNrOfObjects", numberObj);
                if (cell.getAir() != null) {
                    node.put("airQuality", cell.getAir().qualityLabel());
                } else {
                    node.put("airQuality", "");
                }
                if (cell.getSoil() != null) {
                    node.put("soilQuality", cell.getSoil().qualityLabel());
                } else {
                    node.put("soilQuality", "");
                }
                out.add(node);
            }
        }
        return out;
    }
}
