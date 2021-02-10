import java.util.HashMap;
import java.util.Iterator;


// Time Complexity : O(1)
// Space Complexity : O(n)
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : getting started

// Your code here along with comments explaining your approach
class SkipIterator implements Iterator<Integer> {
    HashMap<Integer,Integer> map = new HashMap<>(); //k = number to be skipped, v = number of times to be skipped
    Iterator<Integer> it; //maintain at class level
    Integer nextEl; //maintain at class level

    public SkipIterator(Iterator<Integer> it) {
        this.it = it;
        map = new HashMap<>();
        advance(); //for putting nextElpointer at next value
    }

    public boolean hasNext() {
        return nextEl != null;
    }

    public Integer next() {
        int result = nextEl;
        advance();
        return result;
    }

    /**
     * The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
     * This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
     */
    public void skip(int val) {
        if(val == nextEl){
            advance();
        } else{
            map.put(val, map.getOrDefault(val,0)+1);
        }
    }

    //advance if key is not in map else decrement count in map or remove if count is 0
    private void advance(){
        nextEl = null;
        while(nextEl == null && it.hasNext()){
            Integer el = it.next();
            if(!map.containsKey(el)){
                nextEl = el;
            }else{
                map.put(el, map.get(el)-1);
                map.remove(el,0);
            }
        }
    }
}