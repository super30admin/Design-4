// Time Complexity : O(n)
// Space Complexity : O(n)
// Did this code successfully run on Leetcode :
// Any problem you faced while coding this :
class SkipIterator implements Iterator<Integer> {
    Iterator<Integer> nit;
    Integer nextel;
    Map<Integer,Integer> skipmap;
   public SkipIterator(Iterator<Integer> it) { 
        this.nit = it;
        this.skipmap = new HashMap<>();
        advance();
    }
    private void advance(){ //O(n)
        // it is taking the pointer of skip iterator to next valid location;
        nextel = null;
        while(nextel == null && nit.hasNext()){
            Integer el = nit.next();
            if(skipmap.containsKey(el))
            {
                skipmap.put(el,skipmap.get(el)-1);
                skipmap.remove(el,0);
            }
            else
                nextel = el;
        }
    }
     public void skip(int num) {  //O(n)
        if(nextel != num)
            skipmap.put(num,skipmap.getOrDefault(num,0)+1);
        else
            advance();
    }
   @Override
     public boolean hasNext() { //O(1)
         return nextel!=null
     }

   @Override
     public Integer next() {  //O(n)
        Integer ans = nextel;
        advance();
        return ans;
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