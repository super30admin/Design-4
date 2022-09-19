import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SkipIterator implements Iterator<Integer> {

     /*
    We are given a basic iterator (hasNext,next)
    This native iterator has to be motdified to a different type of iterrator to a different type of iterator called skip iterator
    This iterator has one additional function skip apart from basic next and hasNext
    to keep track of the skips for repeated number we maintain a map (skipELemnt:Frequency)
    */
    Map<Integer,Integer> skipMap;
    Integer nextElement;
    //native iterator
    Iterator<Integer> nit;
    public SkipIterator(Iterator<Integer> it) {
        skipMap = new HashMap<>();
        this.nit = it;
        //setting the next Element
        advance();
	}
   

    private void advance() {
        //This function sets nextEl to a valid element
        this.nextElement = null;
        while(nextElement == null && nit.hasNext())
        {
            Integer el= nit.next();
            if(skipMap.containsKey(el))
            {
                skipMap.put(el, skipMap.get(el)-1);
                skipMap.remove(el,0);
            }
            else{
                //set nextEl;
                this.nextElement = el;
                break;
            }
        }
    }


    @Override
    public Integer next() {
      Integer re = nextElement;
      advance();
      return re;
    }

    @Override
    public boolean hasNext() {
        return nextElement!=null;
    }

    public void skip(int num)
    {
        if(num == nextElement)
        {
            advance();
        }
        else{
            skipMap.put(num, skipMap.getOrDefault(num,0)+1);
        }

    }

    public static void main(String[] args)
    {
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
