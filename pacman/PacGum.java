
package pacman;
/*
public class PacGum {
    private static final long INVISIBLE_DURATION = 10000;
    private static final long SUPER_PACMAN_DURATION = 10000;

    private boolean isPacManInvisible;
    private long invisibleStartTime;
    private boolean isSuperPacMan;
    private long superPacManStartTime;

    PacGum() {
        isPacManInvisible = false;
        isSuperPacMan = false;
        invisibleStartTime = 0;
        superPacManStartTime = 0;
    }

    boolean isPacManInvisible() {
        return isPacManInvisible;
    }

    void activateInvisibility() {
        isPacManInvisible = true;
        invisibleStartTime = System.currentTimeMillis();
    }

    void resetInvisibility() {
        isPacManInvisible = false;
        invisibleStartTime = 0;
    }

    void updateInvisibility() {
        if (isPacManInvisible) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - invisibleStartTime >= INVISIBLE_DURATION) {
                isPacManInvisible = false;
            }
        }
    }

    boolean isSuperPacMan() {
        return isSuperPacMan;
    }

    void resetSuperPacMan() {
        isSuperPacMan = false;
        superPacManStartTime = 0;
    }

    void activateSuperPacMan() {
        isSuperPacMan = true;
        superPacManStartTime = System.currentTimeMillis();
    }

    void updateSuperPacMan() {
        if (isSuperPacMan) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - superPacManStartTime >= SUPER_PACMAN_DURATION) {
                isSuperPacMan = false;
            }
        }
    }

}*/

/*
public class PacGum {
    private PacState currentState;

    PacGum() {
        currentState = new NormalState();
    }

    boolean isPacManInvisible() {
        return currentState.getState() == PacState.State.INVISIBLE;
    }

    void activateInvisibility() {
        currentState = new InvisibleState();
    }

    void resetInvisibility() {
        currentState = new NormalState();
    }

    void updateInvisibility() {
        currentState.update();
    }

    boolean isSuperPacMan() {
        return currentState.getState() == PacState.State.SUPER;
    }

    void resetSuperPacMan() {
        currentState = new NormalState();
    }

    void activateSuperPacMan() {
        currentState = new SuperState();
    }

    void updateSuperPacMan() {
        currentState.update();
    }
}
*/