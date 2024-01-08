package pacman.entities;

import pacman.app.Game;
import pacman.state.InvisibleState;
import pacman.state.NormalState;
import pacman.state.PacState;
import pacman.state.SuperState;

/**
 * Représente le personnage Pac-Man dans le jeu.
 */
public class PacMan {
    /**
     * Position en X du joueur Pac-Man.
     */
    private int playerX;

    /**
     * Position en Y du joueur Pac-Man.
     */
    private int playerY;

    /**
     * Direction du joueur Pac-Man (0: Droite, 1: Gauche, 2: Haut, 3: Bas).
     */
    private int playerDirection;

    /**
     * Instance du jeu associée à Pac-Man.
     */
    private final Game game;

    /**
     * État actuel du joueur Pac-Man.
     */
    public PacState state;

    /**
     * Initialise un nouveau joueur Pac-Man avec des paramètres donnés.
     *
     * @param playerX         Position en X initiale du joueur Pac-Man.
     * @param playerY         Position en Y initiale du joueur Pac-Man.
     * @param playerDirection Direction initiale du joueur Pac-Man.
     * @param game            Instance du jeu associée à Pac-Man.
     */
    public PacMan(int playerX, int playerY, int playerDirection, Game game) {
        this.playerX = playerX;
        this.playerY = playerY;
        this.playerDirection = playerDirection;
        this.game = game;
        state = new NormalState();
    }

    /**
     * Déplace le joueur Pac-Man en fonction de sa direction.
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
     * Vérifie les collisions du joueur Pac-Man avec les cellules du labyrinthe.
     *
     * @param playerCellX Position de la cellule en X du joueur Pac-Man.
     * @param playerCellY Position de la cellule en Y du joueur Pac-Man.
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


    // TODO isnt this swapped ? X is column and Y is row ?

    /**
     * Renvoie la position en X du joueur Pac-Man.
     *
     * @return Position en X du joueur Pac-Man.
     */
    public int getPlayerX() {
        return playerX;
    }

    /**
     * Renvoie la position en Y du joueur Pac-Man.
     *
     * @return Position en Y du joueur Pac-Man.
     */
    public int getPlayerY() {
        return playerY;
    }

    /**
     * Renvoie la direction du joueur Pac-Man.
     *
     * @return Direction du joueur Pac-Man.
     */
    public int getPlayerDirection() {
        return playerDirection;
    }

    /**
     * Définit la direction du joueur Pac-Man.
     *
     * @param playerDirection Nouvelle direction du joueur Pac-Man.
     */
    public void setPlayerDirection(int playerDirection) {
        this.playerDirection = playerDirection;
    }
}
