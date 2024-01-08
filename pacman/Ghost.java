package pacman;

import java.awt.*;
import java.util.Random;

/**
 * Représente un fantôme dans le jeu Pac-Man.
 */
public class Ghost {
    /**
     * Position en X du fantôme.
     */
    private int x;

    /**
     * Position en Y du fantôme.
     */
    private int y;

    /**
     * Couleur du fantôme.
     */
    private final Color color;

    /**
     * Direction actuelle du fantôme (0: Droite, 1: Gauche, 2: Haut, 3: Bas).
     */
    private int currentDirection;

    /**
     * Indique si le fantôme a rencontré un mur lors du mouvement.
     */
    private boolean wall;

    /**
     * Indique si le fantôme peut se déplacer (utilisé pour l'état SUPER du Pac-Man).
     */
    private boolean canMove = true;

    /**
     * Initialise un nouveau fantôme avec des paramètres donnés.
     *
     * @param x     Position en X initiale du fantôme.
     * @param y     Position en Y initiale du fantôme.
     * @param color Couleur du fantôme.
     */
    public Ghost(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.currentDirection = 2;
        this.wall = false;
    }

    /**
     * Déplace le fantôme de manière aléatoire.
     *
     * @param state État actuel du Pac-Man.
     */
    public void moveRandomly(PacState.State state) {
        if (state != PacState.State.SUPER || canMove) {
            handleWallMovement();
            int[] newCoordinates = calculateNewCoordinates(x, y, currentDirection);

            if (Labyrinth.isValidMove(newCoordinates[0], newCoordinates[1])) {
                x = newCoordinates[0];
                y = newCoordinates[1];
            } else {
                wall = true;
            }
        }
        if (state == PacState.State.SUPER) canMove = !canMove;
    }

    //TODO : JavaDoc
    private void handleWallMovement() {
        if (wall) {
            Random random = new Random();
            int newDirection;

            do {
                newDirection = random.nextInt(4); // Choisir une nouvelle direction aléatoire
            } while (!trySetDirection(newDirection));
        }
    }

    //TODO : JavaDoc
    private boolean trySetDirection(int newDirection) {
        int[] newCoordinates = calculateNewCoordinates(x, y, newDirection);

        if (Labyrinth.isValidMove(newCoordinates[0], newCoordinates[1])) {
            currentDirection = newDirection;
            wall = false;
            return true;
        }
        return false;
    }

    //TODO : JavaDoc
    private int[] calculateNewCoordinates(int x, int y, int newDirection) {
        int[] newCoordinates = {x, y};

        switch (newDirection) {
            case 0 -> // Droite
                    newCoordinates[0]++;
            case 1 -> // Gauche
                    newCoordinates[0]--;
            case 2 -> // Haut
                    newCoordinates[1]--;
            case 3 -> // Bas
                    newCoordinates[1]++;
        }

        return newCoordinates;
    }

    /**
     * Renvoie la position en X du fantôme.
     *
     * @return Position en X du fantôme.
     */
    public int getX() {
        return x;
    }

    /**
     * Renvoie la position en Y du fantôme.
     *
     * @return Position en Y du fantôme.
     */
    public int getY() {
        return y;
    }

    /**
     * Renvoie la couleur du fantôme.
     *
     * @return Couleur du fantôme.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Définit la nouvelle position en X du fantôme.
     *
     * @param newX Nouvelle position en X.
     */
    public void setX(int newX) {
        this.x = newX;
    }

    /**
     * Définit la nouvelle position en Y du fantôme.
     *
     * @param newY Nouvelle position en Y.
     */
    public void setY(int newY) {
        this.y = newY;
    }
}
