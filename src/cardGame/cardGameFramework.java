package cardGame;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.*;


//class cardGame.CardGameFramework  ----------------------------------------------------
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

        highCardGame.deal();

        /*
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
        */

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
        JButton testButton1 = new JButton("Draw Card");
        JButton testButton2 = new JButton("Start Game");
        JButton testButton3 = new JButton("End Game");

        controlPanel.add(testButton1);
        controlPanel.add(Box.createRigidArea(new Dimension(30, 0)));
        controlPanel.add(testButton2);
        controlPanel.add(Box.createRigidArea(new Dimension(30, 0)));
        controlPanel.add(testButton3);
        controlPanel.add(Box.createRigidArea(new Dimension(30, 0)));

        // Create JLabels to hold ImageIcons
        // Add JLabels to JPanels
        GUICard2.loadCardIcons();
        //deck.shuffle();
        JLabel compCard;
        JLabel humanCard;

        for(int i = 0; i < NUM_CARDS_PER_HAND; i++)
        {
            compCard = new JLabel(GUICard2.getIconBack());
            Assign5Two.computerLabels[i] = compCard;
            myCardTable.pnlComputerHand.add(Assign5Two.computerLabels[i]);
            humanCard = new JLabel(GUICard2.getIcon(highCardGame.hand[1].inspectCard(i)));
            //humanCard = new JLabel(guiCard.getIconBack());
            Assign5Two.humanLabels[i] = humanCard;
            myCardTable.pnlHumanHand.add(Assign5Two.humanLabels[i]);
        }

        // Add two random cards in the play region (computer/human) and text
        // labels
        JLabel compPlayCard;
        JLabel humanPlayCard;

        //compPlayCard = new JLabel(GUICard2.getIcon(deck.dealCard()));
        compPlayCard = new JLabel(GUICard2.getIconBack());
        Assign5Two.playedCardLabels[0] = compPlayCard;
        myCardTable.pnlPlayArea.add(Assign5Two.playedCardLabels[0]);
        //humanPlayCard = new JLabel(GUICard2.getIcon(deck.dealCard()));
        humanPlayCard = new JLabel(GUICard2.getIconBack());
        Assign5Two.playedCardLabels[1] = humanPlayCard;
        myCardTable.pnlPlayArea.add(Assign5Two.playedCardLabels[1]);

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

class Assign5Two {

    //////////////////////////////////////////////////////////////////////////
    // Members for Assign5Two class
    static int NUM_CARDS_PER_HAND = 7;
    static int  NUM_PLAYERS = 2;
    static JLabel[] computerLabels = new JLabel[NUM_CARDS_PER_HAND];
    static JLabel[] humanLabels = new JLabel[NUM_CARDS_PER_HAND];
    static JLabel[] playedCardLabels  = new JLabel[NUM_PLAYERS];
    static JLabel[] playLabelText  = new JLabel[NUM_PLAYERS];
    //////////////////////////////////////////////////////////////////////////

    static Card generateRandomCard() {
        Deck deck = new Deck();
        Random randomGen = new Random();
        return deck.inspectCard(randomGen.nextInt(deck.getNumCards()));
    }


}

class Card {

    public enum Suit {spades, hearts, diamonds, clubs}
    public static Map<Character, Integer> valueRanks = new HashMap<>();
    private Suit suit;
    private char value;
    private boolean errorFlag;

    public Card() {
        setValueRanks();
        set('A', Suit.spades);
    }

    public Card(char value, Suit suit) {
        setValueRanks();
        set(value, suit);
    }

    public Card(Card card) {
        setValueRanks();
        set(card.getValue(), card.getSuit());
    }

    public String toString() {
        if (errorFlag) {
            return "[ invalid ]";
        }

        return value + " of " + suit;
    }

    public boolean set(char value, Suit suit) {
        if (!isValid(value, suit)) {
            this.errorFlag = true;
            return false;
        }
        this.value = value;
        this.suit = suit;
        this.errorFlag = false;
        return true;
    }

    public Suit getSuit() {
        return suit;
    }

    public char getValue() {
        return value;
    }

    public boolean getErrorFlag() {
        return errorFlag;
    }

    public boolean equals(Card card) {
        return this.value == card.value && this.suit == card.suit && this.errorFlag == card.errorFlag;
    }

    private boolean isValid(char value, Suit suit) {
        for (char i : valueRanks.keySet()) {
            if (value == i)
                return true;
        }
        return false;
    }

    // Insertion Sort
    static void arraySort(Card[] cards, int high) {
        for (int i = 1; i < high; i++) {
            Card current = cards[i];
            int j = i - 1;
            for ( ; j >= 0 && firstCardGreater(cards[j], current); j--) {
                cards[j + 1] = cards[j];
            }
            cards[j + 1] = current;
        }
    }

    private static boolean firstCardGreater(Card first, Card second) {
        return cardTotalValue(first) > cardTotalValue(second);
    }

    private static int cardTotalValue(Card card) {
        int totalValue = valueRanks.get(card.value) * 10;
        totalValue += Suit.valueOf(card.getSuit().toString()).ordinal();
        return totalValue;
    }

    public static void setValueRanks() {
        if (valueRanks.size() > 0)
            return;
        char[] values = {'2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A', 'X'};
        int rank = 0;
        for (char i : values) {
            valueRanks.put(i, rank);
            rank++;
        }
    }

}

class Hand {

    public static int MAX_CARDS = 50;
    private Card[] myCards;
    private int numCards;

    public Hand() {
        myCards = new Card[MAX_CARDS];
        numCards = 0;
    }

    public void resetHand() {
        myCards = new Card[MAX_CARDS];
        numCards = 0;
    }

    public boolean takeCard(Card card) {
        myCards[numCards] = new Card(card.getValue(), card.getSuit());
        if (myCards[numCards].equals(card)) {
            numCards++;
            return true;
        }
        return false;
    }

    public Card playCard() {
        int topPosition = numCards - 1;
        Card toPlay = new Card(myCards[topPosition].getValue(), myCards[topPosition].getSuit());
        myCards[topPosition] = null;
        numCards--;
        return toPlay;
    }

    public Card playCard(int index) {
        Card toPlay = new Card(myCards[index].getValue(), myCards[index].getSuit());
        myCards[index] = null;
        numCards--;
        return toPlay;
    }

    public String toString() {
        StringBuilder fullHand = new StringBuilder();
        fullHand.append("Hand = (");
        for (int i = 0; i < numCards; i++) {
            fullHand.append(myCards[i].toString());
            if (i + 1 != numCards)
                fullHand.append(", ");
        }
        fullHand.append(")");
        return fullHand.toString();
    }

    public int getNumCards() {
        return this.numCards;
    }

    public Card inspectCard(int k) {
        if (k >= numCards) {
            return new Card('1', Card.Suit.hearts);
        }

        return myCards[k];
    }

    void sort() {
        Card.arraySort(myCards, numCards);
    }

}

class Deck {

    public static final int MAX_CARDS = 6 * 56;
    private static Card[] masterPack;
    private Card[] cards;
    private int topCard;

    public Deck() {
        allocateMasterPack();
        init(1);
    }

    public Deck(int numPacks) {
        allocateMasterPack();
        init(numPacks);
    }

    public void init(int numPacks) {
        int totalCards = numPacks * 56;
        if (totalCards > MAX_CARDS) {
            return;
        }

        cards = new Card[totalCards];
        int position = 0;
        for (int i = 0; i < numPacks; i++) {
            for (Card card : masterPack) {
                cards[position++] = new Card(card);
            }
        }
        this.topCard = position;
    }

    public void shuffle() {
        for (int k = 0; k < 4; k++) {
            Random randomGen = new Random();
            for (int i = 0; i < topCard; i++) {
                int randPosition = randomGen.nextInt(topCard);
                if (i != randPosition) {
                    Card saveCard = new Card(cards[i]);
                    cards[i] = new Card(cards[randPosition]);
                    cards[randPosition] = new Card(saveCard);
                }
            }
        }
    }

    public Card dealCard() {
        if (cards[topCard - 1] == null) {
            return new Card('1', Card.Suit.hearts);
        }

        Card toPlay = new Card(cards[topCard - 1].getValue(), cards[topCard - 1].getSuit());
        cards[topCard - 1] = null;
        topCard--;
        return toPlay;
    }

    public int getTopCard() {
        return this.topCard;
    }

    public Card inspectCard(int k) {
        if (k > topCard) {
            return new Card('1', Card.Suit.hearts);
        }

        return cards[k];
    }

    public boolean addCard(Card card) {
        if (cards.length == 56)
            return false;
        for (Card current : cards) {
            if (card.equals(current))
                return false;
        }
        cards[topCard] = card;
        topCard += 1;
        return true;
    }

    public boolean removeCard(Card card) {
        for (int i = 0; i < cards.length; i++) {
            if (cards[i].equals(card)) {
                cards[i] = cards[topCard - 1];
                topCard--;
                return true;
            }
        }
        return false;
    }

    public void sort() {
        Card.arraySort(cards, getNumCards());
    }

    public int getNumCards() {
        return topCard - 1;
    }

    private static void allocateMasterPack() {
        if (masterPack != null)
            return;
        if (Card.valueRanks.size() < 1)
            Card.setValueRanks();
        masterPack = new Card[56];
        int position = 0;
        for (Card.Suit suit : Card.Suit.values()) {
            for (char value : Card.valueRanks.keySet()) {
                masterPack[position++] = new Card(value, suit);
            }
        }
    }

}
class CardTable extends JFrame {
    static int MAX_CARDS_PER_HAND = 56;
    static int MAX_PLAYERS = 2;

    private int numCardsPerHand;
    private int numPlayers;

    public JPanel pnlComputerHand, pnlHumanHand, pnlPlayArea;

    public CardTable(String title, int numCardsPerHand, int numPlayers) {
        super(title);
        setLayout(new BorderLayout());

        pnlComputerHand = new JPanel();
        pnlComputerHand.setLayout(new GridLayout(1, numCardsPerHand));
        add(pnlComputerHand, BorderLayout.NORTH);

        pnlPlayArea = new JPanel();
        pnlPlayArea.setLayout(new GridLayout(numPlayers,numPlayers));
        add(pnlPlayArea, BorderLayout.CENTER);

        pnlHumanHand = new JPanel();
        pnlHumanHand.setLayout(new GridLayout(1, numCardsPerHand));
        add(pnlHumanHand, BorderLayout.SOUTH);

        this.numCardsPerHand = numCardsPerHand;
        this.numPlayers = numPlayers;
    }

    public int getNumCardsPerHand() {
        return numCardsPerHand;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

}

class GUICard2 {

    private static Icon[][] iconCards = new ImageIcon[14][4];
    private static Icon iconBack;
    static boolean iconsLoaded = false;

    static void loadCardIcons() {
        if (iconsLoaded)
            return;
        for (int i = 0; i < iconCards.length; i++) {
            for (int j = 0; j < iconCards[i].length; j++) {
                String filename = intToCardValue(i) + intToCardSuit(j) + ".gif";
                ImageIcon cardIcon = new ImageIcon("images/" + filename);
                iconCards[i][j] = cardIcon;
            }
        }
        iconBack = new ImageIcon("images/BK.gif");
        iconsLoaded = true;
    }

    static String intToCardValue(int valAsInt) {
        String cardValue = "";
        for (Character i : Card.valueRanks.keySet()) {
            if (Card.valueRanks.get(i) == valAsInt)
                cardValue = i.toString();
        }
        return cardValue;
    }

    static String intToCardSuit(int suitAsInt) {
        if (suitAsInt < 0 || suitAsInt > 3)
            return "invalid";
        return Card.Suit.values()[suitAsInt].toString().toUpperCase().substring(0, 1);
    }

    private static int valueToInt(Card card) {
        char cardVal = card.getValue();
        if (Card.valueRanks.size() < 1 || !Card.valueRanks.containsKey(cardVal))
            return -1;
        return Card.valueRanks.get(cardVal);
    }

    // Don't need a guard block since Suit is an enum
    private static int suitToInt(Card card) {
        Card.Suit cardSuit = card.getSuit();
        if (cardSuit == Card.Suit.spades)
            return 0;
        else if (cardSuit == Card.Suit.hearts)
            return 1;
        else if (cardSuit == Card.Suit.diamonds)
            return 2;
        else // It's clubs
            return 3;
    }

    public static Icon getIcon(Card card) {
        return iconCards[valueToInt(card)][suitToInt(card)];
    }

    public static Icon getIconBack() {
        return iconBack;
    }
}
