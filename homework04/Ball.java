/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  File name     :  Ball.java
 *  Purpose       :  Provides a class defining methods for the SoccerSim class
 *  @author       :  Mallory Benna
 *  Date written  :  2019-02-26
 *  Description   :  This class provides a bunch of methods which may be useful for the SoccerSim class
 *
 *
 *  Notes         :  None
 *  Warnings      :  None
 *  Exceptions    :  IllegalArgumentException when the input arguments are "hinky"
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  Revision Histor
 *  ---------------
 *            Rev      Date     Modified by:  Reason for change/modification
 *           -----  ----------  ------------  -----------------------------------------------------------
 *  @version 1.0.0  2019-02-26  Mallory Benna  Initial writing and release
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */

import java.text.DecimalFormat;



public class Ball {
  /**
   *  Class field definintions go here
   */
   static double x = 0.0;
   static double y = 0.0;
   static double dx = 0.0;
   static double dy = 0.0;




  /**
   *  Constructor goes here
   */
   public Ball(double x, double y, double dx, double dy) {
       this.x = x;
       this. y = y;
       this.dx = dx;
       this.dy = dy;
   }

   /**
    * Move THIS ball
    */
    public void move() {
        x = x + dx;
        y = y + dy;
    }

    /**
     * Method to update the velocity of the ball
     *
     */
     public void updateVelocity() {
        dx = dx * 0.99;
        dy = dy * 0.99;
     }


    /**
     * Detects if the ball is still moving
     * @return  boolean
     */
     public boolean isInMotion() {
         //If dx and dy is less than or equal to 1 in/sec then stop
         if(dx <= 1/12 || dy <= 1/12){
             return false;
         }
         return true;
     }

    /**
     * Gets the current location of the ball (x, y) values
     * @return  double value of the ball location
     */
     public double [] getLoc() {
         double [] location = new double[2];
         location[0] = x;
         location[1] = y;

         return location;
     }

    /**
     * Gets the current speed of the ball (x, y) values
     * @return  double value of the speed
     */
     public double [] getSpeed() {
         double [] speed = new double[2];
         speed[0] = dx;
         speed[1] = dy;

         return speed;
     }

    /**
     * Checks to see if the ball is within the bounds of the field
     * @return  boolean
     */
     public boolean isInBounds() {
        if((Math.abs(x) > 500) || (Math.abs(y) > 500)){
            return false;
        }
        return true;
     }


    /**
     *  Method to return check for collisions
     *  @return true or false
     */
    public boolean collision (Ball b2){
         //Check if ball hit another ball
         if(Math.sqrt(((x*12) - (b2.x*12)) + ((y*12) - (b2.y*12))) <= 8.9) {
            return true;
         }
         return false;
    }

   /**
    *  Method to return a String representation of this Ball
    *  @return
    */
    public String toString() {
     String velocityPattern = "00.00";
     DecimalFormat velocityFormat = new DecimalFormat(velocityPattern);
     String dxString = velocityFormat.format(dx);
     String dyString = velocityFormat.format(dy);
     String xString = velocityFormat.format(x);
     String yString = velocityFormat.format(y);

     return "<" + xString + "," + yString + "," + dxString + "," + dyString + ">";
    }



  /**
   *  The main program starts here
   *  be sure to make LOTS of tests!!
   *  remember you are trying to BREAK your code, not just prove it works!
   */
   public static void main( String args[] ) {

      System.out.println( "\nBALL CLASS TESTER PROGRAM\n" +
                          "--------------------------\n" );
      System.out.println( "  Creating a new ball: " );
      Ball ball = new Ball(10,10,1,1);
      System.out.println( "    New ball created: " + ball.toString() );

      System.out.println( "    Calling move three times ");
      ball.move();
      System.out.println( "    New ball created: " + ball.toString() );

      ball.move();
      System.out.println( "    New ball created: " + ball.toString() );

      ball.move();
      System.out.println( "    New ball created: " + ball.toString() );




      System.out.println( "    Calling update velocity three times ");
      ball.updateVelocity();
      System.out.println( "    New ball created: " + ball.toString() );

      ball.updateVelocity();
      System.out.println( "    New ball created: " + ball.toString() );

      ball.updateVelocity();
      System.out.println( "    New ball created: " + ball.toString() );



      //Testing move and update velocity
      System.out.println( "    Calling move and update velocity  ");
      ball.move();
      ball.updateVelocity();
      System.out.println( "    New ball created: " + ball.toString() );


      //Testing isInMotion with ball that is moving
      ball = new Ball(10,10,1,1);
      System.out.println("Calling isInMotion, expect true: " + ball.isInMotion());
      ball = new Ball (100,100,0,0);
      System.out.println("Calling isInMotion, ball has zero dx and dy, expect false: " + ball.isInMotion());


      //Testing isInBounds with in bounds ball

      ball = new Ball (100,100,5,5);
      System.out.println("Calling isInBounds, expect true: " + ball.isInMotion());
      ball = new Ball (600,100,0,0);
      System.out.println("Calling isInBounds, expect false: " + ball.isInMotion());
      ball = new Ball (700,-900,0,0);
      System.out.println("Calling isInBounds, expect false: " + ball.isInMotion());

      //Testing getSpeed

      ball = new Ball (5,5,1,-1);
      double [] array = ball.getSpeed();
      System.out.println("Calling getSpeed, expect 1: " + array[0]);
      System.out.println("Calling getSpeed, expect -1: " + array[1]);



      //Testing getLoc

      ball = new Ball (5,6,1,-1);
      array = ball.getLoc();
      System.out.println( "Calling getLoc, expect 5: " + array[0]);
      System.out.println( "Calling getLoc, expect 6: " + array[1]);


   }
}
