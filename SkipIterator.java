import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SkipIterator implements Iterator<Integer> {

    Iterator<Integer> it;
    Integer nextEl;
    Map<Integer, Integer> map;

    public SkipIterator(Iterator<Integer> it) {
        this.it = it;
        map = new HashMap<>();
        advance();
    }

    // TC: O(1)
    public boolean hasNext() {
        return nextEl != null;
    }

    // TC: O(1)
    public Integer next() {
        int answer = nextEl;
        advance();
        return answer;
    }

    // TC: O(1)
    private void advance() {
        nextEl = null;
        while (nextEl == null && it.hasNext()){
            int curr = it.next();
            if(map.containsKey(curr)) {
                map.put(curr, map.get(curr) - 1);
                map.remove(curr,0); // remove the entry having key as  curr only if it has value 0
            }else {
                nextEl = curr;
            }
        }
    }

    /**
     * The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
     * This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
     */
    public void skip(int val) {
        if(nextEl == val){
            advance();
        }else {
            map.put(val, map.getOrDefault(val, 0) + 1);
        }
    }

    public static void main(String[] args) {
        SkipIterator itr = new SkipIterator(Arrays.asList(2, 3, 5, 6, 5, 7, 5, -1, 5, 10).iterator());
        itr.skip(2);
        System.out.println("itr.hasNext = " + itr.hasNext());
        System.out.println("itr.next = " + itr.next());
    }
}
