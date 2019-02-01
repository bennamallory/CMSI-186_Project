/**
 *  File name     :  CountTheDays.java
 *  Purpose       :  TO count the days between two dates
 *  Author        :  Mallory Benna
 *  Date          :  2019-01-29 (prototype)
 *  Author        :  Mallory Benna
 *  Date          :  2019-01-31
 *  Description   :  This file will calculate the number of days between two dates. It converts all the string
 *                   value inputs into long values and then uses those long values in the daysBetween method.
 *
 *  Notes         :  None
 *  Warnings      :  None
 *  Exceptions    :  None
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  Revision History
 *  ----------------
 *            Rev      Date     Modified by:  Reason for change/modification
 *           -----  ----------  -------------  -----------------------------------------------------------
 *  @version 1.0.0  2019-01-29  Mallory Benna  Initial writing and release
 */


public class CountTheDays {

    public static void main( String args[] ) {

       long month1 = 0;
       long day1 =0;
       long year1 = 0;
       long month2  = 0;
       long day2 = 0;
       long year2 = 0;

       if( args.length == 0 ) {
          System.out.print( "Invalid Date Input" );


       } else {
          try {
            month1 = Long.parseLong( args[0] );
            day1 = Long.parseLong( args[1] );
            year1 = Long.parseLong( args[2] );
            month2 = Long.parseLong( args[3]);
            day2 = Long.parseLong( args[4]);
            year2 = Long.parseLong( args[5] );

          }
          catch( NumberFormatException nfe ) {
            System.out.println( "Bad input" );
            System.exit( -1 );
         }
     }
     CalendarStuff.daysBetween( long month1, long day1, long year1, long month2, long day2, long year2 );
     System.out.println(CalendarStuff.daysBetween( long month1, long day1, long year1, long month2, long day2, long year2 ));
  }
}
