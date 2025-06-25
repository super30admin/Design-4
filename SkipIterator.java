// Time Complexity : hasNext() - O(1), skip and next in worst case- O(n)
// Space Complexity : O(1)
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : no
// Approach : Use a basic iterator and a skipMap to store elements we wanna skip. If the current nextEle is what we want to skip
// move one step ahead while decrementing the skipCount of that element. 
import java.util.*;
class Main
{
	public static void main(String[] args) {
		System.out.println("Hello World");
		ArrayList<Integer> li = new ArrayList<>();
		//[2, 3, 5, 6, 5, 7, 5, -1, 5, 10]
		li.add(2);
		li.add(3);
		li.add(5);
		li.add(6);
		li.add(5);
		li.add(7);
		li.add(5);
		li.add(-1);
		li.add(5);
		li.add(10);
		SkipIterator itr = new SkipIterator(li);
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
class SkipIterator{
    Integer nextEle;
    Iterator nit;
    HashMap<Integer, Integer> skipMap;
    SkipIterator(ArrayList<Integer> list){
        skipMap = new HashMap<>();
        nit = list.iterator();
        nextEle = advance();
        
    }
    void skip(int ele){
        if(nextEle == ele){
            advance();
        }
        else{
            skipMap.put(ele, skipMap.getOrDefault(ele, 0) + 1);
        }
    }
    boolean hasNext(){
        return nextEle!=null;
    }
    int next(){
        int ele = nextEle;
        advance();
        return ele;
    }
    Integer advance(){
        nextEle = null;
        while(nextEle==null && nit.hasNext()){
            Integer ele = (Integer)nit.next();
            if(skipMap.containsKey(ele)){
                if(skipMap.get(ele) == 1){
                    skipMap.remove(ele);
                }
                else{
                    skipMap.put(ele, skipMap.get(ele)-1);
                }
                
            }
            else{
                nextEle = ele;
            }
        }
        return nextEle;
    }
}