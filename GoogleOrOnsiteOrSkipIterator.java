// Time Complexity : O(n)
// Space Complexity : O(n) , used for HashMap
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : Figuring out how to advance the pointer with help of go() function

// Notes : Create a function go() which will set the pointer at the next valid position, when next() is called

public class GoogleOrOnsiteOrSkipIterator {
    Iterator<Integer> it;
    Integer nextElement;
    HashMap<Integer, Integer> map = new HashMap<>();
    
	public SkipIterator(Iterator<Integer> it) {
        this.it = it;
        go();
	}

	public boolean hasNext() {
        return nextElement != null;
	}

	public Integer next() {
        Integer nextEl = nextElement;
        go();
        return nextEl;
	}

	/**
	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	*/ 
	public void skip(int val) {
        if(val == nextElement){
            go();
        } else {
            map.put(val, map.getOrDefault(val, 0) + 1 );
        }
	}
    
    public void go(){
        nextElement = null;
        while(nextElement == null && it.hasNext()){
            Integer el = it.next();
            if(!map.containsKey(el)){
                nextElement = el;
            } else {
                map.put(el, map.get(el) - 1 );
                map.remove(el, 0);
            }
        }
        
    }

    
    public static void main(String[] args){
        SkipIterator itr = new SkipIterator(Arrays.asList(2, 3, 5, 5, 9, 7, 5, -1, 5, 10).iterator());
        System.out.println(itr.hasNext()); // true
        itr.skip(2);
        System.out.println(itr.next()); // returns 3
        itr.skip(5);
        itr.skip(5);
        itr.skip(-1);
        System.out.println(itr.next()); // returns 9
        System.out.println(itr.hasNext()); // true
        System.out.println(itr.next()); // returns 7 
        System.out.println(itr.next()); // returns 5
        System.out.println(itr.next()); // returns 5
        System.out.println(itr.next()); // returns 10
        System.out.println(itr.next()); // returns null
        System.out.println(itr.hasNext()); // false
        System.out.println(itr.next()); // null
    }
}
