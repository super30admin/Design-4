// Time Complexity : O(n)
// Space Complexity : O(n)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No
// Your code here along with comments explaining your approach
// We will maintain a hashMap of all the numbers which are to be skipped in the future,with their frequency. We will store the current element in the nextEl variable and move the next pointer to the next element. We will start from nextEl=null. 
// Whenever nextEl is set to any value, we need to check if it is already present in the skipMap. If yes, then we will move forward, otherwise we will set NextEl to the value. If the number which needs to be skipped is the current number, i.e the number 
// nextEl, we will set nextEl to null and move to the next element.

import com.sun.source.tree.Tree;

import javax.swing.tree.TreeNode;
import java.util.*;


class SkipIterator {

    private HashMap<Integer, Integer> SkipMap = new HashMap<>();
    private Integer nextEl;
    private Iterator<Integer> nit;

    public SkipIterator(Iterator<Integer> it) {
        this.SkipMap = new HashMap<>();
        this.nit = it;
        advance();
    }

    private void advance() {
        nextEl = null;
        while (nit.hasNext() && nextEl == null) {
            Integer el = nit.next();
            if (SkipMap.containsKey(el)) //If the Skip map contains the key
            {
                //Decrement the value and if it is zero, remove it
                SkipMap.put(el, SkipMap.get(el) - 1);
                SkipMap.remove(el, 0);
            }
            //if it is not present in the HashMap, then we will set it to nextEl
            else {
                nextEl = el;
            }
        }
    }

    //@Override
    public boolean hasNext() {
        return nextEl != null;
    }

   // @Override
    public Integer next() {
        Integer result = nextEl;
        advance(); // We will get the next Element here
        return result;
    }

  //  @Override
    public void skip(int num) {
        //if num is equal to nextEl, then we will call advance function
        if (num == nextEl) {
            advance();
        }
        //otherwise if it a future element, we will add it to the SkipMap
        {
            SkipMap.put(num, SkipMap.getOrDefault(num, 0) + 1);
        }

    }

}
public class Main {

    public static void main(String[] args) {

        SkipIterator it = new SkipIterator(Arrays.asList(5,6,7,5,6,8,9,5,5,6,8).iterator());

        System.out.println(it.hasNext());// true
        it.skip(5);//
        System.out.println(it.next()); //6
        it.skip(5);
        System.out.println(it.next());// 7
        System.out.println(it.next()); // 6
        it.skip(8); //
        it.skip(9); //

        System.out.println(it.next()); // 5

        System.out.println(it.next()); //5
        System.out.println(it.next());//6
        System.out.println(it.hasNext());// true
        System.out.println(it.next());// 8
        // it.skip(1);

//          it.skip(3);

        System.out.println(it.hasNext()); //false

    }

}