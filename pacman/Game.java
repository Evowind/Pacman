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
        ghosts.add(new Ghost(12, 11, Color.CYAN));
        ghosts.add(new Ghost(13, 11, Color.WHITE));
        ghosts.add(new Ghost(14, 11, Color.GRAY));
        ghosts.add(new Ghost(15, 11, Color.PINK));
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
                    // All pacdots have been collected (victory)
                }
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

        // Check for collisions with ghosts
        for (Ghost ghost : ghosts) {
            int ghostCellX = ghost.getX();
            int ghostCellY = ghost.getY();

            if (playerCellX == ghostCellX && playerCellY == ghostCellY) {
                if (ghost.isVulnerable()) {
                    // Ghost is eaten by super pacman
                    // Increase score
                } else {
                    // Pacman is caught by ghost
                    handlePlayerCaught();
                }
            }
        }
    }

    private void handlePlayerCaught() {
        lives--;
        if (lives == 0) {
            // Game over
            JOptionPane.showMessageDialog(this, "Vous avez perdu !", "Partie terminée", JOptionPane.INFORMATION_MESSAGE);
            new Game();
        } else {
            // Reset player position and continue
            playerX = 15;
            playerY = 17;
            playerDirection = 0;
        }
    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Draw the labyrinth
        for (int i = 0; i < originalLabyrinth.length; i++) {
            for (int j = 0; j < originalLabyrinth[i].length; j++) {
                int cellValue = originalLabyrinth[i][j];
                drawCell(g2d, j, i, cellValue);
            }
        }

        // Draw the player
        g2d.setColor(Color.YELLOW);
        g2d.fillArc(playerX * CELL_SIZE, playerY * CELL_SIZE, CELL_SIZE, CELL_SIZE, 45, 270);

        // Draw ghosts
        for (Ghost ghost : ghosts) {
            g2d.setColor(ghost.isVulnerable() ? Color.BLUE : ghost.getColor());
            g2d.fillRoundRect(ghost.getX() * CELL_SIZE, ghost.getY() * CELL_SIZE, CELL_SIZE, CELL_SIZE, 10, 10);
        }

        // Draw the score and lives
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.PLAIN, 20));
        g2d.drawString("Score: " + score, 10, 20);
        g2d.drawString("Lives: " + lives, 10, 50);
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
                drawPacdot(g2d, x, y);
                break;

            case Labyrinth.VIOLET:
                drawGhost(g2d, x, y, Color.MAGENTA);
                break;

            case Labyrinth.ORANGE:
                drawGhost(g2d, x, y, Color.ORANGE);
                break;

            case Labyrinth.GREEN:
                drawGhost(g2d, x, y, Color.GREEN);
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

    private void drawPacdot(Graphics2D g2d, int x, int y) {
        g2d.setColor(Color.WHITE);
        int pacdotSize = CELL_SIZE / 3;
        g2d.fillOval((x * CELL_SIZE) + (CELL_SIZE / 2) - pacdotSize / 2,
                (y * CELL_SIZE) + (CELL_SIZE / 2) - pacdotSize / 2,
                pacdotSize, pacdotSize);
    }

    private void drawGhost(Graphics2D g2d, int x, int y, Color color) {
        g2d.setColor(color);
        g2d.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
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
                playerDirection = 2;
                break;

            case KeyEvent.VK_DOWN:
                playerDirection = 3;
                break;

            case KeyEvent.VK_LEFT:
                playerDirection = 1;
                break;

            case KeyEvent.VK_RIGHT:
                playerDirection = 0;
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