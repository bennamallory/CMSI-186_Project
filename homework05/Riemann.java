/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  File name     :  Riemann.java
 *  Purpose       :  The main program for the Riemann class
 *
 *  @author       :  Mallory Benna
 *  Date written  :  2019-03-21
 *  Description   :  This class provides means to calculate the area under various curves
 *
 *  Notes         :  None
 *  Warnings      :  None
 *  Exceptions    :  IllegalArgumentException when the input arguments are "hinky"
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  Revision History
 *  ---------------
 *            Rev      Date     Modified by:  Reason for change/modification
 *           -----  ----------  ------------  -----------------------------------------------------------
 *  @version 1.0.0  2019-02-26  Mallory Benna  Initial writing and release
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */


public class Riemann {
  /**
   *  Class field definitions go here
   */

  public static double percentage = 0.0;
  public static double lowerBound = 0.0;
  public static double upperBound = 0.0;
  public static double[] coeff = null;
  public static double coeffiecients = 0.0;
  public static double previous = 0.0;
  public static double current = 0.0;
  public int coeffLength = 0;
  public static int q = 0;
  public static double area = 0;

  /**
   *  Constructor
   *
   */
  public Riemann() {

  }


   /**
    *  Method to handle all the input arguments from the command line
    *   this sets up the variables for the simulation
    */
  public boolean validate( String args[] ) {

    System.out.println( "\n   Hello world, from the Riemann program!!\n\n" );


    if( 0 == args.length || (1 == args.length && !args[0].equals("runtests")) || (args[0].equals("poly") && (3 == args.length || 2 == args.length))) {
      System.out.println( "   Sorry you must enter valid arguments\n" +
                          "   Usage: java Riemann <functionName> <addDescript> <lowerBound> <upperBound> [percentage]\n" +
                          "   Please try again..........." );
      return false;
    }

    if( args[0].equals("runtests") ) {
      return true;
    }



       try {

           //Checking if there is an optional percentage
           if (args[args.length-1].contains("%") == true){
               //Setting percentage to argument from the command line
               percentage = Double.parseDouble((args[args.length-1].substring(0, args[args.length-1].length() - 1)));


               //Set the lowerbound and upperbound values
               lowerBound = Double.parseDouble(args[args.length-3]);
               upperBound = Double.parseDouble(args[args.length-2]);

              if(lowerBound > upperBound){
                System.out.println( "   Sorry lowerBound must be a smaller number than upperBound");
                System.exit( 1 );
              }

               //Set the length of coeffiecients
               coeffLength = args.length - 4;


               //Set coefficient array with remaining values
               coeff = new double[coeffLength];
               //For loop adding all coeff into array and validating
               for(int i = 0; i < coeffLength; i++){
                     coeff[i] = Double.parseDouble(args[i+1]);
                }


           } else {
               percentage = 1.0;
               lowerBound = Double.parseDouble(args[args.length-2]);
               upperBound = Double.parseDouble(args[args.length-1]);

               if(lowerBound > upperBound){
                 System.out.println( "   Sorry lowerBound must be a smaller number than upperBound");
                 System.exit( 1 );
               }

               coeffLength = args.length - 3;



               //Set coefficient array with remaining values
               coeff = new double[coeffLength];
               //For loop adding all coeff into array and validating
               for(int i = 0; i < coeffLength; i++){
                     coeff[i] = Double.parseDouble(args[i+1]);
                }

           }

       }
       catch( NumberFormatException nfe ){
           System.out.println( "Caught NumberFormat Exception" );
           System.exit(1);
       }







       if( args[0].equals("sin")){
         if (args.length == 3 || (args.length == 4 && (args[args.length-1].contains("%") == true))){
           this.coeffLength = 2;
           coeff = new double[coeffLength];
           coeff[0] = 0.0;
           coeff[1] = 1.0;
         } else {
               if (args[args.length-1].contains("%") == true){
                   //Setting percentage to argument from the command line
                   percentage = Double.parseDouble((args[args.length-1].substring(0, args[args.length-1].length() - 1)));


                   //Set the lowerbound and upperbound values
                   lowerBound = Double.parseDouble(args[args.length-3]);
                   upperBound = Double.parseDouble(args[args.length-2]);

                  if(lowerBound > upperBound){
                    System.out.println( "   Sorry lowerBound must be a smaller number than upperBound");
                    System.exit( 1 );
                  }

                   //Set the length of coeffiecients
                   coeffLength = args.length - 4;

                   //Set coefficient array with remaining values
                   coeff = new double[coeffLength];
                   //For loop adding all coeff into array and validating
                   for(int i = 0; i < coeffLength; i++){
                       coeff[i] = Double.parseDouble(args[i+1]);
                    }


               } else {
                   percentage = 1.0;

                   lowerBound = Double.parseDouble(args[args.length-2]);
                   upperBound = Double.parseDouble(args[args.length-1]);

                   if(lowerBound > upperBound){
                     System.out.println( "   Sorry lowerBound must be a smaller number than upperBound");
                     System.exit( 1 );
                   }

                   coeffLength = args.length - 3;

                   //Set coefficient array with remaining values
                   coeff = new double[coeffLength];
                   //For loop adding all coeff into array and validating
                   for(int i = 0; i < coeffLength; i++){
                         coeff[i] = Double.parseDouble(args[i+1]);
                    }

           }

         }
       }






       return true;
  }






   /**
    *  Method to execute Riemann calculation
    */
    public double integrate(double lowerBound, double upperBound, double[] coeff, double numRec) {

      double width = (upperBound - lowerBound)/numRec; //gives the width of each of the rectangles
      double midpoint = 0.0;
      double yValue = 0.0; //the y value that corresponds to the x -- F(x) value
      area = 0.0;

      //Calculate the Area Under the curve using rectangles
      System.out.println("\n NumRec is: " + numRec);
      for(int j = 0; j < numRec; j++){
        System.out.println("\n ====== J is: " + j);
        System.out.println("Midpoint is: " + midpoint);
        midpoint = lowerBound + (width/2.0) + (width*j);
        //Calculate yValue
        for(int i = 0; i < coeffLength; i ++){
          yValue += (Math.pow(midpoint,i)*coeff[i]);
        }
        System.out.println("Area is: " + area);
        area += yValue * width;
      }

      return area;
    }



    /**
     *  Method to execute Riemann calculation for sine
     */
    public double sinIntegrate(double lowerBound, double upperBound, double[] coeff, double numRec) {
       double width = (upperBound - lowerBound)/numRec; //gives the width of each of the rectangles
       double midpoint = 0.0;
       double yValue = 0.0; //the y value that corresponds to the x -- F(x) value
       area = 0.0;

         //Calculate the Area Under the curve using rectangles

        for(int j = 0; j < numRec; j++){
          midpoint = lowerBound + (width/2.0) + (width*j);
          //Calculate yValue

          for(int i = 0; i < coeffLength; i++){
            yValue += Math.sin(Math.pow(midpoint,i)*coeff[i]);
          }

          area += yValue * width;
        }

        return area;
    }






    /**
      *  Method to test integrate
      */
    public void testIntegrate(){

        System.out.println("Running testIntegrate \n");


        //-2x^2 + 8x
        System.out.println("Equation Plugged In:  -2x^2 + 8x");
        coeffLength = 3;
        double[] myArgs = {0,8,-2};
        double result = integrate(1,4,myArgs,1);
        System.out.println("Expected area is 22.5, got: " + result);


        //9x^3 + 5x^2 + 2x + 3
        System.out.println("Equation Plugged In:  7x^3 + 2x^2 + 1x + 5");
        coeffLength = 4;
        myArgs = new double[] {5,1,2,7};
        result = integrate(-2,3,myArgs,1);
        System.out.println("Expected area is 34.375, got: " + result);


        //3
        System.out.println("Equation Plugged In: 3");
        coeffLength = 1;
        myArgs = new double[]{3};
        result = integrate(1,4,myArgs,1);
        System.out.println("Expected area is 9, got: " + result);

    }




     /**
      *  Method to test validation
      */
    public void testValidate(){

        System.out.println("Running testValidate \n");
        //8x^2 + 5x
        String[] args = {"poly", "0.0", "5.0", "8.0", "1", "4", "5%"};
        boolean result = validate(args);
        System.out.println("Testing poly with valid args, Expected true, got " + result + "\n");

        args = new String[] {"poly","1", "4", "5%"};
        result = validate(args);
        System.out.println("Testing poly with no arguments, Expected false, got " + result + "\n");

        args = new String[] {"poly"};
        result = validate(args);
        System.out.println("Testing 'poly', expected false, got " + result + "\n");

        args = new String[] {"poly","1"};
        result = validate(args);
        System.out.println("Testing 'poly 1' expected false, got " + result + "\n");

        args = new String[]{"poly", "0.0", "5.0", "8.0", "9.0","1", "4", "5%"};
        result = validate(args);
        System.out.println("Testing poly with valid args, Expected true, got " + result + "\n");

     }


    /**
     *  Method to test integrateSin
     */
     public void testIntegrateSin(){

         System.out.println("\n Running testIntegrateSin");

         //Sin(-2x)
         System.out.println("Equation Plugged In: Sin(-2x)");
         coeffLength = 2;
         double[] myArgs = {0,-2};
         double result = sinIntegrate(1,4,myArgs,1);
         System.out.println("Expected area is 0.1353234013692644, got: " + area);


         //Sin(x)
         System.out.println("Equation Plugged In: Sin(x)");
         coeffLength = 2;
         myArgs = new double[]{0.0,1.0};
         result = sinIntegrate(1,4,myArgs,1);
         System.out.println("Expected area is 1.193945926731752, got: " + area);

     }


    /**
     *  Method to test methods
     */
    public void runMyTests(){
        // testValidate();
        testIntegrate();
        testIntegrateSin();
    }




      /**
       *  The main program starts here
       *  remember the constraints from the project description
       *  @param  args  String array of the arguments from the command line
       *
       *
       */
       public static void main( String args[] ) {

           Riemann r = new Riemann();

          if(!r.validate(args)){
            System.exit( 1 );
          }





           switch( args[0] ) {


              case "poly" :

                           //Calculations
                            previous = r.integrate(lowerBound, upperBound, coeff, 1);
                            q = 2;

                            while( true ){
                                current = r.integrate(lowerBound, upperBound, coeff, q);
                                //Checking if the previous area is close enough to the next area
                                System.out.println("\n Calculation: " + (1-(current/previous)));
                                System.out.println("\n Current: " + current);
                                System.out.println("\n Previous: " + previous);
                                if(1-(current/previous) <= percentage){
                                    System.out.println("Area is: " + area);
                                    System.exit(1);
                                }
                                previous = current;
                                q++;
                            }


              case  "sin" :

                            previous = r.sinIntegrate(lowerBound, upperBound, coeff, 1);
                            q = 2;

                            while( true ){

                              current = r.sinIntegrate(lowerBound,upperBound, coeff, q);

                              //Checking if the previous area is close enough to the next area
                              if(1-(current/previous) <= percentage){
                                System.out.println("Area is: " + area);
                                System.exit(1);
                              }

                              previous = current;
                              q++;
                            }


              case "log" :
                            break;

              case "exp" :
                            break;

              case "sqrt": //check for negative numbers
                           break;


              case "runtests": r.runMyTests();
                               break;


              default: throw new IllegalArgumentException( "Illegal function value given" );
           }

       }

}
