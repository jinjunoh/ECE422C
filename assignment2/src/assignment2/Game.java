/* EE422C Assignment #2 submission by
 * Replace <...> with your actual data.
 * <Jin Jun Oh>
 * <jo26964>
 */

package assignment2;
import java.util.*;
import assignment2.*;
public class Game {
    private static boolean testBool;
    private static Scanner scanner;
    private static String code;
    private static List<String[]>history;
    private static int guessNumber;
    public Game (boolean a, Scanner sc, String secret){
        // loop for multiple games(?)
        guessNumber = GameConfiguration.guessNumber;
        testBool = a;
        scanner = sc;
        code = secret;
        history = new ArrayList<>();
        //System.out.println("Game created");
    }
    // carries out the actual game
    private static void updateHistory(String input, String result){
        String[] guess = {input, result};
        history.add(guess);
        return;
    }
    private static void displayHistory(){
        for(int i = 0; i < history.size(); i ++){
            System.out.print("\n" + history.get(i)[0] + "        " +  history.get(i)[1]);
        }
        System.out.print("\n\n");
        return;
    }
    private static void checkInput(String input){
        if(input.equals("HISTORY")){
            displayHistory();
            return;
        }
        // if it is an invalid input
        if(input.length() == 0){
            System.out.println("\n-> INVALID GUESS\n");
            return;
        }
        if(input.length() != GameConfiguration.pegNumber){
            System.out.println("\n" + input + " -> INVALID GUESS\n");
            return;
        }
        for(int i = 0; i < input.length(); i++){
            if(input.charAt(i) >= 'a' && input.charAt(i) <= 'z'){
                System.out.println("\n" + input + " -> INVALID GUESS\n");
                return;
            }
            boolean iscolor = false;
            for(int j = 0; j < GameConfiguration.colors.length; j++){
                if(input.charAt(i) == (GameConfiguration.colors[j].charAt(0))){
                    iscolor = true;
                }
            }
            if(iscolor == false){
                System.out.println("\n" + input + " -> INVALID GUESS\n");
                return;
            }
        }
        // input is valid
        validGuess(input);
    }
    private static void validGuess(String input){
        // format: OOOO -> Result: 0B_0W
        int black = 0;
        int white = 0;
        // check back pegs first. If a peg is used for black or white, it cannot be reused
        boolean[] used1 = new boolean[input.length()];
        boolean[] used2 = new boolean[input.length()];

        for(int i = 0; i < input.length(); i ++) {
            if (input.charAt(i) == code.charAt(i)) {
                black++;
                used1[i] = true;
                used2[i] = true;
            }
        }
        for(int i = 0; i < input.length(); i ++){
            for(int j = 0; j < input.length(); j++){
                if(input.charAt(i) == code.charAt(j) && used1[i] != true && used2[j] != true ){
                    white++;
                    used1[i] = true;
                    used2[j] = true;
                    break;
                }
            }
        }
        if(black == code.length()){
            System.out.println("\n" + input + " -> Result: "+ black +"B_0W - You win !!");
            guessNumber = 0;
            return;
        } else{
            guessNumber--;
            if(guessNumber == 0){
                System.out.println("\n Sorry, you are out of guesses. You lose, boo-hoo.");
                return;
            } else{
                System.out.println("\n" + input + " -> Result: "+ black +"B_"+ white + "W\n");
                updateHistory(input, black +"B_"+ white + "W");
                return;
            }
        }
    }

    private static void gameloop(){
        while(guessNumber > 0){
            System.out.print("You have " + guessNumber
                    + " guesses left.\n" +
                    "What is your next guess?\n" +
                    "Type in the characters for your guess and press enter.\n" +
                    "Enter guess: " );
            String input = scanner.nextLine();
            checkInput(input);
        }
        System.out.print("\nAre you ready for another game (Y/N): ");
        String input1 = scanner.nextLine();
        if(input1.equals("Y")){
            history.clear();
            guessNumber = GameConfiguration.guessNumber;
            code = SecretCodeGenerator.getInstance().getNewSecretCode();
            runGame();
        }
    }
    public static void runGame (){
        if(testBool == true){
            // testing mode
            System.out.print("\nGenerating secret code ...(for this example the secret code is "+ code +")\n\n" );
            gameloop();
        } else{
            // user mode
            System.out.print("\nGenerating secret code ... \n\n" );
            gameloop();
        }
    }
}
