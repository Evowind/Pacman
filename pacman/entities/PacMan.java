package pacman.entities;

import pacman.app.Game;
import pacman.state.InvisibleState;
import pacman.state.NormalState;
import pacman.state.PacState;
import pacman.state.SuperState;

/**
 * Classe contenant toutes les méthodes et informations sur l'entité PacMan.
 */
public class PacMan {
    /**
     * Variables contenant la ligne de PacMan.
     */
    private int playerX;
    /**
     * Variables contenant la colonne de PacMan.
     */
    private int playerY;
    /**
     * Variables contenant la direction de PacMan.
     */
    private int playerDirection;
    /**
     * L'objet Game associé a l'entité PacMan.
     */
    private final Game game;
    /**
     * L'état de l'entité PacMan.
     */
    public PacState state;

    /**
     * Constructeur de base pour la classe PacMan.
     *
     * @param playerX ligne de départ de PacMan.
     * @param playerY colonne de départ de PacMan.
     * @param playerDirection direction de départ de PacMan.
     * @param game objet Game associé au a l'entité PacMan.
     */
    public PacMan(int playerX, int playerY, int playerDirection, Game game) {
        this.playerX = playerX;
        this.playerY = playerY;
        this.playerDirection = playerDirection;
        this.game = game;
        state = new NormalState();
    }

    /**
     * Méthode qui déplace le joueur Pac-Man en fonction de sa direction.
     */
    public void movePlayer() {
        int newX = playerX;
        int newY = playerY;
        int playerSpeed = 1;
        switch (playerDirection) {
            case 0 -> newX += playerSpeed;
            case 1 -> newX -= playerSpeed;
            case 2 -> newY -= playerSpeed;
            case 3 -> newY += playerSpeed;
        }
        if (Labyrinth.isValidMove(newX, newY)) {
            playerX = newX;
            playerY = newY;
        }
    }

    /**
     * Méthode qui vérifie les collisions du joueur Pac-Man avec les cellules du labyrinthe.
     *
     * @param playerCellX rangée de PacMan.
     * @param playerCellY colonne de PacMan.
     */
    public void checkCellCollisions(int playerCellX, int playerCellY) {

        Cell cell = game.labyrinth.getCell(playerCellY, playerCellX);

        switch (cell) {
            case PACDOT -> handlePacDotCollision();
            case PURPLE -> handlePurpleCollision();
            case ORANGE -> handleOrangeCollision();
            case GREEN -> handleGreenCollision();
            case TELEPORTER -> handleTeleportCollision(playerCellX);
            default -> {
            }
        }
    }

    /**
     * Gère la collision avec une pac-dot, ajoute des points au score du jeu et retire la pac-dot du labyrinthe.
     */
    private void handlePacDotCollision() {
        game.setScore(game.getScore() + 100);
        game.labyrinth.setCell(Cell.EMPTY, getPlayerY(), getPlayerX());
    }

    /**
     * Gère la collision avec une cellule violette.
     * Ajoute des points au score du jeu, applique l'état invisible au joueur
     * Pac-Man et retire la cellule violette du labyrinthe.
     */
    private void handlePurpleCollision() {
        game.setScore(game.getScore() + 300);
        state = new InvisibleState();
        game.labyrinth.setCell(Cell.EMPTY, getPlayerY(), getPlayerX());
    }

    /**
     * Gère la collision avec une cellule orange.
     * Ajoute des points au score du jeu, applique l'état super au joueur
     * Pac-Man et retire la cellule orange du labyrinthe.
     */
    private void handleOrangeCollision() {
        game.setScore(game.getScore() + 500);
        state = new SuperState();
        game.labyrinth.setCell(Cell.EMPTY, getPlayerY(), getPlayerX());
    }

    /**
     * Gère la collision avec une cellule verte.
     * Ajoute des points au score du jeu, applique l'effet de la gomme verte
     * au labyrinthe, réinitialise les fantômes et retire la cellule verte du labyrinthe.
     */
    private void handleGreenCollision() {
        game.setScore(game.getScore() + 1000);
        game.labyrinth.applyGreenPacGumEffect();
        game.ghosts.initializeGhosts();
        game.labyrinth.setCell(Cell.EMPTY, getPlayerY(), getPlayerX());
    }

    /**
     * Gère la collision avec un téléporteur.
     * Téléporte le joueur Pac-Man à la position opposée du téléporteur.
     *
     * @param playerCellX Position de la cellule en X du joueur Pac-Man.
     */
    private void handleTeleportCollision(int playerCellX) {
        if (playerCellX == 27) {
            playerX = 1;
        } else {
            playerX = 26;
        }
    }

    /**
     * Getter pour la rangée de l'entité PacMan.
     *
     * @return rangée de PacMan.
     */
    public int getPlayerX() {
        return playerX;
    }

    /**
     * Getter pour la colonne de l'entité PacMan.
     *
     * @return colonne de PacMan.
     */
    public int getPlayerY() {
        return playerY;
    }

    /**
     * Getter de la direction du joueur Pac-Man.
     *
     * @return Direction du joueur Pac-Man.
     */
    public int getPlayerDirection() {
        return playerDirection;
    }

    /**
     * Setter de la direction de l'entité Pac-Man.
     *
     * @param playerDirection Nouvelle direction du joueur Pac-Man.
     */
    public void setPlayerDirection(int playerDirection) {
        this.playerDirection = playerDirection;
    }
}
