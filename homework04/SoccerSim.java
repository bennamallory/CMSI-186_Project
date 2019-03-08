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
        if(Math.sqrt(((x*12) - (b2.x*12)) + ((y*12) - (b2.y*12))) <= 4.45){
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
          if(args.length % 2 != 0) {
              //The argument length minus one divided by four is zero = there is a time slice
              if( (args.length - 1) % 4 == 0 ) {
                  //Set timeSlice to this last value and validate it
                  if(Double.parseDouble(args[args.length-1]) >= 0){
                      timeSlice = Double.parseDouble(args[args.length-1]);
                  } else {
                      System.out.println("Invalid timeSlice value, must be greater than zero");
                      System.exit( 1 );
                  }
                  //Find out how many balls there are in the argument
                  numberOfBalls = args.length/4;
                  b = new Ball[numberOfBalls];
                  //Nested loop - dividing out arguments into ball values
                  for(int j = 0; j < numberOfBalls; j++){
                      b[j] = new Ball(0,0,0,0);
                      for(int i = 0; i < args.length-1; i += 4){
                          b[j].x = Double.parseDouble(args[i+0]);
                          b[j].y = Double.parseDouble(args[i+1]);
                          b[j].dx = Double.parseDouble(args[i+2]);
                          b[j].dy = Double.parseDouble(args[i+3]);
                      }
                  }

                 //NOT ENOUGH ARGUMENTS RETURN ERROR
                } else {
                    System.out.println( "   Sorry you must enter the correct number of valid arguments\n" +
                                     "   Usage: java SoccerSim <x> <y> <dx> <dy>\n" +
                                     "   Please try again..........." );
                    System.exit( 1 );
                }
            }


            //If there is an even number of arguments divisible by four, add default timeSlice, create ball array, validate arguments
            if( (args.length) % 4 == 0 ) {
                timeSlice = 1.0;
                numberOfBalls = args.length/4;
                b = new Ball[numberOfBalls];
                for(int j = 0; j < numberOfBalls; j++){
                    b[j] = new Ball(0,0,0,0);
                    for(int i = 0; i < args.length; i += 4){
                        b[j].x = Double.parseDouble(args[i+0]);
                        b[j].y = Double.parseDouble(args[i+1]);
                        b[j].dx = Double.parseDouble(args[i+2]);
                        b[j].dy = Double.parseDouble(args[i+3]);
                    }
                }
             }

      }
      catch( NumberFormatException nfe ){
          System.out.println( "Caught NumberFormat Exception" );
          System.exit(1);
      }


      //Checking if the initial x and y values of balls are within the bounds of the field
      for(int i = 0; i < numberOfBalls; i++){
          if((Math.abs(b[i].x) > 500) || (Math.abs(b[i].y) > 500)){
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
      Timer timer = new Timer(timeSlice);
      Ball ball = new Ball(x,y,dx,dy);
      b = new Ball[numberOfBalls];
      handleInitialArguments(args);


      //Initial output progress report
      System.out.println( "   FIELD SIZE IS 1000.0 BY 1000.0 - (Cartesian -500 BY 500) \n" +
                          "   POLE LOCATION IS: " + poleX + "," + poleY + "\n" +
                          "   TIME SLICE VALUE IS: " + timeSlice + "\n" +
                          "   INITIAL REPORT AT: " + timer.toString() + "\n" +
                          "   BALL COUNT = " + numberOfBalls + "\n");


      while( true ) {
         //Updates the clock timer
         timer.tick();
         //Add to the number of iterations through the loop
         iteration ++;

         for(i = 0; i < numberOfBalls; i++){
             //Move the balls
             b[i].move();

             //Update the velocity values
             b[i].updateVelocity();

             //Check if balls are still in the field
             if(b[i].isInBounds() == false){
                b[i].x = 5000;
                b[i].y = 5000;
                b[i].dx = 0;
                b[i].dy = 0;
             }

             //Check if ball is in motion still
             if(b[i].isInMotion() == false){
                 b[i].x = 50000;
                 b[i].y = 50000;
                 b[i].dx = 0;
                 b[i].dy = 0;
             }


         }

        i = 0;
        //Check if all balls stop moving without a collision
        if(b[i].isInMotion() == false) {
               System.out.println("All soccer balls have stopped moving, stopping sim... NO COLLISION");
               System.exit(1);
        }


         //Checking if the balls hit one another or the pole
         for(i = 0; i < numberOfBalls; i++){
             for(int j = i + 1; j < numberOfBalls; j++){
                 if(b[i].collision(b[j])){
                     //Stop Sim
                     System.out.println("Report collision between " + i + " and " + j + "at time " + timer.toString());
                     System.out.println("Simulation required " + iteration + " iterations to complete");
                     System.exit(1);

                 } else if( poleCollision(b[j]) ){
                     //stop simulation
                     System.out.println("Report collision between " + i + " and pole at time " + timer.toString());
                     System.out.println("Simulation required " + iteration + " iterations to complete");
                     System.exit(1);
                 }

             }

             //System report after every tick of the clock
             System.out.println( "   PROGRESS REPORT at " + timer.toString());
             System.out.println("   Ball " + i + b[i].toString() + "\n");

         }

      }
   }
}
