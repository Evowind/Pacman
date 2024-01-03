package pacman;

public class InvisibleState extends PacState {
    private static final long TIMEOUT = 10000;
    private long startTime;

    public InvisibleState() {
        startTime = System.currentTimeMillis();
    }

    @Override
    public void move() {
        // TODO: Implement move logic for invisible state
    }

    @Override
    public State getState() {
        return (System.currentTimeMillis() - startTime >= TIMEOUT) ? State.NORMAL : State.INVISIBLE;
    }
}
