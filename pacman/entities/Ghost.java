package pacman.entities;

import pacman.state.PacState;

import java.awt.*;
import java.util.Random;

/**
 * Classe qui contient toutes les méthodes et informations a propos des entitées fantomes.
 */
public class Ghost {
    /**
     * Variables contenant la ligne du fantome.
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
     * Variable contenant la direction actuelle du fantôme (0: Droite, 1: Gauche, 2: Haut, 3: Bas).
     */
    private int currentDirection;
    /**
     * Variable indiquant si le fantôme a rencontrer un mur lors du mouvement.
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
     * @param x     ligne de départ du fantome.
     * @param y     colonne de départ du fantome.
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
     * Méthode qui déplace le fantôme de manière aléatoire.
     *
     * @param state état actuel du Pac-Man.
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

    /**
     * Gère le mouvement du fantôme lorsqu'il rencontre un mur.
     * Si le fantôme est bloqué par un mur, il choisit une nouvelle direction aléatoire 
     * jusqu'à ce qu'une direction valide soit trouvée.
     */
    private void handleWallMovement() {
        if (wall) {
            Random random = new Random();
            int newDirection;

            do {
                newDirection = random.nextInt(4); // Choisir une nouvelle direction aléatoire
            } while (!trySetDirection(newDirection));
        }
    }

    /**
     * Méthode qui tente de définir une nouvelle direction pour le déplacement.
     *
     * @param newDirection Nouvelle direction à essayer.
     * @return true si la nouvelle direction est valide et a été définie, false sinon.
     */
    private boolean trySetDirection(int newDirection) {
        int[] newCoordinates = calculateNewCoordinates(x, y, newDirection);

        if (Labyrinth.isValidMove(newCoordinates[0], newCoordinates[1])) {
            currentDirection = newDirection;
            wall = false;
            return true;
        }
        return false;
    }

    /**
     * Méthode qui calcule les nouvelles coordonnées en fonction de la direction spécifiée.
     *
     * @param x            coordonnée x actuelle.
     * @param y            coordonnée y actuelle.
     * @param newDirection direction pour laquelle calculer les nouvelles coordonnées.
     * @return tableau d'entiers représentant les nouvelles coordonnées [nouvelle_x, nouvelle_y].
     */
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
