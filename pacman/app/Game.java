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
 * La classe Game représente le jeu Pac-Man avec ses éléments principaux tels que le Pac-Man, le labyrinthe,
 * les fantômes, le score, les vies et l'interface graphique.
 * <p>
 * Cette classe implémente les interfaces ActionListener et KeyListener pour gérer les actions du jeu.
 */
//TODO maybe separating app/entities is not a good idea because it forces the use of public access modifiers as default(package) is not an option anymore

public class Game implements ActionListener, KeyListener {
    /**
     * Liste des observateurs.
     */
    private final List<GameObserver> observers = new ArrayList<>();
    /**
     * Interface graphique du jeu.
     */
    private final GUI gui;
    /**
     * Instance du PacManObservable représentant le Pac-Man.
     */
    private PacMan pacman;

    /**
     * Instance du labyrinthe.
     */
    public Labyrinth labyrinth;

    /**
     * Liste des fantômes.
     */
    public GhostList ghosts;

    /**
     * Score du joueur.
     */
    private int score;

    /**
     * Nombre de vies restantes.
     */
    private int lives;

    public Game() {
        Timer timer = new Timer(100, this);
        timer.start();
        initializeGame();

        gui = new GUI(this);
        addObserver(gui);
    }

    /**
     * Ajoute un observateur au jeu.
     *
     * @param observer L'observateur à ajouter.
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
     * Gère le cas où le joueur est capturé par un fantôme.
     */
    // TODO Currently accessible by package, is it possible to make it private ? dont think so
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
     * Gère le cas où le joueur gagne la partie.
     */
    private void checkWin() {
        if (labyrinth.countPacdots() == 0) {
            JOptionPane.showMessageDialog(gui, "Vous avez gagné !", "Partie terminée", JOptionPane.INFORMATION_MESSAGE);
            resetGame();
        }
    }

    /**
     * Gère le score nécessaire pour obtenir une vie supplémentaire.
     */
    private void handleScoreForExtraLife() {
        if (score >= 5000) {
            lives++;
            score -= 5000;
        }
    }

    /**
     * Méthode appelée à chaque "tick" du timer, gérant les mouvements des éléments du jeu.
     *
     * @param e L'événement d'action.
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
     * Définit le score du joueur.
     *
     * @param score Le nouveau score du joueur.
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Obtient le score du joueur.
     *
     * @return Le score du joueur.
     */
    public int getScore() {
        return score;
    }

    /**
     * Obtient le nombre de vies restantes du joueur.
     *
     * @return Le nombre de vies restantes.
     */
    public int getLives() {
        return lives;
    }

    /**
     * Obtient la classe PacMan.
     *
     * @return la classe pacman
     */
    public PacMan getPacman() {
        return pacman;
    }

    /**
     * Gère les événements liés aux touches du clavier.
     *
     * @param e L'événement de touche.
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
     * Méthode non utilisée.
     *
     * @param e L'événement de touche tapée.
     */
    @Override
    public void keyTyped(KeyEvent e) {
        // Not needed
    }

    /**
     * Méthode non utilisée.
     *
     * @param e L'événement de libération de touche.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        // Not needed
    }
}