package pedigree;

import java.util.Comparator;

//Comparator for age of sims (instead of death)
public class BirthComparator implements Comparator<Sim> {

  public int compare(Sim a, Sim b) {
      return Double.compare(a.getBirthTime(), b.getBirthTime());
  }
}
