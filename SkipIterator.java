import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

//Time Complexity : O(1) for hasNext(),  rest O(n)
//Space Complexity : O(n)
class SkipIterator implements Iterator<Integer> {
    Integer nextEle;
    HashMap<Integer, Integer> map;
    Iterator<Integer> nativeIt;
    
	public SkipIterator(Iterator<Integer> it) {
        this.map= new HashMap<>();
        this.nativeIt= it;
        advance();
	}

	public boolean hasNext() { //O(1)
        return (nextEle!=null);
	}

	public Integer next() { //O(n)
        Integer ele= nextEle;
        advance();
        return ele;
	}
 
	public void skip(int val) {//O(n)
        if(val!=nextEle){
            map.put(val, map.getOrDefault(val, 0)+1);	            
        }else{
            advance();
        }
	}
    
    private void advance(){
        this.nextEle=null;
        while(nativeIt.hasNext() && nextEle==null){
            Integer ele= nativeIt.next();
            if(map.containsKey(ele)){
                map.put(ele, map.get(ele)-1);
                map.remove(ele, 0);
            }else {
            	nextEle= ele;
            }
        }
    }
	
	// Driver code to test above
	public static void main (String[] args) {
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
