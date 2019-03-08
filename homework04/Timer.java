/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  File name     :  Timer.java
 *  Purpose       :  Provides a class defining methods for the SoccerSim class
 *  @author       :  Mallory Benna
 *  Date written  :  2019-02-14
 *  Description   :  This class provides a bunch of methods which may be useful for the SoccerSim class
 *
 *  Notes         :  None
 *  Warnings      :  None
 *  Exceptions    :  IllegalArgumentException when the input arguments are "hinky"
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  Revision Histor
 *  ---------------
 *            Rev      Date     Modified by:  Reason for change/modification
 *           -----  ----------  ------------  -----------------------------------------------------------
 *  @version 1.0.0  2017-02-28  B.J. Johnson  Initial writing and release
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
import java.text.DecimalFormat;


public class Timer {
  /**
   *  Class field definintions go here
   */
   private static final double DEFAULT_TIME_SLICE_IN_SECONDS = 60.0;
   private static final double INVALID_ARGUMENT_VALUE = -1.0;
   private static final double MAXIMUM_DEGREE_VALUE = 360.0;



   static double timeSlice = 0.0;
   double elapsedTime = 0.0;
   int revolution = 0;


  /**
   *  Constructor goes here
   */
   public Timer( double timeSlice) {
       this.timeSlice = timeSlice;
       elapsedTime = 0.0;
   }

  /**
   *  Methods go here
   *
   *  Method to calculate the next tick from the time increment
   *  @return double-precision value of the current clock tick
   */
   public double tick() {
      //Calculate the next tick from the time increment

      elapsedTime += timeSlice;

      //If the total seconds minus the number of revolutions in seconds is greater than 3600 sec
      //Help by Thomas Kelly 2/21/2019 with revolution steps only
      if(elapsedTime - (3600*revolution) >= 3600){
          revolution ++;
      }
      return elapsedTime;
   }



  /**
   *  Method to fetch the total number of seconds
   *   we can use this to tell when 12 hours have elapsed
   *  @return double-precision value the total seconds private variable
   */
   public double getTotalSeconds() {
      //get total number of seconds passed
      return elapsedTime;
   }

  /**
   *  Method to return a String representation of this clock
   *  @return String value of the current clock
   */
   public String toString() {
      
      String hourPattern = "00";
      String minutePattern = "00";
      String secondPattern = "00.000";

      DecimalFormat minuteFormat = new DecimalFormat(minutePattern);
      DecimalFormat secondFormat = new DecimalFormat(secondPattern);

      double remainder = elapsedTime % 3600;
      String hourString = minuteFormat.format(Math.floor(elapsedTime / 3600));
      String minuteString = minuteFormat.format(Math.floor(remainder / 60));
      String secondString = secondFormat.format(Math.floor(remainder % 60));


      return hourString + " : " + minuteString + " : " + secondString;
   }


  /**
   *  The main program starts here
   *  remember the constraints from the project description
   *  @see  http://bjohnson.lmu.build/cmsi186web/homework04.html
   *  be sure to make LOTS of tests!!
   *  remember you are trying to BREAK your code, not just prove it works!
   */
   public static void main( String args[] ) {

      System.out.println( "\nTIMER CLASS TESTER PROGRAM\n" +
                          "--------------------------\n" );
      System.out.println( "  Creating a new timer: " );
      Timer timer = new Timer(10);
      timer.tick();
      System.out.println( "    New timer created: " + timer.toString() );

      timer.tick();
      System.out.println( "    New timer created: " + timer.toString() );

      timer.tick();
      System.out.println( "    New timer created: " + timer.toString() );
      System.out.println( "    New timer created, should print out 30 sec ==== " + timer.toString());

      timer = new Timer(180);
      timer.tick();
      System.out.println( "    New timer created: " + timer.toString() );

      timer.tick();
      System.out.println( "    New timer created: " + timer.toString() );

      timer.tick();
      System.out.println( "    New timer created: " + timer.toString() );
      System.out.println( "    New timer created, should print out 00:09:00 === " + timer.toString() );

      timer = new Timer(1800);
      timer.tick();
      System.out.println( "    New timer created: " + timer.toString() );

      timer.tick();
      System.out.println( "    New timer created: " + timer.toString() );

      timer.tick();
      System.out.println( "    New timer created: " + timer.toString() );
      System.out.println( "    New timer created, should print out 01:30:00 === " + timer.toString());





   }

}
