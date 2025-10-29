// Time Complexity : advance: O(n)
// Space Complexity : O(1)
// Did this code successfully run on Leetcode : -
// Any problem you faced while coding this : No


// Your code here along with comments explaining your approach
//maintain skip map to store number of times the element needs to be skipped

public class SkipIterator {
    Iterator<Integer> nit;
    Integer nextEl;
    HashMap<Integer,Integer> skipMap;

    public SkipIterator(Iterator<Integer> it){
        this.nit = it;
        skipMap = new HashMap();
        advance();
    }
    public boolean hasNext() {
        return nextEl != null; 
    }

    public Integer next(){
        int re = nextEl;
        advance();
        return re;
    }

    public void skip(int num){
        if(num == nextEl)
            advance();
        else
            skipMap.put(num, skipMap.getOrDefault(num,0)+1);
    }

    public void advance(){ //assigns nextEl
        nextEl = null;
        while(nextEl == null && nit.hasNext()){ //till we have valid element
            int el = nit.next();
            if(skipMap.containsKey(el)){      
                skipMap.put(el,skipMap.get(el)-1);
                skipMap.remove(el,0);
            } else
                nextEl =el;
        }
    }
}