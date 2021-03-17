package pedigree;

import java.util.Comparator;

/**
 * Compares 2 Sims according to their date of death
 * */
public class DeathComparator implements Comparator<Sim> {

	  public int compare(Sim a, Sim b) {
	      return a.compareTo(b);
	  }

}
