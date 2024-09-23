import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

public class Main {
    public static class SkipIterator implements Iterator<Integer> {
        Iterator<Integer> it;
        HashMap<Integer, Integer> map;
        Integer nextEl;
        //Constructor
        public SkipIterator(Iterator<Integer> it){
            this.it =it;
            map = new HashMap<>();
            advance();
        }
        public Integer next(){
            Integer result = nextEl;
            advance();
            return result;

        }
        public boolean hasNext(){
            return nextEl!=null;
        }

        public void skip(int val){
            if(nextEl ==val){
                advance();
            }
            else{
                map.put(val, map.getOrDefault(val,0)+1);
            }
        }
        // advance is to set next element properly
        public void advance(){
            nextEl=null;
            while(nextEl==null && it.hasNext()){
                nextEl=it.next();
                if(!map.containsKey(nextEl)){
                    break;
                }
                map.put(nextEl, map.get(nextEl)-1);
                map.remove(nextEl,0);

            }
        }
    }




    public static void main(String[] args) {
        SkipIterator itr = new SkipIterator(Arrays.asList(1,4,5,2,5,6,7,6,8).iterator());
        System.out.println(itr.next());
        System.out.println(itr.hasNext());
        itr.skip(5);
        itr.skip(5);
        System.out.println(itr.next());
        System.out.println(itr.hasNext());
        itr.skip(6);
        itr.skip(6);
        itr.skip(1);
        itr.skip(7);
        System.out.println(itr.next());
        System.out.println(itr.hasNext());
        System.out.println(itr.next());
        System.out.println(itr.hasNext());
    }
}
