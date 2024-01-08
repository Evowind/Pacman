package pacman.app;

import pacman.app.Game;
import pacman.entities.Ghost;
import pacman.entities.PacMan;
import pacman.state.PacState;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Représente une liste de fantômes dans le jeu Pac-Man.
 */
public class GhostList {
    /**
     * Liste des fantômes.
     */
    private final List<Ghost> ghosts;

    /**
     * Instance du joueur Pac-Man.
     */
    private final PacMan pacman;

    /**
     * Instance du jeu associée à la liste de fantômes.
     */
    private final Game game;

    /**
     * Initialise une nouvelle liste de fantômes avec le joueur Pac-Man et le jeu associés.
     *
     * @param pacman Instance du joueur Pac-Man.
     * @param game   Instance du jeu Pac-Man.
     */
    public GhostList(PacMan pacman, Game game) {
        ghosts = new ArrayList<>();
        this.pacman = pacman;
        this.game = game;
    }

    /**
     * Initialise les fantômes pour une nouvelle partie ou les replace au centre lors d'un reset.
     */
    public void initializeGhosts() {
        // Le cas d'un reset
        if (!ghosts.isEmpty()) {
            for (Ghost ghost : ghosts) {
                ghost.setX(12);
                ghost.setY(11);
            }
            // Le cas d'une nouvelle partie
        } else {
            Color[] ghostColors = {Color.CYAN, Color.WHITE, Color.ORANGE, Color.PINK};

            for (int i = 0; i < ghostColors.length; i++) {
                ghosts.add(new Ghost(12 + i, 11, ghostColors[i]));
            }
        }
    }

    /**
     * Déplace tous les fantômes de la liste de manière aléatoire.
     */
    public void moveGhosts() {
        for (Ghost ghost : ghosts) {
            ghost.moveRandomly(pacman.state.getState());
        }
    }

    /**
     * Replace tous les fantômes au centre du labyrinthe.
     */
    public void resetAllGhostsToCenter() {
        for (Ghost ghost : ghosts) {
            ghost.setX(12);
            ghost.setY(11);
        }
    }

    /**
     * Replace un fantôme spécifique de couleur donnée au centre du labyrinthe.
     *
     * @param color Couleur du fantôme à replacer.
     */
    private void resetGhostToCenter(Color color) {
        for (Ghost ghost : ghosts) {
            if (ghost.getColor() == color) {
                ghost.setX(12);
                ghost.setY(11);
            }
        }
    }

    /**
     * Vérifie les collisions entre le joueur Pac-Man et les fantômes.
     *
     * @param playerCellX Position de la cellule en X du joueur Pac-Man.
     * @param playerCellY Position de la cellule en Y du joueur Pac-Man.
     */
    public void checkGhostCollisions(int playerCellX, int playerCellY) {
        for (Ghost ghost : ghosts) {
            int ghostCellX = ghost.getX();
            int ghostCellY = ghost.getY();

            if (playerCellX == ghostCellX && playerCellY == ghostCellY) {
                handleGhostCollision(ghost);
            }
        }
    }

    /**
     * Gère la collision avec un fantôme spécifique.
     *
     * @param ghost Fantôme avec lequel le joueur Pac-Man a collisionné.
     */
    private void handleGhostCollision(Ghost ghost) {
        // state
        if (pacman.state.getState() == PacState.State.SUPER) {
            // Le fantome est mangé par le Super PacMan
            game.setScore(game.getScore() + 400);
            resetGhostToCenter(ghost.getColor());
        } else if (pacman.state.getState() != PacState.State.INVISIBLE) {
            // PacMan est mangé
            game.handlePlayerCaught();
        }
    }

    /**
     * Renvoie la liste des fantômes.
     *
     * @return Liste des fantômes.
     */
    public List<Ghost> getGhosts() {
        return ghosts;
    }
}
