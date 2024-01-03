package pacman;
import javax.swing.*;
import java.awt.*;

import static pacman.Game.CELL_SIZE;

public class GUI extends JPanel implements GameObserver {
    private final Game game;

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
        for (int i = 0; i < Game.labyrinth.length; i++) {
            for (int j = 0; j < Game.labyrinth[i].length; j++) {
                Cell cellValue = Game.labyrinth[i][j];
                drawCell(g2d, j, i, cellValue);
            }
        }
    }

    private void drawPlayer(Graphics2D g2d) {
        if (game.pacman.state.getState() == PacState.State.SUPER) {
            g2d.setColor(Color.RED);
        } else {
            g2d.setColor(game.pacman.state.getState() == PacState.State.INVISIBLE ? Color.ORANGE : Color.YELLOW);
        }

        int startAngle = 0;
        int extentAngle = 360;

        switch (game.pacman.getPlayerDirection()) {
            case 0: // Droite
                startAngle = 45;
                extentAngle = 270;
                break;
            case 1: // Gauche
                startAngle = 225;
                extentAngle = 270;
                break;
            case 2: // Haut
                startAngle = 135;
                extentAngle = 270;
                break;
            case 3: // Bas
                startAngle = 315;
                extentAngle = 270;
                break;
        }
        g2d.fillArc(game.pacman.getPlayerX() * CELL_SIZE, game.pacman.getPlayerY()* CELL_SIZE, CELL_SIZE, CELL_SIZE, startAngle, extentAngle);
    }

    private void drawGhosts(Graphics2D g2d) {
        for (Ghost ghost : game.getGhosts()) {
            g2d.setColor(game.pacman.state.getState() == PacState.State.SUPER ? Color.BLUE.darker() : ghost.getColor());
            g2d.fillRoundRect(ghost.getX() * CELL_SIZE, ghost.getY() * CELL_SIZE, CELL_SIZE, CELL_SIZE, 10, 10);
        }
    }

    private void drawScoreAndInfo(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Segoe UI", Font.BOLD, 18));

        String scoreText = "Score: " + game.getScore();
        String livesText = "Lives: " + game.getLives();
        String pacdotsRemainingText = "Pacdots restantes: " + game.getPacdotsRemaining();

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
                g2d.setColor(Color.BLUE);
                g2d.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                break;

            case PACDOT:
            case PURPLE:
            case ORANGE:
            case GREEN:
                if (Game.labyrinth[y][x] != Cell.EMPTY) {
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
        switch (cellValue) {
            case PACDOT:
                return Color.WHITE;
            case PURPLE:
                return Color.MAGENTA;
            case ORANGE:
                return Color.ORANGE;
            case GREEN:
                return Color.GREEN;
            default:
                return Color.BLACK;
        }
    }
}
