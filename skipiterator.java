class SkipIterator implements Iterator<Integer> {
    HashMap<Integer,Integer> skipmap;
    Integer nextEl;
    Iterator<Integer> it;
	public SkipIterator(Iterator<Integer> it) {
        this.it=it;
        skipmap = new HashMap<>();
        advance();       
	}
    
    private void advance(){
        nextEl=null;
        while(nextEl==null && it.hasNext()){
            Integer el=it.next();
            if(skipmap.containsKey(el)){
                skipmap.put(el,skipmap.get(el)-1);
                skipmap.remove(el,0);
            }
            else{
                nextEl=el;
            }
        }
    }
    
    @Override
	public boolean hasNext() {
        return nextEl!=null;
	}
    
    @Override
	public Integer next() {
        int res=nextEl;
        advance();
        return res;
	}

	/**
	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	*/ 
	public void skip(int val) {
        if(nextEl!=val){
            skipmap.put(val,skipmap.getOrDefault(val,0)+1);
        }
        else{
            advance();
        }
        
	}
}
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        SkipIterator itr = new SkipIterator(Arrays.asList(2, 3, 5, 6, 5, 7, 5, -1, 5, 10).iterator());
        System.out.println(itr.hasNext()); // true
        System.out.println(itr.next()); // returns 2
        itr.skip(5);
        System.out.println(itr.next()); // returns 3
        System.out.println(itr.next()); // returns 6 because 5 should be skipped
        System.out.println(itr.next()); // returns 5
        itr.skip(5);
        itr.skip(5);
        System.out.println(itr.next()); // returns 7
        System.out.println(itr.next()); // returns -1
        System.out.println(itr.next()); // returns 10
        System.out.println(itr.hasNext()); // false
        System.out.println(itr.next()); // error
    }
}
