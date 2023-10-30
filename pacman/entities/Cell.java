package pacman.entities;

public class Cell {
    // 0 = wall
    // 1 = empty
    // 2 = pacgum;
    public int type;

    public int x;
    public int y;

    public Cell(int x, int y, int type){
        this.x = x;
        this.y = y;
        this.type = type;
    }
}