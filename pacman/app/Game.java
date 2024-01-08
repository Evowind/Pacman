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
 * Classe qui contient toutes les méthodes et informations nécéssaires pour une partie de PacMan.
 * Celle-ci est observable par les classes qui implémentent l'interface GameObserver.
 */
public class Game implements ActionListener, KeyListener {
    /**
     * Liste de tout les observeurs.
     */
    private final List<GameObserver> observers = new ArrayList<>();
    /**
     * Objet JPanel utilisé pour dessiner l'interface graphique.
     */
    private final GUI gui;
    /**
     * Objet contenant les méthodes et informations reliées a l'entité PacMan.
     */
    public PacMan pacman;
    /**
     * Objet contenant les méthodes et informations reliées au labyrinthe.
     */
    public Labyrinth labyrinth;
    /**
     * Objet contenant les méthodes et informations reliées aux fantomes.
     */
    public GhostList ghosts;
    /**
     * Variable contenant le score du joueur.
     */
    private int score;
    /**
     * Variable contenant le nombre de vies du joueur.
     */
    private int lives;

    /**
     * Constructeur de base de la classe Game.
     */
    Game() {
        Timer timer = new Timer(100, this);
        timer.start();
        initializeGame();

        gui = new GUI(this);
        addObserver(gui);
    }

    /**
     * Méthode qui ajoute un observeur a la liste d'observeurs.
     *
     * @param observer observeur que l'on souhaite ajouter.
     */
    public void addObserver(GameObserver observer) {
        observers.add(observer);
    }

    /**
     * Méthode qui notifier tout les observeurs.
     */
    private void notifyObservers() {
        for (GameObserver observer : observers) {
            observer.update();
        }
    }

    /**
     * Méthode qui créer une partie.
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
     * Méthode qui réinitialiser la partie.
     */
    private void resetGame() {
        initializeGame();
        notifyObservers();
    }

    /**
     * Méthode qui vérifier les collision de l'entité PacMan.
     */
    private void checkCollisions() {
        int playerCellX = pacman.getPlayerX();
        int playerCellY = pacman.getPlayerY();

        ghosts.checkGhostCollisions(playerCellX, playerCellY);
        pacman.checkCellCollisions(playerCellX, playerCellY);
        handleScoreForExtraLife();
    }

    /**
     * Méthode qui gérer la collision entre PacMan et un fantome.
     * Appelée seulement si l'état de pacman est State.NORMAL.
     */
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
     * Méthode qui vérifier si le joueur a gagné.
     */
    private void checkWin(){
        if (labyrinth.countPacdots() == 0) {
            JOptionPane.showMessageDialog(gui, "Vous avez gagné !", "Partie terminée", JOptionPane.INFORMATION_MESSAGE);
            resetGame();
        }
    }

    /**
     * Méthode qui ajoute une vie si le score est au dessus d'un certain seuil.
     */
    private void handleScoreForExtraLife() {
        if (score >= 5000) {
            lives++;
            score -= 5000;
        }
    }

    /**
     * Méthode de ActionListener qui met a jour la partie.
     *
     * @param e l'événement qui est a traiter.
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
     * Méthode KeyListener pour gérer l'entrée de l'utilisateur.
     *
     * @param e l'événement qui est a traiter.
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
     * Méthode non-utilisée mais nécéssaire pour l'implementation de KeyListener.
     *
     * @param e l'événement qui est a traiter.
     */
    @Override
    public void keyTyped(KeyEvent e) {
        // Not needed
    }

    /**
     * Méthode non-utilisée mais nécéssaire pour l'implementation de KeyListener.
     *
     * @param e l'événement qui est a traiter.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        // Not needed
    }

    /**
     * Setter pour la variable score.
     *
     * @param score nouveau score.
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Getter pour la variable score.
     *
     * @return score actuel.
     */
    public int getScore() {
        return score;
    }

    /**
     * Getter pour la variable lives.
     *
     * @return nombre de vies actuel.
     */
    int getLives() {
        return lives;
    }
}