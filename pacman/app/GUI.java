package pacman.app;
import pacman.entities.Cell;
import pacman.entities.Ghost;
import pacman.state.PacState;

import javax.swing.*;
import java.awt.*;

/**
 * Classe utilisée pour gérer l'interface graphique utilisant JPanel.
 * Celle-ci est un observeur de la classe Game.
 */
public class GUI extends JPanel implements GameObserver {
    /**
     * Variable contenant la partie a afficher.
     */
    private final Game game;
    /**
     * Variable contenant la taille d'une case du labyrinthe.
     */
    static final int CELL_SIZE = 30;

    /**
     * Constructeur de base de la classe GUI.
     *
     * @param game la partie que l'on souhaite afficher.
     */
    public GUI(Game game) {
        this.game = game;
        addKeyListener(game);
        setFocusable(true);
        game.addObserver(this);
        setFocusTraversalKeysEnabled(false);
        requestFocus();
    }

    /**
     * Méthode a appeler quand l'observable envoie une notification.
     */
    public void update() {
        repaint();
    }

    /**
     * Méthode qui dessine la partie en appelant les sous-méthodes.
     *
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        drawLabyrinth(g2d);
        drawPlayer(g2d);
        drawGhosts(g2d);
        drawScoreAndInfo(g2d);
    }

    /**
     * Méthode qui dessine la labyrinthe en appelant la sous méthode drawCell().
     *
     * @param g2d objet utilisé pour l'interprétation de formes en 2D.
     */
    private void drawLabyrinth(Graphics2D g2d) {
        for (int i = 0; i < game.labyrinth.getHeight(); i++) {
            for (int j = 0; j < game.labyrinth.getWidth(); j++) {
                Cell cellValue = game.labyrinth.getCell(i,j);
                drawCell(g2d, j, i, cellValue);
            }
        }
    }

    /**
     * Méthode qui dessine l'entité PacMan.
     *
     * @param g2d objet utilisé pour l'interprétation de formes en 2D.
     */
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

    /**
     * Méthode qui dessine les entités fantomes.
     *
     * @param g2d objet utilisé pour l'interprétation de formes en 2D.
     */
    private void drawGhosts(Graphics2D g2d) {
        for (Ghost ghost : game.ghosts.getGhosts()) {
            g2d.setColor(game.pacman.state.getState() == PacState.State.SUPER ? Color.BLUE.darker() : ghost.getColor());
            g2d.fillRoundRect(ghost.getX() * CELL_SIZE, ghost.getY() * CELL_SIZE, CELL_SIZE, CELL_SIZE, 10, 10);
        }
    }

    /**
     * Méthode qui dessine le score, le nombre de vies et le nombre de pacdots restants.
     *
     * @param g2d objet utilisé pour l'interprétation de formes en 2D.
     */
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

    /**
     * Méthode qui dessine une cellule.
     *
     * @param g2d objet utilisé pour l'interprétation de formes en 2D.
     */
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

    /**
     * Méthode qui dessine un pacdot.
     *
     * @param g2d objet utilisé pour l'interprétation de formes en 2D.
     */
    private void drawPacdot(Graphics2D g2d, int x, int y, Color color) {
        g2d.setColor(color);
        int pacdotSize = CELL_SIZE / 3;
        g2d.fillOval((x * CELL_SIZE) + (CELL_SIZE / 2) - pacdotSize / 2,
                (y * CELL_SIZE) + (CELL_SIZE / 2) - pacdotSize / 2,
                pacdotSize, pacdotSize);
    }

    /**
     * Méthode renvoie la couleur du pacdot.
     *
     * @param cellValue case pour laquelle on veut obtenir la couleur de son pacdot.
     */
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
