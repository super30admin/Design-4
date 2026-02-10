import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

// Approach:
// You know how many times a number is skipped, we need to keep it in a frequencyMap.
// Edge case: 1. We need to keep in mind a number can be skipped such that there is no value remaining. So we should evaluate
// next value in the next method call in a while loop.
// 2. We should the consider the cases, where the first value itself is skipped

public class SkipIterator {
    Map<Integer, Integer> countMap;
    Iterator<Integer> itr;
    Integer nextValue;

    public SkipIterator(Iterator<Integer> it) {
        countMap = new HashMap<>();
        itr = it;
        if (itr.hasNext()) {
            nextValue = itr.next();
        }
    }

    public boolean hasNext() {
        return nextValue != null;
    }

    public Integer next() {
        int result = nextValue;
        advance();
        return result;
    }

    private void advance() {
        nextValue = null;
        while(itr.hasNext()){
            int temp = itr.next();
            if(countMap.containsKey(nextValue)){
                countMap.put(nextValue, countMap.get(nextValue) - 1);
                countMap.remove(nextValue, 0);
            }else{
                nextValue = temp;
                break;
            }
        }
    }

    /**
     * The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
     * This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
     */
    public void skip(int val) {
        if(val == nextValue){
            advance();
        }else {
            countMap.put(val, countMap.getOrDefault(val, 0) + 1);
        }
    }
}
