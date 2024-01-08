package pacman.app;

import javax.swing.*;

/**
 * Classe principale qui lance le jeu et une fenêtre dans laquelle ce dernier est affiché.
 */
public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Jeu Pac-Man");
        Game game = new Game();
        GUI gui = new GUI(game);
        frame.add(gui);

        int windowWidth = (game.labyrinth.getWidth() * GUI.CELL_SIZE) + 19;
        int windowHeight = (game.labyrinth.getHeight() * GUI.CELL_SIZE) + 20;
        frame.setSize(windowWidth, windowHeight);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

