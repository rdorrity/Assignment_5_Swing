import javax.swing.*;
import java.awt.*;

public class GUICard
{
   // Static instance variables
   private static final int numSuits = 4;
   private static final int numValues = 14;
   private static final int CARD_IMAGES = 56;

   // Arrays for resulting filenames, card values and suits
   private static String[] filenames = new String[CARD_IMAGES];
   private static String[] cardValues = {"A", "2", "3", "4", "5", "6", "7",
           "8", "9", "T", "J", "Q", "K", "X"};    // "X" cards are jokers
   private static String[] cardSuits = {"H", "S", "D", "C"};

   // Array for final Icon images, along with ImageIcon for card back
   private static Icon[][] iconCards = new ImageIcon[numValues][numSuits];
   private static Icon iconBack;
   private static boolean iconsLoaded = false;

   /**
    * Loads cards as ImageIcons and stores them into Icon array.
    * Run-once method: if Icon array is already instantiated, does nothing.
    */
   private static void loadCardIcons()
   {
      int fileIndex = 0;
      int fnCardIndex = 0;

      if (!iconsLoaded)
      {
         // Create filenames and load into filenames array
         while (fileIndex < CARD_IMAGES)
         {
            for (int i = 0; i < numSuits; i++)
               for (int j = 0; j < numValues; j++)
                  {
                     filenames[fileIndex++] = cardValues[j] + cardSuits[i] +
                             ".gif";
                  }
            // For each filename, create ImageIcon and insert into 2D iconCards
            // array
            for (int suitIndex = 0; suitIndex < cardSuits.length; suitIndex++)
               for (int valueIndex = 0; valueIndex < cardValues.length; valueIndex++)
               {
                  ImageIcon cardIcon = new ImageIcon("images/" + filenames[fnCardIndex++]);
                  iconCards[valueIndex][suitIndex] = cardIcon;
               }
         }

         // Instantiate iconBack ImageIcon (card back)
         iconBack = new ImageIcon("images/" + "BK.gif");
         iconsLoaded = true;
      }
   }

   /**
    * Getter for Icon from Card object.
    * @param card
    * @return Icon object from Card
    */
   public static Icon getIcon(Card card)
   {
      return iconCards[valueToInt(card)][suitToInt(card)];
   }

   /**
    * Gets card back Icon image.
    * @return Card back
    */
   public static Icon getBackCardIcon()
   {
      return iconBack;
   }

   /**
    * Converts a Card object's value to an int.
    * @param card
    * @return Card value as int
    */
   private static int valueToInt(Card card)
   {
      // Use Card class's method to get the value of Card
      int value = card.getValue();

      // If the Card object's value matches the value in cardValues, return
      // the index value, else return nothing
      for (int i = 0; i < cardValues.length; i++)
      {
         if (Integer.parseInt(cardValues[i]) == value)
         {
            return i;
         }
      }
      return -1;
   }

   /**
    * Converts a Card object's suit to an int.
    * @param card
    * @return Card suit as int
    */
   private static int suitToInt(Card card)
   {
      // Get suit from Card object using class method
      Card.Suit suit = card.getSuit();
      int result = -1;

      if (suit == Card.Suit.hearts)
      {
         result = 0;
      }
      else if (suit == Card.Suit.spades)
      {
         result = 1;
      }
      else if (suit == Card.Suit.diamonds)
      {
         result = 2;
      }
      else if (suit == Card.Suit.clubs)
      {
         result = 3;
      }

      return result;
   }

}
