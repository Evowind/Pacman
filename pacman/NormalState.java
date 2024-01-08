package pacman;

/**
 * Représente l'état normal d'un Pac-Man.
 */
public class NormalState extends PacState {

    /**
     * Renvoie l'état normal.
     *
     * @return L'état normal.
     */
    @Override
    public State getState() {
        return State.NORMAL;
    }
}