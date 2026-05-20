// Time Complexity : 
// next -> O(1) amortized
// hasNext -> O(1)
// skip -> O(1)
// Space Complexity : O(k) where k is the number of skipped values stored in the HashMap
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No


// Your code here along with comments explaining your approach in three sentences only
// We use a HashMap to store how many times a particular value needs to be skipped in the future.
// The iterator lazily advances using the advance() helper method, which keeps moving until it finds a valid unskipped element.
// If skip is called for the current next element, we immediately advance the iterator; otherwise we store the skip count in the map.

class SkipIterator implements Iterator<Integer> {

    Iterator<Integer> nativeIterator;

    HashMap<Integer, Integer> skipMap;

    Integer nextEl;

	public SkipIterator(Iterator<Integer> it) {

        // initialize iterator and skip map
        this.nativeIterator = it;

        this.skipMap = new HashMap<>();

        // preload first valid element
        advance();
	}
    
    private void advance() {

        // reset next element
        nextEl = null;

        // keep advancing until valid element found
        while (nextEl == null &&
               nativeIterator.hasNext()) {

            Integer curr = nativeIterator.next();

            // valid element
            if (!skipMap.containsKey(curr)) {

                nextEl = curr;
            }

            // skipped element
            else {

                skipMap.put(
                    curr,
                    skipMap.get(curr) - 1
                );

                // remove if skip count becomes 0
                skipMap.remove(curr, 0);
            }
        }
    }

	public boolean hasNext() {

        // check if valid element exists
        return nextEl != null;
	}

	public Integer next() {

        Integer res = nextEl;

        // move iterator forward
        advance();

        return res;
	}

	/**
	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	*/ 
	public void skip(int val) {

        // current element should be skipped immediately
        if (val == nextEl) {

            advance();
        }

        // future skip request
        else {

            skipMap.put(
                val,
                skipMap.getOrDefault(val, 0) + 1
            );
        }
	}
}

public class skip_iterator {

    public static void main(String[] args) {

        SkipIterator sit =
            new SkipIterator(
                Arrays.asList(
                    5,6,7,5,6,8,9,5,5,6,8
                ).iterator()
            );
        
        System.out.println(sit.hasNext());

        System.out.println(sit.next());

        sit.skip(5);

        System.out.println(sit.next());

        System.out.println(sit.next());

        sit.skip(7);

        sit.skip(9);

        System.out.println(sit.next());

        System.out.println(sit.next());

        System.out.println(sit.next());

        sit.skip(8);

        sit.skip(5);

        System.out.println(sit.hasNext());

        System.out.println(sit.next());

        System.out.println(sit.hasNext());
    }
}