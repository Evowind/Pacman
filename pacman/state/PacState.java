package pacman.state;

import pacman.entities.PacMan;

public abstract class PacState {
    public enum State { NORMAL, INVISIBLE, SUPER }

    public abstract State getState();

    public void update(PacMan pacman) {
        // Default behavior for states without time constraints
    }
}
