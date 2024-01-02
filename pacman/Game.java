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
    private final List<GameObserver> observers = new ArrayList<>();

    public static final int CELL_SIZE = 30;
    private int playerX, playerY;
    static Cell[][] originalLabyrinth;
    private boolean[][] pacdots;
    List<Ghost> ghosts;
    private int pacdotsRemaining;
    private int score;
    private final PacGum pacGum;
    private int lives;
    private int playerDirection;
    private GUI gui;

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
        setFocusTraversalKeysEnabled(false);
        initializeGame();
        requestFocus();

        SwingUtilities.invokeLater(() -> {
            gui = new GUI(this);
            addObserver(gui);
        });
    }


    public void addObserver(GameObserver observer) {
        observers.add(observer);
    }
    private void notifyObservers() {
        for (GameObserver observer : observers) {
            observer.update();
        }
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
                if (originalLabyrinth[i][j] == Cell.PACDOT || originalLabyrinth[i][j] == Cell.PURPLE ||
                        originalLabyrinth[i][j] == Cell.GREEN || originalLabyrinth[i][j] == Cell.ORANGE) {
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
                originalLabyrinth[y][x] != Cell.WALL;
    }

    private void moveGhosts() {
        for (Ghost ghost : ghosts) {
            if(ghost.isCanMove()) ghost.moveRandomly();
            else ghost.setCanMove(true);
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

        checkGhostCollisions(playerCellX, playerCellY);
        checkCellCollisions(playerCellX, playerCellY);
        handleScoreForExtraLife();
    }

    private void checkGhostCollisions(int playerCellX, int playerCellY) {
        for (Ghost ghost : ghosts) {
            int ghostCellX = ghost.getX();
            int ghostCellY = ghost.getY();

            if (playerCellX == ghostCellX && playerCellY == ghostCellY) {
                handleGhostCollision(ghost);
            }
        }
    }

    private void checkCellCollisions(int playerCellX, int playerCellY) {
        Cell cellValue = originalLabyrinth[playerCellY][playerCellX];
        switch (cellValue) {
            case PACDOT:
                score += 100;
                originalLabyrinth[playerCellY][playerCellX] = Cell.EMPTY;
                pacdotsRemaining--;
                break;
            case PURPLE:
                score += 300;
                originalLabyrinth[playerCellY][playerCellX] = Cell.EMPTY;
                pacdotsRemaining--;
                pacGum.activateInvisibility();
                break;
            case ORANGE:
                score += 500;
                originalLabyrinth[playerCellY][playerCellX] = Cell.EMPTY;
                pacdotsRemaining--;
                pacGum.activateSuperPacMan();
                break;
            case GREEN:
                score += 1000;
                originalLabyrinth[playerCellY][playerCellX] = Cell.EMPTY;
                pacdotsRemaining--;
                pacGum.activateGreenPacGum(this);
                break;
            case TELEPORTER:
                if (playerCellX == 27) {
                    playerX = 1;
                } else {
                    playerX = 26;
                }
                break;
            default:
                break;
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

    private void handleGhostCollision(Ghost ghost) {
        if (pacGum.isSuperPacMan()) {
            // Le fantome est mangé par le Super PacMan
            score += 400;
            resetGhostToCenter(ghost.getColor());
        } else if (!pacGum.isPacManInvisible()) {
            // PacMan est mangé
            handlePlayerCaught();
        }
    }

    private void handleScoreForExtraLife() {
        if (score >= 5000) {
            lives++;
            score -= 5000;
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        pacGum.updateInvisibility();
        pacGum.updateSuperPacMan();
        movePlayer();
        checkCollisions();
        moveGhosts();
        checkCollisions();
        notifyObservers();
    }

    public int getScore() {
        return score;
    }

    public int getLives() {
        return lives;
    }

    public int getPacdotsRemaining() {
        return pacdotsRemaining;
    }

    public int getPlayerX() {
        return playerX;
    }

    public int getPlayerY() {
        return playerY;
    }

    public int getPlayerDirection() {
        return playerDirection;
    }

    public PacGum getPacGum() {
        return pacGum;
    }

    public List<Ghost> getGhosts() {
        return ghosts;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_UP) {
            if (playerY > 0 && originalLabyrinth[playerY - 1][playerX] != Cell.WALL) {
                playerDirection = 2;
            }
        } else if (key == KeyEvent.VK_DOWN) {
            if (playerY < originalLabyrinth.length - 1 && originalLabyrinth[playerY + 1][playerX] != Cell.WALL) {
                playerDirection = 3;
            }
        } else if (key == KeyEvent.VK_LEFT) {
            if (playerX > 0 && originalLabyrinth[playerY][playerX - 1] != Cell.WALL) {
                playerDirection = 1;
            }
        } else if (key == KeyEvent.VK_RIGHT) {
            if (playerX < originalLabyrinth[0].length - 1 && originalLabyrinth[playerY][playerX + 1] != Cell.WALL) {
                playerDirection = 0;
            }
        } else if (key == KeyEvent.VK_R) {
            resetGame();
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