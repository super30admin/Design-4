import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SkipIterator implements Iterator<Integer> {

    Map<Integer, Integer> map;
    Integer nextEle;
    Iterator<Integer> iterator;
    public SkipIterator(Iterator<Integer> iterator) {
        map = new HashMap<>();
        this.iterator = iterator;
        advance();
    }

    //O(n)
    private void advance() {
        nextEle = null;
        while (nextEle==null && iterator.hasNext()) {
            Integer ele = iterator.next();
            if (map.containsKey(ele)) {
                map.put(ele, map.get(ele)-1);
                map.remove(ele, 0);
            }
            else nextEle = ele;
        }
    }
    //O(1)
    @Override
    public boolean hasNext() {
        return nextEle!=null;
    }

    //O(n)
    private void skip(Integer ele) {
        if (ele!=nextEle) {
            map.put(ele, map.getOrDefault(ele,0)+1);
        }
        else advance();
    }

    //O(n)
    @Override
    public Integer next() {
        Integer res = nextEle;
        advance();
        return res;
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
