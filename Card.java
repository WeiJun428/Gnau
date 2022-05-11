/*
 * This class defines a basic poker card.
 */

public class Card implements Comparable<Card> {
   private int number;
   private int shape;
   
   /**
    * pre : 0 < number <= NUMBER_SIZE && 0 < shape <= SHAPE_SIZE (throws illegalArgumentException otherwise) 
    * post: constructs a card with given number and shape
    * @param number value stored in card
    * @param shape shape stored in card
    */
   public Card(int number, int shape) {
      if (number > Constant.NUMBER_SIZE || number <= 0 || shape > Constant.SHAPE_SIZE || shape <= 0) {
         throw new IllegalArgumentException();
      }
      this.number = number;
      this.shape = shape;
   }
   
   /*
    * post: returns the number of card
    */
   public int getNumber() {
      return number;
   }

   /*
    * post: returns the shape of card
    */   
   public int getShape() {
      return shape;
   }
   
   /*
    * post: returns the string representation of card
    */
   public String toString() {
      return "|" + Constant.NUMBER[number] + Constant.SHAPE[shape] + "|";
   }
   
   /*
    * post: returns an integer showing order of card compared to other card
    * @param other other card to be compared
    */
   public int compareTo(Card other) {
      if (number != other.number) {
         return number - other.number;
      } else {
         return shape - other.shape;
      }
   }
}