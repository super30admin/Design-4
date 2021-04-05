// Time Complexity : O(1) for hasNext() and O(N) for next() and skip()
// Space Complexity : O(N)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : NA


// Your code here along with comments explaining your approach

// "static void main" must be defined in a public class.
class skipIterator implements Iterator<Integer> {
    
    Iterator<Integer> it;
    HashMap<Integer, Integer> map;
    Integer nextEl;
    
    public skipIterator(Iterator<Integer> it) {
        this.it = it;
        map = new HashMap<>();
        advance();
    }
    
    public boolean hasNext() {
        return nextEl != null;
    }
    
    public Integer next() {
        Integer tmp = nextEl;
        advance();
        return tmp;
    }
    
    public void skip(int val) {
        if(val == nextEl) {
            advance();
        }
        else
        {
            map.put(val, map.getOrDefault(val, 0) + 1);
        }
    }
    
    public void advance() {
        nextEl = null;
        while(nextEl == null && it.hasNext()) {
            Integer element = it.next();
            if(map.containsKey(element)) {
                map.put(element, map.get(element) - 1);
                map.remove(element, 0);
            }
            else 
            {
                nextEl = element;
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        
        skipIterator itr = new skipIterator(Arrays.asList(2, 3, 4, 5, 6, 5, 7).iterator());
        System.out.println(itr.hasNext());
        System.out.println(itr.next());
        itr.skip(5);
        System.out.println(itr.next());
        System.out.println(itr.next());
        System.out.println(itr.next());
        itr.skip(5);
        System.out.println(itr.next());
        System.out.println(itr.hasNext());
    }
}