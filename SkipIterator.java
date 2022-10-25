class SkipIterator implements Iterator<Integer> {

    HashMap<Integer,Integer> skipMap;
    Iterator<Integer> nit;
    Integer nextEl;
    public SkipIterator(Iterator<Integer> it) {
        this.skipMap = new HashMap<>();
        this.nit = it;
        advance();
    }
    //TC: O(n)
    private void advance(){
        this.nextEl = null;
        while(this.nextEl == null && nit.hasNext()){
            Integer el = nit.next();
            if(skipMap.containsKey(el)){
                skipMap.put(el,skipMap.get(el) -1);
                skipMap.remove(el,0);
            }
            else{
                this.nextEl = el;
            }
        }
    }

    //TC: O(1)
    public boolean hasNext() {
        return nextEl != null;
    }
    //TC: O(n)
    public Integer next() {
        Integer re = nextEl;
        advance();
        return re;
    }
    //TC: O(n)
    /**
     * The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
     * This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
     */
    public void skip(int num) {
        if(num == nextEl){
            advance();
        }
        else{
            skipMap.put(num,skipMap.getOrDefault(num,0)+1);
        }
    }
}

// "static void main" must be defined in a public class.
public class Main {
    public static void main(String[] args) {
        SkipIterator itr = new SkipIterator([2, 3, 5, 6, 5, 7, 5, -1, 5, 10]);
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
        System.out.println(itr.next());
    }
}
