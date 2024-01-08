package pacman.entities;

import java.awt.event.KeyEvent;

/**
 * Class contenant le labyrinthe et toutes ses méthodes.
 */
public class Labyrinth {
    /**
     * Tableau de cellules qui forment le labyrinthe.
     */
    private static Cell[][] LABYRINTH_DATA;

    /**
     * Constructor de base de la classe Labyrinth contenant l'organisation par défaut du labyrinthe.
     */
    public Labyrinth() {
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
    }

    /**
     * Méthode utilisée pour accéder au nombre de pacdots restants dans la labyrinthe.
     *
     * @return nombre de pacdots restants dans le labyrinthe.
     */
     public int countPacdots() {
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

    /**
     * Setter pour les cellules du labyrinthe.
     *
     * @param type type de la cellule.
     * @param y rangée de la cellule.
     * @param x colonne de la cellule.
     */
    public void setCell(Cell type, int y, int x){
        LABYRINTH_DATA[y][x] = type;
    }

    /**
     * Getter pour le type d'une cellule.
     *
     * @param y rangée de la cellule.
     * @param x colonne de la cellule.
     * @return retourne le type de la cellule.
     */
    public Cell getCell(int y, int x){
        return LABYRINTH_DATA[y][x];
    }

    /**
     * Méthode qui applique l'effet du pacgum vert.
     * Échange la place de quelques cellules du labyrinthe pour créer une disposition différente.
     */
    void applyGreenPacGumEffect() {
        // Store the swaps we plan on doing inside an array
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

        // Go through that array and apply each of the swaps
        for (int[] swap : swaps) {
            swapValues(swap[0], swap[1], swap[2], swap[3]);
        }
    }

    /**
     * Méthode qui échange deux cellules.
     *
     * @param srcRow rangée de la première cellule.
     * @param srcCol colomne de la première cellule.
     * @param destRow rangée de la deuxième cellule.
     * @param destCol colomne de la deuxième cellule.
     */
    private static void swapValues(int srcRow, int srcCol, int destRow, int destCol) {
        Cell temp = LABYRINTH_DATA[srcRow][srcCol];
        LABYRINTH_DATA[srcRow][srcCol] = LABYRINTH_DATA[destRow][destCol];
        LABYRINTH_DATA[destRow][destCol] = temp;
    }

    /**
     * Method qui retourne si une case est valide pour un déplacement.
     *
     * @param y rangée de la cellule.
     * @param x colonne de la cellule.
     * @return true si la cellule n'est pas un mur, sinon false.
     */
    static boolean isValidMove(int x, int y) {
        return x >= 0 && x < LABYRINTH_DATA[0].length &&
                y >= 0 && y < LABYRINTH_DATA.length &&
                LABYRINTH_DATA[y][x] != Cell.WALL;
    }

    /**
     * Method qui gère la saisie KeyListener de l'utilisateur.
     *
     * @param key saisie de l'utilisateur.
     * @param playerX rangée de PacMan.
     * @param playerY colonne de PacMan.
     * @return nouvelle direction de pacman si key est une des flèches, -1 qui indique reset de la partie si key est R.
     */
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

    /**
     * Getter pour le nombre de rangées du labyrinthe
     *
     * @return nombre de rangées du labyrinthe.
     */
    public int getHeight(){
        return LABYRINTH_DATA.length;
    }

    /**
     * Getter pour le nombre de colonnes du labyrinthe
     *
     * @return nombre de colonnes du labyrinthe.
     */
    public int getWidth(){
        return LABYRINTH_DATA[0].length;
    }
}