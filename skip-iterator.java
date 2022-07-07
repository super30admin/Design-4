# Time Complexity: O(1) average worst O(n)
# Space Complexity: O(1)
class SkipIterator implements Iterator<Integer> {
    private HashMap<Integer,Integer> map;
    private Integer nextEl;
    private Iterator<Integer> it;
    

    public SkipIterator(Iterator<Integer> it) {
        this.map=new HashMap<>();
        this.it=it;
        advance();
    }

    @Override
    public boolean hasNext() {
        return nextEl!=null;
    }

    @Override
    public Integer next() {
        Integer result=nextEl;
        advance();
        return result;
    }

    public void skip(int num) {
        if(nextEl!=num){
            map.put(num,map.getOrDefault(num,0)+1);
        }
        else{
            advance();
        }
    }

    private void advance() {
     nextEl=null;
        while(nextEl==null && it.hasNext()){
            Integer e1=it.next();
                if(map.containsKey(e1)){
                    map.put(e1,map.get(e1)-1);
                    map.remove(e1,0);
                }
                else{
                    nextEl=e1;
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