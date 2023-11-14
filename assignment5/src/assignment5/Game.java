/* EE422C Assignment #2 submission by
 * Replace <...> with your actual data.
 * <Jin Jun Oh>
 * <jo26964>
 */

package assignment5;
import java.util.*;
import assignment5.*;
public class Game {
    private boolean testBool;
    private Scanner scanner;
    private String code;
    private List<String[]>history;
    private int guessNumber;
    public Game (boolean a, String secret){
        // loop for multiple games(?)
        guessNumber = GameConfiguration.guessNumber;
        testBool = a;
        code = secret;
        history = new ArrayList<>();
        //System.out.println("Game created");
    }
    // carries out the actual game
    private void updateHistory(String input, String result){
        String[] guess = {input, result};
        history.add(guess);
        return;
    }
    private String displayHistory(){
        String res = "";
        for(int i = 0; i < history.size(); i ++){
            res += "\n" + history.get(i)[0] + "        " +  history.get(i)[1];
        }
        return res += "\n\n" + "You have " + guessNumber + " guesses left.\n" + "What is your next guess?\n" + "Type in the characters for your guess and press enter.\n" + "Enter guess: ";
    }
     public String checkInput(String input){
        if(input.equals("HISTORY")){
            return displayHistory();
        }
        // if it is an invalid input
        if(input.length() == 0){
            return "\n-> INVALID GUESS \n\n" + "You have " + guessNumber + " guesses left.\n" + "What is your next guess?\n" + "Type in the characters for your guess and press enter.\n" + "Enter guess: ";
        }
        if(input.length() != GameConfiguration.pegNumber){
            return "\n" + input + " -> INVALID GUESS \n\n"+ "You have " + guessNumber + " guesses left.\n" + "What is your next guess?\n" + "Type in the characters for your guess and press enter.\n" + "Enter guess: ";
        }
        for(int i = 0; i < input.length(); i++){
            if(input.charAt(i) >= 'a' && input.charAt(i) <= 'z'){
                return "\n" + input + " -> INVALID GUESS \n\n" + "You have " + guessNumber + " guesses left.\n" + "What is your next guess?\n" + "Type in the characters for your guess and press enter.\n" + "Enter guess: ";
            }
            boolean iscolor = false;
            for(int j = 0; j < GameConfiguration.colors.length; j++){
                if(input.charAt(i) == (GameConfiguration.colors[j].charAt(0))){
                    iscolor = true;
                }
            }
            if(iscolor == false){
                return "\n" + input + " -> INVALID GUESS \n\n"+ "You have " + guessNumber + " guesses left.\n" + "What is your next guess?\n" + "Type in the characters for your guess and press enter.\n" + "Enter guess: ";
            }
        }
        // input is valid
        return validGuess(input);
    }
    private String validGuess(String input){
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
            guessNumber = GameConfiguration.guessNumber;
            return "\n" + input + " -> Result: "+ black +"B_0W - You win !!" + "\n\nAre you ready for another game (Y/N): ";
        } else{
            guessNumber--;
            if(guessNumber == 0){
                return ("\n Sorry, you are out of guesses. You lose, boo-hoo." + "\n\nAre you ready for another game (Y/N): ");
            } else{
                String res = "\n" + input + " -> Result: "+ black +"B_"+ white + "W\n\n";
                res += "You have " + guessNumber + " guesses left.\n" + "What is your next guess?\n" + "Type in the characters for your guess and press enter.\n" + "Enter guess: ";
                updateHistory(input, black +"B_"+ white + "W");
                return res;
            }
        }
    }

    private void gameloop(String input){
        while(guessNumber > 0){
            System.out.print("You have " + guessNumber
                    + " guesses left.\n" +
                    "What is your next guess?\n" +
                    "Type in the characters for your guess and press enter.\n" +
                    "Enter guess: " );
            checkInput(input);
        }
        System.out.print("\nAre you ready for another game (Y/N): ");
        String input1 = scanner.nextLine();
        if(input1.equals("Y")){
            history.clear();
            guessNumber = GameConfiguration.guessNumber;
            code = SecretCodeGenerator.getInstance().getNewSecretCode();
            //runGame();
        }
    }
    public String startGame() {
        String response = "";
        if(testBool == true){
            // testing mode
            response = "\nGenerating secret code ...(for this example the secret code is "+ code +")\n\n";
            guessNumber = GameConfiguration.guessNumber;
            response += "You have " + guessNumber + " guesses left.\n" + "What is your next guess?\n" + "Type in the characters for your guess and press enter.\n" + "Enter guess: ";
            return response;
        } else{
            // user mode
            response = "\nGenerating secret code ... \n\n" ;
            guessNumber = GameConfiguration.guessNumber;
            response += "You have " + guessNumber + " guesses left.\n" + "What is your next guess?\n" + "Type in the characters for your guess and press enter.\n" + "Enter guess: ";
            return response;
        }
    }
}
