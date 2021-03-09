package pedigree;

public class Pedigree {
	
	public void simulate() {
		
	}
	
	public static void main(String[] args) {
		EventComparator ec = new EventComparator();
		Heap h = new Heap(ec);
		h.insert(new Event(Event.Type.Birth, new Sim(null), 2023.0));
		h.insert(new Event(Event.Type.Birth, new Sim(null), 2022.0));
		h.insert(new Event(Event.Type.Birth, new Sim(null), 2021.0));
		h.insert(new Event(Event.Type.Birth, new Sim(null), 2020.0));
		System.out.println(h.deleteMin());
		System.out.println(h.deleteMin());
		System.out.println(h.deleteMin());
		h.insert(new Event(Event.Type.Birth, new Sim(null), 3000.0));
		h.insert(new Event(Event.Type.Birth, new Sim(null), 1000.0));
		System.out.println(h.deleteMin());
		System.out.println(h.deleteMin());
		System.out.println(h.deleteMin());
		System.out.println(h.deleteMin());
		
	}

}
