package pacman.entities;

import pacman.state.PacState;

import java.awt.*;
import java.util.Random;

/**
 * Classe qui contient toutes les méthodes et informations a propos des entitées fantomes.
 */
public class Ghost {
    /**
     * Variables contenant la rangée du fantome.
     */
    private int x;
    /**
     * Variables contenant la colonne du fantome.
     */
    private int y;
    /**
     * Variables contenant la couleur du fantome.
     */
    private final Color color;
    /**
     * Variables contenant la direction du fantome.
     */
    private int currentDirection;
    /**
     * Variables qui indique si le fantome est face a un mur.
     */
    private boolean wall;
    /**
     * Variables qui indique si le fantome peut se déplacer.
     * Utilisé pour ralentir les fantomes quand l'état de PacMan est State.SUPER.
     */
    private boolean canMove = true;

    /**
     * Constructeur de base de la classe Ghost.
     *
     * @param x rangée de départ du fantome.
     * @param y colonne de départ du fantome.
     * @param color couleur du fantome.
     */
    public Ghost(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.currentDirection = 2;
        this.wall = false;
    }

    /**
     * Méthode qui déplace le fantome.
     *
     * @param state état de l'entité PacMan.
     */
    public void moveRandomly(PacState.State state) {
        if (state != PacState.State.SUPER || canMove) {
            if (wall) {
                Random random = new Random();
                int newDirection;

                do {
                    newDirection = random.nextInt(4); // Choisir une nouvelle direction aléatoirement
                } while (!trySetDirection(newDirection));
            }

            int[] array = changeCoordinates(x, y, currentDirection);

            int newX = array[0];
            int newY = array[1];

            if (Labyrinth.isValidMove(newX, newY)) {
                x = newX;
                y = newY;
            } else {
                wall = true;
            }
        }
        if (state == PacState.State.SUPER) canMove = !canMove;
    }

    /**
     * Méthode qui teste si un déplacement dans une direction donnée est possible.
     *
     * @param newDirection direction que l'on veut tester.
     * @return vrai si la la direction est possible, non si c'est un mur
     */
    private boolean trySetDirection(int newDirection) {
        int[] array = changeCoordinates(x, y, newDirection);

        int newX = array[0];
        int newY = array[1];

        if (Labyrinth.isValidMove(newX, newY)) {
            currentDirection = newDirection;
            wall = false;
            return true;
        }
        return false;
    }

    /**
     * Méthode qui déplace le fantome dans sa direction actuelle.
     *
     * @param x rangée du fantome.
     * @param y colonne du fantome.
     * @param newDirection direction du fantome.
     * @return un tableau contenant les nouvelles coordonnées du fantome.
     */
    private int[] changeCoordinates(int x, int y, int newDirection) {
        int[] array = new int[]{x,y};
        switch (newDirection) {
            case 0: // Droite
                array[0]++;
                break;
            case 1: // Gauche
                array[0]--;
                break;
            case 2: // Haut
                array[1]--;
                break;
            case 3: // Bas
                array[1]++;
                break;
        }

        return array;
    }

    /**
     * Setter pour la variable x.
     *
     * @param newX nouvelle rangée du fantome.
     */
    public void setX(int newX) {
        this.x = newX;
    }

    /**
     * Getter pour la variable x.
     *
     * @return rangée du fantome.
     */
    public int getX() {
        return x;
    }

    /**
     * Setter pour la variable y.
     *
     * @param newY nouvelle colonne du fantome.
     */
    public void setY(int newY) {
        this.y = newY;
    }

    /**
     * Getter pour la variable y.
     *
     * @return colonne du fantome.
     */
    public int getY() {
        return y;
    }

    /**
     * Getter pour la variable color.
     *
     * @return couleur du fantome.
     */
    public Color getColor() {
        return color;
    }
}
