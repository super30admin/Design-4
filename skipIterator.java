import java.util.Iterator;

// Time Complexity : O(1) 
// Space Complexity : O(D) ==> one List size at a time
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : yes


// Your code here along with comments explaining your approach

public class skipIterator implements Iterator<Integer>{
    private Integer nextEl;
    

private void advance(){
    nextEl = null;

    while(nextEl == null && it.hasNext()){
        int el = it.next();
        if(!map.containsKey(el))
            nextEl = el;
        else{
            map.put(el, map.get(el)-1);
            if((map.get(el) - 1) == 0)
                map.remove(el);
        }
    }
}

// Time Complexity: O(1)
private boolean hasNext(){
    return nextEl != null;
}

// Time Complexity: O(n)    (where n -> maximum of number of elements to skip)
private Integer next(){
    int el = nextEl;
    advance();
    return el;
}

// Time Complexity: O(n)
private void skip(int val){
    // Update my next element
    if(nextEl == val)
        advance();
    else
        map.put(val, map.getOrDefault(val, 0) + 1);
} 
}
