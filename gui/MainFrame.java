package gui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        // Initialisation de la fenêtre du jeu
        setTitle("Pac-Man");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600); // Taille de la fenêtre
        setLocationRelativeTo(null); // Centrer la fenêtre sur l'écran

        // Ajoutez d'autres composants ici

        // Création d'un panneau pour le terrain de jeu
        JPanel gamePanel = new JPanel();
        // Personnalisez le panneau pour afficher le terrain de jeu ici

        // Création d'une étiquette pour afficher le score
        JLabel scoreLabel = new JLabel("Score: 0");
        // Personnalisez l'étiquette ici

        // Ajout des composants à la fenêtre
        getContentPane().add(gamePanel, BorderLayout.CENTER);
        getContentPane().add(scoreLabel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame game = new MainFrame();
            game.setVisible(true);
        });
    }
}
