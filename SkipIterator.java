//TC : O(#elements to skip) or O(N) in worst case for hasNext(), O(1) for next()
//SC : O(N), for the map to store elements to skip

//Approach
//We use a map to store elements to skip and their count
//when we call next could check this map if next item matches then skip and go to the next element
//but a bug could be not finding an element, last element is to be skipped
//hence doing this check in hasNext(), hasNext() is called every time before next() 

import java.util.HashMap;
import java.util.Iterator;

class SkipIterator implements Iterator<Integer> {
    HashMap<Integer, Integer> map;
    int[] nums;
    int idx;

	public SkipIterator(int[] nums) {
        this.nums = nums;
        map = new HashMap<>();
	}

	public boolean hasNext() {
        while(idx<nums.length && map.containsKey(nums[idx])){
            map.put(nums[idx], map.get(nums[idx])-1);
        
            if(map.get(nums[idx])==0){
                map.remove(nums[idx]);
            }

            idx++;
        }

        return idx<nums.length;
	}

	public Integer next() {
        return nums[idx++];
	}

	/**
	* The input parameter is an int, indicating that the next element equals 'val' needs to be skipped.
	* This method can be called multiple times in a row. skip(5), skip(5) means that the next two 5s should be skipped.
	*/ 
	public void skip(int val) {
        map.put(val, map.getOrDefault(val, 0) + 1);
    }

    public static void main(String[] args){
        int[] nums = {3, 4, 6, 7, 5, 8, 9};
        SkipIterator itr = new SkipIterator(nums);

        itr.skip(7);
        itr.skip(5);
        itr.skip(9);
        while(itr.hasNext()){
            System.out.println(itr.next());
        }
    }
}

