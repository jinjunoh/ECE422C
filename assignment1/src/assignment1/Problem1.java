/* Student Name: Jin Jun Oh, Lab Section: F 1:30-3:00 */

package assignment1;
import java.util.Scanner;
public class Problem1 {
    public static void main (String[] args){
        Scanner s = new Scanner(System.in);
        int n = Integer.parseInt(s.nextLine());
        String input = s.nextLine();
        Problem1.adjmax(n, input);
    }
    public static void adjmax (int n, String s){
        // placeholder which stores the maximum product of n adjacent units
        long max = 0;
        // placeholder which contains the product of current n adjacent units
        long num1 = 0;

        // edge case when n is bigger than s.
        if(s.length() < n){
            max = mul(s.substring(0,s.length()));
            System.out.print(max);
        } else{
            // set max to the first n adjacent numbers
            max = mul(s.substring(0, n));

            // iterate through the string and calculate the product of n adjacent elements
            for(int j = 1; j < s.length()-n+1; j++){
                num1 = mul(s.substring(j,j+n));
                // compare the current product of n adjacent elements with the max
                if(num1 > max) max = num1;
            }
            System.out.println(max);
        }
    }

    private static long mul (String s){
        // helper function that gets the product of n adjacent elements
        // initialize placeholder values
        long max = 0;
        long product = 0;
        for(int i = 0; i < s.length(); i++){
            // when val at i == 0, cmp product with max and reset product
            if(Character.getNumericValue(s.charAt(i)) == 0){
                //System.out.println(s.charAt(i));
                max = Math.max(max, product);
                product = 0;
            } else{
                // mul the product with val at i and compare with max
                if(product == 0){
                    product++;
                }
                product *= Character.getNumericValue((s.charAt(i)));
                max = Math.max(max, product);
            }
        }
        // return the max adj digits of given string
        return max;
    }
}
