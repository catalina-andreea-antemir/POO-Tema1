package main.simulation.map;

public class MapSimulator {
    private int rows; //map's rows
    private int cols; //map's cols
    private Cell[][] map; //cells map

    public MapSimulator(final int cols, final int rows) {
        this.rows = rows;
        this.cols = cols;
        //instantiate cells map
        this.map = new Cell[cols][rows];
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                //instantiate each cell
                this.map[i][j] = new Cell();
            }
        }
    }

    //Getter and Setter methods for private class fields
    public final int getRows() {
        return this.rows;
    }
    public final int getCols() {
        return this.cols;
    }

    /**
     * Getter for the cell at "col" column and "row" row
     * @param col current col
     * @param row current roe
     * @return cell = map[col][row]
     */
    public final Cell getCell(final int col, final int row) {
        return this.map[col][row];
    }
}
