import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SkipIterator implements Iterator<Integer> {

    Map<Integer, Integer> skipMap;
    Iterator<Integer> it;
    Integer nextEl;

    public SkipIterator(Iterator<Integer> it) {
        skipMap = new HashMap<>();
        this.it = it;
        advance();
    }

    public boolean hasNext() {
        return it.hasNext();
    }

    public Integer next() {
        int temp = nextEl;
        advance();
        return temp;
    }

    /**
     * The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
     * This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
     */
    public void skip(int val) {
        if(val==nextEl){
            advance();
        } else {
            skipMap.put(val, skipMap.getOrDefault(val, 0)+1);
        }


    }

    private void advance(){
        nextEl = null;
        while (nextEl==null && it.hasNext()){
            Integer el = it.next();
            if(skipMap.containsKey(el )){
                skipMap.put(el,skipMap.getOrDefault(el,0)-1 );
                skipMap.remove(el, 0);
            }else {
                nextEl = el;
            }
        }


    }
}
