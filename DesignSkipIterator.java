import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

class SkipIterator implements Iterator<Integer> {
    private Iterator<Integer> nativeIterator;
    private HashMap<Integer, Integer> skipMap;
    private Integer nextElement;

    public SkipIterator(Iterator<Integer> it) {
        this.nativeIterator = it;
        this.skipMap = new HashMap<>();
        advance();
    }

    @Override
    public boolean hasNext() {
        return nextElement != null;
    }

    @Override
    public Integer next() {
        Integer result = nextElement;
        nextElement = null;
        advance();
        return result;
    }

    public void skip(int num) {
        if(nextElement == num){
            nextElement = null;
            advance();
        }else{
            skipMap.put(num, skipMap.getOrDefault(num, 0)+1);
        }
    }

    private void advance() {
        while(nativeIterator.hasNext() && nextElement == null){
            Integer currentElement = nativeIterator.next();
            if(skipMap.containsKey(currentElement)){
                skipMap.put(currentElement, skipMap.get(currentElement) - 1);
                skipMap.remove(currentElement, 0);
            }else{
                nextElement = currentElement;
            }
        }
    }
}

class Main {
    public static void main(String[] args) {
        List<Integer> ip = new ArrayList<>(List.of(2, 3, 5, 6, 5, 7, 5, -1, 5, 10));
        SkipIterator itr = new SkipIterator(ip.iterator());
        System.out.println(itr.hasNext()); // true
        System.out.println(itr.next()); // returns 2
        itr.skip(5);
        System.out.println(itr.next()); // returns 3
        System.out.println(itr.next()); // returns 6 because 5 should be skipped
        System.out.println(itr.next()); // returns
        itr.skip(5);
        itr.skip(5);
        System.out.println(itr.next()); // returns 7
        System.out.println(itr.next()); // returns -1
        System.out.println(itr.next()); // returns 10
        System.out.println(itr.hasNext()); // false
        System.out.println(itr.next()); // error
    }
}