package pacman;

import java.awt.*;
import java.util.Random;

public class Ghost {
    private int x, y;
    private final Color color;
    private int currentDirection;
    private boolean wall;
    private boolean canMove = true;

    public Ghost(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.currentDirection = 2;
        this.wall = false;
    }

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

    private void handleWallMovement() {
        if (wall) {
            Random random = new Random();
            int newDirection;

            do {
                newDirection = random.nextInt(4); // Choisir une nouvelle direction aléatoire
            } while (!trySetDirection(newDirection));
        }
    }

    private boolean trySetDirection(int newDirection) {
        int[] newCoordinates = calculateNewCoordinates(x, y, newDirection);

        if (Labyrinth.isValidMove(newCoordinates[0], newCoordinates[1])) {
            currentDirection = newDirection;
            wall = false;
            return true;
        }
        return false;
    }

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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Color getColor() {
        return color;
    }

    public void setX(int newX) {
        this.x = newX;
    }

    public void setY(int newY) {
        this.y = newY;
    }
}
