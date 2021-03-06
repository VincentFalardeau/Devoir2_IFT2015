package pedigree;

import java.util.Comparator;

/**
 * @author Vincent Falardeau
 * @author Karim Boumghar
 * */

/**
 * Compares 2 Integers
 *
 * Notice: used for testing {@link pedigree.PQ}
 * */
public class IntegerComparator implements Comparator<Integer> {
	
	public int compare(Integer a, Integer b) {
		return Integer.compare(a, b);
	}
}
