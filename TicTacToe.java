import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Play a game of Tic-Tac-Toe with your friend.
 * 
 * @author Omar Dawoud 
 * @version 07/12/16
 */
public class TicTacToe
{
    public static final String PLAYER_X = "X"; // player using "X"
    public static final String PLAYER_O = "O"; // player using "O"
    public static final String EMPTY = " ";  // empty cell
    public static final String TIE = "T"; // game ended in a tie

    private String player;   // current player (PLAYER_X or PLAYER_O)
    private String winner;   // winner: PLAYER_X, PLAYER_O, TIE, EMPTY = in progress
    private int numFreeSquares =9; // number of squares still free
    private JButton board[][]; // 3x3 array representing the board
    private JPanel panel;   //creates a board panel for the game.
    private JTextArea status; // text area to print game status
    private ButtonHandler buttonhandler;
    private JFrame frame; // the frame of the game
    private JLabel label; //text area to display events
    private JMenuBar menubar; // Adds a list of options in the frame.
    private JMenu menu; // menu containing a list of options.
    private Font font;
    private boolean tie;

    /** 
     * Constructs a new Tic-Tac-Toe board, including the frame, menu with a list of 2 options.
     * The constructor initializes the board,panel font and menu etc.
     * Variables such as player and tie(to check if nobody wins) are also initialized.
     */
    public TicTacToe()
    {
        player = PLAYER_X;
        tie  = true;
        font = new Font("Times New Roman", Font.PLAIN, 20);
        board = new JButton [3][3];
        panel = new JPanel();
        panel.setLayout(new GridLayout(3,3));
        status = new JTextArea();
        buttonhandler = new ButtonHandler();
        label = new JLabel("Play a Game of Tic-tac-toe!");
        frame = new JFrame("Tic-Tac-Toe");

        menubar = new JMenuBar();
        menu = new JMenu("Menu");
        JMenuItem newgame = new JMenuItem("New game");
        JMenuItem quit = new JMenuItem("Quit");

        menu.setFont(font); 
        newgame.setFont(font);
        quit.setFont(font);
        quit.setAccelerator(KeyStroke.getKeyStroke('Q', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
        newgame.setAccelerator(KeyStroke.getKeyStroke('N', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
        status.setEditable(false);
        //sets the font of the frame.
        status.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        label.setFont(new Font("Times New Roman", Font.PLAIN, 30));

        //set the size of the frame.
        frame.setSize(new Dimension(500,500));

        //Sets the closing opertation of the program.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        quit.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e){
                    System.exit(0);
                }
            });
        newgame.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e){
                    for(int i = 0; i < 3; i++)
                    {
                        for(int j = 0; j < 3; j++)
                        {
                            board[i][j].setText("");
                            board[i][j].setEnabled(true);
                        }
                    }

                    tie = true;
                    numFreeSquares = 9;                                  
                    player = PLAYER_X;
                    label.setText("Play a Game of Tic-tac-toe!");
                }
            });

        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 3; j++)
            {
                board[i][j] = new JButton();
                board[i][j].addActionListener(buttonhandler);
                panel.add(board[i][j]);
            }
        }
        menu.add(newgame);
        menu.add(quit);
        menubar.add(menu);
        frame.setJMenuBar(menubar);

        frame.add(panel);
        frame.add(label,BorderLayout.SOUTH);
        frame.setVisible(true);       
    }

    /**
     * Returns true if filling the given square gives us a winner, and false
     * otherwise.
     *
     * @param int row of square just set
     * @param int col of square just set
     * 
     * @return true if we have a winner, false otherwise
     */
    private boolean haveWinner(int row, int col) 
    {
        // unless at least 5 squares have been filled, we don't need to go any further
        // (the earliest we can have a winner is after player X's 3rd move).

        if (numFreeSquares>4) return false;

        // Note: We don't need to check all rows, columns, and diagonals, only those
        // that contain the latest filled square.  We know that we have a winner 
        // if all 3 squares are the same, as they can't all be blank (as the latest
        // filled square is one of them).

        // check row "row"
        if ( board[row][0].getText().equals(board[row][1].getText()) &&
        board[row][0].getText().equals(board[row][2].getText()) )
        {
            return true;}

        // check column "col"
        if ( board[0][col].getText().equals(board[1][col].getText()) &&
        board[0][col].getText().equals(board[2][col].getText()) )
        {
            return true;}

        // if row=col check one diagonal
        if (row==col)
            if ( board[0][0].getText().equals(board[1][1].getText()) &&
            board[0][0].getText().equals(board[2][2].getText()) ) 
            {
                return true;}

        // if row=2-col check other diagonal
        if (row==2-col)
            if ( board[0][2].getText().equals(board[1][1].getText()) &&
            board[0][2].getText().equals(board[2][0].getText()) ) 
            {
                return true;}

        // no winner yet
        return false;
    }

    /**
     * Handles all events (button clicks) that occurs within the game.
     * Player turns are swapped everytime a button is clicked.
     */
    public class ButtonHandler implements ActionListener
    {     

        /**
         * handles button-clicking events when playing tic-tac-toe.
         * 
         * @param   Jbutton button          when this button is clicked, the player's symbol (X/O) is placed on the button.
         *          boolean tie             if this variable remains true when all buttons are clicked, the game returns a "Tie!"
         *          String  player          The player who is playing the game (X/O)
         *          int     numFreeSquares  number of available empty spaces on the tic-tac-toe board.
         *          JLabel  label           text that is displayed on the frame.                                 
         */
        public void actionPerformed(ActionEvent e)
        {
            JButton button = (JButton) e.getSource();
            button.setFont(new Font("Times New Roman", Font.PLAIN, 30));
            button.setText(player);
            button.setEnabled(false); 

            numFreeSquares = numFreeSquares - 1;

            if (player==PLAYER_X) 
                player=PLAYER_O;
            else
                player=PLAYER_X;
            label.setText("Player " + player + "'s turn.");

            for(int i = 0; i<3; i++)
            {
                for(int j = 0; j<3; j++)
                {
                    if(board[i][j] == button)
                    {                  
                        if(haveWinner(i,j))
                        {
                            tie = false;
                            if (player==PLAYER_X) 
                                player=PLAYER_O;
                            else
                                player=PLAYER_X;
                            label.setText("Player " + player + " Wins!");
                            for(int a = 0; a<3; a++)
                            {
                                for(int b = 0; b<3; b++)
                                {
                                    board[a][b].setEnabled(false);
                                }                       
                            }
                        }
                        if(tie == true && numFreeSquares == 0)
                        {
                            label.setText("Tie! Nobody wins.");
                        }
                    }

                }
            } 

        }
    }
}