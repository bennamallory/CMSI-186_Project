/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  File name     :  Collatz.java
 *  Purpose       :  The main program for the BigInt class
 *
 *  @author       :  Mallory Benna
 *  Date written  :  2019-04-16
 *  Description   :  This class runs methods from BrobInt for several iterations to reach the number 1
 *
 *  Notes         :  None
 *  Warnings      :  None
 *  Exceptions    :  IllegalArgumentException when the input arguments are "hinky"
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  Revision History
 *  ---------------
 *            Rev      Date     Modified by:  Reason for change/modification
 *           -----  ----------  ------------  -----------------------------------------------------------
 *  @version 1.0.0  2019-04-16  Mallory Benna  Initial writing and release
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */

import java.text.DecimalFormat;


public class Collatz {
  /**
   *  Class field definitions go here
   */

   public static final BrobInt ZERO     = new BrobInt(  "0" );      /// Constant for "zero"
   public static final BrobInt ONE      = new BrobInt(  "1" );      /// Constant for "one"

  public static int iterations = 0;


/**
  *  Constructor
  *
  */
  public Collatz() {
    super();
  }



/**
  *  Method to handle all the input arguments from the command line
  *   this sets up the variables for the simulation
  */
  public static void handleInitialArguments( String args[] ) {


    System.out.println( "\n   Hello world, from the Collatz program!!\n\n" ) ;
    if( 0 == args.length ) {
       System.out.println( "   Sorry you must enter a valid number");
       System.exit( 1 );
    }


    // //Check if all characters are numbers
    // //May have a plus or minus sign on it
    // for(int i = 0; i < args[0].length(); i++){
    //   if( !(Character.isDigit(args[0].charAt(i))) || !('+' == args[0].charAt(i)) || !('-' == args[0].charAt(i)) ) {
    //    System.out.println( "\n  There must only be numbers entered, optional + or - before" );
    //    System.exit(1);
    //   }
    // }

  }


/**
  *  The main program starts here
  *  remember the constraints from the project description
  *  @see  http://bjohnson.lmu.build/cmsi186web/homework06.html
  *  @param  args  String array of the arguments from the command line
  *
  *
  */
  public static void main( String args[] ) {

    handleInitialArguments(args);
    BrobInt b1 = new BrobInt(args[0]);
    BrobInt bCopy = new BrobInt(b1.toString());




    //This is the sequence we saw in the CMSI 185 homework that takes an integer number as input,
    //  then generates a sequence of values based upon whether the current value is even or odd;
    //  if it is even, the value is divided by 2, and if it is odd, the value is multiplied by three
    //  then one is added to that result.
    //  The program should output both the sequence and the number of steps.

    BrobInt result = ZERO;
    iterations = 0;

    System.out.println("ITERATIONS: " + iterations + ", SEQUENCE: " + bCopy);

    while ( bCopy.compareTo(BrobInt.ONE) != 0 ){
      //If even (modulo of this number is zero), divide by 2
      if( bCopy.remainder(BrobInt.TWO) == BrobInt.ZERO ){
        bCopy = bCopy.divide(BrobInt.TWO);
      //Otherwise, it is odd
      } else {
        //multiply by 3 and add one
        bCopy = bCopy.multiply(BrobInt.THREE).add(BrobInt.ONE);
      }

      iterations += 1;



      System.out.println("ITERATIONS: " + iterations + ", SEQUENCE: " + bCopy);
    }

    System.out.println("Value of 1 was reached after " + iterations + " iterations");
    System.exit(1);
  }


}
