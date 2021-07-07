// Time Complexity :O(n) n is the number of integers
// Space Complexity :O(n)  n is the number of integers
// Did this code successfully run on Leetcode :Yes
// Any problem you faced while coding this :No


// Your code here along with comments explaining your approach


lass SkipIterator implements Iterator<Integer> {
    HashMap<Integer,Integer> map;
    Iterator<Integer> it;
    Integer nextEl;
	public SkipIterator(Iterator<Integer> it) {
        map = new HashMap<>();
        this.it = it;
        advance();
	}
    @Override
	public boolean hasNext() {
        return nextEl !=null;
	}
    @Override
	public Integer next() {
        int temp = nextEl;
        advance();
        return temp;
	}

    public void skip(int num) {
        if(num == nextEl){
            advance();
        }
        else
        {
            map.put(num, map.getOrDefault(num,0) +1);
        }
    }

    private void advance() {
        nextEl = null;
        while(nextEl == null && it.hasNext()){
            int el = it.next();
            if(map.containsKey(el)){
                map.put(el,map.get(el) -1);
                map.remove(el, 0);
            }
            nextEl = el;
            break;
        }

    }
}