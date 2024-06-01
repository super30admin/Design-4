// https://leetcode.com/discuss/interview-question/341818

class SkipIterator implements Iterator<Integer> {
    HashMap<Integer,Integer> map;
    Integer nextEl;
    Iterator<Integer> nit;
    public SkipIterator(Iterator<Integer> it) {
        this.nit = it;
        this.map = new HashMap<>();
        advance();
    }

    public void advance() {
        this.nextEl = null;
        while(nit.hasNext()) {
            Integer el = nit.next();
            if(!map.containsKey(el)) {
                nextEl = el;
                break;
            }else {
                map.put(el, map.get(el)-1);
                if(map.get(el) == 0) {
                    map.remove(el);
                }
            }
        }
    }

    @Override
    public boolean hasNext() {
        return nextEl != null;
    }

    @Override
    public Integer next() {

        Integer el = nextEl;
        advance();
        return el;
    }

    public void skip(int val) {
        if(val == nextEl) {
            advance();
        }else{
            map.put(val,map.getOrDefault(val,0)+1);
        }
    }


}

public class Main {
    public static void main(String[] args) {
        SkipIterator it = new SkipIterator(Arrays.asList(5,6,7,5,6,8,9,5,5,6,8).iterator());
        System.out.println(it.hasNext());
        it.skip(5);
        System.out.println(it.next());
        it.skip(5);
        System.out.println(it.next());
        System.out.println(it.next());
        it.skip(8);
        it.skip(9);
        System.out.println(it.next());
        it.skip(5);
        System.out.println(it.next());
    }
}