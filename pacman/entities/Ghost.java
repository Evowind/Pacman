package pacman.entities;

// Class with Ghosts and the methods related to them
public class Ghost{
    // color (4 different ghosts), all same behavior
    // 0 = red
    // 1 = cyan
    // 2 = pink
    // 3 = yellow
    public int color;

    // coords
    public int x;
    public int y;

    public int direction;

    public Ghost(int x, int y, int color){
        this.x = x;
        this.y = y;
        this.color = color;
    }

    // random deplacement, changes direction when cant move (hits wall)
    // add check ifValid from PacMan (when done)
    public void deplacement(){}
}
