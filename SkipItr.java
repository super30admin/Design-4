Time Complexity: o(n)
Space Complexity: o(n)
class SkipIterator implements Iterator<Integer> {
    Iterator<Integer> it;
    Integer nextEl;
    HashMap<Integer, Integer> map;
	public SkipIterator(Iterator<Integer> it) {
        this.it = it;
        map = new HashMap<>();
        findNext();
	}

	public boolean hasNext() {
        if(nextEl != null) return true;
        return false;
	}

	public Integer next() {
        Integer res = nextEl;
        findNext();
        return res;
	}

	/**
	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	*/ 
	public void skip(int val) {
        if(nextEl == val)
        {
            findNext();
        }
        else
        {
            map.put(val, map.getOrDefault(val, 0)+1);
        }
	}
    
    private void findNext()
    {
        nextEl = null;
        while(nextEl == null && it.hasNext())
        {
            int val  = it.next();
            if(!map.containsKey(val))
            {
                nextEl = val;
            }
            else
            {
                map.put(val, map.get(val) -1);
                map.remove(val, 0);
            }
        }
    }
}
public class main {
    public static void main(String[] args){
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