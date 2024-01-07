package pacman.entities;

import pacman.app.Game;
import pacman.state.InvisibleState;
import pacman.state.NormalState;
import pacman.state.PacState;
import pacman.state.SuperState;

public class PacMan {
    private int playerX, playerY;
    private int playerDirection;
    private final Game game;
    public PacState state;

    public PacMan(int playerX, int playerY, int playerDirection, Game game) {
        this.playerX = playerX;
        this.playerY = playerY;
        this.playerDirection = playerDirection;
        this.game = game;
        state = new NormalState();
    }

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
        if (game.getPacdotsRemaining() == 0) {
            game.handleWin();
            game.resetGame();
        }
        if (Labyrinth.isValidMove(newX, newY)) {
            playerX = newX;
            playerY = newY;
        }
    }

    public void checkCellCollisions(int playerCellX, int playerCellY) {
        switch (game.labyrinth.getCell(playerCellY, playerCellX)) {
            case PACDOT:
                game.setScore(game.getScore() + 100);
                game.labyrinth.setCell(Cell.EMPTY, playerCellY, playerCellX);
                game.decrPacDot();
                break;
            case PURPLE:
                game.setScore(game.getScore() + 300);
                game.labyrinth.setCell(Cell.EMPTY, playerCellY, playerCellX);
                game.decrPacDot();
                state = new InvisibleState();
                break;
            case ORANGE:
                game.setScore(game.getScore() + 500);
                game.labyrinth.setCell(Cell.EMPTY, playerCellY, playerCellX);
                game.decrPacDot();
                //game.pacGum.activateSuperPacMan();
                state = new SuperState();
                break;
            case GREEN:
                game.setScore(game.getScore() + 1000);
                game.labyrinth.setCell(Cell.EMPTY, playerCellY, playerCellX);
                game.decrPacDot();
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

    public void teleport(int playerCellX){
        if (playerCellX == 27) {
            playerX = 1;
        } else {
            playerX = 26;
        }
    }

    public int getPlayerX() {
        return playerX;
    }

    public  int getPlayerY() {
        return playerY;
    }

    public int getPlayerDirection() {
        return playerDirection;
    }

    public void setPlayerDirection(int playerDirection) {
        this.playerDirection = playerDirection;
    }
}
