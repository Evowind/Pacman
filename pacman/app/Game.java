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

// TODO maybe separating app/entities is not a good idea because it forces the use of public access modifiers as default(package) is not an option anymore

/**
 * Class containing all the methods and information necessary for a game of PacMan.
 */
public class Game implements ActionListener, KeyListener {
    /**
     * List of all observers.
     */
    private final List<GameObserver> observers = new ArrayList<>();
    /**
     * Object used to display the game.
     */
    private final GUI gui;
    // TODO maybe import pacman, labyrinth, ghosts to GUI and make them private here?
    /**
     * Object containing methods and information related to the PacMan entity.
     */
    public PacMan pacman;
    /**
     * Object containing methods and information related to the maze.
     */
    public Labyrinth labyrinth;
    /**
     * Object containing methods and information about the game's ghosts.
     */
    public GhostList ghosts;
    /**
     * Current score.
     */
    private int score;
    /**
     * Current number of lives.
     */
    private int lives;

    /**
     * Class constructor.
     */
    Game() {
        Timer timer = new Timer(100, this);
        timer.start();
        initializeGame();

        gui = new GUI(this);
        addObserver(gui);
    }

    /**
     * Method used to add an observer to the observer list
     *
     * @param observer Observer that we want to add.
     */
    public void addObserver(GameObserver observer) {
        observers.add(observer);
    }

    /**
     * Method used to notify all observers.
     */
    private void notifyObservers() {
        for (GameObserver observer : observers) {
            observer.update();
        }
    }

    /**
     * Method used to reset the game.
     */
    private void resetGame() {
        initializeGame();
        notifyObservers();
    }

    /**
     * Method used to start a new game.
     */
    private void initializeGame() {
        pacman = new PacMan(15, 17, 0, this);
        labyrinth = new Labyrinth();
        lives = 3;
        score = 0;
        ghosts = new GhostList(pacman, this);
        ghosts.initializeGhosts();
    }

    /**
     * Method used to check collision between PacMan and other entities.
     */
    private void checkCollisions() {
        int playerCellX = pacman.getPlayerX();
        int playerCellY = pacman.getPlayerY();

        ghosts.checkGhostCollisions(playerCellX, playerCellY);
        pacman.checkCellCollisions(playerCellX, playerCellY);
        handleScoreForExtraLife();
    }

    /**
     * Method used to handle PacMan-Ghost collision.
     */
    // TODO Currently accessible by package, is it possible to make it private ? dont think so
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

    /**
     * Method used to check for player victory.
     */
    private void checkWin(){
        if (labyrinth.countPacdots() == 0) {
            JOptionPane.showMessageDialog(gui, "Vous avez gagné !", "Partie terminée", JOptionPane.INFORMATION_MESSAGE);
            resetGame();
        }
    }

    /**
     * Method used to add lives when score is above a certain threshold.
     */
    private void handleScoreForExtraLife() {
        if (score >= 5000) {
            lives++;
            score -= 5000;
        }
    }

    /**
     * ActionListener method used to update the game.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        pacman.state.update(pacman);
        pacman.movePlayer();
        checkWin();

        checkCollisions();
        ghosts.moveGhosts();
        checkCollisions();
        notifyObservers();
    }

    /**
     * Setter for score variable.
     *
     * @param score New score.
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Getter for score variable.
     *
     * @return Current score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Getter for lives variable.
     *
     * @return Current number of lives.
     */
    int getLives() {
        return lives;
    }

    /**
     * KeyListener method to handle user input.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int playerY = pacman.getPlayerY();
        int playerX = pacman.getPlayerX();
        int key = e.getKeyCode();

        int output = labyrinth.processEvent(key, playerX, playerY);

        if (output == -1) resetGame();
        else if (output != -2) pacman.setPlayerDirection(output);
    }

    /**
     * Unused method required when implementing KeyListener.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyTyped(KeyEvent e) {
        // Not needed
    }

    /**
     * Unused method required when implementing KeyListener.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(KeyEvent e) {
        // Not needed
    }
}