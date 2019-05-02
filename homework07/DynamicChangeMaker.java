/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * File name  :  DynamicChangeMaker.java
 * Purpose    :
 * @author    :  Mallory Benna
 * Date       :  2019-04-28
 * Description:
 * Notes      :  None
 * Warnings   :  None
 *
 *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Revision History
 * ================
 *   Ver      Date     Modified by:  Reason for change or modification
 *  -----  ----------  -------------  ---------------------------------------------------------------------
 *  1.0.0  2017-04-19  Mallory Benna  Initial release
 *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */


public class DynamicChangeMaker {
  /**
   *  Class field definintions go here
   */

   public static int total = 0;
   public static int rowCount = 0;
   public static int columnCount = 0;
   public static String[] splitArgs = null;
   public int[] denominations = null;
   public static int copyRow = 0;
   public static int copyColumn = 0;
   public static boolean DEBUG_ON = false;


  /**
   *  Constructor goes here
   */
   public DynamicChangeMaker() {
   }


  /**
   *  Method to handle all the input arguments from the command line
   *
   */
  public String handleInitialArguments( String args[] ) {

    System.out.println( "\n   Hello world, from the DynamicChangeMaker Program!!\n\n" ) ;
    if( 2 != args.length) {
      System.out.println( " BAD DATA: Sorry you must enter arguments\n" +
                              "   Usage: java DynamicChangeMaker <denom1,denom2,denom3> [total] \n" +
                              "   Please try again..........." );
      return "BAD DATA";

    }


    try {
      //NON VALID ARGS : duplicate denominations, negative denominations, numbers not letters
      //convert String[] of denominations (ex: 5,2,4,8) to int[] using .split();

      splitArgs = args[0].split(",");
      denominations = new int[splitArgs.length];

      //Checking numbers not letters
      for (int i = 0; i < splitArgs.length; i++) {
        denominations[i] = Integer.parseInt(splitArgs[i]);
      }

      //Checking for negative denominations
      for (int i = 0; i < denominations.length; i++) {
        if (denominations[i] <= 0){
          System.out.println( " BAD DATA: Sorry you must enter positive or non-zero arguments " );
          return "BAD DATA";

        }
      }

      //Checking for duplicates
      for (int i = 0; i < denominations.length - 1; i++) {
        for(int j = i + 1; j < denominations.length - 2; j++ ){
          if ( denominations[i] == denominations[j]){
            System.out.println(" BAD DATA: Sorry you must only enter one type of each denomination" );
            return "BAD DATA";

          }
        }
      }

      //NON VALID ARGS : negative, numbers not letters
      total = Integer.parseInt(args[1]);
      //Negative total
      if( total <= 0  ){
        System.out.println( " BAD DATA: Sorry you must enter a non-zero positive total " );
        return "BAD DATA";

      }

    }
    catch( NumberFormatException nfe ){
      System.out.println( "Caught NumberFormat Exception" );
      return "BAD DATA";
    }
    return "TRUE";
  }


  /**
   *  Method to make optimal change out of coins given
   *  @param  denominations the integer array of denominations
   *  @param  total the integer that is the target number of the denomination combinations
   *  @return tuple that is the optimal combination of denominations to reach total
   */
  public static Tuple makeChangeWithDynamicProgramming ( int[] denominations, int total ) {
    rowCount = denominations.length;
    columnCount = total + 1;
    Tuple[][] table = new Tuple[rowCount][columnCount];

    // this is just to locate you in the process -- I know YOU know this belongs here...
     //  BTW, the indenting implies the if statements needed
      for( int row = 0; row < rowCount; row ++ ) {
        int curDenom = denominations[row];
        for( int column = 0; column < columnCount; column++ ) {
          // Special case for column zero for all rows
          if( column == 0 ) {
            //PUT {0,0}
            table[row][0] = new Tuple(denominations.length);

          // Otherwise, this is NOT column zero
          } else {
            // check to see if we CAN take ONE thing out of the current value;
            //  if we CAN'T take one of the denominations out of the value of "j"
            //   impossible, at least temporarily
            if( curDenom > column ) {
              table[row][column] = Tuple.IMPOSSIBLE;

              // if this is NOT row zero we need to look above to see if there is
              //  a better/non-impossible solution; if so, copy it down
              if( row != 0 ) {
                // if the cell above is impossible, basically do nothing since
                //  this the current cell is already IMPOSSIBLE
                if ( table[row - 1][column] == Tuple.IMPOSSIBLE ){
                  table[row][column] = table[row][column];

                  // else if the cell above has a total that is less than the current
                  //  cell, copy it down
                } else if ( table[row - 1][column].total() < table[row][column].total() ) {
                    table[row][column] = table[row - 1][column];

                  //else if the cell above is not impossible and our row is impossible
                } else if ( table[row - 1][column] != Tuple.IMPOSSIBLE && table[row][column] == Tuple.IMPOSSIBLE){
                    table[row][column] = table[row - 1][column];

                  //else if the cell above is greater than the current cell, our row stays
                } else if ( table[row - 1][column].total() > table[row][column].total() ){
                    table[row][column] = table[row][column];
                }

              }

            // ELSE -- we *CAN* take one current denomination out
            } else {
              // make a new tuple with a one in the current demonimation index
              table[row][column] = new Tuple(denominations.length);
              table[row][column].setElement(row, 1);

              // look backward to see if there is a valid/impossible solution
                  if( column - curDenom >= 0 ) {
                    // if it's IMPOSSIBLE, mark the current cell IMPOSSIBLE, too
                    if ( table[row][column - curDenom] == Tuple.IMPOSSIBLE  ){
                      table[row][column] = Tuple.IMPOSSIBLE;
                    } else {
                      // else, add the previous cell to the current cell
                      table[row][column] = table[row][column].add(table[row][column - curDenom]);
                    }
                  }


                 // if this is NOT row zero we need to look above to see if there is
                 //  a better/non-impossible solution; if so, copy it down
                  if( row != 0 ) {
                    // if the cell above is impossible, basically do nothing since
                    //  this the current cell is already IMPOSSIBLE
                    if ( table[row - 1][column] == Tuple.IMPOSSIBLE ){
                      table[row][column] = table[row][column];

                      // else if the cell above has a total that is less than the current
                      //  cell, copy it down
                    } else if ( table[row - 1][column].total() < table[row][column].total() ) {
                        table[row][column] = table[row - 1][column];

                      //else if the cell above is not impossible and our row is impossible
                    } else if ( table[row - 1][column] != Tuple.IMPOSSIBLE && table[row][column] == Tuple.IMPOSSIBLE){
                        table[row][column] = table[row - 1][column];

                      //else if the cell above is greater than the current cell, our row stays
                    } else if ( table[row - 1][column].total() > table[row][column].total() ){
                        table[row][column] = table[row][column];
                    }
                  }
            }

            if(DEBUG_ON){
              System.out.println(" CURDENOM: " + curDenom);
              System.out.println(" ROW: " + row);
              System.out.println(" COLUMN: " + column);
              System.out.println(" TUPLE: " + table[row][column]);
              System.out.println(" --------  " );
            }

          }
          copyRow = row;
          copyColumn = column;

        }

      }

      if(DEBUG_ON){
        System.out.println(" FINAL TUPLE:  " + table[copyRow][copyColumn]);
        System.out.println(" --------  " );
      }

    return table[copyRow][copyColumn];
  }



  /**
   *  The main program starts here
   */
  public static void main( String args[] ) {


    DynamicChangeMaker d = new DynamicChangeMaker();
    d.handleInitialArguments(args);

    Tuple t = d.makeChangeWithDynamicProgramming(d.denominations, total);

    if( t.total() > 0 ){
      System.out.println("The following coins make ---- ");
      for(int i = 0; i < t.length(); i++){
        System.out.println( t.getElement(i) + " x " + d.denominations[i] + " cent coins ");
      }

      System.out.println("Which is " + t.total() + " coins");
    } else {
      System.out.println("Total with given denominations is impossible");
    }

  }



}
