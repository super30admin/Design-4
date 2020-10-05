
// Time Complexity : O(1) skip and hasNext - O(n) next 
// Space Complexity : O(n) 
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No


// Your code here along with comments explaining your approach
class SkipIterator implements Iterator<Integer> {
    
    Integer nextElement;
    Iterator<Integer> it;
	private Map<Integer, Integer> map = null;

	public SkipIterator(Iterator<Integer> it) {
		this.it = it;
        map = new HashMap<>();
        advance();
	}

	public boolean hasNext() {
        return nextElement != null;
	}

	public Integer next() {
         if (!hasNext()) {
             throw new RuntimeException("Error");
         }
        Integer val = nextElement;
        advance();
        return val;
	}
    
    private void advance() {
		nextElement = null;
        while (nextElement == null && it.hasNext()) {
            Integer val = it.next();
            if (!map.containsKey(val)) {
                nextElement = val;
            } else {
                map.put(val, map.get(val) - 1);
                map.remove(val, 0);
            }
        }
	}

	public void skip(int val) {
        if (!hasNext()){
            throw new RuntimeException("Error");
        }
        if (nextElement == val) {
            advance();
        } else {
            map.put(val, map.getOrDefault(val, 0) + 1);
        }
	}
    
    public static void main(String arg[]){
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