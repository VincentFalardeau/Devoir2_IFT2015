package pedigree;

import java.util.Comparator;

//Comparator for our event (using time)
public class EventComparator implements Comparator<Event> {
  public int compare(Event a, Event b) {
      return Double.compare(a.getTime(), b.getTime());
  }
}
