package pedigree;

import java.util.Comparator;

public class AgeComparator implements Comparator<Sim> {

	  public int compare(Sim a, Sim b) {
	      return a.compareTo(b);
	  }

}
