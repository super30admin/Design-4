
class SkipIterator implements Iterator<Integer> {
    Iterator<Integer> iterator;
    Map<Integer, Integer> countMap;
    Integer cursor;
    
    public SkipIterator(Iterator<Integer> it) {
        iterator = it;
        countMap = new HashMap<>();
        moveCursor();
    }

    @Override
    public boolean hasNext() {
        //check if list has more elements to iterate over.
        return cursor != null;
    }

    @Override
    public Integer next() {
        Integer curElement = cursor;
        moveCursor();
        return curElement;
    }

    public void skip(int num) {
        //we have to skip the current number. so saving that in a map
        //how many time we need to skip this number.
        if (cursor == num) {
            moveCursor();
        } else {
            countMap.put(num, countMap.getOrDefault(num, 0) + 1);
        }
    }

    private void moveCursor() {
        //setting cursor to null and I would be setting the cursor to its safe location
        //till the current number I am iterating on list is not in skip map.
        cursor = null;
        while (cursor == null && iterator.hasNext()) {
            Integer curElement = iterator.next();
            //I would be considering the current element if that is not present in my map 
            //where I am maintaining the skip count. 
            //or if count is less than 0 if ele present
            if (!countMap.containsKey(curElement) || countMap.get(curElement) <=0) {
                cursor = curElement;
            } else {
                //if current element in the skip list, we have already skip this
                //by moving the cursor, so decrementing its count in the map.
                countMap.put(curElement, countMap.get(curElement) - 1);
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
        System.out.println(itr.next()); // null
    }
}
