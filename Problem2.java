// ## Problem 2: Skip Iterator(https://leetcode.com/discuss/interview-question/341818/Google-or-Onsite-or-Skip-Iterator)

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Arrays;

class SkipIterator implements Iterator<Integer> {
    private Iterator<Integer> it;
    // maintain all the future skips with their counts
    private Map<Integer, Integer> skipList;
    // next possible element
    private Integer currEle;

	public SkipIterator(Iterator<Integer> it) {
        this.it=it;
        this.currEle=null;
        this.skipList=new HashMap<>();

        // set the first possible element
        advance();
	}

    // Time: O(N) worst, O(1) ammortized
    // Space: O(1)
    private void advance(){
        this.currEle=null;
        while(it.hasNext()){
            int ele=it.next();
            // check in skip list
            if(skipList.containsKey(ele)){
                // skip this one
                skipList.put(ele, skipList.get(ele)-1);
                // if count 0, remove it
                skipList.remove(ele, 0);
            }else{
                // we need not skip this element
                this.currEle=ele;
                break;
            }
        }
    }

    // Time: O(1) 
    // Space: O(1)
	public boolean hasNext() {
        return currEle!=null;
	}

    // Time: O(N) worst, O(1) ammortized
    // Space: O(1)
	public Integer next() {
        Integer result=this.currEle;

        //advance to the next one
        advance();

        return result;
	}

	/**
	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	*/ 
    
    // Time: O(N) worst, O(1) ammortized
    // Space: O(1)
	public void skip(int val) {
        // Case 1:
        // If the element to be skipped is set in current
        if(this.currEle == val){
            // Just advance and find the next possible element
            advance();
        }else{
        //Case 2: If the element to be skipped is not current
        // add to future skip list
            skipList.put(val, skipList.getOrDefault(val, 0)+1);
        }
	}
}


class Problem2{
    public static void main(String[] args){
        SkipIterator itr = new SkipIterator(Arrays.asList(2, 3, 5, 6, 5, 7, 5, -1, 5, 10).iterator());
        System.out.println(itr.hasNext()); // true
        System.out.println(itr.next()); // returns 2
        itr.skip(5);
        System.out.println(itr.next()); // returns 3
        System.out.println(itr.next()); // returns 6 because 5 should be skipped
        System.out.println(itr.next()); // returns 5
        itr.skip(5);
        itr.skip(5);
        System.out.println(itr.next()); // returns 7
        System.out.println(itr.next()); // returns -1
        System.out.println(itr.next()); // returns 10
        System.out.println(itr.hasNext()); // false
        System.out.println(itr.next()); // null
    }
}
