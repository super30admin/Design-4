// TC : hasNext - O(1), next - O(s), skip - O(s)  // s = no. of skip elements
// SC : O(s)

package S30_Codes.Design_4;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SkipIterator implements Iterator<Integer> {
    Iterator<Integer> it;
    Integer nextEle;
    Map<Integer, Integer> skippedElements;

    public SkipIterator(Iterator<Integer> it) {
        this.it = it;
        skippedElements = new HashMap<>();
        advance();
    }

    private void advance(){
        while(it.hasNext()){
            Integer next = it.next();

            if(skippedElements.containsKey(next)){
                int freq = skippedElements.get(next);
                if(freq == 1)
                    skippedElements.remove(next);
                else
                    skippedElements.put(next, freq-1);
            }
            else{
                nextEle = next;
                break;
            }
        }
    }

    public boolean hasNext() {
        return nextEle != null;
    }

    public Integer next() {
        Integer cur = nextEle;
        nextEle = null;
        advance();
        return cur;
    }

    /**
     * The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
     * This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
     */
    public void skip(int val) {
        if(val == nextEle){
            nextEle = null;
            advance();
        }
        else
            skippedElements.put(val, skippedElements.getOrDefault(val, 0)+1);

    }
}

class Main {
    public static void main(String[] args) {
        SkipIterator itr = new SkipIterator(Arrays.asList(5,6,7,5,6,8,9,5,5,6,8).iterator());

        System.out.println(itr.hasNext()); // true
        itr.skip(5);
        System.out.println(itr.next()); // returns 2
        itr.skip(5);
        System.out.println(itr.next()); // returns 3
        System.out.println(itr.next()); // returns 6 because 5 should be skipped
        itr.skip(8);
        itr.skip(9);
        System.out.println(itr.next()); // returns 5
        System.out.println(itr.next()); // returns 7
        System.out.println(itr.next()); // returns -1
        System.out.println(itr.next()); // returns 10
        System.out.println(itr.hasNext()); // false

//         System.out.println(itr.next()); // error
    }
}