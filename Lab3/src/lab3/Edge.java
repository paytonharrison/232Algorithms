package lab3;

public class Edge {

	//instance variables
	public char start;		//label i.e. 'A'
	public char end;
	public int weight;
	public int startIndex;
	public int endIndex;
	
	//constructor
	public Edge(char s, char e, int w, Vertex[] list) {
		start = s;
		end = e;
		weight = w;
		for(int i = 0; i < list.length; i++) {
			if(list[i].label == e) {
			endIndex = i;
			}
		}
		for(int j = 0; j < list.length; j++) {
			if(list[j].label == s) {
				startIndex = j;
			}
		}
	}
	
	//method to print the edges and weights...used for output of spanning trees
	public void printEdge() {
		System.out.println("   " + start + " - " + end + " : " + weight);
	}
	
	public int getWeight() {
		return weight;
	}
}
