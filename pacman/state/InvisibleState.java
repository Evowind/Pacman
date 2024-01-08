package pacman.state;

import pacman.entities.PacMan;

/**
 * Représente l'état invisible du joueur Pac-Man.
 */
public class InvisibleState extends PacState {
    /**
     * Durée maximale de l'état invisible en millisecondes.
     */
    private static final long TIMEOUT = 10000;

    /**
     * Temps de début de l'état invisible.
     */
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
     * @return L'état actuel (NORMAL si le temps écoulé dépasse TIMEOUT, sinon INVISIBLE).
     */
    @Override
    public State getState() {
        return (System.currentTimeMillis() - startTime >= TIMEOUT) ? State.NORMAL : State.INVISIBLE;
    }
}
