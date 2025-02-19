import java.util.*;

class SkipIterator implements Iterator<Integer> {

    HashMap <Integer, Integer> fMap; 
    Iterator<Integer> iterator;
    int nextVal = Integer.MAX_VALUE;
	public SkipIterator(Iterator<Integer> it) {
        fMap = new HashMap<>();
         iterator = it;
	}

    //Time Complexity: O(1)
    //Space Complexity: O(1)
	public boolean hasNext() {
        // return iterator.hasNext();
        if (nextVal != Integer.MAX_VALUE) return true;
        else{
            try{
           bringNext();
           if (nextVal == Integer.MAX_VALUE)
            return false; else {
                return true;
            }
       } catch(EmptyStackException e){
        return false;
       }
        }
	}

    //Time Complexity: O(1)
    //Space Complexity: O(1)
    private void bringNext(){
        
            try{
            int n = iterator.next(); 
        while (fMap.containsKey(n) && fMap.get(n) > 0){
            fMap.put(n,fMap.get(n)-1);
            n = iterator.next();
            }
            nextVal = n;
            }
            catch(Exception e){
               nextVal =  Integer.MAX_VALUE;
            }
    }

    //Time Complexity: O(1)
    //Space Complexity: O(1)
	public Integer next() {
                if (nextVal != Integer.MAX_VALUE){
                    int temp = nextVal;
                    bringNext();
                    return temp;
                }
                hasNext();
                if (nextVal == Integer.MAX_VALUE){
                    return Integer.MIN_VALUE;
                }
                return Integer.MAX_VALUE;
        
	}

	/**
	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	*/ 
	public void skip(int val) {
        fMap.put(val, fMap.getOrDefault(val,0)+1);
	}

	public static void main(String[] args) {
		// Create a list and get its iterator
		List<Integer> numbers = Arrays.asList(2, 3, 5, 6, 5, 7, 5, -1, 5, 10);
		Iterator<Integer> iterator = numbers.iterator();
		
		// Create our SkipIterator
		SkipIterator itr = new SkipIterator(iterator);
		
		// Test case 1: Basic iteration
		System.out.println("hasNext(): " + itr.hasNext());  // true
		System.out.println("next(): " + itr.next());        // 2
		
		// Test case 2: Skip single value
		itr.skip(5);
		System.out.println("next(): " + itr.next());        // 3
		System.out.println("next(): " + itr.next());        // 6 (5 was skipped)
		System.out.println("next(): " + itr.next());        // 5
		
		// Test case 3: Multiple skips
		itr.skip(5);
		itr.skip(5);
		System.out.println("next(): " + itr.next());        // 7 (two 5s were skipped)
		System.out.println("next(): " + itr.next());        // -1
		System.out.println("next(): " + itr.next());        // 10
		
		// Test case 4: End of iterator
		System.out.println("hasNext(): " + itr.hasNext());  // false
		
		try {
			itr.next();  // Should throw NoSuchElementException
		} catch (NoSuchElementException e) {
			System.out.println("Successfully caught NoSuchElementException");
		}
	}
}