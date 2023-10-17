package pacman.entities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PacMan extends JPanel {
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
        x = 0;
        y = 0;
        diameter = 25;
        direction = 0; // Droite par défaut
        speed = 5; // Ajustez la vitesse selon vos besoins
        this.maze = maze; // Initialisez la grille du labyrinthe
        mouthAngle = 45; // Angle initial de la bouche
        mouthClosing = true; // La bouche commence à se fermer

        // Créer une minuterie pour mettre à jour la position de Pac-Man et l'animation de la bouche
        timer = new Timer(1000 / speed, new ActionListener() {
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
        g.fillArc(x, y, diameter, diameter, mouthAngle, 360 - (2 * mouthAngle)); // Dessiner Pac-Man
    }

    public void setDirection(int newDirection) {
        direction = newDirection;
    }

    public void move() {
        int newX = x;
        int newY = y;

        if (direction == 0) {
            newX += speed; // Droite
        } else if (direction == 1) {
            newY -= speed; // Haut
        } else if (direction == 2) {
            newX -= speed; // Gauche
        } else if (direction == 3) {
            newY += speed; // Bas
        }
    }
}