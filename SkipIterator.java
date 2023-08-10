package org.example;

// Time Complexity : O(n)

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

class SkipIterator implements Iterator<Integer> {
    Iterator<Integer> nit;

    HashMap<Integer, Integer> map;
    Integer nextEl;
    public SkipIterator(Iterator<Integer> it) {
        this.nit = it;
        this.map = new HashMap<>();
        setIterator();
    }

  
    @Override
    public boolean hasNext() {  //O(1)
        return nextEl!=null;
    }

    @Override
    public Integer next() { //O(n)
        Integer result = nextEl;
        setIterator();
        return result;
    }

    public void setIterator(){  //O(n)
        nextEl = null;
        while(nit.hasNext())
        {
            Integer el = nit.next();

            if(map.containsKey(el))
            {
                map.put(el,map.get(el)-1);
                map.remove(el,0);
            }else{
                 nextEl = el;
                break;
            }
        }
  }

    public void skip(int num) { //O(n)
        if(num == nextEl) {
            setIterator();
        }else
        {
            if (!map.containsKey(num)) {
                map.put(num, 0);
            }
            map.put(num, map.get(num) + 1);
       //     map.put(num, map.getOrDefault(num,0)+1);
        }
    }
}

class Main {

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