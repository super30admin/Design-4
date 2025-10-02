// Time Complexity : Amortized O(1)
// Space Complexity : O(k) where K is the number of distinct values that are currently pending skips. worst case - O(n)
// Did this code successfully run on Leetcode : Yes 
// Any problem you faced while coding this : No


// Your code here along with comments explaining your approach
// We make use of HashMap to store the frequency of the element to be skipped
// advance functions helps us skip the val by checking if in map or not and prepares the next element in the iterator
// skip function checks if the num to be skipped is same as the nextEl then we do not insert that num in hashmap and just move the nextEl pointer to next element in the iterator
// if it is skip for the future element we add it in the hashmap and increase its count by 1

class SkipIterator implements Iterator<Integer> {
    Iterator<Integer> it;
    Integer nextEl;
    HashMap<Integer, Integer> skipMap = new HashMap<>();
    
    public SkipIterator(Iterator<Integer> it) {
        this.it = it;
        advance(); // set the nextEl to first val in iterator
        
    }

    @Override
    public boolean hasNext() {
        return nextEl != null;
    }

    @Override
    public Integer next() {
        Integer res = nextEl;
        advance();
        return res;  
    }

    public void skip(int num) {
        if(nextEl != null && nextEl == num) {
            //skip immediately
            advance();
        } else {
            //to be skipped in the future
            skipMap.put(num, skipMap.getOrDefault(num, 0) +1);
        }
        
    }

    private void advance() {
        nextEl = null; //reset
        while(it.hasNext()) {
            Integer el = it.next();
            if(skipMap.containsKey(el)) {
                //Need to decreement the count by 1
                skipMap.put(el, skipMap.get(el) -1);
                if(skipMap.get(el) == 0) {
                    //count has become 0 skip it
                    skipMap.remove(el);
                }
                continue;
                
            } else {
                //element is not in hashmap, mark it the next element
                nextEl = el;
                break;
            }
        }
        
    }
}

public class Main {
        public static void main(String[] args) {
        SkipIterator itr = new SkipIterator(Arrays.asList(2, 3, 5, 6, 5, 7, 5, -1, 5, 10).iterator());
        itr.hasNext(); // true
        //itr.next(); // returns 2
        System.out.println(itr.next());
        itr.skip(5);
        System.out.println(itr.next()); // returns 3
        System.out.println(itr.next()); // returns 6 because 5 should be skipped
        System.out.println(itr.next()); // returns 5
        itr.skip(5);
        itr.skip(5);
        System.out.println(itr.next()); // returns 7
        System.out.println(itr.next()); // returns -1
        System.out.println(itr.next()); // returns 10
        System.out.println(itr.hasNext());// false
        System.out.println(itr.next()); // error
        System.out.println(itr.hasNext()); //false
    }
}
