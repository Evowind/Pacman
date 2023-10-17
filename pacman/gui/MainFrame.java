package pacman.gui;

import pacman.entities.PacMan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class MainFrame extends JFrame {
    private PacMan pacMan; // Ajoutez un champ pour le personnage Pac-Man
    int pacManSpeed = 2; // Vitesse de Pac-Man

    private static MazeCell getMazeCell(int cellType) {
        return new MazeCell(cellType);
    }

    public MainFrame() {
        // Initialisation de la fenêtre du jeu
        setTitle("Pac-Man");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 1000); // Taille de la fenêtre pour correspondre au labyrinthe Pac-Man
        setLocationRelativeTo(null); // Centrer la fenêtre sur l'écran

        // Création d'un panneau pour le terrain de jeu
        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(31, 27)); // Utilisez une grille 31x28 pour créer le labyrinthe Pac-Man

        // Définition du labyrinthe (0 pour espace vide, 1 pour mur, 2 pour points)
        int[][] maze = {
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
                {1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1},
                {1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1},
                {1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1},
                {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
                {1, 2, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 2, 1},
                {1, 2, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 2, 1},
                {1, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 1},
                {1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 0, 0, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 0, 0, 0, 0, 0, 0, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1},
                {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 0, 0, 0, 0, 0, 0, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
                {1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 0, 0, 0, 0, 0, 0, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1},
                {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
                {1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1},
                {1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1},
                {1, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 2, 0, 0, 2, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 1},
                {1, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 1},
                {1, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 1},
                {1, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 1},
                {1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1},
                {1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1},
                {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        };
        for (int[] ints : maze) {
            for (int j = 0; j < ints.length; j++) {
                JPanel cell = getjPanel(ints, j);
                gamePanel.add(cell);
            }

        }
        // Créez une instance de PacMan
        pacMan = new PacMan();

        // Positionnez Pac-Man dans la grille
        int cellSize = 30; // Taille d'une cellule dans la grille
        int pacManRow = 24; // Ligne initiale de Pac-Man
        int pacManCol = 9; // Colonne initiale de Pac-Man
        JPanel pacManCell = (JPanel) gamePanel.getComponent(pacManRow * 27 + pacManCol);
        int pacManX = pacManCol * cellSize;
        int pacManY = pacManRow * cellSize;
        pacManCell.add(pacMan);
        pacMan.setBounds(pacManX, pacManY, cellSize, cellSize);


        // Création d'une étiquette pour afficher le score
        JLabel scoreLabel = new JLabel("Score: 0");
        // Personnalisez l'étiquette ici

        // Ajout des composants à la fenêtre
        getContentPane().add(gamePanel, BorderLayout.CENTER);
        getContentPane().add(scoreLabel, BorderLayout.SOUTH);

        // Gestion des entrées utilisateur pour le déplacement de Pac-Man
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                int newDirection = -1;

                if (keyCode == KeyEvent.VK_RIGHT) {
                    newDirection = 0; // Droite
                } else if (keyCode == KeyEvent.VK_UP) {
                    newDirection = 1; // Haut
                } else if (keyCode == KeyEvent.VK_LEFT) {
                    newDirection = 2; // Gauche
                } else if (keyCode == KeyEvent.VK_DOWN) {
                    newDirection = 3; // Bas
                }

                if (newDirection != -1) {
                    pacMan.setDirection(newDirection);
                }
            }
        });
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }

    private static JPanel getjPanel(int[] ints, int j) {
        JPanel cell = new JPanel();
        cell.setLayout(new BorderLayout());

        if (ints[j] == 1) {
            cell.setBackground(Color.BLACK); // Couleur pour les murs
        } else if (ints[j] == 2) {
            // Créez un JPanel pour représenter une pacdot
            JPanel pacdot = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    int diameter = 5;
                    int x = (getWidth() - diameter) / 2;
                    int y = (getHeight() - diameter) / 2;
                    g.setColor(Color.WHITE); // Couleur de la pacdot
                    g.fillOval(x, y, diameter, diameter);
                }
            };
            pacdot.setOpaque(false); // Rendre le JPanel pacdot transparent
            cell.setBackground(Color.BLUE); // Couleur pour les espaces vides
            cell.add(pacdot, BorderLayout.CENTER);
        } else {
            cell.setBackground(Color.BLUE); // Couleur pour les espaces vides
        }

        return cell;
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame game = new MainFrame();
            game.setVisible(true);
        });
    }
}
