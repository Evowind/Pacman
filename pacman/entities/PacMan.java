package pacman.entities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PacMan extends JPanel {

    private int cellSize = 10;
    private int x;
    private int y;
    private int diameter;
    private int direction;
    private Timer timer;
    private int speed;
    private int[][] maze; // La grille du labyrinthe
    private int mouthAngle; // Angle de la bouche de Pac-Man
    private boolean mouthClosing; // Indique si la bouche se ferme

    public PacMan(int[][] maze) {
        diameter = 25;
        direction = 0; // Droite par défaut
        speed = 1; // Ajustez la vitesse selon vos besoins
        this.maze = maze; // Initialisez la grille du labyrinthe
        mouthAngle = 45; // Angle initial de la bouche
        mouthClosing = true; // La bouche commence à se fermer

        // Créer une minuterie pour mettre à jour la position de Pac-Man et l'animation de la bouche
        timer = new Timer(100 / speed, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                animateMouth(); // Anime la bouche de Pac-Man
                move();
                repaint();
            }
        });
        timer.start();
    }

    private void animateMouth() {
        // Anime l'ouverture et la fermeture de la bouche
        if (mouthClosing) {
            mouthAngle -= 10;
            if (mouthAngle <= 0) {
                mouthClosing = false;
            }
        } else {
            mouthAngle += 10;
            if (mouthAngle >= 45) {
                mouthClosing = true;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Set the background color to blue
        setBackground(Color.BLUE);

        g.setColor(Color.YELLOW); // Couleur de Pac-Man

        // Dessiner Pac-Man en fonction de la direction
        if (direction == 0) { // Vers la droite
            g.fillArc(x, y, diameter, diameter, mouthAngle, 360 - (2 * mouthAngle));
        } else if (direction == 1) { // Vers le haut
            g.fillArc(x, y, diameter, diameter, 90 + mouthAngle, 360 - (2 * mouthAngle));
        } else if (direction == 2) { // Vers la gauche
            g.fillArc(x, y, diameter, diameter, 180 + mouthAngle, 360 - (2 * mouthAngle));
        } else if (direction == 3) { // Vers le bas
            g.fillArc(x, y, diameter, diameter, -90 + mouthAngle, 360 - (2 * mouthAngle));
        }
    }

    public void setDirection(int newDirection) {
        direction = newDirection;
    }

    public void move() {
        int newX = x;
        int newY = y;
        int gridSize = 30; // Taille d'une cellule dans la grille

        if (direction == 0) {
            newX += gridSize; // Vers la droite
        } else if (direction == 1) {
            newY -= gridSize; // Vers le haut
        } else if (direction == 2) {
            newX -= gridSize; // Vers la gauche
        } else if (direction == 3) {
            newY += gridSize; // Vers le bas
        }

        // Vérification des limites de la grille
        int gridRows = maze.length;
        int gridCols = maze[0].length;

        if (newX >= 0 && newX <= (gridCols - 1) * gridSize && newY >= 0 && newY <= (gridRows - 1) * gridSize) {
            int cellX = newX / gridSize;
            int cellY = newY / gridSize;

            if (maze[cellY][cellX] != 1) {
                x = newX;
                y = newY;
            }
        }
    }



}