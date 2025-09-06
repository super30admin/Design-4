import java.util.HashMap;
import java.util.Iterator;

public class SkipIterator implements Iterator<Integer>{
    HashMap<Integer,Integer> skipMap;
    Integer nextEl;
    Iterator<Integer> it;
    public SkipIterator()
    {
        skipMap = new HashMap<>();
    }

    public boolean hasNext(){

        return nextEl!=null;
    }

    public Integer next()
    {
        //Check if hashmap has count using the regular iterator
            //If yes, reduce the count and set the next element
        // return the element
        Integer el = nextEl;
        advance();
        return el;
    }

    public void skip(int val)
    {
        //Increase the count in hashmap if already present
        //Move the next integer pointer if skip value corresponds to the same
        if(val==nextEl)
        {
            advance();
        }
        else{
            skipMap.put(val,skipMap.getOrDefault(val, 0)+1);
        }
    }

    //removing the element from hashmap and moving the iterator pointer accordingly
    public void advance()
    {
        this.nextEl = null;
        while(it.hasNext())
        {
            Integer el = it.next();
            if(!skipMap.containsKey(el)){
                nextEl = el;
                break;
            }
            else{
                skipMap.put(el, skipMap.get(el) - 1);
                if (skipMap.get(el) == 0) {
                    skipMap.remove(el);
                }
            }
        }
    }
}
