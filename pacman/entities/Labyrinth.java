package pacman.entities;

import pacman.entities.Cell;
import pacman.entities.PacMan;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * Class contenant le labyrinthe et toutes ses méthodes.
 */
public class Labyrinth {
    /**
     * Données du labyrinthe représentées sous forme de tableau de cellules.
     */
    private static Cell[][] LABYRINTH_DATA;

    /**
     * Initialise un nouveau labyrinthe avec des données prédéfinies.
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
                if (isPacdotType(cell)) {
                    pacdots++;
                }
            }
        }
        return pacdots;
    }

    /**
     * Méthode qui vérifie si une cellule est de type pac-gomme.
     *
     * @param cell Cellule à vérifier.
     * @return True si la cellule est de type pac-gomme, sinon False.
     */
    private boolean isPacdotType(Cell cell) {
        return cell == Cell.PACDOT || cell == Cell.PURPLE || cell == Cell.GREEN || cell == Cell.ORANGE;
    }

    /**
     * Méthode qui applique l'effet du pacgum vert.
     * Échange la place de quelques cellules du labyrinthe pour créer une disposition différente.
     */
    public void applyGreenPacGumEffect() {
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
     * Méthode qui échange les valeurs de deux cellules dans le labyrinthe.
     *
     * @param srcRow  Ligne source.
     * @param srcCol  Colonne source.
     * @param destRow Ligne de destination.
     * @param destCol Colonne de destination.
     */
    private static void swapValues(int srcRow, int srcCol, int destRow, int destCol) {
        Cell temp = LABYRINTH_DATA[srcRow][srcCol];
        LABYRINTH_DATA[srcRow][srcCol] = LABYRINTH_DATA[destRow][destCol];
        LABYRINTH_DATA[destRow][destCol] = temp;
    }

    /**
     * Méthode qui vérifie si un déplacement vers une position spécifiée est valide.
     *
     * @param y rangée de la cellule.
     * @param x colonne de la cellule.
     * @return True si le déplacement est valide, sinon False.
     */
    public static boolean isValidMove(int x, int y) {
        return x >= 0 && x < LABYRINTH_DATA[0].length &&
                y >= 0 && y < LABYRINTH_DATA.length &&
                LABYRINTH_DATA[y][x] != Cell.WALL;
    }

    /**
     * Méthode qui traite un événement de touche pour le déplacement du joueur Pac-Man.
     *
     * @param key     Code de la touche.
     * @param playerX Position en colonne du joueur Pac-Man.
     * @param playerY Position en ligne du joueur Pac-Man.
     * @return Code de direction (0 à 3) ou codes spéciaux (-1, -2).
     */
    public int processEvent(int key, int playerX, int playerY) {
        Map<Integer, int[]> directions = new HashMap<>();
        directions.put(KeyEvent.VK_UP, new int[]{-1, 0});
        directions.put(KeyEvent.VK_DOWN, new int[]{1, 0});
        directions.put(KeyEvent.VK_LEFT, new int[]{0, -1});
        directions.put(KeyEvent.VK_RIGHT, new int[]{0, 1});

        int[] direction = directions.getOrDefault(key, new int[]{0, 0});

        if (key == KeyEvent.VK_R) {
            // Touche R pour reset
            return -1;
        }

        int newX = playerX + direction[1];
        int newY = playerY + direction[0];

        if (isValidMove(newX, newY)) {
            return direction[0] == -1 ? 2 : (direction[0] == 1 ? 3 : (direction[1] == -1 ? 1 : 0));
        } else {
            return -2;
        }
    }

    /**
     * Getter pour le nombre de rangées du labyrinthe
     *
     * @return nombre de rangées du labyrinthe.
     */
    public int getHeight() {
        return LABYRINTH_DATA.length;
    }

    /**
     * Getter pour le nombre de colonnes du labyrinthe
     *
     * @return nombre de colonnes du labyrinthe.
     */
    public int getWidth() {
        return LABYRINTH_DATA[0].length;
    }

    /**
     * Setter qui renvoie la cellule à une position spécifiée dans le labyrinthe.
     *
     * @param y Position en ligne.
     * @param x Position en colonne.
     * @return Cellule à la position spécifiée.
     */
    public Cell getCell(int y, int x) {
        return LABYRINTH_DATA[y][x];
    }

    /**
     * Setter qui modifie le type de cellule à une position spécifiée dans le labyrinthe.
     *
     * @param type Nouveau type de cellule.
     * @param y    Position en ligne.
     * @param x    Position en colonne.
     */
    public void setCell(Cell type, int y, int x) {
        LABYRINTH_DATA[y][x] = type;
    }
}