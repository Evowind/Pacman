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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Color getColor() {
        return color;
    }

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

    public void setX(int newX) {
        this.x = newX;
    }
    public void setY(int newY) {
        this.y = newY;
    }
}
