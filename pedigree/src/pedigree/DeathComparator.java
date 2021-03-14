package pedigree;

import java.util.Comparator;

public class DeathComparator implements Comparator<Sim> {

	  public int compare(Sim a, Sim b) {
	      return a.compareTo(b);
	  }

}
