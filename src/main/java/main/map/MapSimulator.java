package main.map;

public class MapSimulator {
    private int rows;
    private int cols;
    private Cell[][] map;

    public MapSimulator(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.map = new Cell[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
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

    public Cell getCell(int row, int col) {
        return this.map[row][col];
    }
}
