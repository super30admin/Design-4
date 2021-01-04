package S30.Design_4;

import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

/*
Time Complexity : O(n)
Space Complexity : O(n)
Did this code successfully run on Leetcode : N/A - Ran in local IDE
Any problem you faced while coding this : None
*/

class SkipIterator implements Iterator<Integer> {
    private final Iterator<Integer> it;
    private final Map<Integer, Integer> map;
    private Integer nextEl;

    public SkipIterator(Iterator<Integer> it) {
        this.it = it;
        this.map = new HashMap<>();
        advance();
    }

    @Override
    public boolean hasNext() {
        return nextEl != null;
    }

    @Override
    public Integer next() {

        Integer result = nextEl;
        advance();
        return result;

    }

    public void skip(int num) {

        if(num == nextEl){
            advance();
        }
        map.put(num, map.getOrDefault(num,0)+1);
    }

    private void advance() {
        nextEl = null;
        while(nextEl == null && it.hasNext()){

            Integer potentialNextEl = it.next();
            if(map.containsKey(potentialNextEl)){
                map.put(potentialNextEl, map.get(potentialNextEl)-1);
                map.remove(potentialNextEl,0);
            }else{
                nextEl = potentialNextEl;
            }

        }


    }


    public static void main(String[] args) {
        SkipIterator itr = new SkipIterator(Arrays.asList(2, 3, 5, 6, 5, 7, 5, -1, 5, 10).iterator());
        System.out.println(itr.hasNext()); // true
        System.out.println(itr.next()); // returns 2
        itr.skip(5);
        System.out.println(itr.next()); // returns 3
        System.out.println(itr.next()); // returns 6 because 5 should be skipped
        System.out.println(itr.next()); // returns 5
        itr.skip(5);
        itr.skip(5);
        System.out.println(itr.next()); // returns 7
        System.out.println(itr.next()); // returns -1
        System.out.println(itr.next()); // returns 10
        System.out.println(itr.hasNext()); // false
        System.out.println(itr.next()); // error
    }
}
