// Time Complexity : O(1)
// Space Complexity : O(n)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this :

class SkipIterator implements Iterator<Integer> {
    
    private Iterator<Integer> it;
    Integer nextElem;
    HashMap<Integer, Integer> map;
    
    public SkipIterator(Iterator<Integer> it){
        this.it = it;
        this.map = new HashMap<>();
        advance(); // put the initial ptr @ right loc
    }
    
    @Override
    
        public boolean hasNext() {
            return nextElem != null;
        }
    
    @Override
    
        public Integer next(){
            Integer temp = nextElem;
            advance();
            return temp;
            
        }
    
    public void skip(int num) {
        if(num != nextElem){
            // put in skip map
            map.put(num, map.getOrDefault(num) + 1 );
        } else{
            advance(); // put at next best pos
        }
    }
    
    private voic advance(){
        nextElem = null;
        while(nextElem == null && it.hasNext()){
            Integer curr = it.next();
            if(map.containsKey(curr)){
                map.put(curr, map.getOrDefault(curr, 0) -1 );
                map.remove(curr,0);
            } else{
                nextElem = curr;
            }
        }
    }
}