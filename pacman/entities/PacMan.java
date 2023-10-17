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

    public PacMan() {
        x = 0;
        y = 0;
        diameter = 30;
        direction = 0; // Droite par défaut
        speed = 5; // Ajustez la vitesse selon vos besoins

        // Créer une minuterie pour mettre à jour la position de Pac-Man
        timer = new Timer(1000 / speed, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                move();
                repaint();
            }
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.YELLOW); // Couleur de Pac-Man
        g.fillArc(x, y, diameter, diameter, 45, 270); // Dessiner Pac-Man
    }

    public void setDirection(int newDirection) {
        direction = newDirection;
    }

    private void move() {
        if (direction == 0) {
            x += speed; // Droite
        } else if (direction == 1) {
            y -= speed; // Haut
        } else if (direction == 2) {
            x -= speed; // Gauche
        } else if (direction == 3) {
            y += speed; // Bas
        }

        // Assurez-vous que Pac-Man reste à l'intérieur de la fenêtre
        if (x > getWidth()) {
            x = -diameter;
        } else if (x < -diameter) {
            x = getWidth();
        }
    }
}
