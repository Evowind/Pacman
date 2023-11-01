package pacman;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class LabyrinthGame extends JPanel implements ActionListener, KeyListener {
    private int playerX, playerY;
    private int cellSize = 30;
    private int[][] originalLabyrinth;
    private boolean[][] pacdots;
    private List<Ghost> ghosts;
    private int pacdotsRemaining;
    private int score = 0;
    private boolean isPacManInvisible;
    private long invisibleStartTime = 0;
    private static final long INVISIBLE_DURATION = 10000;
    private boolean isSuperPacMan = false;
    private long superPacManStartTime = 0;
    private static final long SUPER_PACMAN_DURATION = 10000;
    private int lives = 3;
    private int playerDirection;
    private static final int WALL = 0;
    private static final int PACDOT = 1;
    private static final int VIOLET = 2;
    private static final int ORANGE = 3;
    private static final int GREEN = 4;
    private static final int PATH = 5;
    private static final int TELEPORTER = 7;

    private static final int[][] labyrinth = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
            {0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0},
            {0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0},
            {0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0},
            {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
            {0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0},
            {0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0},
            {0, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 0},
            {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 9, 9, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 9, 9, 9, 9, 9, 9, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0},
            {7, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 9, 9, 9, 9, 9, 9, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 7},
            {0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 9, 9, 9, 9, 9, 9, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0},
            {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
            {0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0},
            {0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0},
            {0, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 0},
            {0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0},
            {0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0},
            {0, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 0},
            {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
            {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
            {0, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    };// 0 = mur, 1 = pacdot, 2 = violet, 3 = orange, 4 = vert,  5 = chemin vide, 9 = La zone bizzare, 7 = teleport


    public LabyrinthGame() {
        Timer timer = new Timer(100, this);
        timer.start();
        playerX = 15;
        playerY = 17;
        playerDirection = 0;
        isPacManInvisible = false; // Ajout de cette ligne pour initialiser la visibilité de Pac-Man
        setFocusable(true);
        addKeyListener(this);
        originalLabyrinth = new int[labyrinth.length][labyrinth[0].length];
        for (int i = 0; i < labyrinth.length; i++) {
            for (int j = 0; j < labyrinth[i].length; j++) {
                originalLabyrinth[i][j] = labyrinth[i][j];
            }
        }

        pacdots = new boolean[labyrinth.length][labyrinth[0].length];
        initializePacdots();
        ghosts = new ArrayList<>();
        initializeGhosts();
    }


    // Méthode pour initialiser les pacdots
    private void initializePacdots() {
        pacdotsRemaining = 0;
        for (int i = 0; i < labyrinth.length; i++) {
            for (int j = 0; j < labyrinth[i].length; j++) {
                if (labyrinth[i][j] == PACDOT || labyrinth[i][j] == VIOLET || labyrinth[i][j] == GREEN || labyrinth[i][j] == ORANGE) {
                    pacdots[i][j] = true;
                    pacdotsRemaining++;
                }
            }
        }
    }

    // Méthode pour initialiser les fantômes
    private void initializeGhosts() {
        ghosts.add(new Ghost(12, 11, Color.CYAN));
        ghosts.add(new Ghost(13, 11, Color.WHITE));
        ghosts.add(new Ghost(14, 11, Color.GRAY));
        ghosts.add(new Ghost(15, 11, Color.PINK));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (lives <= 0) {
            // Gérer la fin de la partie ici (défaite)
            // Réinitialiser le jeu ou afficher un message de défaite
            // Par exemple, vous pouvez afficher un message de défaite ici
            JOptionPane.showMessageDialog(this, "Vous avez perdu !", "Partie terminée", JOptionPane.INFORMATION_MESSAGE);
            // Réinitialiser le jeu
            resetGame();
            return;
        }

        if (pacdotsRemaining == 0) {
            // Gérer la fin de la partie ici (victoire)
            // Réinitialiser le jeu ou afficher un message de victoire
            // Par exemple, vous pouvez afficher un message de victoire ici
            JOptionPane.showMessageDialog(this, "Vous avez gagné !", "Partie terminée", JOptionPane.INFORMATION_MESSAGE);
            // Réinitialiser le jeu
            resetGame();
            return;
        }
        movePlayer();
        updateInvisibility();
        checkCollisions();
        moveGhosts();
        if (checkCollisionWithGhosts()) {
            lives--;
            if (lives > 0) {
                // Le joueur a encore des vies, réinitialisez la position du joueur
                playerX = 13;
                playerY = 17;
            } else {
                // Gérer la fin de la partie ici (défaite)
                // Réinitialiser le jeu ou afficher un message de défaite
                return;
            }
        }
        repaint();
    }

    private void resetGhostsToCenter() {
        for (Ghost ghost : ghosts) {
            ghost.setX(12);
            ghost.setY(11);
            ghost.setVulnerable(false);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Dessiner le labyrinthe ici
        for (int i = 0; i < labyrinth.length; i++) {
            for (int j = 0; j < labyrinth[i].length; j++) {
                int cellValue = labyrinth[i][j];
                Color dotColor = Color.WHITE;  // Par défaut, la couleur est blanche pour les pacdots
                int dotSize = 10;  // Taille par défaut

                if (cellValue == WALL) {
                    g.setColor(Color.BLACK);
                } else {
                    g.setColor(Color.BLUE);
                }
                
                if (cellValue == TELEPORTER) {
                	g.setColor(Color.RED);
                }

                g.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);

                if (cellValue == PACDOT || cellValue == VIOLET || cellValue == ORANGE || cellValue == GREEN) {
                    if (pacdots[i][j]) {
                        if (cellValue == VIOLET) {
                            dotColor = Color.MAGENTA;  // Violet pacdot
                            dotSize = 20;  // Taille plus grande pour les pacdots violets
                        } else if (cellValue == ORANGE) {
                            dotColor = Color.ORANGE;  // Orange pacdot
                            dotSize = 20;  // Taille plus grande pour les pacdots oranges
                        } else if (cellValue == GREEN) {
                            dotColor = Color.GREEN;  // Vert pacdot
                            dotSize = 20;  // Taille plus grande pour les pacdots verts
                        }

                        g.setColor(dotColor);
                        int dotX = j * cellSize + (cellSize - dotSize) / 2;
                        int dotY = i * cellSize + (cellSize - dotSize) / 2;
                        g.fillOval(dotX, dotY, dotSize, dotSize);
                    }
                }
            }
        }


        // Dessiner le joueur
        g.setColor(isPacManInvisible ? Color.RED : Color.YELLOW);
        //g.setColor(isSuperPacMan ? Color.ORANGE : Color.YELLOW);
        g.fillOval(playerX * cellSize, playerY * cellSize, cellSize, cellSize);

        // Dessiner les fantômes
        for (Ghost ghost : ghosts) {
            g.setColor(ghost.getColor());
            g.fillOval(ghost.getX() * cellSize, ghost.getY() * cellSize, cellSize, cellSize);
        }

        // Afficher le score
        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, 200, 20);

        // Afficher le nombre de pacdots restants
        g.drawString("Pacdots restants: " + pacdotsRemaining, 10, 20);

        // Afficher le nombre de vies restantes
        g.drawString("Vies restantes: " + lives, 10, 40);
    }

    private void checkCollisions() {
        int playerCellX = playerX;
        int playerCellY = playerY;

        int cellValue = labyrinth[playerCellY][playerCellX];
        switch (cellValue) {
            case PACDOT:
                score += 100;
                labyrinth[playerCellY][playerCellX] = PATH;
                pacdotsRemaining--;
                break;
            case VIOLET: // Violet Pacdot
                score += 300;
                labyrinth[playerCellY][playerCellX] = PATH;
                pacdotsRemaining--;
                activateInvisibility();
                break;
            case ORANGE: // Orange Pacdot
                score += 500;
                labyrinth[playerCellY][playerCellX] = PATH;
                pacdotsRemaining--;
                activateSuperPacMan();
                break;
            case GREEN: // Vert Pacdot
                score += 1000;
                labyrinth[playerCellY][playerCellX] = PATH;
                pacdotsRemaining--;
                //TODO
                break;
            case TELEPORTER:
            	// checks which teleporter we are on, so we can teleporter to the other
            	if(playerCellX == 27) {
            		playerX = 0;
            	} else {
            		playerX = 27;
            	}
            	break;
        }
        // Vérification pour obtenir une vie supplémentaire si le score dépasse 5000 points
        if (score >= 5000) {
            lives++;
            score -= 5000; // Soustrayez 5000 points pour éviter d'obtenir des vies supplémentaires à chaque vérification
        }
    }

    private boolean checkCollisionWithGhosts() {
        for (Ghost ghost : ghosts) {
            if (!isPacManInvisible && playerX == ghost.getX() && playerY == ghost.getY()) {
                return true;
            }
        }
        return false;
    }


    private void activateInvisibility() {
        isPacManInvisible = true;
        invisibleStartTime = System.currentTimeMillis();
    }

    private void updateInvisibility() {
        if (isPacManInvisible) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - invisibleStartTime >= INVISIBLE_DURATION) {
                isPacManInvisible = false;
            }
        }
    }

    private void activateSuperPacMan() {
        isSuperPacMan = true;
        superPacManStartTime = System.currentTimeMillis();
    }

    private void updateSuperPacMan() {
        if (isSuperPacMan) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - superPacManStartTime >= SUPER_PACMAN_DURATION) {
                isSuperPacMan = false;
                for (Ghost ghost : ghosts) {
                    ghost.setVulnerable(false);
                }
            }
        }
    }

    private void movePlayer() {
        int newX = playerX;
        int newY = playerY;
        int playerSpeed = 1;
        switch (playerDirection) {
            case 0:
                newX += playerSpeed;
                break;
            case 1:
                newX -= playerSpeed;
                break;
            case 2:
                newY -= playerSpeed;
                break;
            case 3:
                newY += playerSpeed;
                break;
        }

        if (isValidMove(newX, newY)) {
            playerX = newX;
            playerY = newY;

            int cellX = playerX;
            int cellY = playerY;
            if (pacdots[cellY][cellX]) {
                pacdots[cellY][cellX] = false;

                if (pacdotsRemaining == 0) {
                    // Tous les pacdots ont été collectés (victoire)
                }
            }
        }
    }

    static boolean isValidMove(int x, int y) {
        return x >= 0 && x < labyrinth[0].length &&
                y >= 0 && y < labyrinth.length &&
                labyrinth[y][x] != WALL;
    }

    private void moveGhosts() {
        for (Ghost ghost : ghosts) {
            ghost.moveRandomly();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Nécessaire en tant que partie de l'interface KeyListener
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            playerDirection = 1;
        } else if (key == KeyEvent.VK_RIGHT) {
            playerDirection = 0;
        } else if (key == KeyEvent.VK_UP) {
            playerDirection = 2;
        } else if (key == KeyEvent.VK_DOWN) {
            playerDirection = 3;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Nécessaire en tant que partie de l'interface KeyListener
    }

    private void resetGame() {
        playerX = 15;
        playerY = 17;
        playerDirection = 0;
        isPacManInvisible = false;
        invisibleStartTime = 0;
        isSuperPacMan = false;
        superPacManStartTime = 0;
        lives = 3;
        score = 0;

        // Réinitialisez la grille du labyrinthe en utilisant la copie de la grille d'origine
        for (int i = 0; i < labyrinth.length; i++) {
            System.arraycopy(originalLabyrinth[i], 0, labyrinth[i], 0, labyrinth[i].length);
        }

        pacdotsRemaining = 0;
        initializePacdots();
        for (Ghost ghost : ghosts) {
            ghost.setX(12);
            ghost.setY(11);
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Labyrinth Game");
            LabyrinthGame game = new LabyrinthGame();
            frame.add(game);
            int windowWidth = (game.labyrinth[0].length * game.cellSize) + 19;
            int windowHeight = (game.labyrinth.length * game.cellSize) + 20;
            frame.setSize(windowWidth, windowHeight);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
