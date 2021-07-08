// Time Complexity : All operations O(1)
// Space Complexity : O(n) hashmap for skip map where n is the total numbers to be skipped
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : to understand the advance function
/* Algorithm: If you see a number to be skipped, put the number in the skip map with a count. As we re-see the number, we decrement the count in the
map and once the count is 0, remove from the map. If the number is not in the map, simply do a next(). HasNext checks if the next element is not null.
Next() gives the current element and moves to the next pointer. In advance, we are moving our nextEle to the correct position considering the fact
if the skip is called even before the iterator starts moving.
*/
class SkipIterator implements Iterator<Integer> {
    private final Iterator<Integer> it;
    private final Map<Integer, Integer> count;
    private Integer nextEl;

    public SkipIterator(Iterator<Integer> it) {
        count = new HashMap<>();                                        // Mapping of skip numbers with their counts
        this.it = it;
        advance();

    }

    @Override
    public boolean hasNext() {
        return nextEl != null;                                      // Check if the next element is not null
       
    }

    @Override
    public Integer next() {
        int t = nextEl;
        advance();
        return t;                                                                   // Return the current element
            
    }

    public void skip(int num) {
            if(num == nextEl){                                                              // Just move to the next position if number == nextEl
                advance();
            }  else {
                count.put(num, count.getOrDefault(num, 0)+1);                                           // Number to be skipped
            }
    }

    private void advance() {
        nextEl  = null;
        while(nextEl ==  null && it.hasNext()==true){                                           // Moving nextEl to correct position
            int ele = it.next();
            if(!count.containsKey(ele)){                                                // Number needn't be skipped
                    nextEl = ele;
            } else {
                    count.put(ele, count.get(ele)-1);                                   // number has been skipped
                    count.remove(ele, 0);                                                   // No such numbers anymore to be skipped
                }
            }
            
        }

}

public class Main {
        public static void main(String[] args) {
        SkipIterator it = new SkipIterator(Arrays.asList(1, 2, 3).iterator());
        System.out.println(it.hasNext());
        it.skip(2);
        it.skip(1);
        it.skip(3);
        System.out.println(it.hasNext());
    }
}