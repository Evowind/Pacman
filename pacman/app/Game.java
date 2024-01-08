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

/**
 * Classe qui contient toutes les méthodes et informations nécéssaires pour une partie de PacMan.
 * Celle-ci est observable par les classes qui implémentent l'interface GameObserver ainsi que les interfaces ActionListener et KeyListener pour gérer les actions du jeu.
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
    private PacMan pacman;
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
    public Game() {
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
     * Notifie tous les observateurs du jeu.
     */
    private void notifyObservers() {
        for (GameObserver observer : observers) {
            observer.update();
        }
    }

    /**
     * Réinitialise le jeu à ses paramètres initiaux.
     */
    private void resetGame() {
        initializeGame();
        notifyObservers();
    }

    /**
     * Initialise les éléments du jeu.
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
     * Vérifie les collisions entre les différents éléments du jeu.
     */
    private void checkCollisions() {
        int playerCellX = pacman.getPlayerX();
        int playerCellY = pacman.getPlayerY();

        ghosts.checkGhostCollisions(playerCellX, playerCellY);
        pacman.checkCellCollisions(playerCellX, playerCellY);
        handleScoreForExtraLife();
    }

    /**
     * Méthode qui gère la collision entre PacMan et un fantome.
     * Appelée seulement si l'état de pacman est State.NORMAL.
     */
    public void handlePlayerCaught() {
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
     * Méthode qui vérifie si le joueur a gagné.
     */
    private void checkWin() {
        if (labyrinth.countPacdots() == 0) {
            JOptionPane.showMessageDialog(gui, "Vous avez gagné !", "Partie terminée", JOptionPane.INFORMATION_MESSAGE);
            resetGame();
        }
    }

    /**
     * Méthode qui gère le score nécessaire pour obtenir une vie supplémentaire.
     */
    private void handleScoreForExtraLife() {
        if (score >= 5000) {
            lives++;
            score -= 5000;
        }
    }

    /**
     * Méthode de ActionListener appelée à chaque "tick" du timer, gérant les mouvements des éléments du jeu.
     *
     * @param e l'événement d'action.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        pacman.state.update(pacman);
        pacman.movePlayer();
        checkCollisions();
        checkWin();

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
     * @param e L'événement de touche tapée.
     */
    @Override
    public void keyTyped(KeyEvent e) {
        // Not needed
    }

    /**
     * Méthode non-utilisée mais nécéssaire pour l'implementation de KeyListener.
     *
     * @param e L'événement de libération de touche.
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
    public int getLives() {
        return lives;
    }

    /**
     * Getter pour l'entité PacMan.
     *
     * @return l'objet PacMan.
     */
    public PacMan getPacman() {
        return pacman;
    }
}
