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
     * Cellule vide permettant le déplacement.
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
     * Cellule Pac-Dotspéciale de couleur verte.
     */
    GREEN,

    /**
     * Téléporteur permettant un déplacement instantané vers une autre position.
     */
    TELEPORTER
}