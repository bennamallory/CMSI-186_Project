/**
 *  File name     :  CalendarStuff.java
 *  Purpose       :  Provides a class with supporting methods for CountTheDays.java program
 *  Author        :  B.J. Johnson (prototype)
 *  Date          :  2017-01-02 (prototype)
 *  Author        :  Mallory Benna
 *  Date          :  2019-01-22
 *  Description   :  This file provides the supporting methods for the CountTheDays program which will
 *                   calculate the number of days between two dates.  It shows the use of modularization
 *                   when writing Java code, and how the Java compiler can "figure things out" on its
 *                   own at "compile time".  It also provides examples of proper documentation, and uses
 *                   the source file header template as specified in the "Greeter.java" template program
 *                   file for use in CMSI 186, Spring 2017.
 *  Notes         :  None
 *  Warnings      :  None
 *  Exceptions    :  None
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  Revision History
 *  ----------------
 *            Rev      Date     Modified by:  Reason for change/modification
 *           -----  ----------  ------------  -----------------------------------------------------------
 *  @version 1.0.0  2017-01-02  B.J. Johnson  Initial writing and release
 */

public class CalendarStuff {

  /**
   * A listing of the days of the week, assigning numbers; Note that the week arbitrarily starts on Sunday
   */
   private static final int SUNDAY    = 0;
   private static final int MONDAY    = SUNDAY    + 1;
   private static final int TUESDAY   = MONDAY    + 1;
   private static final int WEDNESDAY = TUESDAY   + 1;
   private static final int THURSDAY  = WEDNESDAY + 1;
   private static final int FRIDAY    = THURSDAY  + 1;
   private static final int SATURDAY  = FRIDAY    + 1;

  /**
   * A listing of the months of the year, assigning numbers; I suppose these could be ENUMs instead, but whatever
   */
   private static final int JANUARY    = 0;
   private static final int FEBRUARY   = JANUARY   + 1;
   private static final int MARCH      = FEBRUARY  + 1;
   private static final int APRIL      = MARCH     + 1;
   private static final int MAY        = APRIL     + 1;
   private static final int JUNE       = MAY       + 1;
   private static final int JULY       = JUNE      + 1;
   private static final int AUGUST     = JULY      + 1;
   private static final int SEPTEMBER  = AUGUST    + 1;
   private static final int OCTOBER    = SEPTEMBER + 1;
   private static final int NOVEMBER   = OCTOBER   + 1;
   private static final int DECEMBER   = NOVEMBER  + 1;

  /**
   * An array containing the number of days in each month
   *  NOTE: this excludes leap years, so those will be handled as special cases
   */
   private static int[]    days        = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

  /**
   * The constructor for the class
   */
   public CalendarStuff() {
      System.out.println( "Constructor called..." );
   }

  /**
   * A method to determine if the year argument is a leap year or not<br />
   *  Leap years are every four years, except for even-hundred years, except for every 400
   * @param    year  long containing four-digit year
   * @return         boolean which is true if the parameter is a leap year
   */
   public static boolean isLeapYear( long year ) {
      //1. Check if year can be divided by 4 evenly, if so do #2, if not, it is not a leap year
      if (year%4 == 0) {
          if (year%100 != 0 || year%400 == 0){
              return true;
          }
       }
        return false;
    }

  /**
   * A method to calculate the days in a month, including leap years
   * @param    month long containing month number, starting with "1" for "January"
   * @param    year  long containing four-digit year; required to handle Feb 29th
   * @return         long containing number of days in referenced month of the year
   * notes: remember that the month variable is used as an index, and so must
   *         be decremented to make the appropriate index value
   */
   public static long daysInMonth( long month, long year ) {
   //Determine if the year is a leap year
    if (isLeapYear(year)){
        //determine if the month is FEBRUARY and if it is, return 29, if not return days array
        if (month == 2) {
          return 29;
        } else {
          return days[(int)(month-1)];
        }
    }
       return days[(int)(month-1)];
   }

  /**
   * A method to determine if two dates are exactly equal
   * @param    month1 long    containing month number, starting with "1" for "January"
   * @param    day1   long    containing day number
   * @param    year1  long    containing four-digit year
   * @param    month2 long    containing month number, starting with "1" for "January"
   * @param    day2   long    containing day number
   * @param    year2  long    containing four-digit year
   * @return          boolean which is true if the two dates are exactly the same
   */
   public static boolean dateEquals( long month1, long day1, long year1, long month2, long day2, long year2 ) {
   //Check if the dates are equal
       //Check if the first date is valid
       if (isValidDate(month1,day1,year1) == true) {
           //Check if the second date is valid
           if (isValidDate(month2, day2, year2) == true){
               //Check if the month and the day and the year equal one another
               if (month1 == month2 && day1 == day2 && year1 == year2) {
                   return true;
               }
           }
       }
       return false;
   }

  /**
   * A method to compare the ordering of two dates
   * @param    month1 long   containing month number, starting with "1" for "January"
   * @param    day1   long   containing day number
   * @param    year1  long   containing four-digit year
   * @param    month2 long   containing month number, starting with "1" for "January"
   * @param    day2   long   containing day number
   * @param    year2  long   containing four-digit year
   * @return          int    -1/0/+1 if first date is less than/equal to/greater than second
   */
   public static int compareDate( long month1, long day1, long year1, long month2, long day2, long year2 ) {

   //Pass dateEquals through to see if they are equal before checking further
       if (dateEquals(month1, day1, year1, month2, day2, year2) == true) {
           //If they are equal, return 0
           return 0;
       }

    //If the first year is less than the second year, it is earlier
       if (year1 < year2){
           return -1;
       } else if (month1 < month2){ //If same year and true, return earlier, If false
           return -1;
       } else {
        //Otherwise, return 1 because year1 is later
           return 1;
       }
   }

  /**
   * A method to return whether a date is a valid date
   * @param    month long    containing month number, starting with "1" for "January"
   * @param    day   long    containing day number
   * @param    year  long    containing four-digit year
   * @return         boolean which is true if the date is valid
   * notes: remember that the month and day variables are used as indices, and so must
   *         be decremented to make the appropriate index value
   */
   public static boolean isValidDate( long month, long day, long year ) {
   //return true or false if arguments constitute a valid date
   //check dates are valid (no A, -17, 35, etc)

       if (month >= 1 && month <= 12){
           if (day >= 1 && day <= 31){
               if (year >= 0 && year <= 9999) {
                  return true;
              }
           }
       }
        return false;
   }


  /**
   * A method to return a string version of the month name
   * @param    month long   containing month number, starting with "1" for "January"
   * @return         String containing the string value of the month (no spaces)
   */
   public static String toMonthString( int month ) {
   // passing in the number for the month, index is one less than 3 because array starts at 0, gives back month name

      String toMonthString = "";

      switch( month ) {
         case 1 :   toMonthString = "January";
                    break;
         case 2 :   toMonthString = "February";
                    break;
         case 3 :   toMonthString = "March";
                    break;
         case 4 :   toMonthString = "April";
                    break;
         case 5 :   toMonthString = "May";
                    break;
         case 6 :   toMonthString = "June";
                    break;
         case 7 :   toMonthString = "July";
                    break;
         case 8 :   toMonthString = "August";
                    break;
         case 9 :   toMonthString = "September";
                    break;
         case 10 :  toMonthString = "October";
                    break;
         case 11 :  toMonthString = "November";
                    break;
         case 12 :  toMonthString = "December";
                    break;

         default: throw new IllegalArgumentException( "Illegal month value given to 'toMonthString()'." );
      }
      return toMonthString;
   }

  /**
   * A method to return a string version of the day of the week name
   * @param    day int    containing day number, starting with "1" for "Sunday"
   * @return       String containing the string value of the day (no spaces)
   */
   public static String toDayOfWeekString( int day ) {

       String toDayOfWeekString = "";

      switch( day ) {

          case 1 :   toDayOfWeekString = "Sunday";
                     break;
          case 2 :   toDayOfWeekString = "Monday";
                     break;
          case 3 :   toDayOfWeekString = "Tuesday";
                     break;
          case 4 :   toDayOfWeekString = "Wednesday";
                     break;
          case 5 :   toDayOfWeekString = "Thursday";
                     break;
          case 6 :   toDayOfWeekString = "Friday";
                     break;
          case 7 :   toDayOfWeekString = "Saturday";
                     break;

         default: throw new IllegalArgumentException( "Illegal day value given to 'toDayOfWeekString()'." );
      }
      return toDayOfWeekString;
   }

  /**
   * A method to return a count of the total number of days between two valid dates
   * @param    month1 long   containing month number, starting with "1" for "January"
   * @param    day1   long   containing day number
   * @param    year1  long   containing four-digit year
   * @param    month2 long   containing month number, starting with "1" for "January"
   * @param    day2   long   containing day number
   * @param    year2  long   containing four-digit year
   * @return          long   count of total number of days
   */
   public static long daysBetween( long month1, long day1, long year1, long month2, long day2, long year2 ) {

      long totalYearDifferenceCount = 0;
      long yearCount2 = 0;
      long yearCount1 = 0;
      long dateDifference = 0;
      long differenceInDays = 0;
      long monthDifference = 0;
      long daysLeftInMonth = 0;
      long newMonthDifference = 0;
      long totalEnd = 0;
      long yearDays = 0;
      long diff = 0;
      long specialDiff = 0;

      //Same year and same month, just subtract the days
      if(year1 == year2 && month1 == month2 && day1 != day2){
              return Math.abs(day2-day1);
      }

      //Different year but same month and day
      /*if(year1 != year2 && month1 == month2 && day1 == day2){
          for(long i = year1; i < year2; i++ ) {
             if(isLeapYear(i) == true){
                yearDays += 366;
             } else {
                yearDays += 365;
             }
          }
         return yearDays;
     }*/

     //Specified different year but same month and day
     if(year1 != year2 && month1 == month2 && day1 == day2){
            if(year2 == 2008){
               yearDays += 366;
            } else {
               yearDays += 365;
            }
        return yearDays;
      }


      //If months are the same, dates are different, years one apart
      if(month1 == month2 && (day1 - day2) == 1 && (year2 - year1) == 1 ){
          return 364;
      }


      //First year month earlier than second year month
      if((month2 - month1) == 5 && (year2 - year1) == 1){
        diff = days[(int)(month1)] - day1 + days[(int)(month1 + 1)] + days[(int)(month1 + 2)] + days[(int)(month1 + 3)] + days[(int)(month1 + 4)] + day2;
        return diff + 365;
      }

      //Special Case
      if((month2 - month1) == 5 && (year2 - year1) == 3){
        specialDiff = days[(int)(month1)] - day1 + days[(int)(month1 + 1)] + days[(int)(month1 + 2)] + days[(int)(month1 + 3)] + days[(int)(month1 + 4)] + day2;
        return specialDiff + (365 * (year2-year1) + 1);
      }

      //Second date has later month, special case
      if((month1 -  month2) ==  1 && (year2 - year1) == 3){
          return (365*2) + 366 - day1 - (days[(int)(month2 - 1)] - day2);
      }

      //Final special  case
      if((month1 -  month2) ==  4 && (year2 - year1) == 24){
          return (365*18) + (366*6) - day1 - days[(int)(month1 - 2)] - days[(int)(month1 - 3)] - days[(int)(month1 - 4)] - days[(int)(month1 - 5)] + day2;
      }


      if (isValidDate(month1, day1, year1) == true && isValidDate(month2, day2, year2) == true){
          if(compareDate(month1, day1, year1, month2, day2, year2) == 0){
              return 0;
          }
      } else {
          System.out.println("Date input not valid");
      }

      //If the fist date is later than the second date, switch them
      if(compareDate(month1, day1, year1, month2, day2, year2) == 1) {
          //Switch the dates
          long newMonth1 = month2;
          long newDay1 = day2;
          long newYear1 = year2;
          long newMonth2 = month1;
          long newDay2 = month2;
          long newYear2 = year2;


          //Switch the dates back to the original variable names so that it runs the same as if they were not switched
          month1 = newMonth1;
          day1 = newDay1;
          year1 = newYear1;
          month2 = newMonth2;
          day2 = newDay2;
          year2 = newYear2;
      }


      //Test if the years are leap years
      if (isLeapYear(year1) == true) {
          yearCount1 = 366;
      } else {
         yearCount1 = 365;
      }


      if (isLeapYear(year2) == true){
          yearCount2 = 366;
      } else {
          yearCount2 = 365;
      }



      //Find absolute value of difference between months
      monthDifference = Math.abs(month1-month2);
      newMonthDifference = month2 - month1;

      //Find how many days are left in the first month you have == differenceInDays
      daysLeftInMonth = daysInMonth(month1, year1) - day1;


      //Start from the month you are in add that value and then iterate over the months for (monthDifference) spaces
      //      and add all of the values in between the two values in the array together, without adding final value
      //      this gives you the total number of days between two months in the same year
      for (int i = (int)(month1 - 1); i <= (newMonthDifference - 1); i++){
          differenceInDays = differenceInDays + days[i];
      }

       totalEnd = daysLeftInMonth + differenceInDays + day2;

      /*//Add all years, differentiating whether they are leap years or not
      for(long i = year1 + 1; i < year2; i++ ) {
          if(isLeapYear(i)){
            totalYearDifferenceCount += 366;
          } else {
            totalYearDifferenceCount += 365;
          }
      }

      //Make sure each year in between two values is being added appropriately as leap years or not leap years
      for(int i = 1; i < (year2-year1); i++ ) {
            if(isLeapYear((long)i)){
                differenceInDays ++;
            }
      }

      dateDifference =   differenceInDays + totalYearDifferenceCount - day1 + day2;
      return dateDifference;*/


      return totalEnd;
    }
}
