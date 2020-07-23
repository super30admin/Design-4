// Time Complexity : O(n) n is number of values
// Space Complexity :O(m) number of skip calls
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No

class SkipIterator implements Iterator<Integer> {
    private final Iterator<Integer> it;
    private final Map<Integer, Integer> map;
    private Integer nextEl;

    public SkipIterator(Iterator<Integer> it) {
        map = new HashMap<>();
        this.it = it;
        advance();
    }

    @Override
    public boolean hasNext() {
        return nextEl!= null;
    }

    @Override
    public Integer next() {
        int temp = nextEl;
        advance();
        return temp;
    }

    public void skip(int val) {
        if(val == nextEl){
            advance();
        }
        else{
            map.put(val,map.getOrDefault(val,0)+1);
        }
    }

    private void advance() {
        nextEl = null;
        while(nextEl==null && it.hasNext()){
            int el = it.next();
            if(map.containsKey(el)){
                map.put(el,map.get(el)-1);
                map.remove(el,0);
            } else{
                nextEl = el;
            }
        }
    }
}

public class Main {
        public static void main(String[] args) {
        SkipIterator it = new SkipIterator(Arrays.asList(2,3,5,6,5,7,5,-1,5,10).iterator());
        System.out.println(it.hasNext());
        System.out.println(it.next());
        it.skip(5);
        System.out.println(it.next());
        System.out.println(it.next());
        System.out.println(it.next());
        it.skip(5);
        it.skip(5);
        System.out.println(it.next());
        System.out.println(it.next());
        System.out.println(it.next());
        System.out.println(it.hasNext());
    }
}