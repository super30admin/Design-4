// Time Complexity : avg - O(1), worst O(n)
// Space Complexity: O(n)
// Did this code successfully run on Leetcode: Yes
// Any problem you faced while coding this : No

/**
 * https://leetcode.com/discuss/interview-question/341818
 *
 */
class SkipIterator implements Iterator<Integer> {

    Map<Integer, Integer> skippedMap;
    Iterator<Integer> it;
    Integer val;
    
	public SkipIterator(Iterator<Integer> it) {
        skippedMap = new HashMap<>();
        this.it = it;
        moveAhead();
	}

	public boolean hasNext() {
        return (val != null);
	}

	public Integer next() {
        Integer res = val;
        moveAhead();
        return res;
	}

    
    private void moveAhead() {
        val = null;
        
        while(it.hasNext() && val == null){
            Integer num = it.next();
            
            if(skippedMap.containsKey(num)){
                int count = skippedMap.get(num);
                count--;
                
                if(count == 0) {
                    skippedMap.remove(num);
                }else {
                   skippedMap.put(num, count); 
                }
            } else {
                val = num;
            }
        }
        
    }
	/**
	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	*/ 
	public void skip(int skip) {
        
        if( val != skip) {

            if(!skippedMap.containsKey(skip)){
                skippedMap.put(skip, 0);
            }
            
            skippedMap.put(skip, skippedMap.get(skip) + 1);
        }else {
            moveAhead();
        }
        
	}
}

public class Main{
    public static void main(String[] args) {
        SkipIterator itr = new SkipIterator(Arrays.asList(2, 3, 5, 6, 5, 7, 5, -1, 5, 10).iterator());

    System.out.println(itr.hasNext());
        
    System.out.println(itr.next());

    itr.skip(5);

    System.out.println(itr.next());

    System.out.println( itr.next());
  
   System.out.println( itr.next());

    itr.skip(5);

    itr.skip(5);

   System.out.println( itr.next());

    System.out.println( itr.next());

    System.out.println( itr.next());

    System.out.println(itr.hasNext());
        
    System.out.println( itr.next());
    }
}