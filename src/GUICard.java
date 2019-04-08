import javax.swing.*;
import java.awt.*;

public class GUICard
{
   // Instance variables
   private static final int cardSuits = 4;
   private static final int cardValues = 14;

   private static Icon[][] iconCards = new ImageIcon[cardValues][cardSuits];
   private static Icon iconBack;
   private static boolean iconsLoaded = false;

   /**
    * Loads cards as ImageIcons and stores them into Icon array.
    * Run-once method: if Icon array is already instantiated, does nothing.
    */
   private static void loadCardIcons()
   {
      if (!iconsLoaded)
      {
         for (int i = 0; i < cardSuits; i++)
            for (int j = 0; j < cardValues; j++)
            {
               // Store Icon in appropriate index
            }
      }
   }

   /**
    * Getter for Icon from Card object.
    * @param card
    * @return Icon object from Card
    */
   public static Icon getIcon(Card card)
   {
      //return iconCards[valueToInt(card)][suitToInt(card)]
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

   }

   /**
    * Converts a Card object's suit to an int.
    * @param card
    * @return Card suit as int
    */
   private static int suitToInt(Card card)
   {

   }

}
