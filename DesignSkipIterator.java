//Here, we implemented the skip iterator using a regular iterator. 
//We keep the nextEl variable that makes use of the advance() method to point to the next valid element.
//When we iterate through the iterator and find a skip() method, we put it in the map including it's count.
//At each iteration, we check if the given element is in the skip map. If the value exists, we reduce it's count and remove it once it's value becomes 0.
//Time Complexity: O(n) for skip and next methods and O(1) for hasnext method
//Space Complexity: O(n) for hashmap
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

class SkipIterator implements Iterator<Integer> {
    Integer nextEl;
    HashMap<Integer, Integer> skipMap;
    Iterator<Integer> it;
    public SkipIterator(Iterator<Integer> it){
        this.it = it;
        this.skipMap = new HashMap<>();
        advance();
    }
  
    private void advance(){
        this.nextEl = null;
        while(nextEl == null && it.hasNext()){
            Integer currEl = it.next();
            if(!skipMap.containsKey(currEl)){
                nextEl = currEl;
            } else {
                skipMap.put(currEl, skipMap.get(currEl) - 1);
                skipMap.remove(currEl, 0);
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
         int temp = nextEl;
         advance();
         return temp;
     }

  
}

class Main {

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
         // System.out.println(sit.next());// 5
         // it.skip(1);

//          it.skip(3);

         // System.out.println(it.hasNext()); //false 

     }

 }