package assignment4;

import java.io.File;
import java.io.IOException;

public class Main {
    /**
     * Example program using GraphPoet.
     */

    /**
     * Generate example poetry.
     *
     * @param args unused
     * @throws IOException if a poet corpus file cannot be found or read
     */
    public static void main(String[] args) throws IOException {
        final GraphPoet nimoy = new GraphPoet(new File("corpus.txt"));
        // /Users/jjoh/422C/assignments/assignment4/src/assignment4
        System.out.println(nimoy.poem(new File("input.txt")));
        // /Users/jjoh/422C/assignments/assignment4/src/assignment4
    }
}
