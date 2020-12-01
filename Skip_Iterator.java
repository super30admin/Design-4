//Time Complexity : O(n)
//Space Complexity : O(n)
//Did this code successfully run on Leetcode : Yes
//Any problem you faced while coding this : None

package com.s30.satish;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

class Skip_Iterator implements Iterator<Integer> {
    HashMap<Integer, Integer> map;
    Integer nextElement;
    Iterator<Integer> it;
	public Skip_Iterator(Iterator<Integer> it) {
        map = new HashMap<>();
        this.it = it;
        moveForward();
	}

	public boolean hasNext() {
        return nextElement != null;
	}

	public Integer next() {
        Integer el = nextElement;
        moveForward();
        return el;
	}

	/**
	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	*/ 
	public void skip(Integer val) {
        if(nextElement == val)
        {
            moveForward();
        }
        else
            map.put(val, map.getOrDefault(val, 0) + 1);
	}
    
    private void moveForward()
    {
        nextElement = null;
        while(nextElement == null && it.hasNext())
        {
            Integer element = it.next();
            if(map.containsKey(element))
            {
                map.put(element, map.getOrDefault(element, 0) - 1);
                map.remove(element, 0);
            }
            else
                nextElement = element;
        }
    }

    public static void main(String[] args) {
        Skip_Iterator itr = new Skip_Iterator(Arrays.asList(2, 3, 5, 6, 5, 7, 5, -1, 5, 10).iterator());
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