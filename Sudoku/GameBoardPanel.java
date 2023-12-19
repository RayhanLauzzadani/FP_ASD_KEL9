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
    private Timer timer;
    private int seconds;

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
    

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    /** Constructor */
    public GameBoardPanel() {
        super.setLayout(new GridLayout());  // JPanel
        super.add(gridsudoku, BorderLayout.CENTER);
        gridsudoku.setPreferredSize(new Dimension(BOARD_WIDTH,BOARD_HEIGHT));
        gridsudoku.setBorder(new LineBorder(Color.blue,10));
        gridsudoku.setLayout(new GridLayout(SudokuConstants.SUBGRID_SIZE, SudokuConstants.SUBGRID_SIZE));

        // Allocate the 2D array of Cell, and added into JPanel.
        for (int row = 0; row < SudokuConstants.SUBGRID_SIZE; row++) {
            for (int col = 0; col < SudokuConstants.SUBGRID_SIZE; col++) {
                JPanel subgridpanel = new JPanel();
                subgridpanel.setLayout(new GridLayout(SudokuConstants.SUBGRID_SIZE, SudokuConstants.SUBGRID_SIZE));
                subgridpanel.setBorder(new LineBorder(Color.black,2));
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

        super.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));

    }

    /**
     * Generate a new puzzle; and reset the gameboard of cells based on the puzzle.
     * You can call this method to start a new game.
     */
    public void newGame() {
        // Generate a new puzzle
        puzzle.newPuzzle(2);

        // Initialize all the 9x9 cells, based on the puzzle.
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                cells[row][col].newGame(puzzle.numbers[row][col], puzzle.isGiven[row][col]);
            }
        }
    }

    public void restartTimer() {
        timer.stop();
        seconds = 0;
        timer.start();
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

            // Retrieve the int entered
            int numberIn = Integer.parseInt(sourceCell.getText());
            // For debugging
            System.out.println("You entered " + numberIn);

            /*
             * [TODO 5] (later - after TODO 3 and 4)
             * Check the numberIn against sourceCell.number.
             * Update the cell status sourceCell.status,
             * and re-paint the cell via sourceCell.paint().
             */
            if (numberIn == sourceCell.number) {
                sourceCell.status = CellStatus.CORRECT_GUESS;
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
                JOptionPane.showMessageDialog(null, "Congratulation!");

                Object[] opsi = {"Yes", "No"};
                // Menampilkan dialog dengan opsi dan mendapatkan nilai kembaliannya
                int pilihan = JOptionPane.showOptionDialog(
                        null, // Komponen induk (null untuk dialog tengah layar)
                        "Do you wanna play again?", // Pesan dialog
                        "Congratulations! " + playerName, // Judul dialog
                        JOptionPane.DEFAULT_OPTION, // Tipe ikon (DEFAULT_OPTION untuk ikon default)
                        JOptionPane.QUESTION_MESSAGE, // Tipe pesan (QUESTION_MESSAGE untuk pertanyaan)
                        null, // Icon kustom (null untuk ikon default)
                        opsi, // Daftar opsi
                        opsi[0]); // Opsi default yang terpilih 
        
                // Menggunakan nilai kembaliannya untuk menentukan tindakan selanjutnya
                if (pilihan == JOptionPane.CLOSED_OPTION) {
                    System.out.println("Dialog ditutup tanpa pemilihan.");
                    System.exit(0);
                    }  else if (opsi[pilihan]==opsi[0]){
                        newGame();
                    } else {
                   JOptionPane.showMessageDialog(null, "Thank you for playing");
                   System.exit(0);
                }
            }
        }
    }

}