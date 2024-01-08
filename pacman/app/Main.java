package pacman.app;

import javax.swing.*;

/**
 * Classe principale du jeu Pac-Man.
 */
public class Main {
    /**
     * Méthode principale pour lancer le jeu.
     * @param args Les arguments de la ligne de commande (non utilisés).
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Jeu Pac-Man");
        Game game = new Game();
        GUI gui = new GUI(game);
        frame.add(gui);

        int windowWidth = (game.labyrinth.getWidth() * GUI.getCellSize()) + 19;
        int windowHeight = (game.labyrinth.getHeight() * GUI.getCellSize()) + 20;
        frame.setSize(windowWidth, windowHeight);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

