import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

// "static void main" must be defined in a public class.
public class Solution2 {
    public static class SkipIterator implements Iterator<Integer> {
        Iterator<Integer> itr;
        Integer nextElement;
        HashMap<Integer,Integer> map;
	public SkipIterator(Iterator<Integer> itr) {
        this.itr=itr;
        map=new HashMap<>();
        advance();
        
	}
// Time Complexity : O(1)
// Space Complexity :O(1) no extra space required
	public boolean hasNext() {
        return nextElement!=null;
	}
// Time Complexity : O(1)
// Space Complexity :O(1)
	public Integer next() {
        Integer temp=nextElement;
        advance();
        return temp;
	}

// Time Complexity : O(1)
// Space Complexity :O(n) for the HashMap
	/**
	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	*/ 
	public void skip(int val) {
        if(val==nextElement)
            advance();
        else
            map.put(val,map.getOrDefault(val,0)+1);
	}

// Time Complexity : O(n) to check throughall elemnts if its either has next value or its is existing in the HashMap
// Space Complexity :O(1) no extra space required
    public void advance(){
         nextElement= null;
        while(nextElement==null && itr.hasNext()){
             Integer curr=itr.next();
            if(map.containsKey(curr)){
                map.put(curr,map.getOrDefault(curr,0)-1);
                map.remove(curr,0);
            }
            else{
                nextElement=curr;
            }
        }
        //nextElement=itr.next();
    }
}
    public static void main(String[] args) {
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
        System.out.println(itr.next()); // error
    }
}
