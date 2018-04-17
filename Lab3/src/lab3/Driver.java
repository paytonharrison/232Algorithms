package lab3;

import lab3.Graph;

public class Driver {
	
	public static void main(String[] args) {
		
		Graph graph = new Graph();
		
		System.out.println("Prim's Algorithm: ");
		System.out.println("");
		graph.prims();
		System.out.println("");
		
		graph.resetVertices();
		
		System.out.println("Kruskal's Algorithm: ");
		System.out.println("");
		graph.kruskals();
		System.out.println("");
		
		graph.resetVertices();
		
		System.out.println("Floyd-Warshall's Algorithm: ");
		graph.floydWarshall();
	}
	

}
