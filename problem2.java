import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

// "static void main" must be defined in a public class.
class Main {
    public static class SkiptIterator implements Iterator<Integer>{
        Iterator<Integer> it;
        Integer nextEl;
        HashMap<Integer, Integer> map;
        
        public SkiptIterator(Iterator<Integer> it){
            this.it = it;
            map = new HashMap<>();
            advance();
        }
        
        public Integer next(){
            int answer = nextEl;
            advance();
            return answer;
        }
        
        public boolean hasNext(){
            return nextEl != null;
        }
        
        public void skip(int num){
            if(nextEl == num){
                advance();
            }
            else{
                map.put(num, map.getOrDefault(num, 0) + 1);
            }
        }
        
        private void advance(){
            nextEl = null;
            while(nextEl == null && it.hasNext()){
                int curr = it.next();
                if(map.containsKey(curr)){
                    map.put(curr, map.get(curr) - 1);
                    map.remove(curr, 0);
                }
                else{
                    nextEl = curr;
                }
            }
        }
        
    }
    public static void main(String[] args) {
        SkiptIterator it = new SkiptIterator(Arrays.asList(5,6,7,7,8,9,5,6).iterator());
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
        System.out.println(it.next());
        System.out.println(it.hasNext());
        System.out.println(it.next());
        System.out.println(it.hasNext());
    }
}

//time complexity O(1) all operations are doing it in constant
//space complexity O(n) as we use hasmap to store for next time skip