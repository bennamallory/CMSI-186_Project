/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  File name     :  SoccerSim.java
 *  Purpose       :  The main program for the SoccerSim class
 *
 *  @author       :  Mallory Benna
 *  Date written  :  2019-02-26
 *  Description   :  This class provides a bunch of methods which may be useful for the SoccerSim class
 *                   for Homework 4, part 2.  Includes the following:
  *
 *  Notes         :  None
 *  Warnings      :  None
 *  Exceptions    :  IllegalArgumentException when the input arguments are "hinky"
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  Revision History
 *  ---------------
 *            Rev      Date     Modified by:  Reason for change/modification
 *           -----  ----------  ------------  -----------------------------------------------------------
 *  @version 1.0.0  2019-02-26  Mallory Benna  Initial writing and release
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */

import java.text.DecimalFormat;


public class SoccerSim {
  /**
   *  Class field definintions go here
   */

   private static double x = 0.0;
   private static double y = 0.0;
   private static double dx = 0.0;
   private static double dy = 0.0;
   private static double timeSlice = 0.0;
   double fieldXValue = 1000.0; //plus or minus 500
   double fieldYValue = 1000.0; //plus or minus 500
   static Ball[] b = null;
   static int iteration = 0;
   private static double poleX = Math.random() * (500);
   private static double poleY = Math.random() * (500);
   private static int numberOfBalls = 0;

   static String polePattern = "00.00";
   static DecimalFormat poleFormat = new DecimalFormat(polePattern);
   static String poleXString = poleFormat.format(poleX);
   static String poleYString = poleFormat.format(poleY);


  /**
   *  Constructor
   *
   */
   public SoccerSim() {

   }


   /**
    *  Method to return check for collision with pole
    *  @return true or false
    */
   public static boolean poleCollision( Ball b2 ){
        //Check if ball hits pole
        if(Math.sqrt((Math.pow((poleX*12) - (b2.x*12),2) + Math.pow((poleY*12) - (b2.y*12),2))) <= 4.45){
            return true;
        }
        return false;
   }

  /**
   *  Method to handle all the input arguments from the command line
   *   this sets up the variables for the simulation
   */
   public static void handleInitialArguments( String args[] ) {

      System.out.println( "\n   Hello world, from the SoccerSim program!!\n\n" ) ;
      if( 0 == args.length ) {
         System.out.println( "   Sorry you must enter 4 arguments for each ball\n" +
                             "   Usage: java SoccerSim <x> <y> <dx> <dy>\n" +
                             "   Please try again..........." );
         System.exit( 1 );
      }

      try {
          //If the length of the argument is odd



          if(args.length < 4){
              System.out.println( "   Sorry you must enter the correct number of valid arguments\n" +
                               "   Usage: java SoccerSim <x> <y> <dx> <dy>\n" +
                               "   Please try again..........." );
              System.exit( 1 );
          }

          if(args.length % 2 != 0) {
              //The argument length minus one divided by four is zero = there is a time slice
              if( (args.length - 1) % 4 == 0 ) {
                  //Set timeSlice to this last value and validate it
                  if(Double.parseDouble(args[args.length-1]) > 0.0 && Double.parseDouble(args[args.length-1]) <= 1800){
                      timeSlice = Double.parseDouble(args[args.length-1]);
                  } else {
                      System.out.println("Invalid timeSlice value, must be greater than zero or less than 1800");
                      System.exit( 1 );
                  }
                  //Find out how many balls there are in the argument
                  numberOfBalls = (args.length-1)/4;
                  b = new Ball[numberOfBalls];
                  int j = 0;
                      for(int i = 0; i < args.length-1; i += 4){
                          double x = Double.parseDouble(args[i+0]);
                          double y = Double.parseDouble(args[i+1]);
                          double dx = Double.parseDouble(args[i+2]);
                          double dy = Double.parseDouble(args[i+3]);
                          b[j] = new Ball(x,y,dx,dy);
                          j++;
                      }

                 //NOT ENOUGH ARGUMENTS RETURN ERROR
                } else {
                    System.out.println( "   Sorry you must enter the correct number of valid arguments\n" +
                                     "   Usage: java SoccerSim <x> <y> <dx> <dy>\n" +
                                     "   Please try again..........." );
                    System.exit( 1 );
                }
            }


            if( (args.length) % 4 != 0 && args.length % 4 != 1 ) {
                System.out.println( "   Sorry you must enter the correct number of valid arguments\n" +
                                 "   Usage: java SoccerSim <x> <y> <dx> <dy>\n" +
                                 "   Please try again..........." );
                System.exit( 1 );
            }


            //If there is an even number of arguments divisible by four, add default timeSlice, create ball array, validate arguments
            if( (args.length) % 4 == 0 ) {
                timeSlice = 1.0;
                numberOfBalls = args.length/4;
                b = new Ball[numberOfBalls];
                int j = 0;
                    for(int i = 0; i < args.length; i += 4){
                        double x = Double.parseDouble(args[i+0]);
                        double y = Double.parseDouble(args[i+1]);
                        double dx = Double.parseDouble(args[i+2]);
                        double dy = Double.parseDouble(args[i+3]);
                        b[j] = new Ball(x,y,dx,dy);
                        j++;
                    }

             }

      }
      catch( NumberFormatException nfe ){
          System.out.println( "Caught NumberFormat Exception" );
          System.exit(1);
      }


      //Checking if the initial x and y values of balls are within the bounds of the field
      for(int i = 0; i < numberOfBalls; i++){
          if((Math.abs(b[i].x) > 2000) || (Math.abs(b[i].y) > 2000)){
              System.out.println( "   Sorry you must enter x and y values within the bounds of the field (Cartesian, 500 to -500)\n");
              System.exit( 1 );
          }
      }

   }



  /**
   *  The main program starts here
   *  remember the constraints from the project description
   *  @see  http://bjohnson.lmu.build/cmsi186web/homework04.html
   *  @param  args  String array of the arguments from the command line
   *
   *
   */
   public static void main( String args[] ) {
      int i = 0;

      Ball ball = new Ball(x,y,dx,dy);
      b = new Ball[numberOfBalls];
      handleInitialArguments(args);
      Timer timer = new Timer(timeSlice);


      //Initial output progress report
      System.out.println( "   FIELD SIZE IS 1000.0 BY 1000.0 \n" +
                          "   POLE LOCATION IS: " + poleXString + "," + poleYString + "\n" +
                          "   TIME SLICE VALUE IS: " + timeSlice + " seconds \n" +
                          "   INITIAL REPORT AT: " + timer.toString() + "\n" +
                          "   BALL COUNT = " + numberOfBalls + "\n");

      for(i = 0; i < numberOfBalls; i++){
          System.out.println(" BALL " + i + " " + b[i].toString() + "\n");
      }


      while( true ) {

         //Updates the clock timer
         timer.tick();
         System.out.println( "   PROGRESS REPORT at " + timer.toString());
         //Add to the number of iterations through the loop
         iteration ++;

         for(i = 0; i < numberOfBalls; i++){
             //Move the balls
             b[i].move(timeSlice);

             //Update the velocity values
             b[i].updateVelocity(timeSlice);

             //Check if balls are still in the field
             if(b[i].isInBounds() == false){
    
                b[i].dx = 0;
                b[i].dy = 0;
             }



             //Check if ball is in motion still
             if(b[i].isNotInMotion() == true){

                 b[i].dx = 0;
                 b[i].dy = 0;

             }


         }


         //Checking if the balls hit one another or the pole
         for(i = 0; i < numberOfBalls; i++){
             for(int j = i + 1; j < numberOfBalls; j++){
                 if( b[i].collision(b[j]) == true ){
                     //Stop simulation
                     System.out.println("Report collision between " + i + " and " + j + " at time " + timer.toString());
                     System.out.println("Simulation required " + iteration + " iterations to complete" + "\n");
                     System.exit(1);

                 } else if( poleCollision(b[j]) == true ){
                     //Stop simulation
                     System.out.println("Report collision between " + i + " and pole at time " + timer.toString());
                     System.out.println("Simulation required " + iteration + " iterations to complete" + "\n");
                     System.exit(1);
                 }

             }
        }



        //Check if all the balls are out of bounds
        int countTwo = 0;
        for(i = 0; i < numberOfBalls; i++){
            if(b[i].isInBounds() == false) {
                countTwo++;
            }
        }

        if(countTwo == numberOfBalls){
            System.out.println("All soccer balls are out of bounds, stopping sim... NO COLLISION");
            System.out.println("Simulation required " + iteration + " iterations to complete" + "\n");
            System.exit(1);
        }



        //Check if all balls stop moving without a collision
        int count = 0;
        for(i = 0; i < numberOfBalls; i++){
            if(b[i].isNotInMotion() == true) {
                count ++;
            }
        }

        if(count == numberOfBalls){
            System.out.println("All soccer balls have stopped moving, stopping sim... NO COLLISION");
            System.out.println("Simulation required " + iteration + " iterations to complete" + "\n");
            System.exit(1);
        }



        //System report after every tick of the clock
        for(i = 0; i < numberOfBalls; i++){
            System.out.println("   Ball " + i + " " + b[i].toString() + "\n");
        }


    }
  }
}
