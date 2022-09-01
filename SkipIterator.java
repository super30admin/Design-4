// "static void main" must be defined in a public class.

    
    class SkipIterator implements Iterator<Integer> {
    HashMap<Integer,Integer> map;
    Iterator<Integer>  iterator;
    Integer nextEL;
	public SkipIterator(Iterator<Integer> it) {
        map = new HashMap<>();
        iterator = it;
        advance();
	}

	public boolean hasNext() {
        return nextEL!=null;
	}

	public Integer next() {
        Integer re = nextEL;
        advance();
        return re;
	}

	/**
	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	*/ 
	public void skip(int val) {
        if(val == nextEL){
            advance();
        }else{
            map.put(val,map.getOrDefault(val,0)+1);
        }
	}
    
    private void advance(){
        nextEL = null;
        while(nextEL==null && iterator.hasNext()){
            Integer curr = iterator.next();
            if(map.containsKey(curr)){
                map.put(curr,map.get(curr)-1);
                if(map.get(curr)==0){
                    map.remove(curr);
                }
            }
            else{
                nextEL = curr;
            }
        }
    }
}
    

public class Main {
    
    
    public static void main(String[] args) {
        SkipIterator itr = new SkipIterator(Arrays.asList(2, 3, 5, 6, 5, 7, 5, -1, 5, 10).iterator());
itr.hasNext(); // true
itr.next(); // returns 2
itr.skip(5);
itr.next(); // returns 3
itr.next(); // returns 6 because 5 should be skipped
itr.next(); // returns 5
itr.skip(5);
itr.skip(5);
itr.next(); // returns 7
itr.next(); // returns -1
itr.next(); // returns 10
itr.hasNext(); // false
itr.next(); // error
    }
}
