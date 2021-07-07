class SkipIterator implements Iterator<Integer> {
    // Time complexity: O(n)
    // Space complexity: O(n)
    private  Iterator<Integer> it;
    private  Map<Integer, Integer> count;
    private Integer nextEl;

    public SkipIterator(Iterator<Integer> it) {
        this.it = it;
        this.count = new HashMap<>();
        advance();
    }

    public boolean hasNext() {
        return nextEl != null;
    }

    public Integer next() {
        if (!hasNext()) throw new RuntimeException("empty");
        Integer el = nextEl;
        advance();
        return el;
    }
    /**
    * The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
    * This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
    */ 
    public void skip(int num) {
        if (!hasNext()) throw new RuntimeException("empty");
        if (nextEl == num) {
            advance();
        } else {
            count.put(num, count.getOrDefault(num, 0) + 1);
        }
    }

    private void advance() {
        nextEl = null;
        while (nextEl == null && it.hasNext()) {
            Integer el = it.next();
            if (!count.containsKey(el)) {
                nextEl = el;
            } else {
                count.put(el, count.get(el) - 1);
                count.remove(el, 0);
            }
        }
    }
}

// public class Main {
//         public static void main(String[] args) {
//         SkipIterator it = new SkipIterator(Arrays.asList(1, 2, 3).iterator());
//         System.out.println(it.hasNext());
//         it.skip(2);
//         it.skip(1);
//         it.skip(3);
//         System.out.println(it.hasNext());
//     }
// }