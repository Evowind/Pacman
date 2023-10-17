package pacman.gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MazeCell extends JPanel {
    //private List<PacDot> pacDots = new ArrayList<>();

    public MazeCell(int cellType) {
        setLayout(new BorderLayout());
        setOpaque(false);

        if (cellType == 1) {
            setBackground(Color.BLACK); // Mur
        } else if (cellType == 2) {
            setBackground(Color.BLUE); // Espace vide
        }
    }

    /*public void addPacDot(PacDot pacDot) {
        pacDots.add(pacDot);
        add(pacDot);
    }*/
}
