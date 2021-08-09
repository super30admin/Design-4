
// "static void main" must be defined in a public class.
//SC: O(N)
//TC: O(N)
//where N is the number of elements in the list 

class SkipIterator implements Iterator<Integer>{
    
    Map<Integer,Integer> skipMap;
    Iterator<Integer> it;
    Integer nextEl;
    
    public SkipIterator(Iterator<Integer> it){
        this.it = it;
        skipMap = new HashMap<>();
        advance();
    } 
    public boolean hasNext(){
        return nextEl!=null;
    }
    public Integer next(){
        Integer temp = nextEl;
        advance();
        return temp;
    } 
    
    public void skip(int val){
        if(val == nextEl)
            advance();
        else
            skipMap.put(val,skipMap.getOrDefault(val,0)+1);
    }
    private void advance(){
        nextEl = null;
        while(nextEl == null && it.hasNext()){
            Integer el = it.next();
            if(!skipMap.containsKey(el))
                nextEl = el;
            else{
                skipMap.put(el,skipMap.get(el)-1);
                if(skipMap.get(el) == 0)
                    skipMap.remove(el);
            }
        }
    }
    
    
    
    
    
     public static void main(String[] args) {
        System.out.println("Hello World!");
        List<Integer> list = Arrays.asList(2,3,5,6,5,7,5,-1,5,10);
        Iterator it = list.iterator();
        SkipIterator itr = new SkipIterator(it);

        System.out.println(itr.hasNext());
        System.out.println(itr.next());
        itr.skip(5);
        System.out.println(itr.next());
        System.out.println(itr.next());
        System.out.println(itr.next());
        itr.skip(5);
        itr.skip(5);
        System.out.println(itr.next());
        System.out.println(itr.next());
        System.out.println(itr.next());
        System.out.println(itr.hasNext());
    }

}

   