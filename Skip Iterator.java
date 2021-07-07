// Time Complexity : O(n), input size
// Space Complexity : O(k), number of skips called
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this :


class SkipIterator implements Iterator<Integer> {
    Iterator<Integer> it;
    HashMap<Integer, Integer> map;
    Integer nextElement;

    public SkipIterator(Iterator<Integer> it) {
        map = new HashMap<>();
        this.it = it;
        advance();
    }

    @Override
    public boolean hasNext() {
        return nextElement != null;
    }

    @Override
    public Integer next() {
        int tempval = nextElement;
        advance();
        return tempval;
    }

    public void skip(Integer value) {
        if (value == nextElement) {
            advance();
        }
        else {
            map.put(value, map.getOrDefault(value, 0) + 1);
        }
    }

    private void advance() {
        nextElement = null;
        while (nextElement == null && it.hasNext()) {
            Integer element = it.next();
            if (!map.containsKey(element)) {
                nextElement = element;
            } else {     
                map.put(element, map.get(element) - 1);
                map.remove(element, 0);
            }
        }
    }
}

public class Main {
        public static void main(String[] args) {
        SkipIterator it = new SkipIterator(Arrays.asList(2, 3, 5, 6, 5, 7, 5, -1, 5, 10).iterator());
        System.out.println(it.hasNext()); // true
        System.out.println(it.next()); //2
        it.skip(5);
        System.out.println(it.hasNext()); //true
        System.out.println(it.next()); //3
        System.out.println(it.hasNext()); //true
        System.out.println(it.next()); //6, 5 is skipped
        System.out.println(it.next()); //5 
        it.skip(5);
        it.skip(5);
        System.out.println(it.next()); //7
        System.out.println(it.next()); //-1 
        System.out.println(it.next()); //10
        System.out.println(it.hasNext()); //false
        System.out.println(it.next());
    }
}


/*
//Output
Finished in N/A
true
2
true
3
true
6
5
7
-1
10
false

java.lang.NullPointerException
  at line 66, SkipIterator.next
  at line 111, Main.main

*/
