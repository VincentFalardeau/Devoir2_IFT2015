package pedigree;


import java.util.Arrays;
import java.util.Comparator;

/**
 * PiorityQueue implemented with a Binary Heap.  
 * The binary heap is stored as an array of Object.
 * */
public class PQ<T>{
	
    private Object[] H;	 //The array that stores the Heap
    private int n;		 //The number of elements
	private Comparator c;//How we compare the elements of the Heap
   
	public PQ(int n, Comparator c){
        H = new Object[n];
        this.n = 0;
        this.c = c;
    }
    
	public PQ(Comparator c){
        H = new Object[8];
        this.n = 0;
        this.c = c;
    }
	
	public int size() {
        return n;
    }
    
    public boolean isEmpty() {
    	return size() == 0;
    }

    /**
     * Takes a look at the root
     * */
    public T peek() {
        return (T)H[0];
    }
    
    /**
     * Gives the ith element of H
     * */
    public T get(int i) {
    	return (T)H[i];
    }
    
    public void insert(T x) {
    	if(n == H.length) resize();
    	
    	H[n] = x;
        swim(x, n);
        
        //Increment the size
        n++;
    }
    
    /**
     * Heapifies the Heap according to the given comparator
     * @param c: given comparator
     * */
    public void heapify(Comparator c){
    	
    	setComparator(c);
    	
        for(int i = n/2; i >= 0; i--){
            sink((T)H[i], i);
        }
    }
    
    /**
     * Sets a new comparator for the Heap
     * @param c: the new comparator
     * */
    public void setComparator(Comparator c) {
    	this.c = c;
    }
    
    /**
     * Deletes and returns the root element
     * @return the minimal element
     * */
    public T deleteMin() {

        if(n == 0) return null;

        if(n == 1){
            //Keep the a backup of the root (minimal element)
        	Object min = H[0];

            H[0] = null;
            
            //Decrement the size
            n--;

            return (T) min;
        }

        //Keep the a backup of the root (minimal element)
        Object min = H[0];

        //Sink (with the removed last element)
        Object v = H[n-1];
        sink((T)v, 0);
        H[n-1] = null;
        
        //Decrement the size
        n--;

        return (T)min;
    }

    /**
     * Gives the array in which the heap is stored
     * @return H
     * */
    public Object[] toArray() {
        return H;
    }

    @Override
    public String toString(){

    	return Arrays.toString(H);
    }    

    /**
     * Resizes H
     * */
    private void resize() {

        //Make a copy with twice the size
        Object[] copy = new Object[H.length * 2];

        //Copy the elements
        for (int i = 0; i < n; i++) {
            copy[i] = H[i];
        }
        H = copy;
    }

    
	/**
	 * Swim algorithm: swims v from i
	 * 
	 * @param v: the value of the swimming element
	 * @param i: the start position of the swimming element
	 * */
    private void swim(T v, int i){

        //The parent's index
        //Notice: automatic floor with Integer
        int pIndex = Math.floorDiv(i-1,2);

        //While not up to the root, and while parent is greater than v
        while(pIndex >= 0 && c.compare(H[pIndex], v) > 0){

            //Exchange the parent with the current node
            H[i] = H[pIndex];

            //Current node = its parent
            i = pIndex;

            //Parent = next parent
            pIndex = Math.floorDiv(i-1,2);
        }

        //We found the place for v!
        H[i] = v;
    }

    /**
     * Sink algorithm: sinks v from i
     * 
     * @param v: the value of the sinking element
     * @param i: the start position of the sinking element
     * */
    private void sink(T v, int i){

        //The smallest child index
        int cIndex = minChild(i);

        //While a smallest child can be found, and while it is smaller than v
        while(cIndex >= 0 && c.compare(H[cIndex],v) < 0){

            //Exchange the parent with its smallest children
            H[i] = H[cIndex];

            //Parent = its smallest children
            i = cIndex;

            //Smallest children of Parent
            cIndex = minChild(i);
        }

        //We found the place for v!
        H[i] = v;
    }

    /**
     * Gives the minimal child (left of right) of the ith element
     * @param i: index of the parent in H
     * @return its smallest child
     * */
    private int minChild(int i){

        int c1 = (2 * i) + 1;
        int c2 = (2 * i) + 2;
        int minChild = -1;

        if(c1 < n) minChild = c1;
        if(c2 < n && c.compare(H[c2], H[c1]) < 0) minChild = c2;

        return minChild;
    }
    
    /**
     * Testing the PriorityQueue
     * */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) {
    	PQ pq = new PQ(new IntegerComparator());
    	pq.insert(5);
    	pq.insert(6);
    	pq.insert(7);
    	pq.insert(9);
    	pq.insert(100);
    	pq.insert(1);
    	pq.insert(8);
    	pq.insert(1);
    	pq.insert(8);
    	pq.insert(10);
    	pq.insert(-4);
    	pq.insert(1);
    	System.out.println(pq);
    	System.out.println(pq.deleteMin());
    	System.out.println(pq.deleteMin());
    	System.out.println(pq.deleteMin());
    	System.out.println(pq.deleteMin());
    	System.out.println(pq.deleteMin());
    	System.out.println(pq.deleteMin());
    	System.out.println(pq.deleteMin());
    	System.out.println(pq.deleteMin());
    	System.out.println(pq.deleteMin());
    	System.out.println(pq.deleteMin());
    	System.out.println(pq.deleteMin());
    	System.out.println(pq.deleteMin());
    	System.out.println(pq.deleteMin());
    	System.out.println(pq.deleteMin());
    	System.out.println(pq.deleteMin());
    	System.out.println(pq.deleteMin());
    	System.out.println(pq);
    }
}

