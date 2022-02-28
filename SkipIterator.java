import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

// TC O(N). Average O(1)
// SC O(N)
public class SkipIterator implements Iterator<Integer> {
    Iterator<Integer> itr;
    HashMap<Integer, Integer> skipMap;
    Integer nextEle;

    public SkipIterator(Iterator<Integer> it) {
        this.itr = it;
        skipMap = new HashMap<>();
        advance();
    }

    @Override
    public boolean hasNext() {
        return nextEle != null;
    }

    @Override

    // 5,6,7,5,6,8,9,5,5,6
    public Integer next() {
        Integer temp = nextEle;
        advance();
        return temp;
    }

    public void skip(int num) {
        if (num == nextEle) {
            advance();
        } else {
            skipMap.put(num, skipMap.getOrDefault(num, 0) + 1);
        }
    }

    private void advance() {
        // Get the next valid nextEle of skip iterator
        nextEle = null;
        while (itr.hasNext() && nextEle == null) {
            Integer it = itr.next();
            if (skipMap.containsKey(it)) {
                skipMap.put(it, skipMap.get(it) - 1);
                if (skipMap.get(it) == 0) {
                    skipMap.remove(it);
                }
            } else {
                nextEle = it;
            }
        }
        return;
    }

    public static void main(String[] args) {

        SkipIterator it = new SkipIterator(Arrays.asList(5, 6, 7, 5, 6, 8, 9, 5, 5, 6).iterator());

        System.out.println(it.hasNext());
        it.skip(6);// 6 --nextEl
        it.skip(5);// 6
        System.out.println(it.next()); // 7
        it.skip(5);
        System.out.println(it.next());// 6
        System.out.println(it.next()); // 8
        it.skip(5);
        it.skip(8);

        System.out.println(it.next());

        System.out.println(it.next());
        System.out.println(it.next());
        System.out.println(it.next());
        // it.skip(1);

        // it.skip(3);

        System.out.println(it.hasNext());

    }

}