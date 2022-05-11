/*
 * This is a game developed by Tan Wei Jun. All copyright(s) are reserved
 * This is the main class of the program.
 */

import java.util.*;
import java.io.*;

public class Main {
   public static void main(String[] args) {
      Scanner input = new Scanner(System.in);
      // Game game = new Game(input);
      List<Card> test = new ArrayList<>();
      test.add(new Card(4, 1));
      test.add(new Card(6, 1));
      test.add(new Card(8, 1));
      test.add(new Card(12, 1));
      test.add(new Card(4, 1));
      Deck deck = new Deck(test);
      System.out.println(deck);
      System.out.println(deck.getScore() + " " + deck.getSum());
   }
}