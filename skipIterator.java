import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

// "static void main" must be defined in a public class
class SkipIterator1 implements Iterator<Integer>{
    private final Iterator<Integer> itr;
    private final Map<Integer, Integer> skipMap;
    private Integer nextEle; // a pointer to the next element

    public SkipIterator1(Iterator<Integer> it){
        this.itr = it;
        this.skipMap = new HashMap<>();
        advance();
    }

    @Override
    public boolean hasNext(){
        return nextEle != null;
    }

    @Override
    public Integer next(){
        int temp = nextEle;
        advance(); //set a new value of nextEle
        return temp;
    }

    public void skip(int val){
        if(nextEle == val){
            advance();
        }else{
            //(5,1)  skipMap.put(val, skipMap.getOrDefault(val, 0) + 1);
            if(!skipMap.containsKey(val)){
                skipMap.put(val, 1);
            }else{
                //5, 2
                skipMap.put(val, skipMap.get(val) + 1);
            }
        }

    }
    private void advance(){
        //populate ur nextEle, so called advance function here, will advance the nextEle pointer to the right position.
        // It is going to make everything correct. will be populated by advance function()
        nextEle  = null;
        while(nextEle == null && itr.hasNext()){
            Integer ele = itr.next();
            if(!skipMap.containsKey(ele)){
                // if the nextEle is not the element to be skipped
                nextEle = ele;
            }else{
                //if the next element is the element to be skipped, then decrease the count in the HashMap
                skipMap.put(ele, skipMap.get(ele) - 1);
                skipMap.remove(ele, 0); // remove it from the map, if the value is 0
            }
        }
    }
}

public class skipIterator {
    public static void main(String[] args) {
//        SkipIterator1 it = new SkipIterator1(Arrays.asList(1, 2, 3).iterator());
//        System.out.println(it.hasNext());
//        it.skip(2);
//        it.skip(1);
//        it.skip(3);
//        System.out.println(it.hasNext());
        SkipIterator1 it = new SkipIterator1(Arrays.asList(2,3,5,6,5,7,5,-1,5,10).iterator());
        System.out.println(it.hasNext()); //true
        System.out.println(it.next()); //returns 2
        it.skip(5);
        System.out.println(it.next()); // return 3
        System.out.println(it.next()); // return 6
        System.out.println(it.next()); //return 5
        it.skip(5);
        it.skip(5);
        System.out.println(it.next()); //return 7
        System.out.println(it.next()); //-1
        System.out.println(it.next()); //10
        System.out.println(it.hasNext()); //false
    }
}