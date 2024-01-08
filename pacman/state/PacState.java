package pacman.state;

import pacman.entities.PacMan;

/**
 * Classe abstraite représentant l'état d'un Pac-Man.
 */
public abstract class PacState {

    /**
     * Enumération des états possibles d'un Pac-State.
     */
    public enum State { NORMAL, INVISIBLE, SUPER }

    /**
     * Méthode utilisée pour retourner l'état de l'entité PacMan.
     *
     * @return l'état actuel de PacMan
     */
    public abstract State getState();

    /**
     * Méthode utilisée pour modifier mettre a jour les données de l'état de l'entité PacMan.
     *
     * @param pacman objet PacMan que l'on veut mettre à jour.
     */
    public void update(PacMan pacman) {
        // Default behavior for states without time constraints
    }
}
