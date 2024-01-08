package pacman.app;

import pacman.entities.Ghost;
import pacman.entities.PacMan;
import pacman.state.PacState;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe utilisée pour gérer une liste de fantomes.
 */
public class GhostList {
    /**
     * Liste d'objets Ghost.
     */
    private final List<Ghost> ghosts;
    /**
     * L'objet PacMan associé a la liste de fantomes.
     */
    private final PacMan pacman;
    /**
     * L'objet Game associé a la liste de fantomes.
     */
    private final Game game;

    /**
     * Constructeur de base de la classe GhostList.
     *
     * @param pacman objet PacMan associé a la liste de fantomes.
     * @param game objet Game associé a la liste de fantomes.
     */
    public GhostList(PacMan pacman, Game game){
        ghosts = new ArrayList<>();
        this.pacman = pacman;
        this.game = game;
    }

    /**
     * Méthode qui créer les fantomes dans la liste.
     */
    void initializeGhosts() {
        ghosts.clear();
        ghosts.add(new Ghost(12, 11, Color.CYAN));
        ghosts.add(new Ghost(13, 11, Color.WHITE));
        ghosts.add(new Ghost(14, 11, Color.ORANGE));
        ghosts.add(new Ghost(15, 11, Color.PINK));
    }

    public void moveGhosts() {
        for (Ghost ghost : ghosts) {
            ghost.moveRandomly(pacman.state.getState());
        }
    }

    /**
     * Méthode qui renvoie tout les fantomes au point de départ.
     * Utilisé lorsque PacMan mange un PacGum vert.
     */
    public void resetAllGhostsToCenter() {
        for (Ghost ghost : ghosts) {
            ghost.setX(12);
            ghost.setY(11);
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
     * Méthode qui vérifie si PacMan a rencontré un fantome.
     *
     * @param playerCellX numéro de rangée de PacMan.
     * @param playerCellY numéro de colonne de PacMan.
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
     * Méthode qui gère la collision.
     *
     * @param ghost le fantome qui a été rencontré.
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

    public List<Ghost> getGhosts() {
        return ghosts;
    }
}
