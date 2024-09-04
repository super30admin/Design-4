// Problem Google | Skip Iterator
// Time Complexity : O(n)
// Space Complexity : O(n)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this :

// Your code here along with comments explaining your approach
class SkipIterator implements Iterator<Integer> {
    Iterator<Integer> nit;
    Integer nextEl; //next el of skip itertor
    HashMap<Integer, Integer> skipMap;
    public SkipIterator(Iterator<Integer> it){
        this.skipMap = new HashMap<>();
        this.nit = it;
        advance(); // will set the initial val of nextEl
    }

    private void advance(){ //gives us next valid value of nextEl
        nextEl = null;
        while(nextEl == null && nit.hasNext()){
            Integer el = nit.next();
            if(skipMap.containsKey(el)){
                skipMap.put(el, skipMap.get(el) - 1);
                skipMap.remove(el, 0);
            } else {
                nextEl = el;
            }
        }

    }
    public void skip(int num) {  //O(n)
        if(num == nextEl){
            advance();
        } else {
            skipMap.put(num, skipMap.getOrDefault(num, 0) + 1);
        }
    }
    @Override
    public boolean hasNext() { //O(1)
        return nextEl != null;
    }

    @Override
    public Integer next() { //O(n)
        int re = nextEl;
        advance();
        return re;
    }


}

public class Main {

    public static void main(String[] args) {

        SkipIterator it = new SkipIterator(Arrays.asList(5,6,7,5,6,8,9,5,5,6,8).iterator());

        System.out.println(it.hasNext());// true // nextEl = 5
        it.skip(5);//  nextEl = 6
        System.out.println(it.next()); //6   nextEl = 7
        it.skip(5);  // will be store in map
        System.out.println(it.next());// 7 nextEl = 6
        System.out.println(it.next()); // 6 nextEl = 8
        it.skip(8); // nextEl = 9
        it.skip(9); // nextEl = 5

        System.out.println(it.next()); // 5 nextEl = 5

        System.out.println(it.next()); //5 nextEl = 6
        System.out.println(it.next());//6  nextEl = 8
        System.out.println(it.hasNext());// true
        it.skip(8); //nextEl = null
        System.out.println(it.hasNext()); //false
        // System.out.println(it.next());//
        // it.skip(1);

//          it.skip(3);

        // System.out.println(it.hasNext()); //false

    }

}