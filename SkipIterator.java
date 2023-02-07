// Time Complexity : hasNext() -> O(1), next() -> O(n)
// Space Complexity : O(n) n= no of skips
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No

// Your code here along with comments explaining your approach
// advance() function sets the value of nextEl which is the next element in the iterator
// hasNext() and next() return values based on nextEl
// we use a map to keep track of future elements to skip

class SkipIterator implements Iterator<Integer> {
    Integer nextEl;
    HashMap<Integer, Integer> map;
    Iterator<Integer> iter;
    
    public SkipIterator(Iterator<Integer> it) { 
        this.iter = it;
        nextEl = null;
        map = new HashMap<>();
        advance();     
    }
 
    private void advance(){ //O(n)
        nextEl = null;
        while(iter.hasNext()){
            Integer temp = iter.next();
            if(map.containsKey(temp)){
                map.put(temp, map.get(temp)-1);
                if(map.get(temp)==0)
                    map.remove(temp);
            }
            else{
                nextEl = temp;
                break;
            }
        }
    }

   @Override
     public boolean hasNext() { // O(1)
         return nextEl!=null;
     }

   @Override
     public Integer next() { // O(n)
        Integer res = nextEl;
         advance();
         return res;
     }

   public void skip(int num) { //O(n)
     if(nextEl==num)
        advance();
      else
         map.put(num, map.getOrDefault(num,0)+1);
   }


 }

public class Main {

         public static void main(String[] args) {

        SkipIterator it = new SkipIterator(Arrays.asList(5,6,7,5,6,8,9,5,5,6,8).iterator());

         System.out.println(it.hasNext());// true
        it.skip(5);// 
         System.out.println(it.next()); //6
         it.skip(5);
             System.out.println(it.next());// 7
             System.out.println(it.next()); // 6
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