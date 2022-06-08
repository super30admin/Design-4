package com.interview.sthirty;

import java.util.*;

class SkipIterator implements Iterator<Integer>
{
    Iterator<Integer> iterator;
    Map<Integer, Integer> map;

    Integer nextElement;

    public SkipIterator(Iterator<Integer> it) {
        this.iterator = it;
        this.map = new HashMap<>();
        advance();
    }

    public boolean hasNext() {
        return nextElement != null;
    }

    public Integer next()
    {
        if(hasNext())
        {
            int result = nextElement;
            advance();
            return result;
        }

        return null;
    }

    /**
     * The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
     * This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
     */
    public void skip(int val)
    {
        if(  nextElement == val)
        {
            advance();
        }
        else
        {
            map.put(val, map.getOrDefault(val, 0) + 1);
        }
    }

    /**
     * This method sets the nextElement
     */
    private void advance()
    {
        nextElement = null;

        while(nextElement == null && iterator.hasNext())
        {
            int nativeNext = iterator.next();

            if(map.containsKey(nativeNext))
            {
                int prevCount = map.get(nativeNext);
                if(prevCount == 1)
                {
                    map.remove(nativeNext);
                }
                else
                {
                    prevCount--;
                    map.put(nativeNext, prevCount);
                }
            }
            else
            {
                nextElement = nativeNext;
            }
        }
    }

    public static void main(String[] args)
    {
        List<Integer> input = Arrays.asList(new Integer[]{2, 3, 5, 6, 5, 7, 5, -1, 5, 10});
        SkipIterator itr = new SkipIterator(input.iterator());

        System.out.println(itr.hasNext() );// true
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
