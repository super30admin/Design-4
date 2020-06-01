// Time Complexity : O(1) for constructor and hasNext, but O(n) for operation next,advance and skip. 
// Space Complexity : O(N) element in the iterator and map;

// Did this code successfully run on Leetcode :yes. 

// Any problem you faced while coding this :


// Your code here along with comments explaining your approach
//ion
//Success
//Details 
//Runtime: 

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SkipIterator implements Iterator<Integer> {
   private Map<Integer,Integer> map;
   private Iterator<Integer> it;
   private Integer nextElement;
   public SkipIterator(Iterator<Integer> it) {
       map=new HashMap<>();
       this.it=it;
       advance();
   }



    public boolean hasNext() {
       return nextElement!=null;
    }

     public Integer next() {
        Integer tmp=nextElement;
        advance();
        return tmp;
     }

    /**
    * The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
    * This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
    */
    public void skip(int val) { //o(N) IF val==nextElement and skip map has n skips
        if (val==nextElement){
            advance();
        }else{
            map.put(val,map.getOrDefault(val,0)+1);
        }
    }
    private void advance() {//o(N) IF WE CALL SKIP N TIMES;
        nextElement=null;
        while (nextElement==null && it.hasNext()){
            Integer e= it.next();
            if (!map.containsKey(e)){
                nextElement=e;
            }else {
                map.put(e,map.getOrDefault(e,0)-1);
                map.remove(e,0);
            }
        }
    }

public static void main(String[]args){
        SkipIterator itr = new SkipIterator(Arrays.asList(2, 3, 5, 6, 5, 7, 5, -1, 5, 10).iterator());
    System.out.println(itr.hasNext());; // true
    System.out.println(itr.next()); // returns 2
    itr.skip(5);
    System.out.println(itr.next()); // returns 3
    System.out.println( itr.next()); // returns 6 because 5 should be skipped
    System.out.println( itr.next()); // returns 5
     itr.skip(5);
     itr.skip(5);
    System.out.println(    itr.next()); // returns 7
    System.out.println(    itr.next()); // returns -1
    System.out.println(    itr.next()); // returns 10
    System.out.println(    itr.hasNext()); // false
    System.out.println(    itr.next()); // error
   }
}
