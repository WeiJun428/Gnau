/*
 * This class defines a deck of card hold by a player
 */

import java.util.*;

public class Deck implements Comparable<Deck> {
   private List<Card> overallDeck;
   private int score;
   private int sum;
   
   /*
    * post: constructs a deck given the list of card
    * @param overallDeck deck to be initialized
    */
   public Deck(List<Card> overallDeck) {
      this.overallDeck = overallDeck;
      score = 0;
      sum = 0;
      count(overallDeck, 0);
   }
   
   /*
    * post: returns the score of deck
    */
   public int getScore() {
      return score;
   }
   
   /*
    * post: returns the sum of deck
    */
   public int getSum() {
      return sum;
   }
   
   /*
    * post: processes the deck recursively to find the maximum score and sum of deck
    * @param deck deck to be processed
    * @param sum sum to be checked
    */
   private void count(List<Card> deck, int sum) {
      if (deck.size() == 2 && sum % Constant.MOD == 0) {
         update(deck);
      } else if (deck.size() > 2) {
         for (int i = 0; i < deck.size(); i++) {
            Card temp = deck.get(i);
            deck.remove(i);
            count(deck, sum + value(temp.getNumber()));
            if (value(temp.getNumber()) == 3) count(deck, sum + 6);
            if (value(temp.getNumber()) == 6) count(deck, sum + 3);
            deck.add(i, temp);
         }
      }
   }
   
   /*
    * post: updates the score and sum of the deck
    * @param deck deck to be processed
    */
   private void update(List<Card> deck) {
      int tempSum = countSum(deck);
      int tempScore = countScore(deck, tempSum);
      if (tempScore > score || tempScore == score && tempSum > sum) {
         score = tempScore;
         sum = tempSum;
      }
   }
   
   /*
    * post: returns the score of the specific deck
    * @param deck deck to be checked
    * @param tempSum sum of the deck
    */
   private int countScore(List<Card> deck, int tempSum) {
      if (five(deck)) {
         return 5;
      } else if (four(deck)) {
         return 4;
      } else if (three(deck)) {
         return 3;
      } else if (tempSum == 10) {
         return 2;
      } else {
         return 1;
      }
   }
   
   /*
    * post: computes and returns the sum of deck
    * @param deck deck to be  processed
    */
   private int countSum(List<Card> deck) {
      if (three(deck)) {
         if (hasNumber(deck, 1)) {
            return Constant.NUMBER_SIZE + 1;
         } else {
            return deck.get(0).getNumber();
         }
      }
      int temp = countValueSum(deck);
      if (hasNumber(deck, 3)) {
         temp = Math.max(temp, (temp + 3) % Constant.MOD);
      }
      if (hasNumber(deck, 6)) {
         temp = Math.max(temp, (temp + 7) % Constant.MOD);
      }
      if (temp == 0) {
         temp = Constant.MOD;
      }
      return temp;
   }
   
   /*
    * post: counts and returns the value sum of deck
    * @param deck deck to be processed
    */
   private int countValueSum(List<Card> deck) {
      int ans = 0;
      for (Card c : deck) {
         ans += value(c.getNumber());
      }
      return ans % Constant.MOD;
   }
   
   /*
    * post: returns a boolean showing whether the deck worth three points
    * @param deck deck to be checked
    */
   private boolean three(List<Card> deck) {
      return deck.get(0).getNumber() == deck.get(1).getNumber();
   }

   /*
    * post: returns a boolean showing whether the deck worth four points
    * @param deck deck to be checked
    */   
   private boolean four(List<Card> deck) {
      return three(deck) && hasAce(deck);
   }

   /*
    * post: returns a boolean showing whether the deck worth five points
    * @param deck deck to be checked
    */   
   private boolean five(List<Card> deck) {
      return hasAce(deck) && hasJQK(deck);
   }
   
   /**
    * post: returns a boolean showing whether the deck contains the number n given
    * @param deck deck to be checked
    * @param n integer to be checked
    */
   private boolean hasNumber(List<Card> deck, int n) {
      for (Card c : deck) {
         if (c.getNumber() == n) {
            return true;
         }
      }
      return false;
   }
   
   /*
    * post: returns a boolean showing whether the deck contains J, Q or K
    * @param deck deck to be checked
    */
   private boolean hasJQK(List<Card> deck) {
      for (Card c : deck) {
         if (c.getNumber() > Constant.MOD) {
            return true;
         }
      }
      return false;
   }

   /*
    * post: returns a boolean showing whether the deck contains an Ace
    * @param deck deck to be checked
    */
   private boolean hasAce(List<Card> deck) {
      for (Card c : deck) {
         if (c.getNumber() == 1 && c.getShape() == Constant.SPADE) {
            return true;
         }
      }
      return false;
   }
   
   private boolean canEscape() {
      int[] temp = new int[Constant.NUMBER.length];
      for (Card c : overallDeck) {
         temp[c.getNumber()]++;
      }
      for (int i = 0; i < temp.length; i++) {
         if (temp[i] >= 3) {
            return true;
         }
      }
      return false;
   }
   
   /*
    * post: returns an integer showing the difference between the deck to the other
    * @param other deck to be compared
    */
   public int compareTo(Deck other) {
      if (score > other.score) {
         return score;
      } else if (score == other.score) {
         if (sum > other.sum) {
            return score;
         } else if (sum == other.sum || canEscape()) {
            return 0;
         } else {
            return -other.score;
         }
      } else if (canEscape()) {
         return 0;
      } else {
         return -other.score;
      }
   }
   
   /*
    * post: returns a string representation of the deck
    */
   public String toString() {
      String ans = "";
      for (Card i : overallDeck) {
         ans += i;
      }
      return ans;
   }
   
   /*
    * post: returns the true value of a card (e.g.: J, Q, K == 10)
    */
   private int value(int n) {
      return Math.min(n, Constant.MOD);
   }
}