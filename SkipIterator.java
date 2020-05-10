// Time Complexity : different
// Space Complexity : O(1)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

class SkipIterator implements Iterator<Integer> {
    HashMap<Integer, Integer> map;
    Iterator<Integer> it;
    Integer nextElement;

    public SkipIterator(Iterator<Integer> it) {
        map = new HashMap<>();
        this.it = it;
        advance(); //To put next pointer at right position
    }

    private void advance() { // Time: O(n)
        nextElement = null;
        while(nextElement == null && it.hasNext()){
            Integer temp = it.next();
            if(!map.containsKey(temp)){
                nextElement = temp;
            } else {
                map.put(temp, map.getOrDefault(temp, 0) - 1);
                map.remove(temp, 0);
            }
        }
    }

    @Override
    public boolean hasNext() {  // Time : O(1)
        return nextElement != null;
    }

    @Override
    public Integer next() {   // Time : O(n)
        Integer temp = nextElement;
        advance();
        return temp;
    }
    /**
     * The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
     * This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
     */
    public void skip(int val) { // Time: O(n)
        if(nextElement == val){
            advance();
        } else {
            map.put(val, map.getOrDefault(val,0) + 1);
        }
    }

    public static void main(String args[]){
            SkipIterator it = new SkipIterator(Arrays.asList(1, 2, 3).iterator());
            System.out.println(it.hasNext());
            it.skip(2);
            it.skip(1);
            it.skip(3);
            System.out.println(it.hasNext());
    }
}