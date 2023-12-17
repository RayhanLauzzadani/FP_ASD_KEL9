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

import javax.swing.*;
import javax.swing.text.*;
import java.text.NumberFormat;

class LimitInputCell extends PlainDocument {
    private JFormattedTextField textField;
    private int limitNumber;

    LimitInputCell(int limitNumber) {
        super();
        this.limitNumber = limitNumber;
        this.textField = new JFormattedTextField(NumberFormat.getIntegerInstance());
        textField.setDocument(this); // Set document for the text field
    }

    @Override
    public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {

        if (str == null) {
            return;
        }

        // Only allow digits
        if (str.matches("\\d+") && !str.equals("0")) {
            if ((getLength() + str.length()) <= limitNumber) {
                super.insertString(offset, str, attr);
            }
        }
    }
}