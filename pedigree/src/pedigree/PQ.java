package pedigree;


import java.util.Arrays;
import java.util.Comparator;


public class PQ<T>{
	
    //The heap
    private Object[] H;
    private Comparator c;
    
    //The number of elements
    private int n;

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

    //Gives the index of the smallest child
    private int minChild(int i){

        int c1 = (2 * i) + 1;
        int c2 = (2 * i) + 2;
        int minChild = -1;

        if(c1 < n) minChild = c1;
        if(c2 < n && c.compare(H[c2], H[c1]) < 0) minChild = c2;

        return minChild;
    }

    public void insert(T x) {
    	if(n == H.length) resize();
    	H[n] = x;
        swim(x, n);
        
        //Increment the size
        n++;
    }
    
    public void heapify(Comparator c){
    	
    	setComparator(c);
    	
        for(int i = n/2; i >= 0; i--){
            sink((T)H[i], i);
        }
    }
    
    public void setComparator(Comparator c) {
    	this.c = c;
    }

    //Resizes the queue
    private void resize() {

        //Make a copy with twice the size
        Object[] copy = new Object[H.length * 2];

        //Copy the elements
        for (int i = 0; i < n; i++) {
            copy[i] = H[i];
        }
        H = copy;
    }

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

    public int size() {
        return n;
    }
    
    public boolean isEmpty() {
    	return n == 0;
    }

    public T peek() {
        return (T)H[0];
    }
    
    public T get(int i) {
    	return (T)H[i];
    }

    public Object[] toArray() {
        return H;
    }

    @Override
    public String toString(){

//        String s = "";
//
//        int i = 1;
//        int j = 1;
//        while(i < size()){
//
//            s += H[i-1];
//            s += ",";
//
//            if(i == j){
//                j = j * 2 + 1;
//                s += "\n";
//            }
//
//            i++;
//        }
//
//        return s;
    	return Arrays.toString(H);

    }    
    
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

