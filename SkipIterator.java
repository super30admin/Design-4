package Design-4;
 // "static void main" must be defined in a public class.
class SkipIterator implements Iterator<Integer> {
    HashMap<Integer,Integer> map;
    Iterator<Integer>it;
    Integer nextEl;
	public SkipIterator(Iterator<Integer> it) {
        map = new HashMap<>();
        this.it = it;
        moveNext();
	}

	public boolean hasNext() {
    
        return nextEl != null;
	}

	public Integer next() {
        
        Integer result = nextEl;
        moveNext();
        return result;
	}

	/**
	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	*/ 
	public void skip(int val) {
        if(val == nextEl)
        {
            moveNext();
        }else{
           map.put(val, map.getOrDefault(val,0)+1);    
        }
        
	}
    
    private void moveNext()
    {
        nextEl = null;
        while(nextEl == null && it.hasNext())
        {
            Integer val = it.next();
            if(!map.containsKey(val))
            {
                nextEl = val;
            }else{
                map.put(val,map.get(val)-1);
                map.remove(val,0);
            }
        }
    }
}
public class Main {
    public static void main(String[] args) {
        SkipIterator itr = new SkipIterator(Arrays.asList(2, 3, 5, 6, 5, 7, 5, -1, 5, 10).iterator());
        itr.hasNext(); // true
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
        itr.hasNext(); // false
        System.out.println(itr.next()); // error
}
}   

