// Time Complexity : O(1) for hasNext(), Amortized O(1) for next() and O(1) for skip()
// Space Complexity : O(n)
// Did this code successfully run on Leetcode : Yes 
// Any problem you faced while coding this : No

// Approach: I made a helper function to get the next valid element. I used a HashMap to store the elements to be skipped and their counts. In the next() method, I return the current next element and call the helper function to find the next valid element. In the skip() method, if the current next element is equal to the value to be skipped, I call the helper function to find the next valid element. Otherwise, I update the count of the value in the skip map.

import java.util.*;

class SkipIterator implements Iterator<Integer> {
    private final Iterator<Integer> it;
    private final HashMap<Integer, Integer> skipMap;
    private Integer nextEl;

	public SkipIterator(Iterator<Integer> it) {
        this.it = it;
        this.skipMap = new HashMap<>();
        this.nextEl = null;
        helper(); 
	}

	public boolean hasNext() {
        return nextEl != null;
	}

	public Integer next() {
        if (!hasNext()) throw new NoSuchElementException();
        int res = nextEl;
        helper();
        return res;
	}

	/**
	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	*/ 
	public void skip(int val) {
        if (nextEl != null && nextEl == val) helper();
        skipMap.put(val, skipMap.getOrDefault(val, 0) + 1);
	}

    private void helper() {
        nextEl = null;
        while (it.hasNext()) {
            int cand = it.next();
            if (skipMap.containsKey(cand)) {
                skipMap.put(cand, skipMap.get(cand) - 1);
                if (skipMap.get(cand) == 0) skipMap.remove(cand);
                continue;
            }
            nextEl = cand;
            break;
        }
    }
    public static void main(String[] args) {
        List<Integer> nums = Arrays.asList(2, 3, 5, 6, 5, 7, 5, -1, 5, 10);
        SkipIterator itr = new SkipIterator(nums.iterator());
        System.out.println(itr.hasNext()); // true
        System.out.println(itr.next());    // 2
        itr.skip(5);
        System.out.println(itr.next());    // 3
        System.out.println(itr.next());    // 6 (skipped first 5)
        System.out.println(itr.next());    // 5
        itr.skip(5);
        itr.skip(5);
        System.out.println(itr.next());    // 7
        System.out.println(itr.next());    // -1
        System.out.println(itr.next());    // 10
        System.out.println(itr.hasNext()); // false
        itr.next(); // would throw NoSuchElementException
    }
}