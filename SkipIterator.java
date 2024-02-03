import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SkipIterator {
    Integer nextEle;
    Map<Integer, Integer> skipMap = new HashMap<>();
    Iterator<Integer> it;
    public SkipIterator(Iterator<Integer> it) {
        this.it = it;
        advance(it);
    }

    private void advance(Iterator<Integer> it) {
        while (it.hasNext()) {
            nextEle = it.next();
            if (skipMap.containsKey(nextEle)) {
                skipMap.put(nextEle, skipMap.get(nextEle) - 1);
                skipMap.remove(nextEle, 0);
            } else {
                break;
            }
        }
    }

    public boolean hasNext() {
        return nextEle != null;
    }

    public Integer next() {
        Integer res = nextEle;
        advance(it);
        return res;
    }

    /**
     * The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
     * This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
     */
    public void skip(int val) {
        if (nextEle != val) {
            skipMap.put(val, skipMap.getOrDefault(val, 0) + 1);
        } else {
            advance(it);
        }
    }
}

class Main {
    public static void main(String[] args) {
        SkipIterator it = new SkipIterator(Arrays.asList(5,6,7,5,6,8,9,5,5,6,8).iterator());

        System.out.println(it.hasNext());// true // nextEl = 5
        it.skip(5);//  nextEl = 6
        System.out.println(it.next()); //6   nextEl = 7
        it.skip(5);
        System.out.println(it.next());// 7 nextEl = 6
        System.out.println(it.next()); // 6nextEl = 6
        it.skip(8); //
        it.skip(9); //
        System.out.println(it.next()); // 5
        System.out.println(it.next()); //5
        System.out.println(it.next());//6
        System.out.println(it.hasNext());// true
        it.skip(8);
        System.out.println(it.hasNext()); //false
        System.out.println(it.next());// 8
    }
}
