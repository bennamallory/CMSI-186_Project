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
 *  1.0.0  2017-04-04  B.J. Johnson   Initial writing and begin coding
 *  1.1.0  2017-04-13  B.J. Johnson   Completed addByt, addInt, compareTo, equals, toString, Constructor,
 *                                      validateDigits, two reversers, and valueOf methods; revamped equals
 *                                      and compareTo methods to use the Java String methods; ready to
 *                                      start work on subtractByte and subtractInt methods
 *  1.2.0  2019-04-06  Mallory Benna   Methods filled in for functioning program
 *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
import java.util.Arrays;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

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
   private String reversed      = "";        // the backwards version of the internal String representation

   //Other variables
   public static int chunks = 0;
   public static int[] numArray = null;
   public static int brobPiece = 0;
   public static int[] resultArray = null;
   public static int carry = 0;
   public static int borrow = 0;
   public static int resultArraySpace = 0;
   public static String result = "";
   public static final int MAX_VALUE = 999999999;


   public static final boolean DEBUG_ON = true;


   private static BufferedReader input = new BufferedReader( new InputStreamReader( System.in ) );

  /**
    *  Constructor takes a string and assigns it to the internal storage, checks for a sign character
    *   and handles that accordingly;  it then checks to see if it's all valid digits, and reverses it
    *   for later use
    *  @param  value  String value to make into a BrobInt
    */
    public BrobInt( String value ) {

     internalValue = value;

     //Checking for sign value and removing extra sign if neccessary
      if(value.charAt(0) == '-'){
        sign = 1;
        //Remove sign
        internalValue.replace("-", "");

      } else if (value.charAt(0) == '+'){
        sign = 0;
        //Remove sign
        internalValue.replace("+", "");
      }



      //Finding out how many chunks of the number I have
      chunks = internalValue.length()/9;
      if(internalValue.length() % 9 != 0){
        chunks++;
      }

      //Creating a new array with enough space for the chunks
      numArray = new int[chunks];

      int stop = internalValue.length()-1;
      int start = internalValue.length() - 10;


      //Starting at the end of the BrobInt and parsing every integer into an array
      for(int i = 0; i < chunks; i++){
        //Adds numbers into array back into its original form, lowest number at index zero
        numArray[i] = Integer.parseInt(internalValue.substring(start, stop));
        if(i == numArray.length){
          start = 0;
        } else{
          start -= 9;
        }
        stop -= 9;
      }

      toArray(numArray);

    }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    *  Method to validate that all the characters in the value are valid decimal digits
    *  @return  boolean  true if all digits are good
    *  @throws  IllegalArgumentException if something is hinky
    *  note that there is no return false, because of throwing the exception
    *  note also that this must check for the '+' and '-' sign digits
    *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    public boolean validateDigits() throws IllegalArgumentException {
      throw new UnsupportedOperationException( "\n         Sorry, that operation is not yet implemented." );
    }


  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    *  Method to add the value of a BrobInt passed as argument to this BrobInt using byte array
    *  @param  bint         BrobInt to add to this
    *  @return BrobInt that is the sum of the value of this BrobInt and the one passed in
    *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    public BrobInt add( BrobInt bint ) {

      //Creating a new resultArray with enough space for the resulting number
      if(numArray.length > bint.numArray.length){
        resultArraySpace = chunks + 1;
        resultArray = new int[resultArraySpace];
      } else {
        resultArraySpace = bint.chunks + 1;
        resultArray = new int[resultArraySpace];
      }

      //If both signs positive (us bigger than bint) - add
      if( (sign == 0 && bint.sign == 0) && numArray.length > bint.numArray.length){
        //Adding arrays until the shortest one is done
        for(int i = 0; i < bint.numArray.length; i++){
          resultArray[i] = numArray[i] + bint.numArray[i];
          if(resultArray[i] >= 999999999 ){
            carry = 1;
            resultArray[i] -= 1000000000;
            numArray[i + 1] += 1;
          } else {
            carry = 0;
          }
        }

        //Adding rest of the larger array starting from the end of the shorter array
        for(int i = bint.numArray.length; i < numArray.length; i++){
          resultArray[i] = numArray[i];
          if(resultArray[i] >= 999999999 ){
            carry = 1;
            resultArray[i] -= 1000000000;
            numArray[i + 1] += 1;
          } else {
            carry = 0;
          }
        }

        //Making int array into a string to return to create new BrobInt
        //loop from length of the array down to i
        for(int i = resultArray.length; i <= 0; i--){
          result += new String(new Integer(numArray[i]).toString());
        }
      }


      //If both signs are positive (bint bigger than us - only loop to size of us first) -- add
      if((sign == 0 && bint.sign == 0) && numArray.length < bint.numArray.length){
        //Add - make sure to only loop first to smallest array

        //Adding arrays until the shortest one is done
        for(int i = 0; i < numArray.length; i++){
          resultArray[i] = numArray[i] + bint.numArray[i];
          if(resultArray[i] >= 999999999 ){
            carry = 1;
            resultArray[i] -= 1000000000;
            numArray[i + 1] += 1;
          } else {
            carry = 0;
          }
        }

        //Adding rest of the larger array starting from the end of the shorter array
        for(int i = numArray.length; i < bint.numArray.length; i++){
          resultArray[i] = bint.numArray[i];
          if(resultArray[i] >= 999999999 ){
            carry = 1;
            resultArray[i] -= 1000000000;
            numArray[i + 1] += 1;
          } else {
            carry = 0;
          }
        }

        //Making int array into a string to return to create new BrobInt
        //loop from length of the array down to i
        for(int i = resultArray.length; i <= 0; i--){
          result += new String(new Integer(numArray[i]).toString());
        }

      }

      //If both signs are negative (us bigger than bint) -- add, make result negative
      if((sign == 1 && bint.sign == 1) && numArray.length > bint.numArray.length){
        //Add - make sure to only loop to bint size first (make result neg)

        //Adding arrays until the shortest one is done
        for(int i = 0; i < bint.numArray.length; i++){
          resultArray[i] = numArray[i] + bint.numArray[i];
          if(resultArray[i] >= 999999999 ){
            carry = 1;
            resultArray[i] -= 1000000000;
            numArray[i + 1] += 1;
          } else {
            carry = 0;
          }

        }

        //Adding rest of the larger array starting from the end of the shorter array
        for(int i = bint.numArray.length; i < numArray.length; i++){
          resultArray[i] = numArray[i] + carry;
          if(resultArray[i] >= 999999999 ){
            carry = 1;
            resultArray[i] -= 1000000000;
            numArray[i + 1] += 1;
          } else {
            carry = 0;
          }
        }

        //Loop to create resulting String
        //Making int array into a string to return to create new BrobInt
        //loop from length of the array down to i
        for(int i = resultArray.length; i <= 0; i--){
          result += new String(new Integer(numArray[i]).toString());
        }
        result = "-" + result;
      }

      //If both signs are negative (bint bigger than  us) -- add, make result negative
      if((sign == 1 && bint.sign == 1) && numArray.length < bint.numArray.length){
        //Add - make sure to only loop first to smallest array (make result negative)

        //Adding arrays until the shortest one is done
        for(int i = 0; i < numArray.length; i++){
          resultArray[i] = numArray[i] + bint.numArray[i];
          if(resultArray[i] >= 999999999 ){
            carry = 1;
            resultArray[i] -= 1000000000;
            numArray[i + 1] += 1;
          } else {
            carry = 0;
          }

        }

        //Adding rest of the larger array starting from the end of the shorter array
        for(int i = numArray.length; i < bint.numArray.length; i++){
          resultArray[i] = bint.numArray[i];
          if(resultArray[i] >= 999999999 ){
            carry = 1;
            resultArray[i] -= 1000000000;
            numArray[i + 1] += 1;
          } else {
            carry = 0;
          }
        }

        //Loop to create resulting String
        for(int i = resultArray.length; i <= 0; i--){
          result += new String(new Integer(numArray[i]).toString());
        }
        result = "-" + result;
      }

      //If us is neg and bint is pos (us bigger than bint ) -- subtract
      if((sign == 1 && bint.sign == 0) && numArray.length > bint.numArray.length){
        this.subtract(bint);
      }

      //If us is neg and bint is pos (bint bigger than us)  -- swap and subtract
      if((sign == 1 && bint.sign == 0) && numArray.length < bint.numArray.length){
        this.subtract(bint);
      }


      //If bint is neg and us is pos (us bigger than bint) -- subtract
      if((sign == 0 && bint.sign == 1) && numArray.length > bint.numArray.length){
        this.subtract(bint);
      }

      //If bint is neg and us is pos (bint bigger than us) -- swap and subtract, add neg to result
      if((sign == 0 && bint.sign == 1) && numArray.length < bint.numArray.length){
        this.subtract(bint);
      }

      return new BrobInt(result);
    }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    *  Method to subtract the value of a BrobIntk passed as argument to this BrobInt using bytes
    *  @param  bint         BrobInt to subtract from this
    *  @return BrobInt that is the difference of the value of this BrobInt and the one passed in
    *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    public BrobInt subtract( BrobInt bint ) {
      // set up for subtraction sign handling to decide what to do based on the following cases:

      //Creating a new resultArray with enough space for the resulting number
      if(numArray.length > bint.numArray.length){
        resultArraySpace = chunks + 1;
        resultArray = new int[resultArraySpace];
      } else {
        resultArraySpace = bint.chunks + 1;
        resultArray = new int[resultArraySpace];
      }


      //    1. no signs at all, this item larger than argument:        simple subtraction a - b
      //    2. both signs positive, this item larger than argument:    simple subtraction a - b
      //    3. one positive one no sign, this item larger than arg:    simple subtraction a - b
        if( (sign == 0 && bint.sign == 0) && numArray.length > bint.numArray.length){
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
          for(int i = resultArray.length; i <= 0; i--){
            result += new String(new Integer(numArray[i]).toString());
          }

        }



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
        for(int i = resultArray.length; i <= 0; i--){
          result += new String(new Integer(numArray[i]).toString());
        }

      }

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
        for(int i = resultArray.length; i <= 0; i--){
          result += new String(new Integer(numArray[i]).toString());
        }

        result = "-" + result;

      }

      //    7. this no sign or positive, arg negative:                 remove neg from arg and call this.add( arg )
      if( sign == 0 && bint.sign == 1) {
        bint.sign = 0;
        this.add(bint);
      }

      //    8. this negative, arg positive:                            add negative to arg and call this.add( arg )
      if(  sign == 1 && bint.sign == 0 ){
        bint.sign = 1;
        this.add(bint);
      }


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
        for(int i = resultArray.length; i <= 0; i--){
          result += new String(new Integer(numArray[i]).toString());
        }
        result = "-" + result;
      }


      //   10. both signs negative, this smaller abs than arg abs:     remove signs, swap a & b, subtract, result pos
      if(  (sign == 1 && bint.sign ==1) && Math.abs(numArray.length) < Math.abs(bint.numArray.length)){
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
        for(int i = resultArray.length; i <= 0; i--){
          result += new String(new Integer(numArray[i]).toString());
        }
      }


    return new BrobInt(result);
   }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  Method to multiply the value of a BrobIntk passed as argument to this BrobInt
   *  @param  bint         BrobInt to multiply this by
   *  @return BrobInt that is the product of the value of this BrobInt and the one passed in
   *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public BrobInt multiply( BrobInt bint ) {
      //Russian Peasant Multiplication
      //First number (divide by two, put into an array, stop when num == 1)
      //Second number (multiply by two, put into an array, stop at same index that first digit equals 1)
      //If first column number is even, add same index of second number to resulting numbers

      // //Repeated addition
      // for(int i = 0;  i < /*bint number of times */; i++){
      //   //add (this) to itself
      // }

      //Loop to create resulting String
      for(int i = resultArray.length; i <= 0; i--){
        result += new String(new Integer(numArray[i]).toString());
      }

      return new BrobInt(result);
   }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   *  Method to divide the value of this BrobIntk by the BrobInt passed as argument
   *  @param  bint         BrobInt to divide this by
   *  @return BrobInt that is the dividend of this BrobInt divided by the one passed in
   *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
   public BrobInt divide( BrobInt bint ) {
      //Repeated subtraction
      // for(int i = 0;  i < /*bint number of times */; i++){
      //   //subtract (this) from itself
      // }

      //Loop to create resulting String
      for(int i = resultArray.length; i <= 0; i--){
        result += new String(new Integer(numArray[i]).toString());
      }

      return new BrobInt(result);
   }

  /** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    *  Method to get the remainder of division of this BrobInt by the one passed as argument
    *  @param  bint         BrobInt to divide this one by
    *  @return BrobInt that is the remainder of division of this BrobInt by the one passed in
    *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */
    public BrobInt remainder( BrobInt bint ) {
      throw new UnsupportedOperationException( "\n         Sorry, that operation is not yet implemented." );
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

     // handle the signs here
      if( 1 == sign && 0 == bint.sign ) {
        return -1;
      } else if( 0 == sign && 1 == bint.sign ) {
        return 1;
      }

     // the signs are the same at this point
     // remove any leading zeros because we will compare lengths
     // String me  = removeLeadingZeros( this ).toString();
      String me  = removeLeadingZeros( new BrobInt( internalValue ) ).toString();
      String arg = removeLeadingZeros( bint ).toString();

     // check the length and return the appropriate value
     //   1 means this is longer than bint, hence larger
     //  -1 means bint is longer than this, hence larger
      if( me.length() > arg.length() ) {
        return 1;
      } else if( me.length() < arg.length() ) {
          return (-1);

     // otherwise, they are the same length, so compare absolute values
      } else {
          for( int i = 0; i < me.length(); i++ ) {
            Character a = Character.valueOf( me.charAt(i) );
            Character b = Character.valueOf( arg.charAt(i) );
            if( Character.valueOf(a).compareTo( Character.valueOf(b) ) > 0 ) {
              return 1;
            } else if( Character.valueOf(a).compareTo( Character.valueOf(b) ) < 0 ) {
                return (-1);
            }
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
      return (internalValue.equals( bint.toString() ));
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
      if(sign == 1){
        return "-" + internalValue;
      }

      return internalValue;
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
        return bint;
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
      if( sign != null ) {
        returnString = sign.toString() + returnString;
      }
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

      System.exit( 0 );
    }

}
