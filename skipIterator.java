// Problem 1: Design Twitter (https://leetcode.com/problems/design-twitter/)

// Time Complexity : O(n) for advance, skip and next. o(1) for hasNext
// Space Complexity : O(n)
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : no

// Your code here along with comments explaining your approach in three sentences only
/*
 * Here, create a hash map to keep track of skip elements and their count. In advance function, 1st make nextEl as null and if it.hasNext()
 * is true then assign currEl as it.next() and if skipmap contains that currEl then dont do anything just decrease count by 1 else
 * nextEl = currEl. In skip function, if the given num is nextEl then call advance again else just increase the count of element in
 * skipmap. In hasNext check if nextEl is not null. In next function assign nextel to some temp and call advance again and give out
 * temp
 */

// "static void main" must be defined in a public class.


class SkipIterator implements Iterator<Integer> {
    private Integer nextEl;
    private HashMap<Integer, Integer> skipMap;
    private Iterator<Integer> it;
    public SkipIterator(Iterator<Integer> it){
        this.skipMap = new HashMap<>();
        this.it = it;
        advance();
    }
    
    private void advance(){ //O(n)
        this.nextEl = null;
        while(nextEl == null && it.hasNext()){
            Integer currEl = it.next();
            if(!skipMap.containsKey(currEl)){
                nextEl = currEl;    
            }else{
                skipMap.put(currEl, skipMap.get(currEl) - 1);
                skipMap.remove(currEl, 0);
            }
        }
    }
  
 
     public void skip(int num) {  //O(n)
       if(nextEl == num){
         advance();   
       }
        else{
            skipMap.put(num, skipMap.getOrDefault(num, 0)+1);
        }
    }
   @Override
     public boolean hasNext() { //O(1)
         return nextEl!=null;
     }

   @Override
     public Integer next() { //O(n)  
         Integer temp = nextEl;
         advance();
         return temp;
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
         // System.out.println(sit.next());// 5
         // it.skip(1);

//          it.skip(3);

         // System.out.println(it.hasNext()); //false 

     }

 }



