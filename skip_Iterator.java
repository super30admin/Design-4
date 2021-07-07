    /*  Explanation
    # Leetcode problem link : https://leetcode.com/discuss/interview-question/341818/Google-or-Onsite-or-Skip-Iterator
    Time Complexity for operators : o(n^n) .. n is the length of the string
    Extra Space Complexity for operators : o(n) for (List<String> path) without recursive stack
    Did this code successfully run on Leetcode : NA
    Any problem you faced while coding this : No
# Your code here along with comments explaining your approach
        # Basic approach : 
        # Optimized approach: 
                              
            # 1. 
                    A) Used HashMap to keep track of skipped elements.
                    B) Before returning any element, checked if it is part of hashmap or not if not then return and increase 
                       the counter.
                    C) If it part of it then remove or decrease the counter from hashmap of that element and increase the counter to 
                       return the next element from the arary or iterator.
    */  
import java.util.*;
class SkipIterator implements Iterator<Integer> {
    HashMap<Integer,Integer> hm;
    int counter;
    int arr[];
    //for time being I assed array instead of iterator
	public SkipIterator(int arr[]) {
        hm = new HashMap<>();
        counter = 0;
        this.arr = arr;
    }

	public boolean hasNext() {
        return (counter <= arr.length-1);
	}

	public Integer next() {
        if(hm.containsKey(arr[counter])){
            if(hm.get(arr[counter])>1){
                hm.put(arr[counter],hm.get(arr[counter])-1);
            }else{
                hm.remove(arr[counter]);
            }
            counter++;
        }
        int k = arr[counter];
        counter++;
        return k;
	}

	/**
	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	*/ 
	public void skip(int val) {

        if(hm.containsKey(val)){
            hm.put(val,hm.get(val)+1);
        }else
            hm.put(val,1);
        
	}

    public static void main(String args[]){
        int arr[] = new int[]{2, 3, 5, 6, 5, 7, 5, -1, 5, 10};
        SkipIterator itr = new SkipIterator(arr);
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

// o/p came as per expectation
// (base) Laxmikants-MacBook-Pro:Design-4 laxmikantbhaskarpandhare$ java SkipIterator
// true
// 2
// 3
// 6
// 5
// 7
// -1
// 10
// false
// Exception in thread "main" java.lang.ArrayIndexOutOfBoundsException: 10
// 	at SkipIterator.next(skip_Iterator.java:31)
// 	at SkipIterator.main(skip_Iterator.java:72)
// (base) Laxmikants-MacBook-Pro:Design-4 laxmikantbhaskarpandhare$ 

