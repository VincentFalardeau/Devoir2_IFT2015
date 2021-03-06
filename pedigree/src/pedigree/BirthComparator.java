package pedigree;

import java.util.Comparator;

/**
 * @author Vincent Falardeau
 * @author Karim Boumghar
 * */

/**
 * Compares 2 Sims according to their age (youngest first)
 *
 * */
public class BirthComparator implements Comparator<Sim> {

  public int compare(Sim a, Sim b) {
      return Double.compare(b.getBirthTime(), a.getBirthTime());
  }
}
