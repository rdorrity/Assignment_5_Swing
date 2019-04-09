import javax.swing.*;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.BorderLayout;

public class CardTableTester extends JFrame
{
    static int MAX_CARDS_PER_HAND = 56;
    static int MAX_PLAYERS = 2;

    private int numCardsPerHand;
    private int numPlayers;

    public JPanel pnlComputerHand, pnlHumanHand, pnlPlayArea;

    // members for Assig5 class. Testing here
    static int NUM_CARDS_PER_HAND = 7;
    static int  NUM_PLAYERS = 2;
    static JLabel[] computerLabels = new JLabel[NUM_CARDS_PER_HAND];
    static JLabel[] humanLabels = new JLabel[NUM_CARDS_PER_HAND];
    static JLabel[] playedCardLabels  = new JLabel[NUM_PLAYERS];
    static JLabel[] playLabelText  = new JLabel[NUM_PLAYERS];


    public CardTable(String title, int numCardsPerHand, int numPlayers)
    {
        super(title);
        setSize(MAX_WIDTH, MAX_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        pnlComputerHand = new JPanel();
        pnlComputerHand.setLayout(new GridLayout(1, numCardsPerHand));
        add(pnlComputerHand, BorderLayout.NORTH);

        pnlPlayArea = new JPanel();
        pnlPlayArea.setLayout(new GridLayout(1,MAX_PLAYERS));
        add(pnlPlayArea, BorderLayout.CENTER);

        pnlHumanHand = new JPanel();
        pnlHumanHand.setLayout(new GridLayout(1, numCardsPerHand));
        add(pnlHumanHand, BorderLayout.SOUTH);
    }

    public int getNumCardsPerHand()
    {
        return numCardsPerHand;
    }

    public int getNumPlayers()
    {
        return numPlayers;
    }

    public static void main(String[] args)
    {
        int k;
        Icon tempIcon;

        // establish main frame in which program will run
        CardTable myCardTable
                = new CardTable("CardTable", NUM_CARDS_PER_HAND, NUM_PLAYERS);
        myCardTable.setSize(800, 600);
        myCardTable.setLocationRelativeTo(null);
        myCardTable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // show everything to the user
        myCardTable.setVisible(true);

        // CREATE LABELS ----------------------------------------------------
        //code goes here ...

        // ADD LABELS TO PANELS -----------------------------------------
        //code goes here ...

        // and two random cards in the play region (simulating a computer/hum ply)
        // code goes here ...

        // show everything to the user
        myCardTable.setVisible(true);
    }
}
