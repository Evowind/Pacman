package pacman.entities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PacMan extends JPanel {
    private static final int CELL_SIZE = 10;
    private static final int DIAMETER = 25;
    private static final int SPEED = 1;

    private int x;
    private int y;
    private int direction;
    private Timer timer;
    private int[][] maze;
    private int mouthAngle;
    private boolean mouthClosing;

    public static final int RIGHT = 0;
    public static final int UP = 1;
    public static final int LEFT = 2;
    public static final int DOWN = 3;

    public PacMan(int[][] maze) {
        this.maze = maze;
        direction = RIGHT;
        mouthAngle = 45;
        mouthClosing = true;

        timer = new Timer(200, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                animateMouth();
                move();
                repaint();
            }
        });
        timer.start();
    }

    private void animateMouth() {
        if (mouthClosing) {
            mouthAngle -= 10;
            if (mouthAngle <= 0) {
                mouthClosing = false;
            }
        } else {
            mouthAngle += 10;
            if (mouthAngle >= 45) {
                mouthClosing = true;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.BLUE);
        g.setColor(Color.YELLOW);

        if (direction == RIGHT) {
            g.fillArc(x, y, DIAMETER, DIAMETER, mouthAngle, 360 - (2 * mouthAngle));
        } else if (direction == UP) {
            g.fillArc(x, y, DIAMETER, DIAMETER, 90 + mouthAngle, 360 - (2 * mouthAngle));
        } else if (direction == LEFT) {
            g.fillArc(x, y, DIAMETER, DIAMETER, 180 + mouthAngle, 360 - (2 * mouthAngle));
        } else if (direction == DOWN) {
            g.fillArc(x, y, DIAMETER, DIAMETER, -90 + mouthAngle, 360 - (2 * mouthAngle));
        }
    }

    public void setDirection(int newDirection) {
        direction = newDirection;
    }

    public void move() {
        int newX = x;
        int newY = y;
        int gridSize = CELL_SIZE;

        if (direction == RIGHT) {
            newX += gridSize;
        } else if (direction == UP) {
            newY -= gridSize;
        } else if (direction == LEFT) {
            newX -= gridSize;
        } else if (direction == DOWN) {
            newY += gridSize;
        }

        int gridX = newX / gridSize;
        int gridY = newY / gridSize;

        if (isValidPosition(gridX, gridY)) {
            x = newX;
            y = newY;
            printPosition();
        }
    }

    private boolean isValidPosition(int gridX, int gridY) {
        if (gridX >= 0 && gridX < maze[0].length && gridY >= 0 && gridY < maze.length) {
            int cellValue = maze[gridY][gridX];
            return cellValue == 0 || cellValue == 2;
        }
        return false;
    }

    public void printPosition() {
        System.out.println("Position de Pac-Man - X: " + x + ", Y: " + y);
    }
}
