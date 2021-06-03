//Time Complexity: advance()-O(n), hasNext()-O(1), next()-O(n), skip()-O(n)
//Space Complexity: O(n+n)-> O(n)
//DIs it run successfully on leetcode: yes
class SkipIterator implements Iterator<Integer> {
    //map to store the values that need to be skipped
    HashMap<Integer, Integer> map;
    //Iterator
    Iterator<Integer> it;
    //variable to hold next element
    Integer nextEl;
    //constructor
    public SkipIterator(Iterator<Integer> it) {
        this.map = new HashMap<>();
        this.it = it;
        //
        advance();
    }
   //Sets the next element in iterator to nextEl variable
    public void advance(){
        //initialize nextEl to null
        nextEl = null;
        //until iterator has next element and nextEl is not null
        while(nextEl == null && it.hasNext()){
            //get current next element in iterator
            Integer currEl = it.next();
            //if curr next element needs to be skipped
            if(map.containsKey(currEl)){
                //get curr next element from from and decreemnt its value
                map.put(currEl, map.get(currEl)-1);
                map.remove(currEl, 0);
            }//if curr next element not to be skipped
            else{
                // nextEl as current element in iterator
                nextEl = currEl;
                return;
            }
        }
    }

    @Override
    public boolean hasNext() {
        return nextEl != null;
    }

    @Override
    public Integer next() {
        //set the result as the next element
        Integer res = nextEl;
        //move the next point to next element in iterator
        advance();
        //return our result
        return res;
    }

    public void skip(int num) {
        //if we need to skip the next element in iterator
        if(num == nextEl){
            //moves our next pointer to next lement in iterator
            advance();
        }//add num to skip list
        else{
            map.put(num, map.getOrDefault(num, 0)+1);
        }

    }


}