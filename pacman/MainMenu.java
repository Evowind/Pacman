package pacman;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JPanel {
    private JButton startButton;

    public MainMenu(Game game) {
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Pac-Man Game");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        startButton = new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.PLAIN, 18));
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Start the game
                game.resetGame();
                GUI gui = new GUI(game);
                JFrame frame = new JFrame("Jeu Pac-Man");
                frame.add(gui);
                int windowWidth = (Game.labyrinth[0].length * Game.CELL_SIZE) + 19;
                int windowHeight = (Game.labyrinth.length * Game.CELL_SIZE) + 20;
                frame.setSize(windowWidth, windowHeight);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
                getParent().removeAll();
                getParent().add(gui);
                getParent().revalidate();
                getParent().repaint();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton);
        add(buttonPanel, BorderLayout.CENTER);
    }
}
