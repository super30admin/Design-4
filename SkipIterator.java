import java.util.*;
public class SkipIterator implements Iterator<Integer>{
    Map<Integer, Integer> count;
    Integer nextEl;
    Iterator<Integer> it;

    public SkipIterator(Iterator<Integer> it){
        this.it = it;
        this.count  = new HashMap<>();
        advance();
    }

    @Override
    public boolean hasNext() {
        return nextEl != null;
    }

    @Override
    public Integer next() {
        Integer element = nextEl;
        advance();
        return element;
    }

    public void advance(){
        nextEl = null;
        while(nextEl == null && it.hasNext()){
            Integer tmp = it.next();
            if(count.containsKey(tmp)){
                //skip
                count.put(tmp,count.get(tmp)-1);
                count.remove(tmp,0);
            }else{
                nextEl = tmp;
            }
        }
    }

    public void skip(Integer s){
        if(nextEl == s){
            advance();
        }else{
            count.put(s,count.getOrDefault(s,0)+1);
        }
    }


    public static void main(String args[]){
        SkipIterator it = new SkipIterator(Arrays.asList(1, 2, 3).iterator());
        System.out.println(it.hasNext());
        it.skip(2);
        it.skip(1);
        it.skip(3);
        System.out.println(it.hasNext());
    }
}