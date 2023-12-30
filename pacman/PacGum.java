
package pacman;

public class PacGum {
    private static final long INVISIBLE_DURATION = 10000;
    private static final long SUPER_PACMAN_DURATION = 10000;

    private boolean isPacManInvisible = false;
    private long invisibleStartTime = 0;
    private boolean isSuperPacMan = false;
    private long superPacManStartTime = 0;

    PacGum() {
        isPacManInvisible = false;
        isSuperPacMan = false;
        invisibleStartTime = 0;
        superPacManStartTime = 0;
    }

    boolean isPacManInvisible() {
        return isPacManInvisible;
    }

    void activateInvisibility() {
        isPacManInvisible = true;
        invisibleStartTime = System.currentTimeMillis();
    }

    void resetInvisibility() {
        isPacManInvisible = false;
        invisibleStartTime = 0;
    }

    void updateInvisibility() {
        if (isPacManInvisible) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - invisibleStartTime >= INVISIBLE_DURATION) {
                isPacManInvisible = false;
            }
        }
    }

    boolean isSuperPacMan() {
        return isSuperPacMan;
    }

    void resetSuperPacMan() {
        isSuperPacMan = false;
        superPacManStartTime = 0;
    }

    void activateSuperPacMan() {
        isSuperPacMan = true;
        superPacManStartTime = System.currentTimeMillis();
    }

    void updateSuperPacMan() {
        if (isSuperPacMan) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - superPacManStartTime >= SUPER_PACMAN_DURATION) {
                isSuperPacMan = false;
            }
        }
    }

    void activateGreenPacGum(Game game) {
        applyGreenPacGumEffect(game);
    }

    private void applyGreenPacGumEffect(Game game) {
        int[][] swaps = {
                {1, 7, 9, 1}, {1, 8, 10, 1}, {1, 9, 11, 1},
                {1, 10, 12, 1}, {1, 11, 13, 1}, {1, 16, 9, 26},
                {1, 17, 10, 26}, {1, 18, 11, 26}, {1, 19, 12, 26},
                {1, 20, 13, 26}, {5, 13, 1, 13}, {5, 14, 1, 14},
                {6, 9, 6, 12}, {7, 9, 7, 12}, {9, 9, 9, 12},
                {10, 9, 10, 12}, {6, 15, 6, 18}, {7, 15, 7, 18},
                {9, 15, 9, 18}, {10, 15, 10, 18}, {20, 7, 23, 4},
                {20, 8, 23, 5}, {20, 13, 23, 13}, {20, 14, 23, 14},
                {20, 19, 23, 22}, {20, 20, 23, 23}, {26, 10, 26, 7},
                {26, 11, 26, 8}, {29, 13, 26, 13}, {29, 14, 26, 14},
                {26, 16, 26, 19}, {26, 17, 26, 20}, {23, 10, 24, 12},
                {23, 11, 25, 12}, {23, 16, 24, 15}, {23, 17, 25, 15}
        };

        for (int[] swap : swaps) {
            swapValues(swap[0], swap[1], swap[2], swap[3]);
        }

        game.initializePacdots();
        game.resetAllGhostsToCenter();
    }

    private static void swapValues(int srcRow, int srcCol, int destRow, int destCol) {
        int[][] labyrinth = Game.originalLabyrinth;
        // Utilisation de la décomposition de tuples pour échanger les valeurs
        int temp = labyrinth[srcRow][srcCol];
        labyrinth[srcRow][srcCol] = labyrinth[destRow][destCol];
        labyrinth[destRow][destCol] = temp;
    }
}
