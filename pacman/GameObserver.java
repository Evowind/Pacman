package pacman;

/**
 * Interface utilisée pour observer les changements dans le jeu Pac-Man.
 */
public interface GameObserver {

    /**
     * Méthode appelée pour notifier les observateurs des mises à jour dans le jeu.
     */
    void update();
}
