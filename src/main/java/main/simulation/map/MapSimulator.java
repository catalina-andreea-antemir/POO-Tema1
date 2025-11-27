package main.simulation.map;

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
}
