package pacman;

public class PacManObservable {
    // TODO import pacman related things from Game to here
    private PacState state;

    public PacManObservable() {
        state = new NormalState(this);
    }

}
