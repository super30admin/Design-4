/**
 * Time: O(n) n- length of the iterator
 * Space: O(n)
 */
// "static void main" must be defined in a public class.
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

    }
}
class SkipIterator implements Iterator<Integer> {
    HashMap<Integer,Integer> hmap = new HashMap<>();
    Iterator<Integer> it;
    Integer nxt;
    public SkipIterator(Iterator<Integer> it) {
        this.it = it;
    }

    public boolean hasNext() {
        advance();
        return nxt != null;
    }

    public Integer next() {
        Integer temp = nxt;
        advance();
        return temp;
    }

    /**
     * The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
     * This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
     */
    public void skip(int val) {
        if(nxt == val)
        {
            advance();
        }
        else{
            if(!hmap.containsKey(val))
                hmap.put(val,0);
            hmap.put(val,hmap.get(val)+1);
        }
    }
    public void advance(){
        nxt = null;
        while(nxt == null && it.hasNext()){
            nxt = it.next();
            if(hmap.containsKey(nxt) && hmap.get(nxt) != 0){
                hmap.put(nxt,hmap.get(nxt)-1);
                nxt = it.next();
            }
        }
    }
}