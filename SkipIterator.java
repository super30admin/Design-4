package Design4;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

/*
    -------------------------------------------------------------------------------------------------------
    Time complexity : o(N) - worstCase if we skip all elements.
    space complexity: o(N)
    Did this code run successfully in leetcode : yes
    problems faces : no*/   
    
public class SkipIterator implements Iterator<Integer> {
    
    Iterator<Integer> it;
    HashMap<Integer,Integer> skipMap;
    Integer nextElement; 
    

    public SkipIterator(Iterator<Integer> it) {
        
        this.it = it;
        skipMap = new HashMap<>();
        advance();

    }
    
    public void skip(int num)
    {
        if(num == nextElement)
        {
            advance();
        }
        else
        {
            skipMap.put(num, skipMap.getOrDefault(num, 0)+1);
        }
    }

    @Override
    public boolean hasNext() {
        
        return nextElement != null;

    }

    @Override
    public Integer next() {
        
        Integer result = nextElement;
        advance();
        return result;

    }
    
    private void advance()
    {
        nextElement = null;
        while(nextElement  == null && it.hasNext())
        {
            Integer el = it.next();
            if(skipMap.containsKey(el))
            {
                skipMap.put(el,skipMap.get(el)-1);
                skipMap.remove(el,0);
              
            }
            else
            {
               nextElement = el;
            }
            
        }
        
        
        
    }
    
    public static void main(String[] args)
    {
        SkipIterator it = new SkipIterator(Arrays.asList(2,5,3,6,5,8,7,6,5,4).iterator());
        
        it.skip(2);
        it.skip(5);
        it.skip(6);
        
        System.out.println(it.hasNext());
        System.out.println(it.next());
        System.out.println(it.next());
        System.out.println(it.next());
        System.out.println(it.hasNext());
        
    }


}
