package pacman;

/**
 * Classe abstraite représentant l'état d'un Pac-Man.
 */
public abstract class PacState {

    /**
     * Enumération des états possibles d'un Pac-State.
     */
    public enum State { NORMAL, INVISIBLE, SUPER }

    /**
     * Renvoie l'état actuel.
     *
     * @return L'état actuel.
     */
    public abstract State getState();

    /**
     * Met à jour l'état du Pac-Man.
     *
     * @param pacman Instance du Pac-Man à mettre à jour.
     */
    public void update(PacMan pacman) {
        // Implémentation de la mise à jour de l'état du Pac-Man
    }
}