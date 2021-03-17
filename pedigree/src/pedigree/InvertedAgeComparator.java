package pedigree;

import java.util.Comparator;

/**
 * Compares 2 Sims according to their age
 * 
 * Notice: the greatest birth date is the youngest
 * */
public class InvertedAgeComparator implements Comparator<Sim> {

	
	public int compare(Sim a, Sim b) {
		return Double.compare(b.getBirthTime(), a.getBirthTime());
	}
}
