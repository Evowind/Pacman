package pacman.state;

import pacman.entities.PacMan;

/**
 * Classe abstraite du patron de conception État permettant de modifier le comportement de l'entité PacMan.
 */
public abstract class PacState {
    /**
     * Enumération contenant les différents états possible pour l'entité PacMan.
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
