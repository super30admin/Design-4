import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

class SkipIterator implements Iterator<Integer> {
    HashMap<Integer , Integer> skipMap;
    Integer nextEl;
    Iterator<Integer> nit;
    public SkipIterator(Iterator<Integer> it) {
        this.nit = it;
        this.skipMap = new HashMap<>();
        advance();
    }
    private void advance(){  //O(n)
        this.nextEl = null;
        while(nit.hasNext() && nextEl == null){
            Integer el = nit.next();
            if(skipMap.containsKey(el)){
                skipMap.put(el, skipMap.get(el) - 1);
                skipMap.remove(el, 0);
            } else {
                nextEl = el;
            }
        }
    }
    @Override
    public boolean hasNext() {  //O(1)
        return nextEl != null;
    }

    @Override
    public Integer next() { //O(n)
        Integer result = nextEl;
        advance();
        return result;
    }

    public void skip(int num) { //O(n)
        if(num == nextEl){
            advance();
        } else {
            skipMap.put(num, skipMap.getOrDefault(num, 0) + 1 );
        }
    }

    public static void main(String[] args) {

        SkipIterator it = new SkipIterator(Arrays.asList(5,6,7,5,6,8,9,5,5,6,8).iterator());

        System.out.println(it.hasNext());// true
        it.skip(5);//
        System.out.println(it.next()); //6
        it.skip(5);
        System.out.println(it.next());// 7
        System.out.println(it.next()); // 6
        it.skip(8); //
        it.skip(9); //

        System.out.println(it.next()); // 5

        System.out.println(it.next()); //5
        System.out.println(it.next());//6
        System.out.println(it.hasNext());// true
        System.out.println(it.next());// 8
        System.out.println(it.hasNext()); //false

    }

}