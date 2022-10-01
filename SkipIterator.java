// Time Complexity : O(1)
// Space Complexity: O(N) where N is num skips in hashMap
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No
// Your code here along with comments explaining your approach:
/*
use hashmap to store the skips and its freq.
move the iterator and avoid any element in hashmap whose freq is >0
*/
public class SkipIterator {
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
            if (!iterator.hasNext()) return -1;
            return iterator.next();
        }
        return nextElement;
    }

    public void skip(int val) {
        skippedElements.put(val, skippedElements.getOrDefault(val, 0) + 1);
    }
}