import java.util.HashMap;
import java.util.Iterator;

public class problem2 {
// Time Complexity: O(n)
    // Space Complexity: O(k)
    class SkipIterator implements Iterator<Integer>
    {
        HashMap<Integer, Integer> skipMap;
        Iterator<Integer> nit;
        Integer nextEle;

        public SkipIterator(Iterator<Integer> it)
        {
            this.nit=it;
            this.skipMap= new HashMap<>();
            advance();
        }
        private  void advance(){
            nextEle=null;
            while (nit.hasNext())
            {
                Integer ele= nit.next();
                if (skipMap.containsKey(ele))
                {
                    skipMap.put(ele, skipMap.get(ele)-1);
                    skipMap.remove(ele,0);
                }
                else{
                    nextEle=ele;
                    break;
                }
            }
        }

        @Override
        public boolean hasNext() {
            return nextEle!=null;
        }

        @Override
        public Integer next() {
            Integer temp = nextEle;
            advance();
            return temp;
        }
        public void skip(int val){
            if (val==nextEle)
            {
                advance();
            }
            else {
                skipMap.put(val, skipMap.getOrDefault(val, 0)+1);
            }
        }
    }


}
