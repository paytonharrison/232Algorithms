package lab3;

//you strugglebus through java

import java.util.*;
import java.io.*;
import java.lang.reflect.Array;

//This class contains all of the reading in/algorithms
//author PH ZT TO

public class Graph {
	//instance variables
	private Vertex vertexList[];
	private String directed[][];
	private String undirected[][];
	
	//constructor calls methods that read the input files
	public Graph()
	{
		readLabels("src/lab3/labels.txt");
		readAdjacencyMatrix("src/lab3/directed.txt", directed);
		readAdjacencyMatrix("src/lab3/undirected.txt", undirected);
	}
	
	//reads the labels input file, creates a new vertex for each label, and saves each new vertex
	//into the vertexList field
	public void readLabels(String filePath)
	{
		try {
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			String label;
			int count = 0;
			String labelses = "";
			while((label = br.readLine()) != null) {
				labelses += label;
			}
			
			count = labelses.length();
			
			vertexList = new Vertex[count];
			directed = new String[count][count];
			undirected = new String[count][count];
			//commented this out v.3
			//System.out.println();
			
			for(int i = 0; i < count; i++) {
				vertexList[i] = new Vertex(labelses.charAt(i));
			}
		}catch(Exception e) {
			System.out.println("File not Found");
			e.printStackTrace();
		}
		
		
	}
	
	//reads the adjacency matrix input file and saves it into the adjacencyMatrix field
	public void readAdjacencyMatrix(String filePath, String[][] array)
	{
		try {
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			String line;
			
			int c = 0;
			int spot = 0;
			
			for(int x = 0; x < array.length; x++) {
				for(int y = 0; y < array.length; y++) {
					array[x][y] = "";
				}
			}
			
			while((line = br.readLine()) != null) {
				Scanner lb = new Scanner(line);
				
				for(int i = 0; i < line.length(); i++) {
					if(line.charAt(i) == ' ') {
						spot += 1;
					}
					else {
						array[c][spot] += line.charAt(i);
					}
				}
				spot = 0;
				c++;
				lb.close();
			}
		}catch(Exception e) {
			System.out.println("File not Found");
			e.printStackTrace();
		}
		
	}
	
	//method to print matrices...used in floyd-warshall algorithm
	public void printMatrices(int array[][]) {
		
		//what is this a check for?
		for(int i = -2; i < vertexList.length; i++) {
			if(i > -1) {
				System.out.print(vertexList[i].label + "  ");
			}
			else {
				System.out.print(" ");
			}
		}
		//reads in text file, printing INF back instead of integer
		System.out.println();
		for(int i = 0; i < array.length; i++) {
			System.out.print(vertexList[i].label + " ");
			for(int j = 0; j < array[i].length; j++) {
				if(array[i][j] > 9000) {
					System.out.print("INF");
				}
				else{
					System.out.print(array[i][j] + " ");
				}
			}
			System.out.println();
		}	
	}
	
	//method to reset all vertices back to wasVisited = false
	public void resetVertices() {
		for(int i = 0; i < vertexList.length; i++) {
			vertexList[i].wasVisited = false;
		}
	}
	
	
	//method to implement prim's algorithm: prints output as a minimum spanning tree
	public void prims() {
		
		Comparator<Edge> comparator = new Compare();
		
		//add start vertex to a priority queue of open vertices
		PriorityQueue<Edge> pQueue = new PriorityQueue(10, comparator);
		vertexList[0].wasVisited = true;		//mark as visited
		
		int current = 0;
		
		//while there are still vertices to be added to the list...
		for(int i = 0; i < vertexList.length; i++) {
			for(int j = 0; j < undirected[current].length; j++) {
				
				//if vertex is connected, add to pQueue
				if(!undirected[current][j].equals("INF") && !vertexList[j].wasVisited){
					
					Edge edge = new Edge(vertexList[current].label, vertexList[j].label,
							Integer.parseInt(undirected[current][j]), vertexList);
					//System.out.println(Integer.parseInt(undirected[current][j]));
					//System.out.println("********");
					pQueue.add(edge);
					
					//it's not reading to next line?
				}
			}
			//pick edge with lowest weight, add edge and destination to MST
			
			while(!pQueue.isEmpty() && vertexList[pQueue.peek().endIndex].wasVisited) {
				pQueue.poll();
			}
			if(!pQueue.isEmpty()) {
				Edge removed = pQueue.poll();
				for(int w = 0; w < vertexList.length; w++) {
					if(vertexList[w].label == removed.end) {
						current = w;
					}
				}
				vertexList[current].wasVisited = true;
				removed.printEdge();
			}
		}
	}
	
	//method to implement kruskal's algorithm
	public void kruskals() {
		
		Comparator<Edge> comparator = new Compare();
		PriorityQueue<Edge> pQueue = new PriorityQueue(10, comparator);
		
		//create a forest 
		LinkedList forest[] = new LinkedList[vertexList.length];
		
		//make each vertex a separate tree
		for(int i = 0; i < vertexList.length; i++) {
			forest[i] = new LinkedList();
			forest[i].add(vertexList[i].label);
		}
		
		//for every value in adjacency matrix, add to priority queue
		for(int i = 0; i < vertexList.length; i++) {
			for(int j = 0; j < vertexList.length; j++) {
				if(!undirected[i][j].equals("INF")) {
					pQueue.add(new Edge(vertexList[i].label, vertexList[j].label, Integer.parseInt(undirected[i][j]), vertexList));
					
				}
			}
		}
		//as long as queue is not empty check for cycles 
		while(!pQueue.isEmpty()) {
			Edge min = pQueue.poll();
			boolean cycle = false;
			for(int i = 0; i < forest.length; i++) {
				if(forest[i].contains(min.start) && forest[i].contains(min.end)) {
					cycle = true;
				}
			}
			//if there aren't any cycles, print edge and set start and end indices
			if(!cycle) {
				min.printEdge();
				int startIndex = -1;
				int endIndex = -1;
				for(int i = 0; i < forest.length; i++) {
					if(forest[i].contains(min.start)) {
						startIndex = i;
					}
					if(forest[i].contains(min.end)) {
						endIndex = i;
					}
				}
				while(forest[endIndex].peek() != null) {
					forest[startIndex].add(forest[endIndex].pollLast());
				}
			}
		}
	}
	
	//method to implement floyd-warshall's algorithm
	public void floydWarshall() {
		
		//set infinity to largest possible integer
		final int INF = 999999;
		int intMatrix[][] = new int[directed.length][directed.length];
		
		for(int x = 0; x < directed.length; x++) {
			for(int y = 0; y < directed[x].length; y++) {
				
				if(directed[x][y].equals("INF")) {
					intMatrix[x][y] = INF;
				}
				else {
					intMatrix[x][y] = Integer.parseInt(directed[x][y]);
				}
			}
		}
		System.out.println();
		printMatrices(intMatrix);
		
		//not sure on this triple nested loop
		for(int k = 0; k < directed.length; k++) {
			for(int i = 0; i < directed.length; i++) {
				for(int j = 0; j < directed.length; j++) {
					
					if(intMatrix[i][k] + intMatrix[k][j] < intMatrix[i][j]) {
						intMatrix[i][j] = intMatrix[i][k] + intMatrix[k][j];
						System.out.println();
						printMatrices(intMatrix);
					}
				}
			}
		}
		
		System.out.println();
		System.out.println();
		System.out.println("Final Graph: ");
		System.out.println();
		printMatrices(intMatrix);	
	}
}
