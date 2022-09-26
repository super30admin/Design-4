// Time Complexity :
// Space Complexity :
// Did this code successfully run on Leetcode :
// Any problem you faced while coding this :

// Your code here along with comments explaining your approach
//Skip Iterator - https://leetcode.com/discuss/interview-question/341818/Google-or-Onsite-or-Skip-Iterator
//Solution - https://leetcode.com/playground/tx9xyHxq
// https://leetcode.com/discuss/interview-question/341818

class SkipIterator implements Iterator<Integer> {
    
    private Iterator<Integer> it;
    private HashMap<Integer, Integer> skipMap;
    private Integer nextEl;
    
    public SkipIterator(Iterator<Integer> it) {
        this.it = it;
        this.skipMap = new HashMap<>();
        advance(); // set the valid next element
    }

    private void advance() { // O(n) where n = number of elements in iterator
        nextEl = null;
        
        while (nextEl == null && it.hasNext()) {
            Integer element = it.next();
            
            if (skipMap.containsKey(element)) {
                skipMap.put(element, skipMap.get(element) - 1);
                skipMap.remove(element, 0);
            } else {
                nextEl = element;
            }
        }
    }
    
    @Override
    public boolean hasNext() { // O(1)
        return nextEl != null;
    }

    @Override
    public Integer next() { // O(n) where n = number of elements in iterator
        Integer temp = nextEl;
        advance(); // O(n) where n = number of elements in iterator
        return temp;
    }

    public void skip(int num) { // O(n) where n = number of elements in iterator
        if (nextEl == num) {
            advance(); // O(n) where n = number of elements in iterator
        } else {
            skipMap.put(num, skipMap.getOrDefault(num, 0) + 1);
        }
    }
}

public class Main {
        public static void main(String[] args) {
        SkipIterator it = new SkipIterator(Arrays.asList(5, 6, 7, 5, 6, 8, 9, 5, 5, 6, 8).iterator());
            
        System.out.println(it.hasNext()); // true
        it.skip(5);
        System.out.println(it.next()); // 6
        it.skip(5);
        System.out.println(it.next()); // 7
        System.out.println(it.next()); // 6
        it.skip(8);
        it.skip(9);
        System.out.println(it.next()); // 5
        System.out.println(it.next()); // 5
        System.out.println(it.next()); // 6
        System.out.println(it.next()); // 8
        System.out.println(it.hasNext()); // false
    }
}