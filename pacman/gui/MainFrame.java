package pacman.gui;

import pacman.entities.PacMan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MainFrame extends JFrame {
    private PacMan pacMan;
    private JPanel gamePanel;
    private static final int MAZE_WIDTH = 27;
    private static final int MAZE_HEIGHT = 31;
    private static final int CELL_SIZE = 10;

    public MainFrame() {
        initializeWindow();
        createGamePanel();
        initializePacMan();
        addKeyListenerForPacMan();
    }

    // need to add timer somewhere
    private void initializeWindow() {
        setTitle("Pac-Man");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 900);
        setLocationRelativeTo(null);
    }

    // make a Maze class to handle all board methods ?
    // such as Create Board, Change Board (PacGum vert)
    private void createGamePanel() {
        gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(MAZE_HEIGHT, MAZE_WIDTH));
        int[][] maze = {{0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {0, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
                {1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1},
                {1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1},
                {1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1},
                {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
                {1, 2, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 2, 1},
                {1, 2, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 2, 1},
                {1, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 1},
                {1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 0, 0, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 0, 0, 0, 0, 0, 0, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1},
                {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 0, 0, 0, 0, 0, 0, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
                {1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 0, 0, 0, 0, 0, 0, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1},
                {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
                {1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1},
                {1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1},
                {1, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 2, 0, 0, 2, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 1},
                {1, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 1},
                {1, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 1},
                {1, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 1},
                {1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1},
                {1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1},
                {1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        };
        for (int[] row : maze) {
            for (int cellValue : row) {
                JPanel cell = createMazeCell(cellValue);
                gamePanel.add(cell);
            }
        }
        getContentPane().add(gamePanel, BorderLayout.CENTER);
        pacMan = new PacMan(maze);
    }

    private JPanel createMazeCell(int value) {
        JPanel cell = new JPanel();
        cell.setLayout(new BorderLayout());

        if (value == 1) {
            cell.setBackground(Color.BLACK);
        } else if (value == 2) {
            JPanel pacdot = createPacdot();
            cell.setBackground(Color.BLUE);
            cell.add(pacdot, BorderLayout.CENTER);
        } else {
            cell.setBackground(Color.BLUE);
        }

        return cell;
    }

    private JPanel createPacdot() {
        JPanel pacdot = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int diameter = 5;
                int x = (getWidth() - diameter) / 2;
                int y = (getHeight() - diameter) / 2;
                g.setColor(Color.WHITE);
                g.fillOval(x, y, diameter, diameter);
            }
        };
        pacdot.setOpaque(false);
        return pacdot;
    }

    private void initializePacMan() {
        int pacManRow = 0;
        int pacManCol = 0;
        JPanel pacManCell = (JPanel) gamePanel.getComponent(pacManRow * MAZE_WIDTH + pacManCol);
        pacManCell.add(pacMan);
        int pacManX = pacManCol * CELL_SIZE;
        int pacManY = pacManRow * CELL_SIZE;
        pacMan.setBounds(pacManX, pacManY, CELL_SIZE, CELL_SIZE);
    }

    private void addKeyListenerForPacMan() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                int newDirection = -1;

                if (keyCode == KeyEvent.VK_RIGHT) {
                    newDirection = PacMan.RIGHT;
                } else if (keyCode == KeyEvent.VK_UP) {
                    newDirection = PacMan.UP;
                } else if (keyCode == KeyEvent.VK_LEFT) {
                    newDirection = PacMan.LEFT;
                } else if (keyCode == KeyEvent.VK_DOWN) {
                    newDirection = PacMan.DOWN;
                }

                if (newDirection != -1) {
                    pacMan.setDirection(newDirection);
                }
            }
        });
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        pacMan.setFocusable(true);
        pacMan.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame game = new MainFrame();
            game.setVisible(true);
        });
    }
}
