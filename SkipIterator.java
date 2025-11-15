// Time Complexity : For next(), skip() : Amortized O(1), For hasNext(): O(1)
// Space Complexity : O(k) where k is the size of skipMap
// Did this code successfully run on Leetcode : Yes
// Approach : We maintain a iterator and store the current Element value and move the next pointer. For hasNext, we check if the
// currElement has value, for next we return currElement and advance. For skip, we maintain a hashMap for skip elements, if currelement
// needs to be skipped we advance else add to the hashmap. While advancing, we check in the skipMap as well if the element is present there.

// "static void main" must be defined in a public class.
class SkipIterator implements Iterator<Integer> {
    private Iterator<Integer> iter;
    private Integer currElement;
    HashMap<Integer, Integer> skipMap;

    public SkipIterator(Iterator<Integer> it) {
        this.iter = it;
        this.currElement = currElement;
        this.skipMap = new HashMap<>();
        advance();
    }

    private void advance(){ //tp store current value and move the next pointer
        currElement = null;
        while(currElement == null && iter.hasNext()){
            Integer el = iter.next();
            if(!skipMap.containsKey(el)){ //check if next element is in skipMap
                currElement = el;
            } else {
                skipMap.put(el, skipMap.get(el) - 1);//reduce the value as we skipped the key
                if(skipMap.get(el) == 0){
                    skipMap.remove(el);
                }
            }
        }
    }

    public boolean hasNext() {
        return currElement != null; //check currElement value
    }

    public Integer next() {//return currElement and advance
        Integer temp = currElement;
        advance();
        return temp;
    }

    /**
     * The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
     * This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
     */
    public void skip(int val) {
        if(val == currElement){
            advance();//if currElement is the one needs to be skipped, we advance
        } else{
            skipMap.put(val, skipMap.getOrDefault(val, 0) + 1);
        }
    }
}

public class Main {

    public static void main(String[] args) {

        SkipIterator sit = new SkipIterator(Arrays.asList(5,6,7,5,6,8,9,5,5,6,8).iterator());

        System.out.println(sit.hasNext());// true
        System.out.println(sit.next()); //5   nextEl = 6
        sit.skip(5);  // will be store in map
        System.out.println(sit.next());// 6 nextEl = 7
        System.out.println(sit.next()); // 7 nextEl = 6
        sit.skip(7); // nextEl = 6
        sit.skip(9); // store in map

        System.out.println(sit.next()); // 6 nextEl = 8

        System.out.println(sit.next()); //8
        System.out.println(sit.next());// 5
        sit.skip(8); //nextEl = null
        sit.skip(5);
        System.out.println(sit.hasNext()); //true
        System.out.println(sit.next()); //6
        System.out.println(sit.hasNext()); //false


        // System.out.println(it.hasNext()); //false

    }

}