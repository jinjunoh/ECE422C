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
    public String printName(){
        return name;
    }
    public String printEdges(){
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
    public boolean poemChecker(String pname, int num, Map<String, Integer> map){ // checks whether pname exits in graph
        boolean result = false;
        if(this == null) return false;
        if(num == 0){
            for (Vertex ver : this.edges.keySet()){
                result = result || ver.poemChecker(pname,num + 1, map);
            }
        }
        if(num == 1){
            for (Map.Entry<Vertex, Integer> e : this.edges.entrySet()){
                if(e.getKey().name.equals(pname)){
                    map.put(this.name, e.getValue());
                    return true;
                }
           }
            return false;
        }
        return result;
    }
    public String poemAdder(String name){ // returns the bridgeword given the name
        // needs to check if the second vertex from current equals name
        Map<String, Integer> map = new HashMap<>();
        if(this.poemChecker(name, 0, map)){
            // if we find the equivalent vertex then return the first vertex with the largest weight
            // utilize priorityQueue
            PriorityQueue<String> queue = new PriorityQueue<>((a, b) -> map.get(a) - map.get(b));
            queue.addAll(map.keySet());
            return queue.poll();
        } else{
            return "";
        }
    }
    public boolean contains(String name){
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
    public Vertex find(String name){
        return containsRecursion(name, new HashSet<>());
    }
}
