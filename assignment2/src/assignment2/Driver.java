/* EE422C Assignment #2 submission by
 * Replace <...> with your actual data.
 * <Jin Jun Oh>
 * <jo26964>
 */

package assignment2;
import java.util.*;
import assignment2.SecretCodeGenerator;
public class Driver {
    public static void main(String[] args) {
        // should call game constructor
        // Look up documentation on how to use the String[] args part of main.
        String testBool;
        if(args.length >= 1){
            testBool = args[0];
        } else {
            testBool = "0";
        }

        //You must have only 1 Scanner object connected to the keyboard,
        // and it should be created only once during your
        // entire program.
        Scanner sc = new Scanner(System.in);
        System.out.print("Welcome to Mastermind. Here are the rules.\n\n" +
                "This is a text version of the classic board game Mastermind.\n" +
                "The computer will think of a secret code. The code consists of " + GameConfiguration.pegNumber + "\n" +
                "colored pegs. The pegs MUST be one of six colors: blue, green,\n" +
                "orange, purple, red, or yellow. A color may appear more than once in\n" +
                "the code. You try to guess what colored pegs are in the code and\n" +
                "what order they are in. After you make a valid guess the result\n" +
                "(feedback) will be displayed.\n\n" +
                "The result consists of a black peg for each peg you have guessed\n" +
                "exactly correct (color and position) in your guess. For each peg in\n" +
                "the guess that is the correct color, but is out of position, you get\n" +
                "a white peg. For each peg, which is fully incorrect, you get no\n" +
                "feedback.\n\n" +
                "Only the first letter of the color is displayed. B for Blue, R for\n" +
                "Red, and so forth. When entering guesses you only need to enter the\n" +
                "first character of each color as a capital letter.\n\n" +
                "You have 12 guesses to figure out the secret code or you lose the\n" +
                "game. Are you ready to play? (Y/N): ");

        String play = sc.nextLine();

        if(play.equals("Y")){
            String secret = SecretCodeGenerator.getInstance().getNewSecretCode();
            if(testBool.equals("1")){
                Game masterMind = new Game(true, sc, secret);
                masterMind.runGame();
            } else{
                Game masterMind = new Game(false, sc, secret);
                masterMind.runGame();
            }
        } else {
            return;
        }
    }
}
// -------------------------------------------------------------------------
/*   size of the dash
        interface vs abstract classes
        interface does not need a constructor

        in interface everything is declared but not defined
        interfaces are basically polyomrphism
        just an access modifier "default"
        you can only extend one class but you can implement many interfaces

        PrintWriter output = null;
        try{
            output = new PrintWriter(new FileOutputStream("src/assignment2/introduction.txt"));
        } catch(FileNotFoundException e){
            System.out.println("error opening file " + e.getMessage());
            System.exit(0);
        }
        output.close();
*/