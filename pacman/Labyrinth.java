package pacman;

import java.awt.event.KeyEvent;

public class Labyrinth {
    private static Cell[][] LABYRINTH_DATA;

    private PacMan pacman;

    public Labyrinth(PacMan pacman) {
        LABYRINTH_DATA = new Cell[][]{
                {Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL},
                {Cell.WALL, Cell.GREEN, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.WALL},
                {Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL},
                {Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL},
                {Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL},
                {Cell.WALL, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.WALL},
                {Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL},
                {Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL},
                {Cell.WALL, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.WALL},
                {Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL},
                {Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL},
                {Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL},
                {Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL},
                {Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL},
                {Cell.TELEPORTER, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.WALL, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.WALL, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.TELEPORTER},
                {Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.EMPTY, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL},
                {Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL},
                {Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL},
                {Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL},
                {Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL},
                {Cell.WALL, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.WALL},
                {Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL},
                {Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL},
                {Cell.WALL, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.WALL},
                {Cell.WALL, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.WALL},
                {Cell.WALL, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.WALL},
                {Cell.WALL, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.WALL},
                {Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL},
                {Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.PACDOT, Cell.WALL},
                {Cell.WALL, Cell.PURPLE, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.PACDOT, Cell.ORANGE, Cell.WALL},
                {Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL, Cell.WALL},
        };
        this.pacman = pacman;
    }

    int countPacdots() {
        int pacdots = 0;
        for (Cell[] cells : LABYRINTH_DATA) {
            for (Cell cell : cells) {
                if (cell == Cell.PACDOT || cell == Cell.PURPLE ||
                        cell == Cell.GREEN || cell == Cell.ORANGE) {
                    pacdots++;
                }
            }
        }
        return pacdots;
    }

    public void setCell(Cell type, int y, int x){
        LABYRINTH_DATA[y][x] = type;
    }

    public Cell getCell(int y, int x){
        return LABYRINTH_DATA[y][x];
    }

    static Cell[][] getCopy() {
        Cell[][] copy = new Cell[LABYRINTH_DATA.length][LABYRINTH_DATA[0].length];
        for (int i = 0; i < LABYRINTH_DATA.length; i++) {
            System.arraycopy(LABYRINTH_DATA[i], 0, copy[i], 0, LABYRINTH_DATA[i].length);
        }
        return copy;
    }

    public void applyGreenPacGumEffect() {
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
    }

    private static void swapValues(int srcRow, int srcCol, int destRow, int destCol) {
        Cell temp = LABYRINTH_DATA[srcRow][srcCol];
        LABYRINTH_DATA[srcRow][srcCol] = LABYRINTH_DATA[destRow][destCol];
        LABYRINTH_DATA[destRow][destCol] = temp;
    }

    static boolean isValidMove(int x, int y) {
        return x >= 0 && x < LABYRINTH_DATA[0].length &&
                y >= 0 && y < LABYRINTH_DATA.length &&
                LABYRINTH_DATA[y][x] != Cell.WALL;
    }

    public int processEvent(int key, int playerX, int playerY) {
        if (key == KeyEvent.VK_UP) {
            if (playerY > 0 && LABYRINTH_DATA[playerY - 1][playerX] != Cell.WALL) {
                return 2;
            }
        } else if (key == KeyEvent.VK_DOWN) {
            if (playerY < getHeight() - 1 && LABYRINTH_DATA[playerY + 1][playerX] != Cell.WALL) {
                return 3;
            }
        } else if (key == KeyEvent.VK_LEFT) {
            if (playerX > 0 && LABYRINTH_DATA[playerY][playerX - 1] != Cell.WALL) {
                return 1;
            }
        } else if (key == KeyEvent.VK_RIGHT) {
            if (playerX < getWidth() - 1 && LABYRINTH_DATA[playerY][playerX + 1] != Cell.WALL) {
                return 0;
            }
        } else if (key == KeyEvent.VK_R) {
            return -1;
        }
        return -2;
    }

    public int getHeight(){
        return LABYRINTH_DATA.length;
    }

    public int getWidth(){
        return LABYRINTH_DATA[0].length;
    }
}