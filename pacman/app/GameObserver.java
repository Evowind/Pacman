package pacman.app;

/**
 * Interface observeur pour l'implémentation du patron de conception Observeur/Observable.
 */
public interface GameObserver {
    /**
     * Méthode à appeler quand l'observeur notifié.
     */
    void update();
}
