// Time Complexity : O(n)
// Space Complexity : O(k) k - max size of the skipmap
// Did this code successfully run on Leetcode : No 
// Any problem you faced while coding this : No

// "static void main" must be defined in a public class.

class SkipIterator implements Iterator<Integer> {
    HashMap<Integer, Integer> skipMap;
    Integer nextEle;
    Iterator<Integer> natIt;//native iterator

    public SkipIterator(Iterator<Integer> it) {
        this.skipMap = new HashMap<>();
        this.natIt = it;
        advance();
    }
    private void advance(){ //O(n)
        // it is taking the pointer of skip iterator to next valid location;
        nextEle = null;
        while(nextEle == null && natIt.hasNext()){
            Integer ele = natIt.next();
            if(skipMap.containsKey(ele)){
                skipMap.put(ele, skipMap.get(ele) - 1);
                skipMap.remove(ele, 0);
            }
            else{
                nextEle = ele;
            }
        }
    }

    public void skip(int num) {  //O(n)
        if(num != nextEle) {
            skipMap.put(num, skipMap.getOrDefault(num, 0) + 1);
        }
        else{
            advance();
        }
    }

    @Override
    public boolean hasNext() { //O(1)
        return nextEle != null;
    }

    @Override
    public Integer next() {  //O(n)
        Integer result = nextEle;
        advance();
        return result;
    }


}

public class Main {

    public static void main(String[] args) {

        SkipIterator it = new SkipIterator(Arrays.asList(5,6,7,5,6,8,9,5,5,6,8).iterator());

        System.out.println(it.hasNext());// true // nextEl = 5
        it.skip(5);//  nextEl = 6
        System.out.println(it.next()); //6   nextEl = 7
        it.skip(5);
        System.out.println(it.next());// 7 nextEl = 6
        System.out.println(it.next()); // 6nextEl = 6
        it.skip(8); //
        it.skip(9); //

        System.out.println(it.next()); // 5

        System.out.println(it.next()); //5
        System.out.println(it.next());//6
        System.out.println(it.hasNext());// true
        System.out.println(it.next());// 8
        // it.skip(1);

//          it.skip(3);

        System.out.println(it.hasNext()); //false

    }

}