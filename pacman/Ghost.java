package pacman;

import java.awt.*;
import java.util.Random;

public class Ghost {
    private int x, y;
    private Color color;
    private int currentDirection;
    private boolean wall;

    // TODO remove and replace with pacman getstate() == PacState.State.SUPER
    private boolean isVulnerable = false;
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

    public void moveRandomly() {
        if (isVulnerable) canMove = false;
        if (wall) {
            Random random = new Random();
            int newDirection = random.nextInt(4); // Choisir une nouvelle direction aléatoirement

            // Assurez que la nouvelle direction est valide
            int newX = x;
            int newY = y;
            switch (newDirection) {
                case 0: // Droite
                    newX++;
                    break;
                case 1: // Gauche
                    newX--;
                    break;
                case 2: // Haut
                    newY--;
                    break;
                case 3: // Bas
                    newY++;
                    break;
            }

            if (Game.isValidMove(newX, newY)) {
                currentDirection = newDirection;
                wall = false; // Réinitialisez "wall" à false lorsqu'une nouvelle direction est choisie
            }
        }

        int newX = x;
        int newY = y;
        switch (currentDirection) {
            case 0: // Droite
                newX++;
                break;
            case 1: // Gauche
                newX--;
                break;
            case 2: // Haut
                newY--;
                break;
            case 3: // Bas
                newY++;
                break;
        }

        // Vérifiez que la nouvelle position est valide avant de déplacer le fantôme
        if (Game.isValidMove(newX, newY)) {
            x = newX;
            y = newY;
            if(newX == 27 && newY == 14) {
                newX = 0;
            } else if (newX == 0 && newY == 14) {
                newX = 27;
            }

        } else {
            // Si la nouvelle position n'est pas valide, réinitialisez "wall" à true pour choisir une nouvelle direction
            wall = true;
        }
    }


    public void setX(int newX) {
        this.x = newX;
    }
    public void setY(int newY) {
        this.y = newY;
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }
    public boolean isCanMove() {
        return canMove;
    }

    public void setVulnerable(boolean vulnerable) {
        isVulnerable = vulnerable;
    }
    public boolean isVulnerable() {
        return isVulnerable;
    }
}
