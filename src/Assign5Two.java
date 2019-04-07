import java.util.*;

public class Assign5Two {
    static Card generateRandomCard() {
        Deck deck = new Deck();
        Random randomGen = new Random();
        return deck.inspectCard(randomGen.nextInt(deck.getNumCards()));
    }
}

class Card {

    public enum Suit {clubs, diamonds, hearts, spades}
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

    static void arraySort(Card[] cards) {
        arrayQuickSort(cards, 0, cards.length - 1);
    }

    private static void arrayQuickSort(Card[] cards, int low, int high) {
        if (low < high) {
            int pivot = arrayPartition(cards, low, high);
            arrayQuickSort(cards, low, pivot);
            arrayQuickSort(cards, pivot + 1, high);
        }
    }

    private static int arrayPartition(Card[] cards, int low, int high) {
        Card pivot = cards[(low + high) / 2];
        int i = low - 1;
        int j = high + 1;
        while (true) {
            i += 1;
            while (valueRanks.get(cards[i].value) < valueRanks.get(pivot.value)) {
                j -= 1;
                while (valueRanks.get(cards[j].value) > valueRanks.get(pivot.value)) {
                    if (i >= j)
                        return j;
                }
            }
            arraySwap(cards, i, j);
        }
    }

    private static void arraySwap(Card[] cards, int firstCard, int secondCard) {
        Card holder = cards[firstCard];
        cards[firstCard] = cards[secondCard];
        cards[secondCard] = holder;
    }

    private static void setValueRanks() {
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
        Card.arraySort(myCards);
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
        Card.arraySort(cards);
    }

    public int getNumCards() {
        return cards.length;
    }

    private static void allocateMasterPack() {
        if (masterPack != null) {
            return;
        }

        masterPack = new Card[56];
        int position = 0;
        for (Card.Suit suit : Card.Suit.values()) {
            for (char value : Card.valueRanks.keySet()) {
                masterPack[position++] = new Card(value, suit);
            }
        }
    }

}