package pacman;

public class InvisibleState extends PacState {
    private static final int TIMEOUT = 10000;
    private int timer;

    protected InvisibleState(PacManObservable pacman) {
        super(pacman);
        timer = TIMEOUT;
    }

    @Override
    public void move() {
        // TODO import pacman move method
    }

    @Override
    State getState() {
        return State.INVISIBLE;
    }
}
