import java.util.Arrays;
import java.util.Iterator;
import java.util.*;

public class SkipIterator  implements Iterator<Integer> {

    Iterator<Integer> iterator;
    HashMap<Integer, Integer> skipMap;

    Integer current;

    SkipIterator(Iterator<Integer> value) {
        this.iterator = value;
        skipMap = new HashMap<>();
    }

    @Override
    public boolean hasNext() { 
        return current!=null;
    }

    @Override
    public Integer next() { 
        if(iterator.hasNext()) {
            moveNext();
            return current;
        } else {
            return null;
        } 
    }
  
    private void moveNext() {
        Integer pointer = null;
        while(pointer == null && iterator.hasNext()) {
            Integer nextValue = iterator.next();
            if(skipMap.containsKey(nextValue) && skipMap.get(nextValue) > 0) {
                skipMap.put(nextValue, skipMap.get(nextValue)-1); 
            } else {
                pointer = nextValue;
            }
        }
        current = pointer;
    }

    public void Skip(Integer value) {
        if(!skipMap.containsKey(value)) {
            skipMap.put(value, 1);
        } else {
            skipMap.put(value, skipMap.get(value)+1);
        }
    }

    public static void main(String[] args) {
        SkipIterator skipIterator = new SkipIterator(Arrays.asList(2,6,3,4,5,43,3,4,4,5,6,7,9).iterator());
        skipIterator.Skip(2);
        skipIterator.Skip(6);
        skipIterator.Skip(3);
        skipIterator.Skip(4);
        skipIterator.Skip(5);
        skipIterator.print(skipIterator.next());
        skipIterator.print(skipIterator.next());
        skipIterator.print(skipIterator.next());
        skipIterator.print(skipIterator.next());
    }

    private void print(Integer value) {
        System.out.println("The value"+value);
    }
 
    
}
