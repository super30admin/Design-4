//https://leetcode.com/discuss/interview-question/341818/Google-or-Onsite-or-Skip-Iterator

import java.util.List;
import java.util.Map;

class SkipIterator implements Iterator<Integer> {

    Map<Integer, Integer> skipMap;
    Iterator<Integer> it;
    Integer nextEl;

	public SkipIterator(Iterator<Integer> it) {
        this.it = it;
        skipMap = new HashMap;
        advance();
	}

	public boolean hasNext() {
        return nextEl != null;
	}

	public Integer next() {
        Integer temp = nextEl;
        advance();
        return temp;
	}

	/**
	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	*/ 
	public void skip(int val) {
        if(val == nextEl)
            advance();
        else
            skipMap.put(val, skipMap.getOrDefault(val, 0)+1);
	}

    private void advance(){
        nextEl = null;
        while(mextEL == NULL && it.hasNext()){
            Integer el = it.next();
            if(!skipMap.containsKey(el))
                nextEl = el;
            else
                skipMap.put(el, skipMap.get(el)-1);
                if(skipMap.get(el) == 0)
                    skipMap.remove(el);
        }
    }
}

public static void main(String[] args) {
    List<Integer> list = Arrays.asList(2, 3, 5, 6, 5, 7, 5, -1, 5, 10);
    Iterator it = list.iterator();
    SkipIterator ite = new SkipIterator(it);
    System.out.println(itr.hasNext()); // true
    System.err.println(itr.next()); // return 2
    itr.skip(5);
    System.out.println(itr.next()); // return 3;
    System.out.println(itr.next()); // returns 6 because 5 should be skipped
    System.err.println(itr.next()); // return 5
    itr.skip(5);
}