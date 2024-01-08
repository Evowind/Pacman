package pacman;

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
            case 0 -> newX += playerSpeed;
            case 1 -> newX -= playerSpeed;
            case 2 -> newY -= playerSpeed;
            case 3 -> newY += playerSpeed;
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
        Cell cell = game.labyrinth.getCell(playerCellY, playerCellX);

        switch (cell) {
            case PACDOT -> handlePacDotCollision();
            case PURPLE -> handlePurpleCollision();
            case ORANGE -> handleOrangeCollision();
            case GREEN -> handleGreenCollision();
            case TELEPORTER -> handleTeleportCollision(playerCellX);
            default -> {}
        }
    }

    private void handlePacDotCollision() {
        game.setScore(game.getScore() + 100);
        handleCommonCollisionActions();
    }

    private void handlePurpleCollision() {
        game.setScore(game.getScore() + 300);
        state = new InvisibleState();
        handleCommonCollisionActions();
    }

    private void handleOrangeCollision() {
        game.setScore(game.getScore() + 500);
        state = new SuperState();
        handleCommonCollisionActions();
    }

    private void handleGreenCollision() {
        game.setScore(game.getScore() + 1000);
        game.labyrinth.applyGreenPacGumEffect();
        game.ghosts.resetAllGhostsToCenter();
        handleCommonCollisionActions();
    }

    private void handleCommonCollisionActions() {
        game.labyrinth.setCell(Cell.EMPTY, getPlayerY(), getPlayerX());
        game.decrPacDot();
    }


    private void handleTeleportCollision(int playerCellX){
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
