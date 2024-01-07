package pacman;

public class SuperState extends PacState {
    private static final long TIMEOUT = 10000;
    private final long startTime;

    public SuperState() {
        startTime = System.currentTimeMillis();
    }

    @Override
    public void update(PacMan pacman) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - startTime >= TIMEOUT) {
            pacman.state = new NormalState();
        }
    }

    @Override
    public State getState() {
        return (System.currentTimeMillis() - startTime >= TIMEOUT) ? State.NORMAL : State.SUPER;
    }
}