package pacman.game;

import pacman.entities.Cell;
import java.util.ArrayList;

public class Maze {
    // 2D ArrayList of Objects
    private ArrayList<ArrayList<Cell>> grid;

    public boolean modified = false;

    //
    public Maze(int rows, int cols) {
        grid = new ArrayList<>(rows);
        // manually input starting maze

        /*
        for (int i = 0; i < rows; i++) {
            ArrayList<Cell> row = new ArrayList<>(cols);
            for (int j = 0; j < cols; j++) {
                row.add(new Cell(i, j, this));
            }
            grid.add(row);
        }
        */
    }

    // add method to change board layout when PacMan eats a green PacGum
    public void modifyBoard(){
        if (!modified) {
            // default board to new layout
        } else {
            // change back to default layout
        }
    }


    // why is this here
    public boolean isValidPosition(int x, int y) {
        return x >= 0 && y >= 0 && x < grid.size() && y < grid.get(0).size();
    }

    public Cell getCell(int x, int y) {
        return grid.get(x).get(y);
    }

}
