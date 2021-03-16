package pedigree;

import java.util.Comparator;

public class IntegerComparator implements Comparator<Integer> {
	
	public int compare(Integer a, Integer b) {
		return Integer.compare(a, b);
	}
}
