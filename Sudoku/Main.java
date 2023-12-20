package Sudoku;

/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2023/2024
 * Group Capstone Project
 * Group #9
 * 1 - Widyantari Nuriyanti - 5026221137
 * 2 - Samuel Hutagalung - 5026221067
 * 3 - Tommy Gunawan - 5026221037
 * 4 - Yanuar Audrey Sulistiyo - 5026221074
 * 5 - Rayhan Lauzzadani - 5026221186
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * The main Sudoku program
 */
public class Main extends JFrame {
    private static final long serialVersionUID = 1L; // to prevent serial warning
    private String playerName;
    // private variables
    GameBoardPanel board = new GameBoardPanel();
    JButton btnNewGame = new JButton("New Game");
    JButton btnSolve = new JButton("Solve");
    JLabel timerLabel = new JLabel("Timer: 0 seconds");
    JLabel scoreLabel = new JLabel();

    // Timer variables
    private Timer timer;
    private int seconds;
    private int totalScore;

    JComboBox<String> difficultyComboBox;
    JButton btnChangeDifficulty;

    // inisiasi PlayerName
    public String getPlayerName() {
        return playerName;
    }

    // Constructor
    public Main() {
        // Prompt the user to select difficulty
        String[] difficultyOptions = { "Easy", "Medium", "Hard" };
        difficultyComboBox = new JComboBox<>(difficultyOptions);
        // Prompt the user to enter their name
        playerName = JOptionPane.showInputDialog(this, "Enter your name:");
        JOptionPane.showMessageDialog(this, difficultyComboBox, "Select Difficulty", JOptionPane.QUESTION_MESSAGE);

        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        cp.add(board, BorderLayout.CENTER);

        // Add buttons and timer label to the south to re-start the game via
        // board.newGame() and restart
        JPanel buttonPanel = new JPanel();
        JButton changeDifficultyButton = new JButton("Change Difficulty");
        buttonPanel.add(changeDifficultyButton);
        buttonPanel.add(btnNewGame);
        buttonPanel.add(btnSolve);
        buttonPanel.add(timerLabel);
        buttonPanel.add(scoreLabel);
        cp.add(buttonPanel, BorderLayout.SOUTH);

        changeDifficultyButton.addActionListener(e -> showDifficultyDialog());

        // Add ActionListener for New Game button
        btnNewGame.addActionListener(e -> startNewGame());
        btnSolve.addActionListener(e -> solvebtn());

        // Initialize the game board and timer
        initializeTimer();
        board.newGame(getSelectedDifficultyLevel());
        totalScore = 0;
        startTimer();

        pack(); // Pack the UI components, instead of using setSize()
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // to handle window-closing
        setTitle("Sudoku");
        setVisible(true);
    }
    
    private void solvebtn() { // Call the newGame method on button click
        timer.stop();
        board.solve();
        board.newGame(getSelectedDifficultyLevel());
    }

    // Method to start a new game
    private void startNewGame() {
        // Prompt the user to enter their name if not provided
        if (playerName == null || playerName.trim().isEmpty()) {
            playerName = JOptionPane.showInputDialog(this, "Enter your name:");
        }

        restartTimer();
        totalScore = 0;
        board.newGame(getSelectedDifficultyLevel());
    }

    private String getSelectedDifficultyLevel() {
        return (String) difficultyComboBox.getSelectedItem();
    }

    private void showDifficultyDialog() {
        JOptionPane.showMessageDialog(this, difficultyComboBox, "Change Difficulty", JOptionPane.QUESTION_MESSAGE);
        board.newGame(getSelectedDifficultyLevel());
        restartTimer();
    }

    // Method to initialize the timer
    private void initializeTimer() {
        seconds = 0;
        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                seconds++;
                updateTimerLabel();
            }
        });
    }

    // Method to start the timer
    private void startTimer() {
        timer.start();
    }

    // Method to restart the timer
    private void restartTimer() {
        timer.stop();
        seconds = 0;
        updateTimerLabel();
        timer.start();
    }
    

    // Method to update the timer label
    private void updateTimerLabel() {
        timerLabel.setText("Timer: " + seconds + " seconds");
    }    

    /** The entry main() entry method */
    public static void main(String[] args) {
        // Run GUI codes in the Event-Dispatching thread for thread safety
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main(); // Let the constructor do the job
            }
        });
    }
}