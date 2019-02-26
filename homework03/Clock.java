/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  File name     :  Clock.java
 *  Purpose       :  Provides a class defining methods for the ClockSolver class
 *  @author       :  Mallory Benna
 *  Date written  :  2019-02-14
 *  Description   :  This class provides a bunch of methods which may be useful for the ClockSolver class
 *                   for Homework 4, part 1.  Includes the following:
 *
 *  Notes         :  None right now.  I'll add some as they occur.
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


public class Clock {
  /**
   *  Class field definintions go here
   */
   private static final double DEFAULT_TIME_SLICE_IN_SECONDS = 60.0;
   private static final double INVALID_ARGUMENT_VALUE = -1.0;
   private static final double MAXIMUM_DEGREE_VALUE = 360.0;
   private static final double HOUR_HAND_DEGREES_PER_SECOND = 0.00834;
   private static final double MINUTE_HAND_DEGREES_PER_SECOND = 0.1;


   static double timeSlice = 0.0;
   double elapsedTime = 0.0;
   static double angleValue = 0.0;
   double hourHandAngle = 0.0;
   double minuteHandAngle = 0.0;
   double totalAngle = 0.0;
   int revolution = 0;


  /**
   *  Constructor goes here
   */
   public Clock(Double timeSlice) {
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

      getHourHandAngle();
      getMinuteHandAngle();
      getHandAngle();


      return elapsedTime;
   }

  /**
   *  Method to validate the angle argument
   *  @param   argValue  String from the main programs args[0] input
   *  @return  double-precision value of the argument
   *  @throws  NumberFormatException
   */
   public static double validateAngleArg( String argValue ) throws NumberFormatException {
      //Validate the angle argument
      //Determine if angle plugged in is an angle you can use

      angleValue = Double.parseDouble(argValue);

      if (angleValue < 0 || angleValue >= 360){
          throw new NumberFormatException("Angle not in range [0..360]");
      }

      return angleValue;
   }

  /**
   *  Method to validate the optional time slice argument
   *  @param  argValue  String from the main programs args[1] input
   *  @return double-precision value of the argument or -1.0 if invalid
   *  note: if the main program determines there IS no optional argument supplied,
   *         I have elected to have it substitute the string "60.0" and call this
   *         method anyhow.  That makes the main program code more uniform, but
   *         this is a DESIGN DECISION, not a requirement!
   *  note: remember that the time slice, if it is small will cause the simulation
   *         to take a VERY LONG TIME to complete!
   */
   public static double validateTimeSliceArg( String argValue ) {
      //Validate time slice argument
      timeSlice = Double.parseDouble(argValue);

      if(timeSlice < 1800.0 || timeSlice > 0){
          return timeSlice;
      } else {
          timeSlice = 60.0;

      }

      return timeSlice;
   }

  /**
   *  Method to calculate and return the current position of the hour hand
   *  @return double-precision value of the hour hand location
   */
   public double getHourHandAngle() {
      //Calculate and return the current position of the hour hand
      //HOUR_HAND_DEGREES_PER_SECOND = 0.00834;

      hourHandAngle = elapsedTime * HOUR_HAND_DEGREES_PER_SECOND;

      return hourHandAngle;
   }

  /**
   *  Method to calculate and return the current position of the minute hand
   *  @return double-precision value of the minute hand location
   */
   public double getMinuteHandAngle() {
      //Calculate and return the current position of the minute hand
      //Moves 6 degrees a minute, portion of that every second...

      //Sets minute hand angle to seconds according to one clock revolution
      //Help by Thomas Kelly 2/21/2019 with revolution calculation only
      minuteHandAngle = (elapsedTime - (revolution * 3600)) * MINUTE_HAND_DEGREES_PER_SECOND;

      return minuteHandAngle;
   }

  /**
   *  Method to calculate and return the angle between the hands
   *  @return double-precision value of the angle between the two hands
   */
   public double getHandAngle() {
      //find angle between the hands
      //Add getHourHandAngle and getMinuteHandAngle

      totalAngle = Math.abs(hourHandAngle - minuteHandAngle);

      return totalAngle;
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
      //Translate seconds into hour, minute, second as seen on clock
      //DECIMAL FORMAT
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

      System.out.println( "\nCLOCK CLASS TESTER PROGRAM\n" +
                          "--------------------------\n" );
      System.out.println( "  Creating a new clock: " );
      Clock clock = new Clock(1800.0);
      System.out.println( "    New clock created: " + clock.toString() );
      System.out.println( "    Testing validateAngleArg()....");
      System.out.print( "      sending '  0 degrees', expecting double value   0.0" );
      try { System.out.println( (0.0 == clock.validateAngleArg( "0.0" )) ? " - got 0.0" : " - no joy" ); }
      catch( Exception e ) { System.out.println ( " - Exception thrown: " + e.toString() ); }




   }

}
