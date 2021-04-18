// Time Complexity : O(n)
// Space Complexity : O(n) 
// Did this code successfully run on Leetcode : YES
// Any problem you faced while coding this : NO


// Your code here along with comments explaining your approach
class SkipIterator implements Iterator<Integer> {

    HashMap<Integer,Integer> map;
    Iterator<Integer> iterator;
    Integer nextInt;
	
	public SkipIterator(Iterator<Integer> iterator) {
		map = new HashMap<>();
		this.iterator = iterator;
		handleNext();
	}

	public boolean hasNext() {

        	return nextInt != null;
	}

	public Integer next() {

		Integer result = nextInt;
		handleNext();
		return result;
	}


	public void skip(int val) {
		if(val == nextInt)
		{
		    handleNext();
		}else{
		   map.put(val, map.getOrDefault(val,0)+1);    
		}

	}

    private void handleNext()
    {
        nextInt = null;
        while(nextInt == null && iterator.hasNext())
        {
            Integer val = iterator.next();
            if(!map.containsKey(val))
            {
                nextInt = val;
            }else{
                map.put(val,map.get(val)-1);
                map.remove(val,0);
            }
        }
    }
}
