package pacman.app;

import pacman.entities.Ghost;
import pacman.entities.PacMan;
import pacman.state.PacState;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe utilisée pour représenter et gérer une liste de fantômes.
 */
public class GhostList {
    /**
     * Liste d'objets Ghost.
     */
    private final List<Ghost> ghosts;
    /**
     * L'objet PacMan associé à la liste de fantomes.
     */
    private final PacMan pacman;
    /**
     * L'objet Game associé à la liste de fantomes.
     */
    private final Game game;

    /**
     * Constructeur de base de la classe GhostList.
     *
     * @param pacman objet PacMan associé a la liste de fantomes.
     * @param game   objet Game associé a la liste de fantomes.
     */
    public GhostList(PacMan pacman, Game game){
        ghosts = new ArrayList<>();
        this.pacman = pacman;
        this.game = game;
    }

    /**
     * Méthode qui créer les fantomes dans la liste.
     */
    public void initializeGhosts() {
        ghosts.clear();
        Color[] ghostColors = {Color.CYAN, Color.WHITE, Color.ORANGE, Color.PINK};

        for (int i = 0; i < ghostColors.length; i++) {
            ghosts.add(new Ghost(12 + i, 11, ghostColors[i]));
        }
    }

    /**
     * Méthode qui déplace tous les fantômes de la liste de manière aléatoire.
     */
    public void moveGhosts() {
        for (Ghost ghost : ghosts) {
            ghost.moveRandomly(pacman.state.getState());
        }
    }

    /**
     * Méthode qui renvoie un fantome au point de départ.
     * Utilisé si un fantome se fait manger par Super PacMan.
     *
     * @param color couleur du fantomes que l'on veut déplacer.
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
     * Méthode qui vérifie les collisions entre le joueur Pac-Man et les fantômes.
     *
     * @param playerCellX position de la cellule en X(rangée) du joueur Pac-Man.
     * @param playerCellY position de la cellule en Y(colonne) du joueur Pac-Man.
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
     * Méthode qui gère la collision avec un fantôme spécifique.
     *
     * @param ghost fantôme avec lequel le joueur Pac-Man a rencontré.
     */
    private void handleGhostCollision(Ghost ghost) {
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
     * Getter qui renvoie la liste des fantômes.
     *
     * @return liste des fantômes.
     */
    public List<Ghost> getGhosts() {
        return ghosts;
    }
}
