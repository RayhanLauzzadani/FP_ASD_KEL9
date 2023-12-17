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
import java.awt.*;

public class Main extends JFrame {
    private static final long serialVersionUID = 1L;  // to prevent serial warning
    private String playerName;
    // private variables
    GameBoardPanel board = new GameBoardPanel();
    JButton btnNewGame = new JButton("New Game");
    JPanel buttonPanel = new JPanel();
    JButton btnSolve = new JButton("Solve");
    
    public String getPlayerName() {
            return playerName;
        }
    // Constructor
    public Main() {
        // Meminta input nama pemain
        playerName = JOptionPane.showInputDialog("Masukkan nama Player:");
        board.setPlayerName(playerName);
        Object[] opsi = {"Easy", "Medium", "Hard"};

        // Menampilkan dialog dengan opsi dan mendapatkan nilai kembaliannya
        int pilihan = JOptionPane.showOptionDialog(
                null, // Komponen induk (null untuk dialog tengah layar)
                "Select Difficulties", // Pesan dialog
                "Difficulties ", // Judul dialog
                JOptionPane.DEFAULT_OPTION, // Tipe ikon (DEFAULT_OPTION untuk ikon default)
                JOptionPane.QUESTION_MESSAGE, // Tipe pesan (QUESTION_MESSAGE untuk pertanyaan)
                null, // Icon kustom (null untuk ikon default)
                opsi, // Daftar opsi
                opsi[0]); // Opsi default yang terpilih

        // Menggunakan nilai kembaliannya untuk menentukan tindakan selanjutnya
        if(pilihan == JOptionPane.CLOSED_OPTION) {
            System.out.println("Dialog ditutup tanpa pemilihan.");
            System.exit(0);
        } else if (opsi[pilihan]==opsi[0]) {
            Container cp = getContentPane();
            cp.setLayout(new BorderLayout());

            cp.add(board, BorderLayout.CENTER);

            // Add a button to the south to re-start the game via board.newGame()
            btnNewGame.addActionListener(e -> board.EasyGame()); // Add ActionListener to the button
            cp.add(btnNewGame, BorderLayout.SOUTH); // Add button to the south

            btnSolve.addActionListener(e -> board.SolveGame());
            buttonPanel.setLayout(new FlowLayout());
            buttonPanel.add(btnNewGame);
            buttonPanel.add(btnSolve);
            cp.add(buttonPanel, BorderLayout.SOUTH);
            // Initialize the game board to start the game
            board.EasyGame();
            
            pack();     // Pack the UI components, instead of using setSize()
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // to handle window-closing
            setTitle("Sudoku");
            setVisible(true);
        } else if (opsi[pilihan]==opsi[1]){
            Container cp = getContentPane();
            cp.setLayout(new BorderLayout());

            cp.add(board, BorderLayout.CENTER);

            // Add a button to the south to re-start the game via board.newGame()
            btnNewGame.addActionListener(e -> board.MediumGame()); // Add ActionListener to the button
            cp.add(btnNewGame, BorderLayout.SOUTH); // Add button to the south

            btnSolve.addActionListener(e -> board.SolveGame());
            buttonPanel.setLayout(new FlowLayout());
            buttonPanel.add(btnNewGame);
            buttonPanel.add(btnSolve);
            cp.add(buttonPanel, BorderLayout.SOUTH);
            // Initialize the game board to start the game
            board.MediumGame();

            pack();     // Pack the UI components, instead of using setSize()
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // to handle window-closing
            setTitle("Sudoku");
            setVisible(true);
        } else if (opsi[pilihan]==opsi[2]){
            Container cp = getContentPane();
            cp.setLayout(new BorderLayout());

            cp.add(board, BorderLayout.CENTER);

            // Add a button to the south to re-start the game via board.newGame()
            btnNewGame.addActionListener(e -> board.HardGame()); // Add ActionListener to the button
            cp.add(btnNewGame, BorderLayout.SOUTH); // Add button to the south

            btnSolve.addActionListener(e -> board.SolveGame());

            buttonPanel.setLayout(new FlowLayout());
            buttonPanel.add(btnNewGame);
            buttonPanel.add(btnSolve);
            cp.add(buttonPanel, BorderLayout.SOUTH);
            // Initialize the game board to start the game
            board.HardGame();

            pack();     // Pack the UI components, instead of using setSize()
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // to handle window-closing
            setTitle("Sudoku");
            setVisible(true);
        }
        
            
    }

    /** The entry main() entry method */
    public static void main(String[] args) {
        // [TODO 1] Check "Swing program template" on how to run
        // the constructor of "SudokuMain"
        SwingUtilities.invokeLater(() -> {
            new Main();
        });
    }
}
