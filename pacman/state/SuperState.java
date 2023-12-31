package pacman.state;

import pacman.entities.PacMan;

/**
 * Classe représentent l'état Super d'un Pac-Man.
 */
public class SuperState extends PacState {
    /**
     * Variable contenant la durée maximale de l'état SUPER en millisecondes.
     */
    private static final long TIMEOUT = 10000;
    /**
     * Variable contenant le début de l'état super.
     */
    private final long startTime;

    /**
     * Constructeur de l'état Super qui initialise le temps de départ.
     */
    public SuperState() {
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
     * @return la constante State.SUPER.
     */
    @Override
    public State getState() {
        return State.SUPER;
    }
}
