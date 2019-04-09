import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

//class CardGameFramework  ----------------------------------------------------
class CardGameFramework
{
    private static final int MAX_PLAYERS = 50;

    private int numPlayers;
    private int numPacks;            // # standard 52-card packs per deck
    // ignoring jokers or unused cards
    private int numJokersPerPack;    // if 2 per pack & 3 packs per deck, get 6
    private int numUnusedCardsPerPack;  // # cards removed from each pack
    private int numCardsPerHand;        // # cards to deal each player
    private Deck deck;               // holds the initial full deck and gets
    // smaller (usually) during play
    private Hand[] hand;             // one Hand for each player
    private Card[] unusedCardsPerPack;   // an array holding the cards not used
    // in the game.  e.g. pinochle does not
    // use cards 2-8 of any suit

    public static void main(String[] args)
    {
        int NUM_PLAYERS = 2;
        int NUM_CARDS_PER_HAND = 7;
        int numPacksPerDeck = 1;
        int numJokersPerPack = 2;
        int numUnusedCardsPerPack = 0;
        Card[] unusedCardsPerPack = null;

        CardGameFramework highCardGame = new CardGameFramework(
                numPacksPerDeck, numJokersPerPack,
                numUnusedCardsPerPack, unusedCardsPerPack,
                NUM_PLAYERS, NUM_CARDS_PER_HAND);
        

        for (int i = 0; i < deck.getNumCards(); i++) {
            System.out.println(deck.inspectCard(i));
        }
        Card rando = highCardGame.getCardFromDeck();
        Card twoRando = highCardGame.getCardFromDeck();
        System.out.println("\nRando cards below: ");
        System.out.println(rando.toString());
        System.out.println(twoRando.toString());
        deck.dealCard();
        deck.dealCard();
        deck.dealCard();
        System.out.println("\nCards should be sorted below:");
        deck.sort();
        for (int i = 0; i < deck.getNumCards(); i++) {
            System.out.println(deck.inspectCard(i));
        }

        // "On create" method
        CardTable myCardTable
                = new CardTable("CardTable", NUM_CARDS_PER_HAND, NUM_PLAYERS);
        myCardTable.setSize(1366, 768);
        myCardTable.setLayout(new BoxLayout(myCardTable.getContentPane(), BoxLayout.Y_AXIS));
        myCardTable.setLocationRelativeTo(null);
        myCardTable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create JPanels, set borders and panel text
        TitledBorder playerBorder =
                BorderFactory.createTitledBorder("Player");
        TitledBorder compBorder =
                BorderFactory.createTitledBorder("Computer");
        TitledBorder fieldBorder =
                BorderFactory.createTitledBorder("Field");

        // JLabels for field labels
        JLabel playerFieldLabel = new JLabel("Player", JLabel.CENTER);
        JLabel compFieldLabel = new JLabel("Computer", JLabel.CENTER);

        // Add test text labels for center playing field, replace with Cards
        // later

        myCardTable.pnlComputerHand.setBorder(compBorder);

        //JPanel playField = new JPanel();
        myCardTable.pnlPlayArea.setBorder(fieldBorder);

        //JPanel playHand = new JPanel();
        myCardTable.pnlHumanHand.setBorder(playerBorder);

        // Panel for buttons/control/display info (no border)
        JPanel controlPanel = new JPanel();

        // Add JPanels to main program window, set padding between panels
        myCardTable.pnlComputerHand.setPreferredSize(new Dimension(1366,125));
        myCardTable.add(Box.createRigidArea(new Dimension(0,10)));

        myCardTable.pnlPlayArea.setPreferredSize(new Dimension(1366, 250));
        myCardTable.add(Box.createRigidArea(new Dimension(0,10)));

        myCardTable.pnlHumanHand.setPreferredSize(new Dimension(1366, 125));
        myCardTable.add(Box.createRigidArea(new Dimension(0,10)));

        // Add JLabel at bottom for buttons/controls/info
        controlPanel.setPreferredSize(new Dimension(1366, 150));
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.LINE_AXIS));
        myCardTable.add(controlPanel);

        // Add buttons that do stuff
        JButton testButton1 = new JButton("Draw Exodia");
        JButton testButton2 = new JButton("Draw Blue Eyes White Dragon");
        JButton testButton3 = new JButton("Start Duel");
        JButton testButton4 = new JButton("Forfeit Duel");

        controlPanel.add(testButton1);
        controlPanel.add(Box.createRigidArea(new Dimension(30, 0)));
        controlPanel.add(testButton2);
        controlPanel.add(Box.createRigidArea(new Dimension(30, 0)));
        controlPanel.add(testButton3);
        controlPanel.add(Box.createRigidArea(new Dimension(30, 0)));
        controlPanel.add(testButton4);
        controlPanel.add(Box.createRigidArea(new Dimension(30, 0)));


        // Create JLabels to hold ImageIcons
        // Add JLabels to JPanels
        GUICard2.loadCardIcons();
        deck.shuffle();
        JLabel compCard;
        JLabel humanCard;

        for(int i = 0; i < NUM_CARDS_PER_HAND; i++)
        {
            compCard = new JLabel(GUICard2.getIconBack());
            computerLabels[i] = compCard;
            myCardTable.pnlComputerHand.add(computerLabels[i]);
            humanCard = new JLabel(GUICard2.getIcon(deck.dealCard()));
            //humanCard = new JLabel(guiCard.getIconBack());
            humanLabels[i] = humanCard;
            myCardTable.pnlHumanHand.add(humanLabels[i]);
        }

        // Add two random cards in the play region (computer/human) and text
        // labels
        JLabel compPlayCard;
        JLabel humanPlayCard;

        compPlayCard = new JLabel(GUICard2.getIcon(deck.dealCard()));
        playedCardLabels[0] = compPlayCard;
        myCardTable.pnlPlayArea.add(playedCardLabels[0]);
        humanPlayCard = new JLabel(GUICard2.getIcon(deck.dealCard()));
        playedCardLabels[1] = humanPlayCard;
        myCardTable.pnlPlayArea.add(playedCardLabels[1]);

        // Display label text for computer and player
        myCardTable.pnlPlayArea.add(compFieldLabel);
        myCardTable.pnlPlayArea.add(playerFieldLabel);

        // Display everything to screen
        myCardTable.pack();
        myCardTable.setVisible(true);

    }

    public CardGameFramework( int numPacks, int numJokersPerPack,
                              int numUnusedCardsPerPack,  Card[] unusedCardsPerPack,
                              int numPlayers, int numCardsPerHand)
    {
        int k;

        // filter bad values
        if (numPacks < 1 || numPacks > 6)
            numPacks = 1;
        if (numJokersPerPack < 0 || numJokersPerPack > 4)
            numJokersPerPack = 0;
        if (numUnusedCardsPerPack < 0 || numUnusedCardsPerPack > 50) //  > 1 card
            numUnusedCardsPerPack = 0;
        if (numPlayers < 1 || numPlayers > MAX_PLAYERS)
            numPlayers = 4;
        // one of many ways to assure at least one full deal to all players
        if  (numCardsPerHand < 1 ||
                numCardsPerHand >  numPacks * (52 - numUnusedCardsPerPack)
                        / numPlayers )
            numCardsPerHand = numPacks * (52 - numUnusedCardsPerPack) / numPlayers;

        // allocate
        this.unusedCardsPerPack = new Card[numUnusedCardsPerPack];
        this.hand = new Hand[numPlayers];
        for (k = 0; k < numPlayers; k++)
            this.hand[k] = new Hand();
        deck = new Deck(numPacks);

        // assign to members
        this.numPacks = numPacks;
        this.numJokersPerPack = numJokersPerPack;
        this.numUnusedCardsPerPack = numUnusedCardsPerPack;
        this.numPlayers = numPlayers;
        this.numCardsPerHand = numCardsPerHand;
        for (k = 0; k < numUnusedCardsPerPack; k++)
            this.unusedCardsPerPack[k] = unusedCardsPerPack[k];

        // prepare deck and shuffle
        newGame();
    }

    // constructor overload/default for game like bridge
    public CardGameFramework()
    {
        this(1, 0, 0, null, 4, 13);
    }

    public Hand getHand(int k)
    {
        // hands start from 0 like arrays

        // on error return automatic empty hand
        if (k < 0 || k >= numPlayers)
            return new Hand();

        return hand[k];
    }

    public Card getCardFromDeck() { return deck.dealCard(); }

    public int getNumCardsRemainingInDeck() { return deck.getNumCards(); }

    public void newGame()
    {
        int k, j;

        // clear the hands
        for (k = 0; k < numPlayers; k++)
            hand[k].resetHand();

        // restock the deck
        deck.init(numPacks);

        // remove unused cards
        for (k = 0; k < numUnusedCardsPerPack; k++)
            deck.removeCard( unusedCardsPerPack[k] );

        // add jokers
        for (k = 0; k < numPacks; k++)
            for ( j = 0; j < numJokersPerPack; j++)
                deck.addCard( new Card('X', Card.Suit.values()[j]) );

        // shuffle the cards
        deck.shuffle();
    }

    public boolean deal()
    {
        // returns false if not enough cards, but deals what it can
        int k, j;
        boolean enoughCards;

        // clear all hands
        for (j = 0; j < numPlayers; j++)
            hand[j].resetHand();

        enoughCards = true;
        for (k = 0; k < numCardsPerHand && enoughCards ; k++)
        {
            for (j = 0; j < numPlayers; j++)
                if (deck.getNumCards() > 0)
                    hand[j].takeCard( deck.dealCard() );
                else
                {
                    enoughCards = false;
                    break;
                }
        }

        return enoughCards;
    }

    void sortHands()
    {
        int k;

        for (k = 0; k < numPlayers; k++)
            hand[k].sort();
    }

    Card playCard(int playerIndex, int cardIndex)
    {
        // returns bad card if either argument is bad
        if (playerIndex < 0 ||  playerIndex > numPlayers - 1 ||
                cardIndex < 0 || cardIndex > numCardsPerHand - 1)
        {
            //Creates a card that does not work
            return new Card('M', Card.Suit.spades);
        }

        // return the card played
        return hand[playerIndex].playCard(cardIndex);

    }


    boolean takeCard(int playerIndex)
    {
        // returns false if either argument is bad
        if (playerIndex < 0 || playerIndex > numPlayers - 1)
            return false;

        // Are there enough Cards?
        if (deck.getNumCards() <= 0)
            return false;

        return hand[playerIndex].takeCard(deck.dealCard());
    }

}