/*
 * This class defines a player of the game
 */

import java.util.*;

public class Player implements Comparable<Player> {
   private String name;
   private int streak;
   private int wins;
   private int match;
   private int credits;
   private int creditChange;
   
   /*
    * post: constructs a player with given name
    * @param name name of the player
    */
   public Player(String name) {
      this.name = name;
      this.credits = Constant.CREDITS;
      match = 0;
      streak = 0;
      wins = 0;
   }
   
   /*
    * post: updates the status of player
    * @param win score earned by the player
    */
   public void update(int win) {
      if (win > 0) {
         wins++;
         streak++;
      } else {
         streak = 0;
      }
      credits += win;
      creditChange = win;
      match++;
   }
   
   /*
    * post: prints the gameover message to the console
    */
   private void gameOver() {
      stats();
      System.out.println("Now, he is broke(credits < $0)");
   }
   
   /*
    * post: returns the win rate of player
    */
   public double winRate() {
      return Math.round((double)(wins) / match * 10000) / 100.0;
   }
   
   /*
    * post: returns an integer showing the order of player compared to other player
    * @param other other player to be compared
    */
   public int compareTo(Player other) {
      return other.credits - credits;
   }
   
   /*
    * post: prints the status of player
    */
   public void print() {
      String sign = creditChange < 0 ? "" : "+";
      System.out.println(name + "::- credits: " + credits + "(" + sign + creditChange + ")");
      if (streak > 1) {
         System.out.println(name + " is on winning streak of " + streak + "!");
      }
      if (credits < 0) {
         gameOver();
      }
   }
   
   /*
    * post: prints the statistics of player
    */
   public void stats() {
      System.out.println(name + " have played " + match + " rounds with " + winRate() + "% of win rate.");
      System.out.println("Final credits: " + credits);
   }
}