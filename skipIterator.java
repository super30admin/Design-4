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