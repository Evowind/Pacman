package pacman.state;

/**
 * Classe contenant l'état par défaut de l'entité PacMan.
 */
public class NormalState extends PacState {
    /**
     * Méthode utilisée pour retourner l'état de l'entité PacMan.
     *
     * @return la constante State.NORMAL.
     */
    @Override
    public State getState() {
        return State.NORMAL;
    }
}
