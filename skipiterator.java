// "static void main" must be defined in a public class.
public class Main {
    public static class SkipIterator implements Iterator<Integer>{
        Iterator<Integer> it;
        HashMap<Integer,Integer> map;
        Integer nextEl;
        
        public SkipIterator(Iterator<Integer> it){
            this.it = it;
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
            if(nextEl == val){
                advance();
            }
            else{
                map.put(val,map.getOrDefault(val,0)+1);
            }
        }
        private void advance(){
            nextEl = null;
            while(nextEl == null && it.hasNext()){
                nextEl = it.next();
                if(map.containsKey(nextEl)){
                    map.put(nextEl,map.get(nextEl)-1);
                    map.remove(nextEl,0);
                    nextEl = null;
                }
                else{
                    break;
                }
            }
        }
    }
    public static void main(String[] args) {
        SkipIterator itr = new SkipIterator(Arrays.asList(1,1,2,2,3,3,4,5,6).iterator());
        itr.skip(2);
        itr.skip(2);
        itr.skip(1);
        itr.skip(1);
        System.out.println(itr.next());
        System.out.println(itr.hasNext());
        itr.skip(5);
      
        System.out.println(itr.next());
        System.out.println(itr.hasNext());
        System.out.println(itr.next());
        System.out.println(itr.hasNext());
        System.out.println(itr.next());
        System.out.println(itr.hasNext());}
}