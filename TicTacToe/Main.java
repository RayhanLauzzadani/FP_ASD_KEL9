package TicTacToe;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

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

/**
 * Tic-Tac-Toe: Two-player Graphic version with better OO design.
 * The Board and Cell classes are separated in their own classes.
 */
public class Main extends JPanel {
   private static final long serialVersionUID = 1L; // to prevent serializable warning

    private String player1Name;
    private String player2Name;

// Define named constants for the drawing graphics
   public static final String TITLE = "Tic Tac Toe";
   public static final Color COLOR_BG = new Color(20, 189, 172);
   public static final Color COLOR_BG_STATUS = new Color(216, 216, 216);
   public static final Color COLOR_CROSS = new Color(242, 235, 211);  // Red #EF6950
   public static final Color COLOR_NOUGHT = new Color(84, 84, 84); // Blue #409AE1
   public static final Font FONT_STATUS = new Font("Poppins", Font.PLAIN, 16);

   //nama player
   public String getPlayer1Name(){
    return player1Name;
   }

   public String getPlayer2Name(){
    return player2Name;
   }
   // Define game objects
   private Board board;         // the game board
   private State currentState;  // the current state of the game
   private Seed currentPlayer;  // the current player
   private JLabel statusBar;    // for displaying status message

   /** Constructor to setup the UI and game components */
   public Main() {
    // Meminta input nama pemain 1
    player1Name = JOptionPane.showInputDialog("Masukkan nama Player 1:");
    // Meminta input nama pemain 2
    player2Name = JOptionPane.showInputDialog("Masukkan nama Player 2:");

      // This JPanel fires MouseEvent
      super.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent e) {  // mouse-clicked handler
            int mouseX = e.getX();
            int mouseY = e.getY();
            // Get the row and column clicked
            int row = mouseY / Cell.SIZE;
            int col = mouseX / Cell.SIZE;

            if (currentState == State.PLAYING) {
               if (row >= 0 && row < Board.ROWS && col >= 0 && col < Board.COLS
                     && board.cells[row][col].content == Seed.NO_SEED) {
                  // Update cells[][] and return the new game state after the move
                  currentState = board.stepGame(currentPlayer, row, col);
                  // Switch player
                  currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
               }
            } else {        // game over
               newGame();  // restart the game
            }
            // Refresh the drawing canvas
            repaint();  // Callback paintComponent().
         }
      });

      // Setup the status bar (JLabel) to display status message
      statusBar = new JLabel();
      statusBar.setFont(FONT_STATUS);
      statusBar.setBackground(COLOR_BG_STATUS);
      statusBar.setOpaque(true);
      statusBar.setPreferredSize(new Dimension(300, 30));
      statusBar.setHorizontalAlignment(JLabel.LEFT);
      statusBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 12));

      super.setLayout(new BorderLayout());
      super.add(statusBar, BorderLayout.PAGE_END); // same as SOUTH
      super.setPreferredSize(new Dimension(Board.CANVAS_WIDTH, Board.CANVAS_HEIGHT + 30));
            // account for statusBar in height
      super.setBorder(BorderFactory.createLineBorder(COLOR_BG_STATUS, 2, false));

      // Set up Game
      initGame();
      newGame();
   }

   /** Initialize the game (run once) */
   public void initGame() {
      board = new Board();  // allocate the game-board
   }

   /** Reset the game-board contents and the current-state, ready for new game */
   public void newGame() {
      for (int row = 0; row < Board.ROWS; ++row) {
         for (int col = 0; col < Board.COLS; ++col) {
            board.cells[row][col].content = Seed.NO_SEED; // all cells empty
         }
      }
      currentPlayer = Seed.CROSS;    // cross plays first
      currentState = State.PLAYING;  // ready to play
   }

   /** Custom painting codes on this JPanel */
   @Override
   public void paintComponent(Graphics g) {  // Callback via repaint()
      super.paintComponent(g);
      setBackground(COLOR_BG); // set its background color

      board.paint(g);  // ask the game board to paint itself

      // Print status-bar message
      if (currentState == State.PLAYING) {
        statusBar.setForeground(Color.BLACK);
        statusBar.setText((currentPlayer == Seed.CROSS) ?
                player1Name + "'s Turn"  :  player2Name + "'s Turn");
      } else if (currentState == State.DRAW) {
         statusBar.setForeground(Color.RED);
         statusBar.setText("It's a Draw! Click to play again.");
      } else if (currentState == State.CROSS_WON) {
         statusBar.setForeground(new Color(8, 137,91));
         statusBar.setText( player1Name +" Won! Click to play again.");
      } else if (currentState == State.NOUGHT_WON) {
         statusBar.setForeground(new Color(8, 137,91));
         statusBar.setText( player2Name +" Won! Click to play again.");
      }
   }

   /** The entry "main" method */
   public static void main(String[] args) {
      // Run GUI construction codes in Event-Dispatching thread for thread safety
      javax.swing.SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            JFrame frame = new JFrame(TITLE);
            // Menambahkan window listener untuk menangkap kejadian penutupan frame
            frame.addWindowListener(new WindowAdapter() {
               @Override
               public void windowClosing(WindowEvent e) {
                        JOptionPane.showMessageDialog(null, "Thank you for playing");
                        System.exit(0);
               }
            });
            // Set the content-pane of the JFrame to an instance of main JPanel
            frame.setContentPane(new Main());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null); // center the application window
            frame.setVisible(true);            // show it
         }
      });
   }
}