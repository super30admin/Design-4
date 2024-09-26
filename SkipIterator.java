import java.util.*;


public class SkipIterator implements Iterator<Integer> {

	private final Iterator<Integer> iterator;
	private final Map<Integer, Integer> skipCount;
	
	private Integer cursor;
	
	
	public SkipIterator(Iterator<Integer> iterator) {
		
		this.iterator = iterator;
		this.skipCount = new HashMap<>();
		seekCursor();
		
	}
	@Override
	public boolean hasNext() {
	
		 
		return cursor !=null;
	}

	@Override
	public Integer next() {
		Integer element = cursor;
		seekCursor();
		return element;
	}
	
	public void skip(int val) {
		if(cursor == val) {
			seekCursor();
		}else {
			skipCount.put(val, skipCount.getOrDefault(val,0)+1);
		}
		
	}
	
	private void seekCursor() {
		
		cursor = null;
		while(cursor == null && iterator.hasNext()) {
			Integer element = iterator.next();
			if(!skipCount.containsKey(element)) {
				cursor = element;
				
			}else {
				 skipCount.put(element, skipCount.get(element)-1);
				 skipCount.remove(element,0);
		}
		}
		
	}
	
	public static void main(String[] args) {
		List<Integer> list = Arrays.asList(2,3,5,6,7,9,3,5,6,8);
		Iterator iterator = list.iterator();
		SkipIterator skipIterator = new SkipIterator(iterator);
		


	}


}
