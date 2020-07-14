
// https://leetcode.com/discuss/interview-question/341818

class SkipIterator implements Iterator<Integer> {
    
     HashMap<Integer,Integer> countMap;
     Iterator<Integer>it;
    private Integer nextEl;
    
    public SkipIterator(Iterator<Integer> it) {
       this.countMap = new HashMap();
       this.it=it;
    }

    @Override
    public boolean hasNext() {
       return nextEl!=null;
    }

    @Override
    public Integer next() {
        Integer el = nextEl;
        advance();
        return el;
    }

   public void skip(int num){
       if(nextEl==num){ //if both skip number and current pointer is same
           advance();
       }else{
           countMap.put(num,countMap.getOrDefault(num,0)+1);
       }
   }
    
    public void advance(){
        nextEl = null;
        while(nextEl==null && it.hasNext()){
            Integer el = it.next();
            
            if(!countMap.containsKey(el)){
                
                nextEl=el;
            }else{
                countMap.put(el,countMap.get(el)-1);
                countMap.remove(el,0);
            }
            
            
        }
    }

    
}

public class Main {
        public static void main(String[] args) {
        SkipIterator it = new SkipIterator(Arrays.asList(1, 2, 3).iterator());
        System.out.println(it.hasNext());
        it.skip(2);
        it.skip(1);
        it.skip(3);
        System.out.println(it.hasNext());
    }
}
