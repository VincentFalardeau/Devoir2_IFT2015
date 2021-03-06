package pedigree;

import java.util.Comparator;

/**
 * @author Vincent Falardeau
 * @author Karim Boumghar
 * */

/**
 * Compares 2 Events according to their time
 * */
public class EventComparator implements Comparator<Event> {
  public int compare(Event a, Event b) {
      return Double.compare(a.getTime(), b.getTime());
  }
}
