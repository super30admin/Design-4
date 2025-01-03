class SkipIterator implements Iterator<Integer> {
    Iterator<Integer> nit;
    Integer nextEl;
    HashMap<Integer, Integer> skipMap;
    public SkipIterator(Iterator<Integer> it) {
        this.skipMap = new HashMap<>();
        this.nit = it;
        advance(); // will set the initial value of nextEl.
    }
    
    private void advance() { // O(1) // give me the next value of nextEl 
        nextEl = null;
        while(nextEl == null  && nit.hasNext()) {
            Integer el = nit.next();
            if(skipMap.containsKey(el)) {
                skipMap.put(el, skipMap.get(el)-1);
                if(skipMap.get(el) ==0) {
                    skipMap.remove(el);
                }
            } else {
                nextEl = el;
            }
        }
    }
    
    public void skip(int num) { // O(n)
        if(num == nextEl) {
            advance();
        } else {
            skipMap.put(num, skipMap.getOrDefault(num, 0 ) + 1);
        }
        
    }
    
    @Override
    public boolean hasNext() { // O(1)
        return nextEl!=null;
    }
    
    @Override
    public Integer next() { // O(n)
        int re = nextEl;
        advance();
        return re;
        
    }
}
public class Main {
    public static void main(String[] args) {
        SkipIterator it = new SkipIterator(Arrays.asList(5,6,7,5,6,8,9,5,5,6,8).iterator());
        
        System.out.println(it.hasNext());// true, nextEl=5;
        it.skip(5); // this happens when the nextEl is same to the element which is supposed to be skipped.
        System.out.println(it.next());//  6, nextEl=7 we have skipped 5.
        it.skip(5);// This time we have pushed 5 into the map for future skipping.
        System.out.println(it.next()); // 7, nextEl=6, we have skipped 5. 
        System.out.println(it.next()); // 6, nextEl=8
        it.skip(8); // nextEl == current skipped .  nextEl will be at 9
        it.skip(9); // nextEl == current skipped .  nextEl will be at 5
        System.out.println(it.next()); // 5, nextEl=5
        System.out.println(it.next()); // 5, nextEl=6
        System.out.println(it.next()); // 6, nextEl=8
        System.out.println(it.hasNext()); // true
        it.skip(8); // nextEl = null
        System.out.println(it.hasNext()); // false.
    }
}