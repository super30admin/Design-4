

// Time complexity - O(1)
// Space ocmplexity - O(N)

class SkipIterator implements Iterator<Integer> {

    private Iterator<Integer> it;
    private Integer nextEl;
    private HashMap<Integer, Integer> map;


    public SkipIterator(Iterator<Integer> it) {

        this.it = it;
        this.map = new HashMap<>();
        // function to use when we want. toskip the first value
        advance();

    }

    public boolean hasNext() {

        // check if nextEl is not null
        return nextEl != null;
    }

    public Integer next() {

        // first store nextEL to temp and advance the nextEl
        Integer temp = nextEl;
        advance();
        return temp;

    }

    /**
     * The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
     * This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
     */
    public void skip(int val) {

        // if current val is equal to nextEl advance the nextEl pointer or put the skip element in hashmap
        if(val == nextEl) {

            advance();

        }
        else {

            map.put(val, map.getOrDefault(val,0) + 1);

        }


    }

    public void advance() {

        // initialize nextEl
        nextEl = null;

        // if the iterator has next element then go ahead with the list and also nextEl is null
        while(nextEl == null && it.hasNext()) {

            // get the element if it is not inside the map then initialize to nextEl
            Integer el = it.next();
            if(!map.containsKey(el)) {

                nextEl = el;

            }

            // if it is there inside the map then decrement the el count; if el count hits 0 remove from map
            else {

                map.put(el,map.get(el) - 1);
                map.remove(el,0);

            }

        }


    }
}





public class Main {
    public static void main(String[] args) {
        SkipIterator it = new SkipIterator(Arrays.asList(5,6,7,8,9,10).iterator());

        System.out.println(it.hasNext());
        it.skip(5);

        System.out.println(it.next());
        System.out.println(it.next());
        it.skip(9);
        it.skip(10);
        System.out.println(it.next());
        System.out.println(it.hasNext());



    }
}