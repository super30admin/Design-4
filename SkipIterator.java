/*package whatever //do not write package name here */

import java.io.*;
import java.util.*;

// Time Complexity :O(n)
// Space Complexity :O(1)
// Did this code successfully run on Leetcode : Yes
// Any problem you faced while coding this : No

// Java Iterator interface reference:
// https://docs.oracle.com/javase/8/docs/api/java/util/Iterator.html

class SkipIterator implements Iterator<Integer> {
    Iterator<Integer> itr;
    Integer nextVal;

    public SkipIterator(Iterator<Integer> it) {
        // initialize any member here.
        this.itr = it;
        if(itr.hasNext()) {
            nextVal = itr.next();
        }
    }

    // hasNext() and next() should behave the same as in the Iterator interface.
    // Override them if needed.
    @Override
    public boolean hasNext() {
        return nextVal != null;
    }

    @Override
    public Integer next() {
        Integer value = nextVal;
        if(itr.hasNext()) {
            nextVal = itr.next();
        }
        else {
            nextVal = null;
        }

        return value;
    }

    /**
     * The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
     * This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
     */
    public void skip(int val) {
        if (itr.hasNext()) {
            if (nextVal.equals(val)) {
                nextVal = itr.next();
            }
        }
        else {
            nextVal = null;
        }
    }

    public static void main (String[] args) {
        List<Integer> cities = new LinkedList<>();
        cities.add(1);
        cities.add(1);
        cities.add(2);
        cities.add(3);
        Iterator<Integer> citiesIterator = cities.iterator();
        SkipIterator ob = new SkipIterator(citiesIterator);

        System.out.println(ob.hasNext());
        System.out.println(ob.next());
        System.out.println(ob.next());
        System.out.println(ob.next());
        ob.skip(3);
        System.out.println(ob.hasNext());
        System.out.println(ob.next());
        System.out.println(ob.hasNext());
    }
}