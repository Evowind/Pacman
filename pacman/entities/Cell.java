package pacman.entities;

/**
 * Représente les types de cellules dans le jeu Pac-Man.
 */
public enum Cell {
    /**
     * Mur bloquant le déplacement.
     */
    WALL,
    /**
     * Cellule vide.
     */
    EMPTY,
    /**
     * Point Pac-Dot collectible par Pac-Man.
     */
    PACDOT,
    /**
     * Cellule Pac-Dot spéciale de couleur violette.
     */
    PURPLE,
    /**
     * Cellule Pac-Dot spéciale de couleur orange.
     */
    ORANGE,
    /**
     * Cellule Pac-Dot spéciale de couleur verte.
     */
    GREEN,
    /**
     * Téléporteur permettant un déplacement instantané vers un autre téléporteur.
     */
    TELEPORTER
}
