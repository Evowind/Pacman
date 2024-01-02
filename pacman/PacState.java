package pacman;

public abstract class PacState {
    public static enum State {NORMAL, INVISIBLE, SUPER};

    protected PacManObservable pacman;

    protected PacState(PacManObservable pacman){
        this.pacman = pacman;
    }

    // use this method to move pacman, instead of whatever is in Game right now
    public abstract void move();

    abstract State getState();
}
