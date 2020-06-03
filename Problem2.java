import java.util.HashMap;
import java.util.Iterator;

class SkipIterator implements Iterator<Integer> {

    Iterator<Integer> itr;
    Integer nextElement;
    HashMap<Integer, Integer> map;
	public SkipIterator(Iterator<Integer> it) {
        map = new HashMap<>();
        this.itr = it;
        advance();
	}

	public boolean hasNext() {
        return nextElement!= null;
	}

	public Integer next() {
        int temp = nextElement;
        advance();
        return temp;
	}

	/**
	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	*/ 
	public void skip(int val) {
        if(nextElement == val)
        {
            advance();
        }
        else {
            map.put(val,map.getOrDefault(val, 0)+1);
        }
    }
    
    private void advance() {
        nextElement = null;

        while(nextElement == null && itr.hasNext()) {
            int element = itr.next();
            if(map.containsKey(element)) {
                map.put(element, map.get(element)-1);
                map.remove(element, 0);
            }
            else {
                nextElement = element;
            }
        }
    }
}