package assignment4;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Vertex{ // vertex datastructure
    private String name;
    private Map<Vertex, Integer> edges;
    public Vertex(String word){ // constructor input
        name = word;
        edges = new HashMap<>();
    }
    public void addVertex(Vertex next){ // adds vertex to the edge
        // need to check if vertex already exists in the graph
        edges.put(next, edges.getOrDefault(next, 0) + 1);
    }
    public String printName(){ // returns the name of this instance
        return name;
    }
    public String printEdges(){ // returns all the edges of this instance with weights
        String a = "";
        for(Vertex ver : this.edges.keySet()){
            a += ver.name + edges.get(ver);
        }
        return a;
    }
    public void printGraph(Set<Vertex> visited){ // used for debugging. Prints all the graphs including Vertex, edge, weight
        if (visited.contains(this)) {
            return;
        }
        System.out.println(this.printName() + " [edges]: " + this.printEdges());
        visited.add(this);
        for(Vertex v : this.edges.keySet()){
            v.printGraph(visited);
        }
    }
    public void poemChecker(String pname, int num, Map<String, Integer> map){ // checks whether pname exits in graph
        if(this == null) return;
        if(num == 0){
            for (Vertex ver : this.edges.keySet()){
                ver.poemChecker(pname,num + 1, map); // recursive call
            }
        }
        else if(num == 1){ // reached the first vertex
            for (Map.Entry<Vertex, Integer> e : this.edges.entrySet()){ // check whether the children.equals(pname)
                //System.out.println(this.name);
                if(e.getKey().name.equals(pname)){
                    map.put(this.name, e.getValue());
//                    if(this.name.equals("life")){ //////////////////////
//                        System.out.println(this.name + " " + pname);
//                        System.out.println(map);
//                    }
                }
           }
            return;
        } else{
            return;
        }
    }
    public String poemAdder(String name){ // returns the bridgeword given the name
        // needs to check if the second vertex from current equals name
        //System.out.println(name);
        Map<String, Integer> map = new HashMap<>();
        this.poemChecker(name, 0, map);
        // if we find the equivalent vertex then return the first vertex with the largest weight
        // utilize priorityQueue: allows sorting while adding content to queue
        PriorityQueue<String> queue = new PriorityQueue<>((a, b) -> map.get(b) - map.get(a)); // lambda function to auto sort
        //System.out.println(map.keySet() + "\n");
        queue.addAll(map.keySet());
        //System.out.println(queue);
        if(queue.isEmpty()) return "";
        return queue.poll(); // return the bridgeword with the largest weight
    }
    public boolean contains(String name){ // checks whether the word is in the graph
        return this.containsRecursion(name, new HashSet<>()) != null;
    }
    public Vertex containsRecursion(String name, Set<Vertex> visited){
        if (visited.contains(this)) { // check if the current vertex is already visited
            return null;
        } else if (this.name.equals(name)) { // check if the current vertex equals name
            return this;
        } else{
            visited.add(this); // add current vertex to visited map
            for (Vertex ver : this.edges.keySet()) { // iterated through the edges
                if (ver.name.equals(name)) {
                    return ver;
                }
                Vertex found = ver.containsRecursion(name, visited);
                if (found != null) {
                    return found;
                }
            }
            return null;
        }
    }
    public Vertex find(String name){ // returns the Vertex that corresponds with name
        return containsRecursion(name, new HashSet<>());
    }
}
