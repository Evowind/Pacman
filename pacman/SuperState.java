package pacman;

public class SuperState extends PacState {
    private static final long TIMEOUT = 10000;
    private long startTime;

    public SuperState() {
        startTime = System.currentTimeMillis();
    }

    @Override
    public void move() {
        // TODO: Implement move logic for super state
    }

    @Override
    public State getState() {
        return (System.currentTimeMillis() - startTime >= TIMEOUT) ? State.NORMAL : State.SUPER;
    }
}
