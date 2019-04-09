/**
 * Module 5 - Video Cards Killed the Casino Star (working title)
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
   private static String[] filenames = new String[NUM_CARD_IMAGES];
   private static String[] cardValues = {"A", "2", "3", "4", "5", "6", "7",
        "8", "9", "T", "J", "Q", "K", "X"};    // "X" cards are jokers
   private static String[] cardSuits = {"H", "S", "D", "C"};


   // Builds Card GIF file names
   private static void loadCardIcons()
   {
      int fileIndex = 0;
      // NOTE: This variable for debugging only
      int numCards = 0;

      while (fileIndex < NUM_CARD_IMAGES) {

         if (fileIndex == 56)    // At last index, insert card back file name
         {
            filenames[56] = "BK" + ".gif";
            numCards++;
            break;
         }

         for (int i = 0; i < cardSuits.length; i++)
            for (int j = 0; j < cardValues.length; j++)
            {
               filenames[fileIndex++] = cardValues[j] + cardSuits[i] + ".gif";
               numCards++;
            }
      }

      // DEBUG ONLY
      for (String filename: filenames)
      {
         System.out.println(filename);
      }
      System.out.println("Number of cards: " + numCards);

      if (numCards != 57)
      {
         System.out.println("ERROR: Incorrect number of cards.");
      }
      // END DEBUG

      // Use filenames to create Icons and insert into Icon array
      // NOTE: Make sure images folder is in correct working directory.

      for (int fnIndex = 0; fnIndex < filenames.length; fnIndex++)
      {
         ImageIcon cardIcon = new ImageIcon("images/" + filenames[fnIndex]);
         icons[fnIndex] = cardIcon;
      }

   }

   public static void main(String[] args)
   {
      int k;

      loadCardIcons();

      // Create main program window
      JFrame testWindow = new JFrame("Card Display");
      testWindow.setSize(1366, 768);
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
