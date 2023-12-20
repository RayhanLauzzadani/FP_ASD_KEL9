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

import java.awt.Color;
import java.awt.Font;
import javax.swing.*;
import javax.swing.JTextField;

/**
 * The Cell class model the cells of the Sudoku puzzle, by customizing (subclass)
 * the javax.swing.JTextField to include row/column, puzzle number and status.
 */
public class Cell extends JTextField {
    private static final long serialVersionUID = 1L;  // to prevent serial warning

    // Define named constants for JTextField's colors and fonts
    //  to be chosen based on CellStatus
    public static final Color BG_GIVEN = new Color(128, 136, 211); // RGB
    public static final Color FG_GIVEN = Color.white;
    public static final Color FG_NOT_GIVEN = Color.white;
    public static final Color BG_TO_GUESS  = new Color(251, 202, 171);
    public static final Color BG_CORRECT_GUESS = new Color(0, 216, 0);
    public static final Color BG_WRONG_GUESS   = new Color(216, 0, 0);
    public static final Font FONT_NUMBERS = new Font("Poppins", Font.PLAIN, 28);
 
    // Define properties (package-visible)
    /** The row and column number [0-8] of this cell */
    int row, col;
    /** The puzzle number [1-9] for this cell */
    int number;
    /** The status of this cell defined in enum CellStatus */
    CellStatus status;
    boolean islocked;
    int score;

    /** Constructor */
    public Cell(int row, int col) {
        super();   // JTextField
        this.row = row;
        this.col = col;
        setDocument(new LimitInputCell(1));
        // Inherited from JTextField: Beautify all the cells once for all
        super.setHorizontalAlignment(JTextField.CENTER);
        super.setFont(FONT_NUMBERS);
    }

    public Cell(int row, int col, int value){
        super(); //JTextField
        this.row = row;
        this.col = col;
        this.number = value;
        setDocument(new LimitInputCell(1));//Make cell has a limit length input
        super.setHorizontalAlignment(JTextField.CENTER);
        super.setFont(FONT_NUMBERS);
        this.score = 0;
    }

    /** Reset this cell for a new game, given the puzzle number and isGiven */
    public void newGame(int number, boolean isGiven) {
        this.number = number;
        this.score = 0;
        status = isGiven ? CellStatus.GIVEN : CellStatus.TO_GUESS;
        paint();    // paint itself
    }

    /** This Cell (JTextField) paints itself based on its status */
    public void paint() {
        if (status == CellStatus.GIVEN) {
            // Inherited from JTextField: Set display properties
            super.setText(number + "");
            super.setEditable(false);
            super.setBackground(BG_GIVEN);
            super.setForeground(FG_GIVEN);
        } else if (status == CellStatus.TO_GUESS) {
            // Inherited from JTextField: Set display properties
            super.setText("");
            super.setEditable(true);
            super.setBackground(BG_TO_GUESS);
            super.setForeground(FG_NOT_GIVEN);
        } else if (status == CellStatus.CORRECT_GUESS) {  // from TO_GUESS
            super.setBackground(BG_CORRECT_GUESS);
            updateScore(10);
        } else if (status == CellStatus.WRONG_GUESS) {    // from TO_GUESS
            super.setBackground(BG_WRONG_GUESS);
        }
    }

    public void updateScore(int points) {
        this.score += points;
    }

}