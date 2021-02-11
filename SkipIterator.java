// Time Complexity : O(n)
// Space Complexity : O(k)
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : no

class SkipIterator implements Iterator<Integer> {

    HashMap<Integer, Integer> hash;
    Iterator<Integer> it;

    Integer nextEl;
    public SkipIterator(Iterator<Integer> it) {
        hash = new HashMap<>();
        this.it = it;
        //move to next element iterator
        advanced();
    }

    public boolean hasNext() {
        return nextEl != null;
    }

    public Integer next() {
        Integer res = nextEl;

        advanced();
        return res;
    }

    /**
     * The input parameter is an int, indicating that the next element equals 'val'  needs to be skipped.
     * This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
     */
    public void skip(int val) {

        //icall advance if same element again
        if(val == nextEl){
            advanced();
        }
        //store element
        else{
            hash.put(val, hash.getOrDefault(val, 0) + 1);
        }

    }

    private void advanced(){
        nextEl = null;
        //while next is null and there is available element
        while(nextEl == null && it.hasNext()){

            Integer el = it.next();
            //check if not in map
            if(!hash.containsKey(el)){
                nextEl = el;
            }
            else{
                hash.put(el, hash.get(el)-1);
                hash.remove(el,0);
            }
        }

    }



}