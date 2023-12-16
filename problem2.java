// Time Complexity : O(s) , where s is equal to the the number of subsequent skip elements, which in worst case , can be equal to n 
// Space Complexity : O(s) , the number of skip elements to store in the hashmap
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this :no

public class Main {
    public static void main(String[] args) {
        
        int[] arr = {2, 3, 5, 6, 5, 7, 5, -1, 5, 10};
        List<Integer> list = new ArrayList<Integer>();
        for(int i=0;i<arr.length ; i++){
            list.add(arr[i]);
        }
        
        SkipIterator itr = new SkipIterator(list.iterator()); 
        print(itr.hasNext()); // true 
        print(itr.next()); // returns 2 
        itr.skip(5); 
        print(itr.next()); // returns 3 
        print(itr.next()); // returns 6 because 5 should be skipped 
        print(itr.next()); // returns 5 
        itr.skip(5); 
        itr.skip(5); 
        print(itr.next()); // returns 7 
        print(itr.next()); // returns -1 
        print(itr.next()); // returns 10 
        print(itr.hasNext()); // false 
        print(itr.next()); // error
    }
    public static void print(Object i){
        System.out.println(i);
    }

}


class SkipIterator implements Iterator {
    HashMap<Integer, Integer> map; // To store the skip val  to count 
    Iterator<Integer> iter;
    Integer next; 
    
public SkipIterator(Iterator<Integer> it) {
    iter = it;
    map = new HashMap<>();
    if(iter.hasNext()){ // initializing the next 
        next = iter.next();
    }
}
// O(s) , where s is equal to the the number of subsequent skip elements, which in worst case , can be equal to n 
public boolean hasNext() {
    if(next!=null){
        // check if its a skip element
        if(map.containsKey(next) && map.get(next) > 0){
            // it means its a skip element
            while(iter.hasNext() && map.containsKey(next) && map.get(next) > 0){
                // reduce the count and keep on reducing the count after encountering skip elements ( as they can be consecutive)
                map.put(next , map.get(next)-1);
                if(map.get(next) == 0) map.remove(next); //remove when 0
                next = iter.next();
            }
            // check if skip element was the last element on the underlying iterator
            if(map.containsKey(next) && map.get(next) > 0){ // If this condition is true it means that skip element is the only element left in the underlying iterator, so we should return false;
                next = null;
                return false;
            }
        }
        return true;
    }
    else{ // when next is null it means the underlying iterator has no elements
        return false;
    }

}
// O(s) , where s is equal to the the number of subsequent skip elements, which in worst case , can be equal to n 
public Integer next() {
    Integer temp = next;
    advanceNext();
    return temp;
}
    // O(s) , where s is equal to the the number of subsequent skip elements, which in worst case , can be equal to n 
private void advanceNext(){
    if(iter.hasNext()){
        next = iter.next();
        if(hasNext()){
            return;
        }
    }// otherwise make next = null as no more elements left in the underlying iterator
    next = null;
}

/**
* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
*/ 
public void skip(Integer val) {
    if(!map.containsKey(val)){
        map.put(val, 0);
    }
    map.put(val , (map.get(val))+1);
}
}