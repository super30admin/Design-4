/*
 In this program, we design the skip iterator class, using the native iterator class. This class uses the regular iterator functions 
 next() and hasNext(), with the addition of an extra skip() and advance() function. The skip() function takes from the user to values
 to be skipped, and the advance() function builds on top of the native iterator next() function, where each time the iterator advances,
 it checks if the current element is to be skipped or not, and proceeds accordingly

 Did this code run on leetcode: N/A
*/

class SkipIterator implements Iterator<Integer> {
    //We maintain a HashMap of all the values to be skipped and how many times they should be skipped in O(1) lookup
    HashMap<Integer, Integer> skipMap;
    int nextEl;
    Iterator<Integer> iter;

	public SkipIterator(Iterator<Integer> iter) {
        this.skipMap = new HashMap<>();
        this.iter = iter;

        advance();
	}
    //Time Complexity: O(1)
	public boolean hasNext() {
        return nextEl != null;
	}
     //Time Complexity: O(n)
	public Integer next() {
        int result = nextEl;
        advance();
        return result;
	}
     //Time Complexity: O(n)
	public void skip(int val) {
        if(val != nextEl)
        {
            if(!skipMap.containsKey(val))
                skipMap.put(val, 0);
            else
                skipMap.put(val, skipMap.get(val) + 1);

        }
        //If the element to be skipped is the current mextEl, then we just advance and look for the next valid element 
        else
            advance();
	}
    //Time Complexity: O(n)
    private void advance()
    {
        nextEl = null;
        //Keep checking for next element till we skip all the required elements
        while(nextEl == null && it.hasNext())
        {
            int el = iter.next();
            if(skipMap.containsKey(el))
            {
                skipMap.put(el, skipMap.get(el) - 1);
                skipMap.remove(el, 0);
            }
            else
            {
                nextEl = el;
            }
        }
    }
}