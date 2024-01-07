package pacman.app;

import pacman.entities.Labyrinth;
import pacman.entities.PacMan;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class Game implements ActionListener, KeyListener {
    private final List<GameObserver> observers = new ArrayList<>();

    public PacMan pacman;
    // TODO maybe import those to GUI and make them private here?
    public Labyrinth labyrinth;
    public GhostList ghosts;
    //
    private int pacdotsRemaining;
    public int score;
    private int lives;
    private final GUI gui;

    Game() {
        Timer timer = new Timer(100, this);
        timer.start();
        initializeGame();

        gui = new GUI(this);
        addObserver(gui);
    }


    public void addObserver(GameObserver observer) {
        observers.add(observer);
    }
    void notifyObservers() {
        for (GameObserver observer : observers) {
            observer.update();
        }
    }

    public void resetGame() {
        initializeGame();
        notifyObservers();
    }

    private void initializeGame() {
        pacman = new PacMan(15, 17, 0, this);
        labyrinth = new Labyrinth(pacman);
        lives = 3;
        score = 0;
        pacdotsRemaining = labyrinth.countPacdots();
        ghosts = new GhostList(pacman, this);
        ghosts.initializeGhosts();
    }

    private void checkCollisions() {
        int playerCellX = pacman.getPlayerX();
        int playerCellY = pacman.getPlayerY();

        ghosts.checkGhostCollisions(playerCellX, playerCellY);
        pacman.checkCellCollisions(playerCellX, playerCellY);
        handleScoreForExtraLife();
    }

    void handlePlayerCaught() {
        lives--;
        if (lives <= 0) {
            // Game over
            JOptionPane.showMessageDialog(gui, "Vous avez perdu !", "Partie terminée", JOptionPane.INFORMATION_MESSAGE);
            resetGame();
        } else {
            // Reset la position du joueur
            pacman = new PacMan(15, 17, 0, this);
        }
    }

    public void handleWin(){
        JOptionPane.showMessageDialog(gui, "Vous avez gagné !", "Partie terminée", JOptionPane.INFORMATION_MESSAGE);
    }

    private void handleScoreForExtraLife() {
        if (score >= 5000) {
            lives++;
            score -= 5000;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //
        pacman.state.update(pacman);
        pacman.movePlayer();
        //
        checkCollisions();
        ghosts.moveGhosts();
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


    @Override
    public void keyPressed(KeyEvent e) {
        int playerY = pacman.getPlayerY();
        int playerX = pacman.getPlayerX();
        int key = e.getKeyCode();

        int output = labyrinth.processEvent(key, playerX, playerY);

        if (output == -1) resetGame();
        else if (output != -2) pacman.setPlayerDirection(output);
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