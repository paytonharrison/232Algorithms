package lab3;

import java.util.*;

//tells the priority queue to compare edge WEIGHTS

public class Compare implements Comparator<Edge> {
	@Override
	public int compare(Edge a, Edge b) {
		if(a.getWeight() < b.getWeight()) {
			return -1;
		}
		if(a.getWeight() > b.getWeight()) {
			return 1;
		}
		//if a and b are the same
		return 0;
	}
}
