/**
 * Module 5, Phase 1 - Video Cards Killed the Casino Star (working title)
 * @author Sara Kazemi, Nathan Warren-Acord, Ryan Dorrity, Cody Young
 * SCSI Logic
 * CST 338
 */

import javax.swing.*;
import java.awt.*;

public class Assign5One {

   // Phase 1
   // Static variables for number of cards and Icon array, which holds the 57
   // card images

   private static final int NUM_CARD_IMAGES = 57;
   private static Icon[] icons = new ImageIcon[NUM_CARD_IMAGES];

   // Arrays for resulting filenames, card values and suits
   private static char[] cardValues = {'A', '2', '3', '4', '5', '6', '7',
        '8', '9', 'T', 'J', 'Q', 'K', 'X'};    // X cards are jokers
   private static char[] cardSuits = {'H', 'S', 'D', 'C'};


   // Builds Card GIF file names
   private static void loadCardIcons()
   {
      int iconIndex = 0;
      for(char cardSuit : cardSuits) {
         for (char cardValue : cardValues) {
            icons[iconIndex++] = new ImageIcon("images/" + cardValue + cardSuit + ".gif");
         }
      }
      icons[iconIndex] = new ImageIcon("images/BK.gif");
      // load the back of card image
   }

   public static void main(String[] args)
   {
      int k;

      loadCardIcons();

      // Create main program window
      JFrame testWindow = new JFrame("Card Display");
      testWindow.setSize(1150, 650);
      testWindow.setLocationRelativeTo(null);
      testWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      // Set program window layout
      FlowLayout layout = new FlowLayout(FlowLayout.CENTER, 5, 20);
      testWindow.setLayout(layout);

      // Set up image JLabel array
      JLabel[] labels = new JLabel[NUM_CARD_IMAGES];

      for (k = 0; k < NUM_CARD_IMAGES; k++)
      {
         labels[k] = new JLabel(icons[k]);
      }

      // Place controls into program window
      for (k = 0; k < NUM_CARD_IMAGES; k++)
      {
         testWindow.add(labels[k]);
      }

      // Set window as visible to user
      testWindow.setVisible(true);

   }

}
