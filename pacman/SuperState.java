package pacman;

public class SuperState extends PacState{
    private static final int TIMEOUT = 10000;
    private int timer;

    protected SuperState(PacManObservable pacman) {
        super(pacman);
        timer = TIMEOUT;
    }

    @Override
    public void move() {
        // TODO import pacman move method
    }

    @Override
    State getState() {
        return State.SUPER;
    }
}
