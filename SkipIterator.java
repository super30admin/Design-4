//Time: (mentioned against each function) | Space: O(N)

class SkipIterator implements Iterator<Integer> {
    // custom element
    Integer nextElement;
    // native copy
    Iterator<Integer> localIt;
    // whenever element is to be skipped are captured in this dictionary
    Map<Integer, Integer> skipMap;
    public SkipIterator(Iterator<Integer> it) {
        this.localIt = it;
        skipMap = new HashMap<>(); // Space: O(N)
        // initialising the nextElement at the beginning with native iterator
        adv();
    }
    @Override
    public boolean hasNext() { // Time: O(1)
        return nextElement != null;
    }
    @Override
    public Integer next() { // Time: O(N)
        // current state of nextElement is the result
        Integer result = nextElement;
        // then advancing the nextElement to next valid pos
        adv();
        return result;
    }

    public void adv(){ // Time: O(N)
        // making nextElement null at the beginning like initialisation
        nextElement = null;
        // as soon as nextElement is null and still have elements to explore further
        // we go inside the while loop
        while(nextElement== null && localIt.hasNext()) {
            // we take the curr val
            Integer el = localIt.next();
            // check in dictionary if that's to be skipped,
            // if so, we reduce the count in the counter table against that val
            if(skipMap.containsKey(el))  {
                skipMap.put(el, skipMap.get(el)-1);
                // when we finish running into all skip counts of val from the list
                // can not remove the entry from the dictionary
                if(skipMap.get(el) == 0)
                    skipMap.remove(el);
            } else {
                // if the element doesn't have to be skipped,
                // we proceed ahead
                nextElement = el;
            }

        }
    }


    public void skip(int val) { // Time: O(N)
        if(val == nextElement) adv();
        else skipMap.put(val, skipMap.getOrDefault(val, 0)+1);
    }
}


public class Main {
    public static void main(String[] args) {
        SkipIterator itr = new SkipIterator(Arrays.asList(2, 3, 5, 6, 5, 7, 5, -1, 5, 10).iterator());
        itr.skip(2);
        System.out.println(itr.hasNext()); // true
        System.out.println(itr.next());//returns 3
        itr.skip(5);

        System.out.println(itr.next()); // returns 6
        System.out.println(itr.next()); // returns 5
        System.out.println(itr.next()); // returns 7
        itr.skip(5);
        itr.skip(5);
        System.out.println(itr.next()); // returns -1
        System.out.println(itr.next()); // returns 10
        System.out.println(itr.next()); // returns null
        System.out.println(itr.hasNext()); // false
        System.out.println(itr.next()); // null

        SkipIterator it = new SkipIterator(Arrays.asList(1, 2, 3).iterator());
        System.out.println(it.hasNext());//true
        it.skip(2);
        it.skip(1);
        it.skip(3);
        System.out.println(it.hasNext());//false
    }
}