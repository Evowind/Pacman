package pacman.app;
import pacman.entities.Cell;
import pacman.entities.Ghost;
import pacman.state.PacState;

import javax.swing.*;
import java.awt.*;

public class GUI extends JPanel implements GameObserver {
    private final Game game;

    static final int CELL_SIZE = 30;

    public GUI(Game game) {
        this.game = game;
        addKeyListener(game);
        setFocusable(true);
        game.addObserver(this);
        setFocusTraversalKeysEnabled(false);
        requestFocus();
    }
    public void update() {
        repaint();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        drawLabyrinth(g2d);
        drawPlayer(g2d);
        drawGhosts(g2d);
        drawScoreAndInfo(g2d);
    }

    private void drawLabyrinth(Graphics2D g2d) {
        for (int i = 0; i < game.labyrinth.getHeight(); i++) {
            for (int j = 0; j < game.labyrinth.getWidth(); j++) {
                Cell cellValue = game.labyrinth.getCell(i,j);
                drawCell(g2d, j, i, cellValue);
            }
        }
    }

    private void drawPlayer(Graphics2D g2d) {
        if (game.pacman.state.getState() == PacState.State.SUPER) {
            g2d.setColor(Color.RED);
        } else {
            g2d.setColor(game.pacman.state.getState() == PacState.State.INVISIBLE ? new Color(203, 110, 32) : Color.YELLOW);
        }

        int startAngle = switch (game.pacman.getPlayerDirection()) {
            case 0 -> // Droite
                    45;
            case 1 -> // Gauche
                    225;
            case 2 -> // Haut
                    135;
            case 3 -> // Bas
                    315;
            default -> 0;
        };

        int extentAngle = switch (game.pacman.getPlayerDirection()) {
            case 0 -> // Droite
                    270;
            case 1 -> // Gauche
                    270;
            case 2 -> // Haut
                    270;
            case 3 -> // Bas
                    270;
            default -> 360;
        };

        g2d.fillArc(game.pacman.getPlayerX() * CELL_SIZE, game.pacman.getPlayerY()* CELL_SIZE, CELL_SIZE, CELL_SIZE, startAngle, extentAngle);
    }

    private void drawGhosts(Graphics2D g2d) {
        for (Ghost ghost : game.ghosts.getGhosts()) {
            g2d.setColor(game.pacman.state.getState() == PacState.State.SUPER ? Color.BLUE.darker() : ghost.getColor());
            g2d.fillRoundRect(ghost.getX() * CELL_SIZE, ghost.getY() * CELL_SIZE, CELL_SIZE, CELL_SIZE, 10, 10);
        }
    }

    private void drawScoreAndInfo(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Segoe UI", Font.BOLD, 18));

        String scoreText = "Score: " + game.getScore();
        String livesText = "Lives: " + game.getLives();
        String pacdotsRemainingText = "Pacdots restantes: " + game.labyrinth.countPacdots();

        int lineHeight = 25;
        int margin = 15;
        int startY = 25;

        g2d.drawString(scoreText, margin, startY);
        g2d.drawString(livesText, margin + (5 * lineHeight), startY);
        g2d.drawString(pacdotsRemainingText, margin + (9 * lineHeight), startY);
    }


    private void drawCell(Graphics2D g2d, int x, int y, Cell cellValue) {
        switch (cellValue) {
            case WALL:
                g2d.setColor(new Color(0, 0, 107));
                g2d.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                break;

            case PACDOT:
            case PURPLE:
            case ORANGE:
            case GREEN:
                if (game.labyrinth.getCell(y,x) != Cell.EMPTY) {
                    g2d.setColor(Color.BLACK);
                    g2d.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                    drawPacdot(g2d, x, y, getColorForCell(cellValue));
                }
                break;

            case EMPTY:
                g2d.setColor(Color.BLACK);
                g2d.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                break;

            case TELEPORTER:
                g2d.setColor(Color.RED);
                g2d.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                break;

            default:
                break;
        }
    }

    private void drawPacdot(Graphics2D g2d, int x, int y, Color color) {
        g2d.setColor(color);
        int pacdotSize = CELL_SIZE / 3;
        g2d.fillOval((x * CELL_SIZE) + (CELL_SIZE / 2) - pacdotSize / 2,
                (y * CELL_SIZE) + (CELL_SIZE / 2) - pacdotSize / 2,
                pacdotSize, pacdotSize);
    }

    private Color getColorForCell(Cell cellValue) {
        return switch (cellValue) {
            case PACDOT -> new Color(51,153,255);
            case PURPLE -> Color.MAGENTA;
            case ORANGE -> Color.ORANGE;
            case GREEN -> Color.GREEN;
            default -> Color.BLACK;
        };
    }
}
