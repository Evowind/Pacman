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
     * Variables contenant la rangée de PacMan.
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
     * @param playerX rangée de départ de PacMan.
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
     * Methode qui déplace l'entité PacMan dans sa direction actuelle.
     */
    public void movePlayer() {
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
        if (Labyrinth.isValidMove(newX, newY)) {
            playerX = newX;
            playerY = newY;
        }
    }

    /**
     * Methode qui gère la collision entre PacMan et toute autre entitée qui n'est pas un fantome.
     *
     * @param playerCellX rangée de PacMan.
     * @param playerCellY colonne de PacMan.
     */
    public void checkCellCollisions(int playerCellX, int playerCellY) {
        switch (game.labyrinth.getCell(playerCellY, playerCellX)) {
            case PACDOT:
                game.setScore(game.getScore() + 100);
                game.labyrinth.setCell(Cell.EMPTY, playerCellY, playerCellX);
                break;
            case PURPLE:
                game.setScore(game.getScore() + 300);
                game.labyrinth.setCell(Cell.EMPTY, playerCellY, playerCellX);
                state = new InvisibleState();
                break;
            case ORANGE:
                game.setScore(game.getScore() + 500);
                game.labyrinth.setCell(Cell.EMPTY, playerCellY, playerCellX);
                //game.pacGum.activateSuperPacMan();
                state = new SuperState();
                break;
            case GREEN:
                game.setScore(game.getScore() + 1000);
                game.labyrinth.setCell(Cell.EMPTY, playerCellY, playerCellX);
                game.labyrinth.applyGreenPacGumEffect();
                game.ghosts.resetAllGhostsToCenter();
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
    public  int getPlayerY() {
        return playerY;
    }

    /**
     * Setter la direction de l'entité PacMan.
     *
     * @param playerDirection nouvelle direction de PacMan.
     */
    public void setPlayerDirection(int playerDirection) {
        this.playerDirection = playerDirection;
    }

    /**
     * Getter pour la direction de l'entité PacMan.
     *
     * @return direction de PacMan.
     */
    public int getPlayerDirection() {
        return playerDirection;
    }
}
