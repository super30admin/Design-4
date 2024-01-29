//TC O(1) //for all operations
//SC O(n) //count Map size
//Executed in leet code : yes
/*
We maintain a HashMap containing array nums and their counts. Everytime skip is called, we make sure the map is updated
and the variable nextEl is updated. For hasNext() and next() we just check nextEl and return value to user
 */

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SkipIterator implements Iterator<Integer> {
    private final Iterator<Integer> it;
    private final Map<Integer, Integer> count;
    private Integer nextEl;

    public SkipIterator(Iterator<Integer> it){
        this.it = it;
        this.count = new HashMap<>();
        advance();
    }

    private void advance(){
        nextEl = null;
        while(nextEl == null && it.hasNext()){
            Integer el = it.next();
            if(!count.containsKey(el)){
                nextEl = el;
            } else{
                count.put(el,count.get(el)-1);
                count.remove(el,0);
            }
        }
    }
    @Override
    public boolean hasNext() {
        return nextEl != null;
    }

    @Override
    public Integer next() {
        if(!hasNext()) throw new RuntimeException("empty");
        Integer el = nextEl;
        advance();
        return el;
    }

    public void skip(int num){
        if(!hasNext()) throw new RuntimeException("empty");
        if(nextEl == num){
            advance();
        } else {
            count.put(num, count.getOrDefault(num,0) + 1);
        }
    }

    public static void main(String[] args) {
        SkipIterator it = new SkipIterator(Arrays.asList(1, 2, 3).iterator());
        System.out.println(it.hasNext());
        it.skip(2);
        it.skip(1);
        it.skip(3);
        System.out.println(it.hasNext());
    }
}
