//Time Complexity :O(1) for posting a tweet, follow, unfollow. O(N) for getNewsFeed
//Space Complexity :O(1)
//Did this code successfully run on Leetcode :yes
//Any problem you faced while coding this :Nope


//Your code here along with comments explaining your approach

class SkipIterator implements Iterator<Integer> {
	List<Integer> list;
	Map<Integer,Integer> skipMapCount;
	int index;
	boolean checked = false;
	Integer nextNumber;
	public SkipIterator(Iterator<Integer> it) {
		index = 0;
		list = new ArrayList<>();
		skipMapCount = new HashMap<>();
		while(it.hasNext()){
			list.add(it.next());
		}
		nextNumber = null;
		checked = false;
	}

	public static void main(String[] args){
		SkipIterator itr = new SkipIterator([2, 3, 5, 6, 5, 7, 5, -1, 5, 10]);
		System.out.println(itr.hasNext());
	}
	
	public boolean hasNext() {
		if(index == list.size()){
			return false;
		}
		while(index < list.size()){
			int num = list.get(index);
			int count = skipMapCount.getOrDefault(num,0);
			if(count == 0){
				index++;
				nextNumber = num;
				checked = true;
				return true;
			}else{
				count--;
				skipMapCount.put(num,count);
				index++;
			}
		}
		
		return false;
	}

	public Integer next() {
		if(checked){
		checked = false;
		}else{
			hasNext();
			checked = false;
		}
		Integer res =  nextNumber;
		nextNumber = null;
		return res;
	}


	/**
	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	*/ 
	public void skip(int val) {
		int count = skipMapCount.getOrDefault(val,0);
		skipMapCount.put(val,count+1);
	}
}