// Time Complexity :  Advance - O(n)  Skip O(n) next O(n)
// Space Complexity : O(n) 
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No

import java.util.*;

public class SkipIterator implements Iterator<Integer>{
    Iterator<Integer> nit;
    Map<Integer,Integer> skipMap;
    Integer nextEl;

    public SkipIterator(Iterator<Integer> it){
        this.nit = it;
        this.skipMap = new HashMap<>();
        advance();
    }

    private void advance(){
        this.nextEl = null;
        while (nextEl == null && nit.hasNext()){
            int el = nit.next();
            if (skipMap.containsKey(el)){
                skipMap.put(el, skipMap.get(el)-1);
                skipMap.remove(el,0);

            } else {
                nextEl = el;
            }
        }
    }

    @Override
    public boolean hasNext() {
        return nextEl != null;
    }

    @Override
    public Integer next() {
        Integer re = nextEl;
        advance();
        return re;
    }

    public void skip(int num){
        if(num == nextEl){
            advance();
        } else {
            skipMap.put(num, skipMap.getOrDefault(num, 0)+1);
        }
    }

    public static void main(String[] args) {
        SkipIterator it = new SkipIterator(Arrays.asList(5,6,7,5,6,8,9,5,5,6,8).iterator());
        System.out.println(it.hasNext());
        it.skip(5);
        System.out.println(it.next());
        it.skip(5);
        System.out.println(it.next());
        System.out.println(it.next());
        it.skip(8);
        it.skip(9);
        System.out.println(it.next());
        System.out.println(it.next());
        System.out.println(it.next());
        System.out.println(it.next());
        System.out.println(it.hasNext());
    }
}