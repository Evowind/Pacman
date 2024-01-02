package pacman;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Jeu Pac-Man");
        Game game = new Game();
        GUI gui = new GUI(game); // Ajoutez le composant GUI explicitement
        frame.add(gui);

        int windowWidth = (Game.originalLabyrinth[0].length * Game.CELL_SIZE) + 19;
        int windowHeight = (Game.originalLabyrinth.length * Game.CELL_SIZE) + 20;
        frame.setSize(windowWidth, windowHeight);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

