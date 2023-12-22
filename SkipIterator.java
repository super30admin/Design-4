import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

public class SkipIterator implements Iterator<Integer> {
    Iterator<Integer> nit;
    HashMap<Integer, Integer> map;
    Integer nextEl;

    public SkipIterator(Iterator<Integer> it) {
        this.nit = it;
        this.map = new HashMap<>();
        advance();
    }

    private void advance() {//O(n)
        nextEl = null;
        while(nit.hasNext()){
            Integer el = nit.next();
            if(map.containsKey(el)){
                map.put(el, map.get(el) - 1);
                map.remove(el, 0);
            }else{
                nextEl = el;
                break;
            }
        }
    }

    public boolean hasNext() {
        return nextEl!=null;
    }

    public Integer next() {
        Integer result = nextEl;
        advance();
        return result;
    }

    /**
     * The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
     * This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
     */
    public void skip(int num) {
        if(num == nextEl){
            advance();
        }else{
            map.put(num, map.getOrDefault(num,0)+1);
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
        System.out.println(itr.next());  // error
    }
}


//Time Complexity:
//        SkipIterator(Iterator<Integer> it): O(n)
//        advance(): O(n)
//        hasNext(): O(1)
//        next(): O(1)
//        skip(int num): O(1)
//Space Complexity:
//        SkipIterator: O(n)
//        advance(): O(1)
//        hasNext() and next(): O(1)
//        skip(int num): O(1) + O(k), where k is the number of distinct elements to skip (worst-case scenario)