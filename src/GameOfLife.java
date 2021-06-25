package life;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameOfLife extends JFrame {
    JFrame frame = this;
    JLabel aliveLabel; // количество живых клеток на поле в данном поколении
    JLabel generationLabel; // Текущее поколение
    BoardPanel gameBoard; // панель для отрисовки вселенной (поле с клетками)
    Universe universe;

    JTextField textField;
    JButton accept;

    JButton reset;
    JToggleButton pause;
    boolean isPaused = true;

    public GameOfLife() {
        super("GameOfLife");
        Universe universe = new Universe();
        this.universe = universe;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(43 + universe.getCurrentGeneration().length * 20,
                143 + universe.getCurrentGeneration().length * 20);
        setLocationRelativeTo(null);
        initComponents();
        setLayout(null);
        setVisible(true);
        game();
    }

    private void game() {
        while (true) {
            try {
                Thread.sleep(1500L);
            } catch (Exception e) {
            }

            if (!isPaused) {
                this.generationLabel.setText("Generation #" + universe.getCountGen());
                this.aliveLabel.setText("Alive: " + universe.checkAliveCell());
                this.gameBoard.universe = universe.getCurrentGeneration();

                this.gameBoard.repaint();

                universe.nextGeneration();
            }

        }
    }

    private void initComponents() {
        // Текущее поколение
        generationLabel = new JLabel("Generation #" + universe.getCountGen());
        generationLabel.setName("GenerationLabel");
        generationLabel.setBounds(10,45, 100,10);
        add(generationLabel);

        // количество живых клеток на поле в данном поколении
        aliveLabel = new JLabel("Alive: " + universe.checkAliveCell());
        aliveLabel.setName("AliveLabel");
        aliveLabel.setBounds(10,65, 100,10);
        add(aliveLabel);

        // панель для отрисовки вселенной (поле с клетками)
        gameBoard = new BoardPanel(universe.getCurrentGeneration());
        gameBoard.setBounds(10, 90,
                universe.getCurrentGeneration().length * 20 + 2,
                universe.getCurrentGeneration().length * 20 + 2);
        gameBoard.setBackground(Color.WHITE);
        add(gameBoard);

        reset = new JButton("Reset");
        reset.setName("ResetButton"); // !!!
        reset.setBounds(5, 5, 80, 30);
        reset.addActionListener(e -> universe = new Universe());
        add(reset);

        pause = new JToggleButton("Paused");
        pause.setName("PlayToggleButton"); // !!!
        pause.setBounds(90, 5, 80, 30);
        pause.addActionListener(e -> {

            if (pause.getText().equals("Paused")) {
                pause.setText("Playing");
                isPaused = !isPaused;
            } else if (pause.getText().equals("Playing")) {
                pause.setText("Paused");
                isPaused = !isPaused;
            }

        });
        add(pause);

        textField = new JTextField();
        textField.setBounds(175, 45, 80, 30);
        textField.setText("Enter size");
        add(textField);

        accept = new JButton("Accept");
        accept.setName("AcceptButton"); // !!!
        accept.setBounds(175, 5, 80, 30);
        accept.addActionListener(e -> {
            String t = textField.getText();
            try {
                int newSize = Integer.parseInt(t);

                universe = new Universe(newSize);

                int universeSide = universe.getCurrentGeneration().length * 20 + 2;
                int frameWidth = universeSide + 20 < 275 ? 275 : universeSide + 35;
                int frameHeight = 140 + universeSide;

                frame.setSize(frameWidth,
                        frameHeight);
                gameBoard.setBounds(10, 90,
                        universeSide,
                        universeSide);

            } catch (NumberFormatException exception) {
                textField.setText("Enter number");
            }
        });
        add(accept);
    }
}