package pacman;

public abstract class PacState {
    public enum State { NORMAL, INVISIBLE, SUPER }

    public abstract void move();

    public abstract State getState();

    public void update() {
        // Default behavior for states without time constraints
    }
}
