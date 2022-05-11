/*
 * This class defines the game environment and procedures of 'Ngao'.
 */

import java.util.*;

public class Game {
   private List<Player> player;
   private List<Card> publicDeck;
   private int round;
   
   /*
    * post: constructs a game object
    * @param s Scanner to be used to read user's input
    */
   public Game(Scanner s) {
      int numberOfPlayer = introduction(s);
      if (numberOfPlayer > 10 || numberOfPlayer < 2) {
         throw new IllegalStateException("The number of players should be greater than 1 and less than 10");
      }
      player = getName(numberOfPlayer, s);
      round = 0;
      boolean flag = true;
      while (flag) {
         play();
         flag = wantToPlay(s);
      }
      reportStatistics();
   }
   
   /*
    * post: returns the user's decision of whether continue playing
    * @param s Scanner to be used to read user's input
    */
   public boolean wantToPlay(Scanner s) {
      System.out.print("Do you want to play again?(y/n) ");
      String temp = s.nextLine();
      return temp.startsWith("y") || temp.startsWith("Y");
   }
   
   /*
    * post: introduce the game to the users and returns the prompted numbers of players
    * @param s Scanner to be used to read user's input
    */
   public int introduction(Scanner s) {
      System.out.println("Welcome to the Gamble Center by WJ.");
      System.out.print("How many players? (including host) ");
      return Integer.parseInt(s.nextLine());
   }
   
   /*
    * post: returns a list of players with name given by the users
    * @param numberOfPlayer number of player's name to be prompted
    * @param s Scanner to be used to read user's input
    */
   private List<Player> getName(int numberOfPlayer, Scanner s) {
      List<Player> player = new ArrayList<>();
      for (int i = 1; i <= numberOfPlayer; i++) {
         System.out.print("What is the name of player #" + i + "? ");
         if (i == 1) {
            System.out.print("(host) ");
         }
         player.add(new Player(s.nextLine() + ((i == 1)? " (host)" : "")));
      }
      System.out.println();
      return player;
   }
   
   /*
    * post: plays a game of 'Ngao' and updates the result of the game
    */
   public void play() {
      round++;
      System.out.println("Round " + round);
      publicDeck = getPublicDeck();
      List<Deck> decks = new ArrayList<>(); 
      for (int i = 0; i < player.size(); i++) {
         decks.add(getDeck(i));
      }
      updateResult(decks);
      for (int i = 0; i < player.size(); i++) {
         player.get(i).print();
         System.out.println(decks.get(i));
         System.out.println();
      }
   }
   
   /*
    * post: updates the status of players after a game
    * @param decks list of deck to be processed
    */
   private void updateResult(List<Deck> decks) {
      int hostCredit = 0;
      for (int i = 1; i < decks.size(); i++) {
         int temp = decks.get(i).compareTo(decks.get(0));
         player.get(i).update(temp);
         hostCredit -= temp;
      }
      player.get(0).update(hostCredit);
   }
   
   /*
    * post: returns a randomly shuffled public deck
    */
   private List<Card> getPublicDeck() {
      List<Card> deck = new ArrayList<>();
      for (int i = 1; i <= Constant.NUMBER_SIZE; i++) {
         for (int j = 1; j <= Constant.SHAPE_SIZE; j++) {
            deck.add(new Card(i, j));
         }
      }
      Collections.shuffle(deck);
      return deck;
   }   
   
   /*
    * post: returns a deck of given index of size Constant.CARDS
    * @param i index of the player
    */
   private Deck getDeck(int i) {
      List<Card> deck = new ArrayList<>();
      for (int j = 0; j < Constant.CARDS; j++) {
         deck.add(publicDeck.get(i + j * player.size()));
      }
      return new Deck(deck);
   }
   
   /*
    * post: reports the statistics of all players after a game stops
    */
   private void reportStatistics() {
      Queue<Player> q = new PriorityQueue<>();
      for (Player p : player) {
         q.add(p);
      }
      for (int i = 0; i < player.size(); i++) {
         System.out.print("Rank " + (i + 1) + ": ");
         q.remove().stats();
      }
   }
}