package pedigree;

import java.util.Comparator;

/**
 * Compares 2 Sims according to their date of birth
 * */
public class BirthComparator implements Comparator<Sim> {

	
	public int compare(Sim a, Sim b) {
		return Double.compare(a.getBirthTime(), b.getBirthTime());
	}
}
