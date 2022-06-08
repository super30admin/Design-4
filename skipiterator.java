import java.util.*;

class SkipIterator implements Iterator<Integer> {
    
    // Create a global iterator it
    private Iterator<Integer> it;
    private Integer nextValue;
    private HashMap<Integer, Integer> skipmap;

	public SkipIterator(Iterator<Integer> it) {
        this.it = it;
        this.skipmap = new HashMap<>();
        advance();
	}

	public boolean hasNext() {
        return nextValue!=null;
	}

	public Integer next() {
        int result = nextValue;
        advance();
        return result;
	}

    public void advance(){
        this.nextValue = null;
        while(nextValue == null && it.hasNext()){
            Integer temp = it.next();
            if(skipmap.containsKey(temp)){
                skipmap.put(temp, skipmap.get(temp)-1);
                if(skipmap.get(temp) == 0){
                    skipmap.remove(temp);
                }
                nextValue = null;
            } else{
                nextValue = temp;
            }
        }
    }

	/**
	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	*/ 
	public void skip(int val) {
        if(nextValue == val){
            advance();
        } else{
            if(!skipmap.containsKey(val)){
                skipmap.put(val,0);
            }
            skipmap.put(val,skipmap.get(val)+1);
        }
	}

    public static void main(String[] args){
        SkipIterator itr = new SkipIterator(Arrays.asList(5, 6, 7, 5, 6, 8, 9, 5, 5, 6,8).iterator());
        System.out.println(itr.hasNext()); // true
        itr.skip(6);
        itr.skip(6);
        itr.skip(7);
        itr.skip(5);
        System.out.println(itr.next()); // returns 5
        itr.skip(5);
        System.out.println(itr.next()); // returns 8
        System.out.println(itr.next()); // returns 9
        itr.skip(5);
        itr.skip(8);
        System.out.println(itr.next()); // returns 6
    }
}
