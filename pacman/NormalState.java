package pacman;

public class NormalState extends PacState {
    protected NormalState(PacManObservable pacman) {
        super(pacman);
    }

    @Override
    public void move() {
        // TODO import pacman move method
    }

    @Override
    State getState() {
        return State.NORMAL;
    }
}
