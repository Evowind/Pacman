package pacman.state;

import pacman.entities.PacMan;

/**
 * Classe représentent l'état invisible du joueur Pac-Man.
 */
public class InvisibleState extends PacState {
    /**
     * Durée maximale de l'état invisible en millisecondes.
     */
    private static final long TIMEOUT = 10000;

    /**
     * Variable contenant le temps de début de l'état invisible.
     */
    private final long startTime;

    /**
     * Constructeur de l'état invisible avec le temps de début.
     */
    public InvisibleState() {
        startTime = System.currentTimeMillis();
    }

    /**
     * Méthode utilisée pour modifier mettre a jour les données de l'état de l'entité PacMan.
     *
     * @param pacman objet PacMan que l'on veut mettre à jour.
     */
    @Override
    public void update(PacMan pacman) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - startTime >= TIMEOUT) {
            pacman.state = new NormalState();
        }
    }

    /**
     * Méthode utilisée pour retourner l'état de l'entité PacMan.
     *
     * @return la constante State.INVISIBLE.
     */
    @Override
    public State getState() {
        return State.INVISIBLE;
    }
}
