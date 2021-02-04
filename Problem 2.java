//Time Complexity: hasNext() : O(1), next(): O(1), skip(): O(N), advance(): O(N) where N is the length of list
//Space Complexity: O(N) for hashmap space

//Successfully runs on leetcode: 152 ms

//Approach: Two things to keep in mind: Use of advance function to place the next pointer to a valid element after skipping
//some element(s). If SkipIterator's next pointer and Iterator's next pointer are at the same element, just perform advance i.e
//skip the current element otherwise add the skip iterator's element to the hashmap for future occurences.



class SkipIterator implements Iterator<Integer> {
    Iterator<Integer> it;
    HashMap<Integer,Integer> hmap; 
    Integer nextEl; 
 
     public SkipIterator(Iterator<Integer> it) {
         this.it = it;
         hmap = new HashMap<>();
         advance();
     }
 
     public boolean hasNext() {
         return nextEl != null;
     }
   
     public Integer next() {
         int result = nextEl;
         advance();
         return result;
     }
 
     public void skip(int num) {
         if(num == nextEl)
             advance();
         else
         {
             hmap.put(num, hmap.getOrDefault(num,0)+1);
         }
     }
 
     private void advance() {
         nextEl = null;
             while(nextEl == null && it.hasNext())
             {
                 Integer el = it.next();
                 if(!hmap.containsKey(el))
                     nextEl = el;
                 else
                 {
                     hmap.put(el, hmap.get(el) - 1);
                     hmap.remove(el, 0);
                 }
             }
     }
 }