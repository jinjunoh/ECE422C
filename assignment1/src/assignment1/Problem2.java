/* Student Name: Jin Jun Oh, Lab Section: F 1:30-3:00 */

package assignment1;
import java.util.Scanner;

public class Problem2 {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        String paragraph = in.nextLine();
        Problem2.dollarString(paragraph);
    }
    public static void dollarString(String s){
        // convert space delimited paragraph to string array
        String[] paragraph = s.split(" ");
        // iterate through each word in string array and check if it's a dollar
        for (String a : paragraph){
            dollarChecker(a);
        }
    }
    private static void dollarChecker(String s){
        // helper function that checks if the given string is a dollar
        // convert the string to a char array
        char[] word  = s.toCharArray();

        // initialize placeholder
        int cost = 0;
        for(char c : word){
            // store the ASCII value of c in temp
            int temp = (int)c;
            // check if c is upper case
            if(temp<=122 & temp>=97)
                cost += (temp-96);
            // check if c is lower case
            else if(temp<=90 & temp>=65)
                cost += (temp-64);
        }
        // if the sum is a dollar, print the word
        if(cost == 100){
            System.out.println(s);
        }
    }
}
