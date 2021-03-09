package pedigree;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Heap {
	
    //The heap
    private Object[] H;
    private Comparator c;
    
    //The number of elements
    private int n;

    public Heap(int n, Comparator c){
        H = new Object[n];
        this.n = 1;
    }

    private void swim(Object v, int i){

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

    private void sink(Object v, int i){

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

    public void insert(Comparable x) {
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

	public Object deleteMin() {

        if(size() == 0) return null;

        if(size() == 1){
            //Keep the a backup of the root (minimal element)
        	Object min = H[1];

            H[1] = null;
            
            //Decrement the size
            n--;

            return min;
        }

        //Keep the a backup of the root (minimal element)
        Object min = H[1];

        //Sink (with the removed last element)
        Object v = H[size()-1];
        sink(v, 1);
        H[size()-1] = null;
        
        //Decrement the size
        n--;

        return min;
    }

    public int size() {
        return n;
    }

    public Object peek() {
        return H[1];
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
    
    public static void main(String[] args) {
    	Heap h = new Heap(2, );
    	h.insert(1);
    	h.insert(2);
    	h.insert(3);
    	h.insert(4);
    	System.out.println(h);
    	System.out.println();
    	System.out.println(h.peek());
    	System.out.println(h.deleteMin());
    	System.out.println(h.peek());
    	System.out.println();
    	System.out.println(h);
    	h.insert(10);
    	h.insert(3);
    	h.insert(3);
    	h.insert(6);
    	System.out.println(h);
    		
    }

}
