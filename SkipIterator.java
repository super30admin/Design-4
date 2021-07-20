// Time Complexity : O(n)
// Space Complexity : O(n)

class SkipIterator implements Iterator<Integer> {
    private Iterator<Integer> itr;
    private Map<Integer, Integer> map;
    private Integer nextEle;
    
	public SkipIterator(Iterator<Integer> it) {
        itr = it;
        map = new HashMap<>();
        advance();
	}

	public boolean hasNext() {
        return nextEle != null;
	}

	public Integer next() {
        Integer temp = nextEle;
        advance();
        return temp;
	}

	/**
	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	*/ 
	public void skip(int val) {
        if(nextEle == val)
            advance();
        else
            map.put(val, map.getOrDefault(val, 0) + 1);
	}
    
    private void advance(){
        while(itr.hasNext()){
            Integer temp = itr.next();
            if(!map.containsKey(temp)){
                nextEle = temp;
                break;
            }
            map.put(temp, map.get(temp) - 1);
            map.remove(temp, 0);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        SkipIterator itr = new SkipIterator(Arrays.asList(2, 3, 5, 6, 5, 7, 5, -1, 5, 10).iterator());
        System.out.println(itr.hasNext()); // true
        System.out.println(itr.next()); // returns 2
        itr.skip(5);
        System.out.println(itr.next()); // returns 3
        System.out.println(itr.next()); // returns 6 because 5 should be skipped
        System.out.println(itr.next()); // returns 5
        itr.skip(5);
        itr.skip(5);
        System.out.println(itr.next()); // returns( 7
        System.out.println(itr.next()); // returns -1
        System.out.println(itr.next()); // returns 10
        System.out.println(itr.hasNext()); // false
        System.out.println(itr.next()); // error
    }
}