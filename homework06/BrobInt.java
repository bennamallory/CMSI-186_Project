/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * File name  :  BrobInt.java
 * Purpose    :  Learning exercise to implement arbitrarily large numbers and their operations
 * @author    :  Mallory Benna
 * Date       :  2019-04-06
 * Description:  @see <a href='http://bjohnson.lmu.build/cmsi186web/homework06.html'>Assignment Page</a>
 * Notes      :  None
 * Warnings   :  None
 *
 *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Revision History
 * ================
 *   Ver      Date     Modified by:  Reason for change or modification
 *  -----  ----------  ------------  ---------------------------------------------------------------------
 *  1.0.0  2017-04-04  B.J. Johnson  Initial writing and begin coding
 *  1.1.0  2017-04-13  B.J. Johnson  Completed addByt, addInt, compareTo, equals, toString, Constructor,
 *                                     validateDigits, two reversers, and valueOf methods; revamped equals
 *                                     and compareTo methods to use the Java String methods; ready to
 *                                     start work on subtractByte and subtractInt methods
 *  1.2.0  2019-04-18  B.J. Johnson  Fixed bug in add() method that was causing errors in Collatz
 *                                     sequence.  Added some tests into the main() method at the bottom
 *                                     of the file to test construction.  Also added two tests there to
 *                                     test multiplication by three and times-3-plus-1 operations
 *
 *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
import java.util.Arrays;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;

public class BrobInt {

  public static final BrobInt ZERO     = new BrobInt(  "0" );      /// Constant for "zero"
  public static final BrobInt ONE      = new BrobInt(  "1" );      /// Constant for "one"
  public static final BrobInt TWO      = new BrobInt(  "2" );      /// Constant for "two"
  public static final BrobInt THREE    = new BrobInt(  "3" );      /// Constant for "three"
  public static final BrobInt FOUR     = new BrobInt(  "4" );      /// Constant for "four"
  public static final BrobInt FIVE     = new BrobInt(  "5" );      /// Constant for "five"
  public static final BrobInt SIX      = new BrobInt(  "6" );      /// Constant for "six"
  public static final BrobInt SEVEN    = new BrobInt(  "7" );      /// Constant for "seven"
  public static final BrobInt EIGHT    = new BrobInt(  "8" );      /// Constant for "eight"
  public static final BrobInt NINE     = new BrobInt(  "9" );      /// Constant for "nine"
  public static final BrobInt TEN      = new BrobInt( "10" );      /// Constant for "ten"

  /// Some constants for other intrinsic data types
  ///  these can help speed up the math if they fit into the proper memory space
  public static final BrobInt MAX_INT  = new BrobInt( Integer.valueOf( Integer.MAX_VALUE ).toString() );
  public static final BrobInt MIN_INT  = new BrobInt( Integer.valueOf( Integer.MIN_VALUE ).toString() );
  public static final BrobInt MAX_LONG = new BrobInt( Long.valueOf( Long.MAX_VALUE ).toString() );
  public static final BrobInt MIN_LONG = new BrobInt( Long.valueOf( Long.MIN_VALUE ).toString() );

  /// These are the internal fields
  public  String internalValue = "";        // internal String representation of this BrobInt
  public  byte   sign          = 0;         // "0" is positive, "1" is negative


   //Other variables
  public int chunks = 0;
  public int[] numArray = null;
  public int brobPiece = 0;
  public int[] resultArray = null;
  public int[] resultArrayTwo = null;
  public int carry = 0;
  public int borrow = 0;
  public int resultArraySpace = 0;
  public String result = "";
  public int start = 0;
  public int stop = 0;
  public int j = 0;
  public int sum = 0;
  DecimalFormat df = new DecimalFormat("000000000");
  int holdSign = 0;


  private static BufferedReader input = new BufferedReader( new InputStreamReader( System.in ) );
  private static final boolean DEBUG_ON = true;
  private static final boolean INFO_ON  = false;


  /**
    *  Constructor takes a string and assigns it to the internal storage, checks for a sign character
    *   and handles that accordingly;  it then checks to see if it's all valid digits, and reverses it
    *   for later use
    *  @param  value  String value to make into a BrobInt
    */
    public BrobInt( String value ) {

      internalValue = value;

      //Validating digits
      if ( internalValue.length() == 0){
           throw new IllegalArgumentException( "\n  There must be at least one digit entered" );
      }

      // for(int i = 0; i < internalValue.length(); i++){
      //   if( !(Character.isDigit(internalValue.charAt(i))) || !('+' == internalValue.charAt(i)) || !('-' == internalValue.charAt(i)) )) {
      //     throw new IllegalArgumentException( "\n  There must only be numbers entered, optional + or - before" );
      //   }
      // }

      //Checking for sign value and removing extra sign if neccessary
      if(internalValue.charAt(0) == '-'){
        sign = 1;
        //Remove sign
        internalValue = internalValue.replace("-", "");


      } else if (internalValue.charAt(0) == '+'){
        sign = 0;
        //Remove sign
        internalValue = internalValue.replace("+", "");
      }


      //Check if the number has 9 or less characters (if has less than 9, 1 chunk and run rest);
      if( internalValue.length() <= 9 ){

        chunks = 1;

        start = 0;
        stop = internalValue.length();

        //Creating a new array with enough space for the chunks
        numArray = new int[chunks];

        numArray[0] = Integer.parseInt(internalValue.substring(start, stop));


      } else if ( internalValue.length() > 9 ){
        //Finding out how many chunks of the number I have
        chunks = internalValue.length()/9;
        if(internalValue.length() % 9 != 0){
          chunks++;
        }


        stop = internalValue.length();
        start = internalValue.length() - 9;


        //Creating a new array with enough space for the chunks
        numArray = new int[chunks];

        //Starting at the end of the BrobInt and parsing every integer into an array
        for(int i = 0; i < chunks; i++){
          //Adds numbers into array back into its original form, lowest number at index zero
          numArray[i] = Integer.parseInt(internalValue.substring(start, stop));

          if(i == numArray.length - 2){
            start = 0;
          } else {
            start -= 9;
          }
          stop -= 9;
        }
      }

    }



  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    *  Method to add the value of a BrobInt passed as argument to this BrobInt using byte array
    *  @param  bint         BrobInt to add to this
    *  @return BrobInt that is the sum of the value of this BrobInt and the one passed in
    *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    public BrobInt add( BrobInt bint ) {

      result = "";



      //Creating a new resultArray with enough space for the resulting number
      if(numArray.length > bint.numArray.length || numArray.length == bint.numArray.length){
        resultArraySpace = chunks + 1;
        resultArray = new int[resultArraySpace];
      } else if ( bint.numArray.length > numArray.length ){
        resultArraySpace = bint.chunks + 1;
        resultArray = new int[resultArraySpace];
      }

      //result = "";
      //If both signs positive (us bigger than bint) - add
      if( (sign == 0 && bint.sign == 0) && (numArray.length > bint.numArray.length) ){

        //Adding arrays until the shortest one is done
        for(int i = 0; i < bint.numArray.length; i++){
          resultArray[i] = numArray[i] + bint.numArray[i] + carry;
          if(resultArray[i] > 999999999 ){
            resultArray[i] -= 1000000000;
            carry = 1;
          } else {
            carry = 0;
          }
        }


        //Adding rest of the larger array starting from the end of the shorter array
        for(int i = bint.numArray.length; i < numArray.length; i++){
          resultArray[i] = numArray[i] + carry;
          if(resultArray[i] > 999999999 ){
            resultArray[i] -= 1000000000;
            carry = 1;
          } else {
            carry = 0;
          }
        }

        //Result calculation
        for(int i = resultArray.length - 1; i >= 0; i--){
          result += df.format(resultArray[i]);
        }
      }

      //result = "";
      //If both signs are positive (bint bigger than us - only loop to size of us first) -- add
      if((sign == 0 && bint.sign == 0) && numArray.length < bint.numArray.length){
        //Add - make sure to only loop first to smallest array

        //Adding arrays until the shortest one is done
        for(int i = 0; i < numArray.length; i++){
          resultArray[i] = numArray[i] + bint.numArray[i] + carry;
          if(resultArray[i] > 999999999 ){
            resultArray[i] -= 1000000000;
            carry = 1;
          } else {
            carry = 0;
          }
        }

        //Adding rest of the larger array starting from the end of the shorter array
        for(int i = numArray.length; i < bint.numArray.length; i++){
          resultArray[i] = bint.numArray[i] + carry;
          if(resultArray[i] > 999999999 ){
            resultArray[i] -= 1000000000;
            carry = 1;
          } else {
            carry = 0;
          }
        }

        //Result
        for(int i = resultArray.length - 1; i >= 0; i--){
          //result += new String(Integer.valueOf(df.format(resultArray[i])).toString());
          result += df.format(resultArray[i]);
        }
      }


      //result = "";
      //If both signs are negative (us bigger than bint) -- add, make result negative
      if((sign == 1 && bint.sign == 1) && numArray.length > bint.numArray.length){
        //Add - make sure to only loop to bint size first (make result neg)

        //Adding arrays until the shortest one is done
        for(int i = 0; i < bint.numArray.length; i++){
          resultArray[i] = numArray[i] + bint.numArray[i] + carry;
          if(resultArray[i] > 999999999 ){
            resultArray[i] -= 1000000000;
            carry = 1;
          } else {
            carry = 0;
          }
        }

        //Adding rest of the larger array starting from the end of the shorter array
        for(int i = bint.numArray.length; i < numArray.length; i++){
          resultArray[i] = numArray[i] + carry;
          if(resultArray[i] > 999999999 ){
            resultArray[i] -= 1000000000;
            carry = 1;
          } else {
            carry = 0;
          }
        }

        //Result
        for(int i = resultArray.length - 1; i >= 0; i--){
          result += new String(Integer.valueOf(df.format(resultArray[i])).toString());
        }
        holdSign = 1;
      }

      //result = "";
      //If both signs are negative (bint bigger than  us) -- add, make result negative
      if((sign == 1 && bint.sign == 1) && numArray.length < bint.numArray.length){
        //Add - make sure to only loop first to smallest array (make result negative)

        //Adding arrays until the shortest one is done
        for(int i = 0; i < numArray.length; i++){
          resultArray[i] = numArray[i] + bint.numArray[i] + carry;
          if(resultArray[i] > 999999999){
            resultArray[i] -= 1000000000;
            carry = 1;
          } else {
            carry = 0;
          }

        }

        //Adding rest of the larger array starting from the end of the shorter array
        for(int i = numArray.length; i < bint.numArray.length; i++){
          resultArray[i] = bint.numArray[i] + carry;
          if(resultArray[i] > 999999999 ){
            resultArray[i] -= 1000000000;
            carry = 1;
          } else {
            carry = 0;
          }
        }

        //Loop to create resulting String
        for(int i = resultArray.length - 1; i >= 0; i--){
          result += new String(Integer.valueOf(df.format(resultArray[i])).toString());
        }
        holdSign = 1;
      }


      //result = "";
      //If both signs are negative and lengths are equal
      if((sign == 1 && bint.sign == 1) && numArray.length == bint.numArray.length){
        //Add - make sure to only loop first to smallest array (make result negative)

        //Adding arrays until the shortest one is done
        for(int i = 0; i < numArray.length; i++){
          resultArray[i] = numArray[i] + bint.numArray[i] + carry;
          if(resultArray[i] > 999999999 ){
            resultArray[i] -= 1000000000;
            carry = 1;
          } else {
            carry = 0;
          }

        }

        //Loop to create resulting String
        for(int i = resultArray.length - 2; i >= 0; i--){
          result += new String(Integer.valueOf(df.format(resultArray[i])).toString());
        }
        holdSign = 1;
      }


      //result = "";
      //If both signs are positive and lengths are equal
      if((sign == 0 && bint.sign == 0) && numArray.length == bint.numArray.length){
        //Add - make sure to only loop first to smallest array

        //Adding arrays until the shortest one is done
        for(int i = 0; i < numArray.length; i++){
          resultArray[i] = numArray[i] + bint.numArray[i] + carry;
          if(resultArray[i] > 999999999 ){
            resultArray[i] -= 1000000000;
            carry = 1;
          } else {
            carry = 0;
          }

        }

        //Loop to create resulting String
        for(int i = resultArray.length - 1; i >= 0; i--){
          result += df.format(resultArray[i]);
        }
      }


      BrobInt returnR = removeLeadingZeros(new BrobInt(result));
      if(holdSign == 1){
        returnR.sign = 1;
      }
      return returnR;
    }


  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    *  Method to subtract the value of a BrobIntk passed as argument to this BrobInt using bytes
    *  @param  bint         BrobInt to subtract from this
    *  @return BrobInt that is the difference of the value of this BrobInt and the one passed in
    *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    public BrobInt subtract( BrobInt bint ) {
      // set up for subtraction sign handling to decide what to do based on the following cases:
      result = "";
      holdSign = 0;

      //Creating a new resultArray with enough space for the resulting number
      if(numArray.length > bint.numArray.length){
        resultArraySpace = chunks;
        resultArray = new int[resultArraySpace];
      } else {
        resultArraySpace = bint.chunks;
        resultArray = new int[resultArraySpace];
      }


      //result = "";
      //    1. no signs at all, this item larger than argument:        simple subtraction a - b
      //    2. both signs positive, this item larger than argument:    simple subtraction a - b
      //    3. one positive one no sign, this item larger than arg:    simple subtraction a - b
        if( (sign == 0 && bint.sign == 0) && (numArray.length > bint.numArray.length)){
          for(int i = 0; i < bint.numArray.length; i++){
            if(numArray[i] < bint.numArray[i]){
              borrow = 1;
              numArray[i + 1] -= 1;
              numArray[i] += 1000000000;
            } else {
              borrow = 0;
            }
            resultArray[i] = numArray[i] - bint.numArray[i];
          }

          //To add longer array numbers in with borrow
          for(int i = bint.numArray.length; i < numArray.length; i++){
            resultArray[i] = numArray[i];
          }

          //Loop to create resulting String
          for(int i = numArray.length - 1; i >= 0; i--){
            result += df.format(resultArray[i]);
          }
        }


      //result = "";
      //    4. no signs at all, this item smaller than argument:       swap a & b, subtract a - b,
      if(  (sign == 0 && bint.sign == 0) && numArray.length < bint.numArray.length){
        for(int i = 0; i < numArray.length; i++){
          if(numArray[i] > bint.numArray[i]){
            borrow = 1;
            resultArray[i+1] -= 1;
            resultArray[i] += 1000000000;
          } else {
            borrow = 0;
          }
          resultArray[i] = bint.numArray[i] - numArray[i];
        }

        //To add longer array numbers in
        for(int i = numArray.length; i < bint.numArray.length; i++){
          resultArray[i] = bint.numArray[i];
        }

        //Loop to create resulting String
        for(int i = resultArray.length - 1; i >= 0; i--){
          result += df.format(resultArray[i]);
        }
      }


      //result = "";
      //    5. both signs positive, this item smaller than argument:   swap a & b, subtract a - b, result negative
      //    6. one positive one no sign, this item smaller than arg:   swap a & b, subtract a - b, result negative
      if( (sign == 0 && bint.sign == 0) && numArray.length < bint.numArray.length){
        for(int i = 0; i < numArray.length; i++){
          if( numArray[i] > bint.numArray[i] ){
            borrow = 1;
            resultArray[i+1] -= 1;
            resultArray[i] += 1000000000;
          } else {
            borrow = 0;
          }
          resultArray[i] = bint.numArray[i] - numArray[i];
        }

        //To add longer array numbers in with borrow
        for(int i = numArray.length; i < bint.numArray.length; i++){
          resultArray[i] = bint.numArray[i];
        }

        //Loop to create resulting String
        for(int i = resultArray.length - 1; i >= 0; i--){
          result += df.format(resultArray[i]);
        }
        holdSign = 1;
      }

      //result = "";
      //    7. this no sign or positive, arg negative:                 remove neg from arg and call this.add( arg )
      if( sign == 0 && bint.sign == 1) {

        bint.sign = 0;
        return this.add(bint);
      }

      //result = "";
      //    8. this negative, arg positive:                            add negative to arg and call this.add( arg )
      if(  sign == 1 && bint.sign == 0 ){
        bint.sign = 1;
        this.add(bint);
      }

      //result = "";
      //    9. both signs negative, this larger abs than arg abs:      remove signs, subtract, add neg to result
      if(  (sign == 1 && bint.sign == 1) && Math.abs(numArray.length) > Math.abs(bint.numArray.length)){
        sign = 0;
        bint.sign = 0;

        for(int i = 0; i < bint.numArray.length; i++){
          if(numArray[i] < bint.numArray[i]){
            borrow = 1;
            resultArray[i+1] -= 1;
            resultArray[i] += 1000000000;
          } else {
            borrow = 0;
          }
          resultArray[i] = numArray[i] - bint.numArray[i];
        }

        //To add longer array numbers in with borrow
        for(int i = bint.numArray.length; i < numArray.length; i++){
          resultArray[i] = numArray[i];
        }

        //Loop to create resulting String
        for(int i = resultArray.length - 1; i >= 0; i--){
          result += df.format(resultArray[i]);
        }
        holdSign = 1;
      }

      //result = "";
      //   10. both signs negative, this smaller abs than arg abs:     remove signs, swap a & b, subtract, result pos
      if(  (sign == 1 && bint.sign == 1) && Math.abs(numArray.length) < Math.abs(bint.numArray.length)){
        sign = 0;
        bint.sign = 0;

        for(int i = 0; i < numArray.length; i++){
          if(numArray[i] > bint.numArray[i]){
            borrow = 1;
            resultArray[i+1] -= 1;
            resultArray[i] += 1000000000;
          } else {
            borrow = 0;
          }
          resultArray[i] = bint.numArray[i] - numArray[i];
        }

        //To add longer array numbers in with borrow
        for(int i = numArray.length; i < bint.numArray.length; i++){
          resultArray[i] = bint.numArray[i];
        }

        //Loop to create resulting String
        for(int i = resultArray.length - 1; i >= 0; i--){
          result += df.format(resultArray[i]);
        }
        holdSign = 1;
      }


      //result = "";
      //   11. both signs positive, equal chunks, this larger than bint:
      if(  (sign == 0 && bint.sign == 0) && ( numArray.length == bint.numArray.length && compareTo(bint) == 1) ){
        for(int i = 0; i < bint.numArray.length; i++){
          if(numArray[i] < bint.numArray[i]){
            borrow = 1;
            numArray[i + 1] -= 1;
            numArray[i] += 1000000000;
          } else {
            borrow = 0;
          }
          resultArray[i] = numArray[i] - bint.numArray[i];
        }

        //To add longer array numbers in with borrow
        for(int i = bint.numArray.length; i < numArray.length; i++){
          resultArray[i] = numArray[i];
        }

        //Loop to create resulting String
        for(int i = numArray.length - 1; i >= 0; i--){
          result += df.format(resultArray[i]);
        }
      }

      //result = "";
      //   12. both signs positive, equal chunks, this smaller than bint:
      if(  (sign == 0 && bint.sign == 0) && ( numArray.length == bint.numArray.length && compareTo(bint) == -1) ){
        for(int i = 0; i < numArray.length; i++){
          if( numArray[i] > bint.numArray[i] ){
            borrow = 1;
            resultArray[i+1] -= 1;
            resultArray[i] += 1000000000;
          } else {
            borrow = 0;
          }
          resultArray[i] = bint.numArray[i] - numArray[i];
        }

        //To add longer array numbers in with borrow
        for(int i = numArray.length; i < bint.numArray.length; i++){
          resultArray[i] = bint.numArray[i];
        }

        //Loop to create resulting String
        for(int i = resultArray.length - 1; i >= 0; i--){
          result += new String(Integer.valueOf(df.format(resultArray[i])).toString());
        }
        holdSign = 1;
      }


      //result = "";
      //   13. both signs positive, equal chunks, this equal to bint:
      if(  (sign == 0 && bint.sign == 0) && ( numArray.length == bint.numArray.length && compareTo(bint) == 0) ){
        for(int i = 0; i < numArray.length; i++){
          if(numArray[i] > bint.numArray[i]){
            borrow = 1;
            resultArray[i+1] -= 1;
            resultArray[i] += 1000000000;
          } else {
            borrow = 0;
          }
          resultArray[i] = bint.numArray[i] - numArray[i];
        }

        //Loop to create resulting String
        for(int i = resultArray.length - 1; i >= 0; i--){
          result += df.format(resultArray[i]);
        }
      }


      //   14. this pos, bint neg, equal chunks, this larger than bint:
      if(  (sign == 0 && bint.sign == 1) && ( numArray.length == bint.numArray.length && compareTo(bint) == 1) ){
        for(int i = 0; i < bint.numArray.length; i++){
          if(numArray[i] < bint.numArray[i]){
            borrow = 1;
            numArray[i + 1] -= 1;
            numArray[i] += 1000000000;
          } else {
            borrow = 0;
          }
          resultArray[i] = numArray[i] - bint.numArray[i];
        }

        //To add longer array numbers in with borrow
        for(int i = bint.numArray.length; i < numArray.length; i++){
          resultArray[i] = numArray[i];
        }

        //Loop to create resulting String
        for(int i = numArray.length - 1; i >= 0; i--){
          result += new String(Integer.valueOf(df.format(resultArray[i])).toString());
        }
      }


      //   15. this neg, bint neg, equal chunks, this larger than bint:
      if(  (sign == 1 && bint.sign == 1) && ( numArray.length == bint.numArray.length && compareTo(bint) == 1) ){
        for(int i = 0; i < numArray.length; i++){
          if( numArray[i] > bint.numArray[i] ){
            borrow = 1;
            //resultArray[i+1] -= 1;
            //resultArray[i] += 1000000000;
          } else {
            borrow = 0;
          }
          resultArray[i] = bint.numArray[i] - numArray[i];
        }

        //To add longer array numbers in with borrow
        for(int i = numArray.length; i < bint.numArray.length; i++){
          resultArray[i] = bint.numArray[i];
        }


        //Loop to create resulting String
        for(int i = resultArray.length - 1; i >= 0; i--){
          result += new String(Integer.valueOf(df.format(resultArray[i])).toString());
        }
        //holdSign = 1;

      }

      //   16. this neg, bint neg, equal chunks, this smaller than bint:
      if(  (sign == 1 && bint.sign == 1) && ( numArray.length == bint.numArray.length && compareTo(bint) == -1) ){
        for(int i = 0; i < bint.numArray.length; i++){
          if(numArray[i] < bint.numArray[i]){
            borrow = 1;
            //numArray[i + 1] -= 1;
            //numArray[i] += 1000000000;
          } else {
            borrow = 0;
          }
          resultArray[i] = bint.numArray[i] - numArray[i];
        }

        //To add longer array numbers in with borrow
        for(int i = bint.numArray.length; i < numArray.length; i++){
          resultArray[i] = numArray[i];
        }

        //Loop to create resulting String
        for(int i = numArray.length - 1; i >= 0; i--){
          result += new String(Integer.valueOf(df.format(resultArray[i])).toString());
        }
        //holdSign = 1;

      }


      //   17. this positive, bint neg, equal chunks, this smaller than bint:
      if(  (sign == 0 && bint.sign == 1) && ( numArray.length == bint.numArray.length && compareTo(bint) == 1) ){
        for(int i = 0; i < bint.numArray.length; i++){
          if(numArray[i] < bint.numArray[i]){
            borrow = 1;
            numArray[i + 1] -= 1;
            numArray[i] += 1000000000;
          } else {
            borrow = 0;
          }
          resultArray[i] = numArray[i] - bint.numArray[i];
        }

        //To add longer array numbers in with borrow
        for(int i = bint.numArray.length; i < numArray.length; i++){
          resultArray[i] = numArray[i];
        }

        //Loop to create resulting String
        for(int i = numArray.length - 1; i >= 0; i--){
          result += new String(Integer.valueOf(df.format(resultArray[i])).toString());
        }
      }

    BrobInt returnR = removeLeadingZeros(new BrobInt(result));
    if(holdSign == 1){
      returnR.sign = 1;
    }

    return returnR;
  }


  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    *  Method to multiply the value of a BrobIntk passed as argument to this BrobInt
    *  @param  bint         BrobInt to multiply this by
    *  @return BrobInt that is the product of the value of this BrobInt and the one passed in
    *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    public BrobInt multiply( BrobInt bint ) {

      //Repeated addition
      BrobInt sum = ZERO;
      BrobInt counter = ZERO;


      BrobInt bCopy = new BrobInt(bint.toString());
      BrobInt thisCopy = new BrobInt(this.toString());
      bCopy.sign = 0;



      while( counter.compareTo(bCopy) != 0 ){
        sum = sum.add(thisCopy);
        counter = counter.add(BrobInt.ONE);
      }


      return sum;
   }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  Method to divide the value of this BrobIntk by the BrobInt passed as argument
   *  @param  bint         BrobInt to divide this by
   *  @return BrobInt that is the dividend of this BrobInt divided by the one passed in
   *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public BrobInt divide( BrobInt bint ) {

    BrobInt d1 = new BrobInt(bint.toString());
    BrobInt d2 = new BrobInt(this.toString());
    BrobInt d3 = null;
    BrobInt q = ZERO;
    int n = d1.toString().length();

    // for implementation, in this discussion/comment/steps:
     //  0. divisor [passed in as ARGUMENT] is "d1"
     //     dividend [ME] is "d2"
     //     current dividend being handled is "d3"
     //     quotient is "q" and string length count is "n"
     //        for example, 56789 divided by 37: d1 == 37 and d2 == 56789
     //                     d3 starts with 56 and goes on adding single digits with each iteration
     //                     "q" starts at zero, and "n" starts at 2


    //  1. is d1 == 0?  if so, throw IllegalArgumentException
    //  IF ARGUMENT is equal to BrobInt.ZERO
    //     throw new IllegalArgumentException
    if( d1.equals( BrobInt.ZERO )) {
      throw new IllegalArgumentException("\n  Argument causes a divide-by-zero error\n\n" );
    }

    //  2. is d1 == d2 ? if so, return BrobInt.ONE
    if( d2.equals(d1) ) {
      return BrobInt.ONE;
    }

    //  3. is d1 > d2 ?  if so, return BrobInt.ZERO  [INTEGER ARITHMETIC!!!]
    if (d1.compareTo(d2) == 1){
      return BrobInt.ZERO;

    //  4. otherwise, get length of d1 and put into "n"
    }

   //  5. extract that many characters from the front of THIS and put into d3
   d3 = new BrobInt(d2.toString().substring(0,n));

   //  6. is d1 > d3?  if so, add one to "n" and
   if(d1.compareTo(d3) == 1){
    n++;
    //re-extract characters from THIS into d3
    d3 = new BrobInt(d2.toString().substring(0,n));
   }

  //  7. while "n" <= THIS.toString().length()
  while ( n <= d2.toString().length() ){
    while ( d3.compareTo(d1) > -1 ){
      // a. while d3 > BINT:
      //     i. subtract THIS from d3 [ gint.subtract( this ) ]

      d3 = d3.subtract(d1);

      //    ii. add one to quotient [ q.add( BrobInt.ONE ) ]
      q = q.add(BrobInt.ONE);
    }

    // b. d3 now has any remainder [e.g., 56 - 37 = 19, "q" is one and d3 is 19]

    // c. if "n++" is equal to d1.toString().length() then we are done -- break
    if ( n++ == d2.toString().length() ){
      break;
    }

    // d. multiply d3 by 10 using d3.multiply( BrobInt.TEN )
    d3 = d3.multiply( BrobInt.TEN);


    // e. multiply "q" by 10 using q.multiply( BrobInt.TEN )
    q = q.multiply( BrobInt.TEN );

    // f. extract next digit from d2 using d2.toString().substring( n-1, n )
    BrobInt newDigit = new BrobInt(d2.toString().substring(n-1, n));


    // g. add current value of d3 to extracted digit [e.g., get "7" from d2, concat to d3 to make "197"]
    d3 = d3.add(newDigit);
  }

  //  8. return "q" value which is already a BrobInt

  return q;
}


  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    *  Method to get the remainder of division of this BrobInt by the one passed as argument
    *  @param  bint         BrobInt to divide this one by
    *  @return BrobInt that is the remainder of division of this BrobInt by the one passed in
    *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    public BrobInt remainder( BrobInt bint ) {
      //146 - (146/23 * result (6))
      //Original number (divisor) - (division result * result)

      BrobInt result = BrobInt.ZERO;
      result = subtract(divide(bint).multiply(bint));

      return result;
    }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    *  Method to compare a BrobInt passed as argument to this BrobInt
    *  @param  bint  BrobInt to add to this
    *  @return int   that is one of neg/0/pos if this BrobInt precedes/equals/follows the argument
    *  NOTE: this method does not do a lexicographical comparison using the java String "compareTo()" method
    *        It takes into account the length of the two numbers, and if that isn't enough it does a
    *        character by character comparison to determine
    *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    public int compareTo( BrobInt bint ) {

      // remove any leading zeros because we will compare lengths
      String me  = removeLeadingZeros( this ).toString();

      String arg = removeLeadingZeros( bint ).toString();

      // handle the signs here
       if( 1 == sign && 0 == bint.sign ) {
          return -1;
       } else if( 0 == sign && 1 == bint.sign ) {
          return 1;
       } else if( 0 == sign && 0 == bint.sign ) {
         // the signs are the same at this point ~ both positive
         // check the length and return the appropriate value
         //   1 means this is longer than bint, hence larger positive
         //  -1 means bint is longer than this, hence larger positive
          if( me.length() != arg.length() ) {
             return (me.length() > arg.length()) ? 1 : -1;
          }
       } else {
         // the signs are the same at this point ~ both negative
          if( me.length() != arg.length() ) {
             return (me.length() > arg.length()) ? -1 : 1;
          }
       }

      // otherwise, they are the same length, so compare absolute values
       for( int i = 0; i < me.length(); i++ ) {
          Character a = Character.valueOf( me.charAt(i) );
          Character b = Character.valueOf( arg.charAt(i) );
          if( Character.valueOf(a).compareTo( Character.valueOf(b) ) > 0 ) {
             return 1;
          } else if( Character.valueOf(a).compareTo( Character.valueOf(b) ) < 0 ) {
             return (-1);
          }
       }
       return 0;
   }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    *  Method to check if a BrobInt passed as argument is equal to this BrobInt
    *  @param  bint     BrobInt to compare to this
    *  @return boolean  that is true if they are equal and false otherwise
    *  NOTE: this method performs a similar lexicographical comparison as the "compareTo()" using the
    *        java String "equals()" method -- THAT was easy..........
    *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    public boolean equals( BrobInt bint ) {
      return ( (sign == bint.sign) && (this.toString().equals( bint.toString() )) );
    }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    *  Method to return a BrobInt given a long value passed as argument
    *  @param  value    long type number to make into a BrobInt
    *  @return BrobInt  which is the BrobInt representation of the long
    *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    public static BrobInt valueOf( long value ) throws NumberFormatException {
      BrobInt bi = null;
      try { bi = new BrobInt( Long.valueOf( value ).toString() ); }
      catch( NumberFormatException nfe ) { throw new NumberFormatException( "\n  Sorry, the value must be numeric of type long." ); }
      return bi;
    }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    *  Method to return a String representation of this BrobInt
    *  @return String  which is the String representation of this BrobInt
    *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    public String toString() {
      String returnString = "";
      if(sign == 1){
        returnString = "-";
      }
      return returnString + internalValue;
    }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  Method to remove leading zeros from a BrobInt passed as argument
   *  @param  gint     BrobInt to remove zeros from
   *  @return BrobInt that is the argument BrobInt with leading zeros removed
   *  Note that the sign is preserved if it exists
   *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    public BrobInt removeLeadingZeros( BrobInt bint ) {
      Character sign = null;
      String returnString = bint.toString();
      int index = 0;

      if( allZeroDetect( bint ) ) {
        return ZERO;
      }
      if( ('-' == returnString.charAt( index )) || ('+' == returnString.charAt( index )) ) {
        sign = returnString.charAt( index );
        index++;
      }
      if( returnString.charAt( index ) != '0' ) {
        return bint;
      }

      while( returnString.charAt( index ) == '0' ) {
        index++;
      }
      returnString = bint.toString().substring( index, bint.toString().length() );
      // if( sign != null ) {
      //   returnString = sign.toString() + returnString;
      // }
      return new BrobInt( returnString );
    }
  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    *  Method to return a boolean if a BrobInt is all zeros
    *  @param  gint     BrobInt to compare to this
    *  @return boolean  that is true if the BrobInt passed is all zeros, false otherwise
    *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    public boolean allZeroDetect( BrobInt bint ) {
      for( int i = 0; i < bint.toString().length(); i++ ) {
        if( bint.toString().charAt(i) != '0' ) {
          return false;
        }
      }
      return true;
    }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    *  Method to display an Array representation of this BrobInt as its bytes
    *  @param   d  int array from which to display the contents
    *  NOTE: may be changed to int[] or some other type based on requirements in code above
    *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    public void toArray( int[] d ) {
      System.out.println( "Array contents: " + Arrays.toString( d ) );
    }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    *  Method to display a prompt for the user to press 'ENTER' and wait for her to do so
    *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    public void pressEnter() {
      String inputLine = null;
      try {
        System.out.print( "      [Press 'ENTER' to continue]: >> " );
        inputLine = input.readLine();
      }
      catch( IOException ioe ) {
        System.out.println( "Caught IOException" );
      }
    }


  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    *  the main method redirects the user to the test class
    *  @param  args  String array which contains command line arguments
    *  NOTE:  we don't really care about these, since we test the BrobInt class with the BrobIntTester
    *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    public static void main( String[] args ) {
      System.out.println( "\n  Hello, world, from the BrobInt program!!\n" );
       System.out.println( "\n   You should run your tests from the BrobIntTester...\n" );

       BrobInt b1 = null;
       BrobInt b2 = null;
       try { System.out.println( "   Making a new BrobInt: " ); b1 = new BrobInt( "147258369789456123" ); }
       catch( Exception e ) { System.out.println( "        Exception thrown:  " ); }
       try { System.out.println( "   expecting: 147258369789456123\n     and got: " + b1.toString() ); }
       catch( Exception e ) { System.out.println( "        Exception thrown:  " ); }
       System.out.println( "\n    Multiplying 82832833 by 3: " );
       try { System.out.println( "      expecting: 248498499\n        and got: " + new BrobInt("82832833").multiply( BrobInt.THREE ) ); }
       catch( Exception e ) { System.out.println( "        Exception thrown:  " + e.toString() ); }

       try { System.out.println( "   Making a new BrobInt: " ); b1 = new BrobInt( "10" ); }
       catch( Exception e ) { System.out.println( "        Exception thrown:  " ); }
       try { System.out.println( "   expecting: 10\n     and got: " + b1.toString() ); }
       catch( Exception e ) { System.out.println( "        Exception thrown:  " ); }
       System.out.println( "\n    Multiplying 10 by 3: " );
       try { System.out.println( "      expecting: 30\n        and got: " + new BrobInt("10").multiply( BrobInt.THREE ) ); }
       catch( Exception e ) { System.out.println( "        Exception thrown:  " + e.toString() ); }


       try { System.out.println( "   Making a new BrobInt: " ); b1 = new BrobInt( "10" ); }
       catch( Exception e ) { System.out.println( "        Exception thrown:  " ); }
       try { System.out.println( "   expecting: 10\n     and got: " + b1.toString() ); }
       catch( Exception e ) { System.out.println( "        Exception thrown:  " ); }
       System.out.println( "\n    Dividing 50 by 10: " );
       try { System.out.println( "      expecting: 5\n        and got: " + new BrobInt("50").divide(TEN) ); }
       catch( Exception e ) { System.out.println( "        Exception thrown:  " + e.toString() ); }

    }
}
