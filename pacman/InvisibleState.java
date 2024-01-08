package pacman;

/**
 * Représente l'état invisible d'un Pac-Man.
 */
public class InvisibleState extends PacState {
    //TODO : JavaDoc
    private static final long TIMEOUT = 10000;
    //TODO : JavaDoc
    private final long startTime;

    /**
     * Initialise l'état invisible avec le temps de début.
     */
    public InvisibleState() {
        startTime = System.currentTimeMillis();
    }

    /**
     * Met à jour l'état du Pac-Man invisible.
     *
     * @param pacman Instance du Pac-Man à mettre à jour.
     */
    @Override
    public void update(PacMan pacman) {
        // Implémentation de la mise à jour de l'état invisible du Pac-Man
    }

    /**
     * Renvoie l'état actuel.
     *
     * @return L'état actuel.
     */
    @Override
    public State getState() {
        return (System.currentTimeMillis() - startTime >= TIMEOUT) ? State.NORMAL : State.INVISIBLE;
    }
}