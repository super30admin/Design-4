import java.util.HashMap;
import java.util.Iterator;

class SkipIterator implements Iterator<Integer>{
    HashMap<Integer,Integer> map;
    Iterator<Integer> it;
    Integer nextEl;

    public SkipIterator(Iterator<Integer> it){
        map = new HashMap<>();
        this.it = it;
        advance();
    }

    public boolean hasNext(){
        return nextEl != null;

    }

    public Integer next(){

        Integer temp = nextEl;
        advance();
        return temp;

    }

    public void skip(Integer val){
        if (nextEl == val){
            advance();
        }else {
            map.put(val, map.getOrDefault(val, 0) + 1);
        }

    }

    public void advance(){
        nextEl = null;
        while(nextEl == null && it.hasNext()){
            Integer el = it.next();
            if (!map.containsKey(el)){
                nextEl = el;
            } else{
                map.put(el, map.getOrDefault(el, 0) - 1);
                map.remove(el,0);
            }
        }

    }

}