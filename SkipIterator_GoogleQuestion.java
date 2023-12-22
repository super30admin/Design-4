//approach learned from Discussion section on Leetcode 
class SkipIterator implements Iterator<Integer> {
    HashMap<Integer, Integer> skippedElements = new HashMap<>();
    Iterator<Integer> iterator;

    public SkipIterator(Iterator<Integer> it) {
        this.iterator = it;
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public Integer next() {
        int nextElement = -1;
        if (!iterator.hasNext()) {
            return -1;
        }
        nextElement = iterator.next();
        if (skippedElements.containsKey(nextElement)) {
            skippedElements.put(nextElement, skippedElements.get(nextElement) - 1);
            if (skippedElements.get(nextElement) == 0) {
                skippedElements.remove(nextElement);
            }
            if (!iterator.hasNext())
                return -1;
            return iterator.next();
        }
        return nextElement;
    }

    /**
     * The input parameter is an int, indicating that the next element equals 'val'
     * needs to be skipped.
     * This method can be called multiple times in a row. skip(5), skip(5) means
     * that the next two 5s should be skipped.
     */
    public void skip(int val) {
        skippedElements.put(val, skippedElements.getOrDefault(val, 0) + 1);
    }
}

// approach -2 learned in class
// "static void main" must be defined in a public class.
public class Main {
    private static class SkipIterator implements Iterator<Integer> {
        Iterator<Integer> it;
        Integer nextEl;
        HashMap<Integer, Integer> skipped;

        public SkipIterator(Iterator<Integer> it) {
            this.it = it;
            skipped = new HashMap<>();
            advance(); // when called first time automatically calls advance.
        }

        public Integer printonly() {
            if (skipped.containsKey(-1)) {
                System.out.println("yes : " + skipped.get(-1));
                return skipped.get(-1);
            }

            return 492342;
        }

        public Integer next() {
            Integer result = nextEl;
            advance();
            return result;
        }

        public boolean hasNext() {
            return nextEl != null;
        }

        public void skip(Integer val) {
            if (nextEl == val)
                advance();
            else
                skipped.put(val, skipped.getOrDefault(val, 0) + 1);
        }

        public void advance() {
            // setting enxtel to null
            nextEl = null;

            while (nextEl == null && it.hasNext()) {
                nextEl = it.next();
                // check if skipped map has this value!
                if (skipped.containsKey(nextEl)) {
                    int cnt = skipped.get(nextEl);
                    cnt--;

                    if (cnt == 0) {
                        skipped.remove(nextEl);
                    } else {
                        skipped.put(nextEl, cnt);
                    }
                    nextEl = null;
                    // make sure to set nextEl to null, so when we have enxt operation, we dont mess
                    // with earlier values/
                } // we dont need to print, as print from main will call the next(), next() will
                  // call advance().
            }

        }
    }

    // calling skipIterator
    public static void main(String[] args) {
        SkipIterator itr = new SkipIterator(Arrays.asList(2, 3, 5, 6, 5, 7, 5, -1, 5, 10, 11, 12, 13, 14).iterator());

        itr.skip(2);
        System.out.println(itr.next());
        System.out.println(itr.next());
        itr.skip(6);
        itr.skip(5);
        System.out.println(itr.next());
        itr.skip(-1);
        System.out.println("Mpa values : " + itr.printonly());
        itr.skip(5);
        System.out.println(itr.next());
        System.out.println(itr.next());
        System.out.println(itr.hasNext());
        System.out.println(itr.next());
    }
}

// tc: O(1)
// sc: O(n) ; for skip map

// Another approach from Leetcode discussion
class SkipIterator {
    private int index = 0;
    private int[] nums = null;
    private Map<Integer, Integer> map = null;

    public SkipIterator(int[] nums) {
        this.nums = nums;
        map = new HashMap<>();
    }

    /**
     * Returns true if the iteration has more elements.
     */
    public boolean hasNext() {
        return index < nums.length;
    }

    /**
     * Returns the next element in the iteration.
     */
    public Integer next() {
        Integer value = nums[index++];
        checkSkipped();
        return value;
    }

    private void checkSkipped() {
        while (index < nums.length && map.containsKey(nums[index])) {
            if (map.get(nums[index]) == 1)
                map.remove(nums[index]);
            else
                map.put(nums[index], map.get(nums[index]) - 1);
            ++index;
        }
    }

    /**
     * The input parameter is an int, indicating that the next element equals 'num'
     * needs to be skipped.
     * This method can be called multiple times in a row. skip(5), skip(5) means
     * that the next two 5s should be skipped.
     */
    public void skip(int num) {
        map.put(num, map.getOrDefault(num, 0) + 1);
        checkSkipped();
    }
}