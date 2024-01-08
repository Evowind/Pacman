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
     * Variable contenant la taille d'une cellule dans le labyrinthe.
     */
    static final int CELL_SIZE = 30;

    /**
     * Initialise une nouvelle interface graphique pour le jeu Pac-Man.
     *
     * @param game Instance du jeu Pac-Man.
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
     * Méthode à appeler quand l'observable envoie une notification.
     * Met à jour l'interface graphique en appelant la méthode repaint().
     */
    public void update() {
        repaint();
    }

    /**
     * Méthode appelée pour dessiner les composants graphiques à l'aide de ses sous-méthodes.
     *
     * @param g Objet Graphics utilisé pour dessiner.
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
                Cell cellValue = game.labyrinth.getCell(i, j);
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
        if (game.getPacman().state.getState() == PacState.State.SUPER) {
            g2d.setColor(Color.RED);
        } else {
            g2d.setColor(game.getPacman().state.getState() == PacState.State.INVISIBLE ? new Color(255, 255, 153) : Color.YELLOW);
        }

        drawPacmanMouth(g2d, game.getPacman().getPlayerX(), game.getPacman().getPlayerY(), game.getPacman().getPlayerDirection());
    }

    /**
     * Méthode qui dessine la bouche de Pac-Man en fonction de l'orientation.
     *
     * @param g2d        Objet Graphics2D utilisé pour dessiner.
     * @param x          Coordonnée x du joueur Pac-Man.
     * @param y          Coordonnée y du joueur Pac-Man.
     * @param direction  Direction du joueur Pac-Man.
     */
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

    /**
     * Méthode qui dessine les entités fantomes.
     *
     * @param g2d objet utilisé pour l'interprétation de formes en 2D.
     */
    private void drawGhosts(Graphics2D g2d) {
        for (Ghost ghost : game.ghosts.getGhosts()) {
            g2d.setColor(game.getPacman().state.getState() == PacState.State.SUPER ? Color.BLUE.darker() : ghost.getColor());
            g2d.fillRoundRect(ghost.getX() * CELL_SIZE, ghost.getY() * CELL_SIZE, CELL_SIZE, CELL_SIZE, 10, 10);
        }
    }

    /**
     * Méthode qui dessine le score, le nombre de vies et le nombre de pacdots restants en haut de l'écran.
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
     * Méthode qui dessine une cellule du labyrinthe.
     *
     * @param g2d       Objet Graphics2D utilisé pour dessiner.
     * @param x         Coordonnée x de la cellule.
     * @param y         Coordonnée y de la cellule.
     * @param cellValue Valeur de la cellule.
     */
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

                    // Change la taille de la pacdots en fonction du type
                    int pacdotSize = (cellValue == Cell.ORANGE || cellValue == Cell.PURPLE || cellValue == Cell.GREEN) ?
                            CELL_SIZE / 2 : CELL_SIZE / 3;

                    drawPacdot(g2d, x, y, getColorForCell(cellValue), pacdotSize);
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

    /**
     * Méthode qui dessine une pacdot.
     *
     * @param g2d        Objet Graphics2D utilisé pour dessiner.
     * @param x          Coordonnée x de la pacdot.
     * @param y          Coordonnée y de la pacdot.
     * @param color      Couleur de la pacdot.
     * @param pacdotSize Taille de la pacdot.
     */
    private void drawPacdot(Graphics2D g2d, int x, int y, Color color, int pacdotSize) {
        g2d.setColor(color);
        g2d.fillOval((x * CELL_SIZE) + (CELL_SIZE / 2) - pacdotSize / 2,
                (y * CELL_SIZE) + (CELL_SIZE / 2) - pacdotSize / 2,
                pacdotSize, pacdotSize);
    }

    /**
     * Méthode qui renvoie la couleur associée à une valeur de cellule.
     *
     * @param cellValue Valeur de la cellule.
     * @return Couleur associée à la cellule.
     */
    private Color getColorForCell(Cell cellValue) {
        return switch (cellValue) {
            case PACDOT -> new Color(51, 153, 255);
            case PURPLE -> Color.MAGENTA;
            case ORANGE -> Color.ORANGE;
            case GREEN -> Color.GREEN;
            default -> Color.BLACK;
        };
    }

    /**
     * Renvoie la taille d'une cellule dans le labyrinthe.
     *
     * @return Taille d'une cellule.
     */
    public static int getCellSize() {
        return CELL_SIZE;
    }
}