import javax.swing.*;
import java.awt.*;

public class GUICard extends Module5
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
      if (iconCards == null)
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

   }

   /**
    * Gets card back Icon image.
    * @return Card back
    */
   public static Icon getBackCardIcon()
   {
      return iconBack;
   }

}
