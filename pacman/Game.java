package pacman;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class Game implements ActionListener, KeyListener {
    private final List<GameObserver> observers = new ArrayList<>();

    public PacManObservable pacman;
    public static final int CELL_SIZE = 30;
    static Cell[][] labyrinth;
    List<Ghost> ghosts;
    private int pacdotsRemaining;
    private int score;
    public final PacGum pacGum;
    private int lives;
    private GUI gui;

    Game() {
        Timer timer = new Timer(100, this);
        timer.start();
        pacman = new PacManObservable(15, 17, 0, this);
        lives = 3;
        score = 0;
        pacGum = new PacGum();
        initializeGame();

        gui = new GUI(this);
        addObserver(gui);
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
        // put in state
        pacGum.resetSuperPacMan();
        pacGum.resetInvisibility();
        pacman = new PacManObservable(15, 17, 0, this);
        initializeGame();
        score = 0;
        lives = 3;
        notifyObservers();
    }

    private void initializeGame() {
        labyrinth = Labyrinth.getCopy();
        initializePacdots();
        ghosts = new ArrayList<>();
        initializeGhosts();
    }

    void initializePacdots() {
        pacdotsRemaining = 0;
        for (int i = 0; i < labyrinth.length; i++) {
            for (int j = 0; j < labyrinth[i].length; j++) {
                if (labyrinth[i][j] == Cell.PACDOT || labyrinth[i][j] == Cell.PURPLE ||
                        labyrinth[i][j] == Cell.GREEN || labyrinth[i][j] == Cell.ORANGE) {
                    pacdotsRemaining++;
                }
            }
        }
    }

    void initializeGhosts() {
        // Le cas d'un reset
        if (!ghosts.isEmpty()) {
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

    static boolean isValidMove(int x, int y) {
        return x >= 0 && x < labyrinth[0].length &&
                y >= 0 && y < labyrinth.length &&
                labyrinth[y][x] != Cell.WALL;
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
        int playerCellX = pacman.getPlayerX();
        int playerCellY = pacman.getPlayerY();

        checkGhostCollisions(playerCellX, playerCellY);
        pacman.checkCellCollisions(playerCellX, playerCellY);
        handleScoreForExtraLife();
    }

    // maybe do something with this in Ghost class
    private void checkGhostCollisions(int playerCellX, int playerCellY) {
        for (Ghost ghost : ghosts) {
            int ghostCellX = ghost.getX();
            int ghostCellY = ghost.getY();

            if (playerCellX == ghostCellX && playerCellY == ghostCellY) {
                handleGhostCollision(ghost);
            }
        }
    }

    private void handlePlayerCaught() {
        lives--;
        if (lives <= 0) {
            // Game over
            JOptionPane.showMessageDialog(gui, "Vous avez perdu !", "Partie terminée", JOptionPane.INFORMATION_MESSAGE);
            resetGame();
        } else {
            // Reset la position du joueur
            pacman = new PacManObservable(15, 17, 0, this);
        }
    }

    public void handleWin(){
        JOptionPane.showMessageDialog(gui, "Vous avez gagné !", "Partie terminée", JOptionPane.INFORMATION_MESSAGE);
    }

    private void handleGhostCollision(Ghost ghost) {
        // state
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

    public void applyGreenPacGumEffect() {
        int[][] swaps = {
                {1, 7, 9, 1}, {1, 8, 10, 1}, {1, 9, 11, 1},
                {1, 10, 12, 1}, {1, 11, 13, 1}, {1, 16, 9, 26},
                {1, 17, 10, 26}, {1, 18, 11, 26}, {1, 19, 12, 26},
                {1, 20, 13, 26}, {5, 13, 1, 13}, {5, 14, 1, 14},
                {6, 9, 6, 12}, {7, 9, 7, 12}, {9, 9, 9, 12},
                {10, 9, 10, 12}, {6, 15, 6, 18}, {7, 15, 7, 18},
                {9, 15, 9, 18}, {10, 15, 10, 18}, {20, 7, 23, 4},
                {20, 8, 23, 5}, {20, 13, 23, 13}, {20, 14, 23, 14},
                {20, 19, 23, 22}, {20, 20, 23, 23}, {26, 10, 26, 7},
                {26, 11, 26, 8}, {29, 13, 26, 13}, {29, 14, 26, 14},
                {26, 16, 26, 19}, {26, 17, 26, 20}, {23, 10, 24, 12},
                {23, 11, 25, 12}, {23, 16, 24, 15}, {23, 17, 25, 15}
        };

        for (int[] swap : swaps) {
            swapValues(swap[0], swap[1], swap[2], swap[3]);
        }
    }

    private static void swapValues(int srcRow, int srcCol, int destRow, int destCol) {
        Cell[][] labyrinth = Game.labyrinth;
        // Utilisation de la décomposition de tuples pour échanger les valeurs
        Cell temp = labyrinth[srcRow][srcCol];
        labyrinth[srcRow][srcCol] = labyrinth[destRow][destCol];
        labyrinth[destRow][destCol] = temp;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // state
        pacGum.updateInvisibility();
        pacGum.updateSuperPacMan();
        pacman.movePlayer();
        checkCollisions();
        moveGhosts();
        checkCollisions();
        notifyObservers();
    }

    public void decrPacDot() {
        pacdotsRemaining--;
    }

    public void setScore(int score) {
        this.score = score;
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

    public PacGum getPacGum() {
        return pacGum;
    }

    public List<Ghost> getGhosts() {
        return ghosts;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int playerY = pacman.getPlayerY();
        int playerX = pacman.getPlayerX();
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_UP) {
            if (playerY > 0 && labyrinth[playerY - 1][playerX] != Cell.WALL) {
                pacman.setPlayerDirection(2);
            }
        } else if (key == KeyEvent.VK_DOWN) {
            if (playerY < labyrinth.length - 1 && labyrinth[playerY + 1][playerX] != Cell.WALL) {
                pacman.setPlayerDirection(3);
            }
        } else if (key == KeyEvent.VK_LEFT) {
            if (playerX > 0 && labyrinth[playerY][playerX - 1] != Cell.WALL) {
                pacman.setPlayerDirection(1);
            }
        } else if (key == KeyEvent.VK_RIGHT) {
            if (playerX < labyrinth[0].length - 1 && labyrinth[playerY][playerX + 1] != Cell.WALL) {
                pacman.setPlayerDirection(0);
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