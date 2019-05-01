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

   public int total = 0;
   public static int rowCount = 0;
   public static int columnCount = 0;
   public static String[] splitArgs = null;
   public static int[] denominations = null;
   public static int copyRow = 0;
   public static int copyColumn = 0;




  /**
   *  Constructor goes here
   */
   public DynamicChangeMaker() {

   }


  /**
   *  Method to handle all the input arguments from the command line
   *
   */
  public static void handleInitialArguments( String args[] ) {

    System.out.println( "\n   Hello world, from the DynamicChangeMaker Program!!\n\n" ) ;
    if( 0 == args.length || 1 == args.length) {
      System.out.println( "   Sorry you must enter arguments\n" +
                              "   Usage: java DynamicChangeMaker <denom1,denom2,denom3> [total] \n" +
                              "   Please try again..........." );
      System.exit( 1 );
    }


    try {
      //NON VALID ARGS : duplicate denominations, negative denominations, numbers not letters
      //convert String[] of denominations (ex: 5,2,4,8) to int[] using .split();

      String[] splitArgs = args[0].split(",");
      int[] denominations = new int[splitArgs.length];

      //Checking numbers not letters
      for (int i = 0; i < splitArgs.length; i++) {
        denominations[i] = Integer.parseInt(splitArgs[i]);
      }

      //Checking for negative denominations
      for (int i = 0; i < denominations.length; i++) {
        if (denominations[i] <= 0){
          System.out.println( "   Sorry you must enter positive or non-zero arguments " );
          System.exit( 1 );
        }
      }

      //Checking for duplicates
      for (int i = 0; i < denominations.length; i++) {
        for(int j = i + 1; j < denominations.length; i++ ){
          if ( denominations[i] == denominations[j]){
            System.out.println("  Sorry you must only enter one type of each denomination" );
            System.exit( 1 );
          }
        }
      }


      //NON VALID ARGS : negative, numbers not letters
      int total = Integer.parseInt(args[1]);
      //Negative total
      if( total <= 0  ){
        System.out.println( "   Sorry you must enter a non-zero positive total " );
        System.exit( 1 );
      }


    }
    catch( NumberFormatException pse ){
      System.out.println( "Caught NumberFormat Exception, BAD DATA" );
      System.exit(1);
    }


  }


  /**
   *  Method to make optimal change out of coins given
   *  @param  coins the integer array of denominations
   *  @param  target the integer that is the target number of the denomination combinations
   *  @return tuple that is the optimal combination of denominations
   */
  public static Tuple MakeChangeWithDynamicProgramming ( int[] coins, int target ) {

    rowCount = denominations.length;
    columnCount = target + 1;
    Tuple[][] table = new Tuple[rowCount][columnCount];

    // this is just to locate you in the process -- I know YOU know this belongs here...
     //  BTW, the indenting implies the if statements needed

      for( int row = 0; row < rowCount; row ++ ) {

        int curDenom = denominations[row];

        for( int column = 0; column <= columnCount; column++ ) {

          // Special case for column zero for all rows
          if( column == 0 ) {
            //PUT {0,0}
            table[row][0] = new Tuple(denominations.length);

          // Otherwise, this is NOT column zero
          } else {

            // check to see if we CAN take ONE thing out of the current value;
            //  if we CAN'T take one of the denominations out of the value of "j"
            //   impossible, at least temporarily
            if( curDenom <= column ) {
              table[row][column] = new Tuple(denominations.length);
              table[row][column].setElement(1, curDenom);
            } else {
              table[row][column] = Tuple.IMPOSSIBLE;
            }

            // look backward to see if there is a valid/impossible solution
            //  if there is, copy it over and add/replace the one that is there
            if( column - curDenom < 0 ) {
              //If backwards cell is IMPOSSIBLE
              if( table[row][column - curDenom] == Tuple.IMPOSSIBLE )  {
                table[row][column] = Tuple.IMPOSSIBLE;
              } else {
                // if the cell looking backward is NOT an "IMPOSSIBLE", add it
                table[row][column] = table[row][column].add(table[row][column - curDenom]);
              }


              // if this is NOT row zero we need to look above to see if there is
              //  a better/non-impossible solution; if so, copy it down
              if( row != 0 ) {
                // if the cell above is impossible, basically do nothing since
                //  this the current cell is already IMPOSSIBLE
                if ( table[row + 1][column] == Tuple.IMPOSSIBLE ){
                  //do nothing --- table[row][column] == Tuple.IMPOSSIBLE

                  // else if the cell above has a total that is less than the current
                  //  cell, copy it down
                } else if ( table[row + 1][column].total() < table[row][column].total() ) {
                  table[row][column] = table[row + 1][column];
                }
              }

            // ELSE -- we *CAN* take one current denomination out
            } else {
              // make a new tuple with a one in the current demonimation index
              table[row][column] = new Tuple(denominations.length);
              table[row][column].setElement(1, curDenom);

              // look backward to see if there is a valid/impossible solution
                  if( (column - denominations[row]) >= 0 ) {
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
                    if ( table[row + 1][column] == Tuple.IMPOSSIBLE ){
                      //do nothing --- table[row][column] == Tuple.IMPOSSIBLE

                      // else if the cell above has a total that is less than the current
                      //  cell, copy it down
                    } else if ( table[row + 1][column].total() < table[row][column].total() )
                        table[row][column] = table[row + 1][column];
                        copyRow = row;
                        copyColumn = column;
                  }
            }

          }
        }
      }

    return new Tuple( table[copyRow][copyColumn] );
  }



  /**
   *  The main program starts here
   */
  public static void main( String args[] ) {

    handleInitialArguments(args);
    DynamicChangeMaker d = new DynamicChangeMaker();

    //table = new Tuple [rows][columns];

  }



}
