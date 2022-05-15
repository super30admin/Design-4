import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

public class Main {
    public static class SkipIterator implements Iterator<Integer>{
        Iterator <Integer> it;
        Integer nextElement;
        HashMap<Integer, Integer> map;

        public SkipIterator(Iterator<Integer> it){
            this.it = it;
            map = new HashMap<>();
            advance();
        }

        public void advance(){
            nextElement = null;
            while (nextElement == null && it.hasNext()){
                int current = it.next();
                if (map.containsKey(current)){
                    map.put(current, map.get(current) - 1);
                    map.remove(current, 0);
                }
                else{
                    nextElement = current;
                }
            }
        }

        public Integer next(){
            int ans = nextElement;
            advance();
            return nextElement;
        }
        public boolean hasNext(){
            return nextElement != null;
        }

        public void skip(int num){
            if(nextElement == num){
                advance();
            }
            else{
                map.put(num, map.getOrDefault(num, 0) + 1);
            }
        }

    }
    public static void main(String[] args){
        SkipIterator it = new SkipIterator(Arrays.asList(5, 6, 7, 7, 8, 9, 5, 6).iterator());
        System.out.println(it.next());
        System.out.println(it.hasNext());
        it.skip(7);
        System.out.println(it.next());
        System.out.println(it.hasNext());
        System.out.println(it.next());
        System.out.println(it.hasNext());
        it.skip(5);
        System.out.println(it.next());
        System.out.println(it.hasNext());
    }
}
