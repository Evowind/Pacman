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
        for (int i = 0; i < game.labyrinth.getHeight(); i++) {
            for (int j = 0; j < game.labyrinth.getWidth(); j++) {
                Cell cellValue = game.labyrinth.getCell(i,j);
                drawCell(g2d, j, i, cellValue);
            }
        }
    }

    private void drawPlayer(Graphics2D g2d) {
        if (game.getPacman().state.getState() == PacState.State.SUPER) {
            g2d.setColor(Color.RED);
        } else {
            g2d.setColor(game.getPacman().state.getState() == PacState.State.INVISIBLE ? new Color(255, 255, 153) : Color.YELLOW);
        }

        drawPacmanMouth(g2d, game.getPacman().getPlayerX(), game.getPacman().getPlayerY(), game.getPacman().getPlayerDirection());
    }

    private void drawPacmanMouth(Graphics2D g2d, int x, int y, int direction) {
        int startAngle = switch (direction) {
            case 0 -> 45;    // Droite
            case 1 -> 225;   // Gauche
            case 2 -> 135;   // Haut
            case 3 -> 315;   // Bas
            default -> 0;
        };
        int extentAngle = switch (direction) {
            case 0, 1, 2, 3 -> 270;
            default -> 360;
        };

        g2d.fillArc(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE, startAngle, extentAngle);
    }


    private void drawGhosts(Graphics2D g2d) {
        for (Ghost ghost : game.ghosts.getGhosts()) {
            g2d.setColor(game.getPacman().state.getState() == PacState.State.SUPER ? Color.BLUE.darker() : ghost.getColor());
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
            case WALL -> {
                g2d.setColor(new Color(0, 0, 107));
                g2d.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
            case PACDOT, PURPLE, ORANGE, GREEN -> {
                if (game.labyrinth.getCell(y, x) != Cell.EMPTY) {
                    g2d.setColor(Color.BLACK);
                    g2d.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                    drawPacdot(g2d, x, y, getColorForCell(cellValue));
                }
            }
            case EMPTY -> {
                g2d.setColor(Color.BLACK);
                g2d.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
            case TELEPORTER -> {
                g2d.setColor(Color.RED);
                g2d.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
            default -> {
            }
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
