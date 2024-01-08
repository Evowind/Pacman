package pacman.state;

import pacman.entities.PacMan;

/**
 * Représente l'état super d'un Pac-Man.
 */
public class SuperState extends PacState {
    /**
     * Durée maximale de l'état super en millisecondes.
     */
    private static final long TIMEOUT = 10000;

    /**
     * Temps de début de l'état super.
     */
    private final long startTime;

    /**
     * Initialise l'état super avec le temps de début.
     */
    public SuperState() {
        startTime = System.currentTimeMillis();
    }

    /**
     * Met à jour l'état du Pac-Man super.
     *
     * @param pacman Instance du Pac-Man à mettre à jour.
     */
    @Override
    public void update(PacMan pacman) {
        // Implémentation de la mise à jour de l'état super du Pac-Man
    }

    /**
     * Renvoie l'état actuel.
     *
     * @return L'état actuel (NORMAL si le temps écoulé dépasse TIMEOUT, sinon SUPER).
     */
    @Override
    public State getState() {
        return (System.currentTimeMillis() - startTime >= TIMEOUT) ? State.NORMAL : State.SUPER;
    }
}
