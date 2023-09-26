/* Student Name: Jin Jun Oh, Lab Section: F 1:30-3:00 */

package assignment1;
import java.util.Scanner;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class Problem3 {
    public static void main(String args[]){
        Scanner s = new Scanner(System.in);
        String input = s.nextLine();
        // initialize MaxentTagger Object
        MaxentTagger mt = new MaxentTagger("./english-bidirectional-distsim.tagger");
        // print tagged string input
        System.out.println(mt.tagString(input));
    }
}
