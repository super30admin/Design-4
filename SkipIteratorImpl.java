// Time Complexity : O(1)
// Space Complexity : O(N)
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : No


// Your code here along with comments explaining your approach
/*Approach:
1) in the skip iterator we have to implement the methods skip and hasNext and next methods
2) we use a map to keep a track of numbers of occurences of the skip method and also the number of times we need to skip the particular value
3) nextEl = current pointer to point the element
4) adavnce works and deletes the entry from hashmap if if we call skip
5)          nextEl=null;
            while(nextEl==null && it.hasNext())
            {
                Integer el = it.next();
                if(!map.containsKey(el))
                {
                    
                    nextEl = el;
                    
                }
                else
                {
                    map.put(el,map.get(el)-1);
                    map.remove(el,0);
                }
            }
6) nextEl is set to null (which is the nextElement)
then we check if there is another element then only we go inside the loop
then we check in map, if the entry does not exist, we assign the element nextEl = el;
-> Then decrement the entry

*/


import java.util.*;

public class SkipIteratorImpl{


    public static class SkipIterator implements Iterator<Integer>{
        
        public Iterator<Integer> it;
        Map<Integer,Integer> map = new HashMap<>();
        
        Integer nextEl; // next Element will the pointer at the integer at given time
        
        public SkipIterator(Iterator<Integer> it){
            this.it = it;
            this.map = new HashMap<>();
            advance(); // just to carry out other operations, as users would just give 3 calls -> skip, hasNext() and next()
            
            
            
        }
        
        public boolean hasNext()
        {
            return nextEl !=null;
            
        }
        
        
        public Integer next()
        {
            Integer temp = nextEl;
            advance();
            return temp;
            
        }
        
        public void advance()
        {
            nextEl=null;
            while(nextEl==null && it.hasNext())
            {
                Integer el = it.next();
                if(!map.containsKey(el))
                {
                    
                    nextEl = el;
                    
                }
                else
                {
                    map.put(el,map.get(el)-1);
                    map.remove(el,0);
                }
            }
            
            
        }
        
        private void skip(int val)
        {
            if(val==nextEl)
            {
                advance();
            }
            else
            {
                map.put(val,map.getOrDefault(val,0)+1);
                
            }
            
        }
        
        
    }
    


     public static void main(String []args){
       
    
    SkipIterator si = new SkipIterator(Arrays.asList(5,6,7,7,7,8,9,10).iterator());
    
    System.out.println(si.next());
    si.skip(7);
    si.skip(7);
    si.skip(7);
    System.out.println(si.hasNext());
    System.out.println(si.next());
    System.out.println(si.next());
    
    
       
       
     }
}