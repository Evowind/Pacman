package pacman.entities;

// Cell that teleports PacMan and Ghosts
// They are situated on the sides of the Maze
public class Teleporter{
    // Each teleporter has an exit (another Teleporter)
    public Teleporter exit;

    // coords
    public int x;
    public int y;

    // Create a new teleporter
    public Teleporter(int x, int y, Teleporter exit){
        this.x = x;
        this.y = y;
        this.exit = exit;
    }

    public void teleport(){}
}
