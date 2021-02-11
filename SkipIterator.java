public class SkipIterator implements Iterator<Integer> 
{

    // nextElement to be returned by the skip iterator
    Integer nextEl;
    
    // map storing the values to be skipped
    Map<Integer, Integer> hashMap;
    
    
    // native iterator
    Iterator<Integer> it;
    
    
    
    public SkipIterator(Iterator<Integer> it) 
    {
        this.hashMap = new HashMap<>();
        this.it = it;
        this.advance();
    }

    public boolean hasNext() 
    {
        return this.nextEl != null;
    }

    public Integer next() 
    {
        Integer result = this.nextEl;
        
        this.advance();
        
        return result;
    }

    /**
    * The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
    * This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
    */ 
    public void skip(int val) 
    {
        Integer nextVal = this.nextEl;
        
        if( this.nextVal != null)
        {
            if( this.nextVal == val)
            {
                // simply advance the this.nextVal to valid position
                this.advance();
            }
            else
            {
                // put it in the map for future skips
                this.hashMap.put( val , this.hashMap.getOrDefault( val, 0) + 1);
            }
        }
    }
    
    // This method places the nextElement of the valid next element of the skip iterator
    public void advance()
    {
        this.nextEl = null;
        
        while( this.nextEl == null && this.it.hasNext())
        {
            Integer current = this.it.next();
            
            if( this.hashMap.containsKey(current))
            {
                // decrement the count of that element in hashMap 
                int count = this.hashMap.get(current);
                count--;
                
               if(count == 0)
                this.hashMap.remove(current);
               else
                this.hashMap.put( current, count);
                
            }
            else
            {
                this.nextEl = current;
            }
        }
    }
    
    public static void main( String argv[])
    {
        Integer integers = new Integer[]{2, 3, 5, 6, 5, 7, 5, -1, 5, 10};
        
        List<Integer> input = Arrays.asList(integers);
        
        SkipIterator itr = new SkipIterator(input.iterator());
        
        itr.hasNext(); // true
        itr.next(); // returns 2
        itr.skip(5);
        itr.next(); // returns 3
        itr.next(); // returns 6 because 5 should be skipped
        itr.next(); // returns 5
        itr.skip(5);
        itr.skip(5);
        itr.next(); // returns 7
        itr.next(); // returns -1
        itr.next(); // returns 10
        itr.hasNext(); // false
        itr.next(); // error
    }
}