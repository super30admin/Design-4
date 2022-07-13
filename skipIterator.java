// Time Complexity : O(n) 
// Space Complexity : O(l)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this: No

// "static void main" must be defined in a public class.
class SkipIterator implements Iterator<Integer> {
    HashMap<Integer, Integer> map;
    Integer nextEl;
    Iterator<Integer> it;
	public SkipIterator(Iterator<Integer> it) {
        this.it = it;
        this.map = new HashMap<>();
        advance();
	}
    private void advance(){
        nextEl = null;
        while(nextEl == null && it.hasNext()){
            Integer el = it.next();
            if(map.containsKey(el)){
                map.put(el, map.get(el) - 1);
                map.remove(el,0);
            } else{
                nextEl = el;
            }
        }
    }
	public boolean hasNext() {
        return nextEl != null;
	}

	public Integer next() {
        Integer result = nextEl;
        advance();
        return result;
	}

	/**
	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	*/ 
	public void skip(int num) {
        if(nextEl != num){
            map.put(num, map.getOrDefault(num , 0) + 1);
        } else{
            advance();
        }
	}
}

public class Main {
    public static void main(String[] args) {
        SkipIterator it = new SkipIterator(Arrays.asList(5,6,7,5,6,8,9,5,5,6,8).iterator());
        System.out.println(it.hasNext()); //true
        //it.skip(5);
        System.out.println(it.next()); //5
        it.skip(6);
        System.out.println(it.next()); //7
        System.out.println(it.next()); //5
        it.skip(6); //8
        it.skip(9); //5
        System.out.println(it.next());
        System.out.println(it.next());
        System.out.println(it.next());
        
    }
}