/**
 * Time Complexity : All operations = O(1)
 * Space Complexity : O(n) hashmap for skip map where n is the total numbers to be skipped
 */

import java.util.*;
class Solution implements Iterator<Integer> {
    private final Iterator<Integer> it;
    private final Map<Integer, Integer> count;
    private Integer nextEl;

    public Solution(Iterator<Integer> it) {
        count = new HashMap<>();                                        
        this.it = it;
        advance();

    }

    @Override
    public boolean hasNext() {
        return nextEl != null;                                      
       
    }

    @Override
    public Integer next() {
        int t = nextEl;
        advance();
        return t;                                                                   
            
    }

    public void skip(int num) {
            if(num == nextEl){                                                              
                advance();
            }  else {
                count.put(num, count.getOrDefault(num, 0)+1);                                          
            }
    }

    private void advance() {
        nextEl  = null;
        while(nextEl ==  null && it.hasNext()==true){                                           
            int ele = it.next();
            if(!count.containsKey(ele)){                                                
                    nextEl = ele;
            } else {
                    count.put(ele, count.get(ele)-1);                                  
                    count.remove(ele, 0);                                                
                }
            }
            
        }

}

public class SkipIterator {
        public static void main(String[] args) {
        Solution it = new Solution(Arrays.asList(1, 2, 3).iterator());
        System.out.println(it.hasNext());
        it.skip(2);
        it.skip(1);
        it.skip(3);
        System.out.println(it.hasNext());
    }
}