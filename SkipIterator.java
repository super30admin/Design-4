import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

//Time Complexity: O(1)
//Space Complexity: O(1) or O(n) bcz of hashmap
class SkipIterator1 implements Iterator<Integer> {
    private Iterator<Integer> it ;
    private HashMap<Integer, Integer> map;
    private Integer nextEle;

    public SkipIterator1(Iterator<Integer> it) {
        this.it = it;
        this.map = new HashMap<>();
        advance();
    }

    @Override
    public boolean hasNext() {
        if (nextEle != null) return true;
        return false;
    }

    @Override
    public Integer next() {

        if (!hasNext()) throw new RuntimeException("empty");


        Integer el = nextEle;
        advance();

        return el;
    }

    public void skip(int num) {
        if (!hasNext()) throw new RuntimeException("empty");
        if (nextEle == num)
            advance();
        else map.put(num, map.getOrDefault(num, 0) + 1);

    }

    private void advance() {
        nextEle = null;
        while (nextEle == null && it.hasNext()) {
            Integer temp = it.next();
            if (!map.containsKey(temp)) {
                nextEle = temp;
            }
            else {
                map.put(temp, map.get(temp) - 1);

                if (map.get(temp).equals(0)) {
                    map.remove(temp);
                }
            }
        }
    }
}

public class SkipIterator {
    public static void main(String[] args) {
        SkipIterator1 it = new SkipIterator1(Arrays.asList(2, 3, 5, 6, 5, 7, 5, -1, 5, 10).iterator());
        System.out.println(it.hasNext());
        System.out.println(it.next());
        it.skip(5);
        System.out.println(it.next()); // returns 3
        System.out.println(it.next()); // returns 6 bcz 5 has skipped
        System.out.println(it.next()); // returns 5
        it.skip(5);
        it.skip(5);
        System.out.println(it.next()); // returns 7
        System.out.println(it.next()); // returns -1
        System.out.println(it.next()); // returns 10
        System.out.println(it.hasNext()); // false
        // System.out.println(it.next()); // error


    }
}
