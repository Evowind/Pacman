package pacman;

public class InvisibleState extends PacState {
    private static final long TIMEOUT = 10000;
    private long startTime;

    public InvisibleState() {
        startTime = System.currentTimeMillis();
    }

    @Override
    public void update(PacManObservable pacman) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - startTime >= TIMEOUT) {
            pacman.state = new NormalState();
        }
    }

    @Override
    public State getState() {
        return (System.currentTimeMillis() - startTime >= TIMEOUT) ? State.NORMAL : State.INVISIBLE;
    }
}
