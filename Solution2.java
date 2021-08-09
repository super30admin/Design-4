//Time complexity: hasNext(): O(1), next(): O(N) in worst case, O(1) amortized.

import java.util.*;
// "static void main" must be defined in a public class.
class SkipIterator implements Iterator {
    Iterator<Integer> it; 
    HashMap<Integer, Integer> map; 
    Integer nextElement; 
    public SkipIterator(Iterator<Integer> it) {
        this.it = it;
        map = new HashMap<>(); 
        advance();
    }
    
    public void advance() {
        nextElement = null;
        while(nextElement == null && it.hasNext()) {
            Integer element = it.next();
            if(!map.containsKey(element)) {
                nextElement = element; 
            } else {
                map.put(element, map.get(element)-1); 
                if(map.get(element) == 0) {
                    map.remove(element); 
                }
            }
        }
    }

    public boolean hasNext() {
        return nextElement != null; 
    }

    public Integer next() {
        Integer element = nextElement;
        advance();
        return element; 
    }

    /**
    * The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
    * This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
    */ 
    public void skip(int val) {
        if(val == nextElement) advance();
        else map.put(val, map.getOrDefault(val, 0)+1); 
    }
}

public class Solution2 {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(2, 3, 5, 6, 5, 7, 5, -1, 5, 10);
        Iterator<Integer> it = list.iterator(); 
        SkipIterator itr = new SkipIterator(it);
        System.out.println(itr.hasNext()); 
        System.out.println(itr.next()); 
        itr.skip(5); 
        System.out.println(itr.next()); 
        System.out.println(itr.next()); 
        System.out.println(itr.next());
        itr.skip(5); 
        itr.skip(5); 
        System.out.println(itr.next()); 
        System.out.println(itr.next()); 
        System.out.println(itr.next()); 
        System.out.println(itr.hasNext()); 
        System.out.println(itr.next());
    }
}