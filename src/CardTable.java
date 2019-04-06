import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;

public class CardTable extends JFrame
{
    static int MAX_CARDS_PER_HAND = 56;
    static int MAX_PLAYERS = 2;

    private int numCardsPerHand;
    private int numPlayers;

    public JPanel pnlComputerHand, getPnlHumanHand, pnlPlayArea;

    public CardTable(String title, int numCardsPerHand, int numPlayers)
    {

    }

    public int getNumCardsPerHand()
    {
        return numCardsPerHand;
    }

    public int getNumPlayers()
    {
        return numPlayers;
    }

}
