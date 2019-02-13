/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  File name     :  HighRoll.java
 *  Purpose       :  Demonstrates the use of input from a command line for use with Yahtzee
 *  Author        :  Mallory Benna
 *  Date          :  2019-02-14
 *  Description   :
 *  Notes         :  None
 *  Warnings      :  None
 *  Exceptions    :  None
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  Revision Histor
 *  ---------------
 *            Rev      Date     Modified by:  Reason for change/modification
 *           -----  ----------  ------------  -----------------------------------------------------------
 *  @version 1.0.0  2017-02-14  B.J. Johnson  Initial writing and release
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class HighRoll {

   public static void main( String args[] ) {
      System.out.println( "\n   Welcome to High Roll\n" );
      int count = Integer.parseInt(args[0]);
      int sides = Integer.parseInt(args[1]);
      DiceSet ds = new DiceSet(count, sides);
      int highScore = 0;
      int value = 0;
      int individual = 0;



     // This line uses the two classes to assemble an "input stream" for the user to type
     // text into the program
      BufferedReader input = new BufferedReader( new InputStreamReader( System.in ) );
      while( true ) {
        //The 5 options you can choose from
         System.out.println( " Option 1: ROLL ALL THE DICE " );
         System.out.println( " Option 2: ROLL A SINGLE DIE " );
         System.out.println( " Option 3: CALCULATE THE SCORE FOR THIS SET " );
         System.out.println( " Option 4: SAVE THIS SCORE AS HIGH SCORE " );
         System.out.println( " Option 5: DISPLAY THE HIGH SCORE " );
         System.out.println( " Option 6: ENTER 'Q' TO QUIT THE PROGRAM " );
         String inputLine = null;
         try {
            inputLine = input.readLine();
        //If you get one of the numbers 1-5 then perform task, but if you get 6, quit program
           if( 0 == inputLine.length() ) {
               System.out.println("enter some text!:");
            }
            System.out.println( "\n   You typed: " + inputLine + "\n");

            //OPTION1: Roll all the DICE
            if ( '1' == inputLine.charAt(0) ){
                //DiceSet.java roll() method
                ds.roll();
                System.out.println("\n" + ds.toString() + "\n");
            }

            //OPTION2: Roll a single die
            if ( '2' == inputLine.charAt(0) ){
                //Prompt again "Which die?"
                System.out.println( "Which die?" );
                inputLine = input.readLine();

                //DiceSet.java rollIndividual() method
                individual = ds.rollIndividual(Integer.parseInt(inputLine));
                System.out.println("\n" + "[" + individual + "]" + "\n");
            }

            //OPTION3: Calculate the score for this set - you add all die values together
            if ( '3' == inputLine.charAt(0) ){
                //DiceSet.java sum() method
                value = ds.sum();
                System.out.println("\n Score is: " + value + "\n");
            }

            //OPTION4: Save this score as high score
            if ( '4' == inputLine.charAt(0) ){
                //Create variable that sees if sum() after every roll is higher than last. If it is, store as highScore

                if(ds.sum() > highScore ){
                    highScore = ds.sum();
                }

            }

            //OPTION5: Display the high score
            if ( '5' == inputLine.charAt(0) ){
                 //Display variable highScore
                 System.out.println("\n" + highScore + "\n");
            }

            //OPTION6: ENTER 'Q' TO QUIT THE PROGRAM
            if( 'q' == inputLine.charAt(0) || '6' == inputLine.charAt(0)) {
               break;
            }         }
         catch( IOException ioe ) {
            System.out.println( "Caught IOException" );
         }
      }
   }

}
