// "static void main" must be defined in a public class.
class SkipIterator implements Iterator<Integer> {
    HashMap<Integer, Integer> elementsToSkip = null;
    Iterator<Integer> iterator;
    Integer nextElement;
    
    public SkipIterator(Iterator<Integer> it) {
        iterator = it;
        elementsToSkip = new HashMap<>();
        nextElement = null;
    }
    
    public Integer next() {
        int result = nextElement;
        advance();
        return result;
    }
    
    public boolean hasNext() {
        advance();
        return nextElement != null;
    }
    
    public void skip(int num) {
        elementsToSkip.put(num, elementsToSkip.getOrDefault(num, 0) + 1);
        if (nextElement == num) {
            advance();
        }
    }
    
    public void advance() {
        nextElement = null;
        
        while (nextElement == null && iterator.hasNext()) {
            int cur = iterator.next();
            if (elementsToSkip.containsKey(cur)) {
                int freq = elementsToSkip.get(cur);
                if (freq - 1 == 0) {
                    elementsToSkip.remove(cur);
                } else {
                    elementsToSkip.put(cur, freq - 1);
                }
            } else {
                nextElement = cur;
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        SkipIterator itr = new SkipIterator(Arrays.asList(2, 3, 5, 6, 5, 7, 5, -1, 5, 10).iterator());
        System.out.println(itr.hasNext()); // true
        System.out.println(itr.next()); // returns 2
        itr.skip(5);
        System.out.println(itr.next()); // returns 3
        System.out.println(itr.next()); // returns 6 because 5 should be skipped
        System.out.println(itr.next()); // returns 5
        itr.skip(5);
        itr.skip(5);
        System.out.println(itr.next()); // returns 7
        System.out.println(itr.next()); // returns -1
        System.out.println(itr.next()); // returns 10
        System.out.println(itr.hasNext()); // false
        System.out.println(itr.next()); // error
    }
}