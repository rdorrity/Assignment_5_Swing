import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;

public class CardTable extends JFrame implements ActionListener
{
    static int MAX_CARDS_PER_HAND = 56;
    static int MAX_PLAYERS = 2;
    static int MAX_WIDTH = 1366;
    static int MAX_HEIGHT = 768;

    static int HAND_HEIGHT = 150;
    static int PLAY_HEIGHT = 400;

    private int numCardsPerHand = 5;
    private int numPlayers = 1;

    public JPanel pnlComputerHand, pnlHumanHand, pnlPlayArea;

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

    public void actionPerformed(ActionEvent e)
    {
        String buttonString = e.getActionCommand();

    }
    public static void main(String[] args)
    {
        CardTable gui = new CardTable("Table Demo", 1,5);
        gui.setVisible(true);
    }
}
