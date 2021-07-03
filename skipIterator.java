// Time Complexity : O(n) n-number of integers in iterator
// Space Complexity : O(n) n elements are stored in hashmap
// Did this code successfully run on Leetcode : Yes
//https://leetcode.com/discuss/interview-question/341818
//https://leetcode.com/playground/tx9xyHxq

class SkipIterator implements Iterator<Integer> {


    HashMap<Integer,Integer> map;
    Iterator<Integer> it;
    Integer retElement;
	public SkipIterator(Iterator<Integer> it) {
        map=new HashMap<>();
        this.it=it;
        advance();

	}

	public boolean hasNext() {
        return retElement!=null;
	}

	public Integer next() {
        int temp=retElement;
        advance();
        return temp;

	}
    
    private void advance()
    {
        retElement=null;
        while(retElement==null && it.hasNext())
        {
            int temp=it.next();
            if(map.containsKey(temp))
            {
                map.put(temp,map.get(temp)-1);
                map.remove(temp,0);
            }else
            {
                retElement=temp;
            }
        }
        
        
    }

	/**

	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.

	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.

	*/ 
	public void skip(int val) {
        if(val==retElement)
            advance();
        else
            map.put(val,map.getOrDefault(val,0)+1);
      
	}

}

public class Main {
        public static void main(String[] args) {
        SkipIterator it = new SkipIterator(Arrays.asList(2, 3, 5, 6, 5, 7, 5, -1, 5, 10).iterator());

        System.out.println(it.hasNext()); // true

        System.out.println(it.next()); // returns 2

        it.skip(5);

        System.out.println(it.next()); // returns 3

        System.out.println(it.next()); // returns 6 because 5 should be skipped

        System.out.println(it.next()); // returns 5

        it.skip(5);

        it.skip(5);

        System.out.println(it.next()); // returns 7

        System.out.println(it.next()); // returns -1

        System.out.println(it.next()); // returns 10

        System.out.println(it.hasNext()); // false

        System.out.println(it.next()); // error
    }
}