// Time Complexity : O(n), where n is the number of Integers to be skipped (in a case all the Integers in the iterator are skipped)
// Space Complexity :O(n), where n is the number of Integers to be skipped
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : correct me on the space and time complexity


// Your code here along with comments explaining your approach

class SkipIterator implements Iterator<Integer> {
    private final Iterator<Integer> it;
    private final Map<Integer, Integer> count;
    private Integer nextEl;

    public SkipIterator(Iterator<Integer> it) {
        this.it = it;
        this.count = new HashMap<>();
        //while initialization, the nextEL should contain a valid number to be returned
        advance();
    }

    @Override
    public boolean hasNext() {
        return nextEl != null;
    }

    @Override
    public Integer next() {
        //advacnce to the next valid position before returning the current nextEl
        Integer temp = nextEl; 
        advance();
        return temp;
    }

    public void skip(int num) {
        if(num == nextEl){//nextEl has to be skipped
            advance();
        }
        else{//else add the number as a future reference to be skipped in the map
            count.put(num, count.getOrDefault(num, 0)+1);
        }
    }

    private void advance() {
        nextEl = null;
        while(nextEl == null && it.hasNext()){
            //next position
            Integer el = it.next();
            //check if next position is valid
            if(count.containsKey(el)){//next position is inValid
                count.put(el, count.get(el)-1);
                count.remove(el, 0);
            }else{//next position is valid
                nextEl = el;
            }
        }
    }
}

public class Main {
        public static void main(String[] args) {
        SkipIterator it = new SkipIterator(Arrays.asList(1, 2, 3).iterator());
        System.out.println(it.hasNext());
        it.skip(2);
        System.out.println(it.next());
        it.skip(1);
        it.skip(3);
        System.out.println(it.hasNext());
    }
}