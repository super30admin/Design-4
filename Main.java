// "static void main" must be defined in a public class.

/*
- Skip iterator is a functionality on top of the native iterator where if skip is called, that element should be skipped
- Two cases 
    - When skip is called on a future element in the iterator
        - We need to store the future element along with its frequency to make sure whenever we process nextEl, we check the skip list before HasNext and Next returns any answer
        - Makes sense to use Map, key: element to be skipped val: frequency
    - When skip is called on the immediate element in the iterator
        - If we apply the concept of putting the skip element in the skipMap, it wont work in the case of immediate element to be skipped. 
        eg. if you have [1,2,3,4,6] and nit(next iterator) is pointing at 6 so nextEl is set to 6, if skip(6) is called and we store it in the skipMap, the hasNext function will give "true" which is incorrect and then next will skip 6 based on the previous logic but hasNext will give incorrect result
*/


// SC: O(k) -> skipmap worst case, all the elements in the iterator

class SkipIterator implements Iterator{
    HashMap<Integer, Integer> skipMap;
    Integer nextEl;
    Iterator<Integer> nit;
    
    public SkipIterator(Iterator<Integer> it){
        this.skipMap = new HashMap<>();
        this.nit = it;
        advance();
    }
    
    //TC: O(1)
    public boolean hasNext(){
        return nextEl != null;
    }
    
    // TC: O(1) - amortized
    public Integer next(){
        Integer el = nextEl;
        advance();
        return el;
    }
    
    // TC: O(1) - amortized
    public void skip(int val){
        // case1: nextEl is to be skipped
        if(nextEl == val){
            advance();
        }
        // case2: future element is to be skipped
        else{
            skipMap.put(val, skipMap.getOrDefault(val, 0) + 1);
        }
    }
    
    public void advance(){
        this.nextEl = null;
        // get the next element and if is not in the skipMap, set nextEl to the next valid element
        while(nit.hasNext()){
            Integer el = nit.next();
            if(!skipMap.containsKey(el)){
                nextEl = el;
                break;
            }
            else{
                skipMap.put(el, skipMap.get(el) - 1);
                if(skipMap.get(el) == 0)
                    skipMap.remove(el);
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        SkipIterator itr = new SkipIterator(Arrays.asList(5, 6, 7, 5, 6, 8, 9, 5, 5, 6, 8).iterator());
        System.out.println(itr.hasNext()); // true
        itr.skip(5);
        System.out.println(itr.next()); // 6
        itr.skip(5);
        System.out.println(itr.next()); // 7
        System.out.println(itr.next()); // 6
        itr.skip(8);
        itr.skip(9);
        System.out.println(itr.next()); // 5
        System.out.println(itr.next()); // 5
        System.out.println(itr.next()); // 6
        System.out.println(itr.hasNext()); // true
        System.out.println(itr.next()); // 8
        System.out.println(itr.hasNext()); // false
    }
}