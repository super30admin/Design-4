//Time Complexity: next(), skip() : Amortized O(1) / hasNext(): O(1)
//space Complexity: O(k) where k is the number of skipped elements tracked in skipMap

class SkipIterator implements Iterator<Integer> {
    Iterator<Integer> papaIt;
    HashMap<Integer, Integer> skipMap;
    Integer nextEl;
    public SkipIterator(Iterator<Integer> it){
       this.papaIt = it;
       this.skipMap = new HashMap<>();
        advance();
    }
  
    private void advance(){ 
       this.nextEl = null;
        while(nextEl == null && papaIt.hasNext()){
            Integer currEl = papaIt.next();
            if(!skipMap.containsKey(currEl)){
                this.nextEl = currEl;
            } else {
                skipMap.put(currEl, skipMap.get(currEl) - 1);
                skipMap.remove(currEl, 0);
            }
        }
    }  
     public void skip(int num) { 
         if(num == nextEl){
             advance ();
         } else {
             skipMap.put(num, skipMap.getOrDefault(num, 0) + 1);
         }
     }
   @Override
     public boolean hasNext() { 
        return nextEl != null;
     }

   @Override
     public Integer next() { 
        Integer res = nextEl;
         advance();
         return res;
     }

}