class skipIterator implements Iterator<Integer> {
    
    HashMap<Integer, Integer> map;
    Iterator<Integer> it;
    Integer nextEl; //pointer of my skip Iterator
    
    public SkipIterator(Iterator<Integer> it){
        map = new HashMap<>();
        this.it = it;
        // nextEl = it.next(); //2
        advance();
    }
    
    public boolean hasNext(){
        
        return it.hasNext();
    }
    public Integer next(){
        Integer temp = nextEl;
        nextEl = it.next();
        return temp;
    }
    
    public void skip(Integer val){
        if(val == nextEl){  //if the value to which next is pointing, we have to skip that only then we dont need to store it in the skip hashmap which we created
            advance(); //going to get us the next valid position. if there are two skips, it will give us the element after tthe two skips.
        }
        else{
            map.put(val, map.getOrDefault(val, 0) + 1);
        }
    }
    
    //used for putting nextEl pointer of skip iterator at the next valid element in my native iterator
    private void advance(){
        
        nextEl = null;
        while(nextEl == null && it.hasNext()){
            
            Integer el = it.next();
            if(map.containsKey(el)){
                map.put(el, map.getOrDefault(el, 0) - 1);
                map.remove(el, 0);
            }
            else{
                nextEl = el;
            }
             
        }
    }
}