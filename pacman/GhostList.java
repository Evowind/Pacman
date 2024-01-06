package pacman;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GhostList {
    private List<Ghost> ghosts;

    private PacManObservable pacman;

    private Game game;

    public GhostList(PacManObservable pacman, Game game){
        ghosts = new ArrayList<>();
        this.pacman = pacman;
        this.game = game;
    }

    void initializeGhosts() {
        // Le cas d'un reset
        if (!ghosts.isEmpty()) {
            for (Ghost ghost : ghosts) {
                ghost.setX(12);
                ghost.setY(11);
            }
            // Le cas d'une nouvelle partie
        } else {
            ghosts.add(new Ghost(12, 11, Color.CYAN));
            ghosts.add(new Ghost(13, 11, Color.WHITE));
            ghosts.add(new Ghost(14, 11, Color.GRAY));
            ghosts.add(new Ghost(15, 11, Color.PINK));
        }
    }

    public void moveGhosts() {
        for (Ghost ghost : ghosts) {
            ghost.moveRandomly(pacman.state.getState());
        }
    }

    public void resetAllGhostsToCenter() {
        for (Ghost ghost : ghosts) {
            ghost.setX(12);
            ghost.setY(11);
        }
    }

    private void resetGhostToCenter(Color color) {
        for (Ghost ghost : ghosts) {
            if (ghost.getColor() == color) {
                ghost.setX(12);
                ghost.setY(11);
            }
        }
    }

    public void checkGhostCollisions(int playerCellX, int playerCellY) {
        for (Ghost ghost : ghosts) {
            int ghostCellX = ghost.getX();
            int ghostCellY = ghost.getY();

            if (playerCellX == ghostCellX && playerCellY == ghostCellY) {
                handleGhostCollision(ghost);
            }
        }
    }

    private void handleGhostCollision(Ghost ghost) {
        // state
        if (pacman.state.getState() == PacState.State.SUPER) {
            // Le fantome est mangé par le Super PacMan
            game.score += 400;
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
