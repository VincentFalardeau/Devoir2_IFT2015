package pedigree;


import java.util.Comparator;


public class Heap<T>{
	
    //The heap
    private Object[] H;
    private Comparator c;
    
    //The number of elements
    private int n;

    public Heap(int n, Comparator c){
        H = new Object[n];
        this.n = 1;
        this.c = c;
    }
    
    public Heap(Comparator c){
        H = new Object[8];
        this.n = 1;
        this.c = c;
    }

    private void swim(T v, int i){

        //The parent's index
        //Notice: automatic floor with Integer
        int pIndex = i/2;

        //While not up to the root, and while parent is greater than v
        while(pIndex > 0 && c.compare(H[pIndex], v) > 0){

            //Exchange the parent with the current node
            H[i] = H[pIndex];

            //Current node = its parent
            i = pIndex;

            //Parent = next parent
            pIndex = i/2;
        }

        //We found the place for v!
        H[i] = v;
    }

    private void sink(T v, int i){

        //The smallest child index
        int cIndex = minChild(i);

        //While a smallest child can be found, and while it is smaller than v
        while(cIndex > 0 && c.compare(H[cIndex],v) < 0){

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

        int c1 = 2 * i;
        int c2 = (2 * i) + 1;
        int minChild = 0;

        if(c1 < size()) minChild = c1;
        if(c2 < size() && c.compare(H[c2], H[c1]) < 0) minChild = c2;

        return minChild;
    }

    public void insert(T x) {
    	if(size() == H.length) resize();
    	H[size()] = x;
        swim(x, size());
        
        //Increment the size
        n++;
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

        if(size() == 0) return null;

        if(size() == 1){
            //Keep the a backup of the root (minimal element)
        	Object min = H[1];

            H[1] = null;
            
            //Decrement the size
            n--;

            return (T) min;
        }

        //Keep the a backup of the root (minimal element)
        Object min = H[1];

        //Sink (with the removed last element)
        Object v = H[size()-1];
        sink((T)v, 1);
        H[size()-1] = null;
        
        //Decrement the size
        n--;

        return (T)min;
    }

    public int size() {
        return n;
    }
    
    public boolean isEmpty() {
    	return size() == 1;
    }

    public T peek() {
        return (T)H[1];
    }
    
    public T get(int i) {
    	return (T)H[i];
    }

    @Override
    public String toString(){

        String s = "";

        int i = 1;
        int j = 1;
        while(i < size()){

            s += H[i];
            s += ",";

            if(i == j){
                j = j * 2 + 1;
                s += "\n";
            }

            i++;
        }

        return s;

    }
    
    

}