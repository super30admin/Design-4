import java.util.*;

class PeekingIterator implements Iterator<Integer> {
    Integer peek;
    Iterator<Integer> it;
    boolean isDone;

    public PeekingIterator(Iterator<Integer> iterator) {
        this.it = iterator;
        peek = it.next();
    }
    
    public Integer peek() {
        return peek;
    } 
    @Override
    public Integer next() {
        Integer temp = peek;
        if (it.hasNext()) {
            peek = it.next();
        } else {
            isDone = true;
        }
        return temp;
    }

    @Override
    public boolean hasNext() {
        return !isDone;
    }

    public static void main(String[] args) {
        PeekingIterator skipIterator = new PeekingIterator(Arrays.asList(1,2,3).iterator());
        print(skipIterator.next()); // return 1, the pointer moves to the next element [1,2,3].
        print(skipIterator.peek()); // return 2, the pointer does not move [1,2,3].
        print(skipIterator.next()); // return 2, the pointer moves to the next element [1,2,3]
        print(skipIterator.next()); // return 3, the pointer moves to the next element [1,2,3]
        System.out.println(skipIterator.hasNext());
    }

    private static void print(Integer value) {
        System.out.println("The value" + value);
    }

}