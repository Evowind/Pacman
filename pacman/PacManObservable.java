package pacman;

public class PacManObservable {
    private int playerX, playerY;
    private int playerDirection;
    private Game game;
    private PacState state;

    public PacManObservable(int playerX, int playerY, int playerDirection, Game game) {
        this.playerX = playerX;
        this.playerY = playerY;
        this.playerDirection = playerDirection;
        this.game = game;
        state = new NormalState(this);
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
        if (game.isValidMove(newX, newY)) {
            playerX = newX;
            playerY = newY;

            int cellX = playerX;
            int cellY = playerY;
            if (game.labyrinth[cellY][cellX] == Cell.PACDOT) {
                game.labyrinth[cellY][cellX] = Cell.EMPTY;
            }

        }
    }

    public void checkCellCollisions(int playerCellX, int playerCellY) {
        Cell cellValue = game.labyrinth[playerCellY][playerCellX];
        switch (cellValue) {
            case PACDOT:
                game.setScore(game.getScore() + 100);
                game.labyrinth[playerCellY][playerCellX] = Cell.EMPTY;
                game.decrPacDot();
                break;
            case PURPLE:
                game.setScore(game.getScore() + 300);
                game.labyrinth[playerCellY][playerCellX] = Cell.EMPTY;
                game.decrPacDot();
                game.pacGum.activateInvisibility();
                break;
            case ORANGE:
                game.setScore(game.getScore() + 500);
                game.labyrinth[playerCellY][playerCellX] = Cell.EMPTY;
                game.decrPacDot();
                game.pacGum.activateSuperPacMan();
                break;
            case GREEN:
                game.setScore(game.getScore() + 1000);
                game.labyrinth[playerCellY][playerCellX] = Cell.EMPTY;
                game.decrPacDot();
                game.applyGreenPacGumEffect();
                game.initializePacdots();
                game.resetAllGhostsToCenter();
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

    public void setPlayerX(int playerX) {
        this.playerX = playerX;
    }

    public  int getPlayerY() {
        return playerY;
    }

    public void setPlayerY(int playerY) {
        this.playerY = playerY;
    }

    public int getPlayerDirection() {
        return playerDirection;
    }

    public void setPlayerDirection(int playerDirection) {
        this.playerDirection = playerDirection;
    }
}
