// "static void main" must be defined in a public class.
/*
 * Time Complexity : O(n)
 * Space Complexity : O(n)
 */
class SkipIterator implements Iterator<Integer> {

    Map<Integer, Integer> skipmap = new HashMap<Integer, Integer>();
    int nextel;
    Iterator<Integer> iterator;
	public SkipIterator(Iterator<Integer> it) {
        iterator = it;
        advance();
	}

	public boolean hasNext() {
        return nextel == null;
	}

	public Integer next() {
        int temp = nextel;
        advance();
        return temp;
	}

	/**
	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	*/ 
	public void skip(int val) {
        
        if(nextel == val){
            advance();
        }else{
            if(!skipMap.containsKey(val)){
                skipMap.put(val, 1);
            }else{
                skipMap.put(val, skipMap.get(val)+1);
            }
        }
	}
    
    private void advance(){
        nextel = null;
        
        while(nextel == null && iterator.hasNext()){
            Iterator el = iterator.next();
            
            if(!skipMap.containsKey(el)){
                nextel = el;
            }else{
                skipMap.put(el, skipMap.get(el)-1);
                skipMap.remove(el, 0);
            }
        }
    }
}