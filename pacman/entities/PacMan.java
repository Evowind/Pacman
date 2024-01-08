package pacman.entities;

import pacman.app.Game;
import pacman.state.InvisibleState;
import pacman.state.NormalState;
import pacman.state.PacState;
import pacman.state.SuperState;

/**
 * Class containing all the methods and information about the PacMan entity.
 */
public class PacMan {
    private int playerX, playerY;
    private int playerDirection;
    private final Game game;
    public PacState state;

    /**
     * Constructor for PacMan class.
     *
     * @param playerX Default PacMan row.
     * @param playerY Default PacMan column.
     * @param playerDirection Default PacMan direction.
     * @param game Game object to access its value and methods.
     */
    public PacMan(int playerX, int playerY, int playerDirection, Game game) {
        this.playerX = playerX;
        this.playerY = playerY;
        this.playerDirection = playerDirection;
        this.game = game;
        state = new NormalState();
    }

    /**
     * Method used to move PacMan in the direction it is facing.
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
     * Method used to process if PacMan collided with any entity that isn't a ghost.
     *
     * @param playerCellX Current PacMan row
     * @param playerCellY Current PacMan column
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
                teleport(playerCellX);
                break;
            default:
                break;
        }
    }

    // TODO is this really needed ? only 1 usage could just put this in checkCellCollisions() just above
    public void teleport(int playerCellX){
        if (playerCellX == 27) {
            playerX = 1;
        } else {
            playerX = 26;
        }
    }

    // TODO isnt this swapped ? X is column and Y is row ?
    /**
     * Getter for player row position in the maze.
     *
     * @return PacMan row.
     */
    public int getPlayerX() {
        return playerX;
    }

    /**
     * Getter for player column position in the maze.
     *
     * @return PacMan column.
     */
    public  int getPlayerY() {
        return playerY;
    }

    /**
     * Getter for player direction.
     *
     * @return PacMan direction.
     */
    public int getPlayerDirection() {
        return playerDirection;
    }

    /**
     * Setter for player direction.
     *
     * @param playerDirection new PacMan direction.
     */
    public void setPlayerDirection(int playerDirection) {
        this.playerDirection = playerDirection;
    }
}
