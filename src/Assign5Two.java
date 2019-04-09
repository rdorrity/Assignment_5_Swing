import javax.swing.*;
import java.util.*;
import java.awt.*;

public class Assign5Two {
    static Card generateRandomCard() {
        Deck deck = new Deck();
        Random randomGen = new Random();
        return deck.inspectCard(randomGen.nextInt(deck.getNumCards()));
    }

    public static void main(String[] args) {
        Deck deck = new Deck();
        deck.shuffle();

        for (int i = 0; i < deck.getNumCards(); i++) {
            System.out.println(deck.inspectCard(i));
        }
        Card rando = generateRandomCard();
        Card twoRando = generateRandomCard();
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
            for ( ; j >= 0 && valueRanks.get(cards[j].value) > valueRanks.get(current.value); j--) {
                cards[j + 1] = cards[j];
            }
            cards[j + 1] = current;
        }
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
        return Card.Suit.values()[suitAsInt].toString();
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