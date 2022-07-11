class SkipIterator implements Iterator<Integer> {
    HashMap<Integer, Integer> f;
    Iterator<Integer> it ;
    Integer nextEl;
	public SkipIterator(Iterator<Integer> it) {
        this.it = it;
        f =new HashMap<>();
        advance();
        
	}
    public void advance()  //O(n)
    {
        nextEl = null;
        while(nextEl==null && it.hasNext())
        {
            int el = it.next();
            if(f.containsKey(el))
            {
                int val = f.get(el);
                val--;
                if(val==0)
                    f.remove(el);
                else
                    f.put(el,val);
            }
            else
                nextEl=el;
        }
        
    } 
    

	public boolean hasNext() {  // O(1)
     return nextEl!=null;

	}

	public Integer next() {  // O(n)
    Integer res = nextEl;
        advance();
        return res;
	}

	/**
	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	*/ 
	public void skip(int val) {  //O(n)
       if(val==nextEl)
           advance();
        else
            f.put(val, f.getOrDefault(val,0)+1);
        
	}
}

class Main{
    
    
    
    public static void main(String[] args)
    {
        Iterator<Integer> it = Arrays.asList(2, 3, 5, 6, 5, 7, 5, -1, 5, 10).iterator();
        SkipIterator itr= new SkipIterator(it);
        
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