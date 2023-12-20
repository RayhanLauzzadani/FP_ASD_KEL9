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
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class GameBoardPanel extends JPanel {
    private static final long serialVersionUID = 1L;  // to prevent serial warning
    private String playerName;
    private boolean gameFinished = false;

    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    static int screenHeight = screenSize.height;
    int screenWidth = screenSize.width;
    
    // Define named constants for UI sizes
    public static int CELL_SIZE = screenHeight/14;   // Cell width/height in pixels
    public static final int BOARD_WIDTH  = CELL_SIZE * SudokuConstants.GRID_SIZE;
    public static final int BOARD_HEIGHT = CELL_SIZE * SudokuConstants.GRID_SIZE;
    // Board width/height in pixels

    // Define properties
    /** The game board composes of 9x9 Cells (customized JTextFields) */
    private Cell[][] cells = new Cell[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
    /** It also contains a Puzzle with array numbers and isGiven */
    private Puzzle puzzle = new Puzzle();
    JPanel gridsudoku = new JPanel();
    JComboBox<String> difficultyComboBox;
    private int totalScore;

    private JLabel scoreLabel = new JLabel("Total Score: 0");
    
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    /** Constructor */
    public GameBoardPanel() {
        super.setLayout(new BorderLayout());
        super.add(gridsudoku, BorderLayout.CENTER);
        gridsudoku.setPreferredSize(new Dimension(BOARD_WIDTH,BOARD_HEIGHT));
        gridsudoku.setBorder(new LineBorder(new Color(190, 208, 238),15));
        gridsudoku.setLayout(new GridLayout(SudokuConstants.SUBGRID_SIZE, SudokuConstants.SUBGRID_SIZE));

        // Allocate the 2D array of Cell, and added into JPanel.
        for (int row = 0; row < SudokuConstants.SUBGRID_SIZE; row++) {
            for (int col = 0; col < SudokuConstants.SUBGRID_SIZE; col++) {
                JPanel subgridpanel = new JPanel();
                subgridpanel.setLayout(new GridLayout(SudokuConstants.SUBGRID_SIZE, SudokuConstants.SUBGRID_SIZE));
                subgridpanel.setBorder(new LineBorder(new Color(251, 217, 216),2));
                for (int i=0; i < SudokuConstants.SUBGRID_SIZE; i++) {
                    for (int j=0; j < SudokuConstants.SUBGRID_SIZE; j++) {
                        cells[row*3+i][col*3+j] = new Cell(row*3+i, col*3+j);
                        subgridpanel.add(cells[row*3+i][col*3+j]);
                    }
                }
                gridsudoku.add(subgridpanel);
            }
        }

        // [TODO 3] Allocate a common listener as the ActionEvent listener for all the
        //  Cells (JTextFields)
            CellInputListener listener = new CellInputListener();

        // [TODO 4] Adds this common listener to all editable cells
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                if (cells[row][col].isEditable()) {
                    cells[row][col].addActionListener(listener);   // For all editable rows and cols
                }
            }
        }
        JPanel southPanel = new JPanel(new FlowLayout());
        southPanel.add(scoreLabel);

        super.add(southPanel, BorderLayout.SOUTH);
        super.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));

    }

    /**
     * Generate a new puzzle; and reset the gameboard of cells based on the puzzle.
     * You can call this method to start a new game.
     */
    public void newGame(String difficultyLevel) {
        // Generate a new puzzle based on difficulty level
        int level;
        int toGivenCells;
        switch (difficultyLevel.toLowerCase()) {
            case "easy":
                level = 1;
                toGivenCells = 76;
                break;
            case "medium":
                level = 2;
                toGivenCells = 50;
                break;
            case "hard":
                level = 3;
                toGivenCells = 30;
                break;
            default:
                level = 1; 
                toGivenCells = 60;
                // Default to medium if an invalid difficulty level is provided
        }
        
        // Generate a new puzzle
        puzzle.newPuzzle(level, toGivenCells);

        // Initialize all the 9x9 cells, based on the puzzle.
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                cells[row][col].newGame(puzzle.numbers[row][col], puzzle.isGiven[row][col]);
            }
        }
        totalScore = 0;
        updateScoreLabel();
    }

    public String getSelectedDifficultyLevel() {
        return (String) difficultyComboBox.getSelectedItem();
    }


    /**
     * Return true if the puzzle is solved
     * i.e., none of the cell have status of TO_GUESS or WRONG_GUESS
     */
    public boolean isSolved() {
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                if (cells[row][col].status == CellStatus.TO_GUESS || cells[row][col].status == CellStatus.WRONG_GUESS) {
                    return false;
                }
            }
        }
        return true;
    }

    // [TODO 2] Define a Listener Inner Class for all the editable Cells
    private class CellInputListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Get a reference of the JTextField that triggers this action event
            Cell sourceCell = (Cell)e.getSource();
            if (sourceCell.status == CellStatus.CORRECT_GUESS) {
                return; // Jangan izinkan perubahan jika sudah benar
            }
            int numberIn;
            try {
                // Retrieve the int entered
                numberIn = Integer.parseInt(sourceCell.getText());
            } catch (NumberFormatException ex) {
                // Handle non-integer input if needed
                return;
            }

            /*
             * [TODO 5] (later - after TODO 3 and 4)
             * Check the numberIn against sourceCell.number.
             * Update the cell status sourceCell.status,
             * and re-paint the cell via sourceCell.paint().
             */
            if (numberIn == sourceCell.number) {
                sourceCell.status = CellStatus.CORRECT_GUESS;
                totalScore += 10;
            } else {
                sourceCell.status = CellStatus.WRONG_GUESS;
            }
            sourceCell.paint();   // re-paint this cell based on its status

            /*
             * [TODO 6] (later)
             * Check if the player has solved the puzzle after this move,
             *   by calling isSolved(). Put up a congratulation JOptionPane, if so.
             */
            if (isSolved()) {
                JOptionPane.showMessageDialog(null, "Congratulation! Your Total Score: "+ totalScore);
                

                Object[] option = {"Yes", "No"};
                // Menampilkan dialog dengan opsi dan mendapatkan nilai kembaliannya
                int pilihan = JOptionPane.showOptionDialog(
                        null, // Komponen induk (null untuk dialog tengah layar)
                        "Do you wanna play again?", // Pesan dialog
                        "Congratulations! " + playerName, // Judul dialog
                        JOptionPane.DEFAULT_OPTION, // Tipe ikon (DEFAULT_OPTION untuk ikon default)
                        JOptionPane.QUESTION_MESSAGE, // Tipe pesan (QUESTION_MESSAGE untuk pertanyaan)
                        null, // Icon kustom (null untuk ikon default)
                        option, // Daftar opsi
                        option[0]); // Opsi default yang terpilih 
        
                // Menggunakan nilai kembaliannya untuk menentukan tindakan selanjutnya
                if (pilihan == JOptionPane.CLOSED_OPTION) {
                    System.out.println("Dialog ditutup tanpa pemilihan.");
                    System.exit(0);
                    }  else if (option[pilihan]==option[0]){
                        newGame(getSelectedDifficultyLevel());
                    } else {
                   JOptionPane.showMessageDialog(null, "Thank you for playing");
                   System.exit(0);
                }
            }
            updateScoreLabel();
        }
    }

    private void updateScoreLabel() {
        scoreLabel.setText("Total Score: " + totalScore);
    }

    public void solve(){
    for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
        for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
            cells[row][col].newGame(puzzle.numbers[row][col], true);
        }
    }
    if (isSolved() && !gameFinished) {
        gameFinished = true; // Set the game status to finished
        JOptionPane.showMessageDialog(null, "Congratulations! Puzzle solved! Your Total Score: " + totalScore);

        int choice = JOptionPane.showConfirmDialog(null, "Do you want to play again?", "Play Again",
                JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            newGame(getSelectedDifficultyLevel()); // Start a new game if the player chooses to play again
        } else {
            JOptionPane.showMessageDialog(null, "Thank you for playing");
            System.exit(0);
        }
    }
    updateScoreLabel();
    }
}