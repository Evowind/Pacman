package pacman.app;

/**
 * Interface observeur pour l'implémentation du patron de conception Observeur/Observable.
 */
public interface GameObserver {
    /**
     * Méthode a appeler quand l'observeur est notifié.
     */
    void update();
}
