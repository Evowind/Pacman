package pacman;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class Game extends JPanel implements ActionListener, KeyListener {
    public static final int CELL_SIZE = 30;
    private int playerX, playerY;
    static int[][] originalLabyrinth;
    private boolean[][] pacdots;
    List<Ghost> ghosts;
    private int pacdotsRemaining;
    private int score;
    private PacGum pacGum;
    private int lives;
    private int playerDirection;
    private Color collision;

    Game() {
        Timer timer = new Timer(100, this);
        timer.start();
        playerX = 15;
        playerY = 17;
        playerDirection = 0;
        lives = 3;
        score = 0;
        pacGum = new PacGum();
        setFocusable(true);
        addKeyListener(this);
        initializeGame();
    }

    public void resetGame() {
        pacGum.resetSuperPacMan();
        pacGum.resetInvisibility();
        playerX = 15;
        playerY = 17;
        playerDirection = 0;
        initializeGame();
        score = 0;
        lives = 3;
        repaint();
    }

    private void initializeGame() {
        originalLabyrinth = Labyrinth.getCopy();
        pacdots = new boolean[originalLabyrinth.length][originalLabyrinth[0].length];
        initializePacdots();
        ghosts = new ArrayList<>();
        initializeGhosts();
    }

    void initializePacdots() {
        pacdotsRemaining = 0;
        for (int i = 0; i < originalLabyrinth.length; i++) {
            for (int j = 0; j < originalLabyrinth[i].length; j++) {
                if (originalLabyrinth[i][j] == Labyrinth.PACDOT || originalLabyrinth[i][j] == Labyrinth.VIOLET ||
                        originalLabyrinth[i][j] == Labyrinth.GREEN || originalLabyrinth[i][j] == Labyrinth.ORANGE) {
                    pacdots[i][j] = true;
                    pacdotsRemaining++;
                }
            }
        }
    }

    void initializeGhosts() {
        // Le cas d'un reset
        if (ghosts.size() > 0) {
            for (Ghost ghost : ghosts) {
                ghost.setX(12);
                ghost.setY(11);
            }
            // Le cas d'une nouvelle partie
        } else {
            ghosts.add(new Ghost(12, 11, Color.CYAN));
            ghosts.add(new Ghost(13, 11, Color.WHITE));
            ghosts.add(new Ghost(14, 11, Color.GRAY));
            ghosts.add(new Ghost(15, 11, Color.PINK));
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
        if (pacdotsRemaining == 0) {
            JOptionPane.showMessageDialog(this, "Vous avez gagné !", "Partie terminée", JOptionPane.INFORMATION_MESSAGE);
            resetGame();
        }
        if (isValidMove(newX, newY)) {
            playerX = newX;
            playerY = newY;

            int cellX = playerX;
            int cellY = playerY;
            if (pacdots[cellY][cellX]) {
                pacdots[cellY][cellX] = false;
            }

        }
    }

    static boolean isValidMove(int x, int y) {
        return x >= 0 && x < originalLabyrinth[0].length &&
                y >= 0 && y < originalLabyrinth.length &&
                originalLabyrinth[y][x] != Labyrinth.WALL;
    }

    private void moveGhosts() {
        for (Ghost ghost : ghosts) {
            ghost.moveRandomly();
        }
    }

    public void resetAllGhostsToCenter() {
        for (Ghost ghost : ghosts) {
            ghost.setX(12);
            ghost.setY(11);
            ghost.setVulnerable(false);
        }
    }

    private void resetGhostToCenter(Color color) {
        for (Ghost ghost : ghosts) {
            if (ghost.getColor() == color) {
                ghost.setX(12);
                ghost.setY(11);
                ghost.setVulnerable(false);
            }
        }
    }

    private void checkCollisions() {
        int playerCellX = playerX;
        int playerCellY = playerY;

        // Cheque les collisions avec les fantomes
        for (Ghost ghost : ghosts) {
            int ghostCellX = ghost.getX();
            int ghostCellY = ghost.getY();

            if (playerCellX == ghostCellX && playerCellY == ghostCellY) {
                if (pacGum.isSuperPacMan()) {
                    // Le fantom est mangé par le Super PacMan
                    score += 400;
                    resetGhostToCenter(ghost.getColor());
                } else if (!pacGum.isPacManInvisible()) {
                    // PacMan est mangeé
                    handlePlayerCaught();
                }
            }
        }

        int cellValue = originalLabyrinth[playerCellY][playerCellX];
        switch (cellValue) {
            case Labyrinth.PACDOT:
                score += 100;
                originalLabyrinth[playerCellY][playerCellX] = Labyrinth.PATH;
                pacdotsRemaining--;
                break;
            case Labyrinth.VIOLET:
                score += 300;
                originalLabyrinth[playerCellY][playerCellX] = Labyrinth.PATH;
                pacdotsRemaining--;
                pacGum.activateInvisibility();
                break;
            case Labyrinth.ORANGE:
                score += 500;
                originalLabyrinth[playerCellY][playerCellX] = Labyrinth.PATH;
                pacdotsRemaining--;
                pacGum.activateSuperPacMan();
                break;
            case Labyrinth.GREEN:
                score += 1000;
                originalLabyrinth[playerCellY][playerCellX] = Labyrinth.PATH;
                pacdotsRemaining--;
                pacGum.activateGreenPacGum(this);
                break;
            case Labyrinth.TELEPORTER:
                if (playerCellX == 27) {
                    playerX = 1;
                } else {
                    playerX = 26;
                }
                break;
            default:
                break;
        }

        // Vérification pour obtenir une vie supplémentaire si le score dépasse 5000 points
        if (score >= 5000) {
            lives++;
            score -= 5000; // Soustrayez 5000 points pour éviter d'obtenir des vies supplémentaires à chaque vérification
        }
    }

    private void handlePlayerCaught() {
        lives--;
        if (lives <= 0) {
            // Game over
            JOptionPane.showMessageDialog(this, "Vous avez perdu !", "Partie terminée", JOptionPane.INFORMATION_MESSAGE);
            resetGame();
        } else {
            // Reset la position du joueur
            playerX = 15;
            playerY = 17;
            playerDirection = 0;
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Dessiner le labyrinthe
        for (int i = 0; i < originalLabyrinth.length; i++) {
            for (int j = 0; j < originalLabyrinth[i].length; j++) {
                int cellValue = originalLabyrinth[i][j];
                drawCell(g2d, j, i, cellValue);
            }
        }

        // Dessiner le joueur
        if (pacGum.isSuperPacMan()) {
            g2d.setColor(Color.RED);
        } else {
            g2d.setColor(pacGum.isPacManInvisible() ? Color.ORANGE : Color.YELLOW);
        }
        int startAngle;
        int extentAngle;

        switch (playerDirection) {
            case 0: // Droite
                startAngle = 45;
                extentAngle = 270;
                break;
            case 1: // Gauche
                startAngle = 225;
                extentAngle = 270;
                break;
            case 2: // Haut
                startAngle = 135;
                extentAngle = 270;
                break;
            case 3: // Bas
                startAngle = 315;
                extentAngle = 270;
                break;
            default:
                startAngle = 0;
                extentAngle = 360;
                break;
        }
        g2d.fillArc(playerX * CELL_SIZE, playerY * CELL_SIZE, CELL_SIZE, CELL_SIZE, startAngle, extentAngle);

        // Dessiner les fantômes
        for (Ghost ghost : ghosts) {
            if (pacGum.isSuperPacMan()) {
                g2d.setColor(Color.BLUE.darker());
            } else {
                g2d.setColor(ghost.isVulnerable() ? Color.BLUE.darker() : ghost.getColor());
            }
            g2d.fillRoundRect(ghost.getX() * CELL_SIZE, ghost.getY() * CELL_SIZE, CELL_SIZE, CELL_SIZE, 10, 10);
        }

        // Dessiner le score et le nombre de vies restantes
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Segoe UI", Font.BOLD, 18));

        String scoreText = "Score: " + score;
        String livesText = "Lives: " + lives;
        String pacdotsRemainingText = "Pacdots restantes: " + pacdotsRemaining;

        int lineHeight = 25;
        int margin = 15;
        int startY = 25;

        g2d.drawString(scoreText, margin, startY);
        g2d.drawString(livesText, margin + (5*lineHeight), startY );
        g2d.drawString(pacdotsRemainingText, margin + (9 * lineHeight), startY );


    }

    private void drawCell(Graphics2D g2d, int x, int y, int cellValue) {
        switch (cellValue) {
            case Labyrinth.WALL:
                g2d.setColor(Color.BLUE);
                g2d.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                break;

            case Labyrinth.PACDOT:
                g2d.setColor(Color.BLACK);
                g2d.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                drawPacdot(g2d, x, y, Color.WHITE);
                break;

            case Labyrinth.VIOLET:
                g2d.setColor(Color.BLACK);
                g2d.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                drawPacdot(g2d, x, y, Color.MAGENTA);
                break;

            case Labyrinth.ORANGE:
                g2d.setColor(Color.BLACK);
                g2d.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                drawPacdot(g2d, x, y, Color.ORANGE);
                break;

            case Labyrinth.GREEN:
                g2d.setColor(Color.BLACK);
                g2d.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                drawPacdot(g2d, x, y, Color.GREEN);
                break;

            case Labyrinth.PATH:
                g2d.setColor(Color.BLACK);
                g2d.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                break;

            case Labyrinth.TELEPORTER:
                g2d.setColor(Color.RED);
                g2d.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                break;

            default:
                break;
        }
    }

    private void drawPacdot(Graphics2D g2d, int x, int y, Color color) {
        g2d.setColor(color);
        int pacdotSize = CELL_SIZE / 3;
        g2d.fillOval((x * CELL_SIZE) + (CELL_SIZE / 2) - pacdotSize / 2,
                (y * CELL_SIZE) + (CELL_SIZE / 2) - pacdotSize / 2,
                pacdotSize, pacdotSize);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        pacGum.updateInvisibility();
        pacGum.updateSuperPacMan();
        movePlayer();
        checkCollisions();
        moveGhosts();
        checkCollisions();
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        switch (key) {
            case KeyEvent.VK_UP:
                if (playerY != 0 && (originalLabyrinth[playerY - 1][playerX] != Labyrinth.WALL)) playerDirection = 2;
                break;

            case KeyEvent.VK_DOWN:
                if (playerY != 30 && (originalLabyrinth[playerY + 1][playerX] != Labyrinth.WALL)) playerDirection = 3;
                break;

            case KeyEvent.VK_LEFT:
                if (playerX != 0 && (originalLabyrinth[playerY][playerX - 1] != Labyrinth.WALL)) playerDirection = 1;
                break;

            case KeyEvent.VK_RIGHT:
                if (playerX != 27 && (originalLabyrinth[playerY][playerX + 1] != Labyrinth.WALL)) playerDirection = 0;
                break;

            case KeyEvent.VK_R:
                resetGame();
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not needed
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Not needed
    }
}