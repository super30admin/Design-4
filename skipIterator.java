
// Time complexity: O(1) for next() and skip() but O(1) amortized for hasNext()
// Space complexity: O(n)

// https://leetcode.com/discuss/interview-question/341818

class SkipIterator implements Iterator<Integer> {
    Iterator<Integer> nit; // native iterator
    HashMap<Integer,Integer> skipMap; // to store frequency of number of times number needs to skipped
    Integer nextEl;

    public SkipIterator(Iterator<Integer> it) {
        this.nit = it;
        this.skipMap = new HashMap<>();
        advance();
    }

    @Override
    public boolean hasNext() {
        return nextEl != null;
    }

    @Override
    public Integer next() {
        // if(!hasNext()) new throw RuntimeException("No more elements!!");
        Integer res = nextEl;
        advance();
        return res;
    }

    public void skip(int num) {
        if(num != nextEl){
            skipMap.put(num, skipMap.getOrDefault(num,0) + 1);
        }
        else{
            advance(); // skip
        }
    }

    private void advance() {
        nextEl = null;
        while(nit.hasNext() && nextEl == null){
            Integer el = nit.next();
            if(skipMap.containsKey(el)){
                skipMap.put(el, skipMap.get(el) - 1);
                skipMap.remove(el,0);
            }else{
                nextEl = el;
            }
        }
        // System.out.println("nextEl " + nextEl);
    }
}

public class Main {
        public static void main(String[] args) {
        Integer[] testArr = new Integer[] { 2, 3, 5, 6, 5, 7, 5, -1, 5, 10 };
        SkipIterator itr = new SkipIterator(Arrays.asList(testArr).iterator());
        System.out.println(itr.hasNext()); // true
        System.out.println(itr.next()); // returns 2
        itr.skip(5);
        System.out.println(itr.hasNext()); // true
        System.out.println(itr.next()); // returns 3
        System.out.println(itr.hasNext());
        System.out.println(itr.next()); // returns 6 because 5 should be skipped
        System.out.println(itr.hasNext());
        System.out.println(itr.next()); // returns 5
        itr.skip(5);
        itr.skip(5);
        System.out.println(itr.hasNext());
        System.out.println(itr.next()); // returns 7
        System.out.println(itr.hasNext());
        System.out.println(itr.next()); // returns -1
        System.out.println(itr.hasNext());
        System.out.println(itr.next()); // returns 10
        System.out.println(itr.hasNext()); // false
    }
}