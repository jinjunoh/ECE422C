
package assignment4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import assignment4.Vertex;

public class GraphPoet {
    /**
     *
     * @param corpus text file from which to derive the poet's affinity graph
     * @throws IOException if the corpus file cannot be found or read
     */
    private Vertex graph;
    public GraphPoet(File corpus) throws IOException {
        // Declare necessary variables
        Scanner sc = new Scanner(corpus);
        String curr; // current word
        Vertex head;
        if(sc.hasNext()){
            curr = sc.next().toLowerCase();
            curr = curr.replaceAll("[^a-zA-Z]", ""); // eliminate non-alphabetical parts of the string
            graph = new Vertex(curr); // initialize graph
            head = graph; // set a reference to the head of the graph
            while(sc.hasNext()){
                curr = sc.next().toLowerCase();
                curr = curr.replaceAll("[^a-zA-Z]", ""); // eliminate non-alphabetical parts of the string
                // check if there is any vertex with name curr
                if(graph.contains(curr)){
                    // if so, store the reference to the vertex
                    Vertex tail = graph.find(curr);
                    // add the vertex to the appropriate vertex
                    head.addVertex(tail);
                    // set current vertex to the child vertex
                    head = tail;
                }else{ // if the graph does not contain vertex, add it to current vertex
                    // create the new vertex
                    Vertex tail = new Vertex(curr);
                    // add the new vertex to the graph
                    head.addVertex(tail);
                    // set current vertex to the child vertex
                    head = tail;
                }
            }
        }
        //graph.printGraph(new HashSet<>()); // prints out all the vertex, edges, and weights of the graph
        sc.close();
    }
    /**
     * Generate a poem.
     *
     * @param input File from which to create the poem
     * @return poem (as described above)
     */
    public String poem(File input) throws IOException {
        /* Read in input and use graph to complete poem */
        //Scanner sc = new Scanner(input);
        String in = new String(Files.readAllBytes(input.toPath())).toLowerCase();
        //System.out.println(in);
        String poem = "";
        // convert input into arraylist
        ArrayList<String> arr = new ArrayList<>(Arrays.asList(in.split("\\s+")));
        // initialize listiterator
        ListIterator<String> iterator = arr.listIterator();
        while(iterator.hasNext()){
            String curr = iterator.next();
            curr = curr.replaceAll("[^a-zA-Z]", ""); // eliminate non-alphabetical parts of the string
            if(iterator.hasNext()){
                String next = iterator.next();
                next = next.replaceAll("[^a-zA-Z]", ""); // eliminate non-alphabetical parts of the string
                if(poem.equals("")){    // if this is the first iteration
                    if(graph.contains(curr)){
                        Vertex v = graph.find(curr); // get the vertex of the starting vertex
                        if(v.poemAdder(next).equals("")){ // check if there exists a bridge word
                            poem = curr;
                        } else {
                            poem = curr + " " + v.poemAdder(next);
                        }
                    } else{
                        poem = curr;
                    }
                    iterator.previous();
                } else{                 // if this is not the first iteration
                    if(graph.contains(curr)){
                        Vertex v = graph.find(curr);
                        if(v.poemAdder(next).equals("")){ // check if there exists a bridge word
                            poem = poem + " " + curr;
                        } else {
                            poem = poem + " "+curr + " " + v.poemAdder(next);
                        }
                    } else{
                        poem = poem + " " + curr;
                    }
                    iterator.previous(); // shift the iterator one back to account for all the pairs in input
                }
            } else{
                poem += " " + curr;
            }
        }
        return poem;
    }
}
