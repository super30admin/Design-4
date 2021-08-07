//Time Complexity : Skip: O(n), Next: O(n), HasNext: O(1); n = Total no of elements in the list
// Space Complexity : O(n)
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class SkipIterator3{
    public static void main(String[] args) {
        List<Integer> arr = Arrays.asList(2, 5, 6, 5, 7, 5, -1, 5, 10);
        SkipIterator itr = new SkipIterator(arr.iterator());
        System.out.println(itr.hasNext()); // true
        System.out.println(itr.next()); // returns 2
        itr.skip(5);
        System.out.println(itr.next()); // returns 6 because 5 should be skipped
        System.out.println(itr.next()); // returns 5
        itr.skip(5);
        itr.skip(5);
        System.out.println(itr.next()); // returns 7
        System.out.println(itr.next()); // returns -1
        System.out.println(itr.next()); // returns 10
        System.out.println(itr.hasNext()); // false
        System.out.println(itr.next()); // null
    }
}
class SkipIterator {
    Iterator<Integer> itr;
    HashMap<Integer,Integer> skipMap;
    Integer nextVal;
    SkipIterator(Iterator<Integer> it) {
        this.itr = it;
        skipMap = new HashMap<>();
        advance();
    }

    boolean hasNext() {
        return nextVal!=null;
    }

    Integer next() {
        Integer next = nextVal;
        advance();
        return next;
    }
    public void skip(int val){
        if(nextVal == val){
            advance();
        }
        skipMap.put(val,skipMap.getOrDefault(val,0)+1);
    }
    void advance(){
        nextVal =null;
        while(nextVal == null && itr.hasNext()){
            Integer val = itr.next();
            if(!skipMap.containsKey(val)){
                nextVal = val;
            }else{
                skipMap.put(val,skipMap.get(val)-1);
                if(skipMap.get(val)==0){
                    skipMap.remove(val);
                }
            }
        }
    }
}
