package life;

import java.awt.*;

class BoardPanel extends Panel {
    Cell[][] universe;
    int size;

    public BoardPanel(Cell[][] universe) {
        this.universe = universe;
        size = universe.length;
    }

    @Override
    public void paint(Graphics g) {

        drawBoard(g);
        if (universe != null) {
            drawCell(g);
        }
    }

    public void drawBoard(Graphics g) {
        for (int i = 0; i <= universe.length; i++) {
            g.drawLine(1, 1 + i * 20, 20 * universe.length, 1 + i * 20);
            g.drawLine(1 + i * 20, 1, 1 + i * 20, 20 * universe.length);
        }
    }

    public void drawCell(Graphics g) {
        for (int i = 0; i < universe.length; i++) {
            for (int j = 0; j < universe.length; j++) {
                if (universe[i][j].isAlive()) {
                    g.fillRect(1 + i * 20, 1 + j * 20, 20, 20);
                }
            }
        }
    }
}
