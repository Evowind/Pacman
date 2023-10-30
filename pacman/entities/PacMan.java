package pacman.entities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import pacman.game.Maze;

// its PacMan
public class PacMan{ //JPanel
    // 0 = right
    // 1 = up
    // 2 = left
    // 3 = down
    public int direction;

    // coords
    public int x;
    public int y;

    // need a copy of maze to verify if movement is possible
    public Maze maze;

    // for drawing
    private int mouthAngle;
    private boolean mouthClosing;

    public PacMan(int x, int y){
        this.x = x;
        this.y = y;
        this.direction = 0;
        mouthAngle = 45;
        mouthClosing = true;
    }

    public void setDirection(int newDirection) {
        direction = newDirection;
    }

    public void move() {
        if (direction == 0) { //right
            if (isValidPosition(x+1, y)) x += x;

        } else if (direction == 1) { //up
            if (isValidPosition(y-1, y)) y -= y;

        } else if (direction == 2) { //left
            if (isValidPosition(x-1, y)) x -= x;

        } else if (direction == 3) { //down
            if (isValidPosition(y+1, y)) y += y;
        }

        printPosition(); // for testing
    }

    // to do
    private boolean isValidPosition(int gridX, int gridY) {
        if (gridX >= 0 && gridX < maze[0].length && gridY >= 0 && gridY < maze.length) {
            int cellValue = maze[gridY][gridX];
            return cellValue == 0 || cellValue == 2;
        }
        return false;
    }

    // for testing
    public void printPosition() {
        System.out.println("Position de Pac-Man - X: " + x + ", Y: " + y);
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




    /*
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

*/
}
