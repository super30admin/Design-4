// TC: O(n)
// SC: O(n)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No

//https://leetcode.com/discuss/interview-question/341818/Google-or-Onsite-or-Skip-Iterator

import java.util.*;

class SkipIterator implements Iterator<Integer> {
    
    private final Iterator<Integer> it;
    private final Map<Integer, Integer> count;
    private Integer nextEle;

	public SkipIterator(Iterator<Integer> it) {
        this.it = it;
        count = new HashMap<>();
	}
    
    @Override
	public boolean hasNext() {
        if (nextEle != null && skipEleCheckAndRemove(nextEle)) {
            nextEle = null;
        }
        
        while (it.hasNext() && nextEle == null) {
            int temp = it.next();
            if (skipEleCheckAndRemove(temp)) {// this is skip ele
                nextEle = temp;
            }
        }   
        return nextEle != null;
	}
    
    @Override
	public Integer next() {
        hasNext();
        Integer temp = nextEle;
        nextEle = null;
        return temp;
	}

	/**
	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	*/ 
    
	public void skip(int val) {
        count.put(val, count.getOrDefault(val, 0) + 1);
	}
    
    private boolean skipEleCheckAndRemove(int ele) {
        if (count.containsKey(ele)) {
            int freq = count.get(ele);
            if (freq == 1) {
                count.remove(ele);
            }
            else {
                count.put(ele, freq-1);
            }
            return true;
        }
        return false;
    }
}

// public class Main {
//     public static void main(String[] args) {
//         SkipIterator it = new SkipIterator(Arrays.asList(1, 2, 3, 4, 1, 5).iterator());
        
//         System.out.println(it.hasNext());
        
//         it.skip(1);
//         System.out.println(it.hasNext());
//         it.skip(1);
//         System.out.println(it.hasNext());
//         System.out.println(it.next());
//         System.out.println(it.hasNext());
//     }
// }