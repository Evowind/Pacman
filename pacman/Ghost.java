package pacman;

import java.awt.*;
import java.util.Random;

public class Ghost {
    private int x, y;
    private Color color;
    private int currentDirection;
    private boolean wall;

    private boolean isVulnerable;

    public Ghost(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.currentDirection = 0; // 0: droite, 1: gauche, 2: haut, 3: bas
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
        if (wall) {
            Random random = new Random();
            int newDirection = random.nextInt(4); // Choisir une nouvelle direction aléatoirement

            // Assurez-vous que la nouvelle direction est valide
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

            if (LabyrinthGame.isValidMove(newX, newY)) {
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
        if (LabyrinthGame.isValidMove(newX, newY)) {
            x = newX;
            y = newY;
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

    public void setVulnerable(boolean vulnerable) {
        isVulnerable = vulnerable;
    }
}
